package com.example.wgpchat.view

import android.app.Application
import android.content.Context


import android.speech.tts.TextToSpeech

import android.widget.Toast
import androidx.compose.foundation.horizontalScroll

import androidx.compose.foundation.layout.*



import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Send

import androidx.compose.material.Icon


import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wgpchat.vmmv.ChatViewModel


import androidx.compose.foundation.layout.Column


import androidx.compose.material.*

import androidx.compose.runtime.*

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import com.example.wgpchat.MainActivity.Companion.speechRecognizer
import com.example.wgpchat.MainActivity.Companion.speechRecognizerIntent
import com.example.wgpchat.MainActivity.Companion.textToSpeech
import com.example.wgpchat.R
import com.example.wgpchat.model.Chat
import com.example.wgpchat.vmmv.MainViewModel
import java.util.Date




import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.wgpchat.NavRoutes
import kotlinx.coroutines.launch
import androidx.compose.runtime.LaunchedEffect as LaunchedEffect1


@Composable
fun Chat(navController: NavHostController  ,chatViewModel: ChatViewModel= viewModel()){

    val vm: MainViewModel = MainViewModel(application = Application())


    var gptResponse = remember { mutableStateOf("") }
    var myChat = remember { mutableStateOf("") }

    val ctx = LocalContext.current
    // val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val onUserNameChange = { text: String -> gptResponse.value = text }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {


        Column(horizontalAlignment = Alignment.Start) {

            Row() {

                CustomTextField(
                    title = "Ask anything ...",
                    textState = gptResponse.value,
                    onTextChange = onUserNameChange
                )

                // Spacer(modifier = Modifier.size(30.dp))

                Button(modifier = Modifier.padding(top = 20.dp), onClick = {


                    if(gptResponse.value== ""){
                        gptResponse.value = "How to use GPT?"
                        Toast.makeText(ctx,"Should enter a text", Toast.LENGTH_LONG).show()

                    }

                    myChat.value = gptResponse.value

                    coroutineScope.launch {
                        chatViewModel.sendRequest(question = myChat.value)
                    }




                }, enabled = !chatViewModel.isLoading.value)

                {
                    Icon(Icons.Filled.Send, contentDescription = null)
                }

                Spacer(modifier = Modifier.size(50.dp))


            }
            Row(horizontalArrangement =Arrangement.Center) {

                TextToSpeechExample(chatViewModel.answer.value)


                Button(modifier= Modifier
                    .padding(start = 10.dp)
                    .width(80.dp)
                    .height(40.dp),onClick = {


                    if(gptResponse.value== ""){
                        gptResponse.value = "Translate Deberias entrar un texto in English"
                        Toast.makeText(ctx,"Should enter a text", Toast.LENGTH_LONG).show()

                    }else {

                        val date = Date().toString()

                        val chat: Chat =
                            Chat(myChat.value, chatViewModel.answer.value, date, true)

                        vm.insertChat(chat)

                        Toast.makeText(
                            ctx,
                            "Your request was saved successfully in the History",
                            Toast.LENGTH_LONG
                        ).show()
                        onUserNameChange("")
                        chatViewModel.answer.value = ""
                    }
                }, enabled = gptResponse.value != "") {
                    Text("Save")
                }


                Button(modifier= Modifier
                    .padding(start = 10.dp)
                    .width(70.dp)
                    .height(40.dp),onClick = {

                    val res = gptResponse.value + chatViewModel.answer.value

                    navController.navigate(NavRoutes.SendByEmail.route+"/$res")
                   
                },enabled = gptResponse.value != "") {
                    // Text(text = "Email", style = TextStyle(fontSize = 14.sp))

                    Icon(Icons.Filled.Email, contentDescription = null)
                }





                Button(modifier= Modifier
                    .padding(start = 10.dp)
                    .width(80.dp)
                    .height(40.dp),onClick = {
                    onUserNameChange("")
                    chatViewModel.answer.value = ""

                }, enabled = gptResponse.value != "") {
                    Icon(Icons.Filled.Delete, contentDescription = null)
                }

            }



            Row(Modifier.padding(10.dp)) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (chatViewModel.isLoading.value) {
                        CircularProgressIndicator(Modifier.padding(150.dp))
                    }
                }


                LazyColumn {
                    item {
                        Text(text = chatViewModel.answer.value)
                    }
                }

            }

        }

    }
}


@Composable
fun CustomTextField(title:String, textState:String, onTextChange:(String)->Unit,){

    OutlinedTextField(value = textState, onValueChange = {onTextChange(it)}, singleLine = true,
        label = {Text(title)},
        modifier = Modifier
            .padding(10.dp)
            .width(290.dp),
        textStyle = TextStyle(fontWeight = FontWeight.Normal, fontSize = 18.sp)

    )
}

@Composable
fun TextToSpeechExample(text: String) {
    val ctx = LocalContext.current

    val iconHear = painterResource(id = R.drawable.ic_hearing_foreground)

    Button(modifier= Modifier
        .padding(start = 10.dp)
        .width(80.dp)
        .height(40.dp), onClick = {

        if(text == ""){
            Toast.makeText(ctx,"anything to speak", Toast.LENGTH_LONG).show()
        }

        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }, enabled = text != "") {

        // Icon(painter = iconHear, contentDescription = null)
        Text("Speak")

    }
}



@Composable
fun SpeechToTextExample() {

    Button(onClick = {
        speechRecognizer.startListening(speechRecognizerIntent)
    }) {
        Text("Click to Start listening")
    }

}
