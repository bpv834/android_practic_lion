package com.lion.a061ex_roomdatabase.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.lion.a061ex_roomdatabase.MainActivity
import com.lion.a061ex_roomdatabase.R
import com.lion.a061ex_roomdatabase.databinding.FragmentMainBinding
import com.lion.a061ex_roomdatabase.databinding.RowMainBinding
import com.lion.a061ex_roomdatabase.repository.StudentRepository
import com.lion.a061ex_roomdatabase.util.FragmentName
import com.lion.a061ex_roomdatabase.viewmodel.StudentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MainFragment : Fragment() {

    lateinit var fragmentMainBinding: FragmentMainBinding
    lateinit var mainActivity: MainActivity

    // RecyclerView 구성을 위한 임시데이터
//    val studentList = Array(50){
//        "홍길동 $it"
//    }
    var studentList = mutableListOf<StudentViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentMainBinding = FragmentMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // Toolbar를 구성하는 메서드 호출
        settingToolbar()
        // RecyclerView를 구성하는 메서드 호출
        settingRecyclerView()
        // FAB를 구성하는 메서드 호출
        settingFAB()
        // RecyclerView 갱신 메서드를 호출한다.
        refreshRecyclerViewMain()

        return fragmentMainBinding.root
    }

    // Toolbar를 구성하는 메서드
    fun settingToolbar(){
        fragmentMainBinding.apply {
            // 타이틀
            toolbarMain.title = "학생 목록"
        }
    }

    // RecyclerView를 구성하는 메서드
    fun settingRecyclerView(){
        fragmentMainBinding.apply {
            // 어뎁터
            recyclerViewMain.adapter = RecyclerViewMainAdapter()
            // LayoutManager
            recyclerViewMain.layoutManager = LinearLayoutManager(mainActivity)
            // 구분선
            val deco = MaterialDividerItemDecoration(mainActivity, MaterialDividerItemDecoration.VERTICAL)
            recyclerViewMain.addItemDecoration(deco)
        }
    }

    // fab를 구성하는 메서드
    fun settingFAB(){
        fragmentMainBinding.apply {
            fabMainAdd.setOnClickListener {
                // InputFragment로 이동한다.
                mainActivity.replaceFragment(FragmentName.INPUT_FRAGMENT, true, null)
            }
        }
    }

    // 학생 정보를 가져와 RecyclerView를 갱신하는 메서드
    fun refreshRecyclerViewMain(){
        // 학생 정보를 가져온다.
        CoroutineScope(Dispatchers.Main).launch {
            val work1 = async(Dispatchers.IO){
                // 학생정보를 가져온다.
                StudentRepository.selectStudentInfoAll(mainActivity)
            }
            studentList = work1.await()
            // RecyclerView를 갱신한다.
            fragmentMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
        }
    }

    // RecyclerView의 어뎁터
    inner class RecyclerViewMainAdapter : RecyclerView.Adapter<RecyclerViewMainAdapter.ViewHolderMain>(){
        // ViewHolder
        inner class ViewHolderMain(val rowMainBinding: RowMainBinding) : RecyclerView.ViewHolder(rowMainBinding.root), OnClickListener{
            override fun onClick(v: View?) {
                // 사용자가 누른 학생의 학생 번호를 담아준다.
                val dataBundle = Bundle()
                dataBundle.putInt("studentIdx", studentList[adapterPosition].studentIdx)
                // ShowFragment로 이동한다.
                mainActivity.replaceFragment(FragmentName.SHOW_FRAGMENT, true, dataBundle)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMain {

//            val rowMainBinding = RowMainBinding.inflate(layoutInflater)
//            rowMainBinding.root.layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )

            // RecyclerView는 자신에게 붙어진 항목 View의 크기를 자동으로 설정해주지 않는다.
            // 이에 ViewBinding 객체를 생성할 때 부모가 누구인지를 알려줘야지만 match_parent를 통해 크기를 설정할 수 있다.
            // 두 번째  : 부모를 지정한다. onCreateViewHolder 메서드의 parent 변수안에는 RecyclerView가 들어온다.
            // 이를 지정해준다.
            // 세 번째 : 생성된 View 객체를 부모에 붙힐 것인가를 설정한다. ReyclerView는 나중에 항목 View가 자동으로 붙는다.
            // 여기에서 true를 넣어주면 ViewBinding 객체가 생성될때 한번, ViewHolder를 반환할 때 한번 더 붙힐려고 하기 때문에
            // 오류가 발생한다.
            val rowMainBinding = RowMainBinding.inflate(layoutInflater, parent, false)

            val viewHolderMain = ViewHolderMain(rowMainBinding)

            // 리스너를 설정해준다.
            rowMainBinding.root.setOnClickListener(viewHolderMain)

            return viewHolderMain
        }

        override fun getItemCount(): Int {
            return studentList.size
        }

        override fun onBindViewHolder(holder: ViewHolderMain, position: Int) {
            holder.rowMainBinding.textViewRowMainStudentName.text = studentList[position].studentName
        }
    }
}