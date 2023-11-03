package com.example.grocerylist.tasks;

import android.os.AsyncTask;

import com.example.grocerylist.Product;
import com.example.grocerylist.ProductRepository;

import java.util.Arrays;
import java.util.function.Consumer;

public class DeleteProductTask extends AsyncTask<Product, Void, Void> {
    private final ProductRepository productRepository;
    private final Consumer<Void> afterDelete;

    public DeleteProductTask(ProductRepository productRepository, Consumer<Void> afterDelete) {
        this.productRepository = productRepository;
        this.afterDelete = afterDelete;
    }

    @Override
    protected Void doInBackground(Product... products) {
        Arrays.stream(products).forEach(productRepository::delete);
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        afterDelete.accept(null);
    }
}
