package com.pratik.demoapp.utils

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
actual fun PlatformMap() {
    val mumbai = LatLng(19.0760, 72.8777)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(mumbai, 8f)
    }

    val markers = listOf(
        LatLng(19.0760, 72.8777) to "Mumbai",
        LatLng(18.5204, 73.8567) to "Pune",
        LatLng(19.2183, 72.9781) to "Thane"
    )

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        markers.forEach { (position, title) ->
            Marker(
                state = MarkerState(position = position),
                title = title,
                snippet = "Marker in $title"
            )
        }
    }
}