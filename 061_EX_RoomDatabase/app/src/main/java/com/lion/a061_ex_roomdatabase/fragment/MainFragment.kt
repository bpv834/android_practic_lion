package com.lion.a061_ex_roomdatabase.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lion.a061_ex_roomdatabase.FragmentName
import com.lion.a061_ex_roomdatabase.MainActivity
import com.lion.a061_ex_roomdatabase.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    lateinit var fragmentMainBinding: FragmentMainBinding
    lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentMainBinding = FragmentMainBinding.inflate(inflater)
        // Inflate the layout for this fragment

        settingFAB()
        return fragmentMainBinding.root
    }
    fun settingFAB() {
        fragmentMainBinding.apply {
            floatingActionButtonMain.setOnClickListener {
                mainActivity.replaceFragment(FragmentName.INPUT_FRAGMENT,true,null)
            }
        }
    }
}