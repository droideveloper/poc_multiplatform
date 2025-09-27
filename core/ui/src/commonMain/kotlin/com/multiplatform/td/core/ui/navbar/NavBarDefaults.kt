package com.multiplatform.td.core.ui.navbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import com.multiplatform.td.core.ui.TdTheme
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

object NavBarDefaults {

    sealed class Action(
        open val onClick: () -> Unit,
    ) {

        @Composable
        abstract fun Content()

        data class Icon(
            val icon: Painter,
            override val onClick: () -> Unit,
            val contentDescription: String? = null,
            val tint: Color? = null,
        ) : Action(onClick) {

            @Composable
            override fun Content() {
                Box(
                    modifier = Modifier.testTag("nav_bar_action"),
                ) {
                    IconButton(
                        onClick = onClick,
                    ) {
                        Icon(
                            painter = icon,
                            contentDescription = contentDescription,
                            tint = tint ?: Color.Unspecified,
                        )
                    }
                }
            }
        }

        data class Text(
            val text: String,
            val style: TextStyle? = null,
            override val onClick: () -> Unit,
        ) : Action(onClick) {

            @Composable
            override fun Content() {
                Box {
                    Text(
                        text = text,
                        style = style ?: TdTheme.typography.titleTiny,
                        modifier = Modifier
                            .padding(end = TdTheme.dimens.standard8)
                            .clickable { onClick() },
                    )
                }
            }
        }
    }

    @Composable
    fun ArrowBackAction(
        tint: Color? = null,
        onClick: () -> Unit,
    ) = DefaultActions(
        icon = rememberVectorPainter(Icons.AutoMirrored.Filled.ArrowBack),
        onClick = onClick,
        tint = tint,
    )

    @Composable
    fun CloseAction(
        tint: Color? = null,
        onClick: () -> Unit,
    ) = DefaultActions(
        icon = rememberVectorPainter(Icons.Filled.Close),
        onClick = onClick,
        tint = tint,
    )

    @Composable
    private fun DefaultActions(
        icon: Painter,
        onClick: () -> Unit,
        tint: Color?,
    ) = Action.Icon(
        icon = icon,
        onClick = onClick,
        contentDescription = "Navigate Up",
        tint = tint,
    )

    sealed class Title {

        @Composable
        abstract fun Content()

        sealed class Default : Title() {

            data class Text(
                val text: String,
                val style: TextStyle? = null,
            ) : Default() {

                @Composable
                override fun Content() {
                    ToolbarTitle(text, style)
                }
            }

            data class Res(
                val stringRes: StringResource,
                val style: TextStyle? = null,
            ) : Default() {

                @Composable
                override fun Content() {
                    ToolbarTitle(stringResource(stringRes), style)
                }
            }
        }

        sealed class Secondary : Title() {

            data class Text(
                val title: String,
                val titleStyle: TextStyle? = null,
                val secondaryTitle: String,
                val secondaryTitleStyle: TextStyle? = null,
            ) : Secondary() {

                @Composable
                override fun Content() {
                    Column {
                        ToolbarTitle(title, titleStyle)
                        Spacer(modifier = Modifier.height(TdTheme.dimens.standard4))
                        ToolbarSecondaryTitle(secondaryTitle, secondaryTitleStyle)
                    }
                }
            }

            data class Res(
                val titleStringRes: StringResource,
                val titleStyle: TextStyle? = null,
                val secondaryTitleRes: StringResource,
                val secondaryTitleStyle: TextStyle? = null,
            ) : Secondary() {

                @Composable
                override fun Content() {
                    Column {
                        ToolbarTitle(stringResource(titleStringRes), titleStyle)
                        Spacer(modifier = Modifier.height(TdTheme.dimens.standard4))
                        ToolbarSecondaryTitle(stringResource(secondaryTitleRes), secondaryTitleStyle)
                    }
                }
            }
        }
    }
}
