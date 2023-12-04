package com.example.flo.data.remote

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean ,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String,
    @SerializedName(value = "result") val result: Result?
//우리는 데이터클래스로 회원가입APi와 로그인 API를 같이 사용하고 있기 때문에 Result에 null이 올 수 있다고 해줘야
    //회원가입 처리를 할 수 있다.
)

data class Result(
    @SerializedName(value = "userIdx") var userIdx : Int,
    @SerializedName(value = "jwt") var jwt : String
)