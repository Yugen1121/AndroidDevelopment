package com.example.myapplication


import android.os.Bundle
import android.service.autofill.OnClickAction
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.lazy.items
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.Spring




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier, names: List<String> = List(1000){"$it"}){
    var shouldShowOnBoard by rememberSaveable { mutableStateOf<Boolean>(true)}

    if (shouldShowOnBoard){
        onBoardingScreen({shouldShowOnBoard = !shouldShowOnBoard})
    }
    else{
        Greetings(names)
    }


}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var showMore by rememberSaveable { mutableStateOf<Boolean>(false) }
    val extraPadding by animateDpAsState(
        if (showMore) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )


    )
    Surface(color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)) {
        Row (modifier = modifier.fillMaxWidth().padding(24.dp).padding(bottom = extraPadding)) {
            Column(modifier = modifier.weight(1f)) {
                Text("Hello")
                Text(text = name)
            }
            ElivatedButton(showMore, {showMore = !showMore})
        }
    }

}

@Composable
fun ElivatedButton(showMore: Boolean, onToggle: () -> Unit, modifier: Modifier = Modifier){
        Button (onClick = onToggle, colors = ButtonDefaults
            .buttonColors(containerColor = Color.White, contentColor = Color.Blue)) {
            Text(if(showMore) "Show less" else "Show more")
        }
}


@Preview(showBackground = false)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}

@Composable
fun onBoardingScreen(nextPage: () -> Unit){
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Click to load next page")
        Button(onClick = nextPage){
            Text("Click here")
        }
    }
}

@Preview(showBackground = false)
@Composable
fun onBoardingScreenPreview(){
    MyApplicationTheme() {
        onBoardingScreen {  }
    }
}

@Composable
fun Greetings(names: List<String>){
    LazyColumn (modifier = Modifier.padding( 4.dp)) {
        items(items = names) { name ->
            Greeting(name)
        }
    }
}
