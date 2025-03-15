package swa.pin.expensetracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import swa.pin.expensetracker.Utils
import swa.pin.expensetracker.data.dao.ExpenseDao
import swa.pin.expensetracker.data.model.ExpenseEntity


@Database(entities = [ExpenseEntity::class], version = 1)
abstract class ExpenseDatabase:RoomDatabase() {
    abstract fun expenseDao():ExpenseDao

    companion object{
        const val DATABASE_NAME="expense_database"

        @JvmStatic
        fun getDatabase(context:Context):ExpenseDatabase{
            return Room.databaseBuilder(
                context,
                ExpenseDatabase::class.java,
                DATABASE_NAME
            ).build()
        }

    }
}