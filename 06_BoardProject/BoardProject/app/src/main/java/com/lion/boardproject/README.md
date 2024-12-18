# 01_프로젝트 기본설정

### DataBinding 설정

[build.gradle.kts]
```kt
    dataBinding{
        enable = true
    }
```

```kt
dependencies {
    ...
    implementation("androidx.fragment:fragment-ktx:1.8.5")
}
```

### 아이콘 파일 복사
- 제공해드린 아이콘 파일들을 res/drawable 폴더에 넣어주세요

---

# 02_앱 아이콘 설정

### 프로젝트에서 마우스 우클릭 > new > Image Assets 도구를 통해 만들어준다.

### AndroidManifest.xml 에 아이콘을 등록해준다.

```xml
        android:icon="@mipmap/like_lion_icon"
        android:roundIcon="@mipmap/like_lion_icon_round"
```


### 어플 이름을 변경해준다.

[res/values/strings.xml]
```xml
    <string name="app_name">멋쟁이사자처럼게시판</string>
```

--- 

# 03_Splash Screen 구성

### 프로젝트에서 마우스 우클릭 > new > Image Assets 도구를 통해 아이콘을 만들어준다.

### 브랜드 이미지로 사용할 이미지를 drawable 폴더에 넣어준다.

### 라이브러리를 추가한다.

[build.gradle.kts]
```kt
implementation("androidx.core:core-splashscreen:1.0.1")
```

### SplashScreen용 테마를 작성한다.

[res/values/themes.xml]

```xml
    <style name="AppTheme.Splash" parent="Theme.Material3.DayNight.NoActionBar">
        <!-- 배경 색상 또는 이미지 -->
        <item name="windowSplashScreenBackground">@color/white</item>
        <!-- 중앙에 표시될 아이콘 이미지 -->
        <item name="windowSplashScreenAnimatedIcon">@mipmap/like_lion_logo</item>
        <!-- Splash Screen이 보여질 시간 -->
        <item name="windowSplashScreenAnimationDuration">1000</item>
        <!-- windowSplashScreenAnimationDuration 에서 지정한 시간이 끝나면 적용할 테마 -->
        <!-- AndroidManifest.xml 에 적용되어 있는 테마를 적용해준다 -->
        <item name="postSplashScreenTheme">@style/Theme.BoardProject</item>
    </style>
```

### MainActivity의 테마를 설정해준다.

[AndroidManifest.xml]
```xml
        <activity
            ...
            android:theme="@style/AppTheme.Splash">
```

### SplashScreen 관련 메서드를 호출해준다.

[MainActivity.kt - onCreate()]
```kt
        // SplashScreen 적용
        installSplashScreen()
```

---

# 04_회원관련 Activity 구성

### UserActivity를 만들어준다.

### MainActivity를 종료하고 UserActivity를 실행한다.

[MainActivity.kt]
```kt
        // UserActivity를 실행한다.
        val userIntent = Intent(this@MainActivity, UserActivity::class.java)
        startActivity(userIntent)
        // MainActivity를 종료한다.
        finish()
```

### UserActivity의 화면을 구성한다.

[res/layout/activity_user.xml]
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/main"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".UserActivity">

        <androidx.fragment.app.FragmentContainerView
            android:id="@+id/fragmentContainerViewUser"
            android:layout_width="0dp"
            android:layout_height="0dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
    </androidx.constraintlayout.widget.ConstraintLayout>

</layout>

```

### DataBinding 코드를 작성한다

[UserActivity.kt]
```kt
    lateinit var activityUserBinding:ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        activityUserBinding = DataBindingUtil.setContentView(this@UserActivity, R.layout.activity_user)
```

### 프래그먼트들의 이름을 가지고 있는 enum class를 작성한다.

[UserActivity.kt]
```kt
// 프래그먼트들을 나타내는 값들
enum class UserFragmentName(var number:Int, var str:String){

}
```

### 프래그먼트를 제거하는 메서드를 구현한다.

[UserActivity.kt]
```kt
    // 프래그먼트를 BackStack에서 제거하는 메서드
    fun removeFragment(fragmentName: UserFragmentName){
        supportFragmentManager.popBackStack(fragmentName.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
```

### 프래그먼트를 담을 변수를 선언한다.

[UserActivity.kt]
```kt
    // 현재 Fragment와 다음 Fragment를 담을 변수(애니메이션 이동 때문에...)
    var newFragment:Fragment? = null
    var oldFragment:Fragment? = null
```

### 프래그먼트 교체 메서드를 구현한다.

[UserActivity.kt]
```kt

    // 프래그먼트를 교체하는 함수
    fun replaceFragment(fragmentName: UserFragmentName, isAddToBackStack:Boolean, animate:Boolean, dataBundle: Bundle?){
        // newFragment가 null이 아니라면 oldFragment 변수에 담아준다.
        if(newFragment != null){
            oldFragment = newFragment
        }
        // 프래그먼트 객체
        newFragment = when(fragmentName){

        }

        // bundle 객체가 null이 아니라면
        if(dataBundle != null){
            newFragment?.arguments = dataBundle
        }

        // 프래그먼트 교체
        supportFragmentManager.commit {

            if(animate) {
                // 만약 이전 프래그먼트가 있다면
                if(oldFragment != null){
                    oldFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
                    oldFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
                }

                newFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
                newFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
                newFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
                newFragment?.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
            }

            replace(R.id.fragmentContainerViewUser, newFragment!!)
            if(isAddToBackStack){
                addToBackStack(fragmentName.str)
            }
        }
    }
```

--- 

# 05_로그인 화면 구성

### 두 개의 패키지를 만들어준다.
- fragment
- viewmodel

### LoginFragment를 만들어준다.

### LoginViewModel을 만들어준다.

[viewmodel/LoginViewModel.kt]
```kt
data class LoginViewModel(val loginFragment: LoginFragment) : ViewModel() {
}
```

### layout 태그로 묶어준다.

[res/layout/fragment_login.xml]

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <variable
            name="loginViewModel"
            type="com.lion.boardproject.viewmodel.LoginViewModel" />
    </data>

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".fragment.LoginFragment">

        <!-- TODO: Update blank fragment layout -->
        <TextView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:text="@string/hello_blank_fragment" />

    </FrameLayout>

</layout>

```

### 프래그먼트의 기본 코드를 작성한다.

[fragment/LoginFragment.kt]
```kt
class LoginFragment : Fragment() {

    lateinit var fragmentLoginBinding: FragmentLoginBinding
    lateinit var userActivity: UserActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        fragmentLoginBinding.loginViewModel = LoginViewModel(this@LoginFragment)
        fragmentLoginBinding.lifecycleOwner = this@LoginFragment

        userActivity = activity as UserActivity

        return fragmentLoginBinding.root
    }
}
```

### Fragment의 이름을 정의해준다.

[UserActivity.kt]
```kt
    // 로그인 화면
    USER_LOGIN_FRAGMENT(1, "UserLoginFragment"),
```

### Fragment의 객체를 생성한다.

[UserActivity.kt - replaceFragment()]
```kt
            // 로그인 화면
            UserFragmentName.USER_LOGIN_FRAGMENT -> LoginFragment()
```

### 첫 번째 프래그먼트를 설정해준다.

[UserActivity.kt - onCreate()]
```kt
        // 첫번째 Fragment를 설정한다.
        replaceFragment(UserFragmentName.USER_LOGIN_FRAGMENT, false, false, null)
```

### 입력 요소의 digit에 설정할 문자열을 만들어준다.

[res/values/stings.xml]
```xml 
    <!-- 영어, 숫자, 특수문자만 입력을 위한 문자열 -->
    <string name="digit_value">abcdefghijklmnopqrstuvwxyz0123456789_!@#</string>
```

### 화면을 구성한다.

[res/layout/fragment_login.xml]
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <variable
            name="loginViewModel"
            type="com.lion.boardproject.viewmodel.LoginViewModel" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:transitionGroup="true">

        <com.google.android.material.appbar.MaterialToolbar
            android:id="@+id/toolbarUserLogin"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@android:color/transparent"
            android:minHeight="?attr/actionBarSize"
            android:theme="?attr/actionBarTheme" />

        <ScrollView
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="vertical"
                android:padding="10dp" >

                <com.google.android.material.textfield.TextInputLayout
                    android:id="@+id/textFieldUserLoginId"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:hint="아이디"
                    app:endIconMode="clear_text"
                    app:startIconDrawable="@drawable/person_24px">

                    <com.google.android.material.textfield.TextInputEditText
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:digits="@string/digit_value"
                        android:singleLine="true"
                        android:textAppearance="@style/TextAppearance.AppCompat.Large" />
                </com.google.android.material.textfield.TextInputLayout>

                <com.google.android.material.textfield.TextInputLayout
                    android:id="@+id/textFieldUserLoginPw"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:hint="비밀번호"
                    app:endIconMode="password_toggle"
                    app:startIconDrawable="@drawable/key_24px"
                    android:layout_marginTop="10dp">

                    <com.google.android.material.textfield.TextInputEditText
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:digits="@string/digit_value"
                        android:singleLine="true"
                        android:inputType="text|textPassword"
                        android:textAppearance="@style/TextAppearance.AppCompat.Large" />
                </com.google.android.material.textfield.TextInputLayout>

                <CheckBox
                    android:id="@+id/checkBoxUserLoginAuto"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="10dp"
                    android:text="자동 로그인"
                    android:textAppearance="@style/TextAppearance.AppCompat.Large" />

                <Button
                    android:id="@+id/buttonUserLoginSubmit"
                    style="@style/Widget.Material3.Button.OutlinedButton"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="10dp"
                    android:text="로그인"
                    android:textAppearance="@style/TextAppearance.AppCompat.Large" />

                <com.google.android.material.divider.MaterialDivider
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="10dp" />

                <Button
                    android:id="@+id/buttonUserLoginJoin"
                    style="@style/Widget.Material3.Button.OutlinedButton"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="10dp"
                    android:text="회원 가입"
                    android:textAppearance="@style/TextAppearance.AppCompat.Large" />
            </LinearLayout>
        </ScrollView>
    </LinearLayout>

</layout>

```

### LiveData를 작성한다.

[viewmodel/LoginViewModel.kt]
```kt
    // toolbarUserLogin - title
    val toolbarUserLoginTitle = MutableLiveData<String>()
```

### layout 파일의 툴바에 LiveData를 설정해준다.

[res/layout/fragment_login.xml]
```xml
        <com.google.android.material.appbar.MaterialToolbar
            ...
            app:title="@{loginViewModel.toolbarUserLoginTitle}"/>
```

### 툴바를 구성하는 메서드를 구현한다.
[fragment/LoginFragment.kt]
```kt
    // 툴바를 구성하는 메서드
    fun settingToolbar(){
        fragmentLoginBinding.loginViewModel?.apply {
            toolbarUserLoginTitle.value = "로그인"
        }
    }
```

### 메서드를 호출한다.
[fragment/LoginFragment.kt - onCreateView()]
```kt
        // 툴바를 구성하는 메서드 호출
        settingToolbar()
```

--- 

# 06 회원 가입 화면 구성 1

### Fragment와 ViewModel을 만들어준다.
- UserJoinStep1Fragment
- UserJoinStep1ViewModel

### ViewModel의 코드를 작성한다.

[viewmodel/UserJoinStep1ViewModel.kt]
```kt
data class UserJoinStep1ViewModel(val userJoinStep1Fragment: UserJoinStep1Fragment) : ViewModel(){
}
```

### 화면을 구성해준다.

[res/layout/fragment_user_join_step1.xml]
```xml
<?xml version="1.0" encoding="utf-8"?>

<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <variable
            name="userJoinStep1ViewModel"
            type="com.lion.boardproject.viewmodel.UserJoinStep1ViewModel" />
    </data>

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".fragment.UserJoinStep1Fragment">

        <!-- TODO: Update blank fragment layout -->
        <TextView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:text="@string/hello_blank_fragment" />

    </FrameLayout>

</layout>

```

### Fragment의 기본 코드를 작성한다.

[fragment/UserJoinStep1Fragment.kt]
```kt
class UserJoinStep1Fragment : Fragment() {

    lateinit var fragmentUserJoinStep1Binding: FragmentUserJoinStep1Binding
    lateinit var userActivity: UserActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentUserJoinStep1Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_join_step1, container, false)
        fragmentUserJoinStep1Binding.userJoinStep1ViewModel = UserJoinStep1ViewModel(this@UserJoinStep1Fragment)
        fragmentUserJoinStep1Binding.lifecycleOwner = this@UserJoinStep1Fragment

        userActivity = activity as UserActivity

        return fragmentUserJoinStep1Binding.root
    }

}
```

### Fragment의 이름을 정의한다.

[UserActivity.kt - UserFragmentName]
```kt
    // 회원 가입 화면
    USER_JOIN_STEP1_FRAGMENT(2, "UserJoinStep1Fragment"),
