package com.example.mobilecomputing.ui.maps

import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mobilecomputing.Graph
import com.example.mobilecomputing.data.Reminder
import com.example.mobilecomputing.ui.home.reminderManager.ReminderViewModel
import com.example.mobilecomputing.ui.home.reminderManager.ReminderViewState
import com.example.mobilecomputing.ui.home.reminderManager.Reminders
import com.example.mobilecomputing.util.rememberMapViewWithLifeCycle
import com.example.mobilecomputing.util.viewModelProviderFactoryOf
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.pow

@Composable
fun SearchRemindersMap (
    navController: NavController,
){
    val mapView = rememberMapViewWithLifeCycle()
    val coroutineScope = rememberCoroutineScope()

    val viewModel: ReminderViewModel = viewModel(
        factory = viewModelProviderFactoryOf { ReminderViewModel()}
    )
    val viewState by viewModel.state.collectAsState()
    val allReminders = viewState.reminders

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .padding(bottom = 36.dp)
    ) {
        AndroidView({mapView}) { mapView ->
            coroutineScope.launch {

                val map = mapView.awaitMap()
                map.uiSettings.isZoomControlsEnabled = true
                val location  = LatLng(20.0, 20.0)
                map.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(location, 15f)
                )

                setMapLongClick(map = map, navController = navController, allReminders)
            }
        }
    }
}

private fun setMapLongClick(
    map: GoogleMap,
    navController: NavController,
    reminders: List<Reminder>
) {
    map.setOnMapLongClickListener { latLng  ->
        val lat = latLng.latitude
        val long = latLng.longitude
        for (reminder in reminders){
            val startPoint = Location("locationA")
            startPoint.setLatitude(lat)
            startPoint.setLongitude(long)
            val endPoint = Location("locationA")
            endPoint.setLatitude(reminder.location_y)
            endPoint.setLongitude(reminder.location_x)
            val distance = startPoint.distanceTo(endPoint).toDouble()
            if (distance < 500){
                val snippet = String.format(
                    Locale.getDefault(),
                    "Lat: %1$.2f, Lng: %2$.2f",
                    reminder.location_y,
                    reminder.location_x
                )
                val reminderLatLng =  LatLng(reminder.location_y, reminder.location_x)
                map.addMarker(
                    MarkerOptions().position(reminderLatLng).title(reminder.message).snippet(snippet)
                )
            }
        }
    }
}

suspend fun <T> Flow<List<T>>.flattenToList() =
    flatMapConcat { it.asFlow() }.toList()
