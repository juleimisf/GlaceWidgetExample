package com.makesmartapps.glacewidgetexample.ui.theme

import com.makesmartapps.glacewidgetexample.StateTack

data class Task(
    val id: Int,
    val name: String,
    val date: String,
    val state: StateTack
)

data class StateTack(
    val type : String
)