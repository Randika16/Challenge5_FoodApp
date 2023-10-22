package com.example.challenge2_foodapp.data

import com.example.challenge2_foodapp.data.entity.CartEntity
import com.example.challenge2_foodapp.data.room.CartDao
import kotlinx.coroutines.flow.Flow

interface CartDataSource {
    fun insertIntoCart(cart: CartEntity)
    fun deleteFromCart(cart: CartEntity)
    fun deleteCartByID(cartId: Int)
    fun decreaseCartItemQuantity(cartFoodQuantity: Int, cartId: Int)
    fun increaseCartItemQuantity(cartFoodQuantity: Int, cartId: Int)
    fun changeNoteForCartItem(cartId: Int, note: String)
    fun updateCart(cart: CartEntity)
    fun getAllCartItem(): Flow<List<CartEntity>>
}

class CartDatabaseDataSource(private val cartDao: CartDao) : CartDataSource {
    override fun insertIntoCart(cart: CartEntity) {
        cartDao.insertIntoCart(cart)
    }

    override fun deleteFromCart(cart: CartEntity) {
        cartDao.deleteFromCart(cart)
    }

    override fun deleteCartByID(cartId: Int) {
        cartDao.deleteCartByID(cartId)
    }

    override fun decreaseCartItemQuantity(cartFoodQuantity: Int, cartId: Int) {
        cartDao.decreaseCartItemQuantity(cartFoodQuantity, cartId)
    }

    override fun increaseCartItemQuantity(cartFoodQuantity: Int, cartId: Int) {
        cartDao.increaseCartItemQuantity(cartFoodQuantity, cartId)
    }

    override fun changeNoteForCartItem(cartId: Int, note: String) {
        cartDao.changeNoteForCartItem(cartId, note)
    }

    override fun updateCart(cart: CartEntity) {
        cartDao.updateCart(cart)
    }

    override fun getAllCartItem(): Flow<List<CartEntity>> {
        return cartDao.getAllCartItem()
    }
}