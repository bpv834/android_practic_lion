package com.lion.a054ex_ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.lion.a054ex_ui.MainActivity
import com.lion.a054ex_ui.R
import com.lion.a054ex_ui.databinding.FragmentTabBinding

class TabFragment : Fragment() {

    lateinit var fragmentTabBinding: FragmentTabBinding
    lateinit var mainActivity: MainActivity

    // 보여줄 이미지 목록
    val imageRes = arrayOf(
        R.drawable.imgflag1,
        R.drawable.imgflag2,
        R.drawable.imgflag3,
        R.drawable.imgflag4,
        R.drawable.imgflag5,
        R.drawable.imgflag6,
        R.drawable.imgflag7,
        R.drawable.imgflag8
    )
    // 문자열 목록
    val textData = arrayOf(
        "토고", "프랑스", "스위스", "스페인", "일본", "독일", "브라질", "대한민국"
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentTabBinding = FragmentTabBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        // 툴바를 셋팅하는 메서드를 호출해준다.
        settingToolbar()

        // TabLayout과 ViewPager를 구성하는 메서드를 호출해준다.
        settingTabLayout()

        return fragmentTabBinding.root
    }

    // 툴바를 셋팅하는 메서드
    fun settingToolbar(){
        fragmentTabBinding.materialToolbarTab.apply {
            // 타이틀
            title = "Tab"
            // 좌측 네비게이션 버튼
            setNavigationIcon(R.drawable.menu_24px)
            // 좌측 네비게이션 버튼을 누르면 NavigationView가 나타나게 한다.
            setNavigationOnClickListener {
                mainActivity.activityMainBinding.drawerlayoutMain.open()
            }
        }
    }

    // 탭을 구성하는 메서드
    fun settingTabLayout(){
        fragmentTabBinding.apply {
            viewPagerTab.adapter = ViewPagerAdapter(mainActivity.supportFragmentManager, lifecycle)
            // TabLayout과 ViewPager2가 상호 작용을 할 수 있도록 연동시켜준다.
            val tabLayoutMediator = TabLayoutMediator(tabLayoutTab, viewPagerTab) { tab, position ->
                tab.text = "탭 ${position + 1}"
            }
            tabLayoutMediator.attach()
        }
    }

    // ViewPager2의 어뎁터
    inner class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle){

        override fun getItemCount(): Int {
            return imageRes.size
        }

        override fun createFragment(position: Int): Fragment {
            val newFragment = when(position){
                0 -> TabSubFragment(imageRes[position], textData[position])
                1 -> TabSubFragment(imageRes[position], textData[position])
                2 -> TabSubFragment(imageRes[position], textData[position])
                3 -> TabSubFragment(imageRes[position], textData[position])
                4 -> TabSubFragment(imageRes[position], textData[position])
                5 -> TabSubFragment(imageRes[position], textData[position])
                6 -> TabSubFragment(imageRes[position], textData[position])
                else -> TabSubFragment(imageRes[position], textData[position])
            }
            return newFragment
        }
    }
}