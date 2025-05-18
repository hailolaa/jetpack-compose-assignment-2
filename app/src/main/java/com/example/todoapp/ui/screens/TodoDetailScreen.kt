package com.example.todoapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todoapp.data.model.Todo
import com.example.todoapp.ui.viewmodel.TodoDetailViewModel
import androidx.compose.animation.core.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailScreen(
    viewModel: TodoDetailViewModel,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                uiState.error != null -> {
                    Text(
                        text = "Error: ${uiState.error}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }
                uiState.todo != null -> {
                    TodoDetailContent(todo = uiState.todo!!)
                }
            }
        }
    }
}

@Composable
fun TodoDetailContent(todo: Todo) {
    // Theme-based colors
    val isDarkTheme = isSystemInDarkTheme()
    val cardBackgroundColor = if (isDarkTheme) {
        Color(0xDD184875)
    } else {
        Color(0xFF616161)
    }
    val textColor = if (isDarkTheme) {
        Color.White
    } else {
        Color.Black
    }

    // Animation states
    val visible = remember { mutableStateOf(false) }
    val alpha = animateFloatAsState(
        targetValue = if (visible.value) 1f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "alphaAnimation"
    )
    val slideOffset = animateDpAsState(
        targetValue = if (visible.value) 0.dp else 20.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "slideAnimation"
    )

    // Trigger animation when the component is first composed
    LaunchedEffect(key1 = true) {
        visible.value = true
    }

    // Background color for the screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(alpha.value)
                .offset(y = slideOffset.value),
            colors = CardDefaults.cardColors(
                containerColor = cardBackgroundColor
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Todo #${todo.id}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Title",
                    style = MaterialTheme.typography.labelLarge,
                    color = textColor
                )
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Status",
                    style = MaterialTheme.typography.labelLarge,
                    color = textColor
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    if (todo.completed) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Completed",
                            tint = Color(0xDD199D16)
                        )
                    }
                    Text(
                        text = if (todo.completed) "Completed" else "Not Completed",
                        modifier = Modifier.padding(start = if (todo.completed) 32.dp else 0.dp),
                        color = if (todo.completed) Color(0xDD199D16) else Color.Red,
                    )
                }

                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = textColor.copy(alpha = 0.5f)
                )

                Text(
                    text = "User ID",
                    style = MaterialTheme.typography.labelLarge,
                    color = textColor
                )
                Text(
                    text = todo.userId.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = textColor
                )
            }
        }
    }
}