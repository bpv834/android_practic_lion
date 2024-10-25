# View들이 가지고 있는 주요 공통 속성

1. id
- View를 지칭하기 위해서 사용하는 이름


2. layout_width
- View의 가로 길이를 설정한다.
- 설정값 : match_parent, wrap_content, dp단위의 크기
- match_parent : View가 배치되어 있는 Layout 가로길이 꽉 채우겠다는 의미
- wrap_content : 자신을 완벽하게 구성할 수 있는 최소 사이즈라는 의미
- dp 단위의 크기 : 개발자가 크기를 강제로 설정한다

3. layout_height
- View의 세로 길이를 설정한다.
- 설정값 : match_parent, wrap_content, dp단위의 크기
- match_parent : View가 배치되어 있는 Layout 세로길이 꽉 채우겠다는 의미
- wrap_content : 자신을 완벽하게 구성할 수 있는 최소 사이즈라는 의미
- dp 단위의 크기 : 개발자가 크기를 강제로 설정한다

4. layout_margin
- View의 외부 여백을 설정한다.
- layout_margin : 상하좌우 여백을 동일하게 설정한다
- layout_marginStart(layout_marginLeft) : 좌측의 여백을 설정
- layout_marginEnd(layout_marginRight) : 우측의 여백을 설정
- layout_marginTop : 상단의 여백을 설정
- layout_marginBottom : 하단의 여백을 설정
- 설정값은 dp 단위의 크기

5. layout_gravity
- Layout 내에서 View가 배치되는 위치를 설정한다.

6. padding
- View 내부의 여백을 설정한다
- padding : 상하좌우 내부 여백을 동일하게 설정한다
- paddingStart(paddingLeft) : 좌측 내부 여백을 설정한다
- paddingEnd(paddingRight) : 우측 내부 여백을 설정한다
- paddingTop : 상단 내부 여백을 설정한다
- paddingBottom : 하단 내부 여백을 설정한다
- 설정값은 dp 단위의 크기

7. background
- View의 배경을 지정한다.
- 이미지나 색상을 지정할 수 있다.