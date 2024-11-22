package com.lion.a066_preferencescreen.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceFragmentCompat
import com.lion.a066_preferencescreen.R
class SettingFragment : PreferenceFragmentCompat() {

    // 환경 설정화면을 구성하기 위해 호출되는 메서드
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // Preference Screen 을 구성하기 위한 XML을 지정한다.
        setPreferencesFromResource(R.xml.pref, rootKey)
    }
}