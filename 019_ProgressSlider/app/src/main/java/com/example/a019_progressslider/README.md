# Progress Indicator
- 현재 작업이 진행 중임을 표시하는 View
- Android에서 기본으로 제공하는 ProgressBar는 속성을 통해 바 형태나 원형 형태를 설정한다.
- Material에서 제공하는 Progress Indicator는 바 형태와 원형 형태를 따로 제공한다.
- LinearProgressIndicator : Bar 형태의 ProgressIndicator
- CircularProgressIndicator : 원 형태의 ProgressIndicator

### 주요 속성
- progress : 진행값 설정 (생략 : 0)
- max : 최대값 (생략 : 100)
- indeterminate : 진행 중임을 표시하는 용도로 사용한다

### 주요 프로퍼티
- progress : 현재 값을 설정하거나 가져온다

### 주요 메서드
- incrementProgressBy : 지정한 숫자만큼 증가시킨다

### 주요 리스너

---

# Slider
- 사용자가 지정된 범위 안에서 값을 설정할 수 있도록 제공되는 View

### 주요 속성
- value : 슬라이더의 초기값을 설정한다
- valueFrom : 최소값을 설정한다.
- valueTo : 최대값을 설정한다.
- stepSize : 설정할 수 있는 값의 단위. 이 속성을 설정하지 않으면 최소와 최대 범위안에서 자유롭게 설정이 가능하다
- labelBehavior : 값 조절시 상단에 나오는 말풍선을 어떻게 나타나게 할 것인가를 설정한다.

### 주요 프로퍼티
- value : Slider의 값을 가져오거나 설정한다

### 주요 메서드

### 주요 리스너
- addOnChangeListener : 값이 변경되었을 때

# RangeSlider
- 최소와 최대 범위를 지정할 수 있는 슬라이더

### 주요 속성
- valueFrom : 최소값을 설정한다.
- valueTo : 최대값을 설정한다.
- stepSize : 설정할 수 있는 값의 단위. 이 속성을 설정하지 않으면 최소와 최대 범위안에서 자유롭게 설정이 가능하다
- labelBehavior : 값 조절시 상단에 나오는 말풍선을 어떻게 나타나게 할 것인가를 설정한다.
- values : 지정한 범위의 최소와 최대를 지정한다. res/values 폴더의 xml 에 array를 정의한 다음 설정해줘야 한다.

### 주요 프로퍼티
- values : 최소와 최대값을 가져오거나 설정한다
- 