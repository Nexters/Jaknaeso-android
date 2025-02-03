package com.jaknaeso.app.presentation.view

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles
import com.jaknaeso.app.presentation.navigation.LoopyBottomNavBar

@Composable
fun ProfileScreen(
    navigateToHome: () -> Unit,
    navigateToReport: () -> Unit,
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {}
    val termsOfServiceIntent = remember {
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.notion.so/leeyongin/18fc428aefed80a79a47f41610b3adab")
        )
    }
    val privacyPolicyIntent = remember {
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.notion.so/leeyongin/18fc428aefed8071aeebc2c85f0e7ad0")
        )
    }
    val openLicenses = remember {
        {
            launcher.launch(Intent(context, OssLicensesMenuActivity::class.java))
            OssLicensesMenuActivity.setActivityTitle("오픈소스 라이센스")
        }
    }
    val openPrivacyPolicyLink = remember { { context.startActivity(privacyPolicyIntent) } }
    val openTermsOfServiceLink = remember { { context.startActivity(termsOfServiceIntent) } }
    Scaffold(
        modifier = Modifier.fillMaxSize(1f).background(color = Color.White),
        bottomBar = {
            LoopyBottomNavBar(
                navigateToHome = { navigateToHome()},
                navigateToReport = { navigateToReport() },
                navigateToProfile = { }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .background(color = Color.White).fillMaxSize(1f)
                    .padding(vertical = 54.dp).padding(paddingValues),
                verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start
            ) {
                Text(text = "설정", style = TextStyles.title03, color = ColorPalette.Neautral900, modifier = Modifier.padding(start = 20.dp).padding(bottom = 40.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().height(47.dp).clickable { openTermsOfServiceLink() },
                    horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "이용 약관", style = TextStyles.subTitle04, color = ColorPalette.Neautral800, modifier = Modifier.padding(start = 20.dp))
                    Icon(painter = painterResource(R.drawable.ic_next), tint = Color.Black, contentDescription = null, modifier = Modifier.padding(end = 20.dp))
                }
                Divider(modifier = Modifier.fillMaxWidth())
                Row(
                    modifier = Modifier.fillMaxWidth().height(47.dp).clickable { openPrivacyPolicyLink() },
                    horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "개인정보처리방침", style = TextStyles.subTitle04, color = ColorPalette.Neautral800, modifier = Modifier.padding(start = 20.dp))
                    Icon(painter = painterResource(R.drawable.ic_next), tint = Color.Black, contentDescription = null, modifier = Modifier.padding(end = 20.dp))
                }
                Divider(modifier = Modifier.fillMaxWidth())
                Row(
                    modifier = Modifier.fillMaxWidth().height(47.dp).clickable { openLicenses() },
                    horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "오픈소스 라이센스", style = TextStyles.subTitle04, color = ColorPalette.Neautral800, modifier = Modifier.padding(start = 20.dp))
                    Icon(painter = painterResource(R.drawable.ic_next), tint = Color.Black, contentDescription = null, modifier = Modifier.padding(end = 20.dp))
                }
            }
        }
    )
}

@Preview
@Composable
fun PreviewProfileScreen() {
    ProfileScreen({}, {})
}
