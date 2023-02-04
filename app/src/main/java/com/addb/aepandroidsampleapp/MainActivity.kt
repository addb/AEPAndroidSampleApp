package com.addb.aepandroidsampleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import com.addb.aepandroidsampleapp.ui.theme.AEPAndroidSampleAppTheme

import com.adobe.marketing.mobile.Assurance
import com.adobe.marketing.mobile.MobileCore
import com.adobe.marketing.mobile.Analytics
import com.adobe.marketing.mobile.Identity
import com.adobe.marketing.mobile.Audience

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AEPAndroidSampleAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Text(text = "Extensions: Analytics(${Analytics.extensionVersion()}), Audience(${Audience.extensionVersion()}), Core(${MobileCore.extensionVersion()}), Identity(${Identity.extensionVersion()})")
                    APIView()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AEPAndroidSampleAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Text(text = "Extensions: Analytics(${Analytics.extensionVersion()}), Audience(${Audience.extensionVersion()}), Core(${MobileCore.extensionVersion()}), Identity(${Identity.extensionVersion()})")
            APIView()
        }
    }
}

@Composable
fun AssuranceUI() {
    val focusManager = LocalFocusManager.current
    var assuranceUrl: String by remember { mutableStateOf("") }
    OutlinedTextField(value = assuranceUrl,
        onValueChange = {
            assuranceUrl = it
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.moveFocus(FocusDirection.Next)
            }
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(all = Dp(1F)),
        placeholder = { Text("Enter Assurance URL") }
    )

    Button(
        onClick = {
            Assurance.startSession(assuranceUrl)
        },
        modifier = Modifier.padding(all = Dp(1F)),
        border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
        shape = MaterialTheme.shapes.medium,
    ) {
        Text(text = "Connect Assurance")
    }
}

@Composable
fun APIView() {
    var outputMsg:String by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AssuranceUI()

        Button(
            onClick = {
                val version = Analytics.extensionVersion()
                outputMsg = "Analytics extension version: ($version)"
            },

            modifier = Modifier.padding(all = Dp(2F)),
            enabled = true,
            border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
            shape = MaterialTheme.shapes.medium,
        )
        {
            Text(text = "extensionVersion", color = Color.White)
        }

        Button(
            onClick = {
                MobileCore.trackAction("testActionKotlin", mutableMapOf("key" to "value"))
            },

            modifier = Modifier.padding(all = Dp(2F)),
            enabled = true,
            border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
            shape = MaterialTheme.shapes.medium,
        )
        {
            Text(text = "TrackAction", color = Color.White)
        }

        Button(
            onClick = {
                MobileCore.trackState("testStateKotlin", mutableMapOf("key" to "value"))
            },

            modifier = Modifier.padding(all = Dp(2F)),
            enabled = true,
            border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
            shape = MaterialTheme.shapes.medium,
        )
        {
            Text(text = "TrackState", color = Color.White)
        }

        Button(
            onClick = {
                val latch = CountDownLatch(1)
                var queueSize = 0L
                Analytics.getQueueSize { size ->
                    latch.countDown()
                    queueSize = size
                }
                latch.await(250, TimeUnit.MILLISECONDS)
                outputMsg = "getQueueSize = ($queueSize)"
            },

            modifier = Modifier.padding(all = Dp(2F)),
            enabled = true,
            border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
            shape = MaterialTheme.shapes.medium,
        )
        {
            Text(text = "getQueueSize", color = Color.White)
        }

        Button(
            onClick = {
                Analytics.clearQueue()
            },

            modifier = Modifier.padding(all = Dp(2F)),
            enabled = true,
            border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
            shape = MaterialTheme.shapes.medium,
        )
        {
            Text(text = "clearQueue", color = Color.White)
        }

        Button(
            onClick = {
                val latch = CountDownLatch(1)
                var aidValue = ""
                Analytics.getTrackingIdentifier { aid ->
                    latch.countDown()
                    aidValue = aid
                }
                latch.await(250, TimeUnit.MILLISECONDS)
                outputMsg = "getTrackingIdentifier = ($aidValue)"
            },

            modifier = Modifier.padding(all = Dp(2F)),
            enabled = true,
            border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
            shape = MaterialTheme.shapes.medium,
        )
        {
            Text(text = "getTrackingIdentifier", color = Color.White)
        }

        Button(
            onClick = {
                val latch = CountDownLatch(1)
                var vidString = ""
                Analytics.getVisitorIdentifier { vid ->
                    latch.countDown()
                    vidString = vid
                }
                latch.await(250, TimeUnit.MILLISECONDS)
                outputMsg = "getVisitorIdentifier = ($vidString)"
            },

            modifier = Modifier.padding(all = Dp(2F)),
            enabled = true,
            border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
            shape = MaterialTheme.shapes.medium,
        )
        {
            Text(text = "getVisitorIdentifier", color = Color.White)
        }

        val focusManager = LocalFocusManager.current
        var vid:String by remember { mutableStateOf("") }

        OutlinedTextField(value = vid,
            onValueChange = {vid = it
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.moveFocus(FocusDirection.Next)
                }
            ),
            label = { Text("Enter Visitor Identifier") }
        )

        Button(
            onClick = {
                Analytics.setVisitorIdentifier(vid)
            },
            modifier = Modifier.padding(all = Dp(1F)),
            border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
            shape = MaterialTheme.shapes.medium,
        ) {
            Text(text = "Update Visitor Identifier (VID)")
        }

        Text(text = outputMsg)
    }

}
