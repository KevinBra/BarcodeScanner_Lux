package fr.epf.min.barcodescannerlux.utils

fun NutriscoreValueAssignement(nutriscore: String): Array<String>{
    return when(nutriscore){
        "a" -> arrayOf("Grade A: Very good nutritional quality", "#228B22")
        "b" -> arrayOf("Grade B: Good nutritional quality", "#00FF00")
        "c" -> arrayOf("Grade C: Average nutritional quality", "#CCCC00")
        "d" -> arrayOf("Grade D: Poor nutritional quality", "#FF8C00")
        "e" -> arrayOf("Grade E: Bad nutritional quality", "#FF0000")
        else -> arrayOf("Missing nutriscore", "black")
    }
}

fun EcoscoreValueAssignement(ecoscore: String): Array<String>{
    return when(ecoscore){
        "a" -> arrayOf("Grade A: Very good environmental impact", "#228B22")
        "b" -> arrayOf("Grade B: Good environmental impact", "#00FF00")
        "c" -> arrayOf("Grade C: Average environmental impact", "#CCCC00")
        "d" -> arrayOf("Grade D: Poor environmental impact", "#FF8C00")
        "e" -> arrayOf("Grade E: Bad environmental impact", "#FF0000")
        else -> arrayOf("Missing ecoscore", "black")
    }
}