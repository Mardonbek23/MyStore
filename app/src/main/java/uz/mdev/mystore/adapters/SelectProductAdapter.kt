package uz.mdev.mystore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mdev.mystore.databinding.ItemSelectBinding
import uz.mdev.mystore.helpers.hide
import uz.mdev.mystore.helpers.show
import uz.mdev.mystore.models.SharingData
import uz.mdev.mystore.models.User

class SelectProductAdapter(
    var list: ArrayList<SharingData>,
    var listener:
    SelectProductAdapter.OnItemClickListener,
) : RecyclerView.Adapter<SelectProductAdapter.Vh>() {

    inner class Vh(var itemRvBinding: ItemSelectBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(sharingData: SharingData) {
            itemRvBinding.title.text = sharingData.name
            itemRvBinding.quantity.text = sharingData.quantity.toString()
        }

        fun onClick(sharingData: SharingData) {
            itemView.setOnClickListener {
                if (sharingData.isSelected) {
                    itemRvBinding.tick.hide()
                    itemRvBinding.llQuantity.hide()
                } else {
                    itemRvBinding.tick.show()
                    itemRvBinding.llQuantity.show()
                }
                list[adapterPosition].isSelected = !sharingData.isSelected
            }
            itemRvBinding.apply {
                btnPlus.setOnClickListener {
                    if (list[adapterPosition].quantity < 999) {
                        list[adapterPosition].quantity = sharingData.quantity + 1
                        onBind(list[adapterPosition])
                    }
                }
                btnMinus.setOnClickListener {
                    if (list[adapterPosition].quantity >1) {
                        list[adapterPosition].quantity = sharingData.quantity -1
                        onBind(list[adapterPosition])
                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemSelectBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
        holder.onClick(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, user: User)
    }

}