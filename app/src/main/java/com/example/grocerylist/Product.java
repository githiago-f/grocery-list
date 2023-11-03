package com.example.grocerylist;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Optional;

@Entity
public class Product {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String name, brand;
    private Long quantity;
    private Double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public String priceAsString() {
        return getPrice().toString().replace('.', ',');
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    private static String getEditable(View view, @IdRes int r) {
        TextInputEditText input = view.findViewById(r);
        return Optional.ofNullable(input.getText())
                .map(Object::toString)
                .orElse("");
    }

    /** @noinspection Since15*/
    public static Product populateProduct(View view) {
        String name      = getEditable(view, R.id.name_input);
        String brand     = getEditable(view, R.id.brand_input);
        String price     = getEditable(view, R.id.price_input);
        String quantity  = getEditable(view, R.id.quantity_input);

        if(name.isEmpty() || name.isBlank()) {
            Snackbar.make(view, R.string.name_input_empty, Snackbar.LENGTH_LONG).show();
            return null;
        } else if (brand.isEmpty() || brand.isBlank()) {
            Snackbar.make(view, R.string.name_input_empty, Snackbar.LENGTH_LONG).show();
            return null;
        } else if (price.isEmpty() || price.isBlank()) {
            Snackbar.make(view, R.string.name_input_empty, Snackbar.LENGTH_LONG).show();
            return null;
        } else if (quantity.isEmpty() || quantity.isBlank()) {
            Snackbar.make(view, R.string.name_input_empty, Snackbar.LENGTH_LONG).show();
            return null;
        }

        Product product = new Product();
        product.setName(name);
        product.setBrand(brand);
        product.setPrice(Double.parseDouble(price.replace(',', '.')));
        product.setQuantity(Long.parseLong(quantity));
        return product;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
