package com.lion.a04_picker

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.lion.a04_picker.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        activityMainBinding.apply {
            button.setOnClickListener {
                // DatePicker
                val datePickerBuilder = MaterialDatePicker.Builder.datePicker()
                datePickerBuilder.setTitleText("날짜 선택")


                // 특정 날짜를 지정하고 싶다면..
                val calendar2 = Calendar.getInstance()
                calendar2.set(Calendar.YEAR, 2025)
                calendar2.set(Calendar.MONTH, 2)
                calendar2.set(Calendar.DAY_OF_MONTH, 6)
                datePickerBuilder.setSelection(calendar2.timeInMillis)

                val datePicker = datePickerBuilder.build()

                datePicker.addOnPositiveButtonClickListener {
                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = it
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH) + 1
                    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

                    textView.text = "${year}-${month}-${dayOfMonth}"
                }

                datePicker.show(supportFragmentManager, "date picker")
            }

            button2.setOnClickListener {
                val dateRangePickerBuilder = MaterialDatePicker.Builder.dateRangePicker()
                dateRangePickerBuilder.setTitleText("날짜 범위 선택")

                // 특정 날짜를 지정하고 싶다면..
                val startCalendar2 = Calendar.getInstance()
                startCalendar2.set(Calendar.YEAR, 2024)
                startCalendar2.set(Calendar.MONTH, 10)
                startCalendar2.set(Calendar.DAY_OF_MONTH, 25)

                val endCalendar2 = Calendar.getInstance()
                endCalendar2.set(Calendar.YEAR, 2025)
                endCalendar2.set(Calendar.MONTH, 2)
                endCalendar2.set(Calendar.DAY_OF_MONTH, 6)

                val rangePair = androidx.core.util.Pair(startCalendar2.timeInMillis, endCalendar2.timeInMillis)
                dateRangePickerBuilder.setSelection(rangePair)


                val dateRangePicker = dateRangePickerBuilder.build()

                dateRangePicker.addOnPositiveButtonClickListener {
                    // 시작날짜
                    val startCalendar = Calendar.getInstance()
                    startCalendar.timeInMillis = it.first

                    // 종료날짜
                    val endCalendar = Calendar.getInstance()
                    endCalendar.timeInMillis = it.second

                    val startYear = startCalendar.get(Calendar.YEAR)
                    val startMonth = startCalendar.get(Calendar.MONTH) + 1
                    val startDayOfMonth = startCalendar.get(Calendar.DAY_OF_MONTH)

                    val endYear = endCalendar.get(Calendar.YEAR)
                    val endMonth = endCalendar.get(Calendar.MONTH) + 1
                    val endDayOfMonth = endCalendar.get(Calendar.DAY_OF_MONTH)

                    textView.text = "${startYear}-${startMonth}-${startDayOfMonth} ~ ${endYear}-${endMonth}-${endDayOfMonth}"
                }

                dateRangePicker.show(supportFragmentManager, "date range picker")
            }

            button3.setOnClickListener {
                button3.setOnClickListener {
                    val timePickerBuilder = MaterialTimePicker.Builder()
                    timePickerBuilder.setTitleText("Time Picker")

                    // 만약 24시간제로 사용하고 싶다면..
                    timePickerBuilder.setTimeFormat(TimeFormat.CLOCK_24H)
                    // 초기 시간값
                    timePickerBuilder.setHour(17)
                    timePickerBuilder.setMinute(47)

                    val timerPicker = timePickerBuilder.build()

                    timerPicker.addOnPositiveButtonClickListener {
                        textView.text = "${timerPicker.hour}:${timerPicker.minute}"
                    }
                    timerPicker.show(supportFragmentManager, "time picker")
                }
            }


        }
    }
}