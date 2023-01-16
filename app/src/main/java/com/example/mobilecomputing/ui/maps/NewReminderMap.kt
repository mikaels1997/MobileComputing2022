package com.example.mobilecomputing.ui.maps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.mobilecomputing.util.rememberMapViewWithLifeCycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun ReminderLocationMap (
    navController: NavController,
){
    val mapView = rememberMapViewWithLifeCycle()
    val coroutineScope = rememberCoroutineScope()

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

                setMapLongClick(map = map, navController = navController)
            }
        }
    }
}

private fun setMapLongClick(
    map: GoogleMap,
    navController: NavController,
) {
    map.setOnMapLongClickListener { latLng  ->
        val snippet = String.format(
            Locale.getDefault(),
            "Lat: %1$.2f, Lng: %2$.2f",
            latLng.latitude,
            latLng.longitude
        )

        map.addMarker(
            MarkerOptions().position(latLng).title("Reminder location").snippet(snippet)
        ).apply {
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("location_data", latLng)
            navController.popBackStack()
        }
    }
}