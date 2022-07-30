package uz.mdev.mystore.ui.main_screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.mdev.mystore.adapters.CalculateAdapter
import uz.mdev.mystore.databinding.FragmentCalculateBinding
import uz.mdev.mystore.db.AppDatabase
import uz.mdev.mystore.db.dao.ProductDao
import uz.mdev.mystore.db.entities.Product
import uz.mdev.mystore.helpers.makeMyToast
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
    lateinit var adapter: CalculateAdapter
    lateinit var list: ArrayList<Product>
    lateinit var productDao: ProductDao
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCalculateBinding.inflate(inflater, container, false)
        shared = SharedPreferencesManager(requireContext())
        gson = Gson()
        productDao = AppDatabase.getInstance(requireContext()).productDao()

        setAdapter()
        return binding.root
    }

    private fun setAdapter() {
        list = ArrayList()
        adapter = CalculateAdapter(list, object : CalculateAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, product: Product) {

            }

            override fun onEditClick(position: Int, product: Product) {
            }
        })
        val dividerItemDecoration = DividerItemDecoration(
            requireContext(),
            DividerItemDecoration.VERTICAL
        )
        binding.apply {
            rv.addItemDecoration(dividerItemDecoration)
            rv.adapter = adapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        if (shared.getCalculateItems() != null) {
            val type: Type = object : TypeToken<ArrayList<Int>>() {}.type
            val ids = gson.fromJson<ArrayList<Int>>(shared.getCalculateItems(), type)
            adapter.list = productDao.getProductsByIds(ids) as ArrayList<Product>
            adapter.notifyDataSetChanged()
        }


    }


}