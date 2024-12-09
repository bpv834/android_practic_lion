# LBS
- Location Base Service
- 사용자 위치를 기반으로 하는 서비스
- 구글 지도를 사용한다.
- GPS 를 이용한 사용자의 현재 위치 파악
- 구글 open api 사용

---

# 01_프로젝트 만들기 및 ViewBinding 셋팅

[build.gradle.kts]

```kt
    viewBinding {
        enable = true
    }
```

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

# 02_기본화면 구성하기

### 화면 구성

[res/layout/activity_main.xml]
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
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

        <com.google.android.material.appbar.MaterialToolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@android:color/transparent"
            android:minHeight="?attr/actionBarSize"
            android:theme="?attr/actionBarTheme" />
    </LinearLayout>
</androidx.constraintlayout.widget.ConstraintLayout>
```

### 아이콘 이미지를 넣어준다.
- res/drawable 폴더에 넣어준다.
- my_location_24px.xml
- storefront_24px.xml

### 툴바에 사용할 메뉴 파일을 만들어준다.

[res/menu/toolbar_main_menu.xml]

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:android="http://schemas.android.com/apk/res/android">

    <item
        android:id="@+id/main_menu_item_my_location"
        android:icon="@drawable/my_location_24px"
        android:title="내위치"
        app:showAsAction="always" />
    <item
        android:id="@+id/main_menu_item_place"
        android:icon="@drawable/storefront_24px"
        android:title="주변정보"
        app:showAsAction="always" />
</menu>
```

### 툴바를 구성하는 메서드를 구현한다.

[MainActivity.kt]

```kt
    // 상단 툴바를 구성하는 메서드
    fun settingToolbar(){
        activityMainBinding.apply {
            // 타이틀
            toolbar.title = "위치기반서비스"
            // 메뉴
            toolbar.inflateMenu(R.menu.toolbar_main_menu)
            // 메뉴의 항목을 눌렀을 때
            toolbar.setOnMenuItemClickListener {
                when(it.itemId){
                    // 현재 위치 메뉴
                    R.id.main_menu_item_my_location -> {

                    }
                    // 주변 정보
                    R.id.main_menu_item_place -> {
                        
                    }
                }
                true
            }
        }
    }
```

### 메서드를 호출한다.

[MainActivity.kt - onCreate()]
```kt
        // 상단 툴바를 구성하는 메서드를 호출한다.
        settingToolbar()
```

---

# 03_구글 지도 사용하기

### 구글 계정을 준비한다.
- 일반적인 구글 계정을 사용하면 된다.

### 구글 개발자 콘솔에 들어간다.
- https://console.cloud.google.com/

### 약관 동의
- 처음 들어오는 계정이라면 구글 클라우드 사용 약관이 나타난다.
- 서비스 약관만 모두 동의한다

### 새 프로젝트 만들기
- 사이트 상단에 있는 "프로젝트 선택" 을 눌러준다.
- "프로젝트 선택"을 눌러 나타난 다이얼로그 화면의 우측 상단에 있는 "새 프로젝트" 를 눌러준다.

### 구글 지도 API 사용 설정을 위한 셋팅 1
- 프로젝트 생성이 완료되었다면 사이트 좌측 상단의 메뉴 버튼을 눌러준다.
- 좌측에 나타나는 메뉴에서 "API 및 서비스" > "사용 설정된 API 및 서비스" 를 눌러준다.
- 만약 프로젝트를 선택하지 않은 상태라고 한다면 프로젝트 선택 화면이 나온다. 여기서 프로젝트를 선택해준다.
- 상단에 있는 "+ API 및 서비스 사용 설정"을 눌러준다.
- 목록에 나오는 서비스들 중에 "Maps SDK for Android"를 눌러준다.
- "Maps SDK for Android" 세부 정보 화면에서 "사용" 버튼을 눌러준다
- 다음 화면부터 나오는 정보 입력 화면에서 모든 정보를 입력해준다.
- 정보를 모두 입력하면 설문조사 화면이 나타난다. 모두 작성한다.
- 설문조사 과정이 끝나면 API키가 발급되어 보여진다. 이를 복사해둔다(나중에 확인 가능합니다)
- 다음 화면에서 "API 키 보호" 설정이라는 것이 나온다.

