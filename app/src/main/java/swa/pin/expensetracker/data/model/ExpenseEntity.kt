package swa.pin.expensetracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.temporal.TemporalAmount


@Entity(tableName = "expense_table")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val title:String,
    val amount: Double,
    val date:String,
    val category:String,
    val type:String
)