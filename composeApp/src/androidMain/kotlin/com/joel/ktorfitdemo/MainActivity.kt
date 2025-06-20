package com.joel.ktorfitdemo

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.joel.ktorfitdemo.core.di.initApplication
import org.koin.android.ext.koin.androidContext

class ComposeApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initApplication {
            androidContext(this@ComposeApp)
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}