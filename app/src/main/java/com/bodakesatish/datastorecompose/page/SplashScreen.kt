package com.bodakesatish.datastorecompose.page

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bodakesatish.datastorecompose.R
import kotlinx.coroutines.delay

class SplashScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // App Logo
                Image(
                    painter = painterResource(R.drawable.ic_android_foreground),
                    contentDescription = "App Logo"
                )
                // App Name
                Text(
                    text = stringResource(R.string.datastore_compose_sample),
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )

                CircularProgressIndicator(
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            LaunchedEffect(key1 = true) {
                // Delay for 3 seconds
                delay(1500L)
                startActivity(Intent(this@SplashScreen, RegisterActivity::class.java))
                finish()
            }
        }
    }
}