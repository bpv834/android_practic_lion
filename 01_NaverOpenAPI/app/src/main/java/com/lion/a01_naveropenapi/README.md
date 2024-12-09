# ViewBinding 셋팅

### build.gradle.kts 파일에 작성한다.

```kt
    viewBinding{
    enable = true
}
```

### ViewBinding 기본 코드를 작성한다.

[MainActivity.kt]
```kt
    lateinit var activityMainBinding: ActivityMainBinding

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(activityMainBinding.root)
```


---

# 화면 구성

### activity_main.xml 화면 구성

- 화면디자인.txt의 [activity_main.xml] 참고

### row.xml 화면 구성

- 화면디자인.txt의 [row.xml] 참고


---

# 기능 구현

### 임시 데이터를 정의해준다.

```kt
    // 임시 데이터
val textList = Array(30){
    "검색결과 ${it + 1}"
}
val linkList = Array(30){
    "https://likelion.net"
}
```

### RecyclerView의 어뎁터를 구현해준다.

[MainActivity.kt]

```kt
    // RecyclerView의 어뎁터
inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>(){
    // ViewHolder
    inner class ViewHolderClass(val rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val rowBinding = RowBinding.inflate(layoutInflater, parent, false)
        val viewHolderClass = ViewHolderClass(rowBinding)
        return viewHolderClass
    }

    override fun getItemCount(): Int {
        return textList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        holder.rowBinding.textViewRow.text = textList[position]
    }
}
```

### RecyclerView를 구성하는 메서드를 구현해준다.

```kt
    // RecyclerView를 구성하는 메서드
fun settingRecyclerView(){
    activityMainBinding.apply {
        // 어뎁터
        recyclerViewResult.adapter = RecyclerViewAdapter()
        // layoutManager
        recyclerViewResult.layoutManager = LinearLayoutManager(this@MainActivity)
        // 데코레이션
        val deco = MaterialDividerItemDecoration(this@MainActivity, MaterialDividerItemDecoration.VERTICAL)
        recyclerViewResult.addItemDecoration(deco)
    }
}
```

### 메서드를 호출한다.

[MainActivity.kt - onCreate()]

```kt
        // RecyclerView를 구성하는 메서드를 호출한다.
settingRecyclerView()
```

### 항목을 누르면 웹 사이트가 뜨도록 한다.

[MainActivity - RecyclerViewAdapter]
```kt
        // ViewHolder
inner class ViewHolderClass(val rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root), OnClickListener{
    override fun onClick(v: View?) {
        // 보여줄 주소
        val uri = Uri.parse(linkList[adapterPosition])
        // Activity를 실행한다.
        val newIntent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(newIntent)
    }
}
```

### 리스너를 설정해준다.

[MainActivity - RecyclerViewAdapter - onCreateViewHolder()]
```kt
            // 리스너 설정
rowBinding.root.setOnClickListener(viewHolderClass)
```

---

# 네이버 open api 확인

### 요청할 사이트 주소
- https://openapi.naver.com/v1/search/news.xml
- https://openapi.naver.com/v1/search/news.json

### 요청할 때 클라이언트가 전달해야 하는 데이터(파라미터)
- query : 검색어
- display : 한번에 가져올 데이터의 개수 (최대 100)

### 요청할 때 클라이언트가 전달해야 하는 데이터(헤더)
- X-Naver-Client_Id : 클라이언트 아이디
- X-Naver-Client-Secret : 클라이언트 시크릿

### 서버가 전달하는 데이터의 양식은 무엇인지
- xml, json

### 서버가 전달하는 데이터는 어떠한 것들이 있는지

---

# 서버와의 연동 구현

### 입력 요소에서 리턴키를 눌렀을 때의 리스너를 구현해준다.

```kt
    // textField를 구성하는 메서드
fun settingTextField(){
    activityMainBinding.apply {
        textFieldKeyword.editText?.setOnEditorActionListener { v, actionId, event ->
            // 사용자가 입력한 내용을 가져온다.
            val keyword = textFieldKeyword.editText?.text.toString()

            true
        }
        // 키보드를 올려준다.
        showSoftInput(textFieldKeyword.editText!!)
    }
}
```

