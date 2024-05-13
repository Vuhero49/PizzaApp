package com.example.food_app_firebase.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.food_app_firebase.Adapter.CartAdapter;
import com.example.food_app_firebase.Helper.ChangeNumberItemsListener;
import com.example.food_app_firebase.Helper.ManagmentCart;
import com.example.food_app_firebase.R;
import com.example.food_app_firebase.databinding.ActivityCartBinding;

public class CartActivity extends BaseActivity {
    private ActivityCartBinding binding;
    private RecyclerView adapter;
    private ManagmentCart managmentCart;
    private double tax;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);

        setVariable();
        caculateCart();
        initList();
    }

    private void initList() {
        if(managmentCart.getListCart().isEmpty()){
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scroolViewCart.setVisibility(View.GONE);
        }else{
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scroolViewCart.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        binding.cardView.setLayoutManager(linearLayoutManager);
        CartAdapter adapter = new CartAdapter(managmentCart.getListCart(), this, () -> caculateCart());
        // Set the adapter to the RecyclerView
        binding.cardView.setAdapter(adapter);
    }


    private void caculateCart() {
        double percent = 0.02;
        double delivery = 10;

        tax = Math.round(managmentCart.getTotalFee() * percent * 100.0);

        double total = Math.round((managmentCart.getTotalFee() + tax + delivery) * 100) / 100;
        double itemTotal = Math.round(managmentCart.getTotalFee() * 100) / 100;

        binding.totalFeeText.setText("$"+itemTotal);
        binding.totalTaxTxt.setText("$"+tax);
        binding.deliveryFeeTxt.setText("$"+delivery);
        binding.totalTxt.setText("$" + total);
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(view -> finish());
    }
}