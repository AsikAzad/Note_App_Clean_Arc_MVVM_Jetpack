package com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.presentation.notes.components

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.util.OrderType

@Composable
fun DefaultRadioButton (
    text: String,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colors.onBackground,
                unselectedColor = MaterialTheme.colors.surface
            ),
            modifier = Modifier
                .selectable(                                //For UI testing
                    selected = selected,
                    enabled = true,
                    role = Role.RadioButton,
                    onClick = onSelect
                )
                .semantics {
                contentDescription = text
                }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.surface
        )
    }
}