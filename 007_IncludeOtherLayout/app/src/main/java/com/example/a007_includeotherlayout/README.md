# Include Other Layout

- layout 폴더에 있는 xml 파일에서 다른 xml 파일의 내용을 불러다 사용할 수 있다.
- 다수의 화면에서 중복되는 부분이 있을 경우 한군데 만들어놓고 이를 불러다 사용할 수 있다.
- 중복된 부분을 최소화 하는데 도움이 된다
- 화면 하나에 있는 각 부분들을 따로 관리하기 위해서 사용하기도 한다.

### 주요 속성
- layout : 포함시킬 xml 파일을 지정하는 속성

### 운영 방식
- IncludeOtherLayout 에 id를 반드시 설정해야 한다.
- ViewBinding 객체 안에 IncludeOtherLayout에 설정한 id와 동일한 프로퍼티가 생성된다.
- 이 프로퍼티 안에는 포함되는 xml 파일을 관리할 수 있는 ViewBinding객체가 들어있다.