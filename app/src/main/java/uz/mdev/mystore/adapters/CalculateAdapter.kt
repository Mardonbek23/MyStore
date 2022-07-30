package uz.mdev.mystore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mdev.mystore.databinding.ItemRvCalculateBinding
import uz.mdev.mystore.databinding.ItemRvTableBinding
import uz.mdev.mystore.db.entities.Product
import uz.mdev.mystore.helpers.intTo4digits
import uz.mdev.mystore.helpers.setFloatToCurrencyFormat
import uz.mdev.mystore.helpers.setIntToStr
import uz.mdev.mystore.helpers.setPercentForm

class CalculateAdapter(
    var list: ArrayList<Product>,
    var listener:
    CalculateAdapter.OnItemClickListener,
) : RecyclerView.Adapter<CalculateAdapter.Vh>() {

    inner class Vh(var itemRvBinding: ItemRvCalculateBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(product: Product) {
            itemRvBinding.apply {
                id.text = intTo4digits(product.id)
                name.setText(product.name)
                tvTotalPrice.setText(setFloatToCurrencyFormat(product.total_price))
                tvBoughtPrice.setText(setFloatToCurrencyFormat(product.price_bought))
                tvTaxPrice.setText(setFloatToCurrencyFormat(product.tax_price))
                tvTaxPrice.setText(setFloatToCurrencyFormat(product.tax_price))
                tvMinPrice.setText(setFloatToCurrencyFormat(product.min_price))
                tvOldPrice.setText(setFloatToCurrencyFormat(product.old_total_price))
                tvTotalPrice.setText(setFloatToCurrencyFormat(product.old_total_price))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvCalculateBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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
