import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bhuppi.qbittorrentremote.presentation.auth.LoginViewModel

@Composable
fun LoginScreen (
    navController:NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.Center),
            onClick = { viewModel.login() }
        ) {
            Text(text = "Hello World")
        }
        if (state.errorMessage.isNotBlank()) {
            Text(
                text = state.errorMessage,
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }

        if (state.success != null) {
            navController.navigate("")
        }
    }
}