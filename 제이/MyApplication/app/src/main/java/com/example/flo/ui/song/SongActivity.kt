package com.example.flo.ui.song

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.R
import com.example.flo.data.entities.Song
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding
//    lateinit var song : Song
    lateinit var timer: Timer
    private var mediaPlayer : MediaPlayer? = null
    private var gson:Gson = Gson()

    //song DB 관련 전역변수 설정 및 초기화
    val songs = arrayListOf<Song>()
    lateinit var songDB : SongDatabase
    var nowPos = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initPlayList()
        initSong()
        initClickListener()
//        setPlayer(songs[nowPos])
//        binding.songDownIb.setOnClickListener { finish() }
//
////        binding.songMiniplayerIv.setOnClickListener { setViewStatus(binding.songMiniplayerIv,binding.songPauseIv, true) }
////        binding.songPauseIv.setOnClickListener { setViewStatus(binding.songPauseIv, binding.songMiniplayerIv,false) }
//
//        binding.songMiniplayerIv.setOnClickListener{
//            setPlayerStatus(true)
//        }
//        binding.songPauseIv.setOnClickListener{
//            setPlayerStatus(false)
//        }

//        binding.songLikeIv.setOnClickListener { setViewStatus(binding.songLikeIv, binding.songLikeIvOn,false) }
//        binding.songLikeIvOn.setOnClickListener { setViewStatus(binding.songLikeIvOn, binding.songLikeIv,true) }
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
    //사용자가 포커스를 잃었을 때 음악을 중지
    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        songs[nowPos].second = ((binding.songProgressSb.progress * songs[nowPos].playTime)/100)/1000
        //뮤직앱이 꺼져있어도 저장이 플레이어 바가 저장되어야 되니까 sharedPreferences 객체이용
        //sharedPreference는 뭐냐? -> 내부 저장소에 데이터를 저장할 수 있게 해주는 것으로, 앱이 종료되었다가
        //다시 실행해도 저장된 데이터를 실행해서 사용할 수 있게 해주는 것.
        //간단한 설정값을 저장하는 용도로 사용. 사용법은 editor로 사용해줘야함
        var sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        var editor = sharedPreferences.edit() //에디터
        editor.putInt("songId",songs[nowPos].id)

        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release() //미디어 플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null //미디어 플레이어 해제
    }

    private fun initPlayList(){
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }


    //모든 버튼 클릭 리스너들 초기화 해주는 함수
    private fun initClickListener(){
        binding.songDownIb.setOnClickListener{
            finish()
        }
        binding.songMiniplayerIv.setOnClickListener{
            setPlayerStatus(true)
        }
        binding.songPauseIv.setOnClickListener{
            setPlayerStatus(false)
        }
        binding.songNextIv.setOnClickListener {
            moveSong(+1)
        }
        binding.songPreviousIv.setOnClickListener {
            moveSong(-1)
        }
        binding.songLikeIv.setOnClickListener{
            setLike(songs[nowPos].isLike)
        }
    }
    private fun initSong(){
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId",0)

        nowPos = getPlayingSongPosition(songId)

        Log.d("now Song ID",songs[nowPos].id.toString())
        startTimer()
        setPlayer(songs[nowPos])
    }
    //songs 객체를 업데이트하고, DB에 반영함
    private fun setLike(isLike: Boolean){
        //우리가 만들어 놓은 songs 객체에 좋아요 표시를 받는 공간이 있으므로, 업데이트 시킴
        songs[nowPos].isLike = !isLike
        songDB.songDao().updateIsLikeById(!isLike,songs[nowPos].id)

        if(!isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.songLikeIv.setImageResource((R.drawable.ic_my_like_off))
        }
    }

    //+1이 들어오면 다음 곡, -1이 들어오면 이전 곡을 재생하게 만드는 함수
    private fun moveSong(direct: Int){
        if(nowPos+direct<0){// 지금 현재 곡의 위치를 나타내는 nowPos의 값과 direct를 더한 값이 0보다 작으면 이전곡이 없다
//            CustomSnackbar.make(binding.root,"처음 곡입니다.").show()
            Toast.makeText(this,"first song",Toast.LENGTH_SHORT).show()
            return
        }
        if(nowPos+direct>=songs.size){
//            CustomSnackbar.make(binding.root,"마지막 곡입니다.").show()
            Toast.makeText(this,"last song",Toast.LENGTH_SHORT).show()
            return
        }

        nowPos +=direct

        timer.interrupt()
        startTimer()

        mediaPlayer?.release()
        mediaPlayer = null

        setPlayer(songs[nowPos])
    }

    //인수로 position을 찾고싶은 id 값을 넣어서 primarykey인 idx가 몇인지 찾는 함수
    private fun getPlayingSongPosition(songId: Int) : Int{
        for(i in 0 until songs.size){
            if(songs[i].id == songId){
                return i
            }
        }
        return 0
    }


    private fun setPlayerStatus(isPlaying: Boolean) {
        songs[nowPos].isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start()
        }
        else{
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            if(mediaPlayer?.isPlaying == true){
                mediaPlayer?.pause()
            }
        }
    }

    private fun startTimer(){
        timer = Timer(songs[nowPos].playTime, songs[nowPos].isPlaying)
        timer.start()
    }
    private fun setPlayer(song : Song){
        binding.songMusicTitleTv.text = song.title
        binding.songSingerNameTv.text = song.singer
        binding.songStartTimeTv.text=String.format("%02d:%02d",song.second/60,song.second%60)
        binding.songEndTimeTv.text=String.format("%02d:%02d",song.playTime/60,song.playTime%60)
        binding.songAlbumIv.setImageResource(song.coverImg!!)
        binding.songProgressSb.progress=(song.second*1000/song.playTime)

        val music = resources.getIdentifier(song.music,"raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this,music)


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
                        if (binding.songRepeatIvOn.visibility == View.VISIBLE) {
                            mediaPlayer?.seekTo(0)// 이게 노래를 0초로 돌려주는 역할을 함.
                            mediaPlayer?.start()
                            timer = Timer(songs[nowPos].playTime, true)
                            timer.start()
                            //위에서 초기화를 하고 타이머객체까지 다시 시작했으니까 play 버튼도 눌리도록 만들자.
                            runOnUiThread{
                                setPlayerStatus(true)
                            }
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