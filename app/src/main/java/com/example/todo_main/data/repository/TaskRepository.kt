package com.example.todo_main.data.repository

import com.example.todo_main.data.model.Task
import com.example.todo_main.data.model.User
import com.example.todo_main.util.UiState

interface TaskRepository {
    fun addTask(task: Task, result: (UiState<Pair<Task,String>>) -> Unit)
    fun updateTask(task: Task, result: (UiState<Pair<Task,String>>) -> Unit)
    fun deleteTask(task: Task, result: (UiState<Pair<Task,String>>) -> Unit)
    fun getTask(id: String, result: (UiState<Pair<Task,String>>) -> Unit)
    fun getTasks(user: User?, result: (UiState<List<Task>>) -> Unit)
    fun storeTasks(tasks: List<Task>, result: (UiState<String>) -> Unit)
}