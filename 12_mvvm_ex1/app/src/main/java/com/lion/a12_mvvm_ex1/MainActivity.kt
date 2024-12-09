package com.lion.a12_mvvm_ex1

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.lion.a12_mvvm_ex1.databinding.ActivityMainBinding
import com.lion.a12_mvvm_ex1.databinding.DialogBinding
import com.lion.a12_mvvm_ex1.databinding.RowBinding
import com.lion.a12_mvvm_ex1.view_model.DialogViewModel
import com.lion.a12_mvvm_ex1.view_model.MainViewModel
import com.lion.a12_mvvm_ex1.view_model.RowViewModel

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
 /*   var dialog:DialogInterface?=null*/

/*    val tempData = Array(100){
        "학생 $it"
    }*/
    var strList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // DataBinding 세팅
        activityMainBinding =  DataBindingUtil.setContentView(this@MainActivity,R.layout.activity_main)
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

    fun inputBtn(){
        val builder1 = MaterialAlertDialogBuilder(this@MainActivity)
        builder1.setTitle("기본 다이얼로그")
        //설정할 View에 대한 작업을 한다.
        val customDialogBinding = DataBindingUtil.inflate<DialogBinding>(layoutInflater,R.layout.dialog,null,false)
        customDialogBinding.dialogViewModel = DialogViewModel(this@MainActivity)
        customDialogBinding.lifecycleOwner = this@MainActivity
        builder1.setView(customDialogBinding.root)
      /*  dialog = builder1.create()*/
        builder1.show()
    }

    fun submitBtn(str:String) {
        strList.add(str)
        refreshRecyclerView()
        /*dialog!!.dismiss()*/
    }

    fun settingRecyclerView() {
        activityMainBinding.apply {
            recyclerViewMain.adapter = RecyclerViewMainAdapter()
            recyclerViewMain.layoutManager = LinearLayoutManager(this@MainActivity)
            val deco = MaterialDividerItemDecoration(this@MainActivity, MaterialDividerItemDecoration.VERTICAL)
            recyclerViewMain.addItemDecoration(deco)
        }
    }

    fun refreshRecyclerView() {
        Log.d("test100","refresh")
        activityMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
    }

    // RecyclerView의 어뎁터
    inner class RecyclerViewMainAdapter : RecyclerView.Adapter<RecyclerViewMainAdapter.ViewHolderMain>(){
        inner class ViewHolderMain(val rowBinding : RowBinding) : RecyclerView.ViewHolder(rowBinding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMain {
            val rowBinding = DataBindingUtil.inflate<RowBinding>(layoutInflater,R.layout.row, parent, false)
            rowBinding.rowViewModel = RowViewModel(this@MainActivity)
            rowBinding.lifecycleOwner = this@MainActivity

            val viewHolderMain = ViewHolderMain(rowBinding)

            return viewHolderMain
        }

        override fun getItemCount(): Int {
            if(strList.size==0 )return 1
            return strList.size
        }

        override fun onBindViewHolder(holder: ViewHolderMain, position: Int) {
            if(strList.size == 0 ){
                holder.rowBinding.rowViewModel?.textViewRow?.value = "정보를 입력해라"
            }
            else{
                holder.rowBinding.rowViewModel?.textViewRow?.value = strList[position]
            }
        }
    }
}