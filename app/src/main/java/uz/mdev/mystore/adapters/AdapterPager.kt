package uz.mdev.mystore.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.mdev.mystore.db.entities.Product
import uz.mdev.mystore.ui.main_screen.*

class AdapterPager(
    fm: FragmentManager,
    lifecycle: Lifecycle,
    private val pageCount: Int,
    var interfaceFunctions: FragmentHome.interface_functions
) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = pageCount

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FragmentHome(interfaceFunctions)
            1 -> FragmentBusiness()
            2 -> FragmentCalculate()
            3 -> FragmentStatistics()
            else -> FragmentProfile()
        }
    }

}