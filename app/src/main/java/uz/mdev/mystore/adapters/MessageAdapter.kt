package uz.mdev.mystore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import uz.mdev.mystore.databinding.ItemFromBinding
import uz.mdev.mystore.databinding.ItemToBinding
import uz.mdev.mystore.helpers.hide
import uz.mdev.mystore.helpers.setFloatToCurrencyWithSymbols
import uz.mdev.mystore.helpers.show
import uz.mdev.mystore.models.Message
import uz.mdev.mystore.models.SharingData
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

class MessageAdapter(
    var list: ArrayList<Message>,
    var my_phone: String,
    var phone_other_user: String?,
    var listener: OnItemClickListener,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var simpleDateFormat = SimpleDateFormat("HH:mm")

    inner class FromVh(var itemFromBinding: ItemFromBinding) :
        RecyclerView.ViewHolder(itemFromBinding.root) {

        fun onBind(message: Message) {
            itemFromBinding.apply {
                text.text = message.text
                time.text = simpleDateFormat.format(message.date)
                if (message.list != null && message.list!!.size > 0) {
                    products_have(message)
                    var sum = 0f
                    message.list!!.forEach {
                        sum += it.quantity!! * it.price!!
                    }
                    totalPrice.setText(setFloatToCurrencyWithSymbols(sum))
                }
            }

        }

        fun products_have(message: Message) {
            itemFromBinding.apply {
                val adapter_in_rv = ProductsOnRvAdapter(message.list!! as ArrayList<SharingData>)
                llProducts.show()
                rv.adapter = adapter_in_rv

                val firebaseDatabase = FirebaseDatabase.getInstance()
                val reference =
                    firebaseDatabase.getReference("messages").child("managers")
                        .child("${my_phone}")
                        .child("${phone_other_user}")
                val reference_2 =
                    firebaseDatabase.getReference("messages").child("shop_boys")
                        .child("${phone_other_user}")
                        .child("${my_phone}")

                itemView.setOnLongClickListener {
                    edit.show()
                    true
                }

                edit.setOnClickListener {
                    message.list = adapter_in_rv.list
                    reference.child(message.key.toString()).setValue(message)
                    reference_2.child(message.key.toString()).setValue(message)
                    edit.hide()
                }
            }
        }

    }

    inner class ToVh(var itemToBinding: ItemToBinding) :
        RecyclerView.ViewHolder(itemToBinding.root) {
        fun onBind(message: Message) {
            itemToBinding.apply {
                text.text = message.text
                time.text = simpleDateFormat.format(message.date)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            return FromVh(
                ItemFromBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            return ToVh(
                ItemToBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FromVh) {
            holder.onBind(list[position])
        } else {
            val toVh = holder as ToVh
            toVh.onBind(list[position])
        }
        holder.setIsRecyclable(false)
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].from == my_phone) {
            0
        } else 1
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {

    }
}
