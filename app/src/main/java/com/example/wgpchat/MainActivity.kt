package com.example.wgpchat

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState


import androidx.navigation.compose.rememberNavController
import com.example.wgpchat.ui.theme.WGPchatTheme

import com.example.wgpchat.vmmv.ChatViewModel


import android.content.Context
import android.content.Intent
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wgpchat.MainActivity.Companion.textToSpeech
import com.example.wgpchat.model.Chat
import com.example.wgpchat.model.ChatDao
import com.example.wgpchat.model.ChatRepository
import com.example.wgpchat.view.*
import com.example.wgpchat.vmmv.MainViewModel

import java.util.*



sealed class NavRoutes(val route:String){
    object Home:NavRoutes("home")
    object Chat:NavRoutes("chat")
    object ChatHistory:NavRoutes("history")
    object SendByEmail:NavRoutes("sendByEmail")
}



class MainActivity : ComponentActivity() {


    companion object {
        lateinit var textToSpeech: TextToSpeech
        lateinit var speechRecognizer: SpeechRecognizer
        val speechRecognizerIntent by lazy {
            Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true)
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
             // Text to Speak
        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = textToSpeech.setLanguage(Locale.US)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language is not supported")
                }
            } else {
                Log.e("TTS", "Initialization failed")
            }
        }


                 // Speak to text

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onError(error: Int) {}
            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (matches != null && matches.isNotEmpty()) {
                    // Handle the recognized text here
                }
            }
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        speechRecognizer.startListening(speechRecognizerIntent)





        setContent {
            WGPchatTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    
                    val owner = LocalViewModelStoreOwner.current
                    
                    owner?.let { 
                        val viewModel:MainViewModel = viewModel(it,"MainViewModel",MainViewModelFactory(
                            LocalContext.current.applicationContext as Application
                          )
                        )

                        MainScreen(viewModel)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.stop()
        textToSpeech.shutdown()

        speechRecognizer.stopListening()
        speechRecognizer.destroy()

    }

    class MainViewModelFactory(val application: Application):ViewModelProvider.Factory{
        override fun<T:ViewModel> create(modelClass:Class<T>):T{
            return MainViewModel(application) as T
        }
    }


}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: MainViewModel){

    val navController = rememberNavController()

   // val vm: MainViewModel = viewModel



    Scaffold(topBar = { TopAppBar(title = { Text("GPT-ChatApp") })},
    content = { NavigationHost(navController = navController)},
        bottomBar = { BottomNavigationBar(navController = navController)}
        )
    

}

@Composable
fun NavigationHost(navController: NavHostController){

    NavHost(navController = navController,  startDestination = NavRoutes.Home.route ){


        composable(NavRoutes.Home.route){
           Home(navController = navController)
        }

        composable(NavRoutes.Chat.route){
           Chat(navController=navController)
        }

        composable(NavRoutes.ChatHistory.route){
            History(navController)
        }

        composable(NavRoutes.SendByEmail.route+"/{res}"){

                backStackEntry ->

                 val res = backStackEntry.arguments?.getString("res")

                EmailScreen(navController=navController,  res)


        }
    }
}


@Composable
fun BottomNavigationBar(navController: NavHostController){

    BottomNavigation {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry.value?.destination


        val iconChat = painterResource(id = R.drawable.ic_chat)
        val iconChatHistory = painterResource(id = R.drawable.ic_chat_history)


        BottomNavigationItem(selected = currentDestination?.route == NavRoutes.Home.route, onClick =  { navController.navigate(NavRoutes.Home.route){
            popUpTo(NavRoutes.Home.route)
            launchSingleTop = true
        } },
           icon = {Icon(Icons.Filled.Home, contentDescription = null)},
            label = {Text(text=NavRoutes.Home.route)}
            )

        BottomNavigationItem(selected = currentDestination?.route == NavRoutes.Chat.route, onClick =  { navController.navigate(NavRoutes.Chat.route){
            popUpTo(NavRoutes.Chat.route)
            launchSingleTop = true
        } },
            icon = {Icon(painter = iconChat, contentDescription = null)},
            label = {Text(text=NavRoutes.Chat.route)}
        )


        BottomNavigationItem(selected = currentDestination?.route == NavRoutes.ChatHistory.route, onClick =  { navController.navigate(NavRoutes.ChatHistory.route){
            popUpTo(NavRoutes.ChatHistory.route)
            launchSingleTop = true
        } },
            icon = {Icon(painter = iconChatHistory, contentDescription = null)},
            label = {Text(text=NavRoutes.ChatHistory.route)}
        )

    }
}