```

### Fragment의 객체를 생성한다.

[UserActivity.kt - replaceFragment()]
```kt
            // 회원 가입 과정1 화면
            UserFragmentName.USER_JOIN_STEP1_FRAGMENT -> UserJoinStep1Fragment()
```

### UserJoinStep1Fragment로 이동할 수 있는 메서드를 구현한다.
[fragment/LoginFragment.kt]
```kt
    // 회원 가입 화면으로 이동시키는 메서드
    fun moveToUserJoinStep1(){
        userActivity.replaceFragment(UserFragmentName.USER_JOIN_STEP1_FRAGMENT, true, true, null)
    }
```

### 버튼과 연결되는 메서드를 구현한다.
[viewmodel/LoginViewModel.kt]
```kt
    // buttonUserLoginJoin - onClick
    fun buttonUserLoginJoinOnClick(){
        loginFragment.moveToUserJoinStep1()
    }
```

### 버튼의 onclick 속성에 메서드 호출을 설정해준다.

[res/layout/fragment_login.xml]
```xml
    <Button
        ...
        android:onClick="@{(view) -> loginViewModel.buttonUserLoginJoinOnClick()}"/>
```

### 화면을 구성한다.
[res/layout/fragment_user_join_step1.xml]
```xml
<?xml version="1.0" encoding="utf-8"?>

<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <data>
        <variable
            name="userJoinStep1ViewModel"
            type="com.lion.boardproject.viewmodel.UserJoinStep1ViewModel" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:transitionGroup="true">

        <com.google.android.material.appbar.MaterialToolbar
            android:id="@+id/toolbarUserJoinStep1"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@android:color/transparent"
            android:minHeight="?attr/actionBarSize"
            android:theme="?attr/actionBarTheme"/>

        <ScrollView
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="vertical"
                android:padding="10dp" >

                <com.google.android.material.textfield.TextInputLayout
                    android:id="@+id/textFieldUserJoinStep1Id"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:hint="아이디"
                    app:endIconMode="clear_text"
                    app:startIconDrawable="@drawable/person_24px">

                    <com.google.android.material.textfield.TextInputEditText
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:digits="@string/digit_value"
                        android:singleLine="true"
                        android:textAppearance="@style/TextAppearance.AppCompat.Large" />
                </com.google.android.material.textfield.TextInputLayout>

                <com.google.android.material.textfield.TextInputLayout
                    android:id="@+id/textFieldUserJoinStep1Pw1"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:hint="비밀번호"
                    app:endIconMode="password_toggle"
                    app:startIconDrawable="@drawable/key_24px"
                    android:layout_marginTop="10dp">

                    <com.google.android.material.textfield.TextInputEditText
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:digits="@string/digit_value"
                        android:singleLine="true"
                        android:inputType="text|textPassword"
                        android:textAppearance="@style/TextAppearance.AppCompat.Large" />
                </com.google.android.material.textfield.TextInputLayout>

                <com.google.android.material.textfield.TextInputLayout
                    android:id="@+id/textFieldUserJoinStep1Pw2"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:hint="비밀번호 확인"
                    app:endIconMode="password_toggle"
                    app:startIconDrawable="@drawable/key_24px"
                    android:layout_marginTop="10dp">

                    <com.google.android.material.textfield.TextInputEditText
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:digits="@string/digit_value"
                        android:singleLine="true"
                        android:inputType="text|textPassword"
                        android:textAppearance="@style/TextAppearance.AppCompat.Large" />
                </com.google.android.material.textfield.TextInputLayout>

                <Button
                    android:id="@+id/buttonUserJoinStep1Next"
                    style="@style/Widget.Material3.Button.OutlinedButton"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="10dp"
                    android:text="다음"
                    android:textAppearance="@style/TextAppearance.AppCompat.Large" />

            </LinearLayout>
        </ScrollView>

    </LinearLayout>

</layout>

```

### ViewModel에 Live 데이터를 정의한다.

[viewmodel/userJoinStep1ViewModel.kt]
```kt
    // toolbarUserLogin - title
    val toolbarUserLoginTitle = MutableLiveData<String>()
    // toolbarUserLogin - navigationIcon
    val toolbarUserLoginNavigationIcon = MutableLiveData<Int>()
```

### Live 데이터와 UI 요소의 속성과 연결해준다.

[res/layout/fragment_user_join_step1.xml]
```xml
        <com.google.android.material.appbar.MaterialToolbar
            ...
            app:title="@{userJoinStep1ViewModel.toolbarUserLoginTitle}"
            app:navigationIcon="@{userJoinStep1ViewModel.toolbarUserLoginNavigationIcon}"/>
```

### Toolbar를 구성하는 메서드를 작성한다

[fragment/UserJoinStep1Fragment.kt]
```kt
    fun settingToolbar(){
        fragmentUserJoinStep1Binding.userJoinStep1ViewModel?.apply { 
            // 타이틀
            toolbarUserLoginTitle.value = "회원 가입"
            // 네비게이션 아이콘
            toolbarUserLoginNavigationIcon.value = R.drawable.arrow_back_24px
        }
    }
```

### 메서드를 호출한다.
[fragment/UserJoinStep1Fragment.kt - onCreateView()]
```kt
        // 툴바를 구성하는 메서드를 호출한다.
        settingToolbar()
```

### 네비게이션 아이콘을 클릭했을때 뒤로 가는 기능을 구현한다.
- toolbar의 속성에는 네비게이션 아이콘 클릭에 대한 리스너 속성이 없다.
- 이번 작업은 없는 속성을 만들어주는 작업을 해본다.

- build.gradle.kts 에 kept 설정을 해준다.
[build.gradle.kts]
```kt
plugins{
    ....
    kotlin("kapt")
} 
```

- ViewModel에 호출될 메서드를 작성해준다.

[viewmoddel/UserJoinStep1ViewModel.kt]
```kt
    companion object{
        // toolbarUserJoinStep1 - onNavigationClickUserJoinStep1
        @JvmStatic
        // xml 에 설정할 속성이름을 설정한다.
        @BindingAdapter("onNavigationClickUserJoinStep1")
        // 호출되는 메서드를 구현한다.
        // 첫 번째 매개변수 : 이 속성이 설정되어 있는 View 객체
        // 그 이후 : 전달해주는 값이 들어온다. xml 에서는 ViewModel이 가는 프로퍼티에 접근할 수 있기 때문에
        // 이것을 통해 Fragment객체를 받아 사용할것이다.
        fun onNavigationClickUserJoinStep1(materialToolbar:MaterialToolbar, userJoinStep1Fragment: UserJoinStep1Fragment){
            materialToolbar.setNavigationOnClickListener {
                // 이전으로 돌아간다.
                userJoinStep1Fragment.userActivity.removeFragment(UserFragmentName.USER_JOIN_STEP1_FRAGMENT)
            }
        }
    }
```

### layout에 해당 속성을 적용해준다.

[res/layout/fragment_user_join_step1.xml]
```xml
app:onNavigationClickUserJoinStep1="@{userJoinStep1ViewModel.userJoinStep1Fragment}"
```

---

# 07_회원 가입 단계 1의 입력 요소 처리

### 입력 요소와 연결할 MutableLiveData를 정의해준다.

[viewmodel/UserJoinStep1ViewModel.kt]
```kt
    // textFieldUserJoinStep1Id - EditText - text
    val textFieldUserJoinStep1IdEditTextText = MutableLiveData("")
    // textFieldUserJoinStep1Pw1 - EditText - text
    val textFieldUserJoinStep1Pw1EditTextText = MutableLiveData("")
    // textFieldUserJoinStep1Pw2 - EditText - text
    val textFieldUserJoinStep1Pw2EditTextText = MutableLiveData("")
```

### layout 파일에 LiveData를 설정해준다.

```xml
                <com.google.android.material.textfield.TextInputLayout
                    android:id="@+id/textFieldUserJoinStep1Id"
                    ...

                    <com.google.android.material.textfield.TextInputEditText
                        ...
                        android:text="@={userJoinStep1ViewModel.textFieldUserJoinStep1IdEditTextText}"/>
                </com.google.android.material.textfield.TextInputLayout>

                <com.google.android.material.textfield.TextInputLayout
                    android:id="@+id/textFieldUserJoinStep1Pw1"
                    ...

                    <com.google.android.material.textfield.TextInputEditText
                        ...
                        android:text="@={userJoinStep1ViewModel.textFieldUserJoinStep1Pw1EditTextText}"/>
                </com.google.android.material.textfield.TextInputLayout>

                <com.google.android.material.textfield.TextInputLayout
                    android:id="@+id/textFieldUserJoinStep1Pw2"
                    ...

                    <com.google.android.material.textfield.TextInputEditText
                        ...
                        android:text="@={userJoinStep1ViewModel.textFieldUserJoinStep1Pw2EditTextText}"/>
                </com.google.android.material.textfield.TextInputLayout>
```

### 키보드 관련 메서드를 넣어준다.
[UserActivity.kt]

```kt
// 키보드 올리는 메서드
fun showSoftInput(view: View){
    // 입력을 관리하는 매니저
    val inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    // 포커스를 준다.
    view.requestFocus()

    thread {
        SystemClock.sleep(500)
        // 키보드를 올린다.
        inputManager.showSoftInput(view, 0)
    }
}
// 키보드를 내리는 메서드
fun hideSoftInput(){
    // 포커스가 있는 뷰가 있다면
    if(currentFocus != null){
        // 입력을 관리하는 매니저
        val inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        // 키보드를 내린다.
        inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        // 포커스를 해제한다.
        currentFocus?.clearFocus()
    }
}
```

### Dialog를 띄우는 메서드를 구현한다.

[UserActivity.kt]
```kt
    // 다이얼로그를 통해 메시지를 보여주는 함수
    fun showMessageDialog(title:String, message:String, posTitle:String, callback:()-> Unit){
        val builder = MaterialAlertDialogBuilder(this@UserActivity)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(posTitle){ dialogInterface: DialogInterface, i: Int ->
            callback()
        }
        builder.show()
    }
