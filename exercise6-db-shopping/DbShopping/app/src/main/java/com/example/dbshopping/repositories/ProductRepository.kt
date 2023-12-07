package com.example.dbshopping.repositories

import com.example.dbshopping.models.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProductRepository {
    private val db = FirebaseFirestore.getInstance()
    private val productCollection = db.collection("products")

    suspend fun addProduct(product: Product) {
        product.id?.let {
            productCollection.document(it).set(product).await()
        }
    }

    suspend fun getProduct(id: String): Product? {
        val document = productCollection.document(id).get().await()

        return if (document.exists()) {
            document.toObject(Product::class.java)
        } else null
    }

    suspend fun getAllProducts(): List<Product> {
        val query = productCollection.get().await()
        return query.toObjects(Product::class.java)
    }

    suspend fun updateProduct(product: Product) {
        product.id?.let {
            productCollection.document(it).set(product).await()
        }
    }

    suspend fun deleteProduct(id: String) {
        productCollection.document(id).delete().await()
    }
}