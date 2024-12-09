package com.lion.a07_studentmanager.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lion.a07_studentmanager.MainActivity
import com.lion.a07_studentmanager.R
import com.lion.a07_studentmanager.databinding.FragmentBottomSheetPlannerListModifyBinding
import com.lion.a07_studentmanager.repository.PlannerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class BottomSheetPlannerListModify(val plannerFragment: PlannerFragment, val plannerIdx:Int) : BottomSheetDialogFragment() {

    // 데이터를 담을 변수들
    var plannerYear = 0
    var plannerMonth = 0
    var plannerDay = 0
    var plannerContent = ""

    lateinit var bottomSheetPlannerListModifyBinding: FragmentBottomSheetPlannerListModifyBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        bottomSheetPlannerListModifyBinding = FragmentBottomSheetPlannerListModifyBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        // 데이터를 가져와 입력 요소에 설정해주는 메서드를 호출한다.
        gettingPlannerData()

        return bottomSheetPlannerListModifyBinding.root
    }

    // 데이터를 가져와 입력 요소에 설정해주는 메서드
    fun gettingPlannerData(){
        CoroutineScope(Dispatchers.Main).launch {
            val work1 = async(Dispatchers.IO){
                PlannerRepository.selectPlannerDataOneByPlannerIdx(mainActivity, plannerIdx)
            }
            val plannerVO = work1.await()

            plannerYear = plannerVO.plannerYear
            plannerMonth = plannerVO.plannerMonth
            plannerDay = plannerVO.plannerDay
            plannerContent = plannerVO.plannerContent

            bottomSheetPlannerListModifyBinding.apply {
                textFieldBottomSheetPlannerModifyContent.editText?.setText(plannerContent)
                buttonBottomSheetPlannerModifyDate.text = "${plannerYear}-${plannerMonth}-${plannerDay}"
            }
        }
    }
}