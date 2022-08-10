package uz.mdev.mystore.ui.main_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetDialog
import uz.mdev.mystore.R
import uz.mdev.mystore.databinding.DialogBottomAddProductBinding
import uz.mdev.mystore.databinding.DialogBottomUserProfileBinding
import uz.mdev.mystore.databinding.FragmentProfileBinding
import uz.mdev.mystore.local_data.SharedPreferencesManager

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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        shared = SharedPreferencesManager(requireContext())
        setButtons()
        return binding.root
    }

    private fun setButtons() {
        binding.apply {
            profile.setOnClickListener {
                if (shared.getAccount() != null) {
                    val bottom_dialog =
                        BottomSheetDialog(requireContext(), R.style.BottomSheetDialogStyle)
                    val binding_dialog = DialogBottomUserProfileBinding.inflate(layoutInflater)
                    bottom_dialog.setContentView(binding_dialog.root)
                    bottom_dialog.show()
                } else {
                    Navigation.findNavController(requireView()).navigate(R.id.fragmentLogin)
                }
            }
        }
    }

}