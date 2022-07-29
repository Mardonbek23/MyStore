package uz.mdev.mystore.db.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import uz.mdev.mystore.db.entities.Product

@Dao
interface ProductDao {
    @Insert(onConflict = REPLACE)
    public fun addProduct(product: Product)

    @Insert(onConflict = REPLACE)
    fun addProductList(list: List<Product>)

    @Update
    fun update(product: Product)

    @Query("select * from product")
    fun getAllProducts():LiveData<List<Product>>
}