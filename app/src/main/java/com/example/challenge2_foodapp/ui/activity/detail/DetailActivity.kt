package com.example.challenge2_foodapp.ui.activity.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.challenge2_foodapp.R
import com.example.challenge2_foodapp.data.api.Food
import com.example.challenge2_foodapp.data.entity.CartEntity
import com.example.challenge2_foodapp.data.entity.FoodEntity
import com.example.challenge2_foodapp.data.repository.CartViewModelFactory
import com.example.challenge2_foodapp.databinding.ActivityDetailBinding
import com.example.challenge2_foodapp.ui.fragment.cart.CartViewModel
import com.example.challenge2_foodapp.utils.toCurrencyFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var priceTotal = 0
    private var foodQuantity = 1

    private val cartViewModel: CartViewModel by lazy {
        ViewModelProvider(
            this,
            CartViewModelFactory(this)
        )[CartViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val food = intent.getSerializableExtra("food") as Food

        binding.apply {
            val foodName = food.nama
            val foodPrice = food.harga_format
            val foodImage = food.image_url
            val foodDescription = food.detail
            val foodLocation = food.alamat_resto

            tvMakananDetail.text = foodName
            Glide.with(this@DetailActivity)
                .load(foodImage)
                .into(ivMakananDetail)

            tvHargaDetail.text = foodPrice
            tvDesc.text = foodDescription
            lokasiValue.text = foodLocation
            addToCart.text = getString(R.string.tambah_keranjang, foodPrice)
        }

        binding.ibMinus.setOnClickListener {
            if (foodQuantity > 1) {
                foodQuantity--
                priceTotal = foodQuantity * food.harga
                binding.tvJumlah.text = foodQuantity.toString()
                binding.addToCart.text = getString(R.string.tambah_keranjang, priceTotal.toCurrencyFormat())

            } else {
                Toast.makeText(this, "Cannot be less than one", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.ibPlus.setOnClickListener {
            foodQuantity++
            priceTotal = foodQuantity * food.harga
            binding.tvJumlah.text = foodQuantity.toString()
            binding.addToCart.text = getString(R.string.tambah_keranjang, priceTotal.toCurrencyFormat())
        }

        binding.addToCart.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    cartViewModel.insertIntoCart(
                        CartEntity(
                            foodItem = FoodEntity(
                                name = food.nama,
                                price = food.harga,
                                priceFormat = food.harga_format,
                                description = food.detail,
                                location = food.alamat_resto,
                                image = food.image_url
                            ),
                            foodQuantity = foodQuantity,
                            foodNote = null
                        )
                    )
                }
                Toast.makeText(
                    this@DetailActivity,
                    "Successfully added your food to cart",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }
}