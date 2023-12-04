//package com.example.flo
//
//import android.view.LayoutInflater
//import android.view.View
//import androidx.core.content.ContextCompat
//import androidx.databinding.DataBindingUtil
//import com.example.flo.databinding.CustomSnackbarBinding
//import com.google.android.material.snackbar.Snackbar
//
//class CustomSnackbar(view : View, private val message: String) {
//
//    companion object{ // companion object는 자바에서 static 같은 느낌으로 정적인 객체를 부를 때 사용함.
//        //여기서는 make 함수를 정의해 놓고, make함수를 호출 할 경우 CustomSnackbar 인스턴스 반환함.
//        fun make(view:View,message: String)=CustomSnackbar(view, message)
//    }
//
//    private val context = view.context
//    //Snackbar 클래스는 코틀린에서 사용하는 간단한 메시지를 띄우는 클래스임.
//    //Snackbar.make의 메소드의 매개변수는 (스낵바가 표시될 뷰, 메시지, 띄울 시간)
//    private val snackbar = Snackbar.make(view,"",5000)
//    private val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
//
//    private val inflater = LayoutInflater.from(context)
//    private val snackbarBinding: CustomSnackbarBinding // 만들어 놓은 custom_snackbar.xml과 연결
//            = DataBindingUtil.inflate(inflater, R.layout.custom_snackbar, null, false)
//                //위 DataBindingUtil 클래스를 임포트 하려면 dependency에 databinding을 할 수 있도록 추가해 줘야함.
//    init {
//        initView()
//        initData()
//    }
//
//    private fun initView() {
//        with(snackbarLayout) {
//            removeAllViews()
//            setPadding(0, 0, 0, 0)
//            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
//            addView(snackbarBinding.root, 0)
//        }
//    }
//
//    private fun initData() {
//        snackbarBinding.customSnackbarTv.text = message
//        snackbarBinding.customSnackbarBtn.setOnClickListener {
//            // OK 버튼을 클릭했을 때 실행할 동작을 정의할 수 있다.
//        }
//    }
//
//    fun show() {
//        snackbar.show()
//    }
//
//
//
//
//}