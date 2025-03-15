package swa.pin.expensetracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import swa.pin.expensetracker.data.model.ExpenseEntity

@Dao
interface ExpenseDao {

    @Query("Select * from expense_table")
    fun getAllExpenses():Flow<List<ExpenseEntity>>

    @Insert
    suspend fun insertExpense(expenseEntity: ExpenseEntity)

    @Delete
    suspend fun deleteExpense(expenseEntity: ExpenseEntity)

    @Update
    suspend fun updateExpense(expenseEntity: ExpenseEntity)
}