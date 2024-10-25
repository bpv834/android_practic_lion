# Chip
- 버튼, 체크박스, 라디오 처럼 사용할 수 있는 UI 요소
- 작은 버튼들, 검색 필터 등등 다양한 용도로 사용되어 진다.
- 보통 가로길이를 wrap_content 설정해서 사용한다.
- ChipGroup을 통해 Chip 들을 관리할 수 있다.

### Chip의 주요 속성
- text : Chip에 표시할 문자열을 설정할 수 있다.
- chipIconVisible : 좌측 아이콘을 보이게 할 것인가 (생략시 true)
- chipIcon : 좌측에 표시할 아이콘을 설정한다.
- closeIconVisible : 우측 아이콘을 보이게 할 것인가 (생략시 false)
- closeIcon : 우측에 표시할 아이콘을 설정한다
- checkedIconVisible : 좌측에 표시되는 체크 아이콘을 보게 할 것인가(style에 따라서 생략시 다르다)
- checkedIcon : 좌측에 표시되는 체크 아이콘을 설정한다
- checkable : 체크 기능을 사용할 것인지를 설정한다 (style에 따라서 생략시 다르다)
- clickable : 버튼 처럼 누르는 기능을 사용할 것인지를 설정한다 (생략시 true)

### Chip의 스타일
- Assist : 기본. 버튼 처럼 사용하기 위해 설정하는 Style
- Filter : 체크박스나 라디오 버튼처럼 사용하기 위해 설정하는 Style
- Input : 체크박스나 라디오 버튼처럼 사용하기 위해 설정하는 Style, 우측에 Close Icon이 나타난다
- Suggestion : 체크박스나 라디오 버튼처럼 사용하기 위해 설정하는 Style

### Chip의 주요 프로퍼티
- isChecked : 체크 상태를 설정한다

### Chip의 주요 메서드
- toggle : 체크상태를 반전시킨다

### Chip의 주요 리스너
- OnClickListener : Chip을 눌렀을 때
- OnCloseIconClick : Close 아이콘을 눌렀을 때

### Chip Group 주요 속성
- chipSpacingHorizontal : Chip 간의 가로 여백을 설정한다.
- chipSpacingVertical : Chip 간의 세로 여백을 설정한다
- singleSelection : true로 설정하면 Chip Group 안에 있는 Chip 중 하나만 선택이 가능하다
- checkedChip : 체크될 Chip 하나를 선택할 수 있다

### Chip Group 주요 프로퍼티
- checkedChipId : 그룹 내에 있는 Chip 중에 선택되어 있는 Chip 하나의 ID를 가져온다(라디오 처럼 쓸때)
- checkedChipIds : 그룹 내에 있는 Chip 중에 선택되어 있는 모든 Chip들의 ID를 가져온다(체크박스 처럼 쓸때)

### Chip Group 주요 메서드
- check : 그룹내에서 체크할 chip의 id를 설정한다. singleSelection이 true로 설정되어 있을 경우 기존에 체크되어 있는
  chip은 체크가 해제된다

### Chip Group 주요 리스너
- OnCheckedStateChangeListener : 그룹 내에 있는 Chip들의 체크 상태가 변경되었을 때