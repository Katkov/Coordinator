package com.katkov.coordinator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.katkov.coordinator.ui.theme.CoordinatorTheme
import com.katkov.coordinatorlayout.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoordinatorTheme {
                //BodyWithEnterAlwaysCollapsedBehavior()
                //BodyWithScrollBehavior()
                //BodyWithEnterAlwaysBehavior()
                BodyWithExitUntilCollapsedBehavior()
            }
        }
    }
}

@Composable
fun AppBarLayoutExample(toolbarState: ToolbarState) {
    val context = LocalContext.current
    AppBarLayout(
        title = "App Bar",
        toolbarState = toolbarState,
        background = { Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        ) },
        navigationIcon = { IconButton(onClick = {
            Toast.makeText(context, "Button Clicked", Toast.LENGTH_LONG).show()
        }) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = null,
                tint = Color.Black
            )
        } },
        actions = { IconButton(onClick = {
            Toast.makeText(context, "Button2 Clicked", Toast.LENGTH_LONG).show()
        }) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = Color.Black
            )
        }})
}

fun LazyListScope.listExample() {
    items(100) { index ->
        Text(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            text = "I'm item $index",
            color = Color.Black)
        Divider(modifier = Modifier.height(1.dp), color = Color.Black)
    }
}

@Composable
fun BodyWithEnterAlwaysCollapsedBehavior() {
    val density = LocalDensity.current
    val minToolbarHeightPx = with(density) { 56.dp.roundToPx().toFloat() }
    val maxToolbarHeightPx = with(density) { 220.dp.roundToPx().toFloat() }
    val toolbarState = rememberToolBarState(minToolbarHeightPx, maxToolbarHeightPx)
    CoordinatorLayout(
        headerElevation = 1.dp,
        behavior = EnterAlwaysCollapsedBehavior(toolbarState),
        toolbarContent = {
            AppBarLayoutExample(toolbarState = toolbarState)
        }) {
        listExample()
    }
}

@Composable
fun BodyWithExitUntilCollapsedBehavior() {
    val density = LocalDensity.current
    val minToolbarHeightPx = with(density) { 56.dp.roundToPx().toFloat() }
    val maxToolbarHeightPx = with(density) { 220.dp.roundToPx().toFloat() }
    val toolbarState = rememberToolBarState(minToolbarHeightPx, maxToolbarHeightPx)
    CoordinatorLayout(
        headerElevation = 1.dp,
        behavior = ExitUntilCollapsedBehavior(toolbarState),
        toolbarContent = {
            AppBarLayoutExample(toolbarState = toolbarState)
        }) {
        listExample()
    }
}

@Composable
fun BodyWithEnterAlwaysBehavior() {
    val density = LocalDensity.current
    val maxToolbarHeightPx = with(density) { 220.dp.roundToPx().toFloat() }
    val toolbarState = rememberToolBarState(maxToolbarHeightPx = maxToolbarHeightPx)
    CoordinatorLayout(
        headerElevation = 1.dp,
        behavior = EnterAlwaysBehavior(toolbarState),
        toolbarContent = {
            AppBarLayoutExample(toolbarState = toolbarState)
        }) {
        listExample()
    }
}

@Composable
fun BodyWithScrollBehavior() {
    val density = LocalDensity.current
    val maxToolbarHeightPx = with(density) { 220.dp.roundToPx().toFloat() }
    val toolbarState = rememberToolBarState(maxToolbarHeightPx = maxToolbarHeightPx)
    CoordinatorLayout(
        headerElevation = 1.dp,
        behavior = ScrollBehavior(toolbarState),
        toolbarContent = {
            AppBarLayoutExample(toolbarState = toolbarState)
        }) {
        listExample()
    }
}
