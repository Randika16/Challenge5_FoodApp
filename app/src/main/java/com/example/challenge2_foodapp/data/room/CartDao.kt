package com.example.challenge2_foodapp.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.challenge2_foodapp.data.entity.CartEntity
import com.example.challenge2_foodapp.data.entity.FoodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIntoCart(cart: CartEntity)

    @Delete
    fun deleteFromCart(cart: CartEntity)

    @Query("DELETE FROM cart_entity WHERE cart_id = :cartId")
    fun deleteCartByID(cartId: Int)

    @Query("UPDATE cart_entity SET cart_food_quantity = :cartFoodQuantity - 1 WHERE cart_id = :cartId")
    fun decreaseCartItemQuantity(cartFoodQuantity: Int, cartId: Int)

    @Query("UPDATE cart_entity SET cart_food_quantity = :cartFoodQuantity + 1 WHERE cart_id = :cartId")
    fun increaseCartItemQuantity(cartFoodQuantity: Int, cartId: Int)

    @Query("UPDATE cart_entity SET cart_food_note = :note WHERE cart_id = :cartId")
    fun changeNoteForCartItem(cartId: Int, note: String)

    @Update
    fun updateCart(cart: CartEntity) : Int

    @Query("SELECT * FROM cart_entity")
    fun getAllCartItem(): Flow<List<CartEntity>>

    @Query("SELECT * FROM cart_entity WHERE cart_id = :cartId")
    fun getSpecificCartItem(cartId: Int): Flow<CartEntity>
}