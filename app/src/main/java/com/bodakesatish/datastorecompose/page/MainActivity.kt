package com.bodakesatish.datastorecompose.page

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.bodakesatish.datastorecompose.ui.theme.DataStoreComposeTheme
import com.bodakesatish.datastorecompose.utils.DataStoreManager
import com.bodakesatish.datastorecompose.utils.preferenceDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DataStoreComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val dataStoreContext = LocalContext.current
                    val dataStoreManager = DataStoreManager(dataStoreContext)
                    AppContent(
                        preferenceDataStore,
                        dataStoreManager
                    )
                }
            }
        }
    }
}

@Composable
fun AppContent(
    preferenceDataStore: DataStore<Preferences>,
    dataStoreManager: DataStoreManager
) {
    var isRegistered by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val onRegisterSuccess = { isRegistered = true }
    val onLogout = {
        isRegistered = false
        scope.launch {
            dataStoreManager.clearDataStore()
        }
    }

    //lets check if user is registered in when the app start
    LaunchedEffect(key1 = Unit) {
        checkRegisterState(preferenceDataStore) { it ->
            isRegistered = it
        }
    }

    if (isRegistered) {
        HomePage(onLogout, dataStoreManager)
    } else {
        RegisterPage(onRegisterSuccess, dataStoreManager)
    }
}

suspend fun checkRegisterState(
    preferenceDataStore: DataStore<Preferences>,
    onResult: (Boolean) -> Unit
) {
    val preferenceFlow = preferenceDataStore.data.first()
    val email = preferenceFlow[DataStoreManager.USER_EMAIL]
    val isRegistered = email != null
    onResult(isRegistered)
}