package com.lion.boardproject.fragment

import BoardModifyFragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.google.android.material.transition.MaterialSharedAxis
import com.lion.boardproject.BoardActivity
import com.lion.boardproject.BoardFragmentName
import com.lion.boardproject.R
import com.lion.boardproject.UserFragmentName
import com.lion.boardproject.databinding.FragmentBoardMainBinding
import com.lion.boardproject.databinding.NavigationBoardMainHeaderBinding
import com.lion.boardproject.viewmodel.BoardMainViewModel
import com.lion.boardproject.viewmodel.NavigationBoardMainHeaderViewModel

class BoardMainFragment : Fragment() {

    lateinit var fragmentBoardMainBinding:FragmentBoardMainBinding
    lateinit var boardActivity: BoardActivity

    // 현재 Fragment와 다음 Fragment를 담을 변수(애니메이션 이동 때문에...)
    var newFragment: Fragment? = null
    var oldFragment: Fragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentBoardMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_board_main, container, false)
        fragmentBoardMainBinding.boardMainViewModel = BoardMainViewModel(this@BoardMainFragment)
        fragmentBoardMainBinding.lifecycleOwner = this@BoardMainFragment

        boardActivity = activity as BoardActivity
        // 첫 프래그먼트 설정
        replaceFragment(BoardSubFragmentName.BOARD_LIST_FRAGMENT, false, false, null)

        // NavigationView를 구성하는 메서드호출
        settingNavigationView()

        return fragmentBoardMainBinding.root
    }

    // 프래그먼트를 교체하는 함수
    fun replaceFragment(fragmentName: BoardSubFragmentName, isAddToBackStack:Boolean, animate:Boolean, dataBundle: Bundle?){
        // newFragment가 null이 아니라면 oldFragment 변수에 담아준다.
        if(newFragment != null){
            oldFragment = newFragment
        }
        // 프래그먼트 객체
        newFragment = when(fragmentName){
            // 게시글 목록 화면
            BoardSubFragmentName.BOARD_LIST_FRAGMENT -> BoardListFragment(this@BoardMainFragment)
            // 게시글 작성 화면
            BoardSubFragmentName.BOARD_WRITE_FRAGMENT -> BoardWriteFragment(this@BoardMainFragment)
            // 게시글 읽는 화면
            BoardSubFragmentName.BOARD_READ_FRAGMENT -> BoardReadFragment(this@BoardMainFragment)
            // 게시글 수정 화면
            BoardSubFragmentName.BOARD_MODIFY_FRAGMENT -> BoardModifyFragment(this@BoardMainFragment)
            // 사용자 정보 수정 화면
            BoardSubFragmentName.USER_MODIFY_FRAGMENT -> UserModifyFragment(this@BoardMainFragment)
        }

        // bundle 객체가 null이 아니라면
        if(dataBundle != null){
            newFragment?.arguments = dataBundle
        }

        // 프래그먼트 교체
        boardActivity.supportFragmentManager.commit {

            if(animate) {
                // 만약 이전 프래그먼트가 있다면
                if(oldFragment != null){
                    oldFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
                    oldFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
                }

                newFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
                newFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
                newFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
                newFragment?.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
            }

            replace(R.id.fragmentContainerViewBoardMain, newFragment!!)
            if(isAddToBackStack){
                addToBackStack(fragmentName.str)
            }
        }
    }


    // 프래그먼트를 BackStack에서 제거하는 메서드
    fun removeFragment(fragmentName: BoardSubFragmentName){
        boardActivity.supportFragmentManager.popBackStack(fragmentName.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    // NavigationView를 구성하는 메서드
    fun settingNavigationView(){
        fragmentBoardMainBinding.apply {
            // Header 구성
            val navigationBoardMainHeaderBinding = DataBindingUtil.inflate<NavigationBoardMainHeaderBinding>(
                layoutInflater, R.layout.navigation_board_main_header, null, false
            )
            navigationBoardMainHeaderBinding.navigationBoardMainHeaderViewModel = NavigationBoardMainHeaderViewModel(this@BoardMainFragment)
            navigationBoardMainHeaderBinding.lifecycleOwner = this@BoardMainFragment

            // 닉네임 설정
            navigationBoardMainHeaderBinding.navigationBoardMainHeaderViewModel?.textViewNavigationBoardMainHeaderNickNameText?.value = "닉네임님"

            navigationViewBoardMain.addHeaderView(navigationBoardMainHeaderBinding.root)

            // 메뉴 구성
            navigationViewBoardMain.inflateMenu(R.menu.menu_board_main_navigation)
            navigationViewBoardMain.setCheckedItem(R.id.menuItemBoardNavigationAll)
            navigationViewBoardMain.setNavigationItemSelectedListener {
                when(it.itemId){
                    // 전체 게시판
                    R.id.menuItemBoardNavigationAll -> replaceFragment(BoardSubFragmentName.BOARD_LIST_FRAGMENT, false, false, null)
                    // 자유 게시판
                    R.id.menuItemBoardNavigation1 -> replaceFragment(BoardSubFragmentName.BOARD_LIST_FRAGMENT, false, false, null)
                    // 유머 게시판
                    R.id.menuItemBoardNavigation2 -> replaceFragment(BoardSubFragmentName.BOARD_LIST_FRAGMENT, false, false, null)
                    // 시사 게시판
                    R.id.menuItemBoardNavigation3 -> replaceFragment(BoardSubFragmentName.BOARD_LIST_FRAGMENT, false, false, null)
                    // 운동 게시판
                    R.id.menuItemBoardNavigation4 -> replaceFragment(BoardSubFragmentName.BOARD_LIST_FRAGMENT, false, false, null)
                    // 사용자 정보 수정
                    R.id.menuItemBoardNavigationModifyUserInfo -> replaceFragment(BoardSubFragmentName.USER_MODIFY_FRAGMENT, false, false, null)
                    // 로그 아웃
                    R.id.menuItemBoardNavigationLogout -> {

                    }
                    // 회원 탈퇴
                    R.id.menuItemBoardNavigationSignOut -> {

                    }
                }
                drawerLayoutBoardMain.close()
                true
            }
        }
    }

    // NavigationView를 보여주는 메서드
    fun showNavigationView(){
        fragmentBoardMainBinding.drawerLayoutBoardMain.open()
    }
}

// 하위 프래그먼트들의 이름
enum class BoardSubFragmentName(var number:Int, var str:String){
    // 게시글 목록 화면
    BOARD_LIST_FRAGMENT(1, "BoardListFragment"),
    // 게시글 작성 화면
    BOARD_WRITE_FRAGMENT(2, "BoardWriteFragment"),
    // 게시글 읽기  화면
    BOARD_READ_FRAGMENT(3, "BoardReadFragment"),
    // 게시글 수정 화면
    BOARD_MODIFY_FRAGMENT(4, "BoardModifyFragment"),
    // 사용자 정보 수정 화면
    USER_MODIFY_FRAGMENT(5, "UserModifyFragment"),
}
