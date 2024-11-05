package com.example.a040ex_todoapplication.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.a040ex_todoapplication.FragmentName
import com.example.a040ex_todoapplication.MainActivity
import com.example.a040ex_todoapplication.R
import com.example.a040ex_todoapplication.databinding.FragmentInputBinding

class InputFragment : Fragment() {
    lateinit var fragmentInputBinding : FragmentInputBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentInputBinding = FragmentInputBinding.inflate(inflater)
        settingButton()
        return fragmentInputBinding.root
    }
    fun settingButton() {
        fragmentInputBinding.apply {
            button.setOnClickListener{
                val title = textFieldInputTitle.editText?.text.toString()
                val content = textFieldInputContent.editText?.text.toString()

                val model = DataModel(title, content,true)

                val a1 = activity as MainActivity
                a1.dataList.add(model)
                a1.removeFragment(FragmentName.INPUT_FRAGMENT)
                Log.d("test100", "${a1.dataList.size}")
            }
        }
    }

}