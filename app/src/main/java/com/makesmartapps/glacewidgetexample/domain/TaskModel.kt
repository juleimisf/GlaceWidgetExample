package com.makesmartapps.glacewidgetexample.domain

import com.makesmartapps.glacewidgetexample.presentation.ui.widget.StateTack

data class Task(
    val id: Int,
    val name: String,
    val date: String,
    val state: StateTack
)
