package com.kielczykowski.tam.details

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kielczykowski.tam.UIStateDetails
import com.kielczykowski.tam.ui.theme.TAMTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsActivity : ComponentActivity() {
    private val viewModel: DetailsViewModel by viewModel()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val city = intent.getStringExtra("CUSTOM_ID") ?: "error"
        
        Toast.makeText(this, city, Toast.LENGTH_SHORT).show()
        
        viewModel.loadDetailData(city)
        
        setContent {
            TAMTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DetailsView(viewModel = viewModel)
                }
            }
            
            
        }
    }
}

@Composable
fun DetailsView(viewModel: DetailsViewModel) {
    val uiStateDetails by viewModel.immutableliveData.observeAsState(UIStateDetails())
   when {
       uiStateDetails.isLoading -> {
           CircularProgressIndicator(
               modifier = Modifier.width(22.dp)
           )

       }

       uiStateDetails.error != null -> {
           Toast.makeText(LocalContext.current, "${uiStateDetails.error}", Toast.LENGTH_LONG).show()
       }

       else -> {
           uiStateDetails.citiessdetails?.let { citiessdetails ->
               LazyColumn {
                   items(citiessdetails) { population ->
                       Log.d("DetailsView", "Loaded element: $population")
                       detailsView(
                           value = population.value,
                           year = population.year
                       )

                   }
               }
           }
       }
   }
    }
@Composable
fun detailsView(
    value: String,
    year: String,
) {


    Column {

        //Text(
           // text = "${citiess?.data?.populationCounts}",
          //  modifier = Modifier.padding(start = 10.dp),
         //   color = Color.Blue
        //)
            Row {
               // Image(
                 //   modifier = Modifier
                   //     .size(50.dp)

                     //   .padding(vertical = 1.dp, horizontal = 3.dp),
                    //painter = painterResource(id = images.elementAtOrNull(randomValues)!!),//painterResource(id = R.drawable.fsm_android), //image1???
                    //contentDescription = "Herb miasta",
                    //colorFilter = ColorFilter.tint(Color.Magenta)
                //)
                Column {
//                    Text(
//                        text = "${citiess?.data?.city}",
//                        modifier = Modifier.padding(start = 10.dp),
//                        color = Color.Blue
//                    )
//                    Text(
//                        text = "${citiess?.data?.country}",
//                        fontSize = 5.sp,
//                        modifier = Modifier.padding(start = 10.dp),
//                        color = Color.Black
//                    )
                    Row {
                        Text(
                            text = "Populacja w poszczególnych latach:",
                            fontSize = 15.sp,
                            modifier = Modifier.padding(
                                vertical = 10.dp,
                                horizontal = 1.dp
                            ),
                            fontStyle = FontStyle(1000),
                            color = Color.Black
                        )

                    }
                    Row {
                        Row {
                            Text(
                                text = "Rok:",
                                fontSize = 15.sp,
                                fontStyle = FontStyle.Italic,
                                modifier = Modifier.padding(
                                    vertical = 10.dp,
                                    horizontal = 6.dp
                                ),
                                color = Color.Black
                            )
                            Text(
                                text = year,//cities.populationCounts.firstOrNull()?.value ?: "",
                                fontSize = 15.sp,
                                fontStyle = FontStyle.Italic,
                                modifier = Modifier.padding(
                                    vertical = 10.dp,
                                    horizontal = 6.dp
                                ),
                                color = Color.Black
                            )
                        }
                        Row {
                            Text(
                                text = "Populacja:",
                                fontSize = 15.sp,
                                fontStyle = FontStyle.Italic,
                                modifier = Modifier.padding(
                                    vertical = 10.dp,
                                    horizontal = 6.dp
                                ),
                                color = Color.Black
                            )
                            Text(
                                text = value,//cities.populationCounts.firstOrNull()?.value ?: "",
                                fontSize = 15.sp,
                                fontStyle = FontStyle.Italic,
                                modifier = Modifier.padding(
                                    vertical = 10.dp,
                                    horizontal = 6.dp
                                ),
                                color = Color.Black
                            )
                        }
                    }
//                    Row {
//                        Text(
//                            text = "Populacja: ",
//                            fontSize = 15.sp,
//                            modifier = Modifier.padding(
//                                vertical = 10.dp,
//                                horizontal = 1.dp
//                            ),
//                            fontStyle = FontStyle(1000),
//                            color = Color.Black
//                        )
//                        Text(
//                            text = "${citiess?.data?.populationObject?.value?.firstOrNull()}",//cities.populationCounts.firstOrNull()?.value ?: "",
//                            fontSize = 15.sp,
//                            fontStyle = FontStyle.Italic,
//                            modifier = Modifier.padding(
//                                vertical = 10.dp,
//                                horizontal = 6.dp
//                            ),
//                            color = Color.Black
//                        )
//                    }
                }
            }

        }
    }



