package fr.epf.min.barcodescannerlux.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(@PrimaryKey val _id: String ="",
                   val image_url: String = "",
                   val ingredients_text: String = "",
                   val generic_name: String = "",
                   val nutriscore_grade: String = "",
                   val ecoscore_grade: String = ""
)