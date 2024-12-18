package com.lion.boardproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lion.boardproject.BoardActivity
import com.lion.boardproject.R
import com.lion.boardproject.databinding.FragmentUserModifyBinding
import com.lion.boardproject.viewmodel.UserModifyViewModel

class UserModifyFragment(val boardMainFragment: BoardMainFragment) : Fragment() {

    lateinit var fragmentUserModifyBinding: FragmentUserModifyBinding
    lateinit var boardActivity: BoardActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentUserModifyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_modify, container, false)
        fragmentUserModifyBinding.userModifyViewModel = UserModifyViewModel(this@UserModifyFragment)
        fragmentUserModifyBinding.lifecycleOwner = this@UserModifyFragment

        boardActivity = activity as BoardActivity

        return fragmentUserModifyBinding.root
    }
}