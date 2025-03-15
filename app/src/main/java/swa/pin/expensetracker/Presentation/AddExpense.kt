package swa.pin.expensetracker.Presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import swa.pin.expensetracker.R
import swa.pin.expensetracker.Utils
import swa.pin.expensetracker.data.model.ExpenseEntity
import swa.pin.expensetracker.ui.theme.ExpenseTrackerTheme
import swa.pin.expensetracker.ui.theme.zinc
import swa.pin.expensetracker.viewmodel.AddExpenseViewModel
import swa.pin.expensetracker.viewmodel.AddExpenseViewModelFactory
import swa.pin.expensetracker.widgets.ExpenseTextView

@Composable
fun AddExpense(navController: NavHostController = rememberNavController()) {
    val viewModel=AddExpenseViewModelFactory(LocalContext.current).create(AddExpenseViewModel::class.java)
    val scope= rememberCoroutineScope()
    Surface(modifier=Modifier.fillMaxSize())
    {
        ConstraintLayout(modifier = Modifier.fillMaxSize())
        {
            val (namerow, card, list, topBar) = createRefs()
            Image(painter = painterResource(R.drawable.ic_topbar),
                contentDescription = null,
                modifier = Modifier.constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
            Box(modifier=Modifier
                .fillMaxWidth()
                .padding(top = 60.dp)
                .padding(16.dp)
                .constrainAs(namerow) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }){
                Image(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = null,
                    modifier=Modifier.align(Alignment.TopStart).clip(CircleShape)
                        .clickable { navController.navigateUp() }
                )
                ExpenseTextView("Add Expense",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
                Image(
                    painter = painterResource(R.drawable.dots_menu),
                    contentDescription = null,
                    modifier=Modifier.align(Alignment.CenterEnd)
                )
            }
            //
            DataForm(modifier = Modifier
                .constrainAs(card) {
                    top.linkTo(namerow.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(top = 48.dp),
                onAddExpenseClick = {
                    scope.launch {
                        if(viewModel.addExpense(it)){
                            navController.popBackStack()
                        }
                    }
                })
        }

    }
}

@Composable
fun DataForm(modifier: Modifier = Modifier,onAddExpenseClick:(model:ExpenseEntity)->Unit) {
    val name= remember { mutableStateOf("") }
    val amount= remember { mutableStateOf("") }
    val date = remember { mutableStateOf(0L) }
    val dateDialogVisibility = remember { mutableStateOf(false) }
    val category= remember { mutableStateOf("") }
    val type= remember { mutableStateOf("") }
    Column (
        modifier=modifier
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(elevation = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)

    ){

//        OutlinedTextField(
//            label = {ExpenseTextView("Type", fontSize = 16.sp)},
//            value = "",
//            onValueChange = {},
//            modifier=Modifier.fillMaxWidth()
//        )
        Spacer(modifier=Modifier.height(12.dp))
        OutlinedTextField(
            label = {ExpenseTextView("Name", fontSize = 16.sp)},
            value = name.value,
            onValueChange = {name.value=it},
            modifier=Modifier.fillMaxWidth()
        )
        Spacer(modifier=Modifier.height(12.dp))
        OutlinedTextField(
            label = {ExpenseTextView("Amount", fontSize = 16.sp)},
            value = amount.value,
            onValueChange = {amount.value=it},
            modifier=Modifier.fillMaxWidth()
        )
        Spacer(modifier=Modifier.height(12.dp))
        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(
                disabledContainerColor = Color.White,
                disabledTextColor = Color.Black,
                disabledLabelColor = Color.DarkGray,
                disabledBorderColor = Color.DarkGray
            ),
            label = {ExpenseTextView("Date", fontSize = 16.sp)},
            value = if(date.value==0L) "" else Utils.formatDateToHumanReadableForm(date.value),
            onValueChange = {},
            modifier=Modifier
                .fillMaxWidth()
                .clickable { dateDialogVisibility.value = true },
            enabled = false
        )

        Spacer(modifier=Modifier.height(12.dp))
        ExpenseDropDown(
            listOfItems = listOf(
                "Netflix",
                "Paypal",
                "Starbucks",
                "Salary",
                "Upwork"
                )
        ) {
            category.value=it
        }
        Spacer(modifier=Modifier.height(12.dp))
        ExpenseDropDown(
            listOfItems = listOf(
                "Income",
                "Expense"
            )
        ) {
            type.value=it
        }
        Spacer(modifier=Modifier.height(16.dp))
        Button(
            onClick = {
                val model=ExpenseEntity(
                    null?:0,
                    title = name.value,
                    amount = amount.value.toDoubleOrNull()?:0.0,
                    date = Utils.formatDateToHumanReadableForm(date.value),
                    category = category.value,
                    type = type.value
                )
                onAddExpenseClick(model)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(zinc)
        ) {
            ExpenseTextView("Add Expense",
                fontSize = 14.sp,
                color = Color.White)
        }

    }
    if(dateDialogVisibility.value){
        ExpenseDatePicker(
            onDateSelected = {
                date.value=it
                dateDialogVisibility.value=false},
            onDismiss = {dateDialogVisibility.value=false}
        )
    }
    
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDatePicker(
    onDateSelected:(date:Long)->Unit,
    onDismiss:()->Unit
) {
    val datePickerState= rememberDatePickerState()
    val selectedDate=datePickerState.selectedDateMillis?:0L
    DatePickerDialog(
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(onClick = {onDateSelected(selectedDate)}) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {onDismiss()}
            ) { Text("Cancel") }
        }
    ) {
        DatePicker(state = datePickerState)
    }



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDropDown(listOfItems:List<String>,onItemSelected:(item:String)->Unit) {
    val expanded= remember {
        mutableStateOf(false)
    }
    val selectedItem= remember {
        mutableStateOf(listOfItems[0])
    }
    ExposedDropdownMenuBox(
        expanded=expanded.value,
        onExpandedChange = {
            expanded.value=it
        }
    ) {
        OutlinedTextField(
            value = selectedItem.value,
            onValueChange = {

            },
            label = { if(listOfItems.contains("Income")) Text("Type") else Text("Category") },
            modifier = Modifier.fillMaxWidth().menuAnchor(),
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded=expanded.value)
            }

        )
        ExposedDropdownMenu(
            expanded= expanded.value,
            onDismissRequest = {
                expanded.value=false
            }) {
            listOfItems.forEach{
                DropdownMenuItem(
                    text = {
                        ExpenseTextView(text = it )
                    },
                    onClick = {
                        selectedItem.value=it
                        onItemSelected(selectedItem.value)
                        expanded.value=false
                    }
                )
            }
        }

    }

}

@Preview(showBackground = true,)
@Composable
fun AddExpensePreview(modifier: Modifier = Modifier){
    ExpenseTrackerTheme {
        AddExpense()
    }

}