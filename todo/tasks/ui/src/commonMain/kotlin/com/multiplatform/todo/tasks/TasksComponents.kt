package com.multiplatform.todo.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.multiplatform.td.core.ui.TdTheme
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.vectorResource
import tdmultiplatform.todo.tasks.ui.generated.resources.Res
import tdmultiplatform.todo.tasks.ui.generated.resources.ic_local_date

@Composable
internal fun TaskItemView(
    modifier: Modifier = Modifier,
    task: Task,
    date: LocalDate,
    onTaskClicked: (Task) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onTaskClicked(task) }
            .background(
                color = selectContainerColor(task.category.color),
                shape = RoundedCornerShape(0.dp),
            )
            .then(modifier),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = TdTheme.dimens.standard8)
                .background(
                    color = TdTheme.colors.whites.primary,
                    shape = RoundedCornerShape(0.dp),
                ),
        ) {
            Spacer(modifier = Modifier.height(TdTheme.dimens.standard8))
            Text(
                modifier = Modifier.padding(horizontal = TdTheme.dimens.standard6),
                text = task.title,
                style = TdTheme.typography.titleLarge.copy(
                    color = TdTheme.colors.blacks.secondary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                ),
            )
            Spacer(modifier = Modifier.height(TdTheme.dimens.standard8))
            Text(
                modifier = Modifier.padding(horizontal = TdTheme.dimens.standard6),
                text = task.description,
                style = TextStyle.Default.copy(
                    fontSize = 16.sp,
                    color = TdTheme.colors.blacks.light,
                    fontWeight = FontWeight.SemiBold,
                ),
            )
            Spacer(modifier = Modifier.height(TdTheme.dimens.standard8))
            TodayTaskFooter(
                modifier = Modifier.padding(horizontal = TdTheme.dimens.standard6),
                category = task.category,
                date = date,
            )
            Spacer(modifier = Modifier.height(TdTheme.dimens.standard8))
            HorizontalDivider(
                thickness = TdTheme.dimens.standard1,
                color = TdTheme.colors.greys.light,
            )
        }
    }
}

@Composable
internal fun TodayTaskFooter(
    modifier: Modifier = Modifier,
    category: Category,
    date: LocalDate,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = category.name,
            style = TextStyle.Default.copy(
                fontSize = 14.sp,
                color = selectContainerColor(category.color),
                fontWeight = FontWeight.SemiBold,
            ),
        )
        Spacer(modifier = Modifier.width(TdTheme.dimens.standard8))
        LocalDateTitle(
            modifier = Modifier.weight(1f),
            date = date,
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
