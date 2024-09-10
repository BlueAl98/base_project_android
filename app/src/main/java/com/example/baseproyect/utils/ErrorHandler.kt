package com.example.baseproyect.utils


import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.baseproyect.App
import com.example.baseproyect.R
import com.example.baseproyect.data.interceptor.HttpError
import com.example.baseproyect.data.interceptor.UnprocessableEntity
import com.example.baseproyect.presentation.composables.ErrorDialog
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLPeerUnverifiedException

object ErrorHandler {

    @Composable
    fun ParseError(titleMessage: String = stringResource(id = R.string.title_error), onDismiss: () -> Unit, e: Throwable){
        if (e is HttpException) {
            when (e.code()) {
                401, 400, 403, 404, 405, 423, 429 -> {
                    val error: HttpError? = HttpError.parseException(e)
                    if (error != null) {
                        ErrorDialog(titleMessage = titleMessage, errorMessage = error.error ?: "Error desconocido", onDismiss = onDismiss)
                    }
                }
                422 -> {
                    val errors = UnprocessableEntity.parseException(e)
                    ErrorDialog(titleMessage = titleMessage, errorMessage = errors!!.errors[0], onDismiss = onDismiss)
                }
                500 -> ErrorDialog(titleMessage = titleMessage, onDismiss = onDismiss)
            }
        } else if (e is IOException) {
            when (e) {
                is ConnectException ->
                    ErrorDialog(titleMessage = titleMessage, errorMessage = App.shareInstance!!.resources.getString(R.string.unreachable_network), onDismiss = onDismiss)
                is SocketException ->
                    ErrorDialog(titleMessage = titleMessage, errorMessage = App.shareInstance!!.resources.getString(R.string.unreachable_network), onDismiss = onDismiss)
                is SocketTimeoutException ->
                    ErrorDialog(titleMessage = titleMessage, errorMessage = App.shareInstance!!.resources.getString(R.string.timeout), onDismiss = onDismiss)
                is UnknownHostException ->
                    ErrorDialog(titleMessage = titleMessage, errorMessage = App.shareInstance!!.resources.getString(R.string.unknown_host_exception), onDismiss = onDismiss)
                is SSLPeerUnverifiedException ->
                    ErrorDialog(titleMessage = titleMessage, errorMessage = App.shareInstance!!.resources.getString(R.string.certificate_error_message), onDismiss = onDismiss)
                else ->
                    ErrorDialog(titleMessage = titleMessage, errorMessage = App.shareInstance!!.resources.getString(R.string.unexpected_error), onDismiss = onDismiss)
            }
        }
    }
}

