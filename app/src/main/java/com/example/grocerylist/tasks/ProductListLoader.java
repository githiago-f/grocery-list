package com.example.grocerylist.tasks;

import android.os.AsyncTask;

import com.example.grocerylist.Product;
import com.example.grocerylist.ProductRepository;

import java.util.List;
import java.util.function.Consumer;

public class ProductListLoader extends AsyncTask<Void, Void, List<Product>> {
    private final ProductRepository productRepository;
    private final Consumer<List<Product>> onLoad;
    public ProductListLoader(ProductRepository productRepository, Consumer<List<Product>> onLoad) {
        this.productRepository = productRepository;
        this.onLoad = onLoad;
    }

    @Override
    protected List<Product> doInBackground(Void... voids) {
        return productRepository.findAll();
    }

    @Override
    protected void onPostExecute(List<Product> products) {
        super.onPostExecute(products);
        onLoad.accept(products);
    }
}
