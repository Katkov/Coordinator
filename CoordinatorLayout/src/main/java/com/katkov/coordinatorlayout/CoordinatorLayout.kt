package com.katkov.coordinatorlayout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CoordinatorLayout(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    headerShape: Shape = RectangleShape,
    headerColor: Color = MaterialTheme.colors.surface,
    headerContentColor: Color = contentColorFor(headerColor),
    headerBorder: BorderStroke? = null,
    headerElevation: Dp = 0.dp,
    behavior: ToolbarScrollBehavior,
    toolbarContent: @Composable () -> Unit,
    content: LazyListScope.() -> Unit) {
    val density = LocalDensity.current
    val nestedScrollConnection  = remember { behavior.nestedScrollConnection }
    val state = remember { behavior.state }
    val maxToolBarHeightDp = with(density) { state.maxToolbarHeightPx.toDp() }
    val toolbarOffsetHeightDp = with(density) { state.toolbarOffsetHeightPx.toDp() }
    Box(
        Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        LazyColumn(
            modifier = modifier,
            state = lazyListState,
            contentPadding = PaddingValues(top = maxToolBarHeightDp),
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            flingBehavior = flingBehavior,
            content = content)
        Surface(
            modifier = modifier.height(maxToolBarHeightDp + toolbarOffsetHeightDp),
            shape = headerShape,
            color = headerColor,
            contentColor = headerContentColor,
            border = headerBorder,
            elevation = headerElevation,
            content = toolbarContent)
    }
}