package com.example.a042ex_fragment1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a042ex_fragment1.DataModel
import com.example.a042ex_fragment1.FragmentName
import com.example.a042ex_fragment1.MainActivity
import com.example.a042ex_fragment1.R
import com.example.a042ex_fragment1.databinding.ActivityMainBinding
import com.example.a042ex_fragment1.databinding.FragmentMainBinding
import com.example.a042ex_fragment1.databinding.RowMainBinding
import com.google.android.material.divider.MaterialDividerItemDecoration


class MainFragment : Fragment() {
    lateinit var fragmentMainBinding: FragmentMainBinding

/*
    // RecyclerView 구성을 위한 임시 데이터
    val dataList = Array(50) {
        "항목 $it"
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentMainBinding = FragmentMainBinding.inflate(inflater)
        //RecyclerView 구성
        settingRecyclerViewMain()
        settingFabMain()
        return fragmentMainBinding.root
    }

    // RecyclerView 구성
    fun settingRecyclerViewMain() {
        fragmentMainBinding.apply {
            // 어뎁터 세팅
            recyclerViewMain.adapter = RecyclerViewMainAdapter()

            // 보여주는 방식 , 컨텍스트가 필요하면 액티비티를 get 하라
            recyclerViewMain.layoutManager = LinearLayoutManager(activity)

            //구분선
            val deco = MaterialDividerItemDecoration(
                requireActivity(),
                MaterialDividerItemDecoration.VERTICAL
            )
            recyclerViewMain.addItemDecoration(deco)
        }
    }

    // FAB 구성
    fun settingFabMain(){
        fragmentMainBinding.apply {
            fabMain.setOnClickListener {
                // InputFragment로 변경한다.
                val a1 = activity as MainActivity
                a1.replaceFragment(FragmentName.INPUT_FRAGMENT, true,null)
            }
        }
    }

    // RecyclerView 의 어뎁터 클래스
    inner class RecyclerViewMainAdapter :
        RecyclerView.Adapter<RecyclerViewMainAdapter.MainViewHolder>() {
        // ViewHolder
        inner class MainViewHolder(var rowMainBinding: RowMainBinding) : RecyclerView.ViewHolder(rowMainBinding.root),
            View.OnClickListener {
            override fun onClick(v: View?) {
                // ShowFragment가 보이도록 한다.
                val a1 = activity as MainActivity
                val dataBundle = Bundle()
                dataBundle.putInt("position",adapterPosition)
                a1.replaceFragment(FragmentName.SHOW_FRAGMENT, true, dataBundle)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val rowMainViewHolder = RowMainBinding.inflate(layoutInflater)
            val mainViewHolder = MainViewHolder(rowMainViewHolder)

            rowMainViewHolder.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            rowMainViewHolder.root.setOnClickListener(mainViewHolder)

            return mainViewHolder
        }

        override fun getItemCount(): Int {
           // return dataList.size
            val a1 = activity as MainActivity
            return a1.dataList.size
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
           // holder.rowMainBinding.textViewMainRow.text = dataList[position]
            val a1 = activity as MainActivity
            holder.rowMainBinding.textViewMainRow.text = a1.dataList[position].name
        }
    }
}