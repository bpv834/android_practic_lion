package com.lion.boardproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.lion.boardproject.BoardActivity
import com.lion.boardproject.R
import com.lion.boardproject.databinding.FragmentBoardListBinding
import com.lion.boardproject.databinding.RowBoardListBinding
import com.lion.boardproject.viewmodel.BoardListViewModel
import com.lion.boardproject.viewmodel.RowBoardListViewModel

class BoardListFragment(val boardMainFragment: BoardMainFragment) : Fragment() {

    lateinit var fragmentBoardListBinding: FragmentBoardListBinding
    lateinit var boardActivity: BoardActivity

    // ReyclerView 구성을 위한 임시 데이터
    val tempList1 = Array(100) {
        "글제목 ${it + 1}"
    }
    val tempList2 = Array(100){
        "닉네임 ${it + 1}"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentBoardListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_board_list, container, false)
        fragmentBoardListBinding.boardListViewModel = BoardListViewModel(this@BoardListFragment)
        fragmentBoardListBinding.lifecycleOwner = this@BoardListFragment

        boardActivity = activity as BoardActivity

        // 툴바를 구성하는 메서드를 호출한다.
        settingToolbar()
        // 메인 RecyclerView 구성 메서드를 호출한다.
        settingMainRecyclerView()
        // 검색 결과 RecyclerView 구성 메서드를 호출한다.
        settingResultRecyclerView()

        return fragmentBoardListBinding.root
    }
    
    // 툴바를 구성하는 메서드
    fun settingToolbar(){
        // 타이틀
        fragmentBoardListBinding.boardListViewModel?.toolbarBoardListTitle?.value = "임시게시판이름"
    }

    // 메인 RecyclerView 구성 메서드
    fun settingMainRecyclerView(){
        fragmentBoardListBinding.apply {
            recyclerViewBoardListMain.adapter = MainRecyclerViewAdapter()
            recyclerViewBoardListMain.layoutManager = LinearLayoutManager(boardActivity)
            val deco = MaterialDividerItemDecoration(boardActivity, MaterialDividerItemDecoration.VERTICAL)
            recyclerViewBoardListMain.addItemDecoration(deco)
        }
    }

    // 검색 결과 RecyclerView 구성 메서드
    fun settingResultRecyclerView(){
        fragmentBoardListBinding.apply {
            recyclerViewBoardListResult.adapter = ResultRecyclerViewAdapter()
            recyclerViewBoardListResult.layoutManager = LinearLayoutManager(boardActivity)
            val deco = MaterialDividerItemDecoration(boardActivity, MaterialDividerItemDecoration.VERTICAL)
            recyclerViewBoardListResult.addItemDecoration(deco)
        }
    }

    // 메인 RecyclerView의 어뎁터
    inner class MainRecyclerViewAdapter : RecyclerView.Adapter<MainRecyclerViewAdapter.MainViewHolder>(){
        inner class MainViewHolder(val rowBoardListBinding: RowBoardListBinding) : RecyclerView.ViewHolder(rowBoardListBinding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val rowBoardListBinding = DataBindingUtil.inflate<RowBoardListBinding>(layoutInflater, R.layout.row_board_list, parent, false)
            rowBoardListBinding.rowBoardListViewModel = RowBoardListViewModel(this@BoardListFragment)
            rowBoardListBinding.lifecycleOwner = this@BoardListFragment

            val mainViewHolder = MainViewHolder(rowBoardListBinding)
            return mainViewHolder
        }

        override fun getItemCount(): Int {
            return tempList1.size
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            holder.rowBoardListBinding.rowBoardListViewModel?.textViewRowBoardListTitleText?.value = tempList1[position]
            holder.rowBoardListBinding.rowBoardListViewModel?.textViewRowBoardListNickNameText?.value = tempList2[position]
        }
    }

    // 검색결과 RecyclerView의 어뎁터
    inner class ResultRecyclerViewAdapter : RecyclerView.Adapter<ResultRecyclerViewAdapter.ResultViewHolder>(){
        inner class ResultViewHolder(val rowBoardListBinding: RowBoardListBinding) : RecyclerView.ViewHolder(rowBoardListBinding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
            val rowBoardListBinding = DataBindingUtil.inflate<RowBoardListBinding>(layoutInflater, R.layout.row_board_list, parent, false)
            rowBoardListBinding.rowBoardListViewModel = RowBoardListViewModel(this@BoardListFragment)
            rowBoardListBinding.lifecycleOwner = this@BoardListFragment

            val resultViewHolder = ResultViewHolder(rowBoardListBinding)
            return resultViewHolder
        }

        override fun getItemCount(): Int {
            return tempList1.size
        }

        override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
            holder.rowBoardListBinding.rowBoardListViewModel?.textViewRowBoardListTitleText?.value = tempList1[position]
            holder.rowBoardListBinding.rowBoardListViewModel?.textViewRowBoardListNickNameText?.value = tempList2[position]
        }
    }
}