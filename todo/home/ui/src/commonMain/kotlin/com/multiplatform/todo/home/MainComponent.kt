package com.multiplatform.todo.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.multiplatform.td.core.ui.TdTheme
import com.multiplatform.todo.core.ui.TodayScheduleLayout
import com.multiplatform.todo.core.ui.TodayTaskLayout
import com.multiplatform.todo.core.ui.selectBackgroundColor
import com.multiplatform.todo.home.main.selectContainerColor
import com.multiplatform.todo.home.main.selectLocalDate
import com.multiplatform.todo.home.main.selectLocalTime
import com.multiplatform.todo.tasks.Category
import com.multiplatform.todo.tasks.CategoryColor
import com.multiplatform.todo.tasks.Task
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import tdmultiplatform.todo.home.ui.generated.resources.Res
import tdmultiplatform.todo.home.ui.generated.resources.ic_clock
import tdmultiplatform.todo.home.ui.generated.resources.ic_local_date
import tdmultiplatform.todo.home.ui.generated.resources.main_progress_title
import tdmultiplatform.todo.home.ui.generated.resources.main_progress_value
import kotlin.time.Duration

@Composable
internal fun TodayTask(
    modifier: Modifier = Modifier,
    task: Task,
    progress: (Task) -> Float,
    onCategoryClick: (Category) -> Unit,
    onTaskClick: (Task) -> Unit,
) {
    Spacer(modifier = Modifier.width(TdTheme.dimens.standard16))
    TodayTaskLayout(
        modifier = Modifier
            .sizeIn(maxWidth = 320.dp, maxHeight = 240.dp, minHeight = 240.dp)
            .then(modifier)
            .clickable { onTaskClick(task) },
        contentColor = selectBackgroundColor(
            color = selectContainerColor(task.category.color),
        )
    ) {
        TodayTaskHeader(
            modifier = Modifier.fillMaxWidth(),
            category = task.category,
            date = task.dueDateTime.date,
            onClick = onCategoryClick,
        )
        Spacer(modifier = Modifier.height(TdTheme.dimens.standard24))
        TodayTaskDescription(
            modifier = Modifier.fillMaxWidth(),
            title = task.title,
            description = task.description,
        )
        Spacer(modifier = Modifier.weight(1f))
        TodayTaskProgress(
            modifier = Modifier.fillMaxWidth(),
            progress = progress(task),
        )
        Spacer(modifier = Modifier.height(TdTheme.dimens.standard16))
    }
}

@Composable
internal fun TodayTaskHeader(
    modifier: Modifier = Modifier,
    category: Category,
    date: LocalDate,
    onClick: (Category) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .padding(horizontal = TdTheme.dimens.standard12)
            .padding(top = TdTheme.dimens.standard16),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CategoryTitle(
            modifier = Modifier.clickable { onClick(category) },
            title = category.name,
            color = category.color,
        )
        Spacer(modifier = Modifier.width(TdTheme.dimens.standard8))
        LocalDateTitle(
            modifier = Modifier.weight(1f),
            date = date,
        )
    }
}

@Composable
internal fun CategoryTitle(
    modifier: Modifier = Modifier,
    title: String,
    color: CategoryColor,
) {
    Column(
        modifier = Modifier
            .wrapContentWidth()
            .then(modifier)
            .background(
                color = selectContainerColor(color),
                shape = RoundedCornerShape(TdTheme.dimens.standard24)
            )
            .padding(vertical = TdTheme.dimens.standard4)
            .padding(horizontal = TdTheme.dimens.standard12),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = title,
            style = TextStyle.Default.copy(
                fontSize = 14.sp,
                color = TdTheme.colors.whites.primary,
                fontWeight = FontWeight.SemiBold,
            )
        )
    }
}

@Composable
internal fun LocalDateTitle(
    modifier: Modifier = Modifier,
    date: LocalDate,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier.size(TdTheme.dimens.standard12),
            painter = rememberVectorPainter(vectorResource(Res.drawable.ic_local_date)),
            tint = TdTheme.colors.blacks.light,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(TdTheme.dimens.standard8))
        Text(
            modifier = Modifier.wrapContentWidth(),
            text = selectLocalDate(date),
            style = TextStyle.Default.copy(
                fontSize = 12.sp,
                color = TdTheme.colors.blacks.light,
            ),
        )
    }
}

