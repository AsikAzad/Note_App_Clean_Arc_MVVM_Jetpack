package com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.util.NoteOrder
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.util.OrderType
import com.azad.note_app_clean_arc_mvvm_jetpack.util.TestTags

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {
    Column (
        modifier = modifier
    ){
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                modifier = Modifier
                    .testTag(TestTags.TITLE_RADIO_BUTTON)       //For UI testing
                    .selectable(                                //For UI testing
                        selected = noteOrder is NoteOrder.Title,
                        enabled = true,
                        role = Role.RadioButton,
                        onClick = { onOrderChange(NoteOrder.Title(noteOrder.orderType)) }
                    ),
                text = "Title",
                selected = noteOrder is NoteOrder.Title,
                onSelect = { onOrderChange(NoteOrder.Title(noteOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                modifier = Modifier
                    .testTag(TestTags.DATE_RADIO_BUTTON)        //For UI testing
                    .selectable(                                //For UI testing
                    selected = noteOrder is NoteOrder.Date,
                    enabled = true,
                    role = Role.RadioButton,
                    onClick = { onOrderChange(NoteOrder.Date(noteOrder.orderType)) }
                ),
                text = "Date",
                selected = noteOrder is NoteOrder.Date,
                onSelect = { onOrderChange(NoteOrder.Date(noteOrder.orderType)) })
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                modifier = Modifier
                    .testTag(TestTags.COLOR_RADIO_BUTTON)       //For UI testing
                    .selectable(                                //For UI testing
                        selected = noteOrder is NoteOrder.Color,
                        enabled = true,
                        role = Role.RadioButton,
                        onClick = { onOrderChange(NoteOrder.Color(noteOrder.orderType)) }
                    ),
                text = "Color",
                selected = noteOrder is NoteOrder.Color,
                onSelect = { onOrderChange(NoteOrder.Color(noteOrder.orderType)) })
        }
        Spacer(modifier = Modifier.width(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                modifier = Modifier
                    .testTag(TestTags.ASC_RADIO_BUTTON)         //For UI testing
                    .selectable(                                //For UI testing
                        selected = noteOrder.orderType is OrderType.Ascending,
                        enabled = true,
                        role = Role.RadioButton,
                        onClick = { onOrderChange(noteOrder.copy(OrderType.Ascending)) }
                    ),
                text = "Ascending",
                selected = noteOrder.orderType is OrderType.Ascending,
                onSelect = { onOrderChange(noteOrder.copy(OrderType.Ascending)) })
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                modifier = Modifier
                    .testTag(TestTags.DESC_RADIO_BUTTON)
                    .selectable(                                //For UI testing
                        selected = noteOrder.orderType is OrderType.Descending,
                        enabled = true,
                        role = Role.RadioButton,
                        onClick = { onOrderChange(noteOrder.copy(OrderType.Descending)) }
                    ),
                text = "Descending",
                selected = noteOrder.orderType is OrderType.Descending,
                onSelect = { onOrderChange(noteOrder.copy(OrderType.Descending)) })
        }
    }
}