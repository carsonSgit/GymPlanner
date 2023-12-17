package com.example.gymplanner.accountpage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymplanner.accountpage.authentication.AuthRepository
import com.example.gymplanner.accountpage.authentication.AuthViewModel
import com.example.gymplanner.ui.theme.WorkoutTrackerTheme
import kotlinx.coroutines.delay

/**
 * UI for signing in or creating a new account.
 * This component manages the user interface for authentication,
 * including sign-in, sign-up, and displaying user account information.
 *
 * @param authRepository The repository responsible for handling authentication logic.
 */
@Composable
fun SignInPage(authRepository: AuthRepository) {
    val authVM = AuthViewModel()

    var showLoginPage by remember { mutableStateOf(false) }
    var showAccountPage by remember { mutableStateOf(false) }
    var isLoggedIn = authVM.currentUser() != null

    if (authVM.currentUser() != null) {
        UserAccountInfo(onSignOutClick = { showLoginPage = true;}, authVM)
    } else if (showLoginPage) {
        LoginPage(
            onSignUpClick = { showLoginPage = false },
            onLogin = { showLoginPage = false },
            authVM
        )
    }
    else {
        SignUpPage(onLogInClick = { showLoginPage = true }, authVM)
    }
}

/**
 * UI for the login page.
 * This component includes email and password input fields,
 * login button, and error message handling.
 *
 * @param onSignUpClick Callback to trigger navigation to the sign-up page.
 * @param onLogin Callback to trigger login action.
 * @param authVM ViewModel for authentication logic.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(onSignUpClick: () -> Unit, onLogin: () -> Unit, authVM : AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    val error = remember { mutableStateOf<String?>(null) }

    var showError by remember { mutableStateOf(false) }

    WorkoutTrackerTheme {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TITLE
            Text(
                text = "Log In",
                modifier = Modifier
                    .padding(bottom = 20.dp)
            )

            // EMAIL INPUT
            TextField(
                value = email,
                onValueChange = { newText ->
                    email = newText
                },
                label = { Text(text = "Enter your email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            )

            // PASSWORD INPUT
            TextField(
                value = password,
                onValueChange = { newText ->
                    password = newText
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                label = { Text("Enter your Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                trailingIcon = {
                    Checkbox(
                        checked = passwordVisibility,
                        onCheckedChange = { passwordVisibility = it }
                    )
                }
            )

            // LOG IN BUTTON
            Button(
                onClick = {
                    if (email.isNotBlank() && password.isNotBlank() &&
                        emailError == null && passwordError == null
                    ) {
                        authVM.signIn(email, password) { isSuccess, errorMessage ->
                            if (isSuccess) {
                                onLogin()
                            } else {
                                error.value = errorMessage ?: "Sign-in failed"
                                showError = true
                            }
                        }
                    } else {
                        error.value = "Please fix the errors before signing in."
                        showError = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Text("Log In")
            }

            // SIGN UP BUTTON
            Button(
                onClick = { onSignUpClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Text("Sign Up")
            }

            // Log-in Error Message
            if (showError) {
                LogInErrorMessage(errorMessage = error.value ?: "Sign-in failed", onDismiss = { showError = false })
            }
        }
    }
}

/**
 * UI for displaying error messages related to login.
 * The error message will automatically dismiss after a delay.
 *
 * @param errorMessage The error message to display.
 * @param onDismiss Callback to handle the dismissal of the error message.
 */
@Composable
fun LogInErrorMessage(errorMessage: String, onDismiss: () -> Unit) {
    var isVisible by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = isVisible) {
        delay(6000L)
        isVisible = false
        onDismiss()
    }

    if (isVisible) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = errorMessage,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Button(
                    onClick = {
                        isVisible = false
                        onDismiss()
                    }
                ) {
                    Text("OK")
                }
            }
        }
    }
}

/**
 * UI for the sign-up page.
 * This component includes email, password, and confirm password input fields,
 * sign-up button, and error message handling.
 *
 * @param onLogInClick Callback to trigger navigation to the login page.
 * @param authVM ViewModel for authentication logic.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpPage(onLogInClick: () -> Unit, authVM: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
    val error = remember { mutableStateOf<String?>(null) }

    var passwordVisibility by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TITLE
        Text(
            text = "Create an Account",
            modifier = Modifier
                .padding(bottom = 20.dp)
        )

        // EMAIL INPUT
        TextField(
            value = email,
            onValueChange = { newText ->
                email = newText
                emailError = if (!isValidEmail(newText)) "Invalid email format" else null
                error.value = null // Clear general error on email change
            },
            label = { Text(text = "Enter your email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            isError = emailError != null,
        )
        emailError?.let { error ->
            Text(text = error, color = Color.Red, modifier = Modifier.padding(vertical = 5.dp))
        }

        // PASSWORD INPUT
        TextField(
            value = password,
            onValueChange = { newText ->
                password = newText
                passwordError = if (newText.length < 3) "Password must be at least 3 characters" else null
                error.value = null // Clear general error on password change
            },
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            label = { Text("Enter your Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            isError = passwordError != null,
            trailingIcon = {
                Checkbox(
                    checked = passwordVisibility,
                    onCheckedChange = { passwordVisibility = it }
                )
            }
        )
        passwordError?.let { error ->
            Text(text = error, color = Color.Red, modifier = Modifier.padding(vertical = 5.dp))
        }

        // CONFIRM PASSWORD
        TextField(
            value = confirmPassword,
            onValueChange = { newText ->
                confirmPassword = newText
                confirmPasswordError =
                    if (newText != password) "Passwords do not match" else null
                error.value = null // Clear general error on confirm password change
            },
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            label = { Text("Confirm Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            isError = confirmPasswordError != null,
        )
        confirmPasswordError?.let { error ->
            Text(text = error, color = Color.Red, modifier = Modifier.padding(8.dp))
        }

        // SUBMIT BUTTON
        Button(
            onClick = {
                if (email.isNotBlank() && password.isNotBlank() &&
                    confirmPassword.isNotBlank() &&
                    emailError == null && passwordError == null &&
                    confirmPasswordError == null
                ) {
                    // Firebase register
                    authVM.signUp(email, password) { isSuccess, errorMessage ->
                        if (isSuccess) {
                            onLogInClick()
                        } else {
                            error.value = errorMessage ?: "Sign-up failed"
                        }
                    }
                } else {
                    error.value = "Please fix the errors before signing in."
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            enabled = true // Always enabled, validation will handle the actual sign-in conditions
        ) {
            Text("Sign Up")
        }

        error.value?.let { error ->
            Text(text = error, color = Color.Red, modifier = Modifier.padding(vertical = 5.dp))
        }

        // BACK TO SIGN IN BUTTON
        Button(
            onClick = { onLogInClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Text("Back to Log In")
        }
    }
}

private fun isValidEmail(email: String): Boolean {
    val emailRegex = Regex("^\\S+@\\S+\\.\\S+\$")
    return email.matches(emailRegex)
}

@Composable
fun UserAccountInfo(
    onSignOutClick: () -> Unit,
    authVM: AuthViewModel
) {
    val currentUser = authVM.currentUser()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "User Account Information",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (currentUser != null) {
            Text("Email: ${currentUser.email}")
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    authVM.signOut()
                    onSignOutClick()
                }
            ) {
                Text("Sign Out")
            }
        } else {
            Text("No user signed in.")
        }
    }
}