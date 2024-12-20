package com.lion.a07_studentmanager.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.lion.a07_studentmanager.MainActivity
import com.lion.a07_studentmanager.R
import com.lion.a07_studentmanager.databinding.FragmentPlannerBinding

class PlannerFragment(val mainFragment: MainFragment) : Fragment() {

    lateinit var fragmentPlannerBinding: FragmentPlannerBinding
    lateinit var mainActivity: MainActivity

    // Planner Mode 값을 담을 변수
    var plannerMode = PlannerMode.PLANNER_MODE_LIST

    // 각 프래그먼트를 담을 변수
    lateinit var plannerListFragment:PlannerListFragment
    lateinit var plannerCalendarFragment: PlannerCalendarFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentPlannerBinding = FragmentPlannerBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        settingToolbar()

        return fragmentPlannerBinding.root
    }

    // 툴바를 구성하는 메서드
    fun settingToolbar(){
        fragmentPlannerBinding.apply {
            toolbarPlanner.title = "학사 일정 관리"

            toolbarPlanner.setNavigationIcon(R.drawable.menu_24px)
            toolbarPlanner.setNavigationOnClickListener {
                mainFragment.fragmentMainBinding.drawerLayoutMain.open()
            }

            toolbarPlanner.inflateMenu(R.menu.planner_main_menu)
            settingMenuItemVisible()
            changePlannerSubFragment()

            toolbarPlanner.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.planner_menu_add -> {
                        // 학사 일정 BottomSheet을 띄운다.
                        showPlannerBottomSheetAdd()
                    }
                    R.id.planner_menu_list -> {
                        plannerMode = PlannerMode.PLANNER_MODE_LIST
                        settingMenuItemVisible()
                        changePlannerSubFragment()
                    }
                    R.id.planner_menu_calendar -> {
                        plannerMode = PlannerMode.PLANNER_MODE_CALENDAR
                        settingMenuItemVisible()
                        changePlannerSubFragment()
                    }
                }

                true
            }
        }
    }

    // 학사 일정 모드에 따라 메뉴 노출을 설정하는 메서드
    fun settingMenuItemVisible(){
        fragmentPlannerBinding.apply {
            // 학사 일정 모드에 따라 메뉴를 숨긴다.
            val item1 = toolbarPlanner.menu.findItem(R.id.planner_menu_calendar)
            val item2 = toolbarPlanner.menu.findItem(R.id.planner_menu_list)
            when(plannerMode){
                PlannerMode.PLANNER_MODE_LIST -> {
                    item1.isVisible = true
                    item2.isVisible = false
                }
                PlannerMode.PLANNER_MODE_CALENDAR -> {
                    item1.isVisible = false
                    item2.isVisible = true
                }
            }
        }
    }

    fun changePlannerSubFragment(){
        when(plannerMode){
            PlannerMode.PLANNER_MODE_LIST -> {
                mainActivity.supportFragmentManager.commit {
                    plannerListFragment = PlannerListFragment(mainFragment, this@PlannerFragment)
                    replace(R.id.fragmentContainerViewPlanner, plannerListFragment)
                }
            }
            PlannerMode.PLANNER_MODE_CALENDAR -> {
                mainActivity.supportFragmentManager.commit {
                    plannerCalendarFragment = PlannerCalendarFragment(mainFragment)
                    replace(R.id.fragmentContainerViewPlanner, plannerCalendarFragment)
                }
            }
        }
    }

    // 학사 일정 BottomSheet을 띄운다.
    fun showPlannerBottomSheetAdd(){
        val bottomSheetPlannerAddFragment = BottomSheetPlannerAddFragment(this)
        bottomSheetPlannerAddFragment.show(mainActivity.supportFragmentManager, "list add")
    }

    // 데이터를 읽어와 RecyclerView나 Calendar를 갱신하는 메서드
    fun refreshPlannerInfo(){
        // 모드로 분기한다.
        when(plannerMode){
            PlannerMode.PLANNER_MODE_LIST -> {
                // 데이터를 읽어와 RecyclerView를 갱신다
                plannerListFragment.gettingPlannerData()
            }
            PlannerMode.PLANNER_MODE_CALENDAR -> {

            }
        }
    }
}

// 학사 일정 모드
enum class PlannerMode(val number:Int, var str:String){
    PLANNER_MODE_LIST(1, "PlannerModeList"),
    PLANNER_MODE_CALENDAR(2, "PlannerModelCalendar")
}