```

### 버튼을 누르면 호출될 메서드를 구현해준다.

[viewmodel/UserJoinStep1ViewModel.kt]
```kt
    // buttonUserJoinStep1Next - onClick
    fun buttonUserJoinStep1NextOnClick(){
        userJoinStep1Fragment.apply {
            // 입력요소 검사
            if(textFieldUserJoinStep1IdEditTextText.value?.isEmpty()!!){
                userActivity.showMessageDialog("아이디 입력", "아이디를 입력해주세요", "확인"){
                    userActivity.showSoftInput(fragmentUserJoinStep1Binding.textFieldUserJoinStep1Id.editText!!)
                }
                return
            }
            if(textFieldUserJoinStep1Pw1EditTextText.value?.isEmpty()!!){
                userActivity.showMessageDialog("비밀번호 입력", "비밀번호를 입력해주세요", "확인"){
                    userActivity.showSoftInput(fragmentUserJoinStep1Binding.textFieldUserJoinStep1Pw1.editText!!)
                }
                return
            }
            if(textFieldUserJoinStep1Pw2EditTextText.value?.isEmpty()!!){
                userActivity.showMessageDialog("비밀번호 입력", "비밀번호를 입력해주세요", "확인"){
                    userActivity.showSoftInput(fragmentUserJoinStep1Binding.textFieldUserJoinStep1Pw2.editText!!)
                }
                return
            }
            if(textFieldUserJoinStep1Pw1EditTextText.value != textFieldUserJoinStep1Pw2EditTextText.value){
                userActivity.showMessageDialog("비밀번호 입력", "비밀번호가 다릅니다", "확인"){
                    textFieldUserJoinStep1Pw1EditTextText.value = ""
                    textFieldUserJoinStep1Pw2EditTextText.value = ""
                    userActivity.showSoftInput(fragmentUserJoinStep1Binding.textFieldUserJoinStep1Pw1.editText!!)
                }
                return
            }
        }
    }
```

### 메서드를 버튼의 onclick 속성과 연결해준다.

```xml
                <Button
                    ...
                    android:onClick="@{(view) -> userJoinStep1ViewModel.buttonUserJoinStep1NextOnClick()}"/>
```

---

# 08_회원 추가 정보 입력 화면 만들기

### viewmodel/UserJoinStep2ViewModel 를 만들어준다.

### fragment/UserJoinStep2Fragment 를 만들어준다.

### viewmodel/UserJoinStep2ViewModel 의 기본 코드를 작성한다.

```kt
class UserJoinStep2ViewModel(val userJoinStep2Fragment: UserJoinStep2Fragment) : ViewModel() {
}
```

### layout을 수정한다.

[res/layout/fragment_user_join_step2.xml]
```xml
<?xml version="1.0" encoding="utf-8"?>

<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <variable
            name="userJoinStep2ViewModel"
            type="com.lion.boardproject.viewmodel.UserJoinStep2ViewModel" />
    </data>

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".fragment.UserJoinStep2Fragment">

        <!-- TODO: Update blank fragment layout -->
        <TextView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:text="@string/hello_blank_fragment" />

    </FrameLayout>
</layout>


```

### Fragment의 기본 코드를 작성한다.

[fragment/UserJoinStep2Fragment.kt]
```kt
class UserJoinStep2Fragment : Fragment() {

    lateinit var fragmentUserJoinStep2Binding: FragmentUserJoinStep2Binding
    lateinit var userActivity: UserActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentUserJoinStep2Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_join_step2, container, false)
        fragmentUserJoinStep2Binding.userJoinStep2ViewModel = UserJoinStep2ViewModel(this@UserJoinStep2Fragment)
        fragmentUserJoinStep2Binding.lifecycleOwner = this@UserJoinStep2Fragment

        userActivity = activity as UserActivity

        return fragmentUserJoinStep2Binding.root
    }

}
```

### 프래그먼트의 이름을 정의해준다.

[UserActivity.kt]
```kt
    // 회원 가입 화면 2
    USER_JOIN_STEP2_FRAGMENT(3, "UserJoinStep2Fragment"),
```

### 프래그먼트 객체를 생성한다.

[UserActivity.kt]
```kt
            // 회원 가입 과정2 화면
            UserFragmentName.USER_JOIN_STEP2_FRAGMENT -> UserJoinStep2Fragment()
```

### 다음 화면으로 이동하는 메서드를 구현해준다.

[fragment/UserJoinStep1Fragment.kt]
```kt
    // 다음 입력 화면으로 이동한다.
    fun moveToUserJoinStep2(){
        userActivity.replaceFragment(UserFragmentName.USER_JOIN_STEP2_FRAGMENT, true, true, null)
    }
```

### 메서드를 호출한다.

[viewmodel/UserJoinStep1ViewModel.kt]
```kt
            // 다음 화면으로 이동한다.
            moveToUserJoinStep2()
```

--- 

# 09_회면 추가 정보 입력 화면 구성

### 화면을 구성한다.

[res/layout/fragment_user_join_step2.xml]
```xml
<?xml version="1.0" encoding="utf-8"?>

<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <data>
        <variable
            name="userJoinStep2ViewModel"
            type="com.lion.boardproject.viewmodel.UserJoinStep2ViewModel" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:transitionGroup="true" >

        <com.google.android.material.appbar.MaterialToolbar
            android:id="@+id/toolbarUserJoinStep2"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@android:color/transparent"
            android:minHeight="?attr/actionBarSize"
            android:theme="?attr/actionBarTheme"
            app:navigationIcon="@drawable/arrow_back_24px"
            app:title="회원 가입" />


        <ScrollView
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="vertical"
                android:padding="10dp" >

                <com.google.android.material.textfield.TextInputLayout
                    android:id="@+id/textFieldUserJoinStep2NickName"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:hint="닉네임"
                    app:endIconMode="clear_text"
                    app:startIconDrawable="@drawable/person_add_24px">

                    <com.google.android.material.textfield.TextInputEditText
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:singleLine="true"
                        android:textAppearance="@style/TextAppearance.AppCompat.Large"
                        android:text="@={userJoinStep2ViewModel.textFieldUserJoinStep2NickNameEditTextText}"/>
                </com.google.android.material.textfield.TextInputLayout>

                <Button
                    android:id="@+id/buttonUserJoinStep2CheckNickName"
                    style="@style/Widget.Material3.Button.OutlinedButton"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="10dp"
                    android:text="닉네임 중복 확인"
                    android:textAppearance="@style/TextAppearance.AppCompat.Large"/>


                <com.google.android.material.textfield.TextInputLayout
                    android:id="@+id/textFieldUserJoinStep2Age"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:hint="나이"
                    app:endIconMode="clear_text"
                    app:startIconDrawable="@drawable/face_24px"
                    android:layout_marginTop="10dp">

                    <com.google.android.material.textfield.TextInputEditText
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:singleLine="true"
                        android:inputType="number|numberDecimal"
                        android:textAppearance="@style/TextAppearance.AppCompat.Large"
                        android:text="@={userJoinStep2ViewModel.textFieldUserJoinStep2AgeEditTextText}"/>
                </com.google.android.material.textfield.TextInputLayout>

                <com.google.android.material.divider.MaterialDivider
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="10dp"
                    app:dividerColor="@color/black"/>

                <com.google.android.material.checkbox.MaterialCheckBox
                    android:id="@+id/checkBoxUserJoinStep2HobbyAll"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="10dp"
                    android:text="취미"
                    android:textAppearance="@style/TextAppearance.AppCompat.Large"
                    app:checkedState="@{userJoinStep2ViewModel.checkBoxUserJoinStep2HobbyAllCheckedState}" />

                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="10dp"
                    android:orientation="vertical">

                    <LinearLayout
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:layout_marginStart="20dp"
                        android:layout_marginTop="10dp"
                        android:orientation="horizontal">

                        <CheckBox
                            android:id="@+id/checkBoxUserJoinStep2Hobby1"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:layout_weight="1"
                            android:text="게임"
                            android:checked="@={userJoinStep2ViewModel.checkBoxUserJoinStep2Hobby1Checked}"/>

                        <CheckBox
                            android:id="@+id/checkBoxUserJoinStep2Hobby2"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:layout_weight="1"
                            android:text="독서"
                            android:checked="@={userJoinStep2ViewModel.checkBoxUserJoinStep2Hobby2Checked}"/>

                        <CheckBox
                            android:id="@+id/checkBoxUserJoinStep2Hobby3"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:layout_weight="1"
                            android:text="요리"
                            android:checked="@={userJoinStep2ViewModel.checkBoxUserJoinStep2Hobby3Checked}"/>
                    </LinearLayout>

                    <LinearLayout
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:layout_marginStart="20dp"
                        android:layout_marginTop="10dp"
                        android:orientation="horizontal">

                        <CheckBox
                            android:id="@+id/checkBoxUserJoinStep2Hobby4"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:layout_weight="1"
                            android:text="낚시"
                            android:checked="@={userJoinStep2ViewModel.checkBoxUserJoinStep2Hobby4Checked}"/>

                        <CheckBox
                            android:id="@+id/checkBoxUserJoinStep2Hobby5"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:layout_weight="1"
                            android:text="영화감상"
                            android:checked="@={userJoinStep2ViewModel.checkBoxUserJoinStep2Hobby5Checked}"/>

                        <CheckBox
                            android:id="@+id/checkBoxUserJoinStep2Hobby6"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:layout_weight="1"
                            android:text="기타"
                            android:checked="@={userJoinStep2ViewModel.checkBoxUserJoinStep2Hobby6Checked}"/>
                    </LinearLayout>
                </LinearLayout>

                <com.google.android.material.divider.MaterialDivider
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="10dp"
                    app:dividerColor="@color/black"/>

                <Button
                    android:id="@+id/buttonUserJoinStep2Submit"
                    style="@style/Widget.Material3.Button.OutlinedButton"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="10dp"
                    android:text="가입 완료"
                    android:textAppearance="@style/TextAppearance.AppCompat.Large"/>
            </LinearLayout>

        </ScrollView>
    </LinearLayout>
</layout>


```

### MutableLiveData를 정의한다.

[viewmodel/UserJoinStep2ViewModel.kt]
```kt
    // textFieldUserJoinStep2NickName - EditText - text
    val textFieldUserJoinStep2NickNameEditTextText = MutableLiveData("")
    // textFieldUserJoinStep2Age - EditExt - text
    val textFieldUserJoinStep2AgeEditTextText = MutableLiveData("")
    // checkBoxUserJoinStep2HobbyAll - checkedState
    val checkBoxUserJoinStep2HobbyAllCheckedState = MutableLiveData(MaterialCheckBox.STATE_UNCHECKED)
    // checkBoxUserJoinStep2Hobby1 - checked
    val checkBoxUserJoinStep2Hobby1Checked = MutableLiveData(false)
    // checkBoxUserJoinStep2Hobby2 - checked
    val checkBoxUserJoinStep2Hobby2Checked = MutableLiveData(false)
    // checkBoxUserJoinStep2Hobby3 - checked
    val checkBoxUserJoinStep2Hobby3Checked = MutableLiveData(false)
    // checkBoxUserJoinStep2Hobby4 - checked
    val checkBoxUserJoinStep2Hobby4Checked = MutableLiveData(false)
    // checkBoxUserJoinStep2Hobby5 - checked
    val checkBoxUserJoinStep2Hobby5Checked = MutableLiveData(false)
    // checkBoxUserJoinStep2Hobby6 - checked
    val checkBoxUserJoinStep2Hobby6Checked = MutableLiveData(false)
