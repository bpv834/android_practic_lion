### PreferenceCategory
- 각 요소들을 그룹으로 묶어 줄 때 사용한다.
- title : 표시되는 타이틀

### EditTextPreference
- 문자열을 입력받기 위한 요소
- defaultValue : 기본값. 최초에 이 값이 저장되어 있는 상태가 된다.
- key : 값을 가져올 때 사용할 이름
- singleLine : 1줄 입력 여부
- title : 표시되는 타이틀
- dependency :
- dialogIcon : 다이얼로그에 표시할 아이콘
- dialogMessage : 다이얼로그에 표시할 메시지
- dialogTitle : 다이얼로그에 표시할 타이틀
- icon : 표시할 아이콘
- summary : 간단한 설명

### CheckBoxPreference
- 체크 박스 요소
- defaultValue : 기본값. 최초에 이 값이 저장되어 있는 상태가 된다.
- key : 값을 가져올 때 사용할 이름
- title : 표시되는 타이틀
- icon : 표시할 아이콘
- summary : 간단한 설명
- summaryOff : 체크 해제 되어 있을 때 표시할 설명, summary 속성은 무시된다.
- summaryOn : 체크 되어 있을 때 표시할 설명, summary 속성은 무시된다

### SwitchPreference
- 스위치 요소
- defaultValue : 기본값. 최초에 이 값이 저장되어 있는 상태가 된다.
- key : 값을 가져올 때 사용할 이름
- title : 표시되는 타이틀
- icon : 표시할 아이콘
- summary : 간단한 설명
- summaryOff : off 상태일 때 표시할 설명, summary 속성은 무시된다.
- summaryOn : on 상태일 때 표시할 설명, summary 속성은 무시된다

### ListPreference
- 항목들 중에서 하나만 선택할 수 있는 입력 요소
- defaultValue : 기본값. 최초에 이 값이 저장되어 있는 상태가 된다. (entryValues 에 설정한 값중 하나를 넣어준다)
- entries : 리스트를 구성할 때 사용할 배열 데이터를 지정한다. (res/values 폴더에 배열 요소로 값을 정의한다.)
- entryValues : 사용자가 항목을 선택했을때 저장될 데이터를 지정한다. (res/values 폴더에 배열 요소로 값을 정의한다.)
- key : 값을 가져올 때 사용할 이름
- title : 표시되는 타이틀
- icon : 표시할 아이콘
- dialogIcon : 다이얼로그에 표시할 아이콘
- dialogTitle : 다이얼로그에 표시할 타이틀
- summary : 간단한 설명

### MultiSelectListPreference
- 항목들 중에서 0개 이상을 선택할 수 있는 입력 요소
- defaultValue : 기본값. 최초에 이 값이 저장되어 있는 상태가 된다. (entryValues 에 설정한 값 중 원하는 만큼의 값의 설정되어 있는 배열 요소를 지정한다)
- entries : 리스트를 구성할 때 사용할 배열 데이터를 지정한다. (res/values 폴더에 배열 요소로 값을 정의한다.)
- entryValues : 사용자가 항목을 선택했을때 저장될 데이터를 지정한다. (res/values 폴더에 배열 요소로 값을 정의한다.)
- key : 값을 가져올 때 사용할 이름
- title : 표시되는 타이틀
- icon : 표시할 아이콘
- dialogIcon : 다이얼로그에 표시할 아이콘
- dialogTitle : 다이얼로그에 표시할 타이틀
- summary : 간단한 설명