package uz.mdev.mystore.ui.messaging

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.*
import com.google.gson.Gson
import uz.mdev.mystore.R
import uz.mdev.mystore.adapters.MessageAdapter
import uz.mdev.mystore.adapters.SelectProductAdapter
import uz.mdev.mystore.databinding.DialogAddProductBinding
import uz.mdev.mystore.databinding.DialogBottomAddProductBinding
import uz.mdev.mystore.databinding.FragmentMessageBinding
import uz.mdev.mystore.db.AppDatabase
import uz.mdev.mystore.db.dao.ProductDao
import uz.mdev.mystore.helpers.hide
import uz.mdev.mystore.helpers.makeMyToast
import uz.mdev.mystore.helpers.show
import uz.mdev.mystore.local_data.SharedPreferencesManager
import uz.mdev.mystore.models.Account
import uz.mdev.mystore.models.Message
import uz.mdev.mystore.models.SharingData
import uz.mdev.mystore.models.User

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "number"
private const val ARG_PARAM2 = "name"
private const val ARG_PARAM3 = "image"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMessage.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentMessage : Fragment() {
    // TODO: Rename and change types of parameters
    private var number: String? = null
    private var name: String? = null
    private var image: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            number = it.getString(ARG_PARAM1)
            name = it.getString(ARG_PARAM2)
            image = it.getString(ARG_PARAM3)
        }
    }

    lateinit var binding: FragmentMessageBinding
    lateinit var shared: SharedPreferencesManager
    lateinit var account: Account
    lateinit var list: ArrayList<Message>
    lateinit var adapter: MessageAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var productt_selected_list: ArrayList<SharingData>
    lateinit var product_dao: ProductDao

    //Firebase
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var reference_2: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        shared = SharedPreferencesManager(requireContext())
        account = Gson().fromJson(shared.getAccount(), Account::class.java)
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference =
            firebaseDatabase.getReference("messages").child("managers").child("${account.number}")
                .child("${number}")
        reference_2 =
            firebaseDatabase.getReference("messages").child("shop_boys").child("${number}")
                .child("${account.number}")
        linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        product_dao = AppDatabase.getInstance(requireContext()).productDao()

        setAdapter()
        setList()
        setButtons()
        setSender()
        return binding.root
    }

    private fun setSender() {
        binding.apply {
            text.addTextChangedListener {
                setSendVisibility()
            }
        }
    }

    private fun setSendVisibility() {
        var string = binding.text.text.toString().replace(" ", "")
        if (string.length > 0 || productt_selected_list.size > 0) {
            binding.send.show()
        } else {
            binding.send.hide()
        }
    }

    fun setAddProduct() {
        if (productt_selected_list.size > 0) {
            binding.addProduct.hide()
            binding.clearSelecteds.show()
        } else {
            binding.addProduct.show()
            binding.clearSelecteds.hide()
        }
        setSendVisibility()
    }

    private fun setButtons() {

        binding.apply {
            //send button
            send.setOnClickListener {
                val key1 = reference.push().key.toString()

                val message = Message(
                    account.number,
                    number,
                    System.currentTimeMillis(),
                    false,
                    key1,
                    0,
                    text.text.toString(),
                    null,
                    null,
                    0,
                    productt_selected_list,
                    null
                )
                reference.child(key1)
                    .setValue(message)
                reference_2.child(key1)
                    .setValue(message)
                text.setText("")
                productt_selected_list.clear()
                setAddProduct()
            }
            addProduct.setOnClickListener {
                val bottom_dialog =
                    BottomSheetDialog(requireContext(), R.style.BottomSheetDialogStyle)
                val binding_dialog = DialogAddProductBinding.inflate(layoutInflater)
                binding_dialog.apply {
                    val products = ArrayList<SharingData>()
                    for (product in product_dao.getAllProductsWithoutLiveData()) {
                        products.add(SharingData(product.id, product.name, 1, product.total_price))
                    }
                    val adapter_pr = SelectProductAdapter(products,
                        object : SelectProductAdapter.OnItemClickListener {
                            override fun onItemClick(position: Int, user: User) {

                            }
                        })
                    rv.addItemDecoration(
                        DividerItemDecoration(
                            requireContext(),
                            LinearLayoutManager.VERTICAL
                        )
                    )
                    rv.adapter = adapter_pr
                    close.setOnClickListener {
                        bottom_dialog.dismiss()
                    }
                    btnOk.setOnClickListener {
                        val selecteds = ArrayList<SharingData>()
                        for (sharingData in adapter_pr.list) {
                            if (sharingData.isSelected) {
                                selecteds.add(sharingData)
                            }
                        }
                        productt_selected_list = selecteds
                        setAddProduct()
                        bottom_dialog.dismiss()
                    }
                }

                bottom_dialog.setContentView(binding_dialog.root)
                bottom_dialog.show()
            }
            clearSelecteds.setOnClickListener {
                productt_selected_list.clear()
                setAddProduct()
            }

        }
    }

    private fun setAdapter() {
        productt_selected_list = ArrayList()

        list = ArrayList()
        adapter = MessageAdapter(
            list,
            account.number.toString(),
            number,
            object : MessageAdapter.OnItemClickListener {})
        binding.rv.adapter = adapter
        binding.rv.layoutManager = linearLayoutManager
    }

    private fun setList() {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (child in snapshot.children) {
                    val value = child.getValue(Message::class.java)
                    if (value != null) {
                        list.add(value)
                    }
                }
                adapter.list = list
                adapter.notifyDataSetChanged()
                binding.progress.hide()
                if (::adapter.isInitialized) {
                    binding.rv.smoothScrollToPosition(adapter.itemCount)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                binding.progress.hide()
            }
        })
    }


}