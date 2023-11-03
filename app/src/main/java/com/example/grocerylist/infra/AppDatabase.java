package com.example.grocerylist.infra;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.grocerylist.Product;
import com.example.grocerylist.ProductRepository;

@Database(entities = {Product.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase;
    public abstract ProductRepository getProductRepository();

    public static AppDatabase getInstance(Context context) {
        if(appDatabase == null) {
            appDatabase = Room.databaseBuilder(
                    context.getApplicationContext(),
                            AppDatabase.class,
                            "driver_database"
                    )
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return appDatabase;
    }
}
