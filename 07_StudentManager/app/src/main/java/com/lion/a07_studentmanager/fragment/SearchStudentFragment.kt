package com.lion.a07_studentmanager.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.lion.a07_studentmanager.MainActivity
import com.lion.a07_studentmanager.R
import com.lion.a07_studentmanager.databinding.FragmentSearchStudentBinding
import com.lion.a07_studentmanager.databinding.RowText1Binding

class SearchStudentFragment(val mainFragment: MainFragment) : Fragment() {

    lateinit var fragmentSearchStudentBinding: FragmentSearchStudentBinding
    lateinit var mainActivity: MainActivity

    // 리사이클러 뷰 구성을 위한 임시 데이터
    val tempData = Array(100){
        "학생 ${it + 1}"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentSearchStudentBinding = FragmentSearchStudentBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // 툴바를 구성하는 메서드를 호출한다.
        settingToolbarSearchStudent()

        // recyclerView를 구성하는 메서드
        settingRecyclerViewSearchStudent()
        return fragmentSearchStudentBinding.root
    }

    // 툴바를 구성하는 메서드
    fun settingToolbarSearchStudent() {
        fragmentSearchStudentBinding.apply {
            toolbarSearchStudent.title = "학생 정보 검색"

            toolbarSearchStudent.setNavigationIcon(R.drawable.arrow_back_24px)
            toolbarSearchStudent.setNavigationOnClickListener {
                mainFragment.removeFragment(SubFragmentName.SEARCH_STUDENT_FRAGMENT)
            }

        }
    }

     // RecyclerView를 구성하는 메서드
         fun settingRecyclerViewSearchStudent(){
             fragmentSearchStudentBinding.apply {
                 // 어뎁터
                 recyclerViewSearchStudent.adapter = RecyclerViewStudentSearchAdapter()
                 // LayoutManager
                 recyclerViewSearchStudent.layoutManager = LinearLayoutManager(mainActivity)
                 // 구분선
                 val deco = MaterialDividerItemDecoration(mainActivity, MaterialDividerItemDecoration.VERTICAL)
                 recyclerViewSearchStudent.addItemDecoration(deco)
             }
         }

         // RecyclerView의 어뎁터
         inner class RecyclerViewStudentSearchAdapter : RecyclerView.Adapter<RecyclerViewStudentSearchAdapter.ViewHolderStudentSearch>(){
             // ViewHolder
             inner class ViewHolderStudentSearch(val rowText1Binding: RowText1Binding) : RecyclerView.ViewHolder(rowText1Binding.root),
                 View.OnClickListener {
                 override fun onClick(v: View?) {
                     // 사용자가 누른 동물 인덱스 담아준다.
                     val dataBundle = Bundle()
                    // dataBundle.putInt("animalIdx", testList[adapterPosition].)
                     // ShowFragment로 이동한다.
                     mainFragment.replaceFragment(SubFragmentName.SHOW_STUDENT_FRAGMENT,true,true,null)
                 }
             }

             override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderStudentSearch {

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
                 val rowText1Binding = RowText1Binding.inflate(layoutInflater, parent, false)

                 val viewHolderStudentSearch = ViewHolderStudentSearch(rowText1Binding)

                 // 리스너를 설정해준다.
                 rowText1Binding.root.setOnClickListener(viewHolderStudentSearch)

                 return viewHolderStudentSearch
             }

             override fun getItemCount(): Int {
                 return tempData.size
             }

             override fun onBindViewHolder(holder: ViewHolderStudentSearch, position: Int) {
                 holder.rowText1Binding.textViewRow.text = tempData[position]
             }
         }

}