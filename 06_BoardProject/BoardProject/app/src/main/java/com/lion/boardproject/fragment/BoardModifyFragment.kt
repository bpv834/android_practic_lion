import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.lion.boardproject.BoardActivity
import com.lion.boardproject.R
import com.lion.boardproject.databinding.FragmentBoardModifyBinding
import com.lion.boardproject.fragment.BoardMainFragment
import com.lion.boardproject.fragment.BoardSubFragmentName
import com.lion.boardproject.viewmodel.BoardModifyViewModel

class BoardModifyFragment(val boardMainFragment: BoardMainFragment) : Fragment() {

    lateinit var fragmentBoardModifyBinding: FragmentBoardModifyBinding
    lateinit var boardActivity: BoardActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentBoardModifyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_board_modify, container, false)
        fragmentBoardModifyBinding.boardModifyViewModel = BoardModifyViewModel(this@BoardModifyFragment)
        fragmentBoardModifyBinding.lifecycleOwner = this@BoardModifyFragment

        boardActivity = activity as BoardActivity

        // Toolbar를 구성하는 메서드를 호출한다.
        settingToolbar()

        return fragmentBoardModifyBinding.root
    }


    // Toolbar를 구성하는 메서드
    fun settingToolbar(){
        fragmentBoardModifyBinding.apply {
            // 메뉴의 항목을 눌렀을 때
            toolbarBoardModify.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.menuItemBoardModifyCamera -> {
                        boardMainFragment.boardActivity.startCameraLauncher(this@BoardModifyFragment)
                    }
                    // 앨범
                    R.id.menuItemBoardModifyAlbum -> {
                        boardMainFragment.boardActivity.startAlbumLauncher(this@BoardModifyFragment)
                    }
                    // 초기화
                    R.id.menuItemBoardModifyReset -> {

                    }
                    // 완료
                    R.id.menuItemBoardModifyDone -> {
                        boardMainFragment.removeFragment(BoardSubFragmentName.BOARD_MODIFY_FRAGMENT)
                    }
                }
                true
            }
        }
    }

    // 이전 화면으로 돌아간다.
    fun movePrevFragment(){
        boardMainFragment.removeFragment(BoardSubFragmentName.BOARD_MODIFY_FRAGMENT)
    }
}