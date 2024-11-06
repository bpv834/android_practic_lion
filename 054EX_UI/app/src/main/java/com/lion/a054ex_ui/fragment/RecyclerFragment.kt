package com.lion.a054ex_ui.fragment

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.lion.a054ex_ui.MainActivity
import com.lion.a054ex_ui.R
import com.lion.a054ex_ui.databinding.DialogRecyclerInputBinding
import com.lion.a054ex_ui.databinding.FragmentRecyclerBinding
import com.lion.a054ex_ui.databinding.FragmentRecyclerRowBinding
import kotlin.concurrent.thread


class RecyclerFragment : Fragment() {

    lateinit var fragmentRecyclerBinding: FragmentRecyclerBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentRecyclerBinding = FragmentRecyclerBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // 툴바를 구성하는 메서드를 호출한다
        settingToolbar()
        // 툴바의 메뉴 이벤트 메서드를 호출한다.
        settingToolbarMenu()
        // RecyclerView 구성 메서드를 호출한다.
        settingRecyclerView()

        return fragmentRecyclerBinding.root
    }

    // 툴바를 구성하는 메서드
    fun settingToolbar(){
        fragmentRecyclerBinding.materialToolbarRecycler.apply {
            // 타이틀
            title = "Recycler"
            // 좌측 네비게이션 버튼
            setNavigationIcon(R.drawable.menu_24px)
            // 좌측 네비게이션 버튼을 누르면 NavigationView가 나타나게 한다.
            setNavigationOnClickListener {
                mainActivity.activityMainBinding.drawerlayoutMain.open()
            }
        }
    }

    // 툴바의 메뉴를 구성하는 메서드
    fun settingToolbarMenu(){
        fragmentRecyclerBinding.materialToolbarRecycler.apply {
            // 메뉴를 눌렀을 때 동작하는 리스너
            setOnMenuItemClickListener {
                // 메뉴의 id로 분기한다.
                when(it.itemId){
                    R.id.recycler_menu_item_add -> {
                        // 다이얼로그를 띄운다.
                        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(mainActivity)
                        // 타이틀
                        materialAlertDialogBuilder.setTitle("문자열 입력")
                        // 보여줄 뷰
                        val dialogRecyclerInputBinding = DialogRecyclerInputBinding.inflate(layoutInflater)
                        materialAlertDialogBuilder.setView(dialogRecyclerInputBinding.root)
                        // 버튼
                        materialAlertDialogBuilder.setNegativeButton("취소", null)
                        materialAlertDialogBuilder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                            // 입력 처리하는 메서드 호출
                            inputDialogEditText(dialogRecyclerInputBinding)

                        }

                        // 키보드를 올려준다.
                        thread {
                            SystemClock.sleep(1000)
                            mainActivity.runOnUiThread {
                                dialogRecyclerInputBinding.editTextDialogRecycler.requestFocus()
                                val imm = mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                                imm.showSoftInput(dialogRecyclerInputBinding.editTextDialogRecycler, 0)
                            }
                        }

                        // 띄운다.
                        val materialAlertDialog = materialAlertDialogBuilder.create()
                        materialAlertDialog.show()

                        // 키보드의 엔터키를 눌렀을 때
                        dialogRecyclerInputBinding.editTextDialogRecycler.setOnEditorActionListener { v, actionId, event ->

                            // 입력 처리하는 메서드 호출
                            inputDialogEditText(dialogRecyclerInputBinding)

                            // 다이얼로그를 없애준다.
                            materialAlertDialog.hide()
                            false
                        }
                    }
                }
                true
            }
        }
    }

    fun settingRecyclerView(){
        // RecyclerView 설정
        fragmentRecyclerBinding.recyclerViewRecycler.apply {
            // 어뎁터
            adapter = RecyclerViewRecyclerAdapter()
            // 보여지는 방식
            layoutManager = LinearLayoutManager(mainActivity)
            // 구분선
            val deco = MaterialDividerItemDecoration(mainActivity, MaterialDividerItemDecoration.VERTICAL)
            addItemDecoration(deco)
        }
    }

    // RecyclerView의 어뎁터
    inner class RecyclerViewRecyclerAdapter : RecyclerView.Adapter<RecyclerViewRecyclerAdapter.ViewHolderRecycler>(){
        // ViewHolder클래스
        inner class ViewHolderRecycler(var fragmentRecyclerRowBinding: FragmentRecyclerRowBinding) : RecyclerView.ViewHolder(fragmentRecyclerRowBinding.root){

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderRecycler {
            val fragmentRecyclerRowBinding = FragmentRecyclerRowBinding.inflate(layoutInflater)
            val viewHolderRecycler = ViewHolderRecycler(fragmentRecyclerRowBinding)
            return viewHolderRecycler
        }

        override fun getItemCount(): Int {
            return mainActivity.recyclerViewList.size
        }

        override fun onBindViewHolder(holder: ViewHolderRecycler, position: Int) {
            holder.fragmentRecyclerRowBinding.textViewRecyclerRow.text = mainActivity.recyclerViewList[position]
        }
    }

    // 입력 처리를 하는 메서드
    fun inputDialogEditText(dialogRecyclerInputBinding:DialogRecyclerInputBinding){
        dialogRecyclerInputBinding.apply {
            // 입력한 문자열 데이터를 가져온다.
            val str1 = editTextDialogRecycler.text.toString()
            // 리스트에 담아준다.
            mainActivity.recyclerViewList.add(str1)
            // RecyclerView를 갱신한다.
            fragmentRecyclerBinding.recyclerViewRecycler.adapter?.notifyDataSetChanged()
        }
    }
}