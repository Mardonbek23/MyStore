package uz.mdev.mystore.ui.register_screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.R
import com.google.firebase.database.*
import uz.mdev.mystore.databinding.FragmentRegisterBinding
import uz.mdev.mystore.helpers.makeMyToast
import uz.mdev.mystore.helpers.progress_dialog
import uz.mdev.mystore.models.Account

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentRegister.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentRegister : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentRegisterBinding
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("users").child("managers")


        setbuttons()
        return binding.root
    }

    private fun setbuttons() {
        binding.apply {
            back.setOnClickListener {
                Navigation.findNavController(requireView()).popBackStack()
            }
            signIn.setOnClickListener {
                if (username.length() > 3) {
                    if (number.unMasked.length == 12) {
                        if (password.text!!.length > 3) {
                            if (password.text.toString() == rePassword.text.toString()) {
                                val progressDialog = requireContext().progress_dialog("Checking...")
                                val name = username.text.toString()
                                val password = password.text.toString()
                                val number = number.unMasked.toString()
                                reference
                                    .addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            var isHave = true
                                            for (child in snapshot.children) {
                                                val value = child.getValue(Account::class.java)
                                                if (value != null) {
                                                    if (value.number == number) {
                                                        requireContext().makeMyToast("Bu raqam ro'yxatda mavjud!")
                                                        progressDialog.dismiss()
                                                        isHave = false
                                                        break
                                                        return
                                                    }
                                                }
                                            }
                                            if (isHave) {
                                                progressDialog.dismiss()
                                                val bundle = Bundle()
                                                bundle.putString("name", name)
                                                bundle.putString("password", password)
                                                bundle.putString("number", number)
                                                Navigation.findNavController(requireView())
                                                    .navigate(
                                                        uz.mdev.mystore.R.id.fragmentSms,
                                                        bundle
                                                    )
                                            }
                                        }

                                        override fun onCancelled(error: DatabaseError) {

                                        }
                                    })
                            } else {
                                requireContext().makeMyToast("Password-2 must equal to password")
                            }
                        } else {
                            requireContext().makeMyToast("Password length must be more than 3 chars")
                        }
                    } else {
                        requireContext().makeMyToast("Number must be 12 chars")
                    }
                } else {
                    requireContext().makeMyToast("Username must be min 4 chars")
                }


            }
        }
    }


}