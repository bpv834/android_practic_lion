package com.lion.a066_preferencescreen.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import com.lion.a066_preferencescreen.MainActivity
import com.lion.a066_preferencescreen.R
import com.lion.a066_preferencescreen.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    lateinit var fragmentResultBinding: FragmentResultBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentResultBinding = FragmentResultBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentResultBinding.apply {
            button3.setOnClickListener {
                // Preference 객체를 가져온다.
                val pref = PreferenceManager.getDefaultSharedPreferences(mainActivity)

                // 저장된 데이터를 가져온다.
                // EditTextPreference
                val data1 = pref.getString("data1", null)

                val data2 = pref.getBoolean("data2", false)

                textView.text = "data1 : ${data1}\n"
                textView.append("data2 : ${data2}\n")

                val data3 = pref.getBoolean("data3",false)
                textView.append("data3 : ${data3}\n")

                // ListPreference
                val data4 = pref.getString("data4", null)
                textView.append("data4 : ${data4}\n")

                // MultiSelectListPreference
                val data5 = pref.getStringSet("data5", null)
                data5?.forEach {
                    textView.append("data5 : ${it}\n")
                }

            }
        }

        return fragmentResultBinding.root
    }


}