package fr.epf.min.barcodescannerlux.utils

import fr.epf.min.barcodescannerlux.models.Product

object data {
    private val favorites : ArrayList<Product> = arrayListOf()

    fun AddFavProduct(product: Product){
        if(!isFavProduct(product)){
            favorites.add(product)
        }
    }
    fun GetFavProducts() : ArrayList<Product>{
        return favorites
    }
    fun isFavProduct(product: Product): Boolean{
        return favorites.contains(product)
    }
    fun RemoveFavProduct(product: Product){
        if(isFavProduct(product)){
            favorites.remove(product)
        }
    }
}