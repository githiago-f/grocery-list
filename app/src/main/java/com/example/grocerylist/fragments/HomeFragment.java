package com.example.grocerylist.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.grocerylist.Product;
import com.example.grocerylist.ProductRepository;
import com.example.grocerylist.R;
import com.example.grocerylist.adapters.ProductListAdapter;
import com.example.grocerylist.infra.AppDatabase;
import com.example.grocerylist.tasks.ProductListLoader;

import java.util.List;
import java.util.function.Consumer;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProductRepository productRepository;
    public HomeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productRepository = AppDatabase.getInstance(requireContext()).getProductRepository();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.products_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        Consumer<List<Product>> onLoadProductsList = products ->
                recyclerView.setAdapter(new ProductListAdapter(products, productRepository));
        new ProductListLoader(productRepository, onLoadProductsList).execute();
        int action = R.id.action_homeFragment_to_addProductFragment;
        root.findViewById(R.id.fab_add_product)
                .setOnClickListener(Navigation.createNavigateOnClickListener(action));
        return root;
    }
}