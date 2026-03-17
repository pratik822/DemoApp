package com.pratik.demoapp.presentation.ui

import androidx.compose.runtime.*
import kotlinx.browser.document
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.css.*
import org.w3c.dom.HTMLDivElement

@Composable
actual fun MapScreen() {

    Div({
        id("map")
        style {
            width(100.percent)
            height(500.px)
        }
    })

    LaunchedEffect(Unit) {
        initMap()
    }
}

fun initMap() {

    val google = js("window.google")

    if (google == null) {
        println("Google Maps not loaded")
        return
    }

    val mapElement = document.getElementById("map") as? HTMLDivElement
    if (mapElement == null) {
        println("Map container not found")
        return
    }

    val center = js("{ lat: 19.0760, lng: 72.8777 }")

    val options = js("{}")
    options.center = center
    options.zoom = 10

    val map = js("new google.maps.Map(mapElement, options)")

    val markerOptions = js("{}")
    markerOptions.position = center
    markerOptions.map = map

    js("new google.maps.Marker(markerOptions)")
}