### 통신 처리를 할 메서드를 작성해준다.

```kt
    // 기본 HTTP 통신 사용 메서드
    fun getNaverDataUsingBasing(keyword:String){

    }
    // okHttp3 사용 메서드
    fun getNaverDataUsingOkHttp3(keyword:String){

    }
    // Retrofit 사용 메서드
    fun getNaverDataUsingRetrofit(keyword: String){
        
    }
```

### 키보드를 관리하는 메서드들을 구현해준다.

```kt
    // 키보드 올리는 메서드
    fun showSoftInput(view:View){
        // 입력을 관리하는 매니저
        val inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        thread {
            SystemClock.sleep(300)
            // 포커스를 준다.
            view.requestFocus()
            // 키보드를 올린다.
            inputManager.showSoftInput(view, 0)
        }
    }
    // 키보드를 내리는 메서드
    fun hideSoftInput(){
        // 포커스가 있는 뷰가 있다면
        if(currentFocus != null){
            // 입력을 관리하는 매니저
            val inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            // 키보드를 내린다.
            inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            // 포커스를 해제한다.
            currentFocus?.clearFocus()
        }
    }
```

### 검색어를 기반으로 일반 http 통신 메서드를 호출한다.

[MainActivity.kt - settingTextField()]
```kt
                // 사용자가 입력한 내용을 가져온다.
                val keyword = textFieldKeyword.editText?.text.toString()

                // 검색어를 기반으로 하여 데이터를 가져온다.
                getNaverDataUsingBasing(keyword)

                // 포커스를 해제하고 키보드를 내린다.
                hideSoftInput()
```

### 데이터를 담을 리스트를 선언해준다.

```kt
    // 임시 데이터
//    val textList = Array(30){
//        "검색결과 ${it + 1}"
//    }
//    val linkList = Array(30){
//        "https://likelion.net"
//    }

    val textList = mutableListOf<String>()
    val linkList = mutableListOf<String>()
```

### AndroidManifest.xml 에 권한을 추가한다.

```xml
    <uses-permission android:name="android.permission.INTERNET"/>
```

### 기본 HTTP 통신을 통해 데이터를 가져오는 메서드를 구현한다.

```kt

    // 기본 HTTP 통신 사용 메서드 (쓰래드)
    fun getNaverDataUsingBasing(keyword:String){
        thread {
            // 파라미터 데이터
            // UTF-8 타입으로 인코딩한다.(한글일 경우에만..)
            val query = URLEncoder.encode(keyword, "UTF-8")
            val display = "100"
            val params = "query=${query}&display=${display}"
            // 접속할 사이트의 주소
            val url = URL("https://openapi.naver.com/v1/search/news.json?${params}")
            // HttpURLConnection 객체를 추출한다.
            val connection = url.openConnection() as HttpURLConnection
            // 요청 방식
            connection.requestMethod = "GET"
            // 헤더 정보
            connection.setRequestProperty("X-Naver-Client-Id", "RJAEj7h70LypE4AT0vA4")
            connection.setRequestProperty("X-Naver-Client-Secret", "9YL9QYGi61")
            // 접속한다
            val inputStreamReader = InputStreamReader(connection.inputStream, "UTF-8")
            val bufferedReader = BufferedReader(inputStreamReader)
            // 데이터를 받아온다.
            val resultData = bufferedReader.readLines()
            // Log.d("test100", resultData.toString())
            val jsonString = resultData.joinToString(separator = " ")

            // 서버와의 연결을 해제한다.
            bufferedReader.close()

            // 전체가 { }로 묶여 있으므로 JSONObject를 생성한다.
            // 전체가 [ ]로 묶여 있으면 JSONArray이다.
            val jsonObjectRoot = JSONObject(jsonString)

            // items라는 이름으로 저장되어 있는 JSON 배열을 가져온다.
            val itemsArray = jsonObjectRoot.getJSONArray("items")
            // 배열안에 있는 JSONObject 수 만큼 반복한다.
            for(index in 0..< itemsArray.length()){
                // index 번째 json 객체를 가져온다.
                val itemsObject = itemsArray.getJSONObject(index)
                // title과 link를 추출한다.
                var title = itemsObject.getString("title")
                // 중간 중간에 있는 html 특수 문자를 변환한다.
                title = title.replace("&quot;", "\"")
                title = title.replace("<b>", "")
                title = title.replace("</b>", "")

                val link = itemsObject.getString("link")
                // Log.d("test100", title)
                // Log.d("test100", link)
                // 리스트에 데이터를 담는다.
                textList.add(title)
                linkList.add(link)
            }
            runOnUiThread {
                // 리사이클러뷰 갱신
                activityMainBinding.recyclerViewResult.adapter?.notifyDataSetChanged()
            }
        }
    }
```

