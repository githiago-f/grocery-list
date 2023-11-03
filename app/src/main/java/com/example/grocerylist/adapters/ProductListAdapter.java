package com.example.grocerylist.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerylist.Product;
import com.example.grocerylist.ProductRepository;
import com.example.grocerylist.R;
import com.example.grocerylist.fragments.EditProductFragment;
import com.example.grocerylist.tasks.DeleteProductTask;

import java.util.List;
import java.util.Optional;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {
    private final List<Product> products;
    private Optional<Context> context;
    private final ProductRepository productRepository;

    public ProductListAdapter(List<Product> products, ProductRepository productRepository) {
        this.products = products;
        context = Optional.empty();
        this.productRepository = productRepository;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = Optional.of(parent.getContext());
        View card = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_card, parent, false);
        return new ProductViewHolder(card);
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.name.setText(product.getName());
        holder.brand.setText(product.getBrand());
        holder.price.setText("R$ " + product.priceAsString());
        holder.quantity.setText("Qtd." + product.getQuantity().toString());
        Bundle bundle = new Bundle();
        bundle.putLong(EditProductFragment.ID_KEY, product.getId());
        bundle.putString(EditProductFragment.NAME_KEY, product.getName());
        bundle.putString(EditProductFragment.BRAND_KEY, product.getBrand());
        bundle.putDouble(EditProductFragment.PRICE_KEY, product.getPrice());
        bundle.putLong(EditProductFragment.QUANTITY_KEY, product.getQuantity());
        View.OnClickListener gotoEdit = Navigation.createNavigateOnClickListener(
                R.id.action_homeFragment_to_editProductFragment, bundle);
        holder.editButton.setOnClickListener(gotoEdit);
        holder.deleteButton.setOnClickListener(view -> {
            if(context.isPresent()) {
                new AlertDialog.Builder(context.get())
                        .setTitle(R.string.delete_dialog_title)
                        .setMessage(R.string.confirm_delete)
                        .setPositiveButton("Sim", (dialog, which) -> removeItem(position))
                        .setNegativeButton("Não", (dialog, which) -> {})
                        .show();
            } else {
                removeItem(position);
            }
        });
    }

    public void removeItem(int position) {
        Product product = products.get(position);
        new DeleteProductTask(productRepository, unused -> {
            products.remove(product);
            notifyItemRemoved(position);
        }).execute(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    protected static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView name, brand, price, quantity;
        ImageButton deleteButton, editButton;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            brand = itemView.findViewById(R.id.product_brand);
            price = itemView.findViewById(R.id.product_price);
            quantity = itemView.findViewById(R.id.product_quantity);
            deleteButton = itemView.findViewById(R.id.delete_btn);
            editButton = itemView.findViewById(R.id.edit_btn);
        }
    }
}
