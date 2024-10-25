package com.example.a013ex_animalmanager

import android.inputmethodservice.InputMethodService
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a013ex_animalmanager.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // 동물들의 정보를 담을 list
    val animalList = mutableListOf<AnimalClass>()

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
            // 첫 번째 입력 요소에 포커스를 준다.
            showKeyboard(textFieldName.editText)

            // 등록 완료 버튼
            buttonSave.setOnClickListener {
                // 등록 처리를 하는 메서드를 호출한다.
                saveData()
                resetInput()
            }
            // setOnEditorActionLister :  키보드의 "완료", "엔터" 또는 "검색" 버튼과 같은 액션 키를 눌렀을 때 특정 작업을 수행할 수 있도록 이벤트를 처리합니다.
            // 마지막 입력 요소에 대한 엔터키 이벤트
            textFieldAge.editText?.setOnEditorActionListener { v, actionId, event ->
                // 등록 처리를 하는 메서드를 호출한다.
                saveData()
                resetInput()
                true // true를 반환: 이벤트가 처리되었음을 시스템에 알립니다. 이 경우, 시스템은 해당 이벤트를 더 이상 처리하지 않으며, 다른 기본 동작(ex. 키보드 닫기 등)이 발생하지 않습니다.

            }

            // 출력 버튼을 눌렀을 때
            buttonShow.setOnClickListener {
                // 동물 정보를 출력하는 메서드를 호출한다.
                showAnimalInfo()
            }
        }
    }

    // 지정된 View에 focus를 주고 키보드를 올려준다.
    fun showKeyboard(view:View?){
        // 지정된 View에 포커스를 준다.
        view?.requestFocus()

        thread {
            Thread.sleep(1000)
            // 포커스를 가지고 있는 View에 대해 키보드를 올려준다.
            // 입력을 관리하는 관리자를 가져온다.
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            // 키보드를 올려준다.
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    // 등록완료 처리 메서드
    fun saveData(){
        activityMainBinding.apply {
            // 각 입력요소에 입력된 내용을 가져온다.
            val nameData = textFieldName.editText?.text.toString()
            val typeData = textFieldType.editText?.text.toString()
            val ageData = textFieldAge.editText?.text.toString().toInt()
            // 동물 객체를 생성한다.
            val animalClass = AnimalClass(nameData, typeData, ageData)
            // 리스트에 담는다
            animalList.add(animalClass)
        }
    }

    // 입력 요소 초기화
    fun resetInput(){
        activityMainBinding.apply {
            // 각 입력 요소를 비워준다.
            textFieldName.editText?.setText("")
            textFieldType.editText?.setText("")
            textFieldAge.editText?.setText("")
            // 첫 번째 입력 요소에 포커스를 준다.
            textFieldName.editText?.requestFocus()
        }
    }

    // 동물 정보 출력
    fun showAnimalInfo(){
        // 동물의 수 만큼 반복한다.
        animalList.forEach {
            // 출력한다.
            it.showAnimalInfo()
        }
    }
}