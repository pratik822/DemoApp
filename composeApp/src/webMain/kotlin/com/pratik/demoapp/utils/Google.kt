package com.pratik.demoapp.utils

external object google {
    object maps {

        class Map(
            element: dynamic,
            options: dynamic
        )

        class LatLng(
            lat: Double,
            lng: Double
        )

        class Marker(
            options: dynamic
        )
    }
}