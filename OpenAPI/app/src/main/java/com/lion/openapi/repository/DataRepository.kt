package com.lion.openapi.repository

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class DataRepository {
    companion object {
        suspend fun getOpenApiData(sidoName: String): Response<RootApiClass> {
            val builder = Retrofit.Builder()
            val gson = GsonBuilder().setLenient().create()

            builder.baseUrl("https://apis.data.go.kr/")
            builder.addConverterFactory(GsonConverterFactory.create(gson))
            val retrofit = builder.build()

            val service = retrofit.create(RetrofitRequestInterface::class.java)
            // 데이터를 받아온다.
            val response = service.getOpenApiData(
                "qe8EY3d1ixNdu4/lC0aXJXbTH/VndGcoj5DABUigtfSCLIIP48IHXbwMEkG5gkGvVW/wKl1XuuFyqYwwWQZJDg==",
                "json",
                "1000",
                "1",
                sidoName,
                "1.0"
            ).execute()

            return response
        }

    }

}

interface RetrofitRequestInterface {

    // @GET : get 요청, @POST : post 요청, ( ) 안에 요청할 주소를 넣어준다.
    @GET("B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty")
    // 메서드의 매개변수에 파라미터와 해더정보를 넣어준다.
    fun getOpenApiData(
        @Query("serviceKey") serviceKey: String,
        @Query("returnType") returnType: String,
        @Query("numOfRows") numOfRows: String,
        @Query("pageNo") pageNo: String,
        @Query("sidoName") sidoName: String,
        @Query("ver") ver: String
    ): Call<RootApiClass>
    // 반환 타입 : Call<데이터를 담을 클래스>
}

// 서버에서 받아온 데이터를 담을 클래스
data class RootApiClass(
    @SerializedName("response")
    var responseApiClass: ResponseApiClass
)

data class ResponseApiClass(
    @SerializedName("body")
    var bodyApiClass: BodyApiClass,

    @SerializedName("header")
    var headerApiClass: HeaderApiClass
)

data class HeaderApiClass(
    @SerializedName("resultMsg")
    var resultMsg: String,

    @SerializedName("resultCode")
    var resultCode: String
)

data class BodyApiClass(
    @SerializedName("items")
    var itemsApiList: List<ItemsApiClass>
)

data class ItemsApiClass(
    @SerializedName("so2Grade")
    var so2Grade: String?,

    @SerializedName("coFlag")
    var coFlag: String?,

    @SerializedName("khaiValue")
    var khaiValue: String?,

    @SerializedName("so2Value")
    var so2Value: String?,

    @SerializedName("coValue")
    var coValue: String?,

    @SerializedName("pm25Flag")
    var pm25Flag: String?,

    @SerializedName("pm10Flag")
    var pm10Flag: String?,

    @SerializedName("o3Grade")
    var o3Grade: String?,

    @SerializedName("pm10Value")
    var pm10Value: String?,

    @SerializedName("khaiGrade")
    var khaiGrade: String?,

    @SerializedName("pm25Value")
    var pm25Value: String?,

    @SerializedName("sidoName")
    var sidoName: String?,

    @SerializedName("no2Flag")
    var no2Flag: String?,

    @SerializedName("no2Grade")
    var no2Grade: String?,

    @SerializedName("o3Flag")
    var o3Flag: String?,

    @SerializedName("pm25Grade")
    var pm25Grade: String?,

    @SerializedName("so2Flag")
    var so2Flag: String?,

    @SerializedName("dataTime")
    var dataTime: String?,

    @SerializedName("coGrade")
    var coGrade: String?,

    @SerializedName("no2Value")
    var no2Value: String?,

    @SerializedName("stationName")
    var stationName: String?,

    @SerializedName("pm10Grade")
    var pm10Grade: String?,

    @SerializedName("o3Value")
    var o3Value: String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(so2Grade)
        parcel.writeString(coFlag)
        parcel.writeString(khaiValue)
        parcel.writeString(so2Value)
        parcel.writeString(coValue)
        parcel.writeString(pm25Flag)
        parcel.writeString(pm10Flag)
        parcel.writeString(o3Grade)
        parcel.writeString(pm10Value)
        parcel.writeString(khaiGrade)
        parcel.writeString(pm25Value)
        parcel.writeString(sidoName)
        parcel.writeString(no2Flag)
        parcel.writeString(no2Grade)
        parcel.writeString(o3Flag)
        parcel.writeString(pm25Grade)
        parcel.writeString(so2Flag)
        parcel.writeString(dataTime)
        parcel.writeString(coGrade)
        parcel.writeString(no2Value)
        parcel.writeString(stationName)
        parcel.writeString(pm10Grade)
        parcel.writeString(o3Value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemsApiClass> {
        override fun createFromParcel(parcel: Parcel): ItemsApiClass {
            return ItemsApiClass(parcel)
        }

        override fun newArray(size: Int): Array<ItemsApiClass?> {
            return arrayOfNulls(size)
        }
    }
}