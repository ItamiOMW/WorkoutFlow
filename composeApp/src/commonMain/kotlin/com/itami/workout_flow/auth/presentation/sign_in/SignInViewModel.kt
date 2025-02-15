package com.itami.workout_flow.auth.presentation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.workout_flow.auth.domain.repository.AuthRepository
import com.itami.workout_flow.core.domain.model.result.onError
import com.itami.workout_flow.core.domain.model.result.onSuccess
import com.itami.workout_flow.core.presentation.utils.toStringRes
import com.mmk.kmpauth.google.GoogleAuthProvider
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.error_unknown
import workoutflow.composeapp.generated.resources.sign_in_successful

class SignInViewModel(
    // DO NOT REMOVE: GoogleAuthProvider instance must be created before signing in with Google.
    private val googleAuthProvider: GoogleAuthProvider,
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _events = Channel<SignInEvent>()
    val events = _events.receiveAsFlow()

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private var signInJob: Job? = null

    fun onAction(action: SignInAction) {
        when (action) {
            is SignInAction.NavigateBackClick -> {
                sendUiEvent(SignInEvent.NavigateBack)
            }

            is SignInAction.SignInResult -> {
                signIn(action.firebaseUserResult)
            }
        }
    }

    private fun signIn(firebaseUserResult: Result<FirebaseUser?>) {
        signInJob?.cancel()
        signInJob = viewModelScope.launch {
            if (firebaseUserResult.isFailure) {
                val errorMessage = getString(Res.string.error_unknown)
                sendUiEvent(SignInEvent.ShowLocalSnackbar(errorMessage))
            }

            if (firebaseUserResult.isSuccess) {
                _state.update { it.copy(isLoading = true) }
                authRepository.authenticate()
                    .onSuccess {
                        sendUiEvent(
                            SignInEvent.ShowGlobalSnackbar(
                                getString(resource = Res.string.sign_in_successful)
                            )
                        )
                        sendUiEvent(SignInEvent.NavigateBack)
                    }
                    .onError { error ->
                        val errorMessage = getString(error.toStringRes())
                        sendUiEvent(SignInEvent.ShowLocalSnackbar(errorMessage))
                    }
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun sendUiEvent(event: SignInEvent) {
        viewModelScope.launch {
            _events.send(event)
        }
    }

}
