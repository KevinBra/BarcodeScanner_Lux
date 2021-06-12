package fr.epf.min.barcodescannerlux

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import fr.epf.min.barcodescannerlux.API.ProductApi
import fr.epf.min.barcodescannerlux.data.ProductDao
import fr.epf.min.barcodescannerlux.data.ProductDatabase
import fr.epf.min.barcodescannerlux.models.Product
import fr.epf.min.barcodescannerlux.utils.data
import kotlinx.android.synthetic.main.list_product_layout.*
import kotlinx.coroutines.runBlocking
import kotlin.collections.toMutableList

class ListProductActivity : AppCompatActivity() {

    private lateinit var scannedProduct: Product
    private var myMenu: Menu? = null
    lateinit var adapter: ProductAdapter
    lateinit var products: List<Product>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_product_layout)

        list_products_recyclerview.layoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
        val database = Room.databaseBuilder(
            this, ProductDatabase::class.java, "product-db"
        ).build()

        val productDao = database.getProductDao()

        runBlocking {
            products = productDao.getAllProducts().toMutableList()
            adapter = ProductAdapter(products)
            list_products_recyclerview.adapter = adapter
        }
        this.title = "Product list"
    }

}