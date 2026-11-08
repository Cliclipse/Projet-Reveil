package com.example.projetreveil.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projetreveil.R
import com.example.projetreveil.ui.theme.datastores.DataStoreUserLogin
import com.example.projetreveil.ui.theme.utilities.HeadedTextField
import com.example.projetreveil.ui.theme.utilities.LoadingIndicator
import com.example.projetreveil.ui.theme.utilities.Logo
import com.example.projetreveil.ui.theme.viewModel.ErrorType
import com.example.projetreveil.ui.theme.viewModel.RegisterScreenViewModel
import com.example.projetreveil.ui.theme.viewModel.RegisterScreenViewModel.AuthStateEnum
import kotlinx.coroutines.flow.first


@Composable
fun RegisterPage(
    name: String,
    modifier: Modifier = Modifier,
    userLoginDataStore: DataStoreUserLogin,
    onConnectionSuccessful: () -> Unit
){
    val registerViewModel : RegisterScreenViewModel = viewModel()

    LaunchedEffect(registerViewModel.state) {
        if (registerViewModel.state == AuthStateEnum.SUCCESS) {

            userLoginDataStore.defineLastUsername(registerViewModel.pseudo)

            onConnectionSuccessful()
        }
    }

    if (registerViewModel.state == AuthStateEnum.LOADING){
        LoadingIndicator()
        return
    }

    LaunchedEffect(Unit){
        val lastUsername = userLoginDataStore.lastUsername.first()

        registerViewModel.updatePseudo(lastUsername)
    }

    RegisterContent(modifier = modifier,
        pseudo = registerViewModel.pseudo,
        password = registerViewModel.mdp,
        errorType = registerViewModel.errorType,
        onRegisterUserClicked = {
            if (registerViewModel.pseudo != "" && registerViewModel.mdp != ""){
                registerViewModel.registerUser()
            }
        },
        onConnectUserClicked = {
            if (registerViewModel.pseudo != "" && registerViewModel.mdp != ""){
                registerViewModel.connectUser()
            }
        },
        onPseudoTextChange = {
            registerViewModel.updatePseudo(inPseudo = it)
        },
        onPasswordTextChange = {
            registerViewModel.updatePassword(inPassword = it)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun RegisterContent(modifier: Modifier = Modifier,
                    pseudo: String = "Pseudo",
                    password: String = "Password",
                    errorType: ErrorType? = null,
                    onRegisterUserClicked: () -> Unit = {},
                    onConnectUserClicked: () -> Unit = {},
                    onPseudoTextChange: (String) -> Unit = {},
                    onPasswordTextChange: (String) -> Unit = {}
){
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ){
        Column(
            modifier = modifier.fillMaxWidth(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Logo(Modifier, 70f)

            Spacer(Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.login),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 24.sp
            )

            if (errorType != null){
                Spacer(Modifier.size(10.dp))

                Text(
                    text = stringResource(R.string.error)
                            + " "
                            + stringResource(getLocalizedErrorId(errorType))
                )

                Spacer(Modifier.size(10.dp))
            }
            else{
                Spacer(Modifier.size(20.dp))
            }

            UserInfosTextFields(
                modifier = Modifier.fillMaxWidth(),
                pseudo = pseudo,
                password = password,
                onPseudoTextChange = {
                    onPseudoTextChange(it)
                },
                onPasswordTextChange = {
                    onPasswordTextChange(it)
                }
            )

            Spacer(Modifier.height(20.dp))

            ConnectionButtons(
                modifier = Modifier.fillMaxWidth(),
                onConnectUserClicked = {
                    onConnectUserClicked()
                },
                onRegisterUserClicked = {
                    onRegisterUserClicked()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserInfosTextFields(
    modifier: Modifier = Modifier,
    pseudo: String = "Pseudo",
    password: String = "Password",
    onPseudoTextChange: (String) -> Unit = {},
    onPasswordTextChange: (String) -> Unit = {}
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        HeadedTextField(
            text = stringResource(R.string.pseudo),
            textField = pseudo,
            onTextChange = {
                onPseudoTextChange(it)
            }
        )

        Spacer(Modifier.height(20.dp))

        HeadedTextField(
            text = stringResource(R.string.password),
            textField = password,
            onTextChange = {
                onPasswordTextChange(it)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ConnectionButtons(
    modifier: Modifier = Modifier,
    onRegisterUserClicked: () -> Unit = {},
    onConnectUserClicked: () -> Unit = {}
){
    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        Button(
            modifier = Modifier.height(40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            ),
            onClick = {
                onRegisterUserClicked()
            }
        ) {
            Text(
                text = stringResource(R.string.login),
                fontSize = 16.sp
            )
        }

        // Connect Btn
        Button(
            modifier = Modifier.height(40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            ),
            onClick = {
                onConnectUserClicked()
            }
        ) {
            Text(
                text = stringResource(R.string.connect),
                fontSize = 16.sp
            )
        }
    }
}

fun getLocalizedErrorId(errorType: ErrorType): Int{

    return when{
        errorType == ErrorType.INVALID_CREDENTIALS -> R.string.error_invalid_credentials

        errorType == ErrorType.USER_ALREADY_EXISTS -> R.string.error_user_already_exist

        errorType == ErrorType.EMAIL_NOT_CONFIRMED -> R.string.error_email_not_confirmed

        errorType == ErrorType.INVALID_PASSWORD -> R.string.error_invalid_password

        errorType == ErrorType.NETWORK_ERROR -> R.string.error_network

        errorType == ErrorType.UNKNOWN_ERROR -> R.string.error_unknown

        else -> R.string.error_unknown
    }

}