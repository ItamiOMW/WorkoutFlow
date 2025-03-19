package com.itami.workout_flow.core.presentation.components.text_field

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FloatTextField(
    modifier: Modifier = Modifier,
    value: Float?,
    onValueChange: (Float?) -> Unit,
    textStyle: TextStyle = WorkoutFlowTheme.typography.bodySmall,
    textFieldColors: TextFieldColors = TextFieldDefaults.colors(
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        cursorColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
        focusedTextColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
        unfocusedTextColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
    ),
    singleLine: Boolean = true,
    enabled: Boolean = true,
    cursorBrush: Brush = Brush.linearGradient(
        colors = listOf(
            WorkoutFlowTheme.colors.surfaceColors.onSurface,
            WorkoutFlowTheme.colors.surfaceColors.onSurface
        )
    ),
    contentPadding: PaddingValues = PaddingValues(WorkoutFlowTheme.padding.extraSmall),
    interactionSource: InteractionSource = remember { MutableInteractionSource() },
) {
    val focusManager = LocalFocusManager.current
    BasicTextField(
        modifier = modifier,
        value = value?.toString() ?: "",
        onValueChange = { newText ->
            val parsedValue = newText.toFloatOrNull()
            onValueChange(parsedValue)
        },
        textStyle = textStyle,
        cursorBrush = cursorBrush,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        singleLine = singleLine,
        decorationBox = { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = value?.toString() ?: "",
                singleLine = singleLine,
                colors = textFieldColors,
                innerTextField = innerTextField,
                enabled = enabled,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                contentPadding = contentPadding,
            )
        }
    )
}