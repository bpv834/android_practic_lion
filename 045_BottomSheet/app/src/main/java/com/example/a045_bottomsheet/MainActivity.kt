package com.example.a045_bottomsheet

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a045_bottomsheet.databinding.ActivityMainBinding
import com.example.a045_bottomsheet.databinding.BottomSheet1Binding
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

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
        activityMainBinding.apply {
            button.setOnClickListener {
                // BottomSheetDialog 사용
                val bottomSheet1Binding = BottomSheet1Binding.inflate(layoutInflater)
                //  BottomSheetDialog는 Android에서 사용하는 다이얼로그 스타일의 바텀시트입니다.
                //  이 다이얼로그는 화면의 하단에서 위로 올라오며, 사용자가 스와이프하여 내려 보낼 수도 있습니다.
                val bottomSheetDialog = BottomSheetDialog(this@MainActivity)
                // bottomSheetDialog 로 보여줄 View를 지정한다.
                bottomSheetDialog.setContentView(bottomSheet1Binding.root)

                bottomSheet1Binding.buttonBottomSheet1.setOnClickListener {
                    textView.text = "Bottom Sheet의 버튼을 눌렀습니다."
                    //닫는다
                    bottomSheetDialog.dismiss()
                }


                // BottomSheetDialog를 띄운다.
                bottomSheetDialog.show()
            }

            button2.setOnClickListener {
                // BottomSheetDialogFragment를 띄운다.
                val bottomSheetFragment = BottomSheetFragment()

                // show() 메서드는 바텀시트를 화면에 띄웁니다

                // supportFragmentManager: 현재 Activity에서 Fragment를 관리하기 위해 사용되는 FragmentManager입니다.
                // 이를 통해 바텀시트를 Activity에 추가하거나 삭제할 수 있습니다.

                //"BottomSheepFragment": 이 문자열은 바텀시트 Fragment의 태그입니다.
                // 필요할 때 해당 Fragment를 참조하거나 검색할 때 사용할 수 있습니다.
                bottomSheetFragment.show(supportFragmentManager, "BottomSheepFragment")
            }
        }
    }
}