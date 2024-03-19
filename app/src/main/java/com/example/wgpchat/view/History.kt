package com.example.wgpchat.view

import android.app.Application
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.wgpchat.MainActivity
import com.example.wgpchat.NavRoutes
import com.example.wgpchat.R
import com.example.wgpchat.model.Chat

import com.example.wgpchat.vmmv.MainViewModel




@Composable
fun History(navController: NavHostController){

    val vm:MainViewModel = MainViewModel(application = Application())


    var chatList:List<Chat> = remember{ mutableStateListOf<Chat>() }



    LaunchedEffect(Unit, block ={
         vm.getChatRequestList()
          chatList = vm.allRequestsList
    } )





    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Chat History", style = MaterialTheme.typography.h5)
            Text("Chats are stored in your device.", style=TextStyle(fontSize = 14.sp, fontStyle = FontStyle.Italic, fontWeight = FontWeight.ExtraLight))

            Spacer(modifier = Modifier.height(30.dp))

            LazyColumn {
                items(vm.allRequestsList) { chat ->
                    // Display each chat in a Card
                    Card(
                        modifier = Modifier
                            .padding(16.dp)
                            .width(500.dp),
                        elevation = 4.dp,
                     //   backgroundColor = Color.White
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            ChatIcon(imageVector = Icons.Default.Favorite, chat.id, chat.isFavorite, isVisible = chat.isFavorite, navController)

                            Text(text = "Id: ${chat.id}", style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Light), modifier = Modifier.padding(start=2.dp, top=0.dp))

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(text = "Me: ${chat.request}", style = TextStyle(fontWeight = FontWeight.Bold))

                            Text(text = "Date: ${chat.chatDate}", style = TextStyle(fontSize = 12.sp, fontStyle = FontStyle.Italic))

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(text = "Assistant: ${chat.response}", style = TextStyle(fontStyle = FontStyle.Italic, fontWeight = FontWeight.SemiBold))
                        //    Text(text = "isFavorite: ${chat.isFavorite}")


                            Spacer(modifier = Modifier.height(26.dp))

                            Row() {

                                Button(modifier = Modifier.padding(5.dp).width(60.dp).height(40.dp), onClick = {
                                    vm.deleteChat(chat.id)
                                    navController.navigate(NavRoutes.Chat.route)

                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = null)
                                }

                                Spacer(modifier = Modifier.width(25.dp))

                                TextToSpeechHistory(chat.request, "User")
                                TextToSpeechHistory(chat.response, "Assistant")


                            }
                        }
                    }
                }
            }

        }

    }
}


@Composable
fun TextToSpeechHistory(text: String, speaker:String) {
    val ctx = LocalContext.current

    //val iconHear = painterResource(id = R.drawable.ic_hearing_foreground)

    Button(modifier= Modifier
        .padding(5.dp)
        .width(110.dp)
        .height(40.dp), onClick = {

        if(text == ""){
            Toast.makeText(ctx,"anything to speak", Toast.LENGTH_LONG).show()
        }

        MainActivity.textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }) {

        Text(speaker)

    }
}


@Composable
fun ChatIcon(imageVector: ImageVector,id:Int, isFavorite:Boolean, isVisible: Boolean, navController: NavHostController) {

    val vm:MainViewModel = MainViewModel(application = Application())

    if (isVisible) {

            Icon(imageVector = imageVector, contentDescription = null, tint = Color.Red,modifier = Modifier
                .padding(start = 260.dp)
                .width(48.dp)
                .clickable {
                    vm.updateFavorite(id, !isFavorite)
                    navController.navigate(NavRoutes.Chat.route)
                })

    } else {
        Icon(imageVector = imageVector, contentDescription = null, tint = Color.Gray,modifier = Modifier
            .padding(start = 260.dp)
            .width(48.dp)
            .clickable {
                vm.updateFavorite(id, !isFavorite)
                navController.navigate(NavRoutes.Chat.route)
            })
    }
}































@Composable
fun TitleRow(head1:String, head2:String, head3:String, head4:String){

    Row(
         modifier = Modifier
             .background(MaterialTheme.colors.primary)
             .fillMaxWidth()
             .padding(5.dp)){

             Text(head1,color = Color.White, modifier = Modifier.weight(0.1f))

             Text(head2, color = Color.White, modifier = Modifier.weight(0.1f))

             Text(head3, color = Color.White, modifier = Modifier.weight(0.1f))

             Text(head3, color = Color.White, modifier = Modifier.weight(0.1f))

        }
}

@Composable
fun ChatRow(id:Int,  request:String, response:String, created_Date:String){


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ){
        Text(id.toString(), modifier = Modifier.weight(0.1f))

        Text(request, modifier = Modifier.weight(0.2f))

        Text(response, modifier = Modifier.weight(0.2f))

        Text(created_Date, modifier = Modifier.weight(0.2f))
    }
}



@Composable
fun CustomTextField(
    title:String,
    textState:String,
    onTextChange:(String) -> Unit,
    keyboardType: KeyboardType
){
    OutlinedTextField(value = textState, onValueChange = {onTextChange(it)},
    keyboardOptions = KeyboardOptions(
        keyboardType = keyboardType
    ),
        singleLine = true,
        label = { Text(title)},
        modifier = Modifier.padding(10.dp),
        textStyle = TextStyle(fontWeight = FontWeight.Bold)
        )
}




