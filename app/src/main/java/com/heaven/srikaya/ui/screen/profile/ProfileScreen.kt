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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.heaven.srikaya.R

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    Image(
                        painter = painterResource(R.drawable.pp_heaven),
                        contentDescription = stringResource(R.string.avatar),
                        modifier = modifier
                            .size(60.dp)
                            .clip(CircleShape)
                    )
                }
                Spacer(modifier = modifier.width(8.dp))
                Column {
                    Text(
                        text = stringResource(R.string.myName),
                        fontWeight = FontWeight.Bold,
                    )
                    Text(stringResource(R.string.heavenvalentine49_gmail_com))
                }
            }

            LinkButton(text = stringResource(R.string.linkedin), url = stringResource(R.string.linkedin_link))
            LinkButton(text = stringResource(R.string.github), url = stringResource(R.string.githubLink))
            LinkButton(text = stringResource(R.string.whatsapp), url = stringResource(R.string.whatsappLink))
        }
    }
}

@Composable
fun LinkButton(
    text: String,
    url: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Button(
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        },
        modifier = modifier
            .padding(vertical = 4.dp)
            .widthIn(min = 200.dp)
    ) {
        Text(text = text)
    }
}
