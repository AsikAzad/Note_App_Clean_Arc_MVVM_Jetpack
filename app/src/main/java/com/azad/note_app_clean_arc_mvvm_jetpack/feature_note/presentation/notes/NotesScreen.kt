package com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.presentation.notes

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.presentation.notes.components.NoteItem
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.presentation.notes.components.OrderSection
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.presentation.util.Screen
import com.azad.note_app_clean_arc_mvvm_jetpack.util.TestTags
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

   Scaffold(
//       backgroundColor = Color.White,
       floatingActionButton = {
           FloatingActionButton(
               onClick = {
                    navController.navigate(Screen.AddEditNoteScreen.route)
               },
               backgroundColor = colors.surface
           ){
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note",
                tint = colors.background)
           }
       },
       scaffoldState = scaffoldState
   ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
//                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.background)
                    .padding(vertical = 8.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "My Notes",
                    style = MaterialTheme.typography.h4,
                    color = colors.surface
                )
                IconButton(
                    onClick = {
                        viewModel.onEvent(NotesEvent.ToggleOrderSection)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Sort",
                        tint = colors.surface
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .background(colors.background)
                        .testTag(TestTags.ORDER_SECTION),
                    noteOrder = state.noteOrder,
                    onOrderChange = {
                        viewModel.onEvent(NotesEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()){
                items(state.notes){ note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                               navController.navigate(
                                   route = Screen.AddEditNoteScreen.route + "?noteId=${note.id}&noteColor=${note.color}"
                               )
                            },
                        onDeleteClick = {
                            viewModel.onEvent(NotesEvent.DeleteNote(note))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Note deleted",
                                    actionLabel = "Undo"
                                )
                                if(result == SnackbarResult.ActionPerformed){
                                    viewModel.onEvent(NotesEvent.RestoreNote)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
   }
}