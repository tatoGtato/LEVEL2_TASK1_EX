package com.example.task1_ex

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings.Global.getString
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.res.TypedArrayUtils.getText
import com.example.task1_ex.ui.theme.TASK1_EXTheme
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TASK1_EXTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Struct()
                }
            }
        }
    }
}

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Struct() {
    val context = LocalContext.current
    val localDensity = LocalDensity.current
    var HeightDp by remember { mutableStateOf(0.dp) }
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    HeightDp = with(localDensity) { coordinates.size.height.toDp() }},
                title = {
                    Text(
                        text = context.getString(R.string.app_name),
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Magenta)
            )
        },
        content = { padding -> Dice(Modifier.padding(padding), HeightDp) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dice(modifier: Modifier, Topbar: Dp) {
    val context = LocalContext.current
    var dValue by remember { mutableStateOf((1..6).random()) }
    var count by remember { mutableStateOf(1) }
    var sum by remember { mutableStateOf(dValue) }
    var avg by remember { mutableStateOf(dValue.toFloat()) }
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.DOWN

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = Topbar + 5.dp, start = 10.dp, end = 10.dp)
    ){
        Row (
        ){
            Text(text = context.getString(R.string.instruction))
        }
        Row (
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
        ){
            Image(
                painter = painterResource(id = diveValueToImageId(dValue)),
                contentDescription = "", // decorative element
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
            )
        }
        Row (
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
        ){
            Text(text = context.getString(R.string.average,df.format(avg), count))
        }
        Row (
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
            verticalAlignment = Alignment.Bottom
        ){
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Magenta),
                onClick = {
                    count++
                    dValue = (1..6).random()
                    sum = sum + dValue
                    avg = (sum).toFloat()/(count).toFloat()
                }) {
                Text(text = context.getString(R.string.next_roll))
            }
        }

    }
}

private fun diveValueToImageId(diceValue: Int): Int{
    val diceValues = arrayOf(
        R.drawable.dice1, R.drawable.dice2, R.drawable.dice3,
        R.drawable.dice4, R.drawable.dice5, R.drawable.dice6
    )
    return diceValues[diceValue - 1]
}
