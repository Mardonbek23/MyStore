package uz.mdev.mystore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mdev.mystore.databinding.ItemRvTableBinding
import uz.mdev.mystore.databinding.ItemUserBinding
import uz.mdev.mystore.db.entities.product.Product
import uz.mdev.mystore.helpers.intTo4digits
import uz.mdev.mystore.helpers.setFloatToCurrencyFormat
import uz.mdev.mystore.helpers.setIntToStr
import uz.mdev.mystore.helpers.setPercentForm
import uz.mdev.mystore.models.Message
import uz.mdev.mystore.models.User

class UserAdapter(
    var list: ArrayList<User>,
    var listener:
    UserAdapter.OnItemClickListener,
) : RecyclerView.Adapter<UserAdapter.Vh>() {

    inner class Vh(var itemRvBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(user: User) {
            itemRvBinding.apply {
                if (user.image != null) {

                }
                name.text = user.name
            }
        }
        fun onClick(user: User){
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition,user)
            }
            itemRvBinding.phone.setOnClickListener {
                listener.onPhoneClick(adapterPosition,user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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
        fun onPhoneClick(position: Int, user: User)
    }

}
