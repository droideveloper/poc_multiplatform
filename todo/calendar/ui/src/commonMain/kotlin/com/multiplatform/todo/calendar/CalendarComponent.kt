package com.multiplatform.todo.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.multiplatform.td.core.ui.TdTheme
import com.multiplatform.todo.core.ui.CalendarScheduleLayout
import com.multiplatform.todo.tasks.Task
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.vectorResource
import tdmultiplatform.todo.calendar.ui.generated.resources.Res
import tdmultiplatform.todo.calendar.ui.generated.resources.ic_clock
import kotlin.time.Duration

@Composable
fun CalendarScheduleTask(
    modifier: Modifier = Modifier,
    task: Task,
    onTaskClick: (Task) -> Unit,
) {
    CalendarScheduleLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = TdTheme.dimens.standard96)
            .then(modifier)
            .clickable { onTaskClick(task) },
        contentColor = selectContainerColor(task.category.color),
    ) {
        Spacer(modifier = Modifier.height(TdTheme.dimens.standard16))
        CalendarScheduleTitle(title = task.title)
        LocalTimeTitle(time = task.dueDateTime.time, duration = task.duration)
        Spacer(modifier = Modifier.height(TdTheme.dimens.standard16))
    }
}

@Composable
internal fun CalendarScheduleTitle(
    modifier: Modifier = Modifier,
    title: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .padding(horizontal = TdTheme.dimens.standard8),
    ) {
        Text(
            text = title,
            style = TdTheme.typography.titleLarge.copy(
                color = TdTheme.colors.blacks.secondary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.height(TdTheme.dimens.standard8))
    }
}

@Composable
internal fun LocalTimeTitle(
    modifier: Modifier = Modifier,
    time: LocalTime,
    duration: Duration,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = TdTheme.dimens.standard8)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(TdTheme.dimens.standard12),
            painter = rememberVectorPainter(vectorResource(Res.drawable.ic_clock)),
            tint = TdTheme.colors.blacks.light,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(TdTheme.dimens.standard4))
        Text(
            modifier = Modifier.wrapContentWidth(),
            text = selectLocalTime(time, duration),
            style = TdTheme.typography.titleMedium.copy(
                color = TdTheme.colors.blacks.light,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
            ),
        )
    }
}

@Composable
fun CalendarDay(
    modifier: Modifier = Modifier,
    day: LocalDate,
    isSelected: Boolean,
    onClick: (LocalDate) -> Unit,
) {
    Column(
        modifier = Modifier
            .wrapContentWidth()
            .then(modifier)
            .clickable { onClick(day) }
            .padding(horizontal = TdTheme.dimens.standard8),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = selectDayName(day),
            style = TdTheme.typography.titleBig.copy(
                color = selectDayNameColor(isSelected),
                fontWeight = FontWeight.Medium,
            ),
        )
        Spacer(modifier = Modifier.height(TdTheme.dimens.standard8))
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = selectDayNumber(day),
            style = TdTheme.typography.titleExtraLarge.copy(
                color = selectDayNumberColor(isSelected),
                fontWeight = FontWeight.ExtraBold,
            ),
        )
        Spacer(modifier = Modifier.height(TdTheme.dimens.standard4))
        Box(
            modifier = Modifier
                .size(TdTheme.dimens.standard8)
                .background(
                    color = selectSelectionIndicatorColor(isSelected),
                    shape = RoundedCornerShape(TdTheme.dimens.standard4),
                ),
        )
    }
}
