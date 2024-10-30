package com.example.a033ex_animalmanager

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a033ex_animalmanager.databinding.ActivityMainBinding
import com.example.a033ex_animalmanager.databinding.MainRowBinding
import com.google.android.material.divider.MaterialDividerItemDecoration


class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // Activity 실행을 위한 런처
    lateinit var inputDataActivityLauncher: ActivityResultLauncher<Intent>
    lateinit var showDataActivityLauncher: ActivityResultLauncher<Intent>

    // RecyclerView를 구성하기 위한 리스트
    val dataList = mutableListOf<DataModel>()


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

        // InputDataActivity 런처 구성
        settingInputDataActivityLauncher()
        // ShowDataActivity 런처 구성
        settingShowDataActivityLauncher()
        // RecyclerView 구성
        settingRecyclerViewMain()
        // FloatingActionButton 구성
        settingFloatingActionButtonMain()
    }

    // RecyclerView 구성을 위한 메서드
    fun settingRecyclerViewMain() {
        activityMainBinding.apply {
            // 어뎁터를 설정한다.
            recyclerViewMain.adapter = RecyclerViewMainAdapter()
            // 보여주는 방식을 설정한다.
            recyclerViewMain.layoutManager = LinearLayoutManager(this@MainActivity)
            // 구분선 데코레이션
            val deco = MaterialDividerItemDecoration(
                this@MainActivity,
                MaterialDividerItemDecoration.VERTICAL
            )
            recyclerViewMain.addItemDecoration(deco)

            /*    // 리사이클러뷰가 스크롤 상태가 변경되면 동작하는 리스너
                recyclerViewMain.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                    // canScrollVertically : 위나 아래로 스크롤이 가능한지를 확인하는 메서드
                    // 만약 -1 을 넣어줬는데 false가 반환되면 : 제일 위에 있는 상태
                    // 만약 1을 넣어줬는데 false가 반환되면 : 제일 마지막에 있는 상태
                    // 만약 제일 아래에 있는 상태라면..
                    if(recyclerViewMain.canScrollVertically(1) == false){
                        // FloatingActionButton을 사라지게 된다.
                        floatingActionButtonMain.hide()
                    } else {
                        // 만약 제일 아래에 있는 상태가 아니고 FloatingActionButton이 보이지 않는 상태라면
                        if(floatingActionButtonMain.isShown == false){
                            // FloatingActionButton을 나타나게 한다.
                            floatingActionButtonMain.show()
                        }
                    }
                }*/
        }
    }

    // FloatingActionButton 설정
    fun settingFloatingActionButtonMain() {
        activityMainBinding.apply {
            // Click Listener
            floatingActionButtonMain.setOnClickListener {
                // InputDataActivity를 실행한다.
                val inputDataIntent = Intent(this@MainActivity, InputDataActivity::class.java)
                inputDataActivityLauncher.launch(inputDataIntent)
            }
        }
    }

    //contracts 객체 : 4가지 기능
    //1. StartActivityForResult:
    //특정 액티비티를 실행하고 그 결과(RESULT_OK 또는 RESULT_CANCELED)와 함께 반환된 데이터를 받아옵니다.
    //이 계약은 사용자 정의 활동에서 데이터를 가져오는 데 자주 사용됩니다.
    //2. RequestPermission:
    //
    //특정 권한을 요청하고, 사용자가 권한을 승인했는지 여부를 Boolean 값으로 반환합니다.
    //3. TakePicture:
    //
    //카메라를 통해 사진을 찍고, 사진이 성공적으로 저장되었는지 여부를 Boolean 값으로 반환합니다.
    //4. GetContent:
    //
    //사용자가 미디어나 파일을 선택하도록 합니다. 선택된 파일의 URI를 반환합니다.

    // InputDataActivity 실행을 위한 런처 구성한다.
    // 목적 : InputDataActivity에서 데이터를 입력하고 돌아올 때, 이 데이터를 받아 처리합니다.
    fun settingInputDataActivityLauncher() {
        // 런처를 구성해준다. 화면전환을 하고 나서 동작하는 것은 registerForActivityResult를 콜백으로 받으면 동작함
        val contract = ActivityResultContracts.StartActivityForResult() // 회면전환 객체
        // registerForActivityResult 메서드를 통해 결과를 처리하는 콜백을 등록합니다
        inputDataActivityLauncher = registerForActivityResult(contract) {
            // 데이터가 전달된 것이 있다면
            if (it.resultCode == RESULT_OK) {
                if (it.data != null) {
                    // 데이터를 추출한다.
                    val type = it.data?.getStringExtra("type")
                    val name = it.data?.getStringExtra("name")
                    val age = it.data?.getIntExtra("age", 0)
                    val gender = it.data?.getStringExtra("gender")
                    val favoriteSnacks = it.data?.getStringArrayListExtra("favoriteSnacks")
                    // 객체에 담는다.
                    val dataModel = DataModel(type!!, name!!, age!!, gender!!, favoriteSnacks!!)
                    // 리스트에 담는다.
                    dataList.add(dataModel)
                    // 리사이클러뷰를 갱신한다.
                    activityMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    // ShowDataActivity 실행을 위한 런처를 구성한다.
    fun settingShowDataActivityLauncher() {
        val contract = ActivityResultContracts.StartActivityForResult()
        showDataActivityLauncher = registerForActivityResult(contract) {
            // 데이터가 전달된 것이 있다면
            if (it.resultCode == RESULT_OK + 1) {
                if (it.data != null) {
                    // 데이터를 추출한다.
                    val idx: Int = it.data?.getIntExtra("idx", 99)!!.toInt()

                    // idx를 이용해 삭제
                    dataList.removeAt(idx)
                    // 리사이클러뷰를 갱신한다.
                    activityMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    /* fun settingDeleteDataActivityLauncher(){
         // 런처를 구성해준다. 화면전환을 하고 나서 동작하는 것은 registerForActivityResult를 콜백으로 받으면 동작함
         val contract = ActivityResultContracts.StartActivityForResult() // 회면전환 객체
         // registerForActivityResult 메서드를 통해 결과를 처리하는 콜백을 등록합니다
         deleteDataActivityLauncher = registerForActivityResult(contract){
             // 데이터가 전달된 것이 있다면
             if(it.resultCode == RESULT_OK+1){
                 if(it.data != null){
                     // 데이터를 추출한다.
                     val idx : Int = it.data?.getStringExtra("idx")!!.toInt()

                     // idx를 이용해 삭제
                     dataList.removeAt(idx)
                     // 리사이클러뷰를 갱신한다.
                     activityMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
                 }
             }
         }
     }*/

    // RecyclerView의 어뎁터
    inner class RecyclerViewMainAdapter : RecyclerView.Adapter<RecyclerViewMainAdapter.RecyclerViewMainViewHolder>() {
        // ViewHolder를 상속받은 클래스
        // ViewHolder를 주 생성자에서 Property로 정의해야 한다.
        inner class RecyclerViewMainViewHolder(var mainRowBinding: MainRowBinding) : RecyclerView.ViewHolder(mainRowBinding.root), View.OnClickListener {
            // 항목을 눌렀을 때
            override fun onClick(v: View?) {
                if (dataList.size > 0) {
                    // ShowDataActivity를 실행한다.
                    val showDataIntent = Intent(this@MainActivity, ShowDataActivity::class.java)
                    // 사용자가 누른 항목 번째 객체에서 데이터를 추출해 Intent에 담아준다.
                    showDataIntent.putExtra("idx", adapterPosition)
                    showDataIntent.putExtra("type", dataList[adapterPosition].type)
                    showDataIntent.putExtra("name", dataList[adapterPosition].name)
                    showDataIntent.putExtra("age", dataList[adapterPosition].age)
                    showDataIntent.putExtra("gender", dataList[adapterPosition].gender)
                    showDataIntent.putExtra(
                        "favoriteSnacks",
                        dataList[adapterPosition].favoriteSnack.toTypedArray()
                    )

                    showDataActivityLauncher.launch(showDataIntent)
                }
            }
        }

        // ViewHolder를 생성하는 메서드
        // 보여져야 하는 항목에 대한 ViewHolder 가 없을 경우 호출된다.
        // 재 사용 가능한 ViewHolder가 있으면 호출하지 않는다..
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RecyclerViewMainViewHolder {
            val mainRowBinding = MainRowBinding.inflate(layoutInflater)
            val recyclerViewMainViewHolder = RecyclerViewMainViewHolder(mainRowBinding)

            mainRowBinding.apply {
                // 터치 영역을 전체로 하기 위해 가로 세로 길이를 설정한다.
                root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                // onClickListener 설정
                root.setOnClickListener(recyclerViewMainViewHolder)
            }

            return recyclerViewMainViewHolder
        }

        // 항목의 전체 개수를 반환하는 메서드
        override fun getItemCount(): Int {
            if (dataList.size == 0) {
                return 1
            }
            return dataList.size
        }

        // 항목에 배치되어 있는 View에 값을 설정하여 준다.
        override fun onBindViewHolder(holder: RecyclerViewMainViewHolder, position: Int) {
            holder.mainRowBinding.apply {
                if (dataList.size == 0) {
                    textViewMainRow.text = "등록된 데이터가 없습니다"
                } else {
                    // textViewMainRow.text = dataList[position]
                    textViewMainRow.text = dataList[position].name
                }
            }
        }
    }
}