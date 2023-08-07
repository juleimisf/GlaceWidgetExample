package com.makesmartapps.glacewidgetexample

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {

    private val _tasks = generateTaskFake().toMutableStateList()

    val tasks: List<Task>
        get() = _tasks

    init {
        sortListByDate()
    }
    private fun sortListByDate() {
        tasks.sortedByDescending { it.date }
    }

     fun updateState(item: Task) {
        tasks.map { if (it.id == item.id) item else it }
    }

}

private fun generateTaskFake() =
    listOf(
        Task(
            0,
            "Corregir el diseño de la pantalla de inicio en diferentes dispositivos",
            "12-12-2013",
            StateTack.PENDING
        ),
        Task(
            1,
            "Implementar la funcionalidad de compartir contenido en redes sociales",
            "12-12-2013",
            StateTack.COMPLETED
        ),
        Task(
            3,
            "Actualizar las dependencias y bibliotecas del proyecto",
            "12-12-2013",
            StateTack.PENDING
        ),
        Task(
            4,
            "Colaborar en la revisión de código de otros desarrolladores",
            "12-12-2013",
            StateTack.COMPLETED
        )
    )
