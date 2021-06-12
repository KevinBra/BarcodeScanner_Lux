package fr.epf.min.barcodescannerlux.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import fr.epf.min.barcodescannerlux.models.Product


@Dao
interface ProductDao {

    @Query("select * from products")
    suspend fun getAllProducts() : List<Product>

    @Insert
    suspend fun addProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("SELECT EXISTS(SELECT * FROM products WHERE _id = :code)")
    suspend fun isRowIsExist(code : String) : Boolean
}