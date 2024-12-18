package com.lion.firebasetestapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.lion.firebasetestapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        activityMainBinding.apply {
            button.setOnClickListener {
                // 저장할 데이터
                val t1 = TestDataClass(100, "문자열1", true)
                val t2 = TestDataClass(200, "문자열2", false)
                val t3 = TestDataClass(300, "문자열3", true)

                // firebase 객체를 생성한다.
                val database = FirebaseFirestore.getInstance()
                val collectionReference = database.collection("TestData")

                // 데이터를 저장한다.
                CoroutineScope(Dispatchers.Main).launch {
                    val work1 = async(Dispatchers.IO){
                        collectionReference.add(t1)
                    }
                    work1.join()

                    val work2 = async(Dispatchers.IO){
                        collectionReference.add(t2)
                    }
                    work2.join()

                    val work3 = async(Dispatchers.IO){
                        collectionReference.add(t3)
                    }
                    work3.join()

                    textView.text = "저장 완료"
                }
            }
            button2.setOnClickListener {
                // firebase 객체를 생성한다.
                val database = FirebaseFirestore.getInstance()
                val collectionReference = database.collection("TestData")

                textView.text = ""

                CoroutineScope(Dispatchers.Main).launch {
                    val work1 = async(Dispatchers.IO){
                        collectionReference.get().await()
                    }
                    val task = work1.await()

                    // 가져온 문서 수 만큼 반복한다.
                    task.documents.forEach {
                        // 데이터를 담을 객체로 변환한다.
                        val testDataClass = it.toObject(TestDataClass::class.java)

                        textView.append("data1 : ${testDataClass?.data1}\n")
                        textView.append("data2 : ${testDataClass?.data2}\n")
                        textView.append("data3 : ${testDataClass?.data3}\n\n")
                    }
                }
            }
        }
    }
}

class TestDataClass(var data1:Int, var data2:String, var data3:Boolean){
    constructor() : this(0, "", false)
}