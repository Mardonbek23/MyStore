package uz.mdev.mystore.ui.register_screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.mdev.mystore.R
import uz.mdev.mystore.databinding.FragmentSmsBinding

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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSmsBinding.inflate(inflater, container, false)


        return binding.root
    }

}