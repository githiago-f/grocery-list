package com.example.grocerylist.fragments;

import android.os.Bundle;

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

public class EditProductFragment extends Fragment {
    public static String ID_KEY = "ID",
            NAME_KEY = "NAME",
            BRAND_KEY = "BRAND",
            QUANTITY_KEY = "QUANTITY",
            PRICE_KEY = "PRICE";

    private final Product product = new Product();
    private ProductRepository productRepository;

    public EditProductFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productRepository = AppDatabase.getInstance(requireContext()).getProductRepository();
        Bundle bundle = getArguments();
        if(bundle != null) {
            product.setId(bundle.getLong(ID_KEY, 0));
            product.setName(bundle.getString(NAME_KEY));
            product.setBrand(bundle.getString(BRAND_KEY));
            product.setQuantity(bundle.getLong(QUANTITY_KEY));
            product.setPrice(bundle.getDouble(PRICE_KEY));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_product, container, false);
        TextInputEditText name      = root.findViewById(R.id.name_input);
        TextInputEditText brand     = root.findViewById(R.id.brand_input);
        TextInputEditText price     = root.findViewById(R.id.price_input);
        TextInputEditText quantity  = root.findViewById(R.id.quantity_input);
        name.setText(product.getName());
        brand.setText(product.getBrand());
        price.setText(String.valueOf(product.getPrice()));
        quantity.setText(String.valueOf(product.getQuantity()));

        root.findViewById(R.id.send).setOnClickListener(view -> {
            Product temp = Product.populateProduct(root);
            assert temp != null;
            temp.setId(product.getId());

            Snackbar.make(root, R.string.saving, Snackbar.LENGTH_LONG).show();
            new SaveProductTask(productRepository, v -> {
                Log.d("EditProduct ->", "Product edited: " + temp);
                Navigation.findNavController(root)
                        .navigate(R.id.action_editProductFragment_to_homeFragment);
            }).execute(temp);
        });
        return root;
    }
}