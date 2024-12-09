package com.lion.a10_mvvm

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lion.a10_mvvm.databinding.ActivityMainBinding
import com.lion.a10_mvvm.databinding.RowBinding


class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    val dataList = mutableListOf<String>()

    lateinit var inputActivityLauncher: ActivityResultLauncher<Intent>

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        // setContentView(activityMainBinding.root)
        activityMainBinding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        mainViewModel = MainViewModel()
        activityMainBinding.mainViewModel = mainViewModel
        activityMainBinding.lifecycleOwner = this@MainActivity

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        activityMainBinding.apply {
            recyclerView.adapter = RecyclerViewAdapter()
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

            inputActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                if(it.resultCode == RESULT_OK){
                    dataList.add(it.data?.getStringExtra("value")!!)
                    recyclerView.adapter?.notifyDataSetChanged()

                }
            }

            button.setOnClickListener {
                val inputIntent = Intent(this@MainActivity, InputActivity::class.java)
                inputActivityLauncher.launch(inputIntent)
            }
        }
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>(){
        inner class ViewHolderClass(val rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowBinding = RowBinding.inflate(layoutInflater)
            val viewHolderClass = ViewHolderClass(rowBinding)

            rowBinding.root.setOnClickListener {
                val resultIntent = Intent(this@MainActivity, ResultActivity::class.java)
                resultIntent.putExtra("data", dataList[viewHolderClass.adapterPosition])
                startActivity(resultIntent)
            }
            return viewHolderClass
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowBinding.textViewRow.text = dataList[position]
        }
    }
}