```

### LiveData와 View 의 속성들과 연결해준다.

```xml

                <com.google.android.material.textfield.TextInputLayout
                    android:id="@+id/textFieldUserJoinStep2NickName"
                   ...
                    <com.google.android.material.textfield.TextInputEditText
                        ...
                        android:text="@={userJoinStep2ViewModel.textFieldUserJoinStep2NickNameEditTextText}"/>
                </com.google.android.material.textfield.TextInputLayout>

               ...
                <com.google.android.material.textfield.TextInputLayout
                    android:id="@+id/textFieldUserJoinStep2Age"
                    ...
                    <com.google.android.material.textfield.TextInputEditText
                        ...
                        android:text="@={userJoinStep2ViewModel.textFieldUserJoinStep2AgeEditTextText}"/>
                </com.google.android.material.textfield.TextInputLayout>

                ...
                
                <com.google.android.material.checkbox.MaterialCheckBox
                    android:id="@+id/checkBoxUserJoinStep2HobbyAll"
                    ...
                    app:checkedState="@{userJoinStep2ViewModel.checkBoxUserJoinStep2HobbyAllCheckedState}" />
                    ...
                        <CheckBox
                            android:id="@+id/checkBoxUserJoinStep2Hobby1"
                            ...
                            android:checked="@={userJoinStep2ViewModel.checkBoxUserJoinStep2Hobby1Checked}"/>

                        <CheckBox
                            android:id="@+id/checkBoxUserJoinStep2Hobby2"
                            ...
                            android:checked="@={userJoinStep2ViewModel.checkBoxUserJoinStep2Hobby2Checked}"/>

                        <CheckBox
                            android:id="@+id/checkBoxUserJoinStep2Hobby3"
                            ...
                            android:checked="@={userJoinStep2ViewModel.checkBoxUserJoinStep2Hobby3Checked}"/>
                    </LinearLayout>

                    ...

                        <CheckBox
                            android:id="@+id/checkBoxUserJoinStep2Hobby4"
                           ...
                            android:checked="@={userJoinStep2ViewModel.checkBoxUserJoinStep2Hobby4Checked}"/>

                        <CheckBox
                            android:id="@+id/checkBoxUserJoinStep2Hobby5"
                            ...
                            android:checked="@={userJoinStep2ViewModel.checkBoxUserJoinStep2Hobby5Checked}"/>

                        <CheckBox
                            android:id="@+id/checkBoxUserJoinStep2Hobby6"
                           ...
                            android:checked="@={userJoinStep2ViewModel.checkBoxUserJoinStep2Hobby6Checked}"/>
                    </LinearLayout>
                </LinearLayout>
                ...


```

### 취미 체크박스 구현을 위한 LiveData를 추가한다.
- checkedState는 단방향 (@) 만 지원하기 때문에 checked 속성을 추가로 이용한다.

[viewmodel/UserJoinStep2ViewModel.kt]
```kt
    // checkBoxUserJoinStep2HobbyAll - checked
    val checkBoxUserJoinStep2HobbyAllChecked = MutableLiveData(false)
```

### LiveData와 연결해준다.

[res/layout/fragment_user_join_step2.xml]
```xml
                <com.google.android.material.checkbox.MaterialCheckBox
                    android:id="@+id/checkBoxUserJoinStep2HobbyAll"
                    ...
                    android:checked="@={userJoinStep2ViewModel.checkBoxUserJoinStep2HobbyAllChecked}"/>
```

### 체크 상태에 따라 다른 체크박스 체크 상태를 설정하는 메서드를 만들어준다.

[viewmodel/UserJoinStep2ViewModel.kt]
```kt
    // checkBoxUserJoinStep2HobbyAll - onClick
    fun checkBoxUserJoinStep2HobbyAllOnClick(){
        // 체크되어 있다면
        if(checkBoxUserJoinStep2HobbyAllChecked.value == true){
            checkBoxUserJoinStep2Hobby1Checked.value = true
            checkBoxUserJoinStep2Hobby2Checked.value = true
            checkBoxUserJoinStep2Hobby3Checked.value = true
            checkBoxUserJoinStep2Hobby4Checked.value = true
            checkBoxUserJoinStep2Hobby5Checked.value = true
            checkBoxUserJoinStep2Hobby6Checked.value = true
        } else {
            checkBoxUserJoinStep2Hobby1Checked.value = false
            checkBoxUserJoinStep2Hobby2Checked.value = false
            checkBoxUserJoinStep2Hobby3Checked.value = false
            checkBoxUserJoinStep2Hobby4Checked.value = false
            checkBoxUserJoinStep2Hobby5Checked.value = false
            checkBoxUserJoinStep2Hobby6Checked.value = false
        }
    }
```

### layout에 적용해준다.

[res/layout/fragment_user_join_step2.xml]
```xml
                <com.google.android.material.checkbox.MaterialCheckBox
                    android:id="@+id/checkBoxUserJoinStep2HobbyAll"
                    ...
                    android:onClick="@{(view) -> userJoinStep2ViewModel.checkBoxUserJoinStep2HobbyAllOnClick()}"/>
```

### 각 체크박스를 눌렀을 때 호출될 메서드를 구현한다.
[viewmodel/UserJoinStep2ViewModel.kt]
```kt
    // 하위 취미 체크박스들
    fun checkBoxUserJoinStep2HobbyOnClick(){
        // 체크되어 있는 체크박스 개수
        var checkedCount = 0
        if(checkBoxUserJoinStep2Hobby1Checked.value == true){
            checkedCount++
        }
        if(checkBoxUserJoinStep2Hobby2Checked.value == true){
            checkedCount++
        }
        if(checkBoxUserJoinStep2Hobby3Checked.value == true){
            checkedCount++
        }
        if(checkBoxUserJoinStep2Hobby4Checked.value == true){
            checkedCount++
        }
        if(checkBoxUserJoinStep2Hobby5Checked.value == true){
            checkedCount++
        }
        if(checkBoxUserJoinStep2Hobby6Checked.value == true){
            checkedCount++
        }

        if(checkedCount == 0){
            // 체크된 것이 없으면
            checkBoxUserJoinStep2HobbyAllChecked.value = false
            checkBoxUserJoinStep2HobbyAllCheckedState.value = MaterialCheckBox.STATE_UNCHECKED
        } else if(checkedCount == 6){
            // 모두 체크되어 있다면
            checkBoxUserJoinStep2HobbyAllChecked.value = true
            checkBoxUserJoinStep2HobbyAllCheckedState.value = MaterialCheckBox.STATE_CHECKED
        } else {
            checkBoxUserJoinStep2HobbyAllCheckedState.value = MaterialCheckBox.STATE_INDETERMINATE
        }
    }
```

### 체크박스들에게 설정해준다.

[res/layout/fragment_user_join_step2.xml]
```xml

                        <CheckBox
                            android:id="@+id/checkBoxUserJoinStep2Hobby1"
                            ...
                            android:onClick="@{(view) -> userJoinStep2ViewModel.checkBoxUserJoinStep2HobbyOnClick()}"/>

                        <CheckBox
                            android:id="@+id/checkBoxUserJoinStep2Hobby2"
                            ...
                            android:onClick="@{(view) -> userJoinStep2ViewModel.checkBoxUserJoinStep2HobbyOnClick()}"/>

                        <CheckBox
                            android:id="@+id/checkBoxUserJoinStep2Hobby3"
                            ...
                            android:onClick="@{(view) -> userJoinStep2ViewModel.checkBoxUserJoinStep2HobbyOnClick()}"/>

                        ...

                        <CheckBox
                            android:id="@+id/checkBoxUserJoinStep2Hobby4"
                            ...
                            android:onClick="@{(view) -> userJoinStep2ViewModel.checkBoxUserJoinStep2HobbyOnClick()}"/>

                        <CheckBox
                            android:id="@+id/checkBoxUserJoinStep2Hobby5"
                            ...
                            android:onClick="@{(view) -> userJoinStep2ViewModel.checkBoxUserJoinStep2HobbyOnClick()}"/>

                        <CheckBox
                            android:id="@+id/checkBoxUserJoinStep2Hobby6"
                            ...
                            android:onClick="@{(view) -> userJoinStep2ViewModel.checkBoxUserJoinStep2HobbyOnClick()}"/>
```

### 이전 화면으로 돌아가는 메서드를 만들어준다.

[fragment/UserJoinStep2Fragment.kt]
```kt
    // 이전 화면으로 돌아가는 메서드
    fun movePrevFragment(){
        userActivity.removeFragment(UserFragmentName.USER_JOIN_STEP2_FRAGMENT)
    }
```

### NavigationIcon을 누를 때 동작할 메서드를 만들어준다

[viewmodel/UserJoinStep2ViewModel.kt]
```kt
    companion object{
        // toolbarUserJoinStep2 - onNavigationClickUserJoinStep2
        @JvmStatic
        @BindingAdapter("onNavigationClickUserJoinStep2")
        fun onNavigationClickUserJoinStep2(materialToolbar: MaterialToolbar, userJoinStep2Fragment: UserJoinStep2Fragment){
            materialToolbar.setNavigationOnClickListener {
                userJoinStep2Fragment.movePrevFragment()
            }
        }
    }
```

### toolar에 설정해준다.

[res/layout/fragment_user_join_step2.xml]
```xml
        <com.google.android.material.appbar.MaterialToolbar
            android:id="@+id/toolbarUserJoinStep2"
            ...
            app:onNavigationClickUserJoinStep2="@{userJoinStep2ViewModel.userJoinStep2Fragment}"/>
```

### 가입 완료 처리하는 메서드를 구현해준다.

[fragment/UserJoinStep2Fragment.kt]
```kt
    // 가입 완료 처리 메서드
    fun proUserJoin(){
        fragmentUserJoinStep2Binding.apply {
            // 입력 검사
            if(userJoinStep2ViewModel?.textFieldUserJoinStep2NickNameEditTextText?.value?.isEmpty()!!){
                userActivity.showMessageDialog("닉네임 입력", "닉네임을 입력해주세요", "확인"){
                    userActivity.showSoftInput(textFieldUserJoinStep2NickName.editText!!)
                }
                return
            }
            if(userJoinStep2ViewModel?.textFieldUserJoinStep2AgeEditTextText?.value?.isEmpty()!!){
                userActivity.showMessageDialog("나이 입력", "나이를 입력해주세요", "확인"){
                    userActivity.showSoftInput(textFieldUserJoinStep2Age.editText!!)
                }
                return
            }
    
            userActivity.showMessageDialog("가입 완료", "가입이 완료되었습니다\n로그인해주세요", "확인"){
                userActivity.removeFragment(UserFragmentName.USER_JOIN_STEP1_FRAGMENT)
            }
        }
    }
```

### 버튼과 연결해줄 메서드를 구현한다.

[viewmodel/UserJoinStep2ViewModel.kt]
```kt
    // buttonUserJoinStep2Submit - onClick
    fun buttonUserJoinStep2SubmitOnClick(){
        // 가입 완료 처리 메서드를 호출한다.
        userJoinStep2Fragment.proUserJoin()
    }
```

### 버튼에 설정해준다.,

[res/layout/fragment_user_join_step2.xml]
```xml
                <Button
                    android:id="@+id/buttonUserJoinStep2Submit"
                    ...
                    android:onClick="@{(view) -> userJoinStep2ViewModel.buttonUserJoinStep2SubmitOnClick()}"/>
```

---

# 10_로그인 화면 처리

### 입력 요소와 연결할 LiveData를 정의해준다.

[viewmodel/LoginViewModel.kt]
```kt
    // textFieldUserLoginId - EditText - text
    val textFieldUserLoginIdEditTextText = MutableLiveData("")
    // textFieldUserLoginPw - EditText - text
    val textFieldUserLoginPwEditTextText = MutableLiveData("")
    // checkBoxUserLoginAuto - checked
    val checkBoxUserLoginAutoChecked = MutableLiveData(false)
