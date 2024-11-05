package com.example.a053_tablayout

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.a053_tablayout.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// TabLayout에 tabMode를 설정하면 탭이 많을 때 스크롤 여부를 설정할 수 있다.
// fixed : 탭이 스크롤 되지 않고 많으면 뭉게진다
// scrolled : 탭의 크기가 유지되고 좌우로 스크롤된다.
// auto : 탭이 많아서 화면을 벗어난다면 스크롤이 활성화된다(추천)


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
            // TabLayout 내에 있는 탭을 눌렀을 때
            // TabLayout.OnTabSelectedListener 리턴 타입은 void
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                // 탭을 눌렀을 때
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    // position : 사용자가 누른 탭의 idx
                    textView.text = "${tab?.position} 번째 탭을 눌렀다."
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

            })

            // ViewPager2의 어뎁터를 설정한다.
            pager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

            // TabLayout과 ViewPager2가 상호 작용을 할 수 있도록 연동시켜준다.
            val tabLayoutMediator = TabLayoutMediator(tabLayout,pager){
                tab, position ->
                //원래는 position별로 분기하여 처리해주세요
                // 각 탭에 보여줄 문자열을 새롭게 구성해줘야 한다.
                tab.text = "탭 ${position+1}"
                tab.setIcon(android.R.drawable.ic_menu_agenda)
            }
            tabLayoutMediator.attach()

        }
    }

    inner class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {
        // ViewPager2를 통해 보여줄 프래그먼트의 개수
        override fun getItemCount(): Int {
            return 6
        }
        // position번째에서 사용할 Fragment 객체를 생성해 반환하는 메서드
        override fun createFragment(position: Int): Fragment {
            val newFragment = when (position) {
                0 -> TestFragment()
                1 -> TestFragment()
                2 -> TestFragment()
                3 -> TestFragment()
                4 -> TestFragment()
                else -> TestFragment()
            }
            return newFragment
        }
    }
}