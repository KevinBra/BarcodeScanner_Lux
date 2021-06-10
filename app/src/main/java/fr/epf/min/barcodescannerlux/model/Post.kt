package fr.epf.min.barcodescannerlux.model

data class Post(val _id: String ="",
                val image_url: String = "",
                val ingredients_text: String = "",
                val allergens_tags: String = "",
                val generic_name: String = "",
                val nutriscore_grade: String = "",
                val ecoscore_grade: String = "")
