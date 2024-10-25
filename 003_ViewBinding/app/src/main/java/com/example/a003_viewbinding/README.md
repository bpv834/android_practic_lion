# View Binding

- layout 폴더에 있는 xml 파일 하나당 하나의 클래스가 만들어진다.
- xml 파일에 배치한 화면 요소 객체들을 관리하는 객체를 생성한다.
- 화면 요소에 id를 설정하면 설정한 id와 동일한 이름의 프로퍼티가 추가되고 해당 화면 요소 객체를 담아준다.
- 개발자는 사용하고자 하는 화면 요소 객체를 직접 가져오지 않아도 되는 편리함을 갖게 된다.

### 생성되는 View Binding 클래스의 이름
- 만약 xml 파일 이름이 activity_main.xml 이라면 ActivityMainBinding
- 만약 xml 파일 이름이 main_screen.xml 이라면 MainScreenBinding
- 만약 xml 파일 이름이 activity_test.xml 이라면 ActivityTestBinding
- 만약 xml 파일 이름이 aaa_bbb_ccc.xml 이라면 AaaBbbCccBinding

### ViewBinding을 사용하기 위한 설정
1. build.gradle.kts (Module:app) 파일에 ViewBinding 사용 설정을 해준다.

```kt
viewBinding {
    enable = true
}
```

2. 우측 상단에 있는 "Sync Now"를 눌러준다.

3. 코드로 와서 다음과 같이 작성해준다.

```kt
    // ViewBinding 객체를 담을 변수
lateinit var activityMainBinding: ActivityMainBinding

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    // ViewBinding 객체를 가져온다.
    // layoutInflater : layout 폴더에 있는 xml 파일을 통해 화면 객체를 만들 수 있는 도구
    activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
    // ViewBinding 객체가 관리하는 화면 요소 중 최 상단에 있는 화면 요소를 지정하여 화면이 나오도록 한다.
    setContentView(activityMainBinding.root)
```