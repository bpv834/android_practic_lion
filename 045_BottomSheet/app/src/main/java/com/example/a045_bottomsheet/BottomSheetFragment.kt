package com.example.a045_bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a045_bottomsheet.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// BottomSheet로 사용할 Fragment는 BottomSheetDialogFragment를 상속받는다.

class BottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var fragmentBottomSheetBinding: FragmentBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentBottomSheetBinding = FragmentBottomSheetBinding.inflate(inflater)
        fragmentBottomSheetBinding.buttonBottomSheet100.setOnClickListener {
            dismiss()
        }
        // Inflate the layout for this fragment
        return fragmentBottomSheetBinding.root
    }

}