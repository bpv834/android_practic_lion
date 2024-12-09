package com.lion.a11_mvvm_recyclerview

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.lion.a11_mvvm_recyclerview.databinding.ActivityMainBinding
import com.lion.a11_mvvm_recyclerview.databinding.RowBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel

    // 리사이클러뷰 구성을 위한 임시데이터
//    val tempData = Array(100){
//        "항목 $it"
//    }

    // 리사이클러 뷰를 구성하기 위한 리스트
    val dataList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityMainBinding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        mainViewModel = MainViewModel(this@MainActivity)
        activityMainBinding.mainViewModel = mainViewModel
        activityMainBinding.lifecycleOwner = this@MainActivity

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        activityMainBinding.apply {
            recyclerView.adapter = AdapterClass()
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            val deco = MaterialDividerItemDecoration(this@MainActivity, MaterialDividerItemDecoration.VERTICAL)
            recyclerView.addItemDecoration(deco)
        }

    }
    inner class AdapterClass : RecyclerView.Adapter<AdapterClass.ViewHolderClass>(){
        inner class ViewHolderClass(val rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root)

//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
//            val rowBinding = RowBinding.inflate(layoutInflater, parent, false)
//            val viewHolderClass = ViewHolderClass(rowBinding)
//            return viewHolderClass
//        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowBinding = DataBindingUtil.inflate<RowBinding>(layoutInflater, R.layout.row, parent, false)
            val rowViewModel = RowViewModel(this@MainActivity)
            rowBinding.rowViewModel = rowViewModel
            rowBinding.lifecycleOwner = this@MainActivity

            val viewHolderClass = ViewHolderClass(rowBinding)

            return viewHolderClass
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            // holder.rowBinding.textViewRow.text = "aaaa"
            holder.rowBinding.rowViewModel?.textViewRowText?.value = dataList[position]
        }
    }

    // recyclerview에 항목을 추가하는 메서드
    fun addRecyclerViewItem(){
        // 리스트에 데이터를 추가한다.
        dataList.add(mainViewModel.editTextText.value!!)

        mainViewModel.editTextText.value = ""

        // RecyclerView를 갱신한다.
        activityMainBinding.recyclerView.adapter?.notifyItemChanged(dataList.lastIndex)
    }
}








