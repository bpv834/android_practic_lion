CoordinatorLayout
- layout_width : match_parent
- layout_height : match_parent

    AppBarLayout
        - layout_width : match_parent
        - layout_height : wrap_content
        - fitsSystemWindows : true
        - background : transparent

            CollapsingToolbarLayout
                - layout_width : match_parent
                - style : Default, Medium, Large
                - layout_height : Default, Medium, Large
                - toolbarId : 관리할 Toolbar의 id를 지정한다.
                - layout_scrollFlags : 스크롤 동작을 어떻게 할 것인가(scroll | exitUntilCollapsed)
                - contentScrim : transparent
                
                이미지를 보여주겠다면 ImageView를 설정해준다.
                
                MaterialToolbar
                    - id : Toolbar의 아이디, CollapsingToolbarLayout의 toolbarId 속성과 일치해야 한다.
                    - elevation : 그림자를 어느정도 줄것인지(0dp)
                    - layout_collapseMode : 스크롤될때 메뉴가 화면에 계속 보이게 하겠다면 pin 으로 설정한다.

    NestedScrollView
        - behavior : com.google.android.material.appbar.AppBarLayout$ScrollingViewBehavior

        화면을 자유롭게 구성해주세요


---