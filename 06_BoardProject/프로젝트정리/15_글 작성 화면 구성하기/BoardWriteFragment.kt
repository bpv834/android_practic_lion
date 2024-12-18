package com.lion.boardproject.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lion.boardproject.BoardActivity
import com.lion.boardproject.R
import com.lion.boardproject.databinding.FragmentBoardWriteBinding
import com.lion.boardproject.viewmodel.BoardWriteViewModel


class BoardWriteFragment(val boardMainFragment: BoardMainFragment) : Fragment() {

    lateinit var fragmentBoardWriteBinding: FragmentBoardWriteBinding
    lateinit var boardActivity: BoardActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentBoardWriteBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_board_write, container, false)
        fragmentBoardWriteBinding.boardWriteViewModel = BoardWriteViewModel(this@BoardWriteFragment)
        fragmentBoardWriteBinding.lifecycleOwner = this@BoardWriteFragment

        boardActivity = activity as BoardActivity

        return fragmentBoardWriteBinding.root
    }


    // 이전 화면으로 돌아간다.
    fun movePrevFragment(){
        boardMainFragment.removeFragment(BoardSubFragmentName.BOARD_WRITE_FRAGMENT)
    }
}