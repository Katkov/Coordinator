package com.katkov.coordinatorlayout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*

@Composable
fun AppBarLayout(modifier: Modifier = Modifier,
                 title: String = "",
                 tintColor: Color = Color.Green,
                 textColor: Color = Color.Black,
                 titleTextSize: TextUnit = 20.sp,
                 appBarTitlePaddingStart: Dp = 40.dp,
                 toolbarState: ToolbarState,
                 background: @Composable () -> Unit,
                 navigationIcon: @Composable () -> Unit,
                 actions: @Composable RowScope.() -> Unit) {
    val density = LocalDensity.current
    val appBarTitlePaddingStartPx = with(density) {appBarTitlePaddingStart.roundToPx().toFloat()}
    var titleOffset by remember { mutableStateOf(Offset.Zero) }
    val delta = (toolbarState.toolbarDeltaPx + toolbarState.toolbarOffsetHeightPx) / toolbarState.toolbarDeltaPx
    val titleTextSizeValue = titleTextSize.value
    val textSize = (titleTextSizeValue + delta * titleTextSizeValue).sp
    val paddingLeft = titleOffset.x - (delta * (titleOffset.x - appBarTitlePaddingStartPx))
    Box(modifier = modifier.fillMaxSize()) {
        background()
        TopAppBar(
            title = { Text(
                modifier = modifier.onGloballyPositioned {
                    if (titleOffset == Offset.Zero) {
                        titleOffset = it.positionInRoot()
                    }},
                text = if (textSize <= titleTextSize) title else "",
                color = textColor,
                fontSize = titleTextSize) },
            navigationIcon = navigationIcon,
            actions = actions,
            elevation = 0.dp,
            backgroundColor =
            if (-toolbarState.toolbarOffsetHeightPx >= toolbarState.toolbarDeltaPx)
                tintColor else Color.Transparent
        )
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.high,
            content = { Text(
                modifier = modifier
                    .align(Alignment.BottomStart)
                    .offset { IntOffset(paddingLeft.toInt(), -titleOffset.y.toInt()) },
                text = if (textSize > titleTextSize) title else "",
                color = textColor,
                fontSize = textSize
            )}
        )
    }
}