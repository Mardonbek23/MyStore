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
import com.google.gson.Gson
import uz.mdev.mystore.R
import uz.mdev.mystore.adapters.AdapterPager
import uz.mdev.mystore.adapters.TableAdapter
import uz.mdev.mystore.databinding.DialogBottomAddProductBinding
import uz.mdev.mystore.databinding.FragmentHomeBinding
import uz.mdev.mystore.db.AppDatabase
import uz.mdev.mystore.db.dao.ProductDao
import uz.mdev.mystore.db.entities.Product
import uz.mdev.mystore.helpers.*
import uz.mdev.mystore.local_data.SharedPreferencesManager


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentHome.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentHome(var interfaceFunctions: interface_functions) : Fragment() {
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
    lateinit var list: List<Product>

    //database
    lateinit var product_dao: ProductDao

    //local data
    lateinit var shared: SharedPreferencesManager
    lateinit var gson: Gson
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        product_dao = AppDatabase.getInstance(requireContext()).productDao()
        shared = SharedPreferencesManager(requireContext())
        gson = Gson()

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
                                binding_dialog.edDescription.text.toString()
                            )
                        )
                    } else {
                        requireContext().makeMyToast("Name is empty!")
                    }
                }
                binding_dialog.btnClose.setOnClickListener {
                    bottom_dialog.dismiss()
                }

                bottom_dialog.setContentView(binding_dialog.root)
                bottom_dialog.show()
            }

            calculate.setOnClickListener {
                var ids = HashSet<Int>()
                for (product in tableAdapter.list) {
                    if (product.isSelected) {
                        ids.add(product.id)
                    }
                }
                if (ids.size>0){
                    val toJson = gson.toJson(ids)
                    shared.setCalculateItems(toJson)
                    interfaceFunctions.openCalculatePage()
                }
                else{
                    Toast.makeText(requireContext(), "Select items!", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onResume() {
        super.onResume()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setAdapters() {
        list = ArrayList()
        tableAdapter =
            TableAdapter(list as ArrayList<Product>, object : TableAdapter.OnItemClickListener {
                override fun onItemClick(position: Int, product: Product) {

                }

                override fun onEditClick(position: Int, product: Product) {
                    val bottom_dialog =
                        BottomSheetDialog(requireContext(), R.style.BottomSheetDialogStyle)
                    val binding_dialog = DialogBottomAddProductBinding.inflate(layoutInflater)
                    binding_dialog.apply {

                        btnSaveEdit.setOnClickListener {

                        }
                        btnClose.setOnClickListener {
                            bottom_dialog.dismiss()
                        }
                        btnSaveEdit.text = "Edit"
                        title.text = intTo4digits(product.id)
                        editAttributes.show()
                        name.setText(product.name.toString())
                        giftPercent.text = setPercentForm(product.gift_percent)
                        minPercent.text = setPercentForm(product.min_percent)
                        interestPercent.text = setPercentForm(product.interest_percent)
                        tvOldBoughtPrice.text =
                            setFloatToCurrencyWithSymbols(product.old_bought_price)
                        tvBoughtPrice.text = setFloatToCurrencyWithSymbols(product.price_bought)
                        tvOldTaxPrice.text = setFloatToCurrencyWithSymbols(product.old_tax_price)
                        tvTaxPrice.text = setFloatToCurrencyWithSymbols(product.tax_price)
                        tvOldGiftPrice.text = setFloatToCurrencyWithSymbols(product.old_gift_price)
                        tvGiftPrice.text = setFloatToCurrencyWithSymbols(product.gift_price)
                        tvOldMinPrice.text = setFloatToCurrencyWithSymbols(product.old_min_price)
                        tvMinPrice.text = setFloatToCurrencyWithSymbols(product.min_price)
                        tvOldPrice.setText(setFloatToCurrencyWithSymbols(product.old_total_price))
                        tvTotalPrice.setText(setFloatToCurrencyWithSymbols(product.total_price))


                    }
                    if (product.description != null) binding_dialog.edDescription.setText(product.description.toString())
                    bottom_dialog.setOnShowListener {
                        val bottomSheetDialog = it as BottomSheetDialog
                        val parentLayout =
                            bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                        parentLayout?.let { it ->
                            val behaviour = BottomSheetBehavior.from(it)
                            setupFullHeight(it)
                            behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                        }
                    }
                    bottom_dialog.setContentView(binding_dialog.root)
                    bottom_dialog.show()
                }
            })

        //rv decoration
        val dividerItemDecoration = DividerItemDecoration(
            requireContext(),
            DividerItemDecoration.VERTICAL
        )
        binding.rvProduct.addItemDecoration(dividerItemDecoration)
        binding.rvProduct.adapter = tableAdapter

        product_dao.getAllProducts().observe(
            viewLifecycleOwner
        ) {
            tableAdapter.list = it as ArrayList<Product>
            tableAdapter.notifyDataSetChanged()
        }


    }

    interface interface_functions {
        fun openCalculatePage()
    }
}