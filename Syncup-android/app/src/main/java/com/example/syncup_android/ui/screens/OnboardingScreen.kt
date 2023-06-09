package com.example.syncup_android.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.syncup_android.R
import com.example.syncup_android.data.onboardPages
import com.example.syncup_android.ui.components.OnboardingPageUI
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(onGetStartedClick: () -> Unit, modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(initialPage = 0);
    val scope = rememberCoroutineScope();

    Column(modifier = modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop,
            painter = painterResource(R.drawable.onboardingbg),
            contentDescription = null
        )
        HorizontalPager(count = 3, state = pagerState, modifier = Modifier.fillMaxWidth()) {
            OnboardingPageUI(onboardingPage = onboardPages[it])
        }
        Spacer(Modifier.weight(1f))

        //Indicators for the pages
        HorizontalPagerIndicator(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            pagerState = pagerState,
            activeColor = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.weight(1f))

        //Buttons for skip and next, only shown in the first 2 screens
        AnimatedVisibility(visible = pagerState.currentPage < 2) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 50.dp, end = 50.dp, bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ClickableText(
                    modifier = Modifier.padding(bottom = 20.dp),
                    style = TextStyle(color = MaterialTheme.colorScheme.primary),
                    text = AnnotatedString("Skip"),
                    onClick = { onGetStartedClick() })
                ClickableText(
                    modifier = Modifier.padding(bottom = 20.dp),
                    style = TextStyle(color = MaterialTheme.colorScheme.primary),
                    text = AnnotatedString("Next"),
                    onClick = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) } })
            }
        }

        //Button to get started
        AnimatedVisibility(visible = pagerState.currentPage == 2) {
            OutlinedButton(colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 50.dp, end = 50.dp, bottom = 20.dp),
                onClick = { onGetStartedClick() }) {
                Text(text = "Get started")
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingScreenPreview() {
    OnboardingScreen(onGetStartedClick = {})
}