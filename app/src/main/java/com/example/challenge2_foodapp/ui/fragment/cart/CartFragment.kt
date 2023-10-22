package com.example.challenge2_foodapp.ui.fragment.cart

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge2_foodapp.adapter.CartAdapter
import com.example.challenge2_foodapp.data.entity.CartEntity
import com.example.challenge2_foodapp.data.entity.FoodEntity
import com.example.challenge2_foodapp.data.repository.CartViewModelFactory
import com.example.challenge2_foodapp.databinding.FragmentCartBinding
import com.example.challenge2_foodapp.ui.activity.confirmation.ConfirmationOrderActivity
import com.example.challenge2_foodapp.ui.activity.detail.DetailActivity
import com.example.challenge2_foodapp.utils.toCurrencyFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CartAdapter

    private var totalPrice = 0

    private val cartViewModel: CartViewModel by lazy {
        ViewModelProvider(
            this,
            CartViewModelFactory(requireContext())
        )[CartViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.cartOrderButton.setOnClickListener {
            if (totalPrice > 0) {
                val intent = Intent(requireActivity(), ConfirmationOrderActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Cart is empty, please add some items to cart first",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupRecyclerView() {
        recyclerView = binding.cartRvFood

        cartViewModel.cart.observe(viewLifecycleOwner) { cart ->

            if (cart == null) {
                binding.cartRvFood.visibility = View.GONE
                binding.animationView.visibility = View.VISIBLE
                binding.emptyDataText.visibility = View.VISIBLE
            } else {
                binding.cartRvFood.visibility = View.VISIBLE
                binding.animationView.visibility = View.GONE
                binding.emptyDataText.visibility = View.GONE

                totalPrice =
                    cart.sumOf { (it.foodItem.price * it.foodQuantity) }
                binding.cartPriceTotal.text = totalPrice.toCurrencyFormat()

                recyclerView.setHasFixedSize(true)
                recyclerView.layoutManager = LinearLayoutManager(requireContext())

                adapter = CartAdapter(cart, requireContext())
                recyclerView.adapter = adapter
                adapter.setOnItemClickCallback(object : CartAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: CartEntity) {
                        sendSelectedFood(data.foodItem)
                    }

                    override fun onDeleteClicked(data: CartEntity) {
                        showDeleteConfirmationDialog(data)
                    }

                    override fun onMinusClicked(data: CartEntity) {
                        if (data.foodQuantity > 1) {
                            lifecycleScope.launch {
                                withContext(Dispatchers.IO) {
                                    cartViewModel.decreaseCartItemQuantity(
                                        data.foodQuantity,
                                        data.id
                                    )
                                }
                            }
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Food quantity cannot be less than 1",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onPlusClicked(data: CartEntity) {
                        lifecycleScope.launch {
                            withContext(Dispatchers.IO) {
                                cartViewModel.increaseCartItemQuantity(data.foodQuantity, data.id)
                            }
                        }
                    }

                    override fun onNotesChanged(data: CartEntity, notes: String) {
                        lifecycleScope.launch {
                            withContext(Dispatchers.IO) {
                                cartViewModel.changeNoteForCartItem(data.id, notes)
                            }
                        }
                    }
                })
            }
        }
    }

    private fun showDeleteConfirmationDialog(data: CartEntity) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete ${data.foodItem.name}")
            .setMessage("Do you want to delete ${data.foodItem.name} from cart?")
            .setPositiveButton("Yes") { dialog, _ ->
                dialog.dismiss()
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        deleteCart(data)
                    }
                }
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun deleteCart(data: CartEntity) {
        cartViewModel.deleteCart(data)
    }

    private fun sendSelectedFood(data: FoodEntity) {
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra("food", data)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}