package fr.epf.min.barcodescannerlux

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import fr.epf.min.barcodescannerlux.API.ProductApi
import fr.epf.min.barcodescannerlux.models.Product
import fr.epf.min.barcodescannerlux.utils.EcoscoreValueAssignement
import fr.epf.min.barcodescannerlux.utils.NutriscoreValueAssignement
import kotlinx.android.synthetic.main.product_layout.*
import kotlinx.coroutines.runBlocking

class ProductActivity : AppCompatActivity() {
    private lateinit var scannedProduct: Product
    private var myMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_layout)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val barcode = intent.getStringExtra("code")

        runBlocking {
            if (barcode != null) {
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
                    ingredient_textview.text = ingredient.toString()
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
}