package com.example.wgpchat.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.wgpchat.NavRoutes


@Composable
fun Home(navController:NavHostController){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter){
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top = 8.dp)) {
            Text("Welcome", style = MaterialTheme.typography.h5)
            
           // Spacer(modifier = Modifier.size(30.dp))


            LazyColumn {
                item {
                    WelcomeLetter("")
                }
            }


            Button(onClick = {navController.navigate(NavRoutes.Chat.route)}) {
                Text(text="Chat with GPT")
            }
        }

    }
}

@Composable
fun WelcomeLetter(name: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        //  Text("Dear $name,")
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            "I would like to introduce you to GPT-ChatApp, a powerful language model based on the GPT-3.5 architecture. With GPT-ChatApp, you can generate human-like language, making it an ideal tool for communication and assistance in various fields."
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "GPT-ChatApp has been trained on a vast corpus of texts, allowing it to understand and generate responses to a wide range of topics and questions. It is also designed to be user-friendly and accessible to everyone, making it easy to integrate into various platforms, including websites, mobile apps, and messaging services."
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "I invite you to try out GPT-ChatApp and experience its many benefits firsthand. Whether you're looking for quick answers or a stimulating conversation, GPT-ChatApp is always ready to help."
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Thank you for considering GPT-ChatApp, and I look forward to hearing about your experience with this incredible technology."
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Best regards,")
        Text("The programmer")
    }
}