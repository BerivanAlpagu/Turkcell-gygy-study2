package com.example.turkcellgygy

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun RegisterScreen( modifier: Modifier= Modifier,
        navController: NavHostController) {
        Column(modifier = modifier.fillMaxSize()) {
            Text("Merhaba")
            Button(onClick = {
                navController.navigate(Screen.Homepage.route)
            }) {
                Text("Ana Sayfaya Dön")
            }
        }

}