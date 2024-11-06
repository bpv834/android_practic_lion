# ViewBinding 셋팅

### ViewBinding 코드를 작성해준다.

[build.gradle.kts(Module:app)]

```kt
    viewBinding {
        enable = true
    }
```

### ViewBinding 코드를 작성해준다.

[MainActivity.kt]

```kt
    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
```

---

# 사용할 Fragment 생성

### com.lion.a054ex_ui.fragment 패키지를 생성해준다.

### fragment 패키지에 두 개의 Fragment를 생성해준다.
- RecyclerFragment 
- TabFragment

---

# NavigationView 구성하기

### RecyclerFragment 에대 화면 작업을 한다.

```text
LinearLayout
    - orientation : vertical

    MaterialToolbar
        - id : materialToolbarRecycler
        - background : transparent
```

### Fragment 기본 코드를 작성한다.

[RecyclerFragment.kt]

```kt
    lateinit var fragmentRecyclerBinding: FragmentRecyclerBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentRecyclerBinding = FragmentRecyclerBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        return fragmentRecyclerBinding.root
    }
```

### Toolbar를 구성하는 메서드를 구현한다.

[RecyclerFragment.kt]

```kt
    // 툴바를 구성하는 메서드
    fun settingToolbar(){
        fragmentRecyclerBinding.materialToolbarRecycler.apply {
            // 타이틀
            title = "Recycler"
            // 좌측 네비게이션 버튼
            setNavigationIcon(R.drawable.menu_24px)
        }
    }
```

### 메서드를 호출한다.

[RecyclerFragment.kt - onCreateView()]

```kt
        // 툴바를 구성하는 메서드를 호출한다
        settingToolbar()
```

### activity_main.xml을 구성한다.

```text
ConstraintLayout
    - id : main

    DrawerLayout
        - layout_constraintBottom_toBottomOf : parent
        - layout_constraintEnd_toEndOf : parent
        - layout_constraintStart_toStartOf : parent
        - layout_constraintTop_toTopOf : parent
        - id : drawerlayoutMain
        - layout_width : 0dp
        - layout_height : 0dp

        CoordinatorLayout
            - layout_width : match_parent
            - layout_height : match_parent

            FragmentContainerView
                - id : fragmentContainerViewMain
                - layout_width : match_parent
                - layout_height : match_parent

    NavigationView
        - id : navigationViewMain
        - layout_gravity : start
        - layout_width : match_parent
        - layout_height : match_parent
        - menu : navigation_main_menu
```

### 네비게이션 뷰가 나타날 수 있도록 구현해준다.

[RecyclerFragment.kt - settingToolbar()]

```kt
            // 좌측 네비게이션 버튼을 누르면 NavigationView가 나타나게 한다.
            setNavigationOnClickListener {
                mainActivity.activityMainBinding.drawerlayoutMain.open()
            }
```

### 네비게이션 뷰에서 사용할 메뉴 파일을 만딀어준다.

[res/menu/navigation_main_menu.xml]

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:android="http://schemas.android.com/apk/res/android">

    <item android:title="화면들" >
        <menu >
            <item
                android:id="@+id/navigation_main_item1"
                android:checkable="true"
                android:icon="@android:drawable/ic_btn_speak_now"
                android:title="RecyclerFragment"
                app:showAsAction="ifRoom" />
            <item
                android:id="@+id/navigation_main_item2"
                android:checkable="true"
                android:icon="@android:drawable/ic_dialog_dialer"
                android:title="TabFragment"
                app:showAsAction="ifRoom" />
        </menu>
    </item>
