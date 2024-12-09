package com.lion.a02_lbs

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lion.a02_lbs.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // 권한 확인을 위한 런처
    lateinit var permissionCheckLauncher:ActivityResultLauncher<Array<String>>

    // 위치 정보 관리 객체
    lateinit var locationManager: LocationManager
    // 위치 측정을 하면 반응하는 리스너
    lateinit var myLocationListener: MyLocationListener
    // 구글 지도 객체를 담을 변수를 선언한다.
    lateinit var mainGoogleMap:GoogleMap
    // 현재 사용자의 위치를 표시할 마커 객체
    var myMarker:Marker? = null
    // 사용자의 현재 위치를 담을 변수
    var userLocation:Location? = null
    // 주변 정보 마커 객체들을 담을 리스트
    val markerList = mutableListOf<Marker>()
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

    // 확인할 권한 목록
    val permissionList = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // 구글 지도에 표시되는 맵 이미지를 최신 버전 이미지로 사용하도록 설정한다.
        MapsInitializer.initialize(this@MainActivity, MapsInitializer.Renderer.LATEST, null)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 권한 확인을 위한 런처 생성 메서드 호출
        createPermissionCheckLauncher()
        // 상단 툴바를 구성하는 메서드를 호출한다.
        settingToolbar()
        // 구글 지도를 설정하는 함수 호출
        settingGoogleMap()

        // 권한 확인을 위한 런처 가동
        permissionCheckLauncher.launch(permissionList)
    }

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
                        // 현재 위치를 측정한다.
                        getMyLocation()
                    }
                    // 주변 정보
                    R.id.main_menu_item_place -> {
                        // 주변 정보 카테고리를 선택하는 다이얼로그를 띄우는 메서드를 호출한다.
                        showPlaceDialog()
                    }
                    // 마커 삭제
                    R.id.main_menu_item_clear -> {
                        // 지도에 표시되어 있는 마커들을 제거한다.
                        markerList.forEach {
                            it.remove()
                        }
                        markerList.clear()
                    }
                }
                true
            }
        }
    }

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

    // 위치 측정에 성공하면 동작하는 리스너
    inner class MyLocationListener : LocationListener{
        // 새로운 위치가 측정되면 호출되는 메서드
        // location : 새롭게 측정된 위도와 경도값이 담긴 객체
        override fun onLocationChanged(location: Location) {
            // Log.d("test100", "위도 : ${location.latitude}, 경도 : ${location.longitude}")
            // 측정된 새로운 위치를 전달한다.
            setMyLocation(location)
        }
    }

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

        // GPS 프로바이더 사용이 가능하다면
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0.0f, myLocationListener)
        }
        // Network 프로바이더 사용이 가능하다면
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0.0f, myLocationListener)
        }
    }

    // 구글 맵에 대한 설정을 하는 함수
    fun settingGoogleMap(){
        // MapFragment를 가져온다.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        // 지도 사용 준비가 완료되면 동작하는 리스너를 설정해준다.
        mapFragment.getMapAsync {
            // 구글 맵 객체를 변수에 담아준다.
            mainGoogleMap = it

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

    // 지도에 현재 위치를 표시하는 메서드
    fun setMyLocation(location: Location){
        // 사용자의 현재 위치를 변수에 담아준다.
        userLocation = location
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
        val markerBitmap = BitmapDescriptorFactory.fromResource(R.drawable.choi_ho_joon)
        markerOptions.icon(markerBitmap)

        // 지도에 마커를 꽂아준다.
        myMarker = mainGoogleMap.addMarker(markerOptions)
    }

    // 주변 정보 카테고리 항목을 고르기 위한 다이얼로그를 띄우는 메서드
    fun showPlaceDialog(){
        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(this@MainActivity)
        materialAlertDialogBuilder.setTitle("장소 종류 선택")
        materialAlertDialogBuilder.setNegativeButton("취소", null)
        // 리스트 항목을 설정해준다.
        materialAlertDialogBuilder.setItems(dialogDataKorean){ dialogInterface: DialogInterface, i: Int ->
            // 데이터를 가져오는 메서드를 호출한다.
            getPlaceData(i)
        }
        materialAlertDialogBuilder.show()
    }

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
                    val key = "AIzaSyDpNbYGD7i8v_qV-blFqREV7UawIqS8KdY"
                    service.requestPlaceApi(location, radius, language, type, key).execute()
                }

                val resultRoot = work1.await()
                // Log.d("test100", resultRoot.body()!!.toString())
                // 마커를 찍어준다.
                settingMaker(resultRoot.body()!!)
            }
        }
    }

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
}