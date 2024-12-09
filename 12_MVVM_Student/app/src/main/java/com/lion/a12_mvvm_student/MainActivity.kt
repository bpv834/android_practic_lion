package com.lion.a12_mvvm_student

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.lion.a12_mvvm_student.databinding.ActivityMainBinding
import com.lion.a12_mvvm_student.databinding.RowBinding
import com.lion.a12_mvvm_student.generated.callback.OnClickListener
import com.lion.a12_mvvm_student.model.StudentModel
import com.lion.a12_mvvm_student.service.StudentService
import com.lion.a12_mvvm_student.viewmodel.MainViewModel
import com.lion.a12_mvvm_student.viewmodel.RowViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var mainViewModel : MainViewModel
/*    // RecyclerView 구성을 위한 데이터
    val tempData = Array(100){
        "학생 $it"
    }*/

    // 모든 학생들의 정보를 담을 리스트
    var studentList = mutableListOf<StudentModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // DataBinding 세팅
        activityMainBinding = DataBindingUtil.setContentView(this@MainActivity,R.layout.activity_main)
        mainViewModel = MainViewModel(this@MainActivity)
        activityMainBinding.mainViewModel = mainViewModel
        activityMainBinding.lifecycleOwner=this@MainActivity
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ReclcerView를 구성하는 메서드를 호출한다.
        settingRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        // 데이터를 가져와 RecyclerView를 갱신하는 메서드를 호출한다.
        refreshRecyclerView()
    }

    // InputActivity를 실행하는 메서드
    fun startInputActivity(){
        val inputIntent = Intent(this@MainActivity, InputActivity::class.java)
        startActivity(inputIntent)
    }

    // ReclcerView를 구성하는 메서드
    fun settingRecyclerView(){
        activityMainBinding.apply {
            recyclerViewMain.adapter = RecyclerViewMainAdapter()
            recyclerViewMain.layoutManager = LinearLayoutManager(this@MainActivity)
            val deco = MaterialDividerItemDecoration(this@MainActivity, MaterialDividerItemDecoration.VERTICAL)
            recyclerViewMain.addItemDecoration(deco)
        }
    }

    // 데이터 가져와 RecyclerView 갱신
    fun refreshRecyclerView() {
        CoroutineScope(Dispatchers.Main).launch {
            val work1 = async(Dispatchers.IO){
                StudentService.gettingStudentAll(this@MainActivity)
            }
            studentList= work1.await()
            activityMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
        }
    }

    // RecyclerView의 어뎁터
    inner class RecyclerViewMainAdapter : RecyclerView.Adapter<RecyclerViewMainAdapter.ViewHolderMain>(){
        inner class ViewHolderMain(val rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMain {
            val rowBinding = DataBindingUtil.inflate<RowBinding>(layoutInflater, R.layout.row, parent, false)
            rowBinding.rowViewModel = RowViewModel(this@MainActivity)
            rowBinding.lifecycleOwner = this@MainActivity

            val viewHolderMain = ViewHolderMain(rowBinding)
            rowBinding.root.setOnClickListener {
                val showIntent= Intent(this@MainActivity, ShowActivity::class.java)
                // 사용자가 선택한 학생의 idx 값 담아준다
                showIntent.putExtra("studentIdx",studentList[viewHolderMain.adapterPosition].studentIdx)
                startActivity(showIntent)
            }

            return viewHolderMain
        }

        override fun getItemCount(): Int {
            return studentList.size
        }

        override fun onBindViewHolder(holder: ViewHolderMain, position: Int) {
            // ViewModel liveData에 넣어줌
            holder.rowBinding.rowViewModel?.textViewRow?.value = studentList[position].studentName
        }
    }
}