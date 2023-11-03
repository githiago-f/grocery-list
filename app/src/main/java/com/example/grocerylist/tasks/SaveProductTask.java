package com.example.grocerylist.tasks;

import android.os.AsyncTask;

import com.example.grocerylist.Product;
import com.example.grocerylist.ProductRepository;

import java.util.Arrays;
import java.util.function.Consumer;

public class SaveProductTask extends AsyncTask<Product, Void, Void> {
    private final ProductRepository productRepository;
    private final Consumer<Void> afterCreate;

    public SaveProductTask(ProductRepository productRepository, Consumer<Void> afterCreate) {
        this.productRepository = productRepository;
        this.afterCreate = afterCreate;
    }

    @Override
    protected Void doInBackground(Product... products) {
        Arrays.stream(products).forEach(productRepository::save);
        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        super.onPostExecute(voids);
        afterCreate.accept(voids);
    }
}
