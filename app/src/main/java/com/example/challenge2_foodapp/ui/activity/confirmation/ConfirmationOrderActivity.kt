package com.example.challenge2_foodapp.ui.activity.confirmation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge2_foodapp.adapter.CartConfirmationAdapter
import com.example.challenge2_foodapp.data.api.Order
import com.example.challenge2_foodapp.data.api.RequestOrder
import com.example.challenge2_foodapp.data.repository.CartViewModelFactory
import com.example.challenge2_foodapp.databinding.ActivityConfirmationOrderBinding
import com.example.challenge2_foodapp.ui.activity.MainActivity
import com.example.challenge2_foodapp.utils.toCurrencyFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConfirmationOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmationOrderBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CartConfirmationAdapter
    private var totalPrice = 0
    private var cartList = listOf<Order>()

    private val confirmationOrderViewModel: ConfirmationOrderViewModel by lazy {
        ViewModelProvider(
            this,
            CartViewModelFactory(this)
        )[ConfirmationOrderViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = binding.cartRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        onClick()

        confirmationOrderViewModel.message.observe(this) {
            showToast(it)
        }

        confirmationOrderViewModel.cart.observe(this) { it ->
            totalPrice =
                it.sumOf { (it.foodItem.price * it.foodQuantity) }
            binding.totalPayment.text = totalPrice.toCurrencyFormat()
            cartList = it.map { cartEntity ->
                Order(
                    cartEntity.foodItem.name,
                    cartEntity.foodQuantity,
                    cartEntity.foodNote,
                    cartEntity.foodItem.price,
                )
            }

            adapter = CartConfirmationAdapter(it, this)
            recyclerView.adapter = adapter
        }

    }

    private fun onClick() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        binding.orderButton.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    onOrder()
                }
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(
            this@ConfirmationOrderActivity,
            msg,
            Toast.LENGTH_SHORT
        ).show()

        if (msg == "Pesanan Berhasil") {
            val intent = Intent(this@ConfirmationOrderActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("order", true) // Kirim informasi bahwa order berhasil
            startActivity(intent)
        }
    }

    private fun onOrder() {
        val sharedPreferences = getSharedPreferences("myAccount", 0)
        val username = sharedPreferences.getString("name", "")
        val totalPrice = totalPrice.toString()

        confirmationOrderViewModel.addCartToServer(
            RequestOrder(
                username.toString(),
                totalPrice.toInt(),
                cartList
            )
        )
    }
}