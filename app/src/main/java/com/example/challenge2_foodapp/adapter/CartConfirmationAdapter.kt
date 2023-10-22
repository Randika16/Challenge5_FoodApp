package com.example.challenge2_foodapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge2_foodapp.R
import com.example.challenge2_foodapp.data.entity.CartEntity
import com.example.challenge2_foodapp.utils.toCurrencyFormat
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class CartConfirmationAdapter(
    private val cartList: List<CartEntity>,
    private val context: Context
) :
    RecyclerView.Adapter<CartConfirmationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.cart_confirmation_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cart = cartList[position]

        holder.cartFoodName.text = cart.foodItem.name
        holder.cartFoodPrice.text = cart.foodItem.price.toDouble().toCurrencyFormat()
        holder.cartFoodQuantity.text = cart.foodQuantity.toString()
        holder.cartFoodNote.text = cart.foodNote
        Glide.with(context).load(cart.foodItem.image).into(holder.foodImage)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cartFoodName: MaterialTextView = itemView.findViewById(R.id.cart_food_title)
        val cartFoodPrice: MaterialTextView = itemView.findViewById(R.id.cart_food_price)
        val cartFoodQuantity: MaterialTextView =
            itemView.findViewById(R.id.cart_food_quantity_value)
        val cartFoodNote: MaterialTextView = itemView.findViewById(R.id.cart_notes_value)
        val foodImage: ShapeableImageView = itemView.findViewById(R.id.iv_cart_food_image)

    }

}