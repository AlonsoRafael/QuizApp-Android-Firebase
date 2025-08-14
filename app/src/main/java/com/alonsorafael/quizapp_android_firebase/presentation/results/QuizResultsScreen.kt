package com.alonsorafael.quizapp_android_firebase.presentation.results

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alonsorafael.quizapp_android_firebase.domain.model.QuizAttempt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizResultsScreen(
    quizId: String,
    onNavigateToHome: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onRetakeQuiz: () -> Unit,
    viewModel: QuizResultsViewModel = hiltViewModel()
) {
    val attempt by viewModel.attempt.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(quizId) {
        viewModel.loadResults(quizId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quiz Results") },
                navigationIcon = {
                    IconButton(onClick = onNavigateToHome) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Error: $error",
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { viewModel.loadResults(quizId) }) {
                                Text("Retry")
                            }
                        }
                    }
                }
                attempt != null -> {
                    ResultsContent(
                        attempt = attempt!!,
                        onNavigateToHistory = onNavigateToHistory,
                        onRetakeQuiz = onRetakeQuiz
                    )
                }
            }
        }
    }
}

@Composable
fun ResultsContent(
    attempt: QuizAttempt,
    onNavigateToHistory: () -> Unit,
    onRetakeQuiz: () -> Unit
) {
    val scorePercentage = (attempt.score.toDouble() / attempt.maxScore.toDouble()) * 100
    val isPassed = scorePercentage >= 60.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Score display
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = if (isPassed) 
                    MaterialTheme.colorScheme.primaryContainer 
                else 
                    MaterialTheme.colorScheme.errorContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isPassed) "Congratulations!" else "Quiz Completed",
                    style = MaterialTheme.typography.headlineMedium,
                    color = if (isPassed) 
                        MaterialTheme.colorScheme.onPrimaryContainer 
                    else 
                        MaterialTheme.colorScheme.onErrorContainer
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "${attempt.score}/${attempt.maxScore}",
                    style = MaterialTheme.typography.displayMedium,
                    color = if (isPassed) 
                        MaterialTheme.colorScheme.onPrimaryContainer 
                    else 
                        MaterialTheme.colorScheme.onErrorContainer
                )
                
                Text(
                    text = "${String.format("%.1f", scorePercentage)}%",
                    style = MaterialTheme.typography.headlineSmall,
                    color = if (isPassed) 
                        MaterialTheme.colorScheme.onPrimaryContainer 
                    else 
                        MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Statistics
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Statistics",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                StatRow(
                    label = "Correct Answers",
                    value = "${attempt.correctAnswers}/${attempt.totalQuestions}",
                    icon = Icons.Default.CheckCircle
                )
                
                StatRow(
                    label = "Time Spent",
                    value = formatTime(attempt.timeSpent),
                    icon = Icons.Default.Timer
                )
                
                StatRow(
                    label = "Completion Date",
                    value = formatDate(attempt.endTime ?: attempt.startTime),
                    icon = Icons.Default.DateRange
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Action buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onRetakeQuiz,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Retake")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Retake Quiz")
            }
            
            OutlinedButton(
                onClick = onNavigateToHistory,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.History, contentDescription = "History")
                Spacer(modifier = Modifier.width(8.dp))
                Text("View History")
            }
        }
    }
}

@Composable
fun StatRow(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

private fun formatTime(timeInMillis: Long): String {
    val minutes = timeInMillis / (1000 * 60)
    val seconds = (timeInMillis % (1000 * 60)) / 1000
    return "${minutes}m ${seconds}s"
}

private fun formatDate(timestamp: Long): String {
    val date = java.util.Date(timestamp)
    val formatter = java.text.SimpleDateFormat("MMM dd, yyyy HH:mm", java.util.Locale.getDefault())
    return formatter.format(date)
}
