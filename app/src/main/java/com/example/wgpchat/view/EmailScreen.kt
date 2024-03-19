package com.example.wgpchat.view

import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.wgpchat.vmmv.ChatViewModel
import com.example.wgpchat.vmmv.MainViewModel

import androidx.compose.runtime.Composable
import androidx.navigation.navArgument
import com.example.wgpchat.NavRoutes
import com.example.wgpchat.model.Chat

@Composable
fun EmailScreen(navController: NavHostController, res:String?) {




    var resp = res.toString()

    var email by remember { mutableStateOf(TextFieldValue("")) }
    var emailSubject by remember { mutableStateOf(TextFieldValue("")) }
    var emailBody by remember { mutableStateOf(TextFieldValue(resp)) }


    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Account") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = emailSubject,
            onValueChange = { emailSubject = it },
            label = { Text("Email Subject") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = emailBody,
            onValueChange = { emailBody = it },
            label = { Text("Email Body") },
            maxLines = 10
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                context.sendMail(to = email.text, subject = emailSubject.text, body = emailBody.text)
                navController.navigate(NavRoutes.Chat.route)
            }
        ) {
            Text("Send Email")
        }
    }
}


fun Context.sendMail(to: String, subject: String, body: String ) {
    try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "vnd.android.cursor.item/email" // or "message/rfc822"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, body)
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        // TODO: Handle case where no email app is available
    } catch (t: Throwable) {
        // TODO: Handle potential other type of exceptions
    }
}