### 메서드를 호출한다.

[MainActivity.kt - settingTextField()]
```kt
                // 검색어를 기반으로 하여 데이터를 가져온다.
                // getNaverDataUsingBasing(keyword)
```

### 코루틴을 이용하는 코드를 적용한 메서드를 구현한다.

```kt
    
    // 기본 HTTP 통신 사용 메서드 (코루틴)
    fun getNaverDataUsingBasing2(keyword:String){
        CoroutineScope(Dispatchers.Main).launch {
            val work1 = async(Dispatchers.IO){
                // 파라미터 데이터
                // UTF-8 타입으로 인코딩한다.(한글일 경우에만..)
                val query = URLEncoder.encode(keyword, "UTF-8")
                val display = "100"
                val params = "query=${query}&display=${display}"
                // 접속할 사이트의 주소
                val url = URL("https://openapi.naver.com/v1/search/news.json?${params}")
                // HttpURLConnection 객체를 추출한다.
                val connection = url.openConnection() as HttpURLConnection
                // 요청 방식
                connection.requestMethod = "GET"
                // 헤더 정보
                connection.setRequestProperty("X-Naver-Client-Id", "RJAEj7h70LypE4AT0vA4")
                connection.setRequestProperty("X-Naver-Client-Secret", "9YL9QYGi61")
                // 접속한다
                val inputStreamReader = InputStreamReader(connection.inputStream, "UTF-8")
                val bufferedReader = BufferedReader(inputStreamReader)
                // 데이터를 받아온다.
                val resultData = bufferedReader.readLines()
                // Log.d("test100", resultData.toString())
                val jsonString = resultData.joinToString(separator = " ")

                // 서버와의 연결을 해제한다.
                bufferedReader.close()

                // 전체가 { }로 묶여 있으므로 JSONObject를 생성한다.
                // 전체가 [ ]로 묶여 있으면 JSONArray이다.
                val jsonObjectRoot = JSONObject(jsonString)

                // items라는 이름으로 저장되어 있는 JSON 배열을 가져온다.
                val itemsArray = jsonObjectRoot.getJSONArray("items")
                // 배열안에 있는 JSONObject 수 만큼 반복한다.
                for(index in 0..< itemsArray.length()){
                    // index 번째 json 객체를 가져온다.
                    val itemsObject = itemsArray.getJSONObject(index)
                    // title과 link를 추출한다.
                    var title = itemsObject.getString("title")
                    // 중간 중간에 있는 html 특수 문자를 변환한다.
                    title = title.replace("&quot;", "\"")
                    title = title.replace("<b>", "")
                    title = title.replace("</b>", "")

                    val link = itemsObject.getString("link")
                    // Log.d("test100", title)
                    // Log.d("test100", link)
                    // 리스트에 데이터를 담는다.
                    textList.add(title)
                    linkList.add(link)
                }
            }
            work1.join()

            // 리사이클러뷰 갱신
            activityMainBinding.recyclerViewResult.adapter?.notifyDataSetChanged()
        }
    }
```

