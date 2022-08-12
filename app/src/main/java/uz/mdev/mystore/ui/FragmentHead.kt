package uz.mdev.mystore.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.mdev.mystore.R
import uz.mdev.mystore.adapters.AdapterPager
import uz.mdev.mystore.databinding.FragmentHeadBinding
import uz.mdev.mystore.helpers.hide
import uz.mdev.mystore.helpers.show
import uz.mdev.mystore.local_data.SharedPreferencesManager
import uz.mdev.mystore.ui.main_screen.FragmentHome
import uz.mdev.mystore.ui.main_screen.FragmentProfile

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
    lateinit var shared: SharedPreferencesManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHeadBinding.inflate(inflater, container, false)
        shared = SharedPreferencesManager(requireContext())
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
                        landscapeMode.show()
                        viewPager.currentItem = 2
                        toolbar.title = "Calculator"
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
                logout.hide()
                imageAcc.show()
                requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                when (it.itemId) {
                    R.id.item_home -> {
                        landscapeMode.show()
                        viewPager.setCurrentItem(0, false)
                        toolbar.title = "Home"
                    }
                    R.id.item_business -> {
                        viewPager.setCurrentItem(1, false)
                        toolbar.title = "Business"
                    }
                    R.id.item_statistics -> {
                        viewPager.setCurrentItem(3, false)
                        toolbar.title = "Statistics"
                    }
                    R.id.item_profile -> {
                        viewPager.setCurrentItem(4, false)
                        toolbar.title = ""
                        logout.show()
                        imageAcc.hide()
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

            //logout
            logout.setOnClickListener {
                if (shared.getAccount() != null) {
                    var dialog = AlertDialog.Builder(requireContext())
                    dialog.setTitle("Log out")
                    dialog.setMessage("Are you sure to logout?")
                    dialog.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            shared.setAccount(null)
                            setComponents()
                            onResume()
                        }
                    })
                    dialog.setNegativeButton("No", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {

                        }
                    })
                    dialog.show()
                }

            }
        }
    }

    private fun setFab() {
        binding.apply {
            fab.setOnClickListener {
                toolbar.show()
                logout.hide()
                bottomNavigationView.menu.getItem(2).isChecked = true
                viewPager.setCurrentItem(2, false)
                toolbar.title = "Calculator"
                landscapeMode.show()
                imageAcc.show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.apply {
            toolbar.show()
            landscapeMode.hide()
            logout.hide()
            imageAcc.show()
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            when (binding.bottomNavigationView.selectedItemId) {
                R.id.item_home -> {
                    landscapeMode.show()
                    viewPager.setCurrentItem(0, false)
                    toolbar.title = "Home"
                }
                R.id.item_business -> {
                    viewPager.setCurrentItem(1, false)
                    toolbar.title = "Business"
                }
                R.id.item_statistics -> {
                    viewPager.setCurrentItem(3, false)
                    toolbar.title = "Statistics"
                }
                R.id.item_profile -> {
                    viewPager.setCurrentItem(4, false)
                    toolbar.title = ""
                    logout.show()
                    imageAcc.hide()
                }
                R.id.item_calculate -> {
                    toolbar.show()
                    logout.hide()
                    bottomNavigationView.menu.getItem(2).isChecked = true
                    viewPager.setCurrentItem(2, false)
                    toolbar.title = "Calculator"
                    landscapeMode.show()
                    imageAcc.show()
                }
            }
        }

    }

}