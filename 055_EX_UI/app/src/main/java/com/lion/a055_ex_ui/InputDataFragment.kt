package com.lion.a055_ex_ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.lion.a055_ex_ui.databinding.FragmentInputDataBinding
import com.lion.a055_ex_ui.databinding.FragmentRecyclerBinding

class InputDataFragment : Fragment() {
    lateinit var fragmentInputDataBinding: FragmentInputDataBinding
    lateinit var fragmentRecyclerBinding : FragmentRecyclerBinding
    lateinit var mainActivity: MainActivity


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentRecyclerBinding = FragmentRecyclerBinding.inflate(inflater)
        fragmentInputDataBinding = FragmentInputDataBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        settingToolbar()

        settingToolbarMenu()

        return fragmentInputDataBinding.root
    }

    // 툴바를 구성하는 메서드
    fun settingToolbar() {
        fragmentInputDataBinding.materialToolbarInputData.apply {
            // 타이틀
            title = " inputData"

            // 좌측 네비게이션 버튼을 누르면 NavigationView가 나타나게 한다.
            setNavigationOnClickListener {
                mainActivity.removeFragment(FragmentName.INPUT_DATA_FRAGMENT)
            }

        }
    }

    fun settingToolbarMenu() {
        fragmentInputDataBinding.materialToolbarInputData.apply {
            // 메뉴를 눌렀을 때 동작하는 리스너
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.inputData_menu_item_add -> {
                        inputDone()
                        // 전달할 데이터 리스트
                        val dataBundle = Bundle()

                        //  Activity가 아니라 Fragment 내에서 작성된 경우, supportFragmentManager 대신 parentFragmentManager
                        parentFragmentManager.commit {
                            // 전체 리스트 객체 프레그먼트에 전달
                            dataBundle.putSerializable("dataModelList", ArrayList(mainActivity.animalList))
                            // 데이터 입력 후 상태 변경-> 전체 보기 화면으로 전환
                            mainActivity.replaceFragment(FragmentName.RECYCLER_FRAGMENT, true, dataBundle)
                        }
                        true
                    }
                    else -> {
                        true
                    }
                }
            }
        }
    }

    fun inputDone() {
        fragmentInputDataBinding.apply {
            var type ="널"
            when (radioGroup.checkedRadioButtonId) {
                R.id.radioButtonDogInputData -> {
                    type = "Dog"
                    val name = editTextNameInputData.text.toString()
                    val age = editTextAgeInputData.text.toString().toInt()
                    val model = DataModel(type, name, age)
                    mainActivity.dogList.add(model)
                    mainActivity.animalList.add(model)
                }
                R.id.radioButtonCatInputData -> {
                    type = "Cat"
                    val name = editTextNameInputData.text.toString()
                    val age = editTextAgeInputData.text.toString().toInt()
                    val model = DataModel(type, name, age)
                    mainActivity.catList.add(model)
                    mainActivity.animalList.add(model)
                }
                R.id.radioButtonParrotInputData -> {
                    type = "Parrot"
                    val name = editTextNameInputData.text.toString()
                    val age = editTextAgeInputData.text.toString().toInt()
                    val model = DataModel(type, name, age)
                    mainActivity.parrotList.add(model)
                    mainActivity.animalList.add(model)
                }

            }
            Log.d("test100","${mainActivity.animalList}")

            fragmentRecyclerBinding.recyclerViewRecycler.adapter?.notifyDataSetChanged()

            mainActivity.removeFragment(FragmentName.INPUT_DATA_FRAGMENT)


        }
    }

}