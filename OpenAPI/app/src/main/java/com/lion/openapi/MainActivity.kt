package com.lion.openapi

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
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
import com.lion.openapi.databinding.ActivityMainBinding
import com.lion.openapi.databinding.RowMainBinding
import com.lion.openapi.repository.DataRepository
import com.lion.openapi.repository.ItemsApiClass
import com.lion.openapi.view_model.MainViewModel
import com.lion.openapi.view_model.RowMainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

/*
    // RecyclerView 구성을 위한 임시데이터
    val tempData = Array(100){
        "우리 동네 $it"
    }
*/
    // RecyclerView 구성을 위한 리스트
    var stationList = mutableListOf<ItemsApiClass>()

    // 시도 데이터
    val siDoArray = arrayOf(
        "서울", "부산", "대구", "인천", "광주", "대전", "울산", "경기",
        "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주", "세종"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityMainBinding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        activityMainBinding.mainViewModel = MainViewModel(this@MainActivity)
        activityMainBinding.lifecycleOwner=this@MainActivity

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // RecyclerView를 구성하는 메서드 호출
        settingRecyclerView()

    }

    // RecyclerView를 구성하는 메서드
    fun settingRecyclerView(){
        activityMainBinding.apply {
            recyclerViewResult.adapter = RecyclerViewAdapter()
            recyclerViewResult.layoutManager = LinearLayoutManager(this@MainActivity)
            val deco = MaterialDividerItemDecoration(this@MainActivity, MaterialDividerItemDecoration.VERTICAL)
            recyclerViewResult.addItemDecoration(deco)
        }
    }

    // 시 선택 버튼을 눌렀을 때 나타나는 다이얼로그 구성
    fun showSelectSiDoDialog(){
        val builder = MaterialAlertDialogBuilder(this@MainActivity)
        builder.setTitle("시도 선택")
        builder.setItems(siDoArray){ dialogInterface: DialogInterface, i: Int ->
            CoroutineScope(Dispatchers.Main).launch {
                val work1 = async(Dispatchers.IO){
                    DataRepository.getOpenApiData(siDoArray[i])
                }
                val response = work1.await()
                val resultData = response.body()!!
                stationList = resultData.responseApiClass.bodyApiClass.itemsApiList.toMutableList()
                activityMainBinding.recyclerViewResult.adapter?.notifyDataSetChanged()

                activityMainBinding.mainViewModel?.textViewMainMessageText?.value = "선택한 시도 : ${siDoArray[i]}"

            }
        }
        builder.setNegativeButton("취소", null)
        builder.show()
    }


    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>(){
        inner class ViewHolderClass(val rowMainBinding: RowMainBinding) : RecyclerView.ViewHolder(rowMainBinding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowMainBinding = DataBindingUtil.inflate<RowMainBinding>(layoutInflater, R.layout.row_main, parent, false)
            rowMainBinding.rowMainViewModel = RowMainViewModel()
            rowMainBinding.lifecycleOwner = this@MainActivity
            val viewHolderClass = ViewHolderClass(rowMainBinding)

            rowMainBinding.root.setOnClickListener {
                val resultIntent = Intent(this@MainActivity, ResultActivity::class.java)
                resultIntent.putExtra("ItemsApiClass", stationList[viewHolderClass.adapterPosition])
                startActivity(resultIntent)
            }

            return viewHolderClass
        }
        override fun getItemCount(): Int {
            return stationList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowMainBinding.rowMainViewModel?.textViewRowMainText?.value = stationList[position].stationName
        }
    }
}






