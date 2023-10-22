package com.example.challenge2_foodapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge2_foodapp.R
import com.example.challenge2_foodapp.data.entity.CartEntity
import com.example.challenge2_foodapp.utils.toCurrencyFormat
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class CartAdapter(private val cartList: List<CartEntity>, private val context: Context) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: CartEntity)
        fun onDeleteClicked(data: CartEntity)
        fun onMinusClicked(data: CartEntity)
        fun onPlusClicked(data: CartEntity)
        fun onNotesChanged(data: CartEntity, notes: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cart_food_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cart = cartList[position]

        holder.cartFoodName.text = cart.foodItem.name
        holder.cartFoodPrice.text = cart.foodItem.price.toDouble().toCurrencyFormat()
        holder.cartFoodQuantity.text = cart.foodQuantity.toString()
        holder.cartFoodNote.setText(cart.foodNote)
        Glide.with(context).load(cart.foodItem.image).into(holder.foodImage)

        holder.deleteButton.setOnClickListener {
            onItemClickCallback.onDeleteClicked(cartList[holder.adapterPosition])
        }

        holder.minusButton.setOnClickListener {
            onItemClickCallback.onMinusClicked(cartList[holder.adapterPosition])
            holder.cartFoodPrice.text =
                (cart.foodQuantity * cart.foodItem.price.toDouble()).toCurrencyFormat()
        }

        holder.plusButton.setOnClickListener {
            onItemClickCallback.onPlusClicked(cartList[holder.adapterPosition])
            holder.cartFoodPrice.text =
                (cart.foodQuantity * cart.foodItem.price.toDouble()).toCurrencyFormat()
        }

        holder.noteField.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                onItemClickCallback.onNotesChanged(
                    cartList[holder.adapterPosition],
                    holder.noteField.text.toString()
                )
            }
        }
    }

    override fun getItemCount(): Int = cartList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cartFoodName: MaterialTextView = itemView.findViewById(R.id.cart_food_title)
        val cartFoodPrice: MaterialTextView = itemView.findViewById(R.id.cart_food_price)
        val cartFoodQuantity: MaterialTextView =
            itemView.findViewById(R.id.tv_total_item)
        val cartFoodNote: TextInputEditText = itemView.findViewById(R.id.et_notes_item)
        val foodImage: ShapeableImageView = itemView.findViewById(R.id.iv_cart_food_image)

        val deleteButton: ImageButton = itemView.findViewById(R.id.cart_delete_button)
        val minusButton: ImageButton = itemView.findViewById(R.id.ib_minus)
        val plusButton: ImageButton = itemView.findViewById(R.id.ib_plus)
        val noteField: TextInputEditText = itemView.findViewById(R.id.et_notes_item)

    }

}