package uz.mdev.mystore.ui.register_screens

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.Navigation
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import uz.mdev.mystore.R
import uz.mdev.mystore.databinding.FragmentSmsBinding
import uz.mdev.mystore.helpers.hide
import uz.mdev.mystore.helpers.makeMyToast
import uz.mdev.mystore.helpers.show
import uz.mdev.mystore.models.Account
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "number"
private const val ARG_PARAM2 = "password"
private const val ARG_PARAM3 = "name"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentSms.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentSms : Fragment() {
    // TODO: Rename and change types of parameters
    private var number: String? = null
    private var name: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(ARG_PARAM3)
            number = it.getString(ARG_PARAM1)
            password = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentSmsBinding

    //firebase auth
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    var sec = 45L
    lateinit var time: CountDownTimer
    val simpleDateFormat = SimpleDateFormat("mm:ss")

    //realtime firebase
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSmsBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("users").child("managers")

        setVerificationCode("+$number")
        setTime()
        checkOtp()
        setResend()
        setButtons()
        return binding.root
    }

    private fun setButtons() {
        binding.back.setOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }
    }

    private fun setResend() {
        binding.refresh.setOnClickListener {
            binding.refresh.hide()
            binding.seconds.show()
            time.start()
            resendCode("+$number")
        }
    }

    private fun checkOtp() {
        binding.otpCode.addTextChangedListener {
            if (binding.otpCode.text.toString().length == 6) {
                val credential = PhoneAuthProvider.getCredential(
                    storedVerificationId,
                    binding.otpCode.text.toString()
                )
                signInWithPhoneAuthCredential(credential)
            }
        }
    }

    private fun setTime() {
        time = object : CountDownTimer(sec * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.seconds.setText(simpleDateFormat.format(millisUntilFinished))
            }

            override fun onFinish() {
                binding.apply {
                    seconds.hide()
                    refresh.show()
                }
            }
        }
        time.start()
    }


    fun setVerificationCode(phoneNumber: String) {
        requireContext().makeMyToast(phoneNumber)
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(sec, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            signInWithPhoneAuthCredential(credential)
            binding.otpCode.setText(credential.smsCode)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            if (e is FirebaseAuthInvalidCredentialsException) {
                Log.d(
                    "firebase error",
                    "onVerificationFailed: " + e.message + "  code" + e.errorCode + "  --" + e.cause
                )
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            }
            requireContext().makeMyToast("fail" + e.message!!)

            Log.d("firebase common", "onVerificationFailed: " + e.message + "  code" + e.cause)
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            requireContext().makeMyToast("Kode sent")
            storedVerificationId = verificationId
            resendToken = token
        }
    }

    private fun resendCode(phoneNumber: String) {
        if (::resendToken.isInitialized) {
            val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phoneNumber)       // Phone number to verify
                .setTimeout(sec, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(requireActivity())                 // Activity (for callback binding)
                .setCallbacks(callbacks)       // OnVerificationStateChangedCallbacks
                .setForceResendingToken(resendToken)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                binding.progress.show()
                binding.seconds.hide()
                binding.refresh.hide()
                reference.child(number!!).setValue(
                    Account(
                        name,
                        null,
                        number,
                        password,
                        null,
                        "Manager",
                        System.currentTimeMillis()
                    ), object : DatabaseReference.CompletionListener {
                        override fun onComplete(error: DatabaseError?, ref: DatabaseReference) {
                            if (error == null) {
                                Navigation.findNavController(requireView())
                                    .popBackStack(R.id.fragmentLogin, false)
                                Navigation.findNavController(requireView())
                                    .popBackStack(R.id.fragmentRegister, false)
                                Navigation.findNavController(requireView()).popBackStack()
                            }
                        }
                    }
                )

            } else {
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(requireContext(), "Kod notug'ri kiritildi!", Toast.LENGTH_SHORT)
                        .show()
                }
                // Update UI
            }
        }
    }
}