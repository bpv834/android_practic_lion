# MVVM 설정

### 1. databinding 설정
[build.gradle.kts]

```kt
    dataBinding{
        enable = true
    }
```

### 2. res/layout 폴더에 있는 모든 xml 파일을 수정해준다.
- 모든 xml 코드를 layout 태그로 묶어줘야 한다.

[res/layout/row.xml]
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout
    xmlns:android="http://schemas.android.com/apk/res/android">
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="10dp">

        <TextView
            android:id="@+id/textViewRow"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="TextView"
            android:textAppearance="@style/TextAppearance.AppCompat.Large" />
    </LinearLayout>
</layout>

```

[res/layout/activity_input.xml]

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
        tools:context=".InputActivity">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent">

            <EditText
                android:id="@+id/editTextInput"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:ems="10"
                android:inputType="text" />

            <Button
                android:id="@+id/buttonFinish"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Button" />
        </LinearLayout>
    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>

```

[res/layout/activity_main.xml]

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
        tools:context=".MainActivity">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent">

            <Button
                android:id="@+id/button"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Button" />

            <androidx.recyclerview.widget.RecyclerView
                android:id="@+id/recyclerView"
                android:layout_width="match_parent"
                android:layout_height="match_parent" />
        </LinearLayout>
    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>

```

[res/layout/activity_result.xml]
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
        tools:context=".ResultActivity">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent">

            <TextView
                android:id="@+id/textViewResult"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="TextView"
                android:textAppearance="@style/TextAppearance.AppCompat.Large" />
        </LinearLayout>
    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>

```

### 3. ViewModel 클래스를 화면의 수 만큼 만들어준다.
- InputViewModel
- MainViewModel
- ResultViewModel
- RowViewModel

[InputViewModel.kt]
```kt
class InputViewModel : ViewModel() {
}
```
[InputViewModel.kt]
```kt
class InputViewModel : ViewModel() {
}
```

[MainViewModel.kt]
```kt
class MainViewModel : ViewModel() {
}
```

[ResultViewModel.kt]
```kt
class ResultViewModel : ViewModel() {
}
```

[RowViewModel.kt]
```kt
class RowViewModel : ViewModel() {
}
```

---

### 4. res/layout 폴더에 있는 모든 xml 파일에 ViewModel 클래스를 지정한다.

[res/layout/activity_input.xml]

```xml
    <data>
        <variable
            name="inputViewModel"
            type="com.lion.a10_mvvm.InputViewModel" />
    </data>
```

[res/layout/activity_main.xml]

```xml
    <data>
        <variable
            name="mainViewModel"
            type="com.lion.a10_mvvm.MainViewModel" />
    </data>
```

[res/layout/activity_result.xml]
```xml

    <data>
        <variable
            name="resultViewModel"
            type="com.lion.a10_mvvm.ResultViewModel" />
    </data>
```

[res/layout/row.xml]

```xml
    <data>
        <variable
            name="rowViewModel"
            type="com.lion.a10_mvvm.RowViewModel" />
    </data>
```

---

# layout 의 xml의 ui 요소의 속성과 연결할 때 사용하는 문법
- ui 요소로 ViewModel의 값을 넣어주기만 하는 경우 : @{ }
- ui 요소로 ViewModel의 값을 넣어주고 ui 요소의 값을 ViewModel에 넣어주고 싶을때 : @={ }

---

# Activity에 ViewModel 기본 코드 작성

[InputActivity.kt]

```kt
    // ViewModel
    lateinit var inputViewModel:InputViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // activityInputBinding = ActivityInputBinding.inflate(layoutInflater)
        // setContentView(activityInputBinding.root)
        // 두 번째 : 사용할 layout 파일
        activityInputBinding = DataBindingUtil.setContentView(this@InputActivity, R.layout.activity_input)
        // ViewModel 객체를 생성한다.
        inputViewModel = InputViewModel()
        // ViewModel 객체를 DataBinding 객체에 설정해준다.
        activityInputBinding.inputViewModel = inputViewModel
        // DataBiding 객체의 오너를 설정해준다.
        activityInputBinding.lifecycleOwner = this@InputActivity

```

[MainActivity.kt]
```kt
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        // setContentView(activityMainBinding.root)
        activityMainBinding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        mainViewModel = MainViewModel()
        activityMainBinding.mainViewModel = mainViewModel
        activityMainBinding.lifecycleOwner = this@MainActivity
```

[ResultActivity.kt]

```kt
    lateinit var resultViewModel: ResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // activityResultBinding = ActivityResultBinding.inflate(layoutInflater)
        // setContentView(activityResultBinding.root)
        activityResultBinding = DataBindingUtil.setContentView(this@ResultActivity, R.layout.activity_result)
        resultViewModel = ResultViewModel()
        activityResultBinding.resultViewModel = resultViewModel
        activityResultBinding.lifecycleOwner = this@ResultActivity
```
---
# 입력 처리

### InputViewModel에 EditText의 text 속성과 연결할 LiveData를 정의해준다.

[InputViewModel.kt]
```kt
    // editTextInput - text
    val editTextInputText = MutableLiveData("")
```

### layout 파일에 속성을 추가해준다.

[res/layout/activity_input.xml]
```xml
android:text="@={inputViewModel.editTextInputText}"
```