package com.itami.workout_flow.auth.presentation.sign_in.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.apple_icon
import workoutflow.composeapp.generated.resources.continue_with_apple
import workoutflow.composeapp.generated.resources.continue_with_google
import workoutflow.composeapp.generated.resources.google_icon
import workoutflow.composeapp.generated.resources.icon_apple
import workoutflow.composeapp.generated.resources.icon_google

@Composable
internal fun GoogleSignInButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        contentPadding = PaddingValues(vertical = WorkoutFlowTheme.padding.small),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp),
        shape = WorkoutFlowTheme.shapes.small,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = WorkoutFlowTheme.colors.surfaceColors.surfaceHigh,
            contentColor = WorkoutFlowTheme.colors.surfaceColors.onSurface
        )
    ) {
        Icon(
            painter = painterResource(Res.drawable.icon_google),
            contentDescription = stringResource(Res.string.google_icon),
            tint = Color.Unspecified,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(WorkoutFlowTheme.padding.extraSmall))
        Text(
            text = stringResource(Res.string.continue_with_google),
            style = WorkoutFlowTheme.typography.labelMedium,
        )
    }
}

@Composable
internal fun AppleSignInButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        contentPadding = PaddingValues(vertical = WorkoutFlowTheme.padding.small),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp),
        shape = WorkoutFlowTheme.shapes.small,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = WorkoutFlowTheme.colors.surfaceColors.surfaceHigh,
            contentColor = WorkoutFlowTheme.colors.surfaceColors.onSurface
        )
    ) {
        Icon(
            painter = painterResource(Res.drawable.icon_apple),
            contentDescription = stringResource(Res.string.apple_icon),
            tint = WorkoutFlowTheme.colors.surfaceColors.onSurface,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(WorkoutFlowTheme.padding.extraSmall))
        Text(
            text = stringResource(Res.string.continue_with_apple),
            style = WorkoutFlowTheme.typography.labelMedium,
        )
    }
}