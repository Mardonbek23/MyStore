package uz.mdev.mystore.ui.main_screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import uz.mdev.mystore.R
import uz.mdev.mystore.adapters.TableAdapter
import uz.mdev.mystore.databinding.DialogBottomAddProductBinding
import uz.mdev.mystore.databinding.FragmentHomeBinding
import uz.mdev.mystore.db.AppDatabase
import uz.mdev.mystore.db.dao.ProductDao
import uz.mdev.mystore.db.entities.product.Product
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
    lateinit var spinnerCategoryAdapter: ArrayAdapter<String>

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

    @SuppressLint("NotifyDataSetChanged")
    private fun setButtons() {
        binding.apply {

            //add product button
            addProduct.setOnClickListener {
                val product = Product(
                    0, null, 0, null, null, 1
                )
                val bottom_dialog =
                    BottomSheetDialog(requireContext(), R.style.BottomSheetDialogStyle)
                val binding_dialog = DialogBottomAddProductBinding.inflate(layoutInflater)
                binding_dialog.spinnerCategory.adapter = spinnerCategoryAdapter
                binding_dialog.apply {
                    plusBtn.setOnClickListener {
                        if (product.quantity_in_group < 1000) {
                            product.quantity_in_group = product.quantity_in_group + 1
                            quantity.setText(product.quantity_in_group.toString())
                        }
                    }
                    minusBtn.setOnClickListener {
                        if (product.quantity_in_group > 1) {
                            product.quantity_in_group = product.quantity_in_group - 1
                            quantity.setText(product.quantity_in_group.toString())
                        }
                    }
                    quantity.setOnEditorActionListener { v, actionId, event ->
                        if (quantity.text.isNotEmpty() && quantity.text.toString() != "0") {
                            product.quantity_in_group = quantity.text.toString().toInt()
                        }
                        return@setOnEditorActionListener false
                    }
                }
                binding_dialog.btnSaveEdit.setOnClickListener {

                    val name = binding_dialog.name.text
                    if (name.isNotEmpty()) {
                        product.name = name.toString()
                        product.category = binding_dialog.spinnerCategory.selectedItemPosition
                        product.description = binding_dialog.edDescription.text.toString()
                        if (binding_dialog.quantity.text.isNotEmpty() && binding_dialog.quantity.text.toString()
                                .toInt() > 0
                        ) {
                            product.quantity_in_group =
                                binding_dialog.quantity.text.toString().toInt()
                        }
                        product_dao.addProduct(product)
                        requireContext().makeMyToast("Added!")
                        bottom_dialog.dismiss()
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

            //calculate button
            calculate.setOnClickListener {
                var ids = ArrayList<Int>()
                for (product in tableAdapter.list) {
                    if (product.isSelected) {
                        ids.add(product.id)
                    }
                }
                if (ids.size > 0) {
                    val toJson = gson.toJson(ids)
                    shared.setCalculateItems(toJson)
                    interfaceFunctions.openCalculatePage()
                } else {
                    Toast.makeText(requireContext(), "Select items!", Toast.LENGTH_SHORT).show()
                }

            }

            //checkbox button
            checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
                for (i in list.indices) {
                    list[i].isSelected = isChecked
                }
                tableAdapter.list = list as ArrayList<Product>
                tableAdapter.notifyDataSetChanged()
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
        //set spinner adapter
        val spinner_items = ArrayList<String>()
        spinner_items.add("None")
        spinner_items.add("Products")
        spinner_items.add("Freshs")
        spinnerCategoryAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            spinner_items
        )
        spinnerCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        list = ArrayList()
        tableAdapter =
            TableAdapter(list as ArrayList<Product>, object : TableAdapter.OnItemClickListener {
                override fun onItemClick(position: Int, product: Product) {

                }

                @SuppressLint("SetTextI18n")
                override fun onEditClick(position: Int, product: Product) {
                    val bottom_dialog =
                        BottomSheetDialog(requireContext(), R.style.BottomSheetDialogStyle)
                    val binding_dialog = DialogBottomAddProductBinding.inflate(layoutInflater)
                    binding_dialog.apply {
                        spinnerCategory.adapter = spinnerCategoryAdapter
                        spinnerCategory.setSelection(product.category)
                        btnCalculate.show()
                        btnSaveEdit.text = "Edit"
                        title.text = intTo4digits(product.id)
                        editAttributes.show()
                        quantity.setText(product.quantity_in_group.toString())
                        price1x.setText(
                            "1x = " + setFloatToCurrencyWithSymbols(
                                product.total_price.div(
                                    product.quantity_in_group
                                )
                            )
                        )
                        plusBtn.setOnClickListener {
                            if (product.quantity_in_group != 1000) {
                                product.quantity_in_group = product.quantity_in_group + 1
                                quantity.setText(product.quantity_in_group.toString())
                            }
                        }
                        minusBtn.setOnClickListener {
                            if (product.quantity_in_group > 1) {
                                product.quantity_in_group = product.quantity_in_group - 1
                                quantity.setText(product.quantity_in_group.toString())
                            }
                        }
                        quantity.setOnEditorActionListener { v, actionId, event ->
                            if (quantity.text.isNotEmpty() && quantity.text.toString() != "0") {
                                product.quantity_in_group = quantity.text.toString().toInt()
                            }
                            return@setOnEditorActionListener false
                        }
                        //btns
                        btnSaveEdit.setOnClickListener {
                            if (quantity.text.isNotEmpty() && quantity.text.toString()
                                    .toInt() > 0
                            ) {
                                product.quantity_in_group = quantity.text.toString().toInt()
                            }
                            if (minPercent.text.isNotEmpty() && minPercent.text.toString()
                                    .toInt() != product.min_percent
                            ) {
                                product.min_percent = minPercent.text.toString().toInt()
                                product.min_price =
                                    product.tax_price + product.price_bought * (1 + product.min_percent.toFloat() / 100)
                            }
                            if (giftPercent.text.isNotEmpty() && giftPercent.text.toString()
                                    .toInt() < interestPercent.text.toString().toInt()
                            ) {
                                product.gift_percent = giftPercent.text.toString().toInt()
                                product.gift_price =
                                    product.tax_price + product.price_bought * (1 + (product.interest_percent - product.gift_percent).toFloat() / 100)
                            }
                            if (interestPercent.text.isNotEmpty() && interestPercent.text.toString()
                                    .toInt() != product.interest_percent
                            ) {
                                product.interest_percent = interestPercent.text.toString().toInt()
                                product.total_price =
                                    product.tax_price + product.price_bought * (1 + product.interest_percent.toFloat() / 100).toFloat()
                            }
                            product.description=edDescription.text.toString()
                            product.category=spinnerCategory.selectedItemPosition
                            product_dao.update(product)
                            requireContext().makeMyToast("Edited!")
                            bottom_dialog.dismiss()
                        }
                        btnClose.setOnClickListener {
                            bottom_dialog.dismiss()
                        }
                        btnCalculate.setOnClickListener {
                            if (minPercent.text.isNotEmpty() && minPercent.text.toString()
                                    .toInt() != product.min_percent
                            ) {
                                product.min_percent = minPercent.text.toString().toInt()
                                product.min_price =
                                    product.tax_price + product.price_bought * (1 + product.min_percent.toFloat() / 100)
                            }
                            if (giftPercent.text.isNotEmpty() && giftPercent.text.toString()
                                    .toInt() < interestPercent.text.toString().toInt()
                            ) {
                                product.gift_percent = giftPercent.text.toString().toInt()
                                product.gift_price =
                                    product.tax_price + product.price_bought * (1 + (product.interest_percent - product.gift_percent).toFloat() / 100)
                            }
                            if (interestPercent.text.isNotEmpty() && interestPercent.text.toString()
                                    .toInt() != product.interest_percent
                            ) {
                                product.interest_percent = interestPercent.text.toString().toInt()
                                product.total_price =
                                    product.tax_price + product.price_bought * (1 + product.interest_percent.toFloat() / 100).toFloat()
                            }
                            price1x.setText(
                                "1x = " + setFloatToCurrencyWithSymbols(
                                    product.total_price.div(
                                        product.quantity_in_group
                                    )
                                )
                            )
                            name.setText(product.name.toString())
                            giftPercent.setText(product.gift_percent.toString())
                            minPercent.setText(product.min_percent.toString())
                            interestPercent.setText(product.interest_percent.toString())
                            tvBoughtPrice.text = setFloatToCurrencyWithSymbols(product.price_bought)
                            tvGiftPrice.text = setFloatToCurrencyWithSymbols(product.gift_price)
                            tvMinPrice.text = setFloatToCurrencyWithSymbols(product.min_price)
                            tvTotalPrice.text = setFloatToCurrencyWithSymbols(product.total_price)
                        }



                        name.setText(product.name.toString())
                        giftPercent.setText(product.gift_percent.toString())
                        minPercent.setText(product.min_percent.toString())
                        interestPercent.setText(product.interest_percent.toString())
                        tvBoughtPrice.text = setFloatToCurrencyWithSymbols(product.price_bought)
                        tvGiftPrice.text = setFloatToCurrencyWithSymbols(product.gift_price)
                        tvMinPrice.text = setFloatToCurrencyWithSymbols(product.min_price)
                        tvTotalPrice.text = setFloatToCurrencyWithSymbols(product.total_price)

                        //not changing data

                        tvOldTaxPrice.text =
                            setFloatToCurrencyWithSymbols(product.old_tax_price)
                        tvTaxPrice.text = setFloatToCurrencyWithSymbols(product.tax_price)
                        tvOldPrice.setText(setFloatToCurrencyWithSymbols(product.old_total_price))
                        tvOldMinPrice.text =
                            setFloatToCurrencyWithSymbols(product.old_min_price)
                        tvOldGiftPrice.text =
                            setFloatToCurrencyWithSymbols(product.old_gift_price)
                        tvOldBoughtPrice.text =
                            setFloatToCurrencyWithSymbols(product.old_bought_price)
                    }
                    if (product.description != null) binding_dialog.edDescription.setText(product.description.toString())

                    //match parent
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
            list = ArrayList(it)
            tableAdapter.list = list as ArrayList<Product>
            tableAdapter.notifyDataSetChanged()
        }

    }

    interface interface_functions {
        fun openCalculatePage()
    }
}