```

### layout 파일에 적용해준다.

```xml

                <com.google.android.material.textfield.TextInputLayout
                    android:id="@+id/textFieldUserLoginId"
                   ...
                    <com.google.android.material.textfield.TextInputEditText
                        ...
                        android:text="@={loginViewModel.textFieldUserLoginIdEditTextText}"/>
                </com.google.android.material.textfield.TextInputLayout>

                <com.google.android.material.textfield.TextInputLayout
                    android:id="@+id/textFieldUserLoginPw"
                   ...

                    <com.google.android.material.textfield.TextInputEditText
                       ...
                        android:text="@={loginViewModel.textFieldUserLoginPwEditTextText}"/>
                </com.google.android.material.textfield.TextInputLayout>

                <CheckBox
                    ...
                    android:checked="@={loginViewModel.checkBoxUserLoginAutoChecked}"/>
```

### 로그인 처리 하는 메서드를 구현한다.

[fragment/LoginFragment.kt]
```kt
    // 로그인 처리 메서드
    fun proLogin(){
        fragmentLoginBinding.apply { 
            // 입력 요소 검사
            if(loginViewModel?.textFieldUserLoginIdEditTextText?.value?.isEmpty()!!){
                userActivity.showMessageDialog("아이디 입력", "아이디를 입력해주세요", "확인"){
                    userActivity.showSoftInput(textFieldUserLoginId.editText!!)
                }
                return
            }
            if(loginViewModel?.textFieldUserLoginPwEditTextText?.value?.isEmpty()!!){
                userActivity.showMessageDialog("비밀번호 입력", "비밀번호를 입력해주세요", "확인"){
                    userActivity.showSoftInput(textFieldUserLoginPw.editText!!)
                }
                return
            }
        }
    }
```

### 버튼을 누르면 호출될 메서드를 구현한다.

[viewmodel/LoginViewModel.kt]
```kt
    // buttonUserLoginSubmit - onClick
    fun buttonUserLoginSubmitOnClick(){
        loginFragment.proLogin()
    }
```

### 로그인 버튼에 메서드를 설정해준다.
[res/layout/fragment_login.xml]
```xml
                <Button
                    android:id="@+id/buttonUserLoginSubmit"
                    ...
                    android:onClick="@{(view) -> loginViewModel.buttonUserLoginSubmitOnClick()}"/>
```

### BoardActivity 를 생성한다.

### UserActivity를 종료하고 BoardActivity를 실행한다

[fragment./LoginFragment.kt]
```kt
            // BoardActivity를 실행하고 현재 Activity를 종료한다.
            val boardIntent = Intent(userActivity, BoardActivity::class.java)
            startActivity(boardIntent)
            userActivity.finish()
```

--- 

# 11_BoardActivity 기본 코드 작성

### layout에 기본 코드를 작성한다
[res/layout/activity_board.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/main"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".BoardActivity">

        <androidx.fragment.app.FragmentContainerView
            android:id="@+id/fragmentContainerViewBoard"
            android:layout_width="0dp"
            android:layout_height="0dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>

```

### 필요한 프로퍼티를 정의해준다.

[BoardActivity.kt]
```kt
    lateinit var activityBoardBinding: ActivityBoardBinding

    // 현재 Fragment와 다음 Fragment를 담을 변수(애니메이션 이동 때문에...)
    var newFragment: Fragment? = null
    var oldFragment: Fragment? = null
```


### Binding 객체를 생성한다.

[BoardActivity.kt - onCreateView()]
```kt
activityBoardBinding = DataBindingUtil.setContentView(this@BoardActivity, R.layout.activity_board)
```

### Fragment 이름들을 관리하기 위한 enum class를 작성해준다.

[BoardActivity.kt]
```kt
// 프래그먼트들을 나타내는 값들
enum class BoardFragmentName(var number:Int, var str:String){

}
```

### 필요한 메서드들을 구현한다.

```kt
    // 프래그먼트를 교체하는 함수
    fun replaceFragment(fragmentName: BoardFragmentName, isAddToBackStack:Boolean, animate:Boolean, dataBundle: Bundle?){
        // newFragment가 null이 아니라면 oldFragment 변수에 담아준다.
        if(newFragment != null){
            oldFragment = newFragment
        }
        // 프래그먼트 객체
        newFragment = when(fragmentName){

        }

        // bundle 객체가 null이 아니라면
        if(dataBundle != null){
            newFragment?.arguments = dataBundle
        }

        // 프래그먼트 교체
        supportFragmentManager.commit {

            if(animate) {
                // 만약 이전 프래그먼트가 있다면
                if(oldFragment != null){
                    oldFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
                    oldFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
                }

                newFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
                newFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
                newFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
                newFragment?.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
            }

            replace(R.id.fragmentContainerViewBoard, newFragment!!)
            if(isAddToBackStack){
                addToBackStack(fragmentName.str)
            }
        }
    }

    // 프래그먼트를 BackStack에서 제거하는 메서드
    fun removeFragment(fragmentName: BoardFragmentName){
        supportFragmentManager.popBackStack(fragmentName.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    // 다이얼로그를 통해 메시지를 보여주는 함수
    fun showMessageDialog(title:String, message:String, posTitle:String, callback:()-> Unit){
        val builder = MaterialAlertDialogBuilder(this@BoardActivity)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(posTitle){ dialogInterface: DialogInterface, i: Int ->
            callback()
        }
        builder.show()
    }

    // 키보드 올리는 메서드
    fun showSoftInput(view: View){
        // 입력을 관리하는 매니저
        val inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        // 포커스를 준다.
        view.requestFocus()

        thread {
            SystemClock.sleep(500)
            // 키보드를 올린다.
            inputManager.showSoftInput(view, 0)
        }
    }
    // 키보드를 내리는 메서드
    fun hideSoftInput(){
        // 포커스가 있는 뷰가 있다면
        if(currentFocus != null){
            // 입력을 관리하는 매니저
            val inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            // 키보드를 내린다.
            inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            // 포커스를 해제한다.
            currentFocus?.clearFocus()
        }
    }
```
---

# 12_게시판 메인 화면 구성

### Fragment와 ViewModel을 만들어준다.
- BoardMainFragment
- BoardMainViewModel

### layout 파일에 기본 코드를 작성한다.

[res/layout/fragment_board_main.xml]
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <variable
            name="boardMainViewModel"
            type="com.lion.boardproject.viewmodel.BoardMainViewModel" />
    </data>


    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".fragment.BoardMainFragment">

        <!-- TODO: Update blank fragment layout -->
        <TextView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:text="@string/hello_blank_fragment" />

    </FrameLayout>
</layout>

```

### ViewModel의 기본 코드를 작성한다

[viewmodel/BoardMainViewModel.kt]
```kt
class BoardMainViewModel(val boardMainFragment: BoardMainFragment) : ViewModel() {
}
```

### Fragment의 기본 코드를 작성한다.

[fragment/BoardMainFragment.kt]
```kt
class BoardMainFragment : Fragment() {

    lateinit var fragmentBoardMainBinding:FragmentBoardMainBinding
    lateinit var boardActivity: BoardActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentBoardMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_board_main, container, false)
        fragmentBoardMainBinding.boardMainViewModel = BoardMainViewModel(this@BoardMainFragment)
        fragmentBoardMainBinding.lifecycleOwner = this@BoardMainFragment

        boardActivity = activity as BoardActivity

        return fragmentBoardMainBinding.root
    }

}
```

### Fragment의 이름을 정의한다.

[BoardActivity.kt]
```kt
    // 게시판 메인 화면
    BOARD_MAIN_FRAGMENT(1, "BoardMainFragment"),
```

### Fragment 객체를 생성한다.

[BoardActivity.kt - replaceFragment()]
```kt
            // 게시판 메인 화면
            BoardFragmentName.BOARD_MAIN_FRAGMENT -> BoardMainFragment()
```

### 첫 프래그먼트를 보여준다.

[BoardActivity.kt - onCreate()]
```kt
        // 첫 프래그먼트를 보여준다.
        replaceFragment(BoardFragmentName.BOARD_MAIN_FRAGMENT, false, false, null)
```

### fragment_board_main을 수정해준다.
[res/layout/fragment_board_main.xml]
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <variable
            name="boardMainViewModel"
            type="com.lion.boardproject.viewmodel.BoardMainViewModel" />
    </data>


    <androidx.drawerlayout.widget.DrawerLayout
        android:id="@+id/drawerLayoutBoardMain"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:transitionGroup="true">

        <androidx.coordinatorlayout.widget.CoordinatorLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:orientation="vertical">

                <androidx.fragment.app.FragmentContainerView
                    android:id="@+id/fragmentContainerViewBoardMain"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent" />
            </LinearLayout>
        </androidx.coordinatorlayout.widget.CoordinatorLayout>

        <com.google.android.material.navigation.NavigationView
            android:id="@+id/navigationViewBoardMain"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
    </androidx.drawerlayout.widget.DrawerLayout>
</layout>

```

### Fragment 들의 이름을 관리할 enum class를 정의해준다.

[fragment/BoardMainFragment.kt]
```kt
// 하위 프래그먼트들의 이름
enum class BoardSubFragmentName(var number:Int, var str:String){

}
```

### 프래그먼트를 담을 변수를 선언해준다.

[fragment/BoardMainFragment.kt]
```kt
    // 현재 Fragment와 다음 Fragment를 담을 변수(애니메이션 이동 때문에...)
    var newFragment: Fragment? = null
    var oldFragment: Fragment? = null
```

### 프래그먼트 관리를 위한 메서드를 구현해준다.
[fragment/BoardMainFragment.kt]
```kt
    // 프래그먼트를 교체하는 함수
    fun replaceFragment(fragmentName: BoardSubFragmentName, isAddToBackStack:Boolean, animate:Boolean, dataBundle: Bundle?){
        // newFragment가 null이 아니라면 oldFragment 변수에 담아준다.
        if(newFragment != null){
            oldFragment = newFragment
        }
        // 프래그먼트 객체
        newFragment = when(fragmentName){

        }

        // bundle 객체가 null이 아니라면
        if(dataBundle != null){
            newFragment?.arguments = dataBundle
        }

        // 프래그먼트 교체
        boardActivity.supportFragmentManager.commit {

            if(animate) {
                // 만약 이전 프래그먼트가 있다면
                if(oldFragment != null){
                    oldFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
                    oldFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
                }

                newFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
                newFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
                newFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
                newFragment?.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
            }

            replace(R.id.fragmentContainerViewBoardMain, newFragment!!)
            if(isAddToBackStack){
                addToBackStack(fragmentName.str)
            }
        }
    }


    // 프래그먼트를 BackStack에서 제거하는 메서드
    fun removeFragment(fragmentName: BoardSubFragmentName){
        boardActivity.supportFragmentManager.popBackStack(fragmentName.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

```

---

# 13_네비게이션 뷰 구성

### 나중에 사용할 Fragment와 ViewModel을 만들어준다.
- BoardListFragment
- BoardListViewModel

### layout 파일을 작성해준다.(임시)
[res/layout/fragment_board_list.xml]
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <variable
            name="boardListViewModel"
            type="com.lion.boardproject.viewmodel.BoardListViewModel" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:transitionGroup="true" >

        <Button
            android:id="@+id/button"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Button" />
    </LinearLayout>
</layout>

```

### ViewModel의 기본 코드를 작성한다.
[viewmodel/BoardListViewModel.kt]
```kt
class BoardListViewModel(val boardListFragment: BoardListFragment) : ViewModel() {
}
```

### Fragment 기본 코드를 작성한다

[fragment/BoardListFragment.kt]
```kt
class BoardListFragment(val boardMainFragment: BoardMainFragment) : Fragment() {

