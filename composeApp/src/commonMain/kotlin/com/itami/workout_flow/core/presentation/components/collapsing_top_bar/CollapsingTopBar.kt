package com.itami.workout_flow.core.presentation.components.collapsing_top_bar

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import kotlin.math.max
import kotlin.math.roundToInt


/**
 * From [hh-histories-compose-custom-toolbar](https://github.com/hhru/hh-histories-compose-custom-toolbar)
 */
@Composable
fun CollapsingTopBar(
    modifier: Modifier = Modifier,
    titleText: String?,
    navigationIcon: (@Composable () -> Unit)? = { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) },
    actions: (@Composable RowScope.() -> Unit)? = null,
    centralContent: (@Composable () -> Unit)? = null,
    additionalContent: (@Composable () -> Unit)? = null,
    scrollBehavior: CollapsingTopAppBarScrollBehavior? = null,
    collapsedTitleStyle: TextStyle = WorkoutFlowTheme.typography.titleSmall,
    expandedTitleStyle: TextStyle = WorkoutFlowTheme.typography.titleMedium,
    containerColor: Color = WorkoutFlowTheme.colors.surfaceColors.surfaceHigh,
    contentColor: Color = WorkoutFlowTheme.colors.surfaceColors.onSurface,
    shadowElevation: Dp = 3.dp,
    collapsedShape: RoundedCornerShape = RoundedCornerShape(0.dp),
    expandedShape: RoundedCornerShape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
) {
    CollapsingTopBarContent(
        modifier = modifier,
        titleText = titleText,
        navigationIcon = navigationIcon,
        actions = actions,
        centralContent = centralContent,
        additionalContent = additionalContent,
        scrollBehavior = scrollBehavior,
        collapsedTitleStyle = collapsedTitleStyle,
        expandedTitleStyle = expandedTitleStyle,
        containerColor = containerColor,
        contentColor = contentColor,
        shadowElevation = shadowElevation,
        collapsedShape = collapsedShape,
        expandedShape = expandedShape,
    )
}

