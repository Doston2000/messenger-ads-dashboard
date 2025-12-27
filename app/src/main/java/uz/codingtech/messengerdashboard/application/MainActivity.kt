package uz.codingtech.messengerdashboard.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.collection.orderedScatterSetOf
import androidx.core.view.WindowCompat
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import uz.codingtech.messengerdashboard.presentation.main_app.MainApp
import uz.codingtech.messengerdashboard.presentation.ui.theme.MessengerDashboardTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MessengerDashboardTheme {
                MainApp()
            }
        }
    }
}