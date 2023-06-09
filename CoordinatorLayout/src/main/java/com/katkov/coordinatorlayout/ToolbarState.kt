package com.katkov.coordinatorlayout

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource

@Composable
fun rememberToolBarState(
    minToolbarHeightPx: Float = 0f,
    maxToolbarHeightPx: Float
): ToolbarState {
    return remember {
        ToolbarState(
            minToolbarHeightPx,
            maxToolbarHeightPx
        )
    }
}

@Stable
open class ToolbarState(
    val minToolbarHeightPx: Float,
    val maxToolbarHeightPx: Float
) {

    var toolbarOffsetHeightPx by mutableStateOf(0.0f)

    var scrollOffsetHeightPx by mutableStateOf(0.0f)

    val toolbarDeltaPx = maxToolbarHeightPx - minToolbarHeightPx

}


@Stable
interface ToolbarScrollBehavior {

    val state: ToolbarState

    val nestedScrollConnection: NestedScrollConnection
}

class ExitUntilCollapsedBehavior(
    override val state: ToolbarState
) : ToolbarScrollBehavior {
    override var nestedScrollConnection = ExitUntilCollapsedNestedScrollConnection(state)
}

open class ExitUntilCollapsedNestedScrollConnection(private val state: ToolbarState):
    NestedScrollConnection {
    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        val postScrollOffset = state.scrollOffsetHeightPx + consumed.y
        state.scrollOffsetHeightPx = postScrollOffset
        if (consumed.y < 0.0f) {
            val newOffset = state.toolbarOffsetHeightPx + consumed.y
            state.toolbarOffsetHeightPx = newOffset.coerceIn(-state.toolbarDeltaPx, 0f)
        }
        if (consumed.y > 0.0f &&
            state.scrollOffsetHeightPx >= state.toolbarOffsetHeightPx) {
            state.toolbarOffsetHeightPx = state.scrollOffsetHeightPx
        }
        if (consumed.y >= 0f && available.y > 0f) {
            state.scrollOffsetHeightPx = 0f
            state.toolbarOffsetHeightPx = 0f
        }
        return Offset.Zero
    }
}

class ScrollBehavior(
    override val state: ToolbarState
): ToolbarScrollBehavior {
    override var nestedScrollConnection = ScrollNestedScrollConnection(state)
}

open class ScrollNestedScrollConnection(state: ToolbarState):
    ExitUntilCollapsedNestedScrollConnection(state)

class EnterAlwaysBehavior(
    override val state: ToolbarState
): ToolbarScrollBehavior {
    override var nestedScrollConnection = EnterAlwaysNestedScrollConnection(state)
}

open class EnterAlwaysNestedScrollConnection(private val state: ToolbarState):
    NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        val preScrollOffset = state.scrollOffsetHeightPx + available.y
        state.scrollOffsetHeightPx = preScrollOffset
        val newOffset = state.toolbarOffsetHeightPx + available.y
        state.toolbarOffsetHeightPx = newOffset.coerceIn(-state.toolbarDeltaPx, 0f)
        return Offset.Zero
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        if (consumed.y == 0f && available.y > 0) {
            state.scrollOffsetHeightPx = 0f
            state.toolbarOffsetHeightPx = 0f
        }
        return Offset.Zero
    }
}

class EnterAlwaysCollapsedBehavior(
    override val state: ToolbarState
): ToolbarScrollBehavior {
    override var nestedScrollConnection = EnterAlwaysCollapsedNestedScrollConnection(state)
}

open class EnterAlwaysCollapsedNestedScrollConnection(private val state: ToolbarState):
    NestedScrollConnection {
    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        val postScrollOffset = state.scrollOffsetHeightPx + consumed.y
        state.scrollOffsetHeightPx = postScrollOffset
        if (consumed.y < 0.0f) {
            val newOffset = state.toolbarOffsetHeightPx + consumed.y
            state.toolbarOffsetHeightPx = newOffset.coerceIn(-state.maxToolbarHeightPx, 0f)
        }
        if (consumed.y > 0.0f && state.scrollOffsetHeightPx < state.toolbarOffsetHeightPx) {
            val newOffset = state.toolbarOffsetHeightPx + consumed.y
            state.toolbarOffsetHeightPx = newOffset.coerceIn(-state.maxToolbarHeightPx, -state.toolbarDeltaPx)
        }
        if (consumed.y > 0.0f && state.scrollOffsetHeightPx >= state.toolbarOffsetHeightPx) {
            state.toolbarOffsetHeightPx = state.scrollOffsetHeightPx
        }
        if (consumed.y >= 0f && available.y > 0f) {
            state.scrollOffsetHeightPx = 0f
            state.toolbarOffsetHeightPx = 0f
        }
        return Offset.Zero
    }
}