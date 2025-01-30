package com.itami.workout_flow.home.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.itami.workout_flow.core.domain.model.user.CurrentUser
import com.itami.workout_flow.core.presentation.components.PulseLoadingAnimation
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.guest
import workoutflow.composeapp.generated.resources.hello
import workoutflow.composeapp.generated.resources.lets_workout
import workoutflow.composeapp.generated.resources.profile_picture
import workoutflow.composeapp.generated.resources.user_greeting

@Composable
internal fun HomeUserGreetingSection(
    modifier: Modifier = Modifier,
    currentUser: CurrentUser,
    onProfilePictureClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f, fill = true),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = when (currentUser) {
                    is CurrentUser.Authenticated -> stringResource(
                        Res.string.user_greeting,
                        currentUser.name
                    )

                    is CurrentUser.Guest -> stringResource(Res.string.hello)
                },
                style = WorkoutFlowTheme.typography.bodyMedium,
                color = WorkoutFlowTheme.colors.backgroundColors.onBackgroundVariant,
            )
            Text(
                text = stringResource(Res.string.lets_workout),
                style = WorkoutFlowTheme.typography.titleMedium,
                color = WorkoutFlowTheme.colors.backgroundColors.onBackground,
            )
        }
        Box(
            modifier = Modifier.weight(0.5f, fill = false),
            contentAlignment = Alignment.CenterEnd,
        ) {
            val painter = rememberAsyncImagePainter(
                model = if (
                    currentUser is CurrentUser.Authenticated &&
                    currentUser.profilePictureUrl != null
                ) {
                    currentUser.profilePictureUrl
                } else {
                    Res.drawable.guest
                },
                error = painterResource(Res.drawable.guest)
            )

            val painterState by painter.state.collectAsStateWithLifecycle()

            when (painterState) {
                is AsyncImagePainter.State.Empty,
                is AsyncImagePainter.State.Loading -> {
                    PulseLoadingAnimation(modifier = Modifier.size(40.dp))
                }

                is AsyncImagePainter.State.Error,
                is AsyncImagePainter.State.Success -> {
                    Image(
                        painter = painter,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .clickable(role = Role.Image) {
                                onProfilePictureClick()
                            },
                        contentDescription = stringResource(Res.string.profile_picture),
                        contentScale = ContentScale.FillBounds,
                    )
                }
            }

        }
    }
}