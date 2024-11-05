package com.example.a040ex_todoapplication.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a040ex_todoapplication.FragmentName
import com.example.a040ex_todoapplication.MainActivity
import com.example.a040ex_todoapplication.R
import com.example.a040ex_todoapplication.databinding.FragmentMainBinding
import com.example.a040ex_todoapplication.databinding.RowBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class MainFragment : Fragment() {
    lateinit var fragmentMainBinding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 뷰와 바인딩
        fragmentMainBinding = FragmentMainBinding.inflate(inflater)
        // Inflate the layout for this fragment
        settingFabMain()
        /*settingRadioGroup()*/
        settingRecyclerViewMain()
        return fragmentMainBinding.root
    }

    // RecyclerViewAdapterClass 생성
    inner class RecyclerViewMainAdapter :
        RecyclerView.Adapter<RecyclerViewMainAdapter.MainViewHolder>() {

        inner class MainViewHolder(var rowBinding: RowBinding) :
            RecyclerView.ViewHolder(rowBinding.root), View.OnClickListener {
            override fun onClick(v: View?) {
                val a1 = activity as MainActivity
                if (a1.dataList.size !=0) {
                    val dataBundle = Bundle()
                    dataBundle.putInt("position",adapterPosition)

                    a1.replaceFragment(FragmentName.SHOW_FRAGMENT, true, dataBundle)

                }
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            // Row xml 파일과 연결된 viewHolder 생성
            val rowMainViewHolder = RowBinding.inflate(layoutInflater)
            //리턴할 viewHolder 객체 생성 근데 이제 내 row xml파일과 연결된 holder를 곁들인
            val mainViewHolder = MainViewHolder(rowMainViewHolder)

            // viewHolder를 레이아웃 전체크기에 맞춤
            rowMainViewHolder.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            //리턴할 viewHolder 는 Onclick 인터페이스와 연결
            // 바인딩 된 viewHolder 의 root 계층에서 설정
            rowMainViewHolder.root.setOnClickListener(mainViewHolder)


            return mainViewHolder
        }

        override fun getItemCount(): Int {
            val a1 = activity as MainActivity
            return if (a1.dataList.size == 0) {
                1 // 데이터가 없으면 1을 반환
            } else {
                a1.dataList.size // 데이터 개수를 반환
            }
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            holder.rowBinding.apply {
                val a1 = activity as MainActivity

                if (a1.dataList.size == 0) {
                    textView.text = "등록데이터 없음" // 데이터가 없으면 이 텍스트를 설정
                } else {
                    textView.text = a1.dataList[position].title // 데이터가 있으면 제목 설정
                }
            }
        }
    }

/*
    fun settingRadioGroup() {
        fragmentMainBinding.apply {
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                val a1 = activity as MainActivity
                when (checkedId) {
                    R.id.optionAll -> {
                        // MainActivity의 전체 리스트를 사용
                        a1.dataList = a1.list
                        Log.d("test 100", "EEEEEEEEEEEEEEEEEEEEE")
                    }

                    R.id.optionNotCompleted -> {
                        // MainActivity의 미완료 리스트를 사용
                        a1.dataList = a1.completedList
                        Log.d("test 100", "WWWWWWWWWWWWWWWWWWWWWWW")

                    }
                }
                // RecyclerView의 어댑터에 데이터 변경을 알림
                recyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }*/

    fun settingRecyclerViewMain() {
        fragmentMainBinding.apply {
            // 어뎁터 세팅
            recyclerView.adapter = RecyclerViewMainAdapter()

            // 보여주는 방식 설정, 컨텍스트가 필요하면 액티비티를 get 하라
            recyclerView.layoutManager = LinearLayoutManager(activity)

            //구분선
            val deco = MaterialDividerItemDecoration(
                requireActivity(),
                MaterialDividerItemDecoration.VERTICAL
            )
            recyclerView.addItemDecoration(deco)
        }
    }

    fun settingFabMain() {
        fragmentMainBinding.apply {
            floatingActionButton.setOnClickListener {
                val activity = activity as MainActivity
                activity.replaceFragment(FragmentName.INPUT_FRAGMENT, true, null)
            }
        }
    }

}