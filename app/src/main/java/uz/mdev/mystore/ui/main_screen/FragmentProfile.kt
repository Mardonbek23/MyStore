package uz.mdev.mystore.ui.main_screen

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import uz.mdev.mystore.R
import uz.mdev.mystore.databinding.DialogAddShopperBinding
import uz.mdev.mystore.databinding.DialogBottomUserProfileBinding
import uz.mdev.mystore.databinding.FragmentProfileBinding
import uz.mdev.mystore.helpers.getTypeToken
import uz.mdev.mystore.helpers.makeMyToast
import uz.mdev.mystore.local_data.SharedPreferencesManager
import uz.mdev.mystore.models.Account
import uz.mdev.mystore.models.Shop

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentProfile : Fragment() {
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

    lateinit var binding: FragmentProfileBinding
    lateinit var shared: SharedPreferencesManager
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        shared = SharedPreferencesManager(requireContext())
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference =
            firebaseDatabase.getReference("users")

        setButtons()
        setUi()
        return binding.root
    }

    override fun onResume() {
        super.onResume()

    }

    public fun setUi() {
        binding.apply {
            if (shared.getAccount() != null) {
                val account = Gson().fromJson<Account>(shared.getAccount(), Account::class.java)
                companyName.setText(account.name.toString())
                plan.setText(account.plan.toString())
            } else {
                companyName.setText("Not registered")
                plan.setText("---")
            }

        }
    }

    private fun setButtons() {
        binding.apply {
            profile.setOnClickListener {
                if (shared.getAccount() != null) {
                    val account =
                        Gson().fromJson<Account>(shared.getAccount(), Account::class.java)
                    val bottom_dialog =
                        BottomSheetDialog(requireContext(), R.style.BottomSheetDialogStyle)
                    val binding_dialog = DialogBottomUserProfileBinding.inflate(layoutInflater)
                    binding_dialog.number.setText("+${account.number}")
                    binding_dialog.username.setText(account.name.toString())
                    bottom_dialog.setContentView(binding_dialog.root)
                    bottom_dialog.show()
                } else {
                    Navigation.findNavController(requireView()).navigate(R.id.fragmentLogin)
                }
            }
            messages.setOnClickListener {
                if (shared.getAccount() != null) {
                    Navigation.findNavController(requireView()).navigate(R.id.fragmentUsers)
                } else {
                    requireContext().makeMyToast("Ro'yxatdan o'ting!")
                }
            }
            addShopBoys.setOnClickListener {
                if (shared.getAccount() != null) {
                    val account =
                        Gson().fromJson<Account>(shared.getAccount(), Account::class.java)

                    val builder = AlertDialog.Builder(requireContext())
                    val inflate = DialogAddShopperBinding.inflate(layoutInflater)
                    builder.setView(inflate.root)
                    builder.setPositiveButton("Add", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            val shop =
                                Shop(account.name, account.number, System.currentTimeMillis())
                            reference.child("shop_boys").child(inflate.number.toString())
                                .child("shop").setValue(shop)
                            requireContext().makeMyToast("Done!")
                        }
                    })
                    builder.show()
                } else {
                    requireContext().makeMyToast("Ro'yxatdan o'ting!")
                }
            }

        }
    }

}