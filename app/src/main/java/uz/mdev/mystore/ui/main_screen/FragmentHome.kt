package uz.mdev.mystore.ui.main_screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import uz.mdev.mystore.R
import uz.mdev.mystore.adapters.TableAdapter
import uz.mdev.mystore.databinding.DialogBottomAddProductBinding
import uz.mdev.mystore.databinding.FragmentHomeBinding
import uz.mdev.mystore.db.AppDatabase
import uz.mdev.mystore.db.dao.ProductDao
import uz.mdev.mystore.db.entities.Product
import uz.mdev.mystore.helpers.makeMyToast


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

    //database
    lateinit var product_dao: ProductDao
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        product_dao = AppDatabase.getInstance(requireContext()).productDao()

        setAdapters()
        setButtons()
        return binding.root
    }

    private fun setButtons() {
        binding.apply {
            addProduct.setOnClickListener {
                var bottom_dialog =
                    BottomSheetDialog(requireContext(), R.style.BottomSheetDialogStyle)
                var binding_dialog = DialogBottomAddProductBinding.inflate(layoutInflater)

                binding_dialog.btnSaveEdit.setOnClickListener {
                    val name = binding_dialog.name.text
                    if (name.isNotEmpty()) {
                        product_dao.addProduct(
                            Product(
                                0,
                                name.toString(),
                                3,
                                null,
                                binding_dialog.description.toString()
                            )
                        )
                    } else {
                        requireContext().makeMyToast("Name is empty!")
                    }
                }
                binding_dialog.btnClose.setOnClickListener {
                    bottom_dialog.dismiss()
                }

//                bottom_dialog.setOnShowListener {
//                    val bottomSheetDialog = it as BottomSheetDialog
//                    val parentLayout =
//                        bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
//                    parentLayout?.let { it ->
//                        val behaviour = BottomSheetBehavior.from(it)
//                        setupFullHeight(it)
//                        behaviour.state = BottomSheetBehavior.STATE_EXPANDED
//                    }
//                }
                bottom_dialog.setContentView(binding_dialog.root)
                bottom_dialog.show()
            }
        }
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setAdapters() {
        var list = ArrayList<Product>()
        tableAdapter = TableAdapter(list, object : TableAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, product: Product) {

            }

            override fun onEditClick(position: Int, product: Product) {

            }
        })
        //rv decoration
        val dividerItemDecoration = DividerItemDecoration(
            requireContext(),
            DividerItemDecoration.VERTICAL
        )
        binding.rvProduct.addItemDecoration(dividerItemDecoration)
        product_dao.getAllProducts().observe(
            viewLifecycleOwner
        ) {
            list = ArrayList(it)
            Toast.makeText(requireContext(), "" + it.size, Toast.LENGTH_SHORT).show()
            tableAdapter.list = list
            binding.rvProduct.adapter = tableAdapter
        }

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