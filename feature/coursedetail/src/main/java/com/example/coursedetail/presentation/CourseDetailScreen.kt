package com.example.coursedetail.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.example.coursedetail.presentation.components.CourseDetailContent
import com.example.coursedetail.presentation.components.ErrorContent
import com.example.coursedetail.presentation.components.LoadingContent
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CourseDetailScreen(
    courseId: Int,
    onNavigateBack:()-> Unit,
    viewModel: CourseDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current


    LaunchedEffect(viewModel.effect, lifecycleOwner) {
        viewModel.effect.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .collectLatest { effect ->
                when (effect) {
                    is CourseDetailEffect.ShowMessage -> {
                        snackbarHostState.showSnackbar(effect.message)
                    }
                    is CourseDetailEffect.ShareCourse -> {
                        snackbarHostState.showSnackbar("Поделиться: ${effect.title}")
                    }
                }
            }
    }

    Scaffold(

        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {paddingValues ->
        when {
            state.isLoading -> {
                LoadingContent()
            }

            state.error != null -> {
                ErrorContent(
                    error = state.error!!,
                    onRetry = { viewModel.handleEvent(CourseDetailEvent.Retry) }
                )
            }

            state.course != null -> {

                Log.d("CourseDetailScreen", state.course!!.title.toString())
                CourseDetailContent(
                    course = state.course!!,
                    onNavigateBack = onNavigateBack
                )
            }
        }
    }
}