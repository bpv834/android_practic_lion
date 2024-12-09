package com.lion.a02_lbs

import com.google.gson.annotations.SerializedName

data class RootClass(
    @SerializedName("results")
    var results:List<ResultsClass>,

    @SerializedName("status")
    var status:String
)

data class ResultsClass(
    @SerializedName("geometry")
    var geometry:GeometryClass,

    @SerializedName("icon")
    var icon:String,

    @SerializedName("name")
    var name:String,

    @SerializedName("vicinity")
    var vicinity:String
)

data class GeometryClass(
    @SerializedName("location")
    var location:LocationClass
)

data class LocationClass(
    @SerializedName("lat")
    var lat:Double,

    @SerializedName("lng")
    var lng:Double
)

//{
//    "html_attributions": [],
//    "next_page_token": "AdDdOWr4M2JnY_pVuNsQAgH_mvTvgBy-jYv2vc_2XwRztsC1YP9i7KEX2ABCmPA-7HIFUF_Wh2UFYSgCqnteRYjcjHBeY6Q0oUaSmWasZucj64-MVFFuJvnfgYt_vsH_EsuJ_n3fVpLNOlGrY0-uWj2E3PjbjjWYfGzVGXo_5AsAQi4gJGO_c9ls7vXIW1DmtQz3vdxqBz7P9ZGD1RXwIChg4HuCUjAwSIgbsTV_68pQZdyJkRCIn43kjCtasNqJeFNn7qRonT8Q39-oFSAdrgrDPEt7bxPOfST9pUTMfZm0c_FL4dABY1YGlZlNvsgxEl7mmnwA5cn-fVNbPvCm7n5d_dUIwiBlh76LbXFch261cdQkgjpeBQcAGkTdWRLOhlZ9Kfm30xSGjgBOEif9kV4HI6unLbUTlsWPH0QC2u9HqsEUliUz",
//    "results": [
// {
//            "business_status": "OPERATIONAL",
//            "geometry": {
//                "location": {
//                    "lat": 37.4957641,
//                    "lng": 126.8887858
//                },
//                "viewport": {
//                    "northeast": {
//                        "lat": 37.49711308029151,
//                        "lng": 126.8901347802915
//                    },
//                    "southwest": {
//                        "lat": 37.49441511970851,
//                        "lng": 126.8874368197085
//                    }
//                }
//            },
//            "icon": "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/bank-71.png",
//            "icon_background_color": "#909CE1",
//            "icon_mask_base_uri": "https://maps.gstatic.com/mapfiles/place_api/icons/v2/bank-intl_pinlet",
//            "name": "신한타워",
//            "opening_hours": {
//                "open_now": true
//            },
//            "place_id": "ChIJcywsbxWefDUR1m0K6x0zckM",
//            "plus_code": {
//                "compound_code": "FVWQ+8G 대한민국 서울특별시",
//                "global_code": "8Q98FVWQ+8G"
//            },
//            "rating": 5,
//            "reference": "ChIJcywsbxWefDUR1m0K6x0zckM",
//            "scope": "GOOGLE",
//            "types": [
//                "bank",
//                "point_of_interest",
//                "finance",
//                "establishment"
//            ],
//            "user_ratings_total": 1,
//            "vicinity": "구로구 구로동 100-8"
//        },
//           "status": "OK"
//      }