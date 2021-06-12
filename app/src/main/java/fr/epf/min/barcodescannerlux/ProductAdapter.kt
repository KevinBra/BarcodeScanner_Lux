package fr.epf.min.barcodescannerlux

import android.content.Intent
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.epf.min.barcodescannerlux.models.Product
import fr.epf.min.barcodescannerlux.utils.NutriscoreValueAssignement
import kotlinx.android.synthetic.main.product_view_layout.view.*

class ProductAdapter(val products : List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(val productView: View) : RecyclerView.ViewHolder(productView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.product_view_layout, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        if (product.generic_name != "") {
            holder.productView.product_list_name_textview.text =
                "${product.generic_name}"
        } else {
            holder.productView.product_list_name_textview.text = "Missing name"
        }

        val color = NutriscoreValueAssignement(product.nutriscore_grade)[1]
        val ingredients = "Ingredients: ${shortIngredientsList(product.ingredients_text)}"
        val nutriscore = "Nutriscore: ${product.nutriscore_grade.uppercase()}"

        val spannableIngredientsString = SpannableString(ingredients)
        val spannableScoreString = SpannableString(nutriscore)

        spannableIngredientsString.setSpan(
            ForegroundColorSpan(Color.BLACK),
            0, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        spannableScoreString.setSpan(
            ForegroundColorSpan(Color.parseColor(color)),
            12, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        holder.productView.product_list_ingredient_textview.text = spannableIngredientsString

        holder.productView.product_list_nutriscore_textview.text = spannableScoreString

        Glide.with(holder.productView)
            .load(product.image_url)
            .into(holder.productView.product_list_imageview)

        holder.productView.setOnClickListener {
            with(it.context) {
                val intent = Intent(this, ProductActivity::class.java)
                intent.putExtra("code", products[position]._id)
                startActivity(intent)
            }
        }
    }

    override fun getItemCount() = products.size

    private fun shortIngredientsList(ingredients_text: String): String {
        if (ingredients_text.count() > 100) {
            return ingredients_text.slice(0..100) + " ..."
        }
        return ingredients_text
    }
}