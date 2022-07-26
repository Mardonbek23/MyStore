package uz.mdev.mystore.ui.main_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import uz.mdev.mystore.adapters.TableAdapter
import uz.mdev.mystore.databinding.FragmentHomeBinding
import uz.mdev.mystore.db.entities.Product


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentHome.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentHome : Fragment() {
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

    lateinit var binding: FragmentHomeBinding

    //adapters
    lateinit var tableAdapter: TableAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setAdapters()
        return binding.root
    }

    private fun setAdapters() {
        var list = ArrayList<Product>()
        list.add(Product(1, "Daftar", 1200000f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(2, "Daftar Kaptar", 1200000f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(11, "Daftar Varoq", 12000000f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(111, "Daftar SAdass", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(31, "Daftard DA d", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(41, "Daftard DADAA", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(1, "Daftar", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(2, "Daftar Kaptar", 120000f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(11, "Daftar Varoq", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(111, "Daftar SAdass", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(31, "Daftard DA d", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(41, "Daftard DADAA", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(1, "Daftar", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(2, "Daftar Kaptar", 120000f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(11, "Daftar Varoq", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(111, "Daftar SAdass", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(31, "Daftard DA d", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(41, "Daftard DADAA", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(1, "Daftar", 1200000f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(2, "Daftar Kaptar", 1200000f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(11, "Daftar Varoq", 12000000f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(111, "Daftar SAdass", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(31, "Daftard DA d", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(41, "Daftard DADAA", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(1, "Daftar", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(2, "Daftar Kaptar", 120000f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(11, "Daftar Varoq", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(111, "Daftar SAdass", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(31, "Daftard DA d", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(41, "Daftard DADAA", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(1, "Daftar", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(2, "Daftar Kaptar", 120000f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(11, "Daftar Varoq", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(111, "Daftar SAdass", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(31, "Daftard DA d", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        list.add(Product(41, "Daftard DADAA", 1200f, 1000f, 100, 10, "Ziyo Print", 10, 12, 2000f))
        tableAdapter = TableAdapter(list, object : TableAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, product: Product) {

            }
        })
        val dividerItemDecoration = DividerItemDecoration(requireContext(),
            DividerItemDecoration.VERTICAL)
        binding.rvProduct.addItemDecoration(dividerItemDecoration)
        binding.rvProduct.adapter = tableAdapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentHome.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentHome().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}