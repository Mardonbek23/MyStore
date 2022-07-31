package uz.mdev.mystore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mdev.mystore.databinding.ItemRvCalculateBinding
import uz.mdev.mystore.db.entities.product.Product
import uz.mdev.mystore.helpers.intTo4digits
import uz.mdev.mystore.helpers.setFloatToCurrencyFormat

class CalculateAdapter(
    var list: ArrayList<Product>,
    var listener:
    CalculateAdapter.OnItemClickListener,
    var total_tax_price: Float,
    var total_bought_price: Float
) : RecyclerView.Adapter<CalculateAdapter.Vh>() {

    inner class Vh(var itemRvBinding: ItemRvCalculateBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(product: Product) {
            itemRvBinding.apply {
                id.text = intTo4digits(product.id)
                name.text = product.name
                tvTotalPrice.setText(setFloatToCurrencyFormat(product.total_price))
                tvBoughtPrice.setText(setFloatToCurrencyFormat(product.price_bought))
                tvTaxPrice.text = setFloatToCurrencyFormat(product.tax_price)
                tvTotalTaxPrice.text =
                    setFloatToCurrencyFormat(product.tax_price * product.quantity)
                tvMinPrice.text = setFloatToCurrencyFormat(product.min_price)
                tvOldPrice.text = setFloatToCurrencyFormat(product.old_total_price)
                tvTotalPrice.setText(setFloatToCurrencyFormat(product.total_price))
                quantity.setText(product.quantity.toString())
                tvBenefit.text = setFloatToCurrencyFormat(product.benefit)

                //total tax price
                list[adapterPosition].total_tax_price = product.tax_price * product.quantity
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
                    (list[click_position].total_price - list[click_position].price_bought - list[click_position].tax_price).toFloat() * product.quantity.toFloat()
            }


        }

        fun onItemsClick(product: Product, click_position: Int) {
            itemRvBinding.apply {
                plusBtn.setOnClickListener {
                    if (product.quantity != 1000) {
                        list[click_position].quantity = product.quantity + 1
                        calculate_prices(product, click_position)
                        onBind(list[click_position])

                        listener.onPlusButtonClick(click_position, list[click_position])
                    }
                }
                minusBtn.setOnClickListener {
                    if (product.quantity != 0) {
                        list[click_position].quantity = product.quantity - 1
                        calculate_prices(product, click_position)
                        onBind(list[click_position])

                        listener.onPlusButtonClick(click_position, list[click_position])
                    }
                }
                removeItem.setOnClickListener {
                    listener.onRemoveClick(click_position,product)
                }
                tvBoughtPrice.setOnEditorActionListener { v, actionId, event ->
                    if (tvBoughtPrice.text.isNotEmpty()) {
                        list[click_position].price_bought =
                            tvBoughtPrice.text.toString().toFloat()
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
//                tvBoughtPrice.addTextChangedListener(object : TextWatcher {
//                    override fun beforeTextChanged(
//                        s: CharSequence?,
//                        start: Int,
//                        count: Int,
//                        after: Int
//                    ) {
//
//                    }
//
//                    override fun onTextChanged(
//                        s: CharSequence?,
//                        start: Int,
//                        before: Int,
//                        count: Int
//                    ) {
//                    }
//
//                    override fun afterTextChanged(s: Editable?) {
//                        if (tvBoughtPrice.text.isNotEmpty()) {
//                            list[click_position].price_bought =
//                                tvBoughtPrice.text.toString().toFloat()
//                        } else {
//                            list[click_position].price_bought = 0f
//                        }
//                        calculate_prices(product, click_position)
//                        onBind(list[click_position])
//
//                    }
//                })


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