    lateinit var fragmentBoardListBinding: FragmentBoardListBinding
    lateinit var boardActivity: BoardActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentBoardListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_board_list, container, false)
        fragmentBoardListBinding.boardListViewModel = BoardListViewModel(this@BoardListFragment)
        fragmentBoardListBinding.lifecycleOwner = this@BoardListFragment

        boardActivity = activity as BoardActivity

        return fragmentBoardListBinding.root
    }
}
```

### 프래그먼트 이름을 정의해준다.

[fragment/BoardMainFragment.kt - BoardSubFragmentName]
```kt
    // 게시글 목록 화면
    BOARD_LIST_FRAGMENT(1, "BoardListFragment"),
```

### 프래그먼트 객체를 생성한다.

[fragment/BoardMainFragment.kt - replaceFragment()]
```kt
            // 게시글 목록 화면
            BoardSubFragmentName.BOARD_LIST_FRAGMENT -> BoardListFragment(this@BoardMainFragment)
```

### 첫 프래그먼트를 설정한다.

[fragment/BoardMainFragment.kt - onCreateView()]
```kt
        // 첫 프래그먼트 설정
        replaceFragment(BoardSubFragmentName.BOARD_LIST_FRAGMENT, false, false, null)
```

### DrawerLayout을 펼쳐줄 수 있도록 버튼의 리스너를 구성한다(삭제 예정)
[fragment/BoardListFragment.kt - onCreateView()]
```kt
        fragmentBoardListBinding.button.setOnClickListener {
            boardMainFragment.fragmentBoardMainBinding.drawerLayoutBoardMain.open()
        }
```

### Navigation View의 헤더 부분을 구성하기 위한 레이아웃 파일과 ViewModel을 만들어준다.
- viewmodel/NavigationBoardMainHeaderViewModel.kt
- res/layout/navigation_board_main_header.xml

### ViewModel의 기본 코드와 LiveData를 작성한다.

[viewmodel/NavigationBoardMainHeaderViewModel.kt]
```kt
class NavigationBoardMainHeaderViewModel(val boardMainFragment: BoardMainFragment) : ViewModel() {
    // textViewNavigationBoardMainHeaderNickName - text
    val textViewNavigationBoardMainHeaderNickNameText = MutableLiveData("")
}
```

### 레이아웃을 작성해준다.

[res/layout/navigation_board_main_header.xml]]
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout
    xmlns:android="http://schemas.android.com/apk/res/android">
    <data>
        <variable
            name="navigationBoardMainHeaderViewModel"
            type="com.lion.boardproject.viewmodel.NavigationBoardMainHeaderViewModel" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="20dp"
        android:transitionGroup="true">

        <TextView
            android:id="@+id/textViewNavigationBoardMainHeaderNickName"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@{navigationBoardMainHeaderViewModel.textViewNavigationBoardMainHeaderNickNameText}"
            android:textAppearance="@style/TextAppearance.AppCompat.Large" />

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="10dp"
            android:text="환영합니다." />
    </LinearLayout>
</layout>

```

### 네비게이션뷰를 구성하는 메서드를 구현해준다.
[fragment/BoardMainFragment.kt]
```kt
    // NavigationView를 구성하는 메서드
    fun settingNavigationView(){
        fragmentBoardMainBinding.apply {
            // Header 구성
            val navigationBoardMainHeaderBinding = DataBindingUtil.inflate<NavigationBoardMainHeaderBinding>(
                layoutInflater, R.layout.navigation_board_main_header, null, false
            )
            navigationBoardMainHeaderBinding.navigationBoardMainHeaderViewModel = NavigationBoardMainHeaderViewModel(this@BoardMainFragment)
            navigationBoardMainHeaderBinding.lifecycleOwner = this@BoardMainFragment

            // 닉네임 설정
            navigationBoardMainHeaderBinding.navigationBoardMainHeaderViewModel?.textViewNavigationBoardMainHeaderNickNameText?.value = "닉네임님"

            navigationViewBoardMain.addHeaderView(navigationBoardMainHeaderBinding.root)
        }
    }
```

### NavigationView에서 사용할 메뉴를 만들어준다.
[res/menu/menu_board_main_navigation.xml]

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <item android:title="게시판" >
        <menu >
            <item
                android:id="@+id/menuItemBoardNavigationAll"
                android:checkable="true"
                android:icon="@drawable/local_shipping_24px"
                android:title="전체게시판"
                app:showAsAction="ifRoom" />
            <item
                android:id="@+id/menuItemBoardNavigation1"
                android:checkable="true"
                android:icon="@drawable/post_add_24px"
                android:title="자유게시판"
                app:showAsAction="ifRoom" />
            <item
                android:id="@+id/menuItemBoardNavigation2"
                android:checkable="true"
                android:icon="@drawable/compost_24px"
                android:title="유머게시판"
                app:showAsAction="ifRoom" />
            <item
                android:id="@+id/menuItemBoardNavigation3"
                android:checkable="true"
                android:icon="@drawable/package_24px"
                android:title="시사게시판"
                app:showAsAction="ifRoom" />
            <item
                android:id="@+id/menuItemBoardNavigation4"
                android:checkable="true"
                android:icon="@drawable/approval_24px"
                android:title="운동게시판"
                app:showAsAction="ifRoom" />
        </menu>
    </item>
    <item
        android:id="@+id/menuItemBoardNavigationModifyUserInfo"
        android:checkable="true"
        android:icon="@drawable/person_24px"
        android:title="사용자 정보 수정"
        app:showAsAction="ifRoom" />
    <item
        android:id="@+id/menuItemBoardNavigationLogout"
        android:icon="@drawable/logout_24px"
        android:title="로그아웃"
        app:showAsAction="ifRoom" />
    <item
        android:id="@+id/menuItemBoardNavigationSignOut"
        android:icon="@drawable/move_item_24px"
        android:title="회원탈퇴"
        app:showAsAction="ifRoom" />
</menu>
```

### 메뉴를 구성하는 코드를 넣어준다.

[fragment/BoardMainFragment.kt]
```kt
            // 메뉴 구성
            navigationViewBoardMain.inflateMenu(R.menu.menu_board_main_navigation)
            navigationViewBoardMain.setCheckedItem(R.id.menuItemBoardNavigationAll)
            navigationViewBoardMain.setNavigationItemSelectedListener {

                drawerLayoutBoardMain.close()
                true
            }
```

---

# 14_게시글 목록 화면 구성

### 테스트 용 코드 삭제

[fragment/BoardListFragment.kt]
```kt
        fragmentBoardListBinding.button.setOnClickListener {
            boardMainFragment.fragmentBoardMainBinding.drawerLayoutBoardMain.open()
        }
```

### 화면 구성
- Material 가이드에 나오는 검색 기능
- SearchBar를 누르면 화면이 바뀌면서 검색할 수 있도록 한다.
- CoordinatorLayout 안에 SearchBar와 SearchView를 배치해야 한다
- SearchView의 layout_anchor 속성에 SearchBar의 id를 지정하여 둘을 연결시켜줘야 한다.

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <variable
            name="boardListViewModel"
            type="com.lion.boardproject.viewmodel.BoardListViewModel" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:transitionGroup="true" >

        <com.google.android.material.appbar.MaterialToolbar
            android:id="@+id/toolbarBoardList"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@android:color/transparent"
            android:minHeight="?attr/actionBarSize"
            android:theme="?attr/actionBarTheme"
            app:navigationIcon="@drawable/menu_24px" />

        <androidx.coordinatorlayout.widget.CoordinatorLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <com.google.android.material.search.SearchBar
                android:id="@+id/searchBarBoardList"
                android:layout_width="match_parent"
                android:layout_height="wrap_content" />

            <androidx.recyclerview.widget.RecyclerView
                android:id="@+id/recyclerViewBoardListMain"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:layout_marginTop="90dp" />

            <com.google.android.material.search.SearchView
                android:id="@+id/searchViewBoardList"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                app:layout_anchor="@id/searchBarBoardList">

                <androidx.recyclerview.widget.RecyclerView
                    android:id="@+id/recyclerViewBoardListResult"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent" />
            </com.google.android.material.search.SearchView>
        </androidx.coordinatorlayout.widget.CoordinatorLayout>
    </LinearLayout>
</layout>

```

### Recyclerview의 항목에서 사용할 ViewModel 클래스와 layout 파일을 만들어준다.
- viewmodel/RowBoardListViewModel.kt
- res/layout/row_board_list.xml

### ViewModel 클래스를 작성한다.
[viewmodel/RowBoardListViewModel.kt]
```kt
class RowBoardListViewModel(val boardListFragment: BoardListFragment) : ViewModel(){
    // textViewRowBoardListTitle - text
    val textViewRowBoardListTitleText = MutableLiveData("")
    // textViewRowBoardListNickName - text
    val textViewRowBoardListNickNameText = MutableLiveData("")
}
```

### layout을 작성해준다.
[res/layout/row_board_list.xml]
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout
    xmlns:android="http://schemas.android.com/apk/res/android">

    <data>
        <variable
            name="rowBoardListViewModel"
            type="com.lion.boardproject.viewmodel.RowBoardListViewModel" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="10dp"
        android:transitionGroup="true">

        <TextView
            android:id="@+id/textViewRowBoardListTitle"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@{rowBoardListViewModel.textViewRowBoardListTitleText}"
            android:textAppearance="@style/TextAppearance.AppCompat.Large" />

        <TextView
            android:id="@+id/textViewRowBoardListNickName"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="10dp"
            android:text="@{rowBoardListViewModel.textViewRowBoardListNickNameText}" />
    </LinearLayout>
</layout>


```

### Toolbar에 title 속성과 연결할 LiveData를 정의한다.

[viewmodel/BoardListViewModel.kt]
```kt
    // toolbarBoardList - title
    val toolbarBoardListTitle = MutableLiveData("")
```

### Toolbar의  title 속성에 LiveData를 연결해준다.

[res/layout/fragment_board_list.xml]
```xml
app:title="@{boardListViewModel.toolbarBoardListTitle}"
```

### Toolbar를 구성하는 메서드를 구현한다.

[fragment/BoardListFragment.kt]
```kt
    // 툴바를 구성하는 메서드
    fun settingToolbar(){
        // 타이틀
        fragmentBoardListBinding.boardListViewModel?.toolbarBoardListTitle?.value = "임시게시판이름"
    }
```

### 메서드를 호출한다.

[fragment/BoardListFragment.kt - onCreateView()]
```kt
        // 툴바를 구성하는 메서드를 호출한다.
        settingToolbar()
```

### 네비게이션 뷰를 보여주는 메서드를 구현한다.

[fragment/BoardMainFragment.kt]
```kt
    // NavigationView를 보여주는 메서드
    fun showNavigationView(){
        fragmentBoardMainBinding.drawerLayoutBoardMain.open()
    }
```

### ViewModel에 메서드를 구현해준다.
[viewmodel/BoardListViewModel.kt]
```kt
    companion object{
        // toolbarBoardList - onNavigationClickBoardList
        @JvmStatic
        @BindingAdapter("onNavigationClickBoardList")
        fun onNavigationClickBoardList(materialToolbar: MaterialToolbar, boardListFragment: BoardListFragment){
            materialToolbar.setNavigationOnClickListener {
                boardListFragment.boardMainFragment.showNavigationView()
            }
        }
    }
```

### 툴바에 적용해준다.

[res/layout/fragment_board_list.xml]
```xml
        <com.google.android.material.appbar.MaterialToolbar
            android:id="@+id/toolbarBoardList"
            ...
            app:onNavigationClickBoardList="@{boardListViewModel.boardListFragment}"/>
```

### SearchBar에서 사용할 메뉴 파일을 만들어준다.
[res/menu/menu_board_list_searchbar.xml]
```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:android="http://schemas.android.com/apk/res/android">

    <item
        android:id="@+id/menuItemBoardListAdd"
        android:icon="@drawable/add_24px"
        android:title="글쓰기"
        app:showAsAction="always" />
