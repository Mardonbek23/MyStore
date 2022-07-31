package uz.mdev.mystore.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import uz.mdev.mystore.db.entities.product.Product

@Dao
interface ProductDao {
    @Insert(onConflict = REPLACE)
    public fun addProduct(product: Product)

    @Insert(onConflict = REPLACE)
    fun addProductList(list: List<Product>)

    @Update
    fun update(product: Product)

    @Query("select * from product")
    fun getAllProducts(): LiveData<List<Product>>

    @Query("select * from product where id in (:ids)")
    fun getProductsByIds(ids: List<Int>): List<Product>

    @Update(onConflict = REPLACE)
    fun updateListProducts(list: List<Product>)

}