package com.example.challenge2_foodapp.data.repository

import com.example.challenge2_foodapp.data.CartDataSource
import com.example.challenge2_foodapp.data.entity.CartEntity
import kotlinx.coroutines.flow.Flow

class CartRepository(
    private val dataSource: CartDataSource
) {

    fun insertIntoCart(cart: CartEntity) {
        dataSource.insertIntoCart(cart)
    }

    fun deleteFromCart(cart: CartEntity) {
        dataSource.deleteFromCart(cart)
    }

    fun deleteCartByID(cartId: Int) {
        dataSource.deleteCartByID(cartId)
    }

    fun decreaseCartItemQuantity(cartFoodQuantity: Int, cartId: Int) {
        dataSource.decreaseCartItemQuantity(cartFoodQuantity, cartId)
    }

    fun increaseCartItemQuantity(cartFoodQuantity: Int, cartId: Int) {
        dataSource.increaseCartItemQuantity(cartFoodQuantity, cartId)
    }

    fun changeNoteForCartItem(cartId: Int, note: String) {
        dataSource.changeNoteForCartItem(cartId, note)
    }

    fun updateCart(cart: CartEntity) {
        dataSource.updateCart(cart)
    }

    fun getAllCartItem(): Flow<List<CartEntity>> {
        return dataSource.getAllCartItem()
    }

}