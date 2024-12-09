# ViewBinding 셋팅

[build.gradle.kts]

```kt
    viewBinding {
        enable = true
    }
```

[MainActivity.kt]

```kt
    val activityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(activityMainBinding.root)
```

---

# Navigation 구성

### 사용할 아이콘 파일들을 res/drawable 폴더에 넣어준다.

### NavigationView에 사용할 메뉴를 구성해준다.

[res/menu/navigation_menu.xml]
```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:android="http://schemas.android.com/apk/res/android">

    <item
        android:id="@+id/navigation_menu_item_all"
        android:icon="@drawable/list_alt_24px"
        android:title="모든 메모 "
        app:showAsAction="ifRoom" />
    <item
        android:id="@+id/navigation_menu_item_favorite"
        android:icon="@drawable/check_24px"
        android:title="즐겨 찾기"
        app:showAsAction="ifRoom" />
    <item
        android:id="@+id/navigation_menu_item_secret"
        android:icon="@drawable/person_24px"
        android:title="비밀 메모"
        app:showAsAction="ifRoom" />
    <item
        android:id="@+id/navigation_menu_item_management_category"
        android:icon="@drawable/edit_24px"
        android:title="카테고리 관리" />
    <item
        android:id="@+id/navigation_menu_item_memo_category"
        android:icon="@drawable/id_card_24px"
        android:title="메모 카테고리"
        app:showAsAction="ifRoom">
        <menu>
            <item
                android:id="@+id/navigation_menu_item_category_basic"
                android:icon="@drawable/storefront_24px"
                android:title="기본 메모"
                app:showAsAction="ifRoom" />
        </menu>
    </item>
</menu>
```

### NavigationView에 Header 설정할 layout을 작성한다.

[res/layout/navigation_header_layout.xml]

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="20dp"
    android:transitionGroup="true">

    <TextView
        android:id="@+id/textViewNavigationHeaderTitle1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="TextView"
        android:textAppearance="@style/TextAppearance.AppCompat.Large" />

    <TextView
        android:id="@+id/textViewNavigationHeaderTitle2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:text="TextView"
        android:textAppearance="@style/TextAppearance.AppCompat.Large" />
</LinearLayout>
```

### MainActivity의 화면을 구성해준다.

[res/layout/activity_main.xml]
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:transitionGroup="true"
    tools:context=".MainActivity">

    <androidx.drawerlayout.widget.DrawerLayout
        android:id="@+id/drawerLayoutMain"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" >

        <androidx.coordinatorlayout.widget.CoordinatorLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:orientation="vertical">

                <androidx.fragment.app.FragmentContainerView
                    android:id="@+id/fragmentContainerView"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent" />
            </LinearLayout>
        </androidx.coordinatorlayout.widget.CoordinatorLayout>

        <com.google.android.material.navigation.NavigationView
            android:id="@+id/navigationViewMain"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_gravity="start" />
    </androidx.drawerlayout.widget.DrawerLayout>
</androidx.constraintlayout.widget.ConstraintLayout>
```
### 네비게이션을 구성하기 위한 메서드를 구현해준다.

```kt
    // 네비게이션 뷰를 구성하는 메서드
    fun settingNavigationView(){
        activityMainBinding.apply {
            // 네비게이션 뷰의 해더
            val navigationHeaderLayoutBinding = NavigationHeaderLayoutBinding.inflate(layoutInflater)
            navigationHeaderLayoutBinding.textViewNavigationHeaderTitle1.text = "멋쟁이사자처럼"
            navigationHeaderLayoutBinding.textViewNavigationHeaderTitle2.text = "앱스쿨3기"
            navigationViewMain.addHeaderView(navigationHeaderLayoutBinding.root)

            // 메뉴
            navigationViewMain.inflateMenu(R.menu.navigation_menu)

            // 네비게이션 뷰의 메뉴 중 전체 메모가 선택되어 있는 상태로 설정한다.
            navigationViewMain.setCheckedItem(R.id.navigation_menu_item_all)

//            val menuItem = navigationViewMain.menu.findItem(R.id.navigation_menu_item_memo_category)
//            menuItem.subMenu?.add(Menu.NONE, 100, Menu.NONE, "새로추가한 메뉴")
//            val subMenuItem = menuItem.subMenu?.findItem(100)
//            subMenuItem?.setIcon(R.drawable.lock_24px)
        }
    }
```

### 메서드를 호출한다.

[MainActivity.kt - onCreate()]
```kt
        // 네비게이션 뷰를 구성하는 메서드를 호출한다.
        settingNavigationView()
```

