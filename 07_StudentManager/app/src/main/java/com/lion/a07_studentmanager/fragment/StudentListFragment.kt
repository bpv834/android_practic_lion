package com.lion.a07_studentmanager.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.lion.a07_studentmanager.MainActivity
import com.lion.a07_studentmanager.R
import com.lion.a07_studentmanager.databinding.DialogStudentListFilterBinding
import com.lion.a07_studentmanager.databinding.FragmentStudentListBinding
import com.lion.a07_studentmanager.databinding.RowText1Binding


class StudentListFragment(val mainFragment: MainFragment) : Fragment() {

    lateinit var fragmentStudentListBinding: FragmentStudentListBinding
    lateinit var mainActivity: MainActivity

    // recyclerView 임시 데이터
    val tempData = Array(50) {
        "학생 ${it + 1}"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentStudentListBinding = FragmentStudentListBinding.inflate(inflater, container, false)
        mainActivity = activity as MainActivity
        settingToolbarStudentList()

        settingRecyclerView()


        return fragmentStudentListBinding.root
    }

    // 툴바를 구성하는 메서드
    fun settingToolbarStudentList() {
        fragmentStudentListBinding.apply {
            toolbarStudentList.title = "학생 목록"
            toolbarStudentList.inflateMenu(R.menu.toolbar_student_list_menu)
            toolbarStudentList.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.student_list_menu_filter -> {
                          // 필터 다이얼로그를 띄우는 메서드를 호출한다.
                        showFilterDialog()
                    }

                    R.id.student_list_menu_search -> {
                        // 검색화면으로 이동한다.
                        mainFragment.replaceFragment(SubFragmentName.SEARCH_STUDENT_FRAGMENT, true, true, null)
                    }
                }
                true
            }
        }
    }

    // RecyclerView를 구성하는 메서드
    fun settingRecyclerView() {
        fragmentStudentListBinding.apply {
            // 어뎁터
            recyclerViewStudentList.adapter = RecyclerViewMainAdapter()
            // LayoutManager
            recyclerViewStudentList.layoutManager = LinearLayoutManager(mainActivity)
            // 구분선
            val deco =
                MaterialDividerItemDecoration(mainActivity, MaterialDividerItemDecoration.VERTICAL)
            recyclerViewStudentList.addItemDecoration(deco)
        }
    }

    // 필터 다이얼로그를 띄우는 메서드
    fun showFilterDialog() {
        fragmentStudentListBinding.apply {
            val builder = MaterialAlertDialogBuilder(mainActivity)
            builder.setTitle("검색 필터 설정")
            val dialogStudentListFilterBinding = DialogStudentListFilterBinding.inflate(layoutInflater)
            builder.setView(dialogStudentListFilterBinding.root)
            builder.setPositiveButton("설정완료", null)
            builder.setNegativeButton("취소", null)
            builder.show()
        }
    }

    // RecyclerView의 어뎁터
    inner class RecyclerViewMainAdapter :
        RecyclerView.Adapter<RecyclerViewMainAdapter.ViewHolderMain>() {
        // ViewHolder
        inner class ViewHolderMain(val rowText1Binding: RowText1Binding) :
            RecyclerView.ViewHolder(rowText1Binding.root),
            View.OnClickListener {
            override fun onClick(v: View?) {
                // 사용자가 누른 동물 인덱스 담아준다.
                val dataBundle = Bundle()
                // dataBundle.putInt("animalIdx", testList[adapterPosition].)
                // ShowFragment로 이동한다.
                //  mainActivity.replaceFragment(FragmentName.SHOW_FRAGMENT, true, dataBundle)
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
            val rowMainBinding = RowText1Binding.inflate(layoutInflater, parent, false)

            val viewHolderMain = ViewHolderMain(rowMainBinding)

            // 리스너를 설정해준다.
            rowMainBinding.root.setOnClickListener(viewHolderMain)

            return viewHolderMain
        }

        override fun getItemCount(): Int {
            return tempData.size
        }

        override fun onBindViewHolder(holder: ViewHolderMain, position: Int) {
            holder.rowText1Binding.textViewRow.text = tempData[position]
        }
    }
}