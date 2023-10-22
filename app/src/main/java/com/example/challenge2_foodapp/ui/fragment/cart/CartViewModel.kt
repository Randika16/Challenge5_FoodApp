package com.example.challenge2_foodapp.ui.fragment.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.challenge2_foodapp.data.entity.CartEntity
import com.example.challenge2_foodapp.data.entity.FoodEntity
import com.example.challenge2_foodapp.data.repository.CartRepository
import kotlinx.coroutines.flow.map

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {

    val cart: LiveData<List<CartEntity>>
        get() = cartRepository.getAllCartItem().map {
            it.map { cartEntity ->
                CartEntity(
                    cartEntity.id,
                    FoodEntity(
                        cartEntity.foodItem.name,
                        cartEntity.foodItem.price,
                        cartEntity.foodItem.priceFormat,
                        cartEntity.foodItem.description,
                        cartEntity.foodItem.location,
                        cartEntity.foodItem.image
                    ),
                    cartEntity.foodQuantity,
                    cartEntity.foodNote
                )
            }
        }.asLiveData()

    fun insertIntoCart(cartEntity: CartEntity) = cartRepository.insertIntoCart(cartEntity)

    fun deleteCartById(cartId: Int) = cartRepository.deleteCartByID(cartId)

    fun deleteCart(cartEntity: CartEntity) = cartRepository.deleteFromCart(cartEntity)

    fun decreaseCartItemQuantity(cartFoodQuantity: Int, cartId: Int) = cartRepository.decreaseCartItemQuantity(cartFoodQuantity, cartId)

    fun increaseCartItemQuantity(cartFoodQuantity: Int, cartId: Int) = cartRepository.increaseCartItemQuantity(cartFoodQuantity, cartId)

    fun changeNoteForCartItem(cartId: Int, note: String) = cartRepository.changeNoteForCartItem(cartId, note)

    fun updateCart(cartEntity: CartEntity) = cartRepository.updateCart(cartEntity)
}