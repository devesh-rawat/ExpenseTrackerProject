package swa.pin.expensetracker

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import swa.pin.expensetracker.Presentation.AddExpense
import swa.pin.expensetracker.Presentation.HomeScreen

@Composable
fun NavHostScreen(modifier: Modifier = Modifier) {
    val navController= rememberNavController()
    NavHost(
        navController=navController,
        startDestination = "/home"
    ){
        composable(route="/home"){
            HomeScreen(navController)
        }
        composable(route="/add"){
            AddExpense(navController)
        }
    }

}