@Composable
fun CollapsingTopBarWithEditableTitle(
    modifier: Modifier = Modifier,
    titleText: String,
    onTitleValueChange: (newValue: String) -> Unit,
    titleHint: String? = null,
    navigationIcon: (@Composable () -> Unit)? = { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) },
    actions: (@Composable RowScope.() -> Unit)? = null,
    centralContent: (@Composable () -> Unit)? = null,
    additionalContent: (@Composable () -> Unit)? = null,
    scrollBehavior: CollapsingTopAppBarScrollBehavior? = null,
    collapsedTitleStyle: TextStyle = WorkoutFlowTheme.typography.titleSmall,
    expandedTitleStyle: TextStyle = WorkoutFlowTheme.typography.titleMedium,
    containerColor: Color = WorkoutFlowTheme.colors.surfaceColors.surfaceHigh,
    contentColor: Color = WorkoutFlowTheme.colors.surfaceColors.onSurface,
    shadowElevation: Dp = 3.dp,
    collapsedShape: RoundedCornerShape = RoundedCornerShape(0.dp),
    expandedShape: RoundedCornerShape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
) {
    CollapsingTopBarContent(
        modifier = modifier,
        titleText = titleText,
        onTitleValueChange = onTitleValueChange,
        titleHint = titleHint,
        navigationIcon = navigationIcon,
        actions = actions,
        centralContent = centralContent,
        additionalContent = additionalContent,
        scrollBehavior = scrollBehavior,
        collapsedTitleStyle = collapsedTitleStyle,
        expandedTitleStyle = expandedTitleStyle,
        containerColor = containerColor,
        contentColor = contentColor,
        shadowElevation = shadowElevation,
        collapsedShape = collapsedShape,
        expandedShape = expandedShape,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CollapsingTopBarContent(
    modifier: Modifier = Modifier,
    titleText: String?,
    onTitleValueChange: ((newValue: String) -> Unit)? = null,
    titleHint: String? = null,
    navigationIcon: (@Composable () -> Unit)? = { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) },
    actions: (@Composable RowScope.() -> Unit)? = null,
    centralContent: (@Composable () -> Unit)? = null,
    additionalContent: (@Composable () -> Unit)? = null,
    collapsedTitleStyle: TextStyle = WorkoutFlowTheme.typography.titleSmall,
    expandedTitleStyle: TextStyle = WorkoutFlowTheme.typography.titleMedium,
    scrollBehavior: CollapsingTopAppBarScrollBehavior? = null,
    containerColor: Color = WorkoutFlowTheme.colors.surfaceColors.surfaceHigh,
    contentColor: Color = WorkoutFlowTheme.colors.surfaceColors.onSurface,
    shadowElevation: Dp = 3.dp,
    collapsedShape: RoundedCornerShape = RoundedCornerShape(0.dp),
    expandedShape: RoundedCornerShape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
) {
    val collapsedFraction = when {
        scrollBehavior != null && centralContent == null -> scrollBehavior.state.collapsedFraction
        scrollBehavior != null && centralContent != null -> 0f
        else -> 1f
    }

    val fullyCollapsedTitleScale = when {
        titleText != null -> CollapsedTitleLineHeight.value / expandedTitleStyle.lineHeight.value
        else -> 1f
    }

    val collapsingTitleScale = lerp(1f, fullyCollapsedTitleScale, collapsedFraction)

    val shape = when {
        scrollBehavior == null -> expandedShape
        scrollBehavior.state.contentOffset <= 0 && collapsedFraction == 1f -> collapsedShape
        else -> expandedShape
    }

    val focusManager = LocalFocusManager.current

    Surface(
        modifier = modifier,
        shadowElevation = shadowElevation,
        color = containerColor,
        contentColor = contentColor,
        shape = shape
    ) {
        Layout(
            content = {
                if (titleText != null) {
                    Column(
                        modifier = Modifier
                            .layoutId(ExpandedContentId)
                            .wrapContentHeight(align = Alignment.Top)
                            .padding(horizontal = HorizontalPadding)
                    ) {
                        if (onTitleValueChange != null) {
                            BasicTextField(
                                modifier = Modifier,
                                value = titleText,
                                onValueChange = onTitleValueChange,
                                textStyle = expandedTitleStyle,
                                cursorBrush = Brush.linearGradient(
                                    colors = listOf(
                                        WorkoutFlowTheme.colors.surfaceColors.onSurface,
                                        WorkoutFlowTheme.colors.surfaceColors.onSurface
                                    )
                                ),
                                maxLines = 2,
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                                decorationBox = { innerTextField ->
                                    TextFieldDefaults.DecorationBox(
                                        value = titleText,
                                        singleLine = false,
                                        colors = TextFieldDefaults.colors(
                                            unfocusedContainerColor = Color.Transparent,
                                            focusedContainerColor = Color.Transparent,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                            cursorColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                                            focusedTextColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                                            unfocusedTextColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                                        ),
                                        placeholder = {
                                            if (titleHint != null) {
                                                Text(
                                                    text = titleHint,
                                                    style = expandedTitleStyle,
                                                    color = WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant,
                                                )
                                            }
                                        },
                                        innerTextField = innerTextField,
                                        enabled = true,
                                        visualTransformation = VisualTransformation.None,
                                        interactionSource = remember { MutableInteractionSource() },
                                        contentPadding = PaddingValues(0.dp),
                                    )
                                }
                            )
                        } else {
                            Text(
                                modifier = Modifier
                                    .graphicsLayer(
                                        scaleX = collapsingTitleScale,
                                        scaleY = collapsingTitleScale,
                                        transformOrigin = TransformOrigin(0f, 0f)
                                    ),
                                text = titleText,
                                style = expandedTitleStyle
                            )
                        }

                        if (additionalContent != null) {
                            additionalContent()
                        }
                    }

                    if (onTitleValueChange != null) {
                        BasicTextField(
                            modifier = Modifier
                                .layoutId(CollapsedContentId)
                                .wrapContentHeight(align = Alignment.Top)
                                .graphicsLayer(
                                    scaleX = collapsingTitleScale,
                                    scaleY = collapsingTitleScale,
                                    transformOrigin = TransformOrigin(0f, 0f)
                                ),
                            value = titleText,
                            onValueChange = onTitleValueChange,
                            textStyle = collapsedTitleStyle,
                            cursorBrush = Brush.linearGradient(
                                colors = listOf(
                                    WorkoutFlowTheme.colors.surfaceColors.onSurface,
                                    WorkoutFlowTheme.colors.surfaceColors.onSurface
                                )
                            ),
                            maxLines = 1,
                            decorationBox = { innerTextField ->
                                TextFieldDefaults.DecorationBox(
                                    value = titleText,
                                    singleLine = false,
                                    colors = TextFieldDefaults.colors(
                                        unfocusedContainerColor = Color.Transparent,
                                        focusedContainerColor = Color.Transparent,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        cursorColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                                        focusedTextColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                                        unfocusedTextColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                                    ),
                                    placeholder = {
                                        if (titleHint != null) {
                                            Text(
                                                text = titleHint,
                                                style = collapsedTitleStyle,
                                                color = WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant,
                                            )
                                        }
                                    },
                                    innerTextField = innerTextField,
                                    enabled = true,
                                    visualTransformation = VisualTransformation.None,
                                    interactionSource = remember { MutableInteractionSource() },
                                    contentPadding = PaddingValues(0.dp),
                                )
                            }
                        )
                    } else {
                        Text(
                            modifier = Modifier
                                .layoutId(CollapsedContentId)
                                .wrapContentHeight(align = Alignment.Top)
                                .graphicsLayer(
                                    scaleX = collapsingTitleScale,
                                    scaleY = collapsingTitleScale,
                                    transformOrigin = TransformOrigin(0f, 0f)
                                ),
                            text = titleText,
                            style = collapsedTitleStyle,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                }

                if (navigationIcon != null) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .layoutId(NavigationIconId)
                    ) {
                        navigationIcon()
                    }
                }

                if (actions != null) {
                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .layoutId(ActionsId)
                    ) {
                        actions()
                    }
                }

                if (centralContent != null) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .layoutId(CentralContentId)
                    ) {
                        centralContent()
                    }
                }
            },
            modifier = modifier.then(Modifier.heightIn(min = MinCollapsedHeight)).statusBarsPadding()
        ) { measurables, constraints ->
            val horizontalPaddingPx = HorizontalPadding.toPx()
            val expandedTitleBottomPaddingPx = ExpandedTitleBottomPadding.toPx()


            // Measuring widgets inside toolbar:

            val navigationIconPlaceable = measurables.firstOrNull { it.layoutId == NavigationIconId }
                ?.measure(constraints.copy(minWidth = 0))

            val actionsPlaceable = measurables.firstOrNull { it.layoutId == ActionsId }
                ?.measure(constraints.copy(minWidth = 0))

            val expandedTitlePlaceable = measurables.firstOrNull { it.layoutId == ExpandedContentId }
                ?.measure(
                    constraints.copy(
                        maxWidth = (constraints.maxWidth - 2 * horizontalPaddingPx).roundToInt(),
                        minWidth = 0,
                        minHeight = 0
                    )
                )

            val additionalContentPlaceable = measurables.firstOrNull { it.layoutId == AdditionalContentId }
                ?.measure(constraints)

            val navigationIconOffset = when (navigationIconPlaceable) {
                null -> horizontalPaddingPx
                else -> navigationIconPlaceable.width + horizontalPaddingPx * 2
            }

            val actionsOffset = when (actionsPlaceable) {
                null -> horizontalPaddingPx
                else -> actionsPlaceable.width + horizontalPaddingPx * 2
            }

            val collapsedTitleMaxWidthPx =
                (constraints.maxWidth - navigationIconOffset - actionsOffset) / fullyCollapsedTitleScale

            val collapsedTitlePlaceable = measurables.firstOrNull { it.layoutId == CollapsedContentId }
                ?.measure(
                    constraints.copy(
                        maxWidth = collapsedTitleMaxWidthPx.roundToInt(),
                        minWidth = 0,
                        minHeight = 0
                    )
                )

            val centralContentPlaceable = measurables.firstOrNull { it.layoutId == CentralContentId }
                ?.measure(
                    constraints.copy(
                        minWidth = 0,
                        maxWidth = (constraints.maxWidth - navigationIconOffset - actionsOffset).roundToInt()
                    )
                )

            val collapsedHeightPx = when {
                centralContentPlaceable != null ->
                    max(MinCollapsedHeight.toPx(), centralContentPlaceable.height.toFloat())
                else -> MinCollapsedHeight.toPx()
            }

            var layoutHeightPx = collapsedHeightPx


            // Calculating coordinates of widgets inside toolbar:

            // Current coordinates of navigation icon
            val navigationIconX = horizontalPaddingPx.roundToInt()
            val navigationIconY = ((collapsedHeightPx - (navigationIconPlaceable?.height ?: 0)) / 2).roundToInt()

            // Current coordinates of actions
            val actionsX = (constraints.maxWidth - (actionsPlaceable?.width ?: 0) - horizontalPaddingPx).roundToInt()
            val actionsY = ((collapsedHeightPx - (actionsPlaceable?.height ?: 0)) / 2).roundToInt()

            // Current coordinates of title
            var collapsingTitleY = 0
            var collapsingTitleX = 0

            if (expandedTitlePlaceable != null && collapsedTitlePlaceable != null) {
                // Measuring toolbar collapsing distance
                val heightOffsetLimitPx = expandedTitlePlaceable.height + expandedTitleBottomPaddingPx
                scrollBehavior?.state?.heightOffsetLimit = when (centralContent) {
                    null -> -heightOffsetLimitPx
                    else -> -1f
                }

                // Toolbar height at fully expanded state
                val fullyExpandedHeightPx = MinCollapsedHeight.toPx() + heightOffsetLimitPx

                // Coordinates of fully expanded title
                val fullyExpandedTitleX = horizontalPaddingPx
                val fullyExpandedTitleY =
                    fullyExpandedHeightPx - expandedTitlePlaceable.height - expandedTitleBottomPaddingPx

                // Coordinates of fully collapsed title
                val fullyCollapsedTitleX = navigationIconOffset
                val fullyCollapsedTitleY = collapsedHeightPx / 2 - CollapsedTitleLineHeight.toPx().roundToInt() / 2

                // Current height of toolbar
                layoutHeightPx = lerp(fullyExpandedHeightPx, collapsedHeightPx, collapsedFraction)

                // Current coordinates of collapsing title
                collapsingTitleX = lerp(fullyExpandedTitleX, fullyCollapsedTitleX, collapsedFraction).roundToInt()
                collapsingTitleY = lerp(fullyExpandedTitleY, fullyCollapsedTitleY, collapsedFraction).roundToInt()
            } else {
                scrollBehavior?.state?.heightOffsetLimit = -1f
            }

            val toolbarHeightPx = layoutHeightPx.roundToInt() + (additionalContentPlaceable?.height ?: 0)


            // Placing toolbar widgets:

            layout(constraints.maxWidth, toolbarHeightPx) {
                navigationIconPlaceable?.placeRelative(
                    x = navigationIconX,
                    y = navigationIconY
                )
                actionsPlaceable?.placeRelative(
                    x = actionsX,
                    y = actionsY
                )
                centralContentPlaceable?.placeRelative(
                    x = navigationIconOffset.roundToInt(),
                    y = ((collapsedHeightPx - centralContentPlaceable.height) / 2).roundToInt()
                )
                if (expandedTitlePlaceable?.width == collapsedTitlePlaceable?.width) {
                    expandedTitlePlaceable?.placeRelative(
                        x = collapsingTitleX,
                        y = collapsingTitleY,
                    )
                } else {
                    expandedTitlePlaceable?.placeRelativeWithLayer(
                        x = collapsingTitleX,
                        y = collapsingTitleY,
                        layerBlock = { alpha = 1 - collapsedFraction }
                    )
                    collapsedTitlePlaceable?.placeRelativeWithLayer(
                        x = collapsingTitleX,
                        y = collapsingTitleY,
                        layerBlock = { alpha = collapsedFraction }
                    )
                }
            }
        }

    }
}


private fun lerp(a: Float, b: Float, fraction: Float): Float {
    return a + fraction * (b - a)
}

data class CollapsingTitle(
    val titleText: String,
    val expandedTextStyle: TextStyle,
    val collapsedTextStyle: TextStyle = expandedTextStyle,
)

private val MinCollapsedHeight = 56.dp
private val HorizontalPadding = 8.dp
private val ExpandedTitleBottomPadding = 8.dp
private val CollapsedTitleLineHeight = 28.sp

private const val ExpandedContentId = "expandedContent"
private const val CollapsedContentId = "collapsedContent"
private const val NavigationIconId = "navigationIcon"
private const val ActionsId = "actions"
private const val CentralContentId = "centralContent"
private const val AdditionalContentId = "additionalContent"