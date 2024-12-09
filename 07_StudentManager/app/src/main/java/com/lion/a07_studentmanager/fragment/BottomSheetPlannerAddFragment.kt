package com.lion.a07_studentmanager.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.lion.a07_studentmanager.MainActivity
import com.lion.a07_studentmanager.R
import com.lion.a07_studentmanager.database.PlannerVO
import com.lion.a07_studentmanager.databinding.FragmentBottomSheetPlannerAddBinding
import com.lion.a07_studentmanager.repository.PlannerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.Calendar


class BottomSheetPlannerAddFragment(val plannerFragment: PlannerFragment) : BottomSheetDialogFragment() {

    lateinit var fragmentBottomSheetPlannerAddBinding: FragmentBottomSheetPlannerAddBinding
    lateinit var mainActivity: MainActivity

    // 날짜를 담을 변수
    var plannerYear = 0
    var plannerMonth = 0
    var plannerDay = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentBottomSheetPlannerAddBinding = FragmentBottomSheetPlannerAddBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        // 날짜를 오늘 날짜로 설정해준다.
        val calendar = Calendar.getInstance()
        plannerYear = calendar.get(Calendar.YEAR)
        plannerMonth = calendar.get(Calendar.MONTH) + 1
        plannerDay = calendar.get(Calendar.DAY_OF_MONTH)

        // TextField 설정메서드를 호출한다.
        settingTextField()
        // Button 설정
        settingButton()

        return fragmentBottomSheetPlannerAddBinding.root
    }

    // TextField 설정
    fun settingTextField(){
        fragmentBottomSheetPlannerAddBinding.apply {
            // 포커스를 준다.
            mainActivity.showSoftInput(textFieldBottomSheetPlannerAddContent.editText!!)
        }
    }

    // Button 설정
    fun settingButton(){
        fragmentBottomSheetPlannerAddBinding.apply {
            buttonBottomSheetPlannerAddDate.text = "${plannerYear}-${plannerMonth}-${plannerDay}"
            buttonBottomSheetPlannerAddDate.setOnClickListener {
                // DatePicker
                val datePickerBuilder = MaterialDatePicker.Builder.datePicker()
                datePickerBuilder.setTitleText("날짜 선택")

                // 특정 날짜를 지정하고 싶다면..
                val calendar2 = Calendar.getInstance()
                datePickerBuilder.setSelection(calendar2.timeInMillis)

                val datePicker = datePickerBuilder.build()

                datePicker.addOnPositiveButtonClickListener {
                    // 사용자가 선택한 날짜 값을 가져온다.
                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = it
                    plannerYear = calendar.get(Calendar.YEAR)
                    plannerMonth = calendar.get(Calendar.MONTH) + 1
                    plannerDay = calendar.get(Calendar.DAY_OF_MONTH)

                    buttonBottomSheetPlannerAddDate.text = "${plannerYear}-${plannerMonth}-${plannerDay}"
                }

                datePicker.show(mainActivity.supportFragmentManager, "date picker")
            }


            buttonBottomSheetPlannerAddSubmit.setOnClickListener {
                // 데이터를 담는다.
                val plannerContent = textFieldBottomSheetPlannerAddContent.editText?.text.toString()
                val plannerVO = PlannerVO(
                    plannerContent = plannerContent,
                    plannerYear = plannerYear,
                    plannerMonth = plannerMonth,
                    plannerDay = plannerDay
                )
                CoroutineScope(Dispatchers.Main).launch {
                    val work1 = async(Dispatchers.IO){
                        // 데이터를 저장한다.
                        PlannerRepository.insertPlannerData(mainActivity, plannerVO)
                    }
                    work1.join()

                    // RecyclerView나 Calendar를 갱신다.
                    plannerFragment.refreshPlannerInfo()

                    // BottomSheet를 내려준다
                    dismiss()
                }
            }
        }
    }
}