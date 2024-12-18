package com.lion.openapi

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.lion.openapi.databinding.ActivityResultBinding
import com.lion.openapi.repository.ItemsApiClass
import com.lion.openapi.view_model.ResultViewModel

class ResultActivity : AppCompatActivity() {
    lateinit var activityResultBinding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityResultBinding = DataBindingUtil.setContentView(this@ResultActivity, R.layout.activity_result)
        activityResultBinding.resultViewModel = ResultViewModel()
        activityResultBinding.lifecycleOwner = this@ResultActivity

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        showStationData()

    }
    // 데이터를 추출해 출력하는 메서드
    fun showStationData(){
        val itemsApiClass = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent.getParcelableExtra("ItemsApiClass", ItemsApiClass::class.java)!!
        } else {
            intent.getParcelableExtra("ItemApiClass")
        }

        activityResultBinding.resultViewModel?.textViewResultText?.value = """
            아황산가스 지수 : ${itemsApiClass?.so2Grade} 
            일산화탄소 플래그 : ${itemsApiClass?.coFlag}
            측정소명 : ${itemsApiClass?.stationName}
        """.trimIndent()
    }

}