</menu>
```

### NavigationView를 설정하는 메서드를 구현한다.

[MainActivity.kt]
```kt
    // NavigtionView를 설정하는 메서드
    fun settingNavigationView(){

        activityMainBinding.navigationViewMain.apply {
            // 제일 처음 메뉴를 선택된 상태로 설정한다.
            setCheckedItem(R.id.navigation_main_item1)

            setNavigationItemSelectedListener {
                if(it.isCheckable == true){
                    // 체크상태를 true로 해준다.
                    it.isChecked = true
                }

                when(it.itemId){
                    R.id.navigation_main_item1 -> {
                        supportFragmentManager.commit {
                            replace(R.id.fragmentContainerViewMain, RecyclerFragment())
                        }
                    }
                    R.id.navigation_main_item2 -> {
                        supportFragmentManager.commit {
                            replace(R.id.fragmentContainerViewMain, TabFragment())
                        }
                    }
                }

                // NavigationView를 닫아준다.
                activityMainBinding.drawerlayoutMain.close()

                true
            }
        }
    }
```

### 메서드를 호출한다.

[MainActivity.kt - onCreate()]

```kt

        // NavigationView 설정 메서드 호출
        settingNavigationView()
```

### fragemnt_tab.xml 파일을 작성한다.

```text
LinearLayout
    - orientation : vertical

    MaterialToolbar
        - id : materialToolbarTab
        - background : transparent
```

### TabFragment의 기본  코드를 작성해준다.

[TabFragment.kt]

```kt

    lateinit var fragmentTabBinding: FragmentTabBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentTabBinding = FragmentTabBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        return fragmentTabBinding.root
    }
```

### 툴바 셋팅 메서드를 작성해준다.

[TabFragment.kt]
```kt
    // 툴바를 셋팅하는 메서드
    fun settingToolbar(){
        fragmentTabBinding.materialToolbarTab.apply {
            // 타이틀
            title = "Tab"
            // 좌측 네비게이션 버튼
            setNavigationIcon(R.drawable.menu_24px)
            // 좌측 네비게이션 버튼을 누르면 NavigationView가 나타나게 한다.
            setNavigationOnClickListener {
                mainActivity.activityMainBinding.drawerlayoutMain.open()
            }
        }
    }
```

### 메서드를 호출해준다.

[TabFragment.kt - onCreateView()]
```kt
        // 툴바를 셋팅하는 메서드를 호출해준다.
        settingToolbar()
```

---

# RecyclerFragment의 화면을 구성한다.

### 메뉴 파일을 작성한다.

[res/menu/toolbar_recycler_menu.xml]

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:android="http://schemas.android.com/apk/res/android">

    <item
        android:id="@+id/recycler_menu_item_add"
        android:icon="@drawable/add_24px"
        android:title="추가"
        app:showAsAction="ifRoom" />
</menu>
```

### Toolbar의 menu 속성에 메뉴 파일을 설정해준다.

[res/layout/fragment_recycler.xml]

```text
menu	@menu/toolbar_recycler_menu
```


### Dialog에서 사용할 layout을 구성한다.

[res/layout/dialog_recycler_input.xml]

```text
LinearLayout
    - orientation :vertical
    - padding : 10dp

    EditText (Plain)
        - id : editTextDialogRecycler
        - text : 삭제
        - hint : 문자열 입력
```


### Toolbar의 메뉴를 누르면 호출되는 부분을 구현한 메서드를 작성한다.

[RecyclerFramgment.kt]

```kt
    // 툴바의 메뉴를 구성하는 메서드
    fun settingToolbarMenu(){
        fragmentRecyclerBinding.materialToolbarRecycler.apply {
            // 메뉴를 눌렀을 때 동작하는 리스너
            setOnMenuItemClickListener {
                // 메뉴의 id로 분기한다.
                when(it.itemId){
                    R.id.recycler_menu_item_add -> {
                        // 다이얼로그를 띄운다.
                        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(mainActivity)
                        // 타이틀
                        materialAlertDialogBuilder.setTitle("문자열 입력")
                        // 보여줄 뷰
                        val dialogRecyclerInputBinding = DialogRecyclerInputBinding.inflate(layoutInflater)
                        materialAlertDialogBuilder.setView(dialogRecyclerInputBinding.root)
                        // 버튼
                        materialAlertDialogBuilder.setNegativeButton("취소", null)
                        materialAlertDialogBuilder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->

                        }
                        // 띄운다.
                        materialAlertDialogBuilder.show()
                    }
                }
                true
            }
        }
    }
```

