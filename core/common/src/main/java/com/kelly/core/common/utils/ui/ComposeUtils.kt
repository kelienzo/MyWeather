package com.kelly.core.common.utils.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootLayout(
    topBarText: String? = null,
    topBarTextColor: Color = Color.White,
    topBarContainerColor: Color = Color.DarkGray,
    useCustomisedTopAppBar: Boolean? = null,
    background: Color = Color.DarkGray,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onBack: (() -> Unit)? = null,
    customisedTopAppBar: (@Composable () -> Unit)? = null,
    bottomBarContent: (@Composable () -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (topBarText != null && useCustomisedTopAppBar == null) {
                TopAppBar(
                    title = {
                        TextCompose(text = topBarText, fontSize = 16, textColor = topBarTextColor)
                    },
                    navigationIcon = {
                        onBack?.let {
                            IconButton(onClick = { it.invoke() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back Arrow",
                                    tint = Color.White
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = topBarContainerColor),
                    modifier = Modifier.shadow(
                        elevation = 10.dp,
                        ambientColor = Color.Black
                    )
                )
            } else if (useCustomisedTopAppBar == true) {
                customisedTopAppBar?.invoke()
            }
        },
        bottomBar = { bottomBarContent?.invoke() },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = background
    ) {
        content(it)
    }
}

@Composable
fun TextCompose(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Color.White,
    fontSize: Int = 8,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textOverflow: TextOverflow = TextOverflow.Clip,
    lineHeight: TextUnit = TextUnit.Unspecified,
    maxLines: Int = Int.MAX_VALUE
) {

    Text(
        modifier = modifier,
        text = text,
        color = textColor,
        fontSize = fontSize.sp,
        textDecoration = textDecoration,
        textAlign = textAlign,
        overflow = textOverflow,
        maxLines = maxLines,
        lineHeight = lineHeight
    )
}

@Composable
fun TextCompose(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    textColor: Color = Color.White,
    fontSize: Int = 8,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    textOverflow: TextOverflow = TextOverflow.Clip,
    lineHeight: TextUnit = TextUnit.Unspecified,
    maxLines: Int = Int.MAX_VALUE
) {

    Text(
        modifier = modifier,
        text = text,
        color = textColor,
        fontSize = fontSize.sp,
        textDecoration = textDecoration,
        textAlign = textAlign,
        overflow = textOverflow,
        maxLines = maxLines,
        lineHeight = lineHeight
    )
}

@Composable
fun ImageCompose(
    modifier: Modifier = Modifier,
    image: Int,
    contentDescription: String? = null,
    colorFilter: ColorFilter? = null
) {
    Image(
        modifier = modifier,
        painter = painterResource(id = image),
        contentDescription = contentDescription,
        colorFilter = colorFilter
    )
}

@Composable
fun SpacerHeightWidth(height: Int = 0, width: Int = 0) =
    Spacer(modifier = Modifier.size(height = height.dp, width = width.dp))

@Composable
fun ButtonCompose(
    modifier: Modifier = Modifier,
    buttonText: String,
    buttonTextColor: Color = Color.White,
    isEnabled: Boolean,
    buttonColor: Color = Color.Blue,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        enabled = isEnabled,
        contentPadding = PaddingValues(vertical = 15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            disabledContainerColor = buttonColor.copy(0.50f)
        )
    ) {
        TextCompose(text = buttonText, textColor = buttonTextColor, fontSize = 16)
    }
}

@Composable
fun CircularLoading(
    modifier: Modifier = Modifier,
    strokeWidth: Int = 5,
    color: Color = Color.White
) {
    CircularProgressIndicator(
        modifier = modifier,
        strokeWidth = strokeWidth.dp,
        color = color
    )
}

