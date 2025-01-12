package com.bodakesatish.datastorecompose.page

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bodakesatish.datastorecompose.R
import com.bodakesatish.datastorecompose.model.UserDetail
import com.bodakesatish.datastorecompose.utils.DataStoreManager
import kotlinx.coroutines.launch

@Composable
fun RegisterPage(onRegisterSuccess: () -> Unit, dataStoreManager: DataStoreManager) {
    var email by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Create focus requesters for each TextField
    val nameFocusRequester = remember { FocusRequester() }
    val mobileNumberFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }

    //Get the current focus manager
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(16.dp)
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Main card Content for Register
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 8.dp)
            ) {
                // Heading Jetpack Compose
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.datastore_compose_sample),
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp
                )
                Text(
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                    text = "Register",
                    fontSize = 25.sp
                )

                // Name Field
                OutlinedTextField(
                    value = name, // Add a variable for name
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next // Set imeAction to Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            mobileNumberFocusRequester.requestFocus()
                        }
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .focusRequester(nameFocusRequester)
                )

                // Mobile Field
                OutlinedTextField(
                    value = mobileNumber, // Add a variable for mobile
                    onValueChange = { mobileNumber = it },
                    label = { Text("Mobile Number") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next // Set imeAction to Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            emailFocusRequester.requestFocus()
                        }
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .focusRequester(mobileNumberFocusRequester)
                )

                // Email Field
                OutlinedTextField(
                    value = email, // Add a variable for email
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next // Set imeAction to Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            passwordFocusRequester.requestFocus()
                        }
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .focusRequester(emailFocusRequester)
                )

                // Password Field
                OutlinedTextField(
                    value = password, // Add a variable for password
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done // Set imeAction to Next
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus() // Hide the keyboard
                        }
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .focusRequester(passwordFocusRequester)
                )

                Button(
                    onClick = {
                        if (name.isEmpty()) {
                            nameFocusRequester.requestFocus()
                            Toast.makeText(context, "Name is Empty", Toast.LENGTH_SHORT).show()
                        } else if (mobileNumber.isEmpty()) {
                            mobileNumberFocusRequester.requestFocus()
                            Toast.makeText(context, "Mobile No. is Empty", Toast.LENGTH_SHORT)
                                .show()
                        } else if (email.isEmpty()) {
                            emailFocusRequester.requestFocus()
                            Toast.makeText(context, "Email is Empty", Toast.LENGTH_SHORT).show()
                        } else if (password.isEmpty()) {
                            passwordFocusRequester.requestFocus()
                            Toast.makeText(context, "Password is Empty", Toast.LENGTH_SHORT).show()
                        } else {
                            //Submit you data
                            scope.launch {
                                dataStoreManager.saveToDataStore(
                                    UserDetail(
                                        emailAddress = email,
                                        name = name,
                                        mobileNumber = mobileNumber,
                                        password = password
                                    )
                                )
                                onRegisterSuccess()
                            }
                        }

                    }, modifier = Modifier.padding(16.dp)
                ) {
                    Text("Submit")
                }


            }
        }
    }
}