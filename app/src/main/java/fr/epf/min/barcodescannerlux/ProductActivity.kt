package fr.epf.min.barcodescannerlux

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.bumptech.glide.Glide
import fr.epf.min.barcodescannerlux.API.ProductApi
import fr.epf.min.barcodescannerlux.data.ProductDatabase
import fr.epf.min.barcodescannerlux.models.Product
import fr.epf.min.barcodescannerlux.utils.EcoscoreValueAssignement
import fr.epf.min.barcodescannerlux.utils.NutriscoreValueAssignement
import kotlinx.android.synthetic.main.product_layout.*
import kotlinx.coroutines.runBlocking


class ProductActivity : AppCompatActivity() {
    private lateinit var scannedProduct: Product
    private val menu: Menu? = null
    lateinit var products: List<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_layout)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val barcode = intent.getStringExtra("code")

        runBlocking {
            if (barcode!! != "") {
                scannedProduct = ProductApi.api.getProductByCode(barcode).product
                val name = scannedProduct.generic_name
                val ingredient = scannedProduct.ingredients_text
                val image = scannedProduct.image_url

                if (name != "") {
                    name_textview.text = name.toString()
                } else {
                    name_textview.text = "Missing name"
                }

                if (ingredient != "") {
                    ingredient_textview.text = "Ingredients: $ingredient"
                } else {
                    ingredient_textview.text = "Missing ingredient"
                }

                if (image != "") {
                    Glide.with(this@ProductActivity)
                        .load(scannedProduct.image_url)
                        .into(product_imageview)
                } else {
                    product_imageview.setImageResource(R.drawable.missing_pic)
                }

                SelectNutriscore(scannedProduct.nutriscore_grade)
                SelectEcoscore(scannedProduct.ecoscore_grade)
            }
        }

        this.title = "Product details"
    }

    private fun SelectNutriscore(nutriscore: String) {
        val scoreInfo = NutriscoreValueAssignement(nutriscore)
        nutriscore_textview.text = scoreInfo[0]
        nutriscore_textview.setTextColor(Color.parseColor(scoreInfo[1]))
    }

    private fun SelectEcoscore(ecoscore: String) {
        val scoreInfo = EcoscoreValueAssignement(ecoscore)
        ecoscore_textview.text = scoreInfo[0]
        ecoscore_textview.setTextColor(Color.parseColor(scoreInfo[1]))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_menu, menu)
        val database = Room.databaseBuilder(
            this, ProductDatabase::class.java, "product-db"
        ).build()
        val productDao = database.getProductDao()
        if (runBlocking { productDao.isRowIsExist(scannedProduct._id) }) {
            menu?.findItem(R.id.add_favorite_product)?.setIcon(R.drawable.fav_full)
        } else {
            menu?.findItem(R.id.add_favorite_product)?.setIcon(R.drawable.fav_border)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val database = Room.databaseBuilder(
            this, ProductDatabase::class.java, "product-db"
        ).build()
        val productDao = database.getProductDao()
        when (item.itemId) {
            R.id.add_favorite_product -> {
                if (runBlocking { productDao.isRowIsExist(scannedProduct._id) }) {
                    runBlocking {
                        productDao.deleteProduct(scannedProduct)
                        item.setIcon(R.drawable.fav_border)
                    }
                } else {
                    runBlocking {
                        productDao.addProduct(scannedProduct)
                        item.setIcon(R.drawable.fav_full)
                    }
                }
            }
            R.id.product_access_list -> {
                val intent = Intent(this, ListProductActivity::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }
}