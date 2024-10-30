package com.example.a038ex_ex

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Display.Mode
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a038ex_ex.databinding.ActivityMainBinding
import com.example.a038ex_ex.databinding.CustomDialogBinding
import com.example.a038ex_ex.databinding.RowBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.divider.MaterialDividerItemDecoration

class MainActivity : AppCompatActivity() {
    // RecyclerView를 구성하기 위한 리스트
    val dataList = mutableListOf<ModelClass>()

    lateinit var activityMainBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //버전으로 분기하여 권한 설정
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            // 권한 목록

            // arrayOf()
            //정적 배열을 생성합니다.

            // arrayListOf()
            // 동적 배열 (리스트)를 생성합니다.
            //크기를 가변적으로 변경할 수 있어, 요소를 추가(add)하거나 제거(remove)할 수 있습니다.


            val permissionList = arrayOf(
                Manifest.permission.POST_NOTIFICATIONS
            )
            requestPermissions(permissionList, 0)

        }

        // Notification Channel 을 등록한다.
        addNotificationChannel("channel11","채널1")

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        settingShowDialog()
        settingRecyclerView()
    }

    private fun settingShowDialog() {
        activityMainBinding.apply {
            button1.setOnClickListener {
                val builder1 = MaterialAlertDialogBuilder(this@MainActivity)

                builder1.setTitle("커스텀 다이얼로그")
                builder1.setIcon(R.mipmap.ic_launcher_round)

                //설정할 View에 대한 작업을 한다.
                val customDialogBinding = CustomDialogBinding.inflate(layoutInflater)

                // View를 Dialog에 설정해준다.
                builder1.setView(customDialogBinding.root)

                //확인 버튼구현
                builder1.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                    val name = customDialogBinding.txtNameCustomDialog.editText?.text.toString()
                    val age =
                        customDialogBinding.txtAgeCustomDialog.editText?.text.toString().toInt()
                    val korScore =
                        customDialogBinding.txtKorScoreCustomDialog.editText?.text.toString()
                            .toInt()
                    val dataClass = ModelClass(name, age, korScore)
                    dataList.add(dataClass)
                    Log.d("data", dataClass.toString())
                    activityMainBinding.recyclerView1.adapter?.notifyDataSetChanged()

                }

                // 취소 버튼 구현
                builder1.setNegativeButton("취소", null)

                // view 띄우기
                builder1.show()
            }
        }
    }

    inner class RecyclerViewMainAdapter :
        RecyclerView.Adapter<RecyclerViewMainAdapter.RecyclerViewMainViewHolder>() {

        inner class RecyclerViewMainViewHolder(var rowBinding: RowBinding) :
            RecyclerView.ViewHolder(rowBinding.root), View.OnClickListener {
            override fun onClick(v: View?) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    // 애플리케이션이 POST_NOTIFICATIONS 권한을 가지고 있는지 확인하는 코드입니다
                    // 이 메서드는 특정 권한에 대해 앱이 현재 허용되어 있는지 검사합니다. 이 메서드는 PERMISSION_GRANTED 또는 PERMISSION_DENIED 값을 반환한다.
                    val check = checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                    if (check == PackageManager.PERMISSION_DENIED) {
                        return
                    }
                }
                // addNotificationChannel() 로 채널을 만들어준 후 생성된 채널의 id를 넘겨줌
                val builder = makeNotificationBuilder("channel11")
                builder.setSmallIcon(android.R.drawable.ic_menu_view)
                builder.setNumber(100)
                builder.setContentTitle("Content Title ")
                builder.setContentText("content Text")

                //알림을 누르면 자동으로 사라지게 한다.
                builder.setAutoCancel(true)

                //실행할 Activity를 실행할 수 있는 Notification의 Intent 생성

                val newIntent = Intent(this@MainActivity, ShowInfoActivity::class.java)

                val name = dataList[adapterPosition].name
                val age = dataList[adapterPosition].age
                val korScore = dataList[adapterPosition].korScore
                newIntent.putExtra("name",name)
                newIntent.putExtra("age",age)
                newIntent.putExtra("korScore",korScore)

                val pending1 = PendingIntent.getActivity(this@MainActivity, 10, newIntent, PendingIntent.FLAG_IMMUTABLE)

                builder.setContentIntent(pending1)

                val notification = builder.build()
                val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

                //ID가 100인 알림을 생성한 후, 같은 100 ID로 다시 알림을 호출하면 새롭게 추가되는 것이 아니라 기존 알림을 덮어씁니다.
                manager.notify(100,notification)
            }

        }

        // ViewHolder를 생성하는 메서드
        // 보여져야 하는 항목에 대한 ViewHolder 가 없을 경우 호출된다.
        // 재 사용 가능한 ViewHolder가 있으면 호출하지 않는다..
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RecyclerViewMainViewHolder {
            // recyclerView 와 xml 파일을 binding

            val rowBinding = RowBinding.inflate(layoutInflater)
            val recyclerViewMainViewHolder = RecyclerViewMainViewHolder(rowBinding)

            rowBinding.apply {
                // 터치 영역을 전체로 하기 위해 가로 세로 길이를 설정한다.
                root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                // root는 보통 뷰 바인딩을 통해 생성된 루트 뷰를 나타냅니다.
                // root는 주로 레이아웃 XML 파일의 최상위 레이아웃을 가리키며, 이를 통해 전체 뷰 계층에 접근할 수 있습니다.

                // onClick InterFace 와 viewHolder 를 연결
                root.setOnClickListener(recyclerViewMainViewHolder)
            }
            return recyclerViewMainViewHolder
        }

        // 항목의 전체 개수를 반환하는 메서드
        override fun getItemCount(): Int {
            if (dataList.size == 0) return 1
            return dataList.size
        }

        // 항목에 배치되어 있는 View에 값을 설정하여 준다.
        override fun onBindViewHolder(holder: RecyclerViewMainViewHolder, position: Int) {
            holder.rowBinding.apply {
                if (dataList.size == 0) {
                    textViewRow.text = " 등록된 데이터가 없습니다."
                } else {
                    textViewRow.text = dataList[position].name
                }

            }
        }
    }

    fun settingRecyclerView() {
        activityMainBinding.apply {
            //어뎁터 설정
            recyclerView1.adapter = RecyclerViewMainAdapter()
            //보여주는 방식 설정
            recyclerView1.layoutManager = LinearLayoutManager(this@MainActivity)
            //구분선 데코
            val deco = MaterialDividerItemDecoration(
                this@MainActivity,
                MaterialDividerItemDecoration.VERTICAL
            )
            recyclerView1.addItemDecoration(deco)
        }
    }

    // Notificaiton Channel을 등록하는 메서드
    // 첫 번째 : 코드에서 채널을 관리하기 위해 사용하는 이름
    // 두 번째 : 사용자에게 노출 시켜줄 이름
    fun addNotificationChannel(id: String, name: String) {
        // 안드로이드 8.0 이상일 때만 동작하게 한다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 알림 메시지를 관리하는 객체를 추출한다.
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            // 채널 이름을 통해 NotificationChannel 객체를 추출한다.
            var channel = manager.getNotificationChannel(id)
            // 만약 채널이 없다면(등록된 적이 없다면..)
            if (channel == null) {
                // 채널 객체를 생성한다.
                channel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)
                // 메시지 출력시 LED를 사용할 것인가(LED 없는 단말기 많습니다~)
                channel.enableLights(true)
                // LED 색상
                channel.lightColor = Color.RED
                // 진동을 사용할 것인가
                channel.enableVibration(true)
                // 등록한다.
                manager.createNotificationChannel(channel)
            }
        }
    }


    // Notification 메시지 관리 객체를 생성하는 메서드
    // 매개변수 : Notification 채널 이름
    //상위 버전이면 channel 이름을 취급한다.
    fun makeNotificationBuilder(channelName : String) : NotificationCompat.Builder{
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val builder = NotificationCompat.Builder(this@MainActivity, channelName)
            return builder
        }
        else{
            val builder = NotificationCompat.Builder(this@MainActivity)
            return builder
        }
    }

}