---

# 기본 작업

### 라이브러리를 추가한다.

[build.gradle.kts]
```kt
plugins {
    ...
    kotlin("kapt")
}

dependencies {
    ...
    implementation("androidx.fragment:fragment-ktx:1.8.5")
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
}
```

### 프래그먼트 관련 기본 코드를 작성한다.

[MainActivity.kt]

```kt
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.google.android.material.transition.MaterialSharedAxis
```

```kt

    // 프래그먼트를 교체하는 함수
    fun replaceFragment(fragmentName: FragmentName, isAddToBackStack:Boolean, animate:Boolean, dataBundle: Bundle?){
        // 프래그먼트 객체
        val newFragment = when(fragmentName){

        }

        // bundle 객체가 null이 아니라면
        if(dataBundle != null){
            newFragment.arguments = dataBundle
        }

        // 프래그먼트 교체
        supportFragmentManager.commit {

            if(animate) {
                newFragment.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
                newFragment.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
                newFragment.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
                newFragment.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
            }

            replace(R.id.fragmentContainerView, newFragment)
            if(isAddToBackStack){
                addToBackStack(fragmentName.str)
            }
        }
    }

    // 프래그먼트를 BackStack에서 제거하는 메서드
    fun removeFragment(fragmentName: FragmentName){
        supportFragmentManager.popBackStack(fragmentName.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
```


### 패키지를 만들어준다.

- dao : Database와 연동되는 모든 기능들을 구현하는 요소
- model : 값을 담아 관리하기 위한 객체들을 통칭
- vo : 데이터 베이스에 저장할 값이나 저장된 값을 담을 프로퍼티를 가지고 있는 Model의 한 종류
- view model : 화면에 보이고 있는 UI 요소들에 설정할 값들을 담을 프로퍼티를 가지고 있는 Model의 한 종류
- 그 밖에 사용 목적에 따라 다양한 model을 정의해서 사용한다.
- repository : vo 에 담겨진 데이터를 다른 model 객체에 담아주는 역할을 한다.
- service : model에 담겨진 데이터를 화면처리하는 요소로 전달하는 역할을 수행한다. 이 때, 다양한 데이터의 가공처리로 수행해준다.


- database
- dao
- model
- vo
- repository
- util
- fragment

---

# 모든 메모 화면 구성

### ShowMemoAllFragment 를 만들어준다.

[fragment/ShowMemoAllFragment.kt]
```kt
class ShowMemoAllFragment : Fragment() {

    lateinit var fragmentShowMemoAllBinding: FragmentShowMemoAllBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentShowMemoAllBinding = FragmentShowMemoAllBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        return fragmentShowMemoAllBinding.root
    }
}
```

### Fragment의 이름을 정의학 위한 util/Values.kt 파일을 만들어준다.

### Fragment의 이름을 정의해준다.

[util/Values.kt]
```kt
// 프래그먼트들의 이름
enum class FragmentName(val number:Int, var str:String){
    SHOW_MEMO_ALL_FRAGMENT(1, "ShowMemoAllFragment"),
}
```


### 프래그먼트의 객체를 생성한다.
[MainActivity.kt - replaceFragment()]
```kt
            // 전체 메모 화면
            FragmentName.SHOW_MEMO_ALL_FRAGMENT -> ShowMemoAllFragment()
```


### 첫 번째 프래그 먼트를 설정해준다.
[MainActivity.kt - onCreate()]
```kt
        // 첫 화면을 설정해준다.
        replaceFragment(FragmentName.SHOW_MEMO_ALL_FRAGMENT, false, false, null)
```

### Recyclerview의 항목으로 사용할 layout을 만들어준다.

[res/layout/row_text1.xml]
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:padding="10dp"
    android:transitionGroup="true">

    <TextView
        android:id="@+id/textViewRow"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="TextView"
        android:textAppearance="@style/TextAppearance.AppCompat.Large" />
</LinearLayout>
```

### ShowMemoAllFragment의 화면을 구성한다

[res/layout/fragment_show_memo_all.xml]

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:transitionGroup="true"
    tools:context=".fragment.ShowMemoAllFragment" >

    <com.google.android.material.appbar.MaterialToolbar
        android:id="@+id/toolbarShowMemoAll"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:background="@android:color/transparent"
        android:minHeight="?attr/actionBarSize"
        android:theme="?attr/actionBarTheme"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerViewShowMemoAll"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/toolbarShowMemoAll" />

    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fabShowMemoAllAdd"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginEnd="20dp"
        android:layout_marginBottom="20dp"
        android:clickable="true"
        android:src="@drawable/add_24px"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

### RecyclerView 구성을 위한 임시 데이터를 정의한다

[fragment/ShowMemoAllFragment.kt]
```kt
    // RecyclerView 구성을 위한 임시데이터
    val tempData1 = Array(100){
        "메모 제목 $it"
    }
