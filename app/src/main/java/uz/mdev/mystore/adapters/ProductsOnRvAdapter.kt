package uz.mdev.mystore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mdev.mystore.databinding.ItemFromProductsRvBinding
import uz.mdev.mystore.databinding.ItemSelectBinding
import uz.mdev.mystore.helpers.hide
import uz.mdev.mystore.helpers.setFloatToCurrencyFormat
import uz.mdev.mystore.helpers.show
import uz.mdev.mystore.models.SharingData
import uz.mdev.mystore.models.User

class ProductsOnRvAdapter(
    var list: ArrayList<SharingData>
) : RecyclerView.Adapter<ProductsOnRvAdapter.Vh>() {

    inner class Vh(var itemRvBinding: ItemFromProductsRvBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(sharingData: SharingData) {
            itemRvBinding.name.setText(sharingData.name)
            itemRvBinding.quantity.setText(sharingData.quantity.toString())
            itemRvBinding.price.setText(setFloatToCurrencyFormat(sharingData.price!!))
        }

        fun onClick(sharingData: SharingData) {
            itemRvBinding.apply {
                btnPlus.setOnClickListener {
                    if (sharingData.quantity!! < 100) {
                        list[adapterPosition].quantity = list[adapterPosition].quantity!! + 1
                        onBind(list[adapterPosition])
                    }
                }
                btnMinus.setOnClickListener {
                    if (sharingData.quantity!!>0){
                        list[adapterPosition].quantity = list[adapterPosition].quantity!! -1
                        onBind(list[adapterPosition])
                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemFromProductsRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
        holder.onClick(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


}