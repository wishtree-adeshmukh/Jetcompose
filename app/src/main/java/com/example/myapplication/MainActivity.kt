package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.ai.osmos.AdRenderSDK.AdRenderer
import com.ai.osmos.AdRenderSDK.BannerAdView
import com.ai.osmos.OsmosSDK
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        OsmosSDK.initialize(this,R.raw.osmos_config)

        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                    AdBannerComposable(this@MainActivity)
                }
            }
        }
    }
}

@Composable
fun AdBannerComposable(context: Context) {
    val adRender = remember { AdRenderer() }
    var adData by remember { mutableStateOf<Map<String, Any>?>(null) }
    
    LaunchedEffect(Unit) {
        adData = adRender.fetchBannerAdsWithAu(
            cliUbid = "9bd111c7-6a57-4a90-8ad0-51a5eb98cfc1",
            pageType = "demo_page",
            adUnits = "video_banner_ads",
            extraParams = mapOf("f.device" to "mobile")
        )
    }

    if (adData != null) {
        AndroidView(
            factory = {
                BannerAdView(
                    context,
                    adData!!,
                    height = 300,
                    width = 290
                )
            },
            update = { bannerAdView ->
                // Optional: You can update the view here if needed
            },
            modifier = Modifier
                .padding(8.dp)
                .height(300.dp)
                .width(290.dp)
        )
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}