### 메서드를 호출한다.

[RecyclerFragment.kt - onCreateView()]
```kt
        // 툴바의 메뉴 이벤트 메서드를 호출한다.
        settingToolbarMenu()

```

### 키보드에 대한 처리를 구현해준다.

[RecyclerFragment.kt - settingToolbarMenu()]

```kt
                        // 키보드를 올려준다.
                        thread {
                            SystemClock.sleep(1000)
                            mainActivity.runOnUiThread {
                                dialogRecyclerInputBinding.editTextDialogRecycler.requestFocus()
                                val imm = mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                                imm.showSoftInput(dialogRecyclerInputBinding.editTextDialogRecycler, 0)
                            }
                        }

                        // 띄운다.
                        val materialAlertDialog = materialAlertDialogBuilder.create()
                        materialAlertDialog.show()

                        // 키보드의 엔터키를 눌렀을 때
                        dialogRecyclerInputBinding.editTextDialogRecycler.setOnEditorActionListener { v, actionId, event ->
                            // 다이얼로그를 없애준다.
                            materialAlertDialog.hide()
                            false
                        }
```

### ReyclerView 구성을 위해 사용할 리스트를 정의해준다.

[MainActivity.kt]

```kt
    // 리사이클러뷰를 구성하기 위해 사용할 리스트
    val recyclerViewList = mutableListOf<String>()
```

### Recyclerview의 항목으로 사용할 layout 파일을 만들어준다.
[ fragment_recycler_row.xml ]

```text
LinearLayout
    - orientation : vertical
    - padding : 10dp

    TextView
        - id : textViewRecyclerRow
        - textAppearance : Large
```

### fragment_recycler.xml 에 RecyclerView를 배치해준다.

```text
    RecyclerView
        - id : recyclerViewRecycler
```

### RecyclerView에 적용할 어뎁터 클래스를 작성한다.

[RecyclerFragment.kt]

```kt
    // RecyclerView의 어뎁터
    inner class RecyclerViewRecyclerAdapter : RecyclerView.Adapter<RecyclerViewRecyclerAdapter.ViewHolderRecycler>(){
        // ViewHolder클래스
        inner class ViewHolderRecycler(var fragmentRecyclerRowBinding: FragmentRecyclerRowBinding) : RecyclerView.ViewHolder(fragmentRecyclerRowBinding.root){

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderRecycler {
            val fragmentRecyclerRowBinding = FragmentRecyclerRowBinding.inflate(layoutInflater)
            val viewHolderRecycler = ViewHolderRecycler(fragmentRecyclerRowBinding)
            return viewHolderRecycler
        }

        override fun getItemCount(): Int {
            return mainActivity.recyclerViewList.size
        }

        override fun onBindViewHolder(holder: ViewHolderRecycler, position: Int) {
            holder.fragmentRecyclerRowBinding.textViewRecyclerRow.text = mainActivity.recyclerViewList[position]
        }
    }
```

### RecyclerView를 구성하는 메서드를 만들어준다.

[RecyclerFragment.kt]

```kt
    fun settingRecyclerView(){
        // RecyclerView 설정
        fragmentRecyclerBinding.recyclerViewRecycler.apply {
            // 어뎁터
            adapter = RecyclerViewRecyclerAdapter()
            // 보여지는 방식
            layoutManager = LinearLayoutManager(mainActivity)
            // 구분선
            val deco = MaterialDividerItemDecoration(mainActivity, MaterialDividerItemDecoration.VERTICAL)
            addItemDecoration(deco)
        }
    }
```

### 메서드를 호출한다.

[RecyclerFragment.kt - onCreateView()]

```kt
        // RecyclerView 구성 메서드를 호출한다.
        settingRecyclerView()
```
