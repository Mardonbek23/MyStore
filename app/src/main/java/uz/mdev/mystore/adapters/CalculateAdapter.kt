package uz.mdev.mystore.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mdev.mystore.databinding.ItemRvCalculateBinding
import uz.mdev.mystore.db.entities.product.Product
import uz.mdev.mystore.helpers.deFormatCurrencyWithoutSymbols
import uz.mdev.mystore.helpers.intTo4digits
import uz.mdev.mystore.helpers.setFloatToCurrencyFormat

class CalculateAdapter(
    var list: ArrayList<Product>,
    var listener:
    CalculateAdapter.OnItemClickListener,
    var total_tax_price: Float,
    var total_bought_price: Float,
    var calc_type:Int
) : RecyclerView.Adapter<CalculateAdapter.Vh>() {

    inner class Vh(var itemRvBinding: ItemRvCalculateBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(product: Product) {
            itemRvBinding.apply {

                tvTotalPrice.setText(setFloatToCurrencyFormat(product.total_price))
                tvTaxPrice.text = setFloatToCurrencyFormat(product.tax_price)
                tvTotalTaxPrice.text =
                    setFloatToCurrencyFormat(product.tax_price * product.quantity)
                tvMinPrice.text = setFloatToCurrencyFormat(product.min_price)
                tvOldPrice.text = setFloatToCurrencyFormat(product.old_total_price)
                tvTotalPrice.setText(setFloatToCurrencyFormat(product.total_price))
                tvBenefit.text = setFloatToCurrencyFormat(product.benefit)
                if (calc_type==0){
                    tvTotalPrice.setText("*** ***")
                    tvTaxPrice.text = "*** ***"
                    tvTotalTaxPrice.text =
                        "*** ***"
                    tvMinPrice.text = "*** ***"
                    tvOldPrice.text = "*** ***"
                    tvTotalPrice.setText("*** ***")
                    tvBenefit.text = "*** ***"
                }
                id.text = intTo4digits(product.id)
                name.text = product.name
                tvBoughtPrice.setText(setFloatToCurrencyFormat(product.price_bought))
                quantity.setText(product.quantity.toString())

            }
        }

        fun calculate_prices(product: Product, click_position: Int) {
            if (total_bought_price != 0f) {

                //tax price
                list[click_position].tax_price =
                    product.price_bought * total_tax_price / (total_bought_price)

                //min price
                list[click_position].min_price =
                    list[click_position].tax_price + product.price_bought * (1 + product.min_percent.toFloat() / 100)

                //price
                list[click_position].total_price =
                    list[click_position].tax_price.plus(product.price_bought * (1 + product.interest_percent.toFloat() / 100).toFloat())

                //gift price
                list[click_position].gift_price =
                    list[click_position].tax_price + product.price_bought * (1 + (product.interest_percent - product.gift_percent).toFloat() / 100).toFloat()

                //benefit
                list[click_position].benefit =
                    (list[click_position].total_price - list[click_position].price_bought - list[click_position].tax_price).toFloat()
            }


        }

        fun onItemsClick(product: Product, click_position: Int) {
            itemRvBinding.apply {
                plusBtn.setOnClickListener {
                    if (product.quantity != 1000) {
                        list[click_position].quantity = product.quantity + 1
                        calculate_prices(list[click_position], click_position)
                        onBind(list[click_position])

                        listener.onPlusButtonClick(click_position, list[click_position])
                    }
                }
                minusBtn.setOnClickListener {
                    if (product.quantity != 0) {
                        list[click_position].quantity = product.quantity - 1
                        calculate_prices(list[click_position], click_position)
                        onBind(list[click_position])

                        listener.onPlusButtonClick(click_position, list[click_position])
                    }
                }
                removeItem.setOnClickListener {
                    listener.onRemoveClick(click_position, product)
                }
                tvBoughtPrice.setOnEditorActionListener { v, actionId, event ->
                    if (tvBoughtPrice.text.isNotEmpty()) {
                        list[click_position].price_bought =
                            deFormatCurrencyWithoutSymbols(tvBoughtPrice.text.toString())
                    } else {
                        list[click_position].price_bought = 0f
                    }
                    calculate_prices(list[click_position], click_position)
                    onBind(list[click_position])
                    notifyDataSetChanged()
                    return@setOnEditorActionListener false
                }
                quantity.setOnEditorActionListener { v, actionId, event ->
                    if (quantity.text.isNotEmpty()) {
                        list[click_position].quantity =
                            quantity.text.toString().toInt()
                    } else {
                        list[click_position].quantity = 0
                    }
                    calculate_prices(list[click_position], click_position)
                    onBind(list[click_position])
                    notifyDataSetChanged()
                    return@setOnEditorActionListener false
                }


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemRvCalculateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.calculate_prices(list[position], position)
        holder.onBind(list[position])
        holder.onItemsClick(list[position], position)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, product: Product)
        fun onEditClick(position: Int, product: Product)
        fun onRemoveClick(position: Int, product: Product)
        fun onPlusButtonClick(position: Int, product: Product)
        fun onMinusButtonClick(position: Int, product: Product)
    }

}
