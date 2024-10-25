package com.example.a013ex_2_carmanager

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a013ex_2_carmanager.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    //자동차 정보를 담을 리스트
    val carList = mutableListOf<CarClass>()

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
            //첫번째 입력 요소에 포커스를 준다.
            showKeyBoard(txtInputName.editText)

            //등록 완료 버튼
            btnSave.setOnClickListener {
                // 등록 처리 메서드 호출
                saveData()
                resetInput()
            }

            //마지막 입력 요소에 대한 엔터키 이벤트
            // setOnEditorActionLister :  키보드의 "완료", "엔터" 또는 "검색" 버튼과 같은 액션 키를 눌렀을 때 특정 작업을 수행할 수 있도록 이벤트를 처리합니다.

            txtInputWheelCnt.editText?.setOnEditorActionListener { v, actionId, event ->
                //등록 처리 메서드
                saveData()
                resetInput()
                true // true를 반환: 이벤트가 처리되었음을 시스템에 알립니다. 이 경우, 시스템은 해당 이벤트를 더 이상 처리하지 않으며, 다른 기본 동작(ex. 키보드 닫기 등)이 발생하지 않습니다.
            }

            btnShow.setOnClickListener {
                //차 정보 출력
                showCarInfo()
            }

        }
    }

    fun showKeyBoard(view: View?) {
        //지정된 View 에 포커스를 준다.
        view?.requestFocus()

        thread {
            Thread.sleep(1000)
            //포커스를 가지고 있는  View에 대해 키보드를 올려준다.
            //입력을 관리하는 관리자를 가져온다.
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            //키보드를 올려준다.
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    //  등록완료 처리 메서드
    fun saveData() {
        activityMainBinding.apply {
            //각 입력요소에 입력된 내용을 가져온다.
            val nameData = txtInputName.editText?.text.toString()
            val typeData = txtInputType.editText?.text.toString()
            val wheelCntData = txtInputWheelCnt.editText?.text.toString().toInt()
            // 자동차 객체 생성
            val carClass = CarClass(nameData, typeData, wheelCntData)
            //리스트에 담는다
            carList.add(carClass)
        }
    }

    // 입력 요소 초기화
    fun resetInput(){
        activityMainBinding.apply {
            // 각 입력 요소를 비워준다.
            txtInputName.editText?.setText("")
            txtInputType.editText?.setText("")
            txtInputWheelCnt.editText?.setText("")
            // 첫 번째 입력 요소에 포커스를 준다.
            txtInputName.editText?.requestFocus()
        }
    }

    //차 정보 출력
    fun showCarInfo() {
        //차 개수만큼 반복
        carList.forEach {
            //출력
            it.showCarInfo()
        }
    }
}