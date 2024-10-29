### Activity 생명주기 메서드

1. onCreate
- Activity가 관리할 화면을 구성하기 위해 호출되는 메서드
- Activity가 처음 실행될 때
- Activity의 화면 회전이 발생했을 때

2. onStart
- Activity가 실행될 때 onCreate 다음으로 호출된다.
- Activity가 Stop 상태에서 다시 Running 상태가 될 때 onRestart 다음으로 호출된다.

3. onRestart
- Activity가 Stop 상태에서 다시 Running 상태가 될 때 호출된다.

4. onResume
- Activity가 실행될 때 onStart 다음에 호출된다.
- Activity가 Stop 상태에서 다시 Running 상태가 될 때 onStart 다음에 호출된다.
- Activity가 Pause 상태에서 다시 Running 상태가 될 때 호출된다.

5. onPause
- Activity가 Running 상태에서 Pause 상태 될 때 호출된다.

6. onStop
- Activity가 Running 상태에서 Stop 상태 될 때 onPause 다음에 호출된다.

7. onDestroy
- Activity가 종료되어 소멸될 때 onStop 다음에 호출된다.

---

### Activity 상태
- Running 상태 : Activity가 눈에 보이면서 동작을 하는 상태를 의미한다.
- Pause 상태 : Activity 위에 무언가 나타나서 Activity가 일시 정지되는 상태
- Stop 상태 : Activity위 다른 Activity가 실행되어 완전히 안보이거나 홈 화면으로 나갔을 때의 상태
- Destroy 상태 : Activity가 완전히 종료되어 메모리상에서 소멸되는 상태를 의미한다.

### 상태 변환
- Activity가 처음 실행될 때 : onCreate -> onStart -> onResume -> Running 상태
- Activity위에 작은 무언가가가 떳을 때 : onPause -> Pause 상태
- Pause 상태에서 다시 보여질 때 : onResume -> Running 상태
- Activity가 더이상 보이지 않을 때 : onStop -> onPause -> Stop 상태
- Stop 상태에서 다시 보여질 때 : onRestart -> onStart -> onResult -> Running 상태
- Activity가 완전히 종료될 때 : onStop -> onPause -> onDestroy -> 소멸

### 구현해야할 기능들
1. onCreate
- 화면을 처음 생성하거나 회전시 보여줄 화면을 구성하는 작업을 한다.

2. onDestroy
- 애플리케이션에서 사용한 다양한 데이터들 혹은 자원들을 모두 메모리에서 소멸 시켜주는 작업을 한다.
- Java는 프로그램이 종료되면 사용한 모든 메모리 자원을 알아서 반납한다.
- 네트워크 연결 종료, 파일 닫기 등등의 작업을 하시면 된다.

3. onResume, onStart, onRestart
- Activity가 Running 상태가 되기전에 동작해야 하는 코드를 구현해준다.
- 각 호출되는 시점에 따라서 코드를 잘 넣어줘야 한다.
- onCreate에 구현해야 하는 코드가 아니라면 onResume에 구현을 한다.

4. onPause, onStop
- Activity가 Running 상태에서 정지 상태가 될때 동작해야 하는 코드를 구현해준다.
- 각 호출되는 시점에 따라서 코드를 잘 넣어줘야 한다.
- onDestroy에 구현해야 하는 코드가 아니라면 onPause에 구현한다.