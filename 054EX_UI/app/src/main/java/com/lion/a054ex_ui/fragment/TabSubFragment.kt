package com.lion.a054ex_ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lion.a054ex_ui.MainActivity
import com.lion.a054ex_ui.R
import com.lion.a054ex_ui.databinding.FragmentTabSubBinding

class TabSubFragment(var imageRes:Int, var textData:String) : Fragment() {

    lateinit var fragmentTabSubBinding: FragmentTabSubBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentTabSubBinding = FragmentTabSubBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentTabSubBinding.apply {
            imageViewTabSub.setImageResource(imageRes)
            textViewTabSub.text = textData
        }

        return fragmentTabSubBinding.root
    }
}