package fr.epf.min.barcodescannerlux.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.epf.min.barcodescannerlux.models.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)

abstract class ProductDatabase : RoomDatabase() {

    abstract fun getProductDao(): ProductDao

}