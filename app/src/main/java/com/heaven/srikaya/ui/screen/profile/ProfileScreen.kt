package com.heaven.srikaya.ui.screen.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.heaven.srikaya.R

@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    Image(
                        painter = painterResource(R.drawable.pp_heaven),
                        contentDescription = "Avatar",
                        modifier = Modifier.size(60.dp).clip(CircleShape)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Heaven Valentine",
                        fontWeight = FontWeight.Bold,
                    )
                    Text("heavenvalentine49@gmail.com")
                }
            }

            LinkButton(text = "LinkedIn", url = "https://id.linkedin.com/in/heaven-valentine-225ab0247")
            LinkButton(text = "GitHub", url = "https://github.com/heavenvalentine2")
            LinkButton(text = "WhatsApp", url = "https://wa.me/qr/FEJLBA7YFHYHK1")
        }
    }
}

@Composable
fun LinkButton(text: String, url: String) {
    val context = LocalContext.current
    Button(
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        },
        modifier = Modifier
            .padding(vertical = 4.dp)
            .widthIn(min = 200.dp) // Set a fixed width for all buttons
    ) {
        Text(text = text)
    }
}
