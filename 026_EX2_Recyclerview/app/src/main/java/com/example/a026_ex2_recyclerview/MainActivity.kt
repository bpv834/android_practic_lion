package com.example.a026_ex2_recyclerview

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.a026_ex2_recyclerview.databinding.ActivityMainBinding
import com.example.a026_ex2_recyclerview.databinding.RowBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class MainActivity : AppCompatActivity() {
    // 사용할 데이터
    val imageArray = arrayOf(
        R.drawable.imgflag1,
        R.drawable.imgflag2,
        R.drawable.imgflag3,
        R.drawable.imgflag4,
        R.drawable.imgflag5,
        R.drawable.imgflag6,
        R.drawable.imgflag7,
        R.drawable.imgflag8,
    )

    val strArray = arrayOf(
        "토고", "프랑스", "스위스", "스페인", "일본", "독일", "브라질", "대한민국"
    )
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
            mainRecyclerView.adapter = RecyclerViewAdapter()
            mainRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

            //deco
            val deco = MaterialDividerItemDecoration(
                this@MainActivity,MaterialDividerItemDecoration.VERTICAL
            )
            mainRecyclerView.addItemDecoration(deco)

        }
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>(){
        inner class ViewHolderClass(var rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root), View.OnClickListener{
            //holder 클릭 이벤트 생성
            override fun onClick(v: View?) {
                // 사용자가 누른 항목의 순서 값으로 사용하였다
                activityMainBinding.mainTxtView.text = strArray[adapterPosition]
                activityMainBinding.mainImgView.setImageResource(imageArray[adapterPosition])
            }

        }

        // ViewHolder 객체를 생성해 반환해주는 메서드
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            // ViewBind 객체를 생성
            val rowBinding : RowBinding = RowBinding.inflate(layoutInflater)
            // ViewHolder 객체 생성
            val viewHolder = ViewHolderClass(rowBinding)

            //항목의 크기를 전체 크기로 설정
            rowBinding.root.layoutParams = ViewGroup.LayoutParams(
                //가로 길이
                ViewGroup.LayoutParams.MATCH_PARENT,
                // 세로 길이
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )

            // 항목에 대한 ViewBinding 객체에 onClickListener를 설정해준다.
            rowBinding.root.setOnClickListener(viewHolder)

            return viewHolder
        }

        // 전체 항목 개수
        override fun getItemCount(): Int {
            return imageArray.size
        }

        // 항목 하나에 있는 View에 값을 넣어주는 작업을 한다.
        // holder : 현재 항목에 해당하는 ViewHolder 객체
        // position : 지금 구성하고자 하는 항목의 인덱스
        // position 번째 항목 하나의 View에 값을 설정하는 작업을 한다.
        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowBinding.rowImgView.setImageResource(imageArray[position])

            holder.rowBinding.rowTxtView.text = strArray[position]
        }
    }
}