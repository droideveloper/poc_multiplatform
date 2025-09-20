package com.multiplatform.todo.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.multiplatform.td.core.ui.TdTheme
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char

@Composable
fun TodayTaskLayout(
    modifier: Modifier = Modifier,
    contentColor: Color = Color.Unspecified,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .background(
                color = contentColor,
                shape = RoundedCornerShape(TdTheme.dimens.standard16),
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        content(this)
    }
}

@Composable
fun TodayScheduleLayout(
    modifier: Modifier = Modifier,
    contentColor: Color = Color.Unspecified,
    backgroundColor: Color = Color.Unspecified,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(TdTheme.dimens.standard16),
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = TdTheme.dimens.standard16)
                .background(
                    color = contentColor,
                    shape = RoundedCornerShape(
                        topStart = TdTheme.dimens.standard0,
                        topEnd = TdTheme.dimens.standard16,
                        bottomStart = TdTheme.dimens.standard0,
                        bottomEnd = TdTheme.dimens.standard16,
                    ),
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            content(this)
        }
    }
}

@Composable
fun CalendarScheduleLayout(
    modifier: Modifier = Modifier,
    contentColor: Color = Color.Unspecified,
    content: @Composable ColumnScope.() -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .then(modifier)
            .background(
                color = TdTheme.colors.whites.primary,
                shape = RoundedCornerShape(TdTheme.dimens.standard16),
            )
            .background(
                color = selectBackgroundColor(color = contentColor),
                shape = RoundedCornerShape(TdTheme.dimens.standard16),
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(TdTheme.dimens.standard28)
                .padding(horizontal = TdTheme.dimens.standard8)
                .padding(vertical = TdTheme.dimens.standard16)
                .background(
                    color = contentColor,
                    shape = RoundedCornerShape(TdTheme.dimens.standard8),
                ),
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            content(this)
        }
    }
}

@Composable
fun CalendarTimeLazyLayout(
    modifier: Modifier = Modifier,
    time: LocalTime,
    localTimes: List<LocalTime> = rememberLocalTimeItems(time),
    itemModifier: Modifier = Modifier,
    taskMap: Map<Int, @Composable () -> Unit>,
) {
    val scrollState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        state = scrollState,
    ) {
        items(localTimes) { localTime ->
            CalendarTimeLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(itemModifier),
                time = localTime,
                taskProvider = taskMap[localTime.hour] ?: { /*no-op*/ },
            )
        }
    }
}

@Composable
fun CalendarTimeLayout(
    modifier: Modifier = Modifier,
    time: LocalTime,
    taskProvider: @Composable () -> Unit = { /*no-op*/ },
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .background(TdTheme.colors.whites.primary),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .then(modifier),
        ) {
            HorizontalDivider(
                thickness = TdTheme.dimens.standard1,
                color = TdTheme.colors.greys.light,
            )
            Spacer(modifier = Modifier.height(TdTheme.dimens.standard12))
            Text(
                text = selectLocalTime(time),
                style = TdTheme.typography.titleMedium.copy(
                    color = TdTheme.colors.blacks.light,
                    fontWeight = FontWeight.SemiBold,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        taskProvider()
    }
}

@Composable
internal fun selectLocalTime(time: LocalTime): String {
    val timeFormat = remember {
        LocalTime.Format {
            hour(Padding.ZERO)
            char(':')
            minute(Padding.ZERO)
        }
    }
    return timeFormat.format(time)
}

@Composable
fun rememberLocalTimeItems(time: LocalTime): List<LocalTime> =
    remember(time) {
        mutableListOf(
            LocalTime(0, 0),
            LocalTime(1, 0),
            LocalTime(2, 0),
            LocalTime(3, 0),
            LocalTime(4, 0),
            LocalTime(5, 0),
            LocalTime(6, 0),
            LocalTime(7, 0),
            LocalTime(8, 0),
            LocalTime(9, 0),
            LocalTime(10, 0),
            LocalTime(11, 0),
            LocalTime(12, 0),
            LocalTime(13, 0),
            LocalTime(14, 0),
            LocalTime(15, 0),
            LocalTime(16, 0),
            LocalTime(17, 0),
            LocalTime(18, 0),
            LocalTime(19, 0),
            LocalTime(20, 0),
            LocalTime(21, 0),
            LocalTime(22, 0),
            LocalTime(23, 0),
        ).filter { it.hour >= time.hour }
    }
