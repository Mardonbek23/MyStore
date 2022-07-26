package uz.mdev.mystore.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import uz.mdev.mystore.R
import uz.mdev.mystore.adapters.AdapterPager
import uz.mdev.mystore.databinding.FragmentHeadBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentHead.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentHead : Fragment() {
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

    lateinit var binding: FragmentHeadBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHeadBinding.inflate(inflater, container, false)
        setComponents()
        setFab()
        return binding.root
    }

    private fun setComponents() {
        binding.apply {
            //set view pager adapter
            viewPager.adapter = AdapterPager(childFragmentManager, lifecycle, 5)
            viewPager.isUserInputEnabled = false

            //set bottom navigation
            bottomNavigationView.background = null
            bottomNavigationView.menu.getItem(2).isEnabled = false
            bottomNavigationView.setOnItemSelectedListener {
                toolbar.visibility=View.VISIBLE
                when (it.itemId) {
                    R.id.item_home -> {
                        viewPager.setCurrentItem(0, false)
                        toolbar.setTitle("Home")
                    }
                    R.id.item_business -> {
                        viewPager.setCurrentItem(1, false)
                        toolbar.setTitle("Business")
                    }
                    R.id.item_statistics -> {
                        viewPager.setCurrentItem(3, false)
                        toolbar.setTitle("Statistics")
                    }
                    R.id.item_profile -> {
                        viewPager.setCurrentItem(4, false)
                        toolbar.visibility=View.GONE
                    }
                }
                return@setOnItemSelectedListener true
            }
        }
    }

    private fun setFab() {
        binding.apply {
            fab.setOnClickListener {
                bottomNavigationView.menu.getItem(2).isChecked = true
                viewPager.setCurrentItem(2, false)
                toolbar.setTitle("Calculator")
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentHead.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentHead().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}