package com.example.flo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding
import kotlin.concurrent.timer

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding
    lateinit var song : Song
    lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong()
        setPlayer(song)
        binding.songDownIb.setOnClickListener { finish() }

//        binding.songMiniplayerIv.setOnClickListener { setViewStatus(binding.songMiniplayerIv,binding.songPauseIv, true) }
//        binding.songPauseIv.setOnClickListener { setViewStatus(binding.songPauseIv, binding.songMiniplayerIv,false) }

        binding.songMiniplayerIv.setOnClickListener{
            setPlayerStatus(true)
        }
        binding.songPauseIv.setOnClickListener{
            setPlayerStatus(false)
        }

        binding.songLikeIv.setOnClickListener { setViewStatus(binding.songLikeIv, binding.songLikeIvOn,false) }
        binding.songLikeIvOn.setOnClickListener { setViewStatus(binding.songLikeIvOn, binding.songLikeIv,true) }
        binding.songRepeatIv.setOnClickListener { setViewStatus(binding.songRepeatIv, binding.songRepeatIvOn,false) }
        binding.songRepeatIvOn.setOnClickListener { setViewStatus(binding.songRepeatIvOn, binding.songRepeatIv,true) }
        binding.songRandomIv.setOnClickListener { setViewStatus(binding.songRandomIv, binding.songRandomIvOn,false) }
        binding.songRandomIvOn.setOnClickListener { setViewStatus(binding.songRandomIvOn, binding.songRandomIv,true) }
        binding.songUnlikeIv.setOnClickListener { setViewStatus(binding.songUnlikeIv, binding.songUnlikeIvOn,false) }
        binding.songUnlikeIvOn.setOnClickListener { setViewStatus(binding.songUnlikeIvOn, binding.songUnlikeIv,true) }


//        if (intent.hasExtra("title") && intent.hasExtra("singer")) {
//            binding.songMusicTitleTv.text = intent.getStringExtra("title")!!
//            binding.songSingerNameTv.text = intent.getStringExtra("singer")!!
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
    }

    private fun initSong(){
        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second",0),
                intent.getIntExtra("playTime",0),
                intent.getBooleanExtra("isPlaying",false)
            )
        }
        startTimer()
    }
    private fun setPlayerStatus(isPlaying: Boolean) {
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
        else{
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
    }

    private fun startTimer(){
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }
    private fun setPlayer(song : Song){
        binding.songMusicTitleTv.text = intent.getStringExtra("title")!!
        binding.songSingerNameTv.text = intent.getStringExtra("singer")!!
        binding.songStartTimeTv.text=String.format("%02d:%02d",song.second/60,song.second%60)
        binding.songEndTimeTv.text=String.format("%02d:%02d",song.playTime/60,song.playTime%60)
        binding.songProgressSb.progress=(song.second*1000/song.playTime)
        setPlayerStatus(song.isPlaying)

    }



    private fun setViewStatus(view: View, view2: View,isOn: Boolean) {
        if (isOn) {
            view.visibility = View.GONE
            view2.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
            view2.visibility = View.VISIBLE
        }
    }
    //자바에서 클래스 안에 클래스를 만들면 자동으로 innerclass로 분류 해서 inner class에서 외부 변수로 접근이 가능했는데
    //코틀린에서는 inner class라고 해주지 않으면 외부 변수로 접근이 불가능 하다.
    inner class Timer(private val playTime : Int,var isPlaying: Boolean=true):Thread(){
        private var second : Int = 0
        private var endtime : Int = playTime

        private var mills : Float = 0f

        override fun run() {
            super.run()
            try {
                while (true){
                    if(endtime<=0){
                        runOnUiThread {// 이렇게 runOnUiThread 즉 UI쓰레드에서 이 아래 함수를 돌리지 않으면 튕김.
                            //  setPlayerStatus(false) 호출 시, 현재 스레드가 아닌 UI 스레드에서 호출되어야 하는데,
                            //  setPlayerStatus 함수 내부에서 UI를 조작하려고 하고 있습니다.
                            //  이렇게 UI 조작은 메인 스레드에서만 가능합니다. 그래서 문제가 발생하는 것입니다.
                            setPlayerStatus(false)
                        }
                        break
                    }
                    if (isPlaying){
                        sleep(50)
                        mills += 50

                        runOnUiThread{
                            binding.songProgressSb.progress = ((mills/playTime)*100).toInt()
                        }
                        if(mills%1000 == 0f){
                            runOnUiThread{
                                binding.songStartTimeTv.text=String.format("%02d:%02d",second/60,second%60)
                                binding.songEndTimeTv.text = String.format("%02d:%02d",endtime/60,endtime%60)
                            }
                            second++
                            endtime--
                        }
                    }
                }
            }catch (e:InterruptedException){
                Log.d("Song","쓰레드가 죽었습니다. ${e.message}")
            }

        }
    }
}