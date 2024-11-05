package com.example.a040ex_animalmanager.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a040ex_animalmanager.DataModel
import com.example.a040ex_animalmanager.FragmentName
import com.example.a040ex_animalmanager.MainActivity
import com.example.a040ex_animalmanager.databinding.FragmentMainBinding
import com.example.a040ex_animalmanager.databinding.RowBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class MainFragment : Fragment() {
    lateinit var fragmentMainBinding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentMainBinding = FragmentMainBinding.inflate(inflater)


        //RecyclerView 구성
        settingRecyclerViewMain()
        //Fab 구성
        settingFabMain()
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

        // 항목의 전체 개수를 반환하는 메서드
        override fun getItemCount(): Int {
            val a1 = activity as MainActivity
            if (a1.dataList.size == 0) {
                return 1
            }
            return a1.dataList.size
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            holder.rowBinding.apply {
                val a1 = activity as MainActivity
                if (a1.dataList.size == 0) {
                    textViewMainRow.text = "등록데이터 없음"
                } else {
                    textViewMainRow.text = a1.dataList[position].name
                }
            }
        }
    }

    fun settingRecyclerViewMain() {
        fragmentMainBinding.apply {
            // 어뎁터 세팅
            recyclerViewMain.adapter = RecyclerViewMainAdapter()

            // 보여주는 방식 설정, 컨텍스트가 필요하면 액티비티를 get 하라
            recyclerViewMain.layoutManager = LinearLayoutManager(activity)

            //구분선
            val deco = MaterialDividerItemDecoration(
                requireActivity(),
                MaterialDividerItemDecoration.VERTICAL
            )
            recyclerViewMain.addItemDecoration(deco)
        }
    }

    //FAB 구성
    fun settingFabMain() {
        fragmentMainBinding.apply {
            floatingActionButtonMain.setOnClickListener {
                // InputFragment로 화면 전환 , 프레그먼트 변경
                val activity = activity as MainActivity
                activity.replaceFragment(FragmentName.INPUT_FRAGMENT, true, null)
            }

        }
    }

}