package swa.pin.expensetracker.Presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import swa.pin.expensetracker.R
import swa.pin.expensetracker.data.model.ExpenseEntity
import swa.pin.expensetracker.ui.theme.ExpenseTrackerTheme
import swa.pin.expensetracker.ui.theme.zinc
import swa.pin.expensetracker.viewmodel.HomeViewModel
import swa.pin.expensetracker.widgets.ExpenseTextView

@Composable
fun HomeScreen(navController: NavHostController) {
    val viewModel:HomeViewModel=HomeViewModel.HomeViewModelFactory(LocalContext.current).create(HomeViewModel::class.java)
    Surface(modifier=Modifier.fillMaxSize())
    {
        ConstraintLayout(modifier = Modifier.fillMaxSize())
        {
            val context= LocalContext.current
            val (namerow,card,list,topBar,add)=createRefs()
            Image(painter = painterResource(R.drawable.ic_topbar),
                contentDescription = null,
                modifier=Modifier.constrainAs(topBar){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
            Box (
                modifier=Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, start = 16.dp, end = 16.dp)
                    .constrainAs(namerow) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ){
                Column {
                    ExpenseTextView("Good AfterNoon,", fontSize = 16.sp, color = Color.White)
                    ExpenseTextView("Devesh Rawat", fontWeight = FontWeight.ExtraBold, fontSize = 24.sp, color = Color.White)

                }
                Image(painter = painterResource(R.drawable.ic_notification), contentDescription = null,
                    modifier=Modifier.align(Alignment.CenterEnd).clip(CircleShape).clickable {
                        Toast.makeText(context,"No Notifications",Toast.LENGTH_SHORT).show()
                    })

            }
            val state=viewModel.expenses.collectAsState(emptyList())
            val expenses=viewModel.getTotalExpense(state.value)
            val income=viewModel.getTotalIncome(state.value)
            val balance=viewModel.getBalance(state.value)
            CardItem(
                modifier=
                    Modifier.constrainAs(card){
                        top.linkTo(namerow.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                balance,income,expenses
            )
            TransactionList(modifier=Modifier
                .fillMaxWidth()
                .constrainAs(list) {
                    top.linkTo(card.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                },
                state.value,
                viewModel
                )
            Image(
                painter = painterResource(R.drawable.ic_add),
                contentDescription = null,
                modifier = Modifier.padding(end = 16.dp, bottom = 16.dp).constrainAs(add){
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }.size(48.dp)
                    .clip(CircleShape)
                    .background(zinc)
                    .clickable {navController.navigate("/add")  }
            )


        }



    }
    
}

@Composable
fun CardItem(modifier: Modifier = Modifier, balance: String, income: String, expenses: String) {
    Column (modifier=modifier
        .padding(top = 32.dp)
        .fillMaxWidth()
        .padding(16.dp)
        .height(200.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(zinc)
        .padding(16.dp)){

        Box(modifier=Modifier
            .fillMaxWidth()
            .weight(1f)
        ){
            Column {
                ExpenseTextView(text = "Total Balance : ",
                    fontSize = 16.sp,
                    color = Color.White)
                ExpenseTextView(
                    text = "$balance",
                    fontSize = 20.sp,
                    fontWeight =FontWeight.Bold,
                    color = Color.White
                )

            }
            Image(painter = painterResource(R.drawable.dots_menu), contentDescription = null,
                modifier=Modifier.align(Alignment.TopEnd).padding(top = 8.dp).
            clickable { })

        }
        Box(modifier
        =Modifier
            .fillMaxWidth()
            .weight(1f)){
            CardRowItem(
                modifier=Modifier.align(Alignment.CenterStart),
                title = "Income",
                amount = "$income",
                image = R.drawable.ic_income
            )
            CardRowItem(
                modifier=Modifier.align(Alignment.CenterEnd),
                title = "Expense",
                amount = "$expenses",
                image = R.drawable.ic_expense
            )
        }

    }
}

@Composable
fun CardRowItem(
    modifier: Modifier,
    title:String,
    amount:String,
    image:Int
) {
    Column (modifier=modifier){
        Row{
            Image(
                painter = painterResource(image),
                contentDescription = null
            )
            Spacer(modifier.size(8.dp))
            ExpenseTextView(text = title, fontSize = 16.sp, color = Color.White)
        }
        ExpenseTextView(text = amount, fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun TransactionList(modifier: Modifier = Modifier.padding(top = 12.dp, start = 8.dp, end = 8.dp, bottom = 32.dp),list:List<ExpenseEntity>,viewModel: HomeViewModel ) {
    LazyColumn (modifier =modifier.padding(horizontal = 16.dp)){
        item {
            Box(modifier=Modifier.fillMaxWidth()){
                ExpenseTextView("Recent Transaction", fontSize = 20.sp)
            }
        }
        items(list.reversed()){item->
            TransactionItem(
            title = item.title,
            amount = item.amount.toString(),
            icon = viewModel.getItemIcon(item),
            date = item.date,
            color = if(item.type=="Income") Color.Green else Color.Red
        )

        }

    }

}

@Composable
fun TransactionItem(title: String,amount: String,icon:Int,date:String,color: Color) {
    Box(modifier = Modifier.fillMaxWidth().padding(12.dp)){
        Row {
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(
                    50.dp
                )
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column {
                ExpenseTextView(text = title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                ExpenseTextView(text = date, fontSize = 12.sp)
            }
        }
        ExpenseTextView(text = amount,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterEnd),
            color =color,
            fontWeight = FontWeight.SemiBold
        )

    }
    
}





@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(modifier: Modifier = Modifier) {
    ExpenseTrackerTheme {
        HomeScreen(navController = rememberNavController())
    }

}