</menu>
```

### SearchBar에 hint와 menu를 설정한다
[res/layout/fragment_board_list.xml]
```xml
            <com.google.android.material.search.SearchBar
                android:id="@+id/searchBarBoardList"
                ...
                android:hint="검색어를 입력해주세요"
                app:menu="@menu/menu_board_list_searchbar" />
```

### SearchView에 hint를 설정해준다.
[res/layout/fragment_board_list.xml]
```xml

            <com.google.android.material.search.SearchView
                ...
                android:hint="검색어를 입력해주세요">
```

### 메인 RecyclerView의 어뎁터를 작성한다.
[fragment/BoardListFragment.kt]
```kt
    // 메인 RecyclerView의 어뎁터
    inner class MainRecyclerViewAdapter : RecyclerView.Adapter<MainRecyclerViewAdapter.MainViewHolder>(){
        inner class MainViewHolder(val rowBoardListBinding: RowBoardListBinding) : RecyclerView.ViewHolder(rowBoardListBinding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val rowBoardListBinding = DataBindingUtil.inflate<RowBoardListBinding>(layoutInflater, R.layout.row_board_list, parent, false)
            rowBoardListBinding.rowBoardListViewModel = RowBoardListViewModel(this@BoardListFragment)
            rowBoardListBinding.lifecycleOwner = this@BoardListFragment

            val mainViewHolder = MainViewHolder(rowBoardListBinding)
            return mainViewHolder
        }

        override fun getItemCount(): Int {
            return tempList1.size
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            holder.rowBoardListBinding.rowBoardListViewModel?.textViewRowBoardListTitleText?.value = tempList1[position]
            holder.rowBoardListBinding.rowBoardListViewModel?.textViewRowBoardListNickNameText?.value = tempList2[position]
        }
    }
```

### 검색 결과 RecyclerView를 작성해준다.

[fragment/BoardListFragment.kt]
```kt
    // 검색결과 RecyclerView의 어뎁터
    inner class ResultRecyclerViewAdapter : RecyclerView.Adapter<ResultRecyclerViewAdapter.ResultViewHolder>(){
        inner class ResultViewHolder(val rowBoardListBinding: RowBoardListBinding) : RecyclerView.ViewHolder(rowBoardListBinding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
            val rowBoardListBinding = DataBindingUtil.inflate<RowBoardListBinding>(layoutInflater, R.layout.row_board_list, parent, false)
            rowBoardListBinding.rowBoardListViewModel = RowBoardListViewModel(this@BoardListFragment)
            rowBoardListBinding.lifecycleOwner = this@BoardListFragment

            val resultViewHolder = ResultViewHolder(rowBoardListBinding)
            return resultViewHolder
        }

        override fun getItemCount(): Int {
            return tempList1.size
        }

        override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
            holder.rowBoardListBinding.rowBoardListViewModel?.textViewRowBoardListTitleText?.value = tempList1[position]
            holder.rowBoardListBinding.rowBoardListViewModel?.textViewRowBoardListNickNameText?.value = tempList2[position]
        }
    }
```

### RecyclerView를 구성하는 메서드를 작성해준다.
[fragment/BoardListFragment.kt]
```kt
    // 메인 RecyclerView 구성 메서드
    fun settingMainRecyclerView(){
        fragmentBoardListBinding.apply {
            recyclerViewBoardListMain.adapter = MainRecyclerViewAdapter()
            recyclerViewBoardListMain.layoutManager = LinearLayoutManager(boardActivity)
            val deco = MaterialDividerItemDecoration(boardActivity, MaterialDividerItemDecoration.VERTICAL)
            recyclerViewBoardListMain.addItemDecoration(deco)
        }
    }

    // 검색 결과 RecyclerView 구성 메서드
    fun settingResultRecyclerView(){
        fragmentBoardListBinding.apply {
            recyclerViewBoardListResult.adapter = ResultRecyclerViewAdapter()
            recyclerViewBoardListResult.layoutManager = LinearLayoutManager(boardActivity)
            val deco = MaterialDividerItemDecoration(boardActivity, MaterialDividerItemDecoration.VERTICAL)
            recyclerViewBoardListResult.addItemDecoration(deco)
        }
    }
```

### 메서드를 호출한다.
[fragment/BoardListFragment.kt - onCreateView()]
```kt
        // 메인 RecyclerView 구성 메서드를 호출한다.
        settingMainRecyclerView()
        // 검색 결과 RecyclerView 구성 메서드를 호출한다.
        settingResultRecyclerView()
```

---

# 15_글 작성 화면 구성하기

### 글 작성을 위한 Fragment와 ViewModel을 만들어준다.
- fragment/BoardWriteFragment.kt
- viewmodel/BoardWriteViewModel.kt

### ViewModel에 기본 코드를 작성한다.

[viewmodel/BoardWriteViewModel.kt]
```kt
class BoardWriteViewModel(val boardWriteFragment: BoardWriteFragment) : ViewModel() {
}
```

### layout 코드를 작성한다.
[res/layout/fragment_board_write.xml]
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">
    <data>
        <variable
            name="boardWriteViewModel"
            type="com.lion.boardproject.viewmodel.BoardWriteViewModel" />
    </data>

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".fragment.BoardWriteFragment">

        <!-- TODO: Update blank fragment layout -->
        <TextView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:text="@string/hello_blank_fragment" />

    </FrameLayout>
</layout>

```

### Fragment의 기본 코드를 작성한다.

[fragment/BoardWriteFragment.kt]
```kt

class BoardWriteFragment(val boardMainFragment: BoardMainFragment) : Fragment() {

    lateinit var fragmentBoardWriteBinding: FragmentBoardWriteBinding
    lateinit var boardActivity: BoardActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentBoardWriteBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_board_write, container, false)
        fragmentBoardWriteBinding.boardWriteViewModel = BoardWriteViewModel(this@BoardWriteFragment)
        fragmentBoardWriteBinding.lifecycleOwner = this@BoardWriteFragment

        boardActivity = activity as BoardActivity

        return fragmentBoardWriteBinding.root
    }
}
```

### Fragment 이름을 작성해준다.
[fragment/BoardMainFragment.kt - BoardSubFragmentName]
```kt
    // 게시글 작성 화면
    BOARD_WRITE_FRAGMENT(2, "BoardWriteFragment"),
```

### Fragment 객체를 생성한다.
[fragment/BoardMainFragment.kt]
```kt
    // 프래그먼트를 교체하는 함수
    fun replaceFragment(fragmentName: BoardSubFragmentName, isAddToBackStack:Boolean, animate:Boolean, dataBundle: Bundle?){
       ...
        newFragment = when(fragmentName){
        ...
            // 게시글 작성 화면
            BoardSubFragmentName.BOARD_WRITE_FRAGMENT -> BoardWriteFragment(this@BoardMainFragment)
        }
        ...
    }
```

### + 메뉴를 누르면 BoardWriteFragment로 이동하는 메서드를 만든다.
[fragment/BoardListFragment.kt]
```kt
    // SearchBar를 구성하는 메서드
    fun settingSearchBar(){
        fragmentBoardListBinding.apply {
            searchBarBoardList.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.menuItemBoardListAdd -> {
                        // 글 작성 화면으로 이동한다.
                        boardMainFragment.replaceFragment(BoardSubFragmentName.BOARD_WRITE_FRAGMENT, true, true, null)
                    }
                }
                true
            }
        }
    }
```

### 메서드를 호출한다.

[fragment/BoardListFragment.kt - onCreateView()]
```kt
        // SearchBar를 구성하는 메서드
        settingSearchBar()
```

### Toolbar에 배치할 메뉴 파일을 작성해준다.
[res/menu/menu_board_write_toolbar.xml]
```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <item android:title="카메라"
        android:id="@+id/menuItemBoardWriteCamera"
        android:icon="@drawable/photo_camera_24px"
        app:showAsAction="always"/>

    <item android:title="앨범"
        android:id="@+id/menuItemBoardWriteAlbum"
        android:icon="@drawable/photo_album_24px"
        app:showAsAction="always"/>

    <item android:title="초기화"
        android:id="@+id/menuItemBoardWriteReset"
        android:icon="@drawable/restart_alt_24px"
        app:showAsAction="always"/>

    <item android:title="완료"
        android:id="@+id/menuItemBoardWriteDone"
        android:icon="@drawable/done_24px"
        app:showAsAction="always"/>

</menu>
```

### 이전화면으로 돌아가는 메서드를 구현한다.
[fragment/BoardWriteFragment.kt]
```kt
    // 이전 화면으로 돌아간다.
    fun movePrevFragment(){
        boardMainFragment.removeFragment(BoardSubFragmentName.BOARD_WRITE_FRAGMENT)
    }
```

### ViewModel에서 navigation icon과 연결할 속성을 만들어준다.
[viewmodel/BoardWriteViewModel.kt]
```kt
    companion object{
        // toolbarBoardWrite - onNavigationClickBoardWrite
        @JvmStatic
        @BindingAdapter("onNavigationClickBoardWrite")
        fun onNavigationClickBoardWrite(materialToolbar: MaterialToolbar, boardWriteFragment: BoardWriteFragment){
            materialToolbar.setNavigationOnClickListener {
                boardWriteFragment.movePrevFragment()
            }
        }
    }
```

### 화면을 구성해준다.
[res/layout/fragment_board_write.xml]
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">
    <data>
        <variable
            name="boardWriteViewModel"
            type="com.lion.boardproject.viewmodel.BoardWriteViewModel" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:transitionGroup="true" >

        <com.google.android.material.appbar.MaterialToolbar
            android:id="@+id/toolbarBoardWrite"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@android:color/transparent"
            android:minHeight="?attr/actionBarSize"
            android:theme="?attr/actionBarTheme"
            app:menu="@menu/menu_board_write_toolbar"
            app:navigationIcon="@drawable/arrow_back_24px"
            app:title="글 작성"
            app:onNavigationClickBoardWrite="@{boardWriteViewModel.boardWriteFragment}"/>

        <ScrollView
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:orientation="vertical"
                android:padding="10dp" >

                <com.google.android.material.textfield.TextInputLayout
                    android:id="@+id/textFieldBoardWriteTitle"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:hint="제목"
                    app:endIconMode="clear_text"
                    app:startIconDrawable="@drawable/subject_24px">

                    <com.google.android.material.textfield.TextInputEditText
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:singleLine="true"
                        android:textAppearance="@style/TextAppearance.AppCompat.Large" />
                </com.google.android.material.textfield.TextInputLayout>

                <com.google.android.material.textfield.TextInputLayout
                    android:id="@+id/textFieldBoardWriteText"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:hint="내용"
                    app:endIconMode="clear_text"
                    app:startIconDrawable="@drawable/description_24px"
                    android:layout_marginTop="10dp">

                    <com.google.android.material.textfield.TextInputEditText
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:textAppearance="@style/TextAppearance.AppCompat.Large" />
                </com.google.android.material.textfield.TextInputLayout>

                <com.google.android.material.button.MaterialButtonToggleGroup
                    android:id="@+id/buttonGroupBoardWriteType"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="10dp"
                    app:checkedButton="@id/buttonBoardWriteType1"
                    app:selectionRequired="true"
                    app:singleSelection="true">

                    <Button
                        android:id="@+id/buttonBoardWriteType1"
                        style="@style/Widget.Material3.Button.OutlinedButton"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:layout_weight="1"
                        android:text="자유" />

                    <Button
                        android:id="@+id/buttonBoardWriteType2"
                        style="@style/Widget.Material3.Button.OutlinedButton"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:layout_weight="1"
                        android:text="유머" />

                    <Button
                        android:id="@+id/buttonBoardWriteType3"
                        style="@style/Widget.Material3.Button.OutlinedButton"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:layout_weight="1"
                        android:text="시사" />

                    <Button
                        android:id="@+id/buttonBoardWriteType4"
                        style="@style/Widget.Material3.Button.OutlinedButton"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:layout_weight="1"
                        android:text="운동" />
                </com.google.android.material.button.MaterialButtonToggleGroup>

                <ImageView
                    android:id="@+id/imageViewBoardWrite"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="10dp"
                    android:adjustViewBounds="true"
                    android:src="@drawable/panorama_24px" />

            </LinearLayout>
        </ScrollView>
    </LinearLayout>
