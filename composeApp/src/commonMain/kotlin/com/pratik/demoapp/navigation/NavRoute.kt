package com.pratik.demoapp.navigation

import kotlinx.serialization.Serializable
import androidx.navigation3.runtime.NavKey
import com.pratik.demoapp.utils.NewsList

@Serializable
class Dashboard() : NavKey{

}
@Serializable
class Home(val item: String) : NavKey

@Serializable
data class Details(val post: com.pratik.demoapp.core.utils.NewsList) : NavKey