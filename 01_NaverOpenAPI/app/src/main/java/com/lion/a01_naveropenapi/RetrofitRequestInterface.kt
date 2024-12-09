package com.lion.a01_naveropenapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

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