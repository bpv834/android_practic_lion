# 상단 바 구성 (바 스크롤 X)

1. AppBarLayout을 배치한다 (이때, CoordinatorLayout으로 자동 변경된다.)
2. 만약 아래의 코드가 있는 상태라면 CoordinatorLayout의 아이디를 main으로 설정한다.

```kt
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
```

3. CollapsingToolbarLayout은 삭제한다.

4. appbar 안에 MaterialToolbar를 배치한다.

5. appbar의 가로길이를 wrap_content로 설정해준다.

6. MaterialToolbar의 배경색을 투명색으로 설정한다.