### 메서드를 호출한다.

[MainActivity.kt - settingTextField()]
```kt
                getNaverDataUsingBasing2(keyword)
```

---

# OkHttp3 라이브러리 사용

### build.gradle.kts 에 라이브러리를 추가해준다.

```kt
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
```

### 데이터를 가져오는 메서드를 구현해준다.

```kt
    // okHttp3 사용 메서드
    fun getNaverDataUsingOkHttp3(keyword:String){
        CoroutineScope(Dispatchers.Main).launch {
            val work1 = async(Dispatchers.IO){
                // 파라미터 데이터
                // UTF-8 타입으로 인코딩한다.(한글일 경우에만..)
                val query = URLEncoder.encode(keyword, "UTF-8")
                val display = "100"
                val params = "query=${query}&display=${display}"
                // 접속할 사이트의 주소
                val url = URL("https://openapi.naver.com/v1/search/news.json?${params}")
                // OkHttp 접속 객체를 생성한다.
                val okHttpClient = OkHttpClient()
                // 요청 정보 객체를 생성한다.
                val builder = Request.Builder().url(url).get()
                // 해더 정보 셋팅한다.
                builder.addHeader("X-Naver-Client-Id", "RJAEj7h70LypE4AT0vA4")
                builder.addHeader("X-Naver-Client-Secret", "9YL9QYGi61")
                //  요청한다.
                val request = builder.build()
                val response = okHttpClient.newCall(request).execute()

                if(response.isSuccessful){
                    // Log.d("test100", response.body?.string()!!)
                    val jsonObjectRoot = JSONObject(response.body?.string()!!)
                    val itemsArray = jsonObjectRoot.getJSONArray("items")
                    for(index in 0..< itemsArray.length()){
                        val itemsObject = itemsArray.getJSONObject(index)
                        var title = itemsObject.getString("title")
                        // 중간 중간에 있는 html 특수 문자를 변환한다.
                        title = title.replace("&quot;", "\"")
                        title = title.replace("<b>", "")
                        title = title.replace("</b>", "")

                        val link = itemsObject.getString("link")

                        textList.add(title)
                        linkList.add(link)
                    }
                }
            }
            work1.join()

            activityMainBinding.recyclerViewResult.adapter?.notifyDataSetChanged()
        }
    }
```

---

# Retrofit

### 라이브러리 추가

[build.gradle.kts]

```kt
implementation("com.squareup.retrofit2:retrofit:2.11.0")
implementation("com.squareup.retrofit2:converter-gson:2.11.0")
```

### JSON 데이터를 담을 클래스를 정의해준다.

```kt
// 받아온 데이터를 담을 클래스들
// @SerializedName(JSON 문서에서의 이름)
data class NaverClass(
    @SerializedName("items")
    var items:List<ItemClass>
)

data class ItemClass(
    @SerializedName("title")
    var title:String,

    @SerializedName("link")
    var link:String
)
```

### Interface를 구성해준다.
- 이 Interface에는 요청과 응답에 관련된 정보가 모두 담겨져 있다.

[RetorfitRequestInterface.kt]

```kt
interface RetrofitRequestInterface {

    // @GET : get 요청, @POST : post 요청, ( ) 안에 요청할 주소를 넣어준다.
    // 주소는 도메인 주소 빼고 나머지를 넣어주세요.
    // https://openapi.naver.com/v1/search/news.json 이기 때문에
    // 도메인 뺀 v1/search/news.json 만 넣어주었습니다~
    @GET("v1/search/news.json")
    // 메서드의 매개변수에 파라미터와 해더정보를 넣어준다.
    // @Header : 해더에 담을 정보
    // @Query : 파라미터 정보
    fun getNaverNewsData(
        @Header("X-Naver-Client-Id") clientId:String,
        @Header("X-Naver-Client-Secret") clientSecret:String,
        @Query("query") query:String,
        @Query("display") display:Int
    ) : Call<NaverClass>
    // 반환 타입 : Call<데이터를 담을 클래스>
}
```