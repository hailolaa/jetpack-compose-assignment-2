package com.example.todoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todoapp.ui.screens.TodoDetailScreen
import com.example.todoapp.ui.screens.TodoListScreen
import com.example.todoapp.ui.viewmodel.TodoDetailViewModel
import com.example.todoapp.ui.viewmodel.TodoListViewModel

object Destinations {
    const val TODO_LIST = "todoList"
    const val TODO_DETAIL = "todoDetail/{todoId}"

    fun todoDetailRoute(todoId: Int) = "todoDetail/$todoId"
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.TODO_LIST
    ) {
        composable(Destinations.TODO_LIST) {
            val viewModel: TodoListViewModel = hiltViewModel()
            TodoListScreen(
                viewModel = viewModel,
                onTodoClick = { todoId ->
                    navController.navigate(Destinations.todoDetailRoute(todoId))
                }
            )
        }

        composable(
            route = Destinations.TODO_DETAIL,
            arguments = listOf(
                navArgument("todoId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getInt("todoId") ?: 0
            val viewModel: TodoDetailViewModel = hiltViewModel()
            // You must pass the todoId to the viewmodel, see below!
            TodoDetailScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}