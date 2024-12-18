package com.lion.boardproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lion.boardproject.BoardActivity
import com.lion.boardproject.R
import com.lion.boardproject.databinding.FragmentBoardListBinding
import com.lion.boardproject.viewmodel.BoardListViewModel

class BoardListFragment(val boardMainFragment: BoardMainFragment) : Fragment() {

    lateinit var fragmentBoardListBinding: FragmentBoardListBinding
    lateinit var boardActivity: BoardActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentBoardListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_board_list, container, false)
        fragmentBoardListBinding.boardListViewModel = BoardListViewModel(this@BoardListFragment)
        fragmentBoardListBinding.lifecycleOwner = this@BoardListFragment

        boardActivity = activity as BoardActivity

        fragmentBoardListBinding.button.setOnClickListener {
            boardMainFragment.fragmentBoardMainBinding.drawerLayoutBoardMain.open()
        }

        return fragmentBoardListBinding.root
    }
}