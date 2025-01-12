package com.bodakesatish.datastorecompose.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bodakesatish.datastorecompose.utils.DataStoreManager
import kotlinx.coroutines.Job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(onLogout: () -> Job, dataStoreManager: DataStoreManager) {
    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
            title = { Text("Home Page", color = Color.White) }
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val userDetail by dataStoreManager.getFromDataStore().collectAsState(initial = null)

            Text(
                text = "Hi, ${"\nWelcome to the Home Page"}",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Name: ${userDetail?.name}",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Email: ${userDetail?.emailAddress}",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Mobile: ${userDetail?.mobileNumber}",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Password: ${userDetail?.password}",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Logout Button
                Button(
                    onClick = { onLogout() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = "Logout",
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }

    }
}