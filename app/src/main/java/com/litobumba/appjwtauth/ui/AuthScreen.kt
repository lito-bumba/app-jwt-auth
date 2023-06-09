package com.litobumba.appjwtauth.ui

import PasswordTextField
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.litobumba.appjwtauth.auth.AuthResult
import kotlinx.coroutines.flow.collect

@Composable
fun AuthScreen(
    navigator: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    LaunchedEffect(viewModel, context) {
        viewModel.authResult.collect { result ->
            when (result) {
                is AuthResult.Authorized -> {
                    navigator.navigate("secret_screen")
                }
                is AuthResult.Unauthorized -> {
                    Toast.makeText(context, "You're not authorized", Toast.LENGTH_LONG).show()
                }
                is AuthResult.UnknownError -> {
                    Toast.makeText(context, "An unknown error occorred", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = state.signUpUsername,
            onValueChange = { viewModel.onEvent(AuthUiEvent.SignUpUsernameChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Username") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Username Icon"
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasswordTextField(
            value = state.signUpPassword,
            onValueChange = { viewModel.onEvent(AuthUiEvent.SignUpPasswordChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Password") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.onEvent(AuthUiEvent.SignUp) },
            modifier = Modifier.align(Alignment.End)
        ) { Text(text = "Sign up") }

        Spacer(modifier = Modifier.height(64.dp))

        TextField(
            value = state.signInUsername,
            onValueChange = { viewModel.onEvent(AuthUiEvent.SignInUsernameChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Username") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Username Icon"
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasswordTextField(
            value = state.signInPassword,
            onValueChange = { viewModel.onEvent(AuthUiEvent.SignInPasswordChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Password") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.onEvent(AuthUiEvent.SignIn) },
            modifier = Modifier.align(Alignment.End)
        ) { Text(text = "Sign in") }
    }
    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator() }
    }
}