package com.lion.boardproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lion.boardproject.R
import com.lion.boardproject.UserActivity
import com.lion.boardproject.databinding.FragmentLoginBinding
import com.lion.boardproject.databinding.FragmentUserJoinStep2Binding
import com.lion.boardproject.viewmodel.UserJoinStep2ViewModel

class UserJoinStep2Fragment : Fragment() {

    lateinit var fragmentUserJoinStep2Binding: FragmentUserJoinStep2Binding
    lateinit var userActivity: UserActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentUserJoinStep2Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_join_step2, container, false)
        fragmentUserJoinStep2Binding.userJoinStep2ViewModel = UserJoinStep2ViewModel(this@UserJoinStep2Fragment)
        fragmentUserJoinStep2Binding.lifecycleOwner = this@UserJoinStep2Fragment

        userActivity = activity as UserActivity

        return fragmentUserJoinStep2Binding.root
    }

}