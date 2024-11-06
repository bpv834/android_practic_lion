package com.lion.a055_ex_ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.lion.a055_ex_ui.databinding.FragmentRecyclerBinding
import com.lion.a055_ex_ui.databinding.RowRecyclerBinding

class RecyclerFragment : Fragment() {

    private lateinit var dataModelList: MutableList<DataModel> // 데이터 모델 리스트를 저장할 변수

    lateinit var fragmentRecyclerBinding: FragmentRecyclerBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // 아규먼트로 DataList를 받아온다. null 이라면 빈 mutableList를 담아준다.
        dataModelList = arguments?.getSerializable("dataModelList") as? MutableList<DataModel>
            ?: mutableListOf()
        Log.d("test500", "${dataModelList}")
        fragmentRecyclerBinding = FragmentRecyclerBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // 툴바를 구성하는 메서드를 호출한다
        settingToolbar()

        // 툴바의 메뉴 이벤트 메서드를 호출한다.
        settingToolbarMenu()

        // RecyclerView 구성
        settingRecyclerView()

        return fragmentRecyclerBinding.root
    }

    // 툴바를 구성하는 메서드
    fun settingToolbar() {
        fragmentRecyclerBinding.materialToolbarRecycler.apply {
            // 타이틀
            title = " recycler"
            // 좌측 네비게이션 버튼
            setNavigationIcon(R.drawable.menu_24px)
            // 좌측 네비게이션 버튼을 누르면 NavigationView가 나타나게 한다.
            setNavigationOnClickListener {
                mainActivity.activityMainBinding.drawerlayoutMain.open()
            }

        }
    }

    //메뉴 버튼 세팅
    fun settingToolbarMenu() {
        fragmentRecyclerBinding.materialToolbarRecycler.apply {
            // 메뉴를 눌렀을 때 동작하는 리스너
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.recycler_menu_item_add -> {
                        //더하기 버튼을 누르면 인풋프래그먼트로 화면 전환,
                        mainActivity.replaceFragment(FragmentName.INPUT_DATA_FRAGMENT, true, null)
                        true
                    }

                    else -> {
                        true
                    }
                }
            }
        }
    }

    fun settingRecyclerView() {
        // RecyclerView 설정
        fragmentRecyclerBinding.recyclerViewRecycler.apply {
            // 어뎁터
            adapter = RecyclerViewRecyclerAdapter()
            // 보여지는 방식
            layoutManager = LinearLayoutManager(mainActivity)
            // 구분선
            val deco =
                MaterialDividerItemDecoration(mainActivity, MaterialDividerItemDecoration.VERTICAL)
            addItemDecoration(deco)
        }
    }

    // RecyclerView의 어뎁터
    inner class RecyclerViewRecyclerAdapter :
        RecyclerView.Adapter<RecyclerViewRecyclerAdapter.ViewHolderRecycler>() {
        // ViewHolder 클래스, row LayOut Binding객체를 매개변수로 받는다
        inner class ViewHolderRecycler(var rowRecyclerBinding: RowRecyclerBinding) :
            RecyclerView.ViewHolder(rowRecyclerBinding.root), View.OnClickListener {
            override fun onClick(v: View?) {
                Log.d("RecyclerViewClick", "Item clicked at position: $adapterPosition")
                // viewHolder 눌렀을 때 이벤트
                // 이벤트만 생성했고, 동작하려면 createViewHolder 에서 리스너 등록을 해줘야 한다. 안해서 한참 헤맸음
                // 전달할 데이터 리스트
                if(dataModelList.size != 0){
                    val dataBundle = Bundle()
                    dataBundle.putSerializable("dataModel",dataModelList[adapterPosition])

                    // 번들로 객체 전달과 함께 화면 전환
                    mainActivity.replaceFragment(FragmentName.SHOW_FRAGMENT,true,dataBundle)
                }

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderRecycler {
            // 타일로 쓸 xml 파일 바인딩
            val rowMainViewHolder = RowRecyclerBinding.inflate(layoutInflater)
            // 내가 만든 ViewHolder 객체 생성 근데 이제 xml 파일 바인딩 된 것을 곁들여서
            val viewHolderRecycler = ViewHolderRecycler(rowMainViewHolder)

            // viewHolder를 레이아웃 전체크기에 맞춤
            rowMainViewHolder.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            //리턴할 viewHolder 는 Onclick 인터페이스와 연결
            // 바인딩 된 viewHolder 의 root 계층에서 설정
            rowMainViewHolder.root.setOnClickListener(viewHolderRecycler)

            return viewHolderRecycler
        }

        // 리사이클러 아이템 개수 리턴
        override fun getItemCount(): Int {
            if (dataModelList.size == 0) return 1
            return dataModelList.size
        }

        override fun onBindViewHolder(holder: ViewHolderRecycler, position: Int) {
            // 바인딩 한 xml 파일에 어떤 데이터를 넣을 지 결정
            if (dataModelList.size != 0) {

                holder.rowRecyclerBinding.textViewTypeRowRecycler.text =
                    dataModelList[position].type

                holder.rowRecyclerBinding.textViewNameRowRecycler.text =
                    dataModelList[position].name
            } else {
                holder.rowRecyclerBinding.textViewTypeRowRecycler.text = "데이터를 입력해줘"
            }

        }
    }

}