### API 키 보호 관련 작업
- select restriction type 은 "Android 앱"으로 선택해준다.
- 패키지 이름은 안드로이드 애플리케이션 패키지 명을 넣어줘야 한다.
- build.gradle.kts 파일을 열어준다.
- android { defaultConfig { applicationId 에 있는 것을 넣어준다.
- SHA-1 인증서 지문은 다음 과정을 따라 진행해서 파악한다(PPT 24 ~ 27)
- 먼저 안드로이드 스튜디오에서 터미널을 띄워준다.
- 터미널에서 다음과 같이 입력하고 ctrl + enter
- gradlew signingReport 
- ./gradlew signingReport
- 만약 안되면 JDK 경로 설정 문제이다.
- 다시 터미널을 띄운다.
- cmd 를 입력하여 명령프롬프트로 변경한다.
- 다음과 같이 입력하여 path설정을 한다.
- path C:\Program Files\Android\Android Studio\jbr\bin
- 그리고 gradlew signingReport 를 입력하고 엔터를 눌러준다.
- 발급받은 SHA1 값을 복사한다

### 구글 지도 API 사용 설정을 위한 셋팅 2
- 복사한 SHA1 값을 넣어주고 "키 제한"을 눌러준다

### 안드로이드 프로젝트 작업
- AndroidManifest.xml 에 발급받은 API 키 값을 넣어준다.

```xml
        <meta-data android:name="com.google.android.geo.API_KEY"
            android:value="발급받은 API 키값"/>
```

- 상단 메뉴의 Tools > SDK Manager를 클릭한다.
- Android SDK에 있는 SDK Tools 탭에서 "Google Play Services"를 체크하고 OK 를 눌러 설치한다.
- builde.gradle.kts 에서 라이브러리를 추가해준다.
```kt
    implementation("com.google.android.gms:play-services-maps:19.0.0")
    implementation("com.google.android.gms:play-services-location:21.3.0") 
```
- MapFragment를 배치해준다.
[res/layout/activity_main.xml]

```xml
    <fragment
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/mapFragment"
        android:name="com.google.android.gms.maps.SupportMapFragment"/>
```

- MainActivity에 다음 코드를 넣어준다.
```kt
    // 구글 지도에 표시되는 맵 이미지를 최신 버전 이미지로 사용하도록 설정한다.
    MapsInitializer.initialize(this@MainActivity, MapsInitializer.Renderer.LATEST, null) 
```
--- 

# 04_현재 위치 가져오기
- 사용자 단말기의 현재 위치를 제공하는 Provider는 다음과 같다.
- Passive Provider : 이전에 측정되어 저장되어 있는 위치 정보 제공자. 
- Network Provider : 연결되어 있는 통신망 업자가 제공하는 위치 정보 제공자.
- GPS Provider : GPS 위성과 연결되어 위치를 측정하고 제공하는 위치 정보 제공자.
- 측정 정확도 : GPS Provider > Network Provider > Passive Provider
- 측정 속도 : Passive Provider > Network Provider > GPS Provider

### 작업 순서
- Passive Provider를 통해서 위치 정보값을 가져와 사용한다.
- Network Provider와 GPS Provider에게 위치 정보를 요청한다.
- 두 Provider를 통해 새롭게 측정된 값을 새롭게 사용한다.

### 권한 추가
[AndroidManifest.xml]

- ACCESS_FINE_LOCATION : 정밀한 위치 측정 권한(GPS)
- ACCESS_COARSE_LOCATION : 대략적인 위치 측정 권한(Network, Passive)
- 현재는 두 권한 모두 설정해야 GPS, Network, Passive 사용이 가능하다

```xml
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
```

### 확인할 권한 목록을 작성해준다.

[MainActivity.kt]

```kt
    // 확인할 권한 목록
    val permissionList = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )
```

### 권한 확인을 위해 사용할 런처를 선언해준다.

[MainActivity.kt]
```kt
    // 권한 확인을 위한 런처
    lateinit var permissionCheckLauncher:ActivityResultLauncher<Array<String>>
```

### 권한 확인을 위한 런처를 등록하는 메서드를 만들어준다.

[MainActivity.kt]
```kt
    // 권한 확인을 위해 사용할 런처 생성
    fun createPermissionCheckLauncher(){
        // 런처를 등록한다.
        permissionCheckLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            Snackbar.make(activityMainBinding.root, "권한 확인 완료", Snackbar.LENGTH_LONG).show()
        }
    }
```

### 메서드를 호출한다.

[MainActivity.kt - onCreate()]
```kt
// 권한 확인을 위한 런처 생성 메서드 호출
        createPermissionCheckLauncher()
```

### 애플리케이션의 설정화면을 띄울 수 있는 메서드를 만들어준다.

```kt
    // 애플리케이션의 설정화면을 실행시키는 메서드
    fun startSettingActivity(){
        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(this@MainActivity)
        materialAlertDialogBuilder.setTitle("권한 확인 요청")
        materialAlertDialogBuilder.setMessage("권한을 모두 허용해줘야 정상적은 서비스 이용이 가능합니다")
        materialAlertDialogBuilder.setPositiveButton("권한 설정 하기"){ dialogInterface: DialogInterface, i: Int ->
            val uri = Uri.fromParts("package", packageName, null)
            val permissionIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri)
            permissionIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(permissionIntent)
        }
        materialAlertDialogBuilder.show()
    }
```

### createPermissionCheckLauncher 메서드를 수정한다.

[MainActivity.kt]

```kt
    // 권한 확인을 위해 사용할 런처 생성
    fun createPermissionCheckLauncher(){
        // 런처를 등록한다.
        permissionCheckLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            // Snackbar.make(activityMainBinding.root, "권한 확인 완료", Snackbar.LENGTH_LONG).show()
            // 모든 권한에 대해 확인한다.
            permissionList.forEach { permissionName ->
                // 현재 권한이 허용되어 있지 않다면 다이얼로그를 띄운다.
                if(it[permissionName] == false){
                    // 설정 화면을 띄우는 메서드를 호출한다.
                    startSettingActivity()
                    // 함수 종료
                    return@registerForActivityResult
                }
            }
        }
    }
```

### 변수들을 선언한다.

[MainActivity.kt]

```kt
    // 위치 정보 관리 객체
    lateinit var locationManager: LocationManager
    // 위치 측정을 하면 반응하는 리스너
    lateinit var myLocationListener: MyLocationListener
```

### 현재를 측정하기 위한 메서드를 만들어주고 권한 확인 코드를 작성한다.

```kt
    // 현재 위치를 측정하는 메서드
    fun getMyLocation(){
        // 권한 허용 여부
        val check1 = ActivityCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION)
        val check2 = ActivityCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION)
        // 만약 거부한 것이 하나라도 있다면
        if(check1 == PackageManager.PERMISSION_DENIED || check2 == PackageManager.PERMISSION_DENIED){
            // 권한 확인을 위한 Activity를 실행한다.
            permissionCheckLauncher.launch(permissionList)
            return
        }

    }
```

### 메서드를 호출해준다.

[MainActivity.kt - settingToolbar()]
```kt
                    // 현재 위치 메뉴
                    R.id.main_menu_item_my_location -> {
                        // 현재 위치를 측정한다.
                        getMyLocation()
                    }
```

### 측정을 시작한다.

[MainActivity.kt - getMyLocation()]
```kt
        // GPS 프로바이더 사용이 가능하다면
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0.0f, myLocationListener)
        }
        // Network 프로바이더 사용이 가능하다면
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0.0f, myLocationListener)
        }
```

---

# 05_구글 지도에 사용위치 표시하기

### 구글 지도 객체를 담을 변수를 선언한다.

[MainActivity.kt]
```kt
    // 구글 지도 객체를 담을 변수를 선언한다.
    lateinit var mainGoogleMap:GoogleMap
```

### 구글 지도 관련 설정을 하는 메서드를 구현해준다.

```kt
    // 구글 맵에 대한 설정을 하는 변수
    fun settingGoogleMap(){
        // MapFragment를 가져온다.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        // 지도 사용 준비가 완료되면 동작하는 리스너를 설정해준다.
        mapFragment.getMapAsync {
            // 구글 맵 객체를 변수에 담아준다.
            mainGoogleMap = it
            // 위치 정보를 관리하는 객체를 담아준다.
            locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            // 위치 측정이 완료되면 동작할 리스너 객체를 생성한다.
            myLocationListener = MyLocationListener()

            // 사전에 저장되어 있는 위치 정보가 있다면 그것을 가져와 사용한다.
            val chk1 = ActivityCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION)
            val chk2 = ActivityCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION)

            if(chk1 == PackageManager.PERMISSION_DENIED || chk2 == PackageManager.PERMISSION_DENIED){
                // 권한 확인을 위한 Activity를 실행한다.
                permissionCheckLauncher.launch(permissionList)
                return@getMapAsync
            }

            val gpsSavedLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val networkSavedLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            val passiveSavedLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)

            if(gpsSavedLocation != null){
                setMyLocation(gpsSavedLocation)
            } else if(networkSavedLocation != null){
                setMyLocation(networkSavedLocation)
            } else if(passiveSavedLocation != null){
                setMyLocation(passiveSavedLocation)
            }

            // 현재 위치 측정을 시작한다.
            getMyLocation()
        }
    }
```

### 위치 측정 리스너 부분도 수정한다.

[MainActivity.kt - MyLocationListener]
```kt
            // Log.d("test100", "위도 : ${location.latitude}, 경도 : ${location.longitude}")
            // 측정된 새로운 위치를 전달한다.
            setMyLocation(location)
```

### 구글 지도 설정 메서드를 호출해준다.

[MainActivity.kt - onCreate()]
```kt
        // 구글 지도를 설정하는 함수 호출
        settingGoogleMap()
```

### 현재 위치를 지도에 표시하는 메서드를 구현해준다.

```kt
    // 지도에 현재 위치를 표시하는 메서드
    fun setMyLocation(location: Location){
        // 현재 위치 측정을 중단한다.
        locationManager.removeUpdates(myLocationListener)

        // 위도와 경도를 관리하는 객체를 생성한다.
        // 첫번째 : 위도
        // 두번째 : 경도
        val loc1 = LatLng(location.latitude, location.longitude)

        // 지도를 업데이트할 수 있는 객체를 생성한다.
        // 현재의 줌 배율 상에서 해당 위치로 이동시킨다.
        // val cameraUpdate = CameraUpdateFactory.newLatLng(loc1)
        // 이동시키고자 하는 위치와 배율을 설정하여 이동시킨다.
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(loc1, 15.0f)
        // 지도를 업데이트 한다(위치 이동)
        // 애니메이션 없이 해당 위치가 나타난다.
        // mainGoogleMap.moveCamera(cameraUpdate)
        // 애니메이션과 함께 해당 위치가 나타난다.
        mainGoogleMap.animateCamera(cameraUpdate)

        // 지도에 표시할 마커를 생성한다.
        val markerOptions = MarkerOptions()
        markerOptions.position(loc1)

        // 기존에 찍은 사용자 위치 마커가 있다면 제거한다.
        if(myMarker != null){
            myMarker?.remove()
            myMarker = null
        }

        // 마커의 이미지를 설정해준다.
        val markerBitmap = BitmapDescriptorFactory.fromResource(android.R.drawable.ic_menu_agenda)
        markerOptions.icon(markerBitmap)

        // 지도에 마커를 꽂아준다.
        myMarker = mainGoogleMap.addMarker(markerOptions)
    }
```

---

# 06_구글 지도 옵션 설정

### 구글 지도 관련 옵션을 설정해준다.

[MainActivity.kt - settingGoogleMap()]

```kt
            // 줌 배율을 설정할 수 있는 버튼을 표시한다.
            mainGoogleMap.uiSettings.isZoomControlsEnabled = true
            // 현재 위치를 표시한다(현재 위치를 위한 마커를 별도로 사용하지 않아야 한다)
            // mainGoogleMap.isMyLocationEnabled = true
            // 위의 옵션을 사용할 면 좌측상단에 현재 위치로 이동하는 버튼이 생긴다.
            // 이를 숨긴다.
            // mainGoogleMap.uiSettings.isMyLocationButtonEnabled = false
            // 지도의 모양
            // mainGoogleMap.mapType = GoogleMap.MAP_TYPE_NONE
            mainGoogleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            // mainGoogleMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
            // mainGoogleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            // mainGoogleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
```

---

# 07_주변 정보 가져오기

### 다이얼로그로 표시할 데이터 배열을 작성해준다.

[MainActivity.kt]
```kt
    // 주변 정보 목록
    val dialogData = arrayOf(
        "accounting", "airport", "amusement_park",
        "aquarium", "art_gallery", "atm", "bakery",
        "bank", "bar", "beauty_salon", "bicycle_store",
        "book_store", "bowling_alley", "bus_station",
        "cafe", "campground", "car_dealer", "car_rental",
        "car_repair", "car_wash", "casino", "cemetery",
        "church", "city_hall", "clothing_store", "convenience_store",
        "courthouse", "dentist", "department_store", "doctor",
        "drugstore", "electrician", "electronics_store", "embassy",
        "fire_station", "florist", "funeral_home", "furniture_store",
        "gas_station", "gym", "hair_care", "hardware_store", "hindu_temple",
        "home_goods_store", "hospital", "insurance_agency",
        "jewelry_store", "laundry", "lawyer", "library", "light_rail_station",
        "liquor_store", "local_government_office", "locksmith", "lodging",
        "meal_delivery", "meal_takeaway", "mosque", "movie_rental", "movie_theater",
        "moving_company", "museum", "night_club", "painter", "park", "parking",
        "pet_store", "pharmacy", "physiotherapist", "plumber", "police", "post_office",
        "primary_school", "real_estate_agency", "restaurant", "roofing_contractor",
        "rv_park", "school", "secondary_school", "shoe_store", "shopping_mall",
        "spa", "stadium", "storage", "store", "subway_station", "supermarket",
        "synagogue", "taxi_stand", "tourist_attraction", "train_station",
        "transit_station", "travel_agency", "university", "eterinary_care","zoo"
    )

    val dialogDataKorean = arrayOf(
        "회계", "공항", "놀이공원",
        "수족관", "미술관", "ATM", "빵집",
        "은행", "바", "미용실", "자전거 가게",
        "서점", "볼링장", "버스 정류장",
        "카페", "캠핑장", "자동차 딜러", "렌터카",
        "자동차 수리", "세차장", "카지노", "묘지",
        "교회", "시청", "의류 매장", "편의점",
        "법원", "치과", "백화점", "의사",
        "약국", "전기 기사", "전자 제품 매장", "대사관",
        "소방서", "꽃집", "장례식장", "가구 매장",
        "주유소", "체육관", "헤어 케어", "철물점", "힌두교 사원",
        "홈 용품 매장", "병원", "보험사",
        "보석 가게", "세탁소", "변호사", "도서관", "경전철역",
        "주류 판매점", "지방 정부 사무소", "열쇠 수리점", "숙박 시설",
        "음식 배달", "테이크아웃 음식점", "모스크", "영화 대여점", "영화관",
        "이사 서비스", "박물관", "클럽", "화가", "공원", "주차장",
        "애완동물 가게", "약국", "물리치료사", "배관공", "경찰서", "우체국",
        "초등학교", "부동산 중개소", "음식점", "지붕 시공 업체",
        "RV 공원", "학교", "중학교", "신발 가게", "쇼핑몰",
        "스파", "경기장", "창고", "상점", "지하철역", "슈퍼마켓",
        "유대교 회당", "택시 승강장", "관광명소", "기차역",
        "환승역", "여행사", "대학교", "동물병원", "동물원"
    )
```

### 다이얼로그를 띄우는 메서드를 구현한다.

```kt
    // 주변 정보 카테고리 항목을 고르기 위한 다이얼로그를 띄우는 메서드
    fun showPlaceDialog(){
        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(this@MainActivity)
        materialAlertDialogBuilder.setTitle("장소 종류 선택")
        materialAlertDialogBuilder.setNegativeButton("취소", null)
        // 리스트 항목을 설정해준다.
        materialAlertDialogBuilder.setItems(dialogDataKorean, null)
        materialAlertDialogBuilder.show()
    }
```



### 메서드를 호출한다.

[MainActivity.kt - settingToolbar()]
```kt
                    // 주변 정보
                    R.id.main_menu_item_place -> {
                        // 주변 정보 카테고리를 선택하는 다이얼로그를 띄우는 메서드를 호출한다.
                        showPlaceDialog()
                    }
                }
```

### 주변 정보를 가져올 수 있는 API를 확인한다.
- https://developers.google.com/maps/documentation/places/web-service/search-nearby?hl=ko

```text
요청할 페이지의 주소
https://maps.googleapis.com/maps/api/place/nearbysearch/json

요청시 전달해야 하는 데이터
location : 위도,경도
radius : 반경(50000)
language : ko
type : 장소유형
key : API KEY

응답으로 전달받는 데이터의 양식(xml or json)
json
```

### API Key를 발급받는다
- 구글 클라우드 콘솔에 들어간다.
- https://console.cloud.google.com/

- 좌측 상단의 메뉴버튼을 누른다
- 좌측 메뉴에서 "API 및 서비스" > "라이브러리"를 클릭한다

- API 라이브러리 화면에서 "Places API"를 눌러준다.
- Places API 화면에서 "사용" 버튼을 눌러준다.

- 좌측 상단 메뉴 버튼을 누른다.
- 좌측 메뉴에서 "API 및 서비스" > "사용자 인증 정보"를 클릭한다.
- 상단에 있는 "+ 사용자 인증 정보" 를 눌러준다.
- 하단에 나오는 목록 중에 "API 키"를 눌러준다.
- 잠시 후 API 키가 생성되어 표시된다. 이를 복사해둔다.

- postman 을 통해 요청 테스트를 해본다.

### retrofit 사용을 위한 라이브러리 설정

[build.gradle.kts]

```kt
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
```

### 권한을 추가한다

[AndroidManifest.xml]

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

### 응답 결과를 담을 클래스들을 정의한다

[PlaceData.kt]
```kt
data class RootClass(
    @SerializedName("results")
    var results:List<ResultsClass>,

    @SerializedName("status")
    var status:String
)

data class ResultsClass(
    @SerializedName("geometry")
    var geometry:GeometryClass,

    @SerializedName("icon")
    var icon:String,

    @SerializedName("name")
    var name:String,

    @SerializedName("vicinity")
    var vicinity:String
)

data class GeometryClass(
    @SerializedName("location")
    var location:LocationClass
)

data class LocationClass(
    @SerializedName("lat")
    var lat:Double,

    @SerializedName("lng")
    var lng:Double
)
```

### 요청과 응답 정보를 담을 인터페이스를 구현한다.

[RetrofitRequestInterface.kt]
```kt
interface RetrofitRequestInterface {

    @GET("maps/api/place/nearbysearch/json")
    fun requestPlaceApi(
        @Query("location") location:String,
        @Query("radius") radius:String,
        @Query("language") language:String,
        @Query("type") type:String,
        @Query("key") key:String
    ) : Call<RootClass>
}
```

### 사용자의 현재위치를 담을 변수를 선언한다.

[MainActivity.kt]

```kt
    // 사용자의 현재 위치를 담을 변수
    var userLocation:Location? = null
```

### 사용자의 현재 위치를 변수에 담아준다.

[MainActivity.kt - setMyLocation()]
```kt
        // 사용자의 현재 위치를 변수에 담아준다.
        userLocation = location
```

### 요청하는 메서드를 구현한다.

```kt
    // 주변 정보를 가져오는 메서드
    fun getPlaceData(position:Int){
        if(userLocation != null){
            CoroutineScope(Dispatchers.Main).launch {
                val work1 = async(Dispatchers.IO){
                    // Retrofit 객체 생성
                    val builder = Retrofit.Builder()
                    builder.baseUrl("https://maps.googleapis.com/")
                    builder.addConverterFactory(GsonConverterFactory.create())
                    val retrofit = builder.build()
    
                    // 요청한다
                    val service = retrofit.create(RetrofitRequestInterface::class.java)
    
                    val location = "${userLocation?.latitude},${userLocation?.longitude}"
                    val radius = "1000"
                    val language = "ko"
                    val type = dialogData[position]
                    val key = "발급 받으신 키를 넣어주세요"
                    service.requestPlaceApi(location, radius, language, type, key).execute()
                }
    
                val resultRoot = work1.await()
                Log.d("test100", resultRoot.body()!!.toString())
            }
        }
    }
```

### 메서드를 호출한다.

[MainActivity.kt - showPlaceDialog()]
```kt
            // 데이터를 가져오는 메서드를 호출한다.
            getPlaceData(i)
```

### Marker 를 담을 리스트를 정의해준다.

[MainActivity.kt]
```kt
    // 주변 정보 마커 객체들을 담을 리스트
    val markerList = mutableListOf<Marker>()
```

### 지도에 마커를 표시해주는 메서드를 구현한다.

```kt
    // 마커를 지도에 찍어주는 메서드
    fun settingMaker(rootClass: RootClass){
        // 지도에 표시되어 있는 마커들을 제거한다.
        markerList.forEach {
            it.remove()
        }
        markerList.clear()

        // 마커에 표시할 용도로 사용할 이미지
        var markerBitmap:Bitmap? = null

        // 데이터를 가져오는데 성공했을 경우에만 사용한다.
        if(rootClass.status == "OK"){
            CoroutineScope(Dispatchers.Main).launch {
                // 마커로 사용할 이미지를 받아오지 않았다면
                if(markerBitmap == null){
                    val work1 = async(Dispatchers.IO){
                        // 이미지의 주소
                        val url = URL(rootClass.results[0].icon)
                        // 스트림을 추출한다.
                        val httpURLConnection = url.openConnection()
                        val inputStream = httpURLConnection.getInputStream()
                        // 이미지 데이터를 받아온다.
                        BitmapFactory.decodeStream(inputStream)
                    }
                    markerBitmap = work1.await()
                }

                // 가져온 장소의 수 만큼 반복한다.
                rootClass.results.forEach {
                    // 위도를 가져온다
                    val lat = it.geometry.location.lat
                    // 경도를 가져온다
                    val lng = it.geometry.location.lng
                    // 이름
                    val placeName = it.name
                    // 대략적인 주소
                    val vicinity = it.vicinity

                    // 마커를 찍어준다
                    val markerOptions = MarkerOptions()
                    val markerLocation = LatLng(lat, lng)
                    markerOptions.position(markerLocation)
                    // 제목
                    markerOptions.title(placeName)
                    // 스니펫
                    markerOptions.snippet(vicinity)
                    // 마커의 이미지
                    val placeBitmap = BitmapDescriptorFactory.fromBitmap(markerBitmap!!)
                    markerOptions.icon(placeBitmap)


                    val marker = mainGoogleMap.addMarker(markerOptions)
                    markerList.add(marker!!)
                }
            }
        }
    }
```

### 메서드를 호출한다.

[MainActivity.kt - getPlaceData()]
```kt
                // 마커를 찍어준다.
                settingMaker(resultRoot.body()!!)
```