package uz.mdev.mystore.ui.register_screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.firebase.database.*
import com.google.gson.Gson
import uz.mdev.mystore.R
import uz.mdev.mystore.databinding.FragmentLoginBinding
import uz.mdev.mystore.helpers.getTypeToken
import uz.mdev.mystore.helpers.makeMyToast
import uz.mdev.mystore.helpers.progress_dialog
import uz.mdev.mystore.local_data.SharedPreferencesManager
import uz.mdev.mystore.models.Account

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentLogin.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentLogin : Fragment() {
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

    lateinit var binding: FragmentLoginBinding

    //realtime firebase
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference

    lateinit var shared: SharedPreferencesManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("users").child("managers")
        shared = SharedPreferencesManager(requireContext())

        setBtns()
        return binding.root
    }

    private fun setBtns() {
        binding.apply {
            back.setOnClickListener {
                Navigation.findNavController(requireView()).popBackStack()
            }
            signIn.setOnClickListener {
                Navigation.findNavController(requireView()).navigate(R.id.fragmentRegister)
            }
            login.setOnClickListener {
                val number = number.unMasked.toString()
                val password = password.text.toString()
                if (number.length == 12) {
                    if (password.length > 3) {
                        llPassword.isErrorEnabled = false
                        val progressDialog = requireContext().progress_dialog("Checking...")
                        progressDialog.show()
                        reference.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                var isCheck = false
                                for (child in snapshot.children) {
                                    val value = child.getValue(Account::class.java)
                                    if (value != null && value.number == number && value.password == password) {
                                        progressDialog.hide()
                                        isCheck = true
                                        Navigation.findNavController(requireView()).popBackStack()
                                        val json_obj = Gson().toJson(value)
                                        shared.setAccount(json_obj)
                                        break
                                        return
                                    }
                                }
                                if (!isCheck) {
                                    progressDialog.hide()
                                    requireContext().makeMyToast("Wrong Name or Password!")
                                }

                            }

                            override fun onCancelled(error: DatabaseError) {

                            }
                        })

                    } else {
                        llPassword.error = "Password length must be more than 3 chars"
                        llPassword.isErrorEnabled = true
                    }
                    llNumber.isErrorEnabled = false
                } else {
                    llNumber.error = "Number format +998 ** *** ** **"
                    llNumber.isErrorEnabled = true
                }
            }
        }
    }


}