package com.example.a026_recyclerview

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a026_recyclerview.databinding.ActivityMainBinding
import com.example.a026_recyclerview.databinding.RowBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

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
        "토고", "프랑스aaaaaaaaaaaaaaaaaa", "스위스", "스페인", "일본", "독일", "aaaaaaaaaaaaa브라질", "대한민국"
    )

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
            // RecyclerView에 어뎁터를 설정한다.
            recyclerView.adapter = RecyclerViewAdapter()
            // 항목들을 어떻게 보여줄 것인지를 설정한다.
            // 한줄에 하나씩 배치한다
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            // 가로를 2칸으로 설정하여 Grid 형태로 배치한다
            // 같은 줄에 있는 각 항목의 높이가 서로 다르면 그 줄의 높이는 가장 높이가 큰 항목에 맞춰진다.
            // recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 2)
            // 가로를 2칸으로 설정하여 Grid 형태로 배치한다. 위 아래로 스크롤
            // 같은 줄에 있는 각 항목의 높이가 서로 다르면 아래에 있는 항목이 붙는다
            // recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            // 세로를 2줄로 설정하여 Grid 형태로 배치한다. 좌우로 스크롤
            // 같은 칸에에 있는 각 항목의 가로길이가 서로 다르면 우측에 있는 항목이 붙는다
            // recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)

            // 각 항목을 구분하기 위한 구분선
            // 위 아래로 스크롤 시 : DividerItemDecoration.VERTICAL
            // 좌 우로 스크롤 시 : DividerItemDecoration.HORIZONTAL
            // val deco = DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            // Material Divider를 사용하면 좌우에 여백을 줄 수 있다.
            val deco = MaterialDividerItemDecoration(
                this@MainActivity,
                MaterialDividerItemDecoration.VERTICAL
            )
            // 디바이더 좌측 여백설정
            deco.dividerInsetStart = 300
            // 디바이더 우측 여백 설정
            deco.dividerInsetEnd = 50
            recyclerView.addItemDecoration(deco)
        }
    }

    // RecyclerView의 Adapter 클래스
    // 작업 순서
    // 1. 상속 관계를 설정하지 않고 클래스를 작성한다.
    // inner class RecyclerViewAdapter{}
    // 2. 클래스 안에 ViewHolder를 상속받은 클래스를 작성해준다.
    /*   inner class RecyclerViewAdapter{

           inner class ViewHolderClass(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root){

           }
       }*/
    // 3. 어뎁터 클래스의 상속 관계를 설정해준다.
    /*  inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>(){

          inner class ViewHolderClass(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root){

          }

          override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
              TODO("Not yet implemented")
          }

          override fun getItemCount(): Int {
              TODO("Not yet implemented")
          }

          override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
              TODO("Not yet implemented")
          }
      }*/
    // RecyclerView의 어뎁터
    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>() {
        // ViewHolder 클래스
        // 항목 하나를 구성하기 위해 사용하는 View 객체를 보관하는 역할 수행한다.
        // 생성자로 viewBinding 객체를 받는다.
        // 부모의 생성자로 보여질 View를 전달을 한다.
        // 부모의 생성자로 전달한 View 가 RecyclerView의 항목 하나로 쓰여진다.
        inner class ViewHolderClass(var rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root), View.OnClickListener {
            override fun onClick(v: View?) {
                // adapterPosition : ViewHolder를 통해 구성된 항목의 순서 값
                // 사용자가 누른 항목의 순서 값으로 사용하였다
                activityMainBinding.textView.text = strArray[adapterPosition]
            }
        }



        // ViewHolder 객체를 생성해 반환해주는 메서드
        // 항목을 구성하려고 할 때 재사용 가능한 ViewHolder가 없을 경우 이 메서드를 호출한다
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            // ViewBinding 객체를 생성한다.
            val rowBinding = RowBinding.inflate(layoutInflater)
            // ViewHolder 객체를 생성한다.
            val viewHolder = ViewHolderClass(rowBinding)

            // 항목의 크기를 전체 크기로 설정해준다.
            rowBinding.root.layoutParams = ViewGroup.LayoutParams(
                // 가로 길이
                ViewGroup.LayoutParams.MATCH_PARENT,
                // 세로 길이
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            // 항목에 대한 ViewBinding 객체에 onClickListener를 설정해준다.
            rowBinding.root.setOnClickListener(viewHolder)

            return viewHolder
        }

        //전체 항목 개수
        override fun getItemCount(): Int {
            return imageArray.size
        }

        // 항목 하나에 있는 View에 값을 넣어주는 작업을 한다.
        // holder : 현재 항목에 해당하는 ViewHolder 객체
        // position : 지금 구성하고자 하는 항목의 순서값
        // position 번째 항목 하나의 View에 값을 설정하는 작업을 한다.
        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowBinding.imageViewRow.setImageResource(imageArray[position])
            holder.rowBinding.textViewRow.text = strArray[position]
        }
    }


}