```

### ToolBar를 설정하는 메서드를 구현한다.

[fragment/ShowMemoAllFragment.kt]
```kt
    // Toolbar를 구성하는 메서드
    fun settingToolbar(){
        fragmentShowMemoAllBinding.apply {
            toolbarShowMemoAll.title = "전체 메모"
            // 네비게이션 아이콘을 설정하고 누를 경우 NavigationView가 나타나도록 한다.
            toolbarShowMemoAll.setNavigationIcon(R.drawable.menu_24px)
            toolbarShowMemoAll.setNavigationOnClickListener {
                mainActivity.activityMainBinding.drawerLayoutMain.open()
            }
        }
    }
```

### 메서드를 호출해준다.

[fragment/ShowMemoAllFragment.kt - onCreateView()]
```kt
        // Toolbar를 구성하는 메서드를 호출한다.
        settingToolbar()
```

### Adapter 클래스를 작성한다.
[fragment/ShowMemoAllFragment.kt]
```kt
    // RecyclerView의 어뎁터
    inner class RecyclerShowMemoAdapter : RecyclerView.Adapter<RecyclerShowMemoAdapter.ViewHolderMemoAdapter>(){
        // ViewHolder
        inner class ViewHolderMemoAdapter(val rowText1Binding: RowText1Binding) : RecyclerView.ViewHolder(rowText1Binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMemoAdapter {
            val rowText1Binding = RowText1Binding.inflate(layoutInflater, parent, false)
            val viewHolderMemoAdapter = ViewHolderMemoAdapter(rowText1Binding)
            return viewHolderMemoAdapter
        }

        override fun getItemCount(): Int {
            return tempData1.size
        }

        override fun onBindViewHolder(holder: ViewHolderMemoAdapter, position: Int) {
            holder.rowText1Binding.textViewRow.text = tempData1[position]
        }
    }
```

### RecyclerView를 구성하는 메서드를 구현한다.

[fragment/ShowMemoAllFragment.kt]
```kt

    // RecyclerView를 구성하는 메서드
    fun settingRecyclerView(){
        fragmentShowMemoAllBinding.apply {
            recyclerViewShowMemoAll.adapter = RecyclerShowMemoAdapter()
            recyclerViewShowMemoAll.layoutManager = LinearLayoutManager(mainActivity)
            val deco = MaterialDividerItemDecoration(mainActivity, MaterialDividerItemDecoration.VERTICAL)
            recyclerViewShowMemoAll.addItemDecoration(deco)
        }
    }

```

### 메서드를 호출한다.

[fragment/ShowMemoAllFragment.kt - onCreateView()]
```kt
        // RecyclerView를 구성하는 메서드를 호출한다.
        settingRecyclerView()

```

---

# 메모 입력 화면

### AddMemoFragment를 만들어준다.

[fragment/AddMemoFragment.kt]
```kt
class AddMemoFragment : Fragment() {

    lateinit var fragmentAddMemoBinding: FragmentAddMemoBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentAddMemoBinding = FragmentAddMemoBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        return fragmentAddMemoBinding.root
    }

}
```

### Fragment 이름을 추가해준다.

[util/Values.kt - FragmentName]
```kt
    // 메모 추가 화면
    ADD_MEMO_FRAGMENT(2, "AddMemoFragment"),
```

### Fragment 객체를 생성한다.

[MainActivity.kt - replaceFragment()]
```kt
            // 메모 추가 화면
            FragmentName.ADD_MEMO_FRAGMENT -> AddMemoFragment()
```

### ShowMemoAllFragment 에서 FAB를 누르면 AddMemoFragment가 보이도록 한다.

[fragment/ShowMemoAllFragment.kt]
```kt
    // 버튼을 설정하는 메서드
    fun settingButton(){
        fragmentShowMemoAllBinding.apply {
            // fab를 누를 때
            fabShowMemoAllAdd.setOnClickListener { 
                // AddMemoFragment가 나타나게 한다.
                mainActivity.replaceFragment(FragmentName.ADD_MEMO_FRAGMENT, true, true, null)
            }
        }
    }
```

### 메서드를 호출한다.

[fragment/ShowMemoAllFragment.kt - onCreateView()]
```kt
        // 버튼을 설정하는 메서드를 호출한다.
        settingButton()
```

