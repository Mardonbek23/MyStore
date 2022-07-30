package uz.mdev.mystore.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.mdev.mystore.R
import uz.mdev.mystore.adapters.AdapterPager
import uz.mdev.mystore.databinding.FragmentHeadBinding
import uz.mdev.mystore.db.entities.Product
import uz.mdev.mystore.helpers.hide
import uz.mdev.mystore.helpers.show
import uz.mdev.mystore.ui.main_screen.FragmentHome
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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
    lateinit var adapterPager: AdapterPager
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
            adapterPager = AdapterPager(
                childFragmentManager,
                lifecycle,
                5,
                object : FragmentHome.interface_functions {
                    override fun openCalculatePage() {
                        landscapeMode.hide()
                        viewPager.setCurrentItem(2)
                        binding.bottomNavigationView.selectedItemId = R.id.item_calculate
                    }
                })
            viewPager.adapter = adapterPager
            viewPager.isUserInputEnabled = false

            //set bottom navigation
            bottomNavigationView.background = null
            bottomNavigationView.menu.getItem(2).isEnabled = false
            bottomNavigationView.setOnItemSelectedListener {
                toolbar.show()
                landscapeMode.hide()
                requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                when (it.itemId) {
                    R.id.item_home -> {
                        landscapeMode.show()
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
                        toolbar.visibility = View.GONE
                    }
                }
                return@setOnItemSelectedListener true
            }

            //set screen mode
            landscapeMode.setOnClickListener {
                if (requireActivity().requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    requireActivity().requestedOrientation =
                        ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                } else {
                    requireActivity().requestedOrientation =
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                }
            }
        }
    }

    private fun setFab() {
        binding.apply {
            fab.setOnClickListener {
                bottomNavigationView.menu.getItem(2).isChecked = true
                viewPager.setCurrentItem(2, false)
                toolbar.setTitle("Calculator")
                landscapeMode.hide()
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