package com.lion.a01_naveropenapi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.gson.annotations.SerializedName
import com.lion.a01_naveropenapi.databinding.ActivityMainBinding
import com.lion.a01_naveropenapi.databinding.RowBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // 임시 데이터
//    val textList = Array(30){
//        "검색결과 ${it + 1}"
//    }
//    val linkList = Array(30){
//        "https://likelion.net"
//    }

    val textList = mutableListOf<String>()
    val linkList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // RecyclerView를 구성하는 메서드를 호출한다.
        settingRecyclerView()
        // textField를 구성하는 메서드를 호출한다.
        settingTextField()
    }

    // textField를 구성하는 메서드
    fun settingTextField(){
        activityMainBinding.apply {
            textFieldKeyword.editText?.setOnEditorActionListener { v, actionId, event ->
                // 사용자가 입력한 내용을 가져온다.
                val keyword = textFieldKeyword.editText?.text.toString()

                // 검색어를 기반으로 하여 데이터를 가져온다.
                // getNaverDataUsingBasing(keyword)
                // getNaverDataUsingBasing2(keyword)
                // getNaverDataUsingOkHttp3(keyword)
                getNaverDataUsingRetrofit(keyword)

                // 포커스를 해제하고 키보드를 내린다.
                hideSoftInput()
                true
            }
            // 키보드를 올려준다.
            showSoftInput(textFieldKeyword.editText!!)
        }
    }

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

    // RecyclerView의 어뎁터
    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>(){
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

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowBinding = RowBinding.inflate(layoutInflater, parent, false)
            val viewHolderClass = ViewHolderClass(rowBinding)

            // 리스너 설정
            rowBinding.root.setOnClickListener(viewHolderClass)

            return viewHolderClass
        }

        override fun getItemCount(): Int {
            return textList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowBinding.textViewRow.text = textList[position]
        }
    }

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


    // Retrofit 사용 메서드
    fun getNaverDataUsingRetrofit(keyword: String){
        CoroutineScope(Dispatchers.Main).launch {
            val work1 = async(Dispatchers.IO){
                // Retrofit 객체 생성
                val builder = Retrofit.Builder()
                builder.baseUrl("https://openapi.naver.com/")

                // JSON 데이터 -> Kotlin 객체로 변환하기 위한 Gson 변환기 추가
                // Google에서 만든 JSON 데이터를 처리하기 위한 라이브러리입니다. fromJson, toJson 기능
                builder.addConverterFactory(GsonConverterFactory.create())
                val retrofit = builder.build()

                // Retrofit Service 객체를 생성한다.
                // 앞서 만든 요청과 응답에 대한 모든 정보가 담긴 Interface를 지정하여
                // 이 Interface를 구현한 클래스를 생성하게 하고 그 객체를 받아온다.

                // RetrofitRequestInterface::class -> Kotlin에서 클래스를 참조하는 표현입니다.
                // Retrofit은 Java 기반 라이브러리이므로 Java의 Class 타입이 필요합니다.
                val service = retrofit.create(RetrofitRequestInterface::class.java)

                // 서버로 부터 데이터를 받아온다.
                service.getNaverNewsData("RJAEj7h70LypE4AT0vA4", "9YL9QYGi61", keyword, 100).execute()
            }
            val response = work1.await()
            Log.d("test100", response.body().toString())
            val resultData = response.body()!!
            resultData.items.forEach {
                textList.add(it.title)
                linkList.add(it.link)
            }

            activityMainBinding.recyclerViewResult.adapter?.notifyDataSetChanged()
        }
    }

    // 키보드 올리는 메서드
    fun showSoftInput(view:View){
        // 입력을 관리하는 매니저
        val inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        // 포커스를 준다.
        view.requestFocus()

        thread {
            SystemClock.sleep(500)
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
}

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





