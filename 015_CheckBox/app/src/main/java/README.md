# Material CheckBox
- CheckBox를 상속받은 UI 요소
- CheckBox는 isChecked라는 속성을 통해서 체크되었냐 아니냐만 설정이 가능하다
- Material CheckBox는 checkedState라는 개념을 도입해 3가지 상태를 설정하였다.
- 체크박스들 전체를 체크하거나 전체를 해제하거나 일부만 체크되어 있음을 표시하는 용도로 사용한다.
- 사용자 동작에 의해서 설정할 수 있는 것은 체크 되었거나 해제되었거나 이고 일부만 체크되어 있는 상태는 코드를 통해서만 설정되어 있다.

[ Switch와 속성, 프로퍼티, 메서드, 리스너 등이 동일하며 추가된 것만 정리하겠습니다 ]

### 추가된 속성
- checkedState : 체크 상태를 설정한다 (checked/unchecked/indeterminate)

### 추가된 프로퍼티
- checkedState : 체크 상태를 설정한다.

### 추가된 리스너
- addOnCheckedStateChangedListener : 체크 상태가 변경되었을 때