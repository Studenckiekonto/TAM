package com.kielczykowski.tam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kielczykowski.tam.details.DetailsActivity
import com.kielczykowski.tam.ui.theme.TAMTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random

class MainActivity : ComponentActivity() {


    private val viewModel: MainViewModel by viewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //crash
        //throw RuntimeException("Mój pierwszy crash")

        viewModel.getData()



        setContent {
            TAMTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold (
                        topBar = { MyTopView(viewModel = viewModel) }
                    ) { scaffoldPaddings ->
                        MainView(
                            modifier = Modifier.padding(scaffoldPaddings),
                            viewModel = viewModel,
                            onClick = { id -> navigateToDetails(id) })
                    }
                }
            }
        }
    }

    fun navigateToDetails(id: String) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("CUSTOM_ID", id)
        startActivity(intent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopView(viewModel: MainViewModel) {
    var searchText by remember { mutableStateOf("") }

    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        query = searchText,
        onQueryChange = { wpisywanyTekst -> searchText = wpisywanyTekst },
        onSearch = { viewModel.updateFilterQuery(it) },
        placeholder = { Text(text = "Wyszukaj...") },
        active = false,
        onActiveChange = { },
        leadingIcon = {
          Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
        },
        trailingIcon = {
            Image(
                modifier = Modifier.clickable {
                    searchText = ""
                    viewModel.updateFilterQuery("")
                },
                imageVector = Icons.Default.Clear,
                contentDescription = "Clear"
            )
        }
    ) {

    }
}

//tu rozpoczyna się zmodyfikowany LazyList

//    @Composable
//    fun MainView(viewModel: MainViewModel) {
//        val citiess by viewModel.immutablecitiesData.observeAsState(emptyList())
//
//        LazyColumn {
//            // import funkcji items z androidx.compose.foundation.lazy.items
//            items(citiess) { cities ->
//                citiesView(city = cities.city, country = cities.country, value = cities.populationCounts.firstOrNull()?.value)
//                Log.d("Main", "${cities.city}, ${cities.country}, ${cities.populationCounts.firstOrNull()?.value}") //${population.value}")
//                Column {
//                Column {
//                    Row {
//                        Image(
//                            modifier = Modifier
//                                .size(50.dp)
//                                .padding(vertical = 1.dp, horizontal = 3.dp),
//                            painter = painterResource(id = R.drawable.fsm_android),
//                            contentDescription = "Fabryka Samochodów Małolitrażowych",
//                            //colorFilter = ColorFilter.tint(Color.Magenta)
//                        )
//                        Column {
//                            Text(
//                                text = cities.city,
//                                modifier = Modifier.padding(start = 10.dp),
//                                color = Color.Blue
//                            )
//                            Text(
//                                text = cities.country,
//                                fontSize = 5.sp,
//                                modifier = Modifier.padding(start = 10.dp),
//                                color = Color.Black
//                            )
//                            Row {
//                                Text(
//                                    text = "Populacja:",
//                                    fontSize = 7.sp,
//                                    modifier = Modifier.padding(
//                                        vertical = 10.dp,
//                                        horizontal = 1.dp
//                                    ),
//                                    fontStyle = FontStyle(1000),
//                                    color = Color.Black
//                                )
//                                Text(
//                                    text = cities.populationCounts.firstOrNull()?.value ?: "",
//                                    fontSize = 7.sp,
//                                    fontStyle = FontStyle.Italic,
//                                    modifier = Modifier.padding(
//                                        vertical = 10.dp,
//                                        horizontal = 6.dp
//                                    ),
//                                    color = Color.Black
//                                )
//                            }
//                        }
//                    }
//
//                }
//            }
//
//        }
//    }
//}
//
//
//
//    @Composable
//    fun citiesView(city: String, country: String, value: String) {
//    }

//tu kończy się zmodyfikowany LazyList




@Composable
fun MainView(modifier: Modifier, viewModel: MainViewModel, onClick: (String) -> Unit) {
    val uiState by viewModel.immutablecitiesData.observeAsState(UIState())
    val query by viewModel.filterQuery.observeAsState("")
    //val pierwszeMiasto = cities.firstOrNull()
    when {
        uiState.isLoading -> {
            CircularProgressIndicator(
                modifier = Modifier.width(22.dp)
            )

        }

        uiState.error != null -> {
            Toast.makeText(LocalContext.current, "${uiState.error}", Toast.LENGTH_LONG).show()
        }


        else -> {//uiState.citiess != null -> {
            //uiState.citiess?.let { citiess ->
            uiState.citiess?.let { restCitiess ->
                restCitiess.filter { it.city.contains(query, ignoreCase = true) }.let { citiess ->
                    LazyColumn {
                        items(citiess) { cities ->
                            Log.d("MainView", "Loaded element: $cities")
                            citiesView(city = cities.city,
                                country = cities.country,
                                value = cities.populationCounts.firstOrNull()!!.value,
                                id = cities.city,
                                onClick = { id -> onClick.invoke(id) })

                        }
                    }
                }
            }
        }
    }
}