@Composable
internal fun TodayTaskDescription(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .padding(horizontal = TdTheme.dimens.standard12)
    ) {
        Text(
            text = title,
            style = TextStyle.Default.copy(
                color = TdTheme.colors.blacks.secondary,
                fontSize = 24.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Bold,
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.height(TdTheme.dimens.standard8))
        Text(
            text = description,
            style = TextStyle.Default.copy(
                color = TdTheme.colors.blacks.secondary,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            ),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
internal fun TodayTaskProgress(
    modifier: Modifier = Modifier,
    progress: Float,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .padding(horizontal = TdTheme.dimens.standard12)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(Res.string.main_progress_title),
                style = TextStyle.Default.copy(
                    fontSize = 14.sp,
                    color = TdTheme.colors.blacks.secondary,
                    fontWeight = FontWeight.SemiBold,
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(Res.string.main_progress_value, (progress * 100).toInt()),
                style = TextStyle.Default.copy(
                    fontSize = 14.sp,
                    color = TdTheme.colors.blacks.secondary,
                    fontWeight = FontWeight.SemiBold,
                )
            )
        }
        Spacer(modifier = Modifier.height(TdTheme.dimens.standard8))
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = TdTheme.colors.whites.primary),
            progress = { progress },
            color = TdTheme.colors.blacks.secondary,
            trackColor = TdTheme.colors.whites.primary,
            gapSize = TdTheme.dimens.standard0,
            drawStopIndicator = {},
        )
    }
}

@Composable
internal fun TodayScheduleTask(
    modifier: Modifier = Modifier,
    task: Task,
    onCategoryClick: (Category) -> Unit,
    onTaskClick: (Task) -> Unit,
) {
    TodayScheduleLayout(
        modifier = Modifier
            .fillMaxWidth()
            then(modifier)
            .clickable { onTaskClick(task) },
        backgroundColor = selectContainerColor(task.category.color),
        contentColor = TdTheme.colors.whites.primary,
    ) {
        Spacer(modifier = Modifier.height(TdTheme.dimens.standard4))
        TodayScheduleDescription(
            modifier = Modifier.fillMaxWidth(),
            title = task.title,
            description = task.description,
        )
        Spacer(modifier = Modifier.height(TdTheme.dimens.standard8))
        TodayScheduleFooter(
            modifier = Modifier.fillMaxWidth(),
            category = task.category,
            time = task.dueDateTime.time,
            duration = task.duration,
            onClick = onCategoryClick,
        )
        Spacer(modifier = Modifier.height(TdTheme.dimens.standard4))
    }
}

@Composable
internal fun TodayScheduleDescription(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .padding(horizontal = TdTheme.dimens.standard12)
    ) {
        Text(
            text = title,
            style = TextStyle.Default.copy(
                color = TdTheme.colors.blacks.secondary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.height(TdTheme.dimens.standard8))
        Text(
            text = description,
            style = TextStyle.Default.copy(
                fontSize = 14.sp,
                color = TdTheme.colors.blacks.light,
                fontWeight = FontWeight.SemiBold,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
internal fun TodayScheduleFooter(
    modifier: Modifier = Modifier,
    category: Category,
    time: LocalTime,
    duration: Duration,
    onClick: (Category) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .padding(horizontal = TdTheme.dimens.standard12)
            .padding(top = TdTheme.dimens.standard4),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LocalTimeTitle(
            modifier = Modifier.wrapContentWidth(),
            time = time,
            duration = duration,
        )
        Spacer(modifier = Modifier.width(TdTheme.dimens.standard24))
        CategoryTitle(
            modifier = Modifier.clickable { onClick(category) },
            title = category.name,
            color = category.color,
        )
    }
}

@Composable
internal fun LocalTimeTitle(
    modifier: Modifier = Modifier,
    time: LocalTime,
    duration: Duration,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(TdTheme.dimens.standard12),
            painter = rememberVectorPainter(vectorResource(Res.drawable.ic_clock)),
            tint = TdTheme.colors.blacks.light,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(TdTheme.dimens.standard8))
        Text(
            modifier = Modifier.wrapContentWidth(),
            text = selectLocalTime(time, duration),
            style = TextStyle.Default.copy(
                fontSize = 12.sp,
                color = TdTheme.colors.blacks.light,
                fontWeight = FontWeight.Medium,
            ),
        )
    }
}