package swa.pin.expensetracker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import swa.pin.expensetracker.data.ExpenseDatabase
import swa.pin.expensetracker.data.dao.ExpenseDao
import swa.pin.expensetracker.data.model.ExpenseEntity

class AddExpenseViewModel(val dao:ExpenseDao):ViewModel() {

    suspend fun addExpense(expenseEntity: ExpenseEntity):Boolean{
        try {
            dao.insertExpense(expenseEntity)
            return true

        }catch (e:Exception){
            return false

        }
    }

}

class AddExpenseViewModelFactory(private val context: Context):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddExpenseViewModel::class.java)){
            val dao=ExpenseDatabase.getDatabase(context).expenseDao()
            return AddExpenseViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}