//    if (!citiess.isNullOrEmpty()) {
//        citiess.forEachIndexed { index, cities ->
//            //citiess.forEachIndexed { index, population ->
//            if (index > 0) {
//                return@forEachIndexed
//            }
//            Log.d(
//                "Main",
//                "$index ${cities.city}, ${cities.country}, ${cities.populationCounts.firstOrNull()?.value}"
//            ) //${population.value}")
//
//        //tu był kafelek
//
//
//        }
//    }
//    }









    @Composable
    fun citiesView(
        city: String,
        country: String,
        value: String,
        id: String,
        onClick: (String) -> Unit
    ) {


        val randomValues = Random.nextInt(0, 4)
        val images = arrayOf(
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
        )
        //fun randomImage() = (images).random()
        Column(modifier = Modifier.clickable { onClick.invoke(id) }) {
            Column {
                Row {
                    Image(
                        modifier = Modifier
                            .size(50.dp)

                            .padding(vertical = 1.dp, horizontal = 3.dp),
                        painter = painterResource(id = images.elementAtOrNull(randomValues)!!),//painterResource(id = R.drawable.fsm_android), //image1???
                        contentDescription = "Herb miasta",
                        //colorFilter = ColorFilter.tint(Color.Magenta)
                    )
                    Column {
                        Text(
                            text = city,
                            modifier = Modifier.padding(start = 10.dp),
                            color = Color.Blue
                        )
                        Text(
                            text = country,
                            fontSize = 5.sp,
                            modifier = Modifier.padding(start = 10.dp),
                            color = Color.Black
                        )
                        Row {
                            Text(
                                text = "Populacja:",
                                fontSize = 7.sp,
                                modifier = Modifier.padding(
                                    vertical = 10.dp,
                                    horizontal = 1.dp
                                ),
                                fontStyle = FontStyle(1000),
                                color = Color.Black
                            )
                            Text(
                                text = value,//cities.populationCounts.firstOrNull()?.value ?: "",
                                fontSize = 7.sp,
                                fontStyle = FontStyle.Italic,
                                modifier = Modifier.padding(
                                    vertical = 10.dp,
                                    horizontal = 6.dp
                                ),
                                color = Color.Black
                            )
//                        AsyncImage(
//                            model = urlobrazka,
//                            contentDesciprion = "tojestobrazek1"
//                            placeholder = painterResource(id = R.drawable.placeholder)
//                        )
                        }
                    }
                }

            }
        }
    }




//@Composable
//fun showSnackbar(error: String) {
//    val snackbarHostState = remember { SnackbarHostState() }
//    Scaffold {
//        SnackbarHost(hostState = snackbarHostState)
//    },
//    flo
//
//}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    TAMTheme {
        //citiesView(city = "Mariehamn", country = "Aaland Islands", value = "11355", id = 9, onClick = (Int) -> Unit)
//    }
//}

//}



/*

   Column {
        Column {
           Row {
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .padding(vertical = 1.dp, horizontal = 3.dp),
                    painter = painterResource(id = R.drawable.fsm_android),
                    contentDescription = "Fabryka Samochodów Małolitrażowych",
                    //colorFilter = ColorFilter.tint(Color.Magenta)
                )
                Column {
                    Text(
                        text = "Mariehamn",
                        modifier = Modifier.padding(start = 10.dp),
                        color = Color.Blue
                    )
                    Text(
                        text = "Åland Islands",
                        fontSize = 5.sp,
                        modifier = Modifier.padding(start = 10.dp),
                        color = Color.Black
                    )
                    Row {
                        Text(
                            text = "Populacja (2011):",
                            fontSize = 7.sp,
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 1.dp),
                            fontStyle = FontStyle(1000),
                            color = Color.Black
                        )
                        Text(
                            text = "11226",
                            fontSize = 7.sp,
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
                            color = Color.Black
                        )
                    }
                }
            }

        }
        Text(
            text = "",
            fontSize = 2.sp
        )
        Column {
            Row {
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .padding(vertical = 1.dp, horizontal = 3.dp),
                    painter = painterResource(id = R.drawable.fsm_android),
                    contentDescription = "Fabryka Samochodów Małolitrażowych",
                    //colorFilter = ColorFilter.tint(Color.Magenta)
                )
                Column {
                    Text(
                        text = "Durrës",
                        modifier = Modifier.padding(start = 10.dp),
                        color = Color.Blue
                    )
                    Text(
                        text = "Albania",
                        fontSize = 5.sp,
                        modifier = Modifier.padding(start = 10.dp),
                        color = Color.Black
                    )
                    Row {
                        Text(
                            text = "Populacja (2011):",
                            fontSize = 7.sp,
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 1.dp),
                            fontStyle = FontStyle(1000),
                            color = Color.Black
                        )
                        Text(
                            text = "113249",
                            fontSize = 7.sp,
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
                            color = Color.Black
                        )
                    }
                }
            }

        }
        Box {

        }
        Text(
            text = "",
            fontSize = 2.sp
        )
        Column {
            Row {
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .padding(vertical = 1.dp, horizontal = 3.dp),
                    painter = painterResource(id = R.drawable.fsm_android),
                    contentDescription = "Fabryka Samochodów Małolitrażowych",
                    //colorFilter = ColorFilter.tint(Color.Magenta)
                )
                Column {
                    Text(
                        text = "Tirana",
                        modifier = Modifier.padding(start = 10.dp),
                        color = Color.Blue
                    )
                    Text(
                        text = "Albania",
                        fontSize = 5.sp,
                        modifier = Modifier.padding(start = 10.dp),
                        color = Color.Black
                    )
                    Row {
                        Text(
                            text = "Populacja (2011):",
                            fontSize = 7.sp,
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 1.dp),
                            fontStyle = FontStyle(1000),
                            color = Color.Black
                        )
                        Text(
                            text = "418495",
                            fontSize = 7.sp,
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
                            color = Color.Black
                        )
                    }
                }
            }

        }
        Box {

        }
   }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TAMTheme {
        Showcase(name = "Mariehamn")
    }
}
*/
