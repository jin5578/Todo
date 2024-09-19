package com.example.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.TodoTheme
import com.example.home.R

@Composable
internal fun NavigationDrawer(
    modifier: Modifier = Modifier,
    buildVersion: String,
) {
    Column(
        modifier = modifier.fillMaxWidth(0.8f)
            .fillMaxHeight()
            .padding(vertical = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(64.dp),
                painter = painterResource(R.drawable.svg_app_logo),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.app_name),
                style = TodoTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = stringResource(
                    R.string.buildVersion,
                    buildVersion
                ),
                style = TodoTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(
                top = 24.dp,
                bottom = 8.dp
            )
        )
    }
}