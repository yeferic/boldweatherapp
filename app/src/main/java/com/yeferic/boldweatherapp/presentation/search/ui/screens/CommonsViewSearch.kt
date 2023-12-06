package com.yeferic.boldweatherapp.presentation.search.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.yeferic.boldweatherapp.R
import com.yeferic.boldweatherapp.core.commons.Constants.EMPTY_STRING
import com.yeferic.boldweatherapp.core.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarSearchInputViewSearch(
    text: String,
    clearButtonAction: () -> Unit,
    onTextChange: (String) -> Unit,
) {
    var textState by remember { mutableStateOf(TextFieldValue(text)) }

    val focusRequester = FocusRequester()

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Purple40),
        title = {
            Row(Modifier.padding(end = 16.dp)) {
                Column {
                    BasicTextField(
                        value = textState,
                        onValueChange = {
                            textState = it
                            onTextChange(it.text)
                        },
                        singleLine = true,
                        maxLines = 1,
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(4.dp))
                            .focusRequester(focusRequester)
                            .height(36.dp)
                            .fillMaxWidth(),
                        decorationBox = { innerText ->
                            Row(
                                Modifier
                                    .weight(1f)
                                    .fillMaxSize()
                                    .background(color = Color.White)
                                    .padding(horizontal = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Column(
                                    Modifier
                                        .weight(1f),
                                ) {
                                    innerText()
                                }

                                if (textState.text.isNotEmpty()) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        tint = Color.Black,
                                        contentDescription = stringResource(id = R.string.icon_description),
                                        modifier = Modifier.clickable {
                                            onTextChange(EMPTY_STRING)
                                            textState = TextFieldValue(EMPTY_STRING)
                                            clearButtonAction()
                                        },
                                    )
                                }
                            }
                        },
                    )
                }
            }
        },
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}
