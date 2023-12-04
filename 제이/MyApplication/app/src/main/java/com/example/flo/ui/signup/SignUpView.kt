package com.example.flo.ui.signup

interface SignUpView {
//SignUpView 클래스는 액티비티와 AuthService를 연결해주기 위한 것.
    fun onSignUpSuccess()
    fun onSignUpFailure()
}