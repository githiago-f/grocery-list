package com.example.grocerylist;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductRepository {
    @Query("select * from Product")
    List<Product> findAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product product);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Product product);

    default void save(Product product) {
        if(product.getId() != null) this.update(product);
        else this.insert(product);
    };

    @Delete
    void delete(Product product);
}
