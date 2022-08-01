package uz.mdev.mystore.db

import android.content.Context
import androidx.room.*
import uz.mdev.mystore.db.dao.ProductDao
import uz.mdev.mystore.db.entities.product.Product
import uz.mdev.mystore.db.entities.product.ProductPriceDetails
import uz.mdev.mystore.helpers.ListConverter

class ProductDetailConvereter : ListConverter<ProductPriceDetails>()

@Database(entities = [Product::class], version = 1)
@TypeConverters(ProductDetailConvereter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        private var appDatabase: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (appDatabase == null) {
                appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "my_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return appDatabase!!
        }
    }

}