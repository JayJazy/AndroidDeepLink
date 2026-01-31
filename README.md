# DeepLink Practice App

Android Deep Link와 App Links 구현을 연습하기 위한 샘플 프로젝트입니다.

## 프로젝트 개요

이 프로젝트는 Android의 Deep Link와 App Links를 구현하는 방법을 보여줍니다.

## 주요 기능

- **Custom Scheme Deep Link**: `jay://` 스킴을 사용한 앱 내부 딥링크
- **App Links**: `https://jayjazy.github.io` 도메인을 사용한 웹 링크 연동
- **타입 안전한 라우팅**: Enum 기반의 딥링크 관리 시스템
- **자동 백스택 관리**: TaskStackBuilder를 활용한 네비게이션 스택 처리
- **Jetpack Compose UI**: 모든 화면을 Compose로 구현

## 지원 딥링크

### 1. Custom Scheme (jay://)
```
jay://main
jay://detail?DETAIL_ID=999
jay://setting?SETTING_ID=123
```

### 2. App Links (https://)
```
https://jayjazy.github.io/
https://jayjazy.github.io/detail?DETAIL_ID=999
https://jayjazy.github.io/setting?SETTING_ID=123
```

## 프로젝트 구조

```
app/src/main/java/com/jay/deeplinkapp/
├── base/
│   └── BaseActivity.kt                 # Compose 기반 Activity 추상 클래스
├── deeplink/
│   ├── DeepLinkActivity.kt            # 딥링크 진입점 Activity
│   ├── DeepLinkInfo.kt                # 딥링크 라우팅 Enum
│   └── DeepLinkInterface.kt           # 딥링크 인터페이스 정의
├── main/
│   ├── MainActivity.kt                # 메인 화면
│   └── DeepLinkBottomSheet.kt         # 딥링크 테스트용 Bottom Sheet
├── detail/
│   ├── DetailActivity.kt              # 상세 화면
│   └── viewmodel/DetailViewModel.kt
├── setting/
│   ├── SettingActivity.kt             # 설정 화면
│   └── viewmodel/SettingViewModel.kt
└── util/
    ├── copy.kt                        # 클립보드 유틸
    └── Log.kt                         # 로그 유틸
```

## 핵심 구현

### DeepLinkInfo Enum

딥링크 정보를 관리하는 핵심 클래스입니다:

```kotlin
enum class DeepLinkInfo(@StringRes val hostResId: Int) : DeepLinkBuilder, DeepLinkHandler {
    MAIN(R.string.scheme_host_main) {
        override fun buildDeepLink(vararg params: Pair<String, String>): String {
            return "${APP_LINK_BASE_URL}/main"
        }
    },
    DETAIL(R.string.scheme_host_detail) {
        override fun buildDeepLink(vararg params: Pair<String, String>): String {
            val query = params.joinToString("&") { "${it.first}=${it.second}" }
            return "${APP_LINK_BASE_URL}/detail?$query"
        }
    },
    // ...
}
```

**주요 기능:**
- URI를 분석하여 적절한 화면으로 라우팅
- 타입 안전한 딥링크 URL 생성
- Host 또는 Path 기반 매칭 지원

### DeepLinkActivity

모든 딥링크의 진입점 역할을 하는 Activity입니다:

**처리 흐름:**
1. URI 유효성 검증 (`isSupportedUri`)
2. URI를 분석하여 목적지 Activity Intent 생성
3. TaskStackBuilder를 사용하여 백스택 관리
4. 목적지 Activity로 이동

### BaseActivity

Jetpack Compose를 사용하는 Activity의 기본 클래스입니다:

```kotlin
abstract class BaseActivity : ComponentActivity() {
    @Composable
    protected abstract fun Content()

    protected fun extractDeepLinkParameter(key: String): String? {
        return intent.data?.getQueryParameter(key) ?: intent.getStringExtra(key)
    }
}
```

## App Links 설정

### 1. AndroidManifest.xml

```xml
<activity
    android:name=".deeplink.DeepLinkActivity"
    android:exported="true"
    android:launchMode="singleTask">

    <!-- Custom Scheme -->
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
        <data android:scheme="@string/app_scheme" />
    </intent-filter>

    <!-- App Links -->
    <intent-filter android:autoVerify="true">
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
        <data android:scheme="https" android:host="jayjazy.github.io" />
    </intent-filter>
</activity>
```

### 2. Digital Asset Links (assetlinks.json)

GitHub Pages를 사용하여 도메인 소유권을 인증합니다:

**위치:** `https://jayjazy.github.io/.well-known/assetlinks.json`

```json
[{
  "relation": ["delegate_permission/common.handle_all_urls"],
  "target": {
    "namespace": "android_app",
    "package_name": "com.jay.deeplinkapp",
    "sha256_cert_fingerprints": [
      "95:da:36:be:28:79:2e:a2:f4:ff:d8:93:7b:f6:ff:62:c7:e8:31:af:f8:96:4d:7b:1d:72:19:9a:d5:a7:3a:40"
    ]
  }
}]
```

**중요 사항:**
- `.well-known` 폴더는 도메인 루트에 위치해야 합니다
- GitHub Pages에서 Jekyll이 `.well-known` 폴더를 숨기지 않도록 `.nojekyll` 파일 추가 필요

### 3. SHA-256 지문 추출

```bash
keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
```

## 기술 스택

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 36
- **Build Tool**: Gradle (Kotlin DSL)
- **Architecture**: MVVM (ViewModel + Compose)

## 테스트 방법

### 1. 앱 내에서 테스트

메인 화면에서 "Detail 화면" 또는 "Setting 화면" 버튼을 클릭하면 Bottom Sheet가 열립니다:
- **딥링크 복사**: 클립보드에 딥링크 URL 복사
- **앱에서 열기**: 딥링크를 사용하여 바로 이동

### 2. 브라우저/메시지 앱에서 테스트

메시지 앱이나 메모장에 딥링크를 입력하고 클릭하면 앱으로 이동합니다.

## 도메인 검증 확인

```bash
# 도메인 검증 상태 확인
adb shell pm get-app-links com.jay.deeplinkapp

# 수동으로 도메인 승인 (필요시)
adb shell pm set-app-links --package com.jay.deeplinkapp 2 jayjazy.github.io
```

**검증 상태:**
- `1024`: 검증 실패
- `2048`: 검증 중
- `4096`: 검증 성공
- `approved`: 수동 승인됨

## 주의사항

### Android 보안 정책

브라우저 주소창에 URL을 직접 입력한 경우, Android 보안 정책상 자동으로 앱이 열리지 않습니다. 대신:
- 주소창 옆에 앱 아이콘이 표시됩니다.
- 사용자가 아이콘을 클릭하면 앱으로 이동합니다.

링크를 클릭한 경우(메시지, 메모, 웹페이지 등)에는 바로 앱이 열립니다.

### Debug vs Release 빌드

- Debug 빌드는 `debug.keystore`의 SHA-256 지문을 사용합니다.
- Release 빌드는 별도의 release keystore 지문을 `assetlinks.json`에 추가해야 합니다.

## 라이선스

이 프로젝트는 학습 목적으로 만들어진 샘플 앱입니다.
