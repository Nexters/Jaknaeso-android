package com.jaknaeso.app.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.component.DotBoard
import com.jaknaeso.app.designSystem.component.LoopyTopBar
import com.jaknaeso.app.designSystem.theme.TextStyles

@Composable
fun PrivateDataPolicyScreen(navigateToBack: () -> Unit) {
    val scrollState = rememberScrollState()
    Column(Modifier.fillMaxSize()) {
        LoopyTopBar(title = "개인정보처리방침", icon = painterResource(R.drawable.ic_back), onClickIcon = {navigateToBack()})
        Column(Modifier.verticalScroll(scrollState).padding(20.dp)) {
            DotBoard(titleContent = "1.개인정보 수집 항목", contentDescription = "당사는 회원가입 과정에서 다음과 같은 개인정보를 수집합니다:", contents = listOf("이름","이메일 주소"), contentsColor = Color.Black, contentsStyle = TextStyles.body01)
            Spacer(Modifier.height(16.dp))
            DotBoard(titleContent = "2.개인정보 수집 목적", contentDescription = "개인정보 수집 목적", contents = listOf("회원 계정 생성 및 서비스 이용 제공","고객 지원 및 문의 응대","서비스 관련 중요 공지 사항 전달"), contentsColor = Color.Black, contentsStyle = TextStyles.body01)
            Spacer(Modifier.height(16.dp))
            DotBoard(titleContent = "3.개인정보 보관 기간", contentDescription = "당사는 회원 탈퇴 시 또는 법령에 따른 보관 기간이 만료되면 즉시 개인정보를 삭제합니다. 단, 관련 법령에 의해 일정 기간 동안 보관이 필요한 경우, 해당 기간 동안 정보를 안전하게 보관합니다.", contents = emptyList(), contentsColor = Color.Black, contentsStyle = TextStyles.body01)
            Spacer(Modifier.height(16.dp))
            DotBoard(titleContent = "4.개인정보 보호 및 보안 조치", contentDescription = "당사는 다음과 같은 보안 조치를 통해 개인정보를 보호합니다:", contents = listOf("암호화된 데이터 저장: 사용자의 개인정보는 암호화하여 안전하게 저장됩니다.","보안 프로토콜 적용: 네트워크를 통한 데이터 전송 시 보안 프로토콜(SSL/TLS) 적용","접근 권한 제한: 개인정보 접근 권한을 최소화하여 관리"), contentsColor = Color.Black, contentsStyle = TextStyles.body01)
            Spacer(Modifier.height(16.dp))
            DotBoard(titleContent = "5.개인정보 제공 및 공유", contentDescription = "당사는 개인정보를 제3자에게 제공하거나 공유하지 않습니다. 단, 법적 요구가 있을 경우, 관련 법령에 따라 제공될 수 있습니다.", contents = emptyList(), contentsColor = Color.Black, contentsStyle = TextStyles.body01)
            Spacer(Modifier.height(16.dp))
            DotBoard(titleContent = "6.이용자의 권리", contentDescription = "이용자는 언제든지 자신의 개인정보를 확인, 수정, 삭제 요청할 수 있습니다. 개인정보 관련 문의는 아래 이메일을 통해 가능합니다.", contents = emptyList(), contentsColor = Color.Black, contentsStyle = TextStyles.body01)
            Spacer(Modifier.height(16.dp))
            DotBoard(titleContent = "7.연락처", contentDescription = "개인정보 보호 관련 문의 사항이 있으시면 아래 이메일로 문의해 주세요.", contents = listOf("이메일: app.jaknaeso@gmail.com"), contentsColor = Color.Black, contentsStyle = TextStyles.body01)

        }
    }
}

@Preview
@Composable
fun PrivateDataPolicyPreview() {
    PrivateDataPolicyScreen({})
}

