package uz.mdev.mystore.ui.main_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.mdev.mystore.databinding.FragmentCalculateBinding
import uz.mdev.mystore.local_data.SharedPreferencesManager
import java.lang.reflect.Type


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentCalculate.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentCalculate() : Fragment() {
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

    lateinit var binding: FragmentCalculateBinding
    lateinit var shared: SharedPreferencesManager
    lateinit var gson: Gson
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCalculateBinding.inflate(inflater, container, false)
        shared = SharedPreferencesManager(requireContext())
        gson = Gson()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (shared.getCalculateItems() != null) {
            val type: Type = object : TypeToken<Set<Int>>() {}.type
            val ids = gson.fromJson<HashSet<Int>>(shared.getCalculateItems(), type)
            Toast.makeText(requireContext(), "" + ids.size, Toast.LENGTH_SHORT).show()
        }


    }


}