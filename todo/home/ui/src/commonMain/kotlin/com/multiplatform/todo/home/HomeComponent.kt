package com.multiplatform.todo.home

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import com.multiplatform.td.core.ui.TdTheme
import com.multiplatform.todo.home.home.HomeEvent
import com.multiplatform.todo.home.home.MenuItem
import com.multiplatform.todo.home.home.selectNewButtonDescription
import org.jetbrains.compose.resources.vectorResource
import tdmultiplatform.todo.home.ui.generated.resources.Res
import tdmultiplatform.todo.home.ui.generated.resources.ic_add

@Composable
internal fun TdFabButton(
    selectedItem: MenuItem,
    dispatch: (HomeEvent) -> Unit,
) {
    FloatingActionButton(
        modifier = Modifier
            .size(TdTheme.dimens.standard48)
            .layout { measurable, constraints ->
                val overriddenMeasure = measurable.measure(
                    constraints = constraints
                )
                layout(overriddenMeasure.width, overriddenMeasure.height) {
                    overriddenMeasure.place(0, overriddenMeasure.toSeventhOfEightHeightMargin())
                }
            }
        ,
        shape = RoundedCornerShape(TdTheme.dimens.standard24),
        onClick = { dispatch(HomeEvent.OnNewClicked) },
        containerColor = TdTheme.colors.deepOranges.primary,
        contentColor = TdTheme.colors.whites.primary,
    ) {
        Icon(
            modifier = Modifier.size(TdTheme.dimens.standard36),
            painter = rememberVectorPainter(vectorResource(Res.drawable.ic_add)),
            contentDescription = selectNewButtonDescription(selectedItem),
        )
    }
}

@Composable
internal fun TdBottomTabBar(
    selectedItem: MenuItem,
    menuItems: List<MenuItem>,
    isSelected: (MenuItem) -> Boolean,
    dispatch: (HomeEvent) -> Unit,
) {
    val index = remember(selectedItem) { menuItems.indexOfFirst { it == selectedItem } }
    Column {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = TdTheme.colors.greys.light,
            thickness = TdTheme.dimens.standard1,
        )
        TabRow(
            modifier = Modifier.sizeIn(minHeight = TdTheme.dimens.standard64),
            selectedTabIndex = index,
            containerColor = TdTheme.colors.whites.primary,
            contentColor = TdTheme.colors.deepOranges.primary,
            indicator = { positions ->
                SecondaryIndicator(
                    modifier = Modifier
                        .tabTopIndicatorOffset(positions[index])
                )
            },
            divider = {},
        ) {
            menuItems.forEach { item ->
                Tab(
                    modifier = Modifier.sizeIn(minHeight = TdTheme.dimens.standard64),
                    icon = {
                        Icon(
                            modifier = Modifier.size(TdTheme.dimens.standard24),
                            painter = rememberVectorPainter(vectorResource(item.iconRes)),
                            tint = when {
                                isSelected(item) -> TdTheme.colors.deepOranges.primary
                                else -> TdTheme.colors.greys.primary
                            },
                            contentDescription = null,
                        )
                    },
                    selected = isSelected(item),
                    onClick = { dispatch(HomeEvent.OnMenuItemSelected(item)) },
                )
            }
        }
    }
}

@Composable
internal fun SecondaryIndicator(
    modifier: Modifier = Modifier,
    height: Dp = TdTheme.dimens.standard4,
    color: Color = TdTheme.colors.deepOranges.primary,
) {
    Box(modifier.fillMaxWidth().height(height).background(color = color))
}

internal fun Modifier.tabTopIndicatorOffset(
    currentTabPosition: TabPosition
): Modifier =
    composed(
        inspectorInfo =
        debugInspectorInfo {
            name = "tabIndicatorOffset"
            value = currentTabPosition
        }
    ) {
        val currentTabWidth by
        animateDpAsState(
            targetValue = currentTabPosition.width,
            animationSpec = TabRowIndicatorSpec
        )
        val indicatorOffset by
        animateDpAsState(
            targetValue = currentTabPosition.left,
            animationSpec = TabRowIndicatorSpec
        )
        fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
            .offset { IntOffset(x = indicatorOffset.roundToPx(), y = 0) }
            .width(currentTabWidth)
            .padding(horizontal = TdTheme.dimens.standard24)
    }

private val TabRowIndicatorSpec: AnimationSpec<Dp> =
    tween(durationMillis = 250, easing = FastOutSlowInEasing)

private fun Placeable.toSeventhOfEightHeightMargin() =
    height * 7 / 8