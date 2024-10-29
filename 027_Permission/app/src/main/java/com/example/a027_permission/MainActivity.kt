package com.example.a027_permission
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a027_permission.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // 나는 권한 확인 요청만 할래~ :  requestPermissions(permissionList, 0)
    // 나는 권한 확인 요청한 후 사용자가 확인을 끝냈을 때 뭔가 자동으로 작업이 이루어지게 할래 : 런처사용

    // 사용하는 권한 목록
    // AndroidManifest.xml 에 등록한 모든 권한을 다 작성해주세요...
    // 여기에 작성한 권한 중 확인 받지 않아도 되는 권한이나 이미 허용한 권한은 무시된다.
    val permissionList = arrayOf(
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    // 권환 확인 창을 띄우는 런처
    // 만약 필요할 때 필요한 권한 몇 가지만 확인 받는 경우 사용
    lateinit var permissionLauncher:ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 권한 확인 창을 띄울 런처 생성
        val r1 = ActivityResultContracts.RequestMultiplePermissions()
        // 런처에 등록한 고차함수는 사용자가 권한확인 모두 끝내면 자동으로 호출
        // it 에는 권한의 이름과 허용 여부값이 담긴 맵이 들어온다.
        permissionLauncher = registerForActivityResult(r1){
            activityMainBinding.textView.text = ""

            // Manifest.permission.READ_CONTACTS
            if(it[Manifest.permission.READ_CONTACTS] == true){
                activityMainBinding.textView.append("연락처 읽기 권한이 허용되었습니다\n")
            } else {
                activityMainBinding.textView.append("연락처 읽기 권한이 거부되었습니다\n")
            }

            // Manifest.permission.WRITE_CONTACTS
            if(it[Manifest.permission.WRITE_CONTACTS] == true){
                activityMainBinding.textView.append("연락처 쓰기 권한이 허용되었습니다\n")
            } else {
                activityMainBinding.textView.append("연락처 쓰기 권한이 거부되었습니다\n")
            }
        }

        activityMainBinding.apply {

            button.setOnClickListener {
                // 거부되어 있는 권한들을 사용자에게 확인한다.
                requestPermissions(permissionList, 0)
            }

            button2.setOnClickListener {
                // 확인하고 싶은 권한을 지정하여 사용자에게 권한을 확인 받는다.
                // 확인하고 싶은 권한 목록
                val a1 = arrayOf(
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS
                )
                // 권한 확인을 위한 런처를 가동한다.
                permissionLauncher.launch(a1)
            }
        }
    }

    // requestPermission 호출 후 사용자가 권한 확인 작업 모두 끝내면 호출되는 메서드
    // permissions : 확인받은 권한 목록
    // grantResults : 해당 권한에 대한 허용 여부값
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        activityMainBinding.textView.text = ""
        // 권한 목록으로 반복한다.
        permissions.forEachIndexed { index, s ->
            if(grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                activityMainBinding.textView.append("${s}권한이 허용되었습니다\n")
            } else {
                activityMainBinding.textView.append("${s}권한이 거부되었습니다")
            }
        }
    }
}