</layout>

```

### 화면 요소의 속성과 연결할 LiveData를 정의해준다.
[viewmodel/BoardWriteViewModel.kt]
```kt
    // textFieldBoardWriteTitle - text
    val textFieldBoardWriteTitleText = MutableLiveData("")
    // textFieldBoardWriteText - text
    val textFieldBoardWriteTextText = MutableLiveData("")\
```

### Live데이터와 뷰의 속성을 연결해준다.
[res/layout/fragment_board_write.xml]
```xml
                <com.google.android.material.textfield.TextInputLayout
                    android:id="@+id/textFieldBoardWriteTitle"
                   ...
                    <com.google.android.material.textfield.TextInputEditText
                      ...
                        android:text="@={boardWriteViewModel.textFieldBoardWriteTitleText}"/>
                </com.google.android.material.textfield.TextInputLayout>

                <com.google.android.material.textfield.TextInputLayout
                    android:id="@+id/textFieldBoardWriteText"
                    ...
                    <com.google.android.material.textfield.TextInputEditText
                        ...
                        android:text="@={boardWriteViewModel.textFieldBoardWriteTextText}"/>
                </com.google.android.material.textfield.TextInputLayout>

```

---

# 16_카메라와 앨범에서 사진 선택 구성하기

### 툴바를 구성하는 메서드를 작성한다.

[fragment/BoardWriteFragment.kt]
```kt
    // Toolbar를 구성하는 메서드
    fun settingToolbar(){
        fragmentBoardWriteBinding.apply {
            // 메뉴의 항목을 눌렀을 때
            toolbarBoardWrite.setOnMenuItemClickListener {
                when(it.itemId){
                    // 카메라
                    R.id.menuItemBoardWriteCamera -> {

                    }
                    // 앨범
                    R.id.menuItemBoardWriteAlbum -> {

                    }
                    // 초기화
                    R.id.menuItemBoardWriteReset -> {

                    }
                    // 완료
                    R.id.menuItemBoardWriteDone -> {

                    }
                }
                true
            }
        }
    }
```

### 호출한다.

[fragment/BoardWriteFragment.kt - onCreateView()]
```kt
        // Toolbar를 구성하는 메서드를 호출한다.
        settingToolbar()
```

### xml 폴더에 xml 파일을 만들어준다.
[res/xml/file_path.xml]
```xml
<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">
    <external-path
        name="storage/emulated/0"
        path="."/>
</paths>
```

### AndroidManifest.xml 에 provider를 등록한다.
[AndroidManifest.xml]
```xml
        <!-- 촬영된 사진을 가져올 수 있는 프로바이더 -->
        <provider
            android:authorities="com.lion.boardproject.camera"
            android:name="androidx.core.content.FileProvider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_path"/>
        </provider>
```

### 사진 데이터를 수정하는 메서드를 추가해준다.
[BoardActivity.kt]
```kt

    // 이미지를 회전시키는 메서드
    fun rotateBitmap(bitmap: Bitmap, degree:Int): Bitmap {
        // 회전 이미지를 구하기 위한 변환 행렬
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        // 회전 행렬을 적용하여 회전된 이미지를 생성한다.
        val resultBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, false)
        return resultBitmap
    }

    // 회전 각도값을 구하는 메서드
    fun getDegree(uri: Uri):Int{

        // 이미지의 태그 정보에 접근할 수 있는 객체를 생성한다.
        // andorid 10 버전 이상이라면
        val exifInterface = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            // 이미지 데이터를 가져올 수 있는 Content Provider의 Uri를 추출한다.
            val photoUri = MediaStore.setRequireOriginal(uri)
            // 컨텐츠 프로바이더를 통해 파일에 접근할 수 있는 스트림을 추출한다.
            val inputStream = contentResolver.openInputStream(photoUri)
            // ExifInterface 객체를 생성한다.
            ExifInterface(inputStream!!)
        } else {
            ExifInterface(uri.path!!)
        }

        // ExifInterface 정보 중 회전 각도 값을 가져온다
        val ori = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1)

        // 회전 각도값을 담는다.
        val degree = when(ori){
            ExifInterface.ORIENTATION_ROTATE_90 -> 90
            ExifInterface.ORIENTATION_ROTATE_180 -> 180
            ExifInterface.ORIENTATION_ROTATE_270 -> 270
            else -> 0
        }

        return degree
    }

    // 이미지의 사이즈를 줄이는 메서드
    fun resizeBitmap(targetWidth:Int, bitmap:Bitmap):Bitmap{
        // 이미지의 축소/확대 비율을 구한다.
        val ratio = targetWidth.toDouble() / bitmap.width.toDouble()
        // 세로 길이를 구한다.
        val targetHeight = (bitmap.height.toDouble() * ratio).toInt()
        // 크기를 조절한 Bitmap 객체를 생성한다.
        val result = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false)
        return result
    }
```

### 필요한 변수들을 정의해준다.

```kt
    // 카메라나 앨범을 사용하는 Fragment를 받을 변수
    var pictureFragment:Fragment? = null
    // 촬영된 사진이 위치할 경로
    lateinit var filePath:String
    // 저장된 파일에 접근하기 위한 Uri
    lateinit var contentUri:Uri
    // 사진 촬영을 위한 런처
    lateinit var cameraLauncher:ActivityResultLauncher<Intent>
```

###  외부 저장소까지의 경로를 가져온다.
[BoardActivity.kt - onCreate()]
```kt
        // 외부 저장소 경로를 가져온다.
        filePath = getExternalFilesDir(null).toString()
```

### 카메라 실행을 위한 런처를 구성하는 메서드를 구현한다.
[BoardActivity.kt]
```kt
    // 카메라 실행을 위한 런처를 구성하는 메서드
    fun settingCameraLauncher(){
        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            // 사진을 찍고 돌아왔다면
            if(it.resultCode == RESULT_OK){
                // uri를 통해 저장된 사진 데이터를 가져온다.
                val bitmap = BitmapFactory.decodeFile(contentUri.path)
                // 이미지의 회전 각도값을 가져온다.
                val degree = getDegree(contentUri)
                // 회전 값을 이용해 이미지를 회전시킨다.
                val rotateBitmap = rotateBitmap(bitmap, degree)
                // 크기를 조정한 이미지를 가져온다.
                val resizeBitmap = resizeBitmap(1024, rotateBitmap)
    
                // 현재 프래그먼트가 무엇인지 분기한다.
                if(pictureFragment != null){
                    // 글을 작성하는 Fragment라면
                    if(pictureFragment is BoardWriteFragment){
                        val f1 = pictureFragment as BoardWriteFragment
                        // 이미지 뷰에 설정해준다.
                        f1.fragmentBoardWriteBinding.imageViewBoardWrite.setImageBitmap(resizeBitmap)
                    }
                }
    
                // 사진 파일은 삭제한다.
                val file = File(contentUri.path!!)
                file.delete()
            }
        }
    }
```

### 메서드를 호출한다.
[BoardActivity.kt - onCreate()]
```kt
        
        // 카메라 실행을 위한 런처를 구성하는 메서드를 호출한다.
        settingCameraLauncher()
```

### 카메라 런처를 실행하는 메서드를 구현한다.
[BoardActivity.kt]
```kt

    // 카메라를 실행시키는 메서드
    fun startCameraLauncher(fragment:Fragment){
        pictureFragment = fragment

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // 촬영한 사진이 저장될 파일 이름
        val fileName = "/temp_${System.currentTimeMillis()}.jpg"
        // 경로 + 파일이름
        val picPath = "${filePath}${fileName}"
        val file = File(picPath)

        // 사진이 저장될 위치를 관리하는 Uri 객체를 생성ㅎ
        contentUri = FileProvider.getUriForFile(this@BoardActivity, "com.lion.boardproject.camera", file)

        // Activity를 실행한다.
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
        cameraLauncher.launch(cameraIntent)
    }
```

### 카메라 화면을 실행시킨다.
[fragment/BoardWriteFragment.kt - settingToolbar()]
```kt
                    // 카메라
                    R.id.menuItemBoardWriteCamera -> {
                        boardMainFragment.boardActivity.startCameraLauncher(this@BoardWriteFragment)
                    }
```

### 앨범을 위한 런처를 선언한다.
[BoardActivity.kt]
```kt
    // 앨범을 위한 런처
    lateinit var albumLauncher:ActivityResultLauncher<PickVisualMediaRequest>
```

### 런처를 구성하는 메서드를 구현한다.
[BoardActivity.kt]
```kt

    // 앨범에서 사진을 가져오는 런처 구성한다.
    fun settingAlbumLauncher(){
        // PhotoPicker를 실행할 수 있도록 런처를 구성해준다.
        albumLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){
            // 가져온 사진이 있다면
            if(it != null){
                var bitmap:Bitmap? = null

                // android 10 버전 이상이라면
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                    // 이미지 객체를 생성할 수 있는 디코드를 생성한다.
                    val source = ImageDecoder.createSource(contentResolver, it)
                    // Bitmap 객체를 생성한다.
                    bitmap = ImageDecoder.decodeBitmap(source)
                } else {
                    // ContentProvider를 통해 사진 데이터를 가져온다.
                    val cursor = contentResolver.query(it, null, null, null, null)
                    if(cursor != null){
                        cursor.moveToNext()

                        // 이미지의 경로를 가져온다.
                        val idx = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                        val source = cursor.getString(idx)

                        // 이미지를 생성한다.
                        bitmap = BitmapFactory.decodeFile(source)
                    }
                }
                // 이미지의 회전 각도값을 가져온다.
                // val degree = getDegree(it)
                // 회전 값을 이용해 이미지를 회전시킨다.
                // val rotateBitmap = rotateBitmap(bitmap!!, degree)
                // 크기를 조정한 이미지를 가져온다.
                // val resizeBitmap = resizeBitmap(1024, rotateBitmap)

                // 현재 프래그먼트가 무엇인지 분기한다.
                if(pictureFragment != null){
                    // 글을 작성하는 Fragment라면
                    if(pictureFragment is BoardWriteFragment){
                        val f1 = pictureFragment as BoardWriteFragment
                        // 이미지 뷰에 설정해준다.
                        f1.fragmentBoardWriteBinding.imageViewBoardWrite.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }
```

### 런처를 가동시키는 메서드를 구현한다.
[BoardActivity.kt]
```kt
    fun startAlbumLauncher(fragment:Fragment){
        pictureFragment = fragment

        // Activity를 실행한다.
        val pickVisualMediaRequest = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        albumLauncher.launch(pickVisualMediaRequest)
    }
```

### 메서드를 호출한다.
[BoardActivity - onCreate()]
```kt
        // 앨범에서 사진을 가져오는 런처 구성하는 메서드를 호출한다.
        settingAlbumLauncher()
```

### 런처를 가동시키는 메서드를 호출한다.
[fragment/BoardWriteFragment.kt - settingToolbar()]

```kt
                    // 앨범
                    R.id.menuItemBoardWriteAlbum -> {
                        boardMainFragment.boardActivity.startAlbumLauncher(this@BoardWriteFragment)
                    }
```