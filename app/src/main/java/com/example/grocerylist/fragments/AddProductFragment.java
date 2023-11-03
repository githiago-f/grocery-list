package com.example.grocerylist.fragments;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.grocerylist.Product;
import com.example.grocerylist.ProductRepository;
import com.example.grocerylist.R;
import com.example.grocerylist.infra.AppDatabase;
import com.example.grocerylist.tasks.SaveProductTask;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Optional;

public class AddProductFragment extends Fragment {
    private ProductRepository productRepository;
    public AddProductFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productRepository = AppDatabase.getInstance(requireContext()).getProductRepository();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_add_product, container, false);
        root.findViewById(R.id.send).setOnClickListener(view -> {
            Product product = Product.populateProduct(root);
            Snackbar.make(root, R.string.saving, Snackbar.LENGTH_LONG).show();
            new SaveProductTask(productRepository, v -> {
                Log.d("AddProduct ->", "Product edited: " + product);
                Navigation.findNavController(root)
                        .navigate(R.id.action_addProductFragment_to_homeFragment);
            }).execute(product);
        });
        return root;
    }
}