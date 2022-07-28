package uz.mdev.mystore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mdev.mystore.databinding.ItemRvTableBinding
import uz.mdev.mystore.db.entities.Product
import uz.mdev.mystore.helpers.setFloatToCurrencyFormat
import uz.mdev.mystore.helpers.setIntToStr
import uz.mdev.mystore.helpers.setPercentForm

class TableAdapter(
    var list: ArrayList<Product>,
    var listener:
    TableAdapter.OnItemClickListener,
) : RecyclerView.Adapter<TableAdapter.Vh>() {

    inner class Vh(var itemRvBinding: ItemRvTableBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(product: Product) {
            itemRvBinding.apply {
                id.text = setIntToStr(product.id)
                name.setText(product.name)
                totalPrice.setText(setFloatToCurrencyFormat(product.total_price))
                minPrice.setText(setFloatToCurrencyFormat(product.min_price))
                quantity.setText(setIntToStr(product.quantity))
                interestPercent.setText(setPercentForm(product.interest_percent))
                manufacturer.setText(product.manufacturer.toString())
                giftQuantity.setText(setIntToStr(product.gift_quantity))
                giftPercent .setText(setPercentForm(product.gift_percent))
                giftPrice.setText(setFloatToCurrencyFormat(product.gift_price))
                edit.setOnClickListener {
                    listener.onEditClick(position, product)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvTableBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, product: Product)
        fun onEditClick(position: Int, product: Product)
    }

}
