package com.example.flo.ui.main.look//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.ScrollView
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.flo.ui.main.album.AlbumLockerRVAdapter
//import com.example.flo.R
//import com.example.flo.ui.main.locker.SavedSongRVAdapter
//import com.example.flo.data.entities.Song
//import com.example.flo.SongDatabase
//import com.example.flo.databinding.FragmentLookBinding
//
//class LookFragment2 : Fragment() {
//    //FragmentLookBinding 변수 생성
//    lateinit var binding: FragmentLookBinding
//    //SongDatabase 변수 생성
//    private lateinit var songDB: SongDatabase
//    //버튼 변수 생성
//    private lateinit var chartBtn : Button
//    private lateinit var videoBtn : Button
//    private lateinit var genreBtn : Button
//    private lateinit var situationBtn : Button
//    private lateinit var audioBtn : Button
//    private lateinit var atmosphereBtn : Button
//    //버튼들을 담아줄 리스트 변수 생성
//    private lateinit var buttonList: List<Button>
//    //텍스트 뷰 변수 생성
//    private lateinit var chartTv : TextView
//    private lateinit var videoTv : TextView
//    private lateinit var genreTv : TextView
//    private lateinit var situationTv : TextView
//    private lateinit var audioTv : TextView
//    private lateinit var atmosphereTv : TextView
//    // 텍스트 뷰들을 담아 줄 리스트 변수 생성
//    private lateinit var textList: List<TextView>
//    // 스크롤 뷰 변수 생성
//    lateinit var scrollView : ScrollView
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        //binding 변수에 xml을 확장해주는,, 즉 코틀린 파일에 xml을 연결해 주는 것
//        //inflater는 XML을 메모리에 로드해주는 역할을 하는 확장자고, container는 부모 뷰 그룹을 의미하며 부모 뷰 그룹으로
//        //레이아웃이 첨부된다는 것을 의미합니다. false는 부모 뷰 그룹인 container에 레이아웃을 즉시 첨부하지 않겠다는 의미입니다.
//        binding = FragmentLookBinding.inflate(inflater, container, false)
//        //songDB 변수에 현재 fragment의 맥락을 가져오는 부분입니다.
//        //맥락을 가져온 다는 것은 앱의 리소스(문자열, 레이아웃, 이미지 등)에 접근할 수 있고, 시스템 서비스 호출,현재 앱의 테마와 스타일을
//        // 할 수 있는 등 여러가지를 할 수 있습니다.
//        songDB = SongDatabase.getInstance(requireContext())!!
//
//        // 스크롤 뷰 초기화(초기 값을 만들어둔 xml의 요소와 연결함)
//        scrollView = binding.lookSv
//
//        // 버튼 초기화
//        chartBtn = binding.lookChipTitle01Btn
//        videoBtn =  binding.lookChipTitle02Btn
//        genreBtn =  binding.lookChipTitle03Btn
//        situationBtn =  binding.lookChipTitle04Btn
//        atmosphereBtn =  binding.lookChipTitle05Btn
//        audioBtn =  binding.lookChipTitle06Btn
//
//        // 위에서 xml과 연결해준 버튼 객체들을 list로 담아줌.
//        // 이유는?-> 버튼들을 반복문을 통해 이벤트 리스너를 달아서 반응을 정해줄 예정..
//        buttonList = listOf(chartBtn, videoBtn, genreBtn, situationBtn, atmosphereBtn, audioBtn)
//
//        // 텍스트 초기화
//        chartTv = binding.lookSubTitleChartTv
//        videoTv = binding.lookSubTitleVideoTv
//        genreTv = binding.lookJenreTitleTv
//        situationTv = binding.lookSituationTitleTv
//        atmosphereTv = binding.lookMoodTitleTv
//        audioTv = binding.lookAudioTitleTv
//
//        //텍스트 뷰도 리스트로 담아둠-> 얘도 마찬가지로 위의 버튼뷰가 눌렸을 때 이 위치로 이동하기 위한 역할..)
//        textList = listOf(chartTv, videoTv, genreTv, situationTv, atmosphereTv, audioTv)
//
//
//        setButtonClickListeners()
//        initButtonScrollbind()
//        return binding.root
//    }
//
//    override fun onStart() {
//        // 부모 클래스의 onStart 메소드를 실행하는 부분-> 이걸 통해 알맞은 초기화를 실행시켜 줄 수 있음..
//        super.onStart()
//        //onStart메소드가 실행될 때 Recyclerview도 초기화 해서 유저에게 보여지기 전에 리사이클러 뷰를 구성할 수 있음.
//        initRecyclerview()
//    }
//
//    private fun initRecyclerview(){
//        //리사이클러뷰를 LinearLayout형식으로 만들어 주기.. 위아래로 스크롤 가능
//        val recyclerView = binding.lookFloChartRv
//        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
//        //이전에 사용했던 저장된 곡을 추가해주는 어댑터를 객체화 해주고,리사이클러 뷰와 연결..
//        val lookAlbumRVAdapter = SavedSongRVAdapter()
//        binding.lookFloChartRv.adapter = lookAlbumRVAdapter
//        //AllayList<Song>에 있는 모든 노래들을 Adapter를 통해 리사이클러뷰로 추가해준다.
//        lookAlbumRVAdapter.addSongs(songDB.songDao().getSongs() as ArrayList<Song>)
//    }
//
//    private fun setButtonClickListeners() {
//        //buttonList의 개수만큼 반복문을 돌아서 initbutton으로 이벤트를 달아주자
//        for (i in buttonList.indices) {
//            val button = buttonList[i]
//
//            button.setOnClickListener {
//                initButton(i)
//            }
//        }
//    }
//    private fun initButtonScrollbind(){
//        // 스크롤 뷰의 ViewTreeObserver를 사용하여 스크롤 이벤트를 감지
//        scrollView.viewTreeObserver.addOnScrollChangedListener {
//            // 현재 스크롤 위치를 가져오기
//            val scrollY = scrollView.scrollY
//            val imv = binding.lookAudioImg06Iv
//            // 각 버튼의 상단 위치를 기준으로 현재 스크롤 위치와 비교하여 색상 변경
//
//            for (i in textList.indices) {
//                val button = buttonList[i]
//                val text = textList[i]
//                if(i==5){
//                    if (scrollY >= text.top+8 && scrollY < imv.bottom) {
//                        // 현재 스크롤 위치가 해당 버튼에 대한 컨텐츠 부분에 있을 때
//                        button.setBackgroundResource(R.drawable.fragment_look_chip_on_background)
//                    } else {
//                        // 현재 스크롤 위치가 해당 버튼에 대한 컨텐츠 부분이 아닐 때
//                        button.setBackgroundResource(R.drawable.fragment_look_chip_off_background)
//                    }
//                }else{
//                    if (scrollY >= text.top+8 && scrollY < textList[i+1].top) {
//                        // 현재 스크롤 위치가 해당 버튼에 대한 컨텐츠 부분에 있을 때
//                        button.setBackgroundResource(R.drawable.fragment_look_chip_on_background)
//                    } else {
//                        // 현재 스크롤 위치가 해당 버튼에 대한 컨텐츠 부분이 아닐 때
//                        button.setBackgroundResource(R.drawable.fragment_look_chip_off_background)
//                    }
//                }
//            }
//        }
//    }
//
//    private fun initButton(idx: Int) {
//        for (i in buttonList.indices) {
//            val button = buttonList[i]
//            if (i == idx) {
//                // 버튼이 클릭된 경우 파란색으로 바꿔주자
//                button.setBackgroundResource(R.drawable.fragment_look_chip_on_background)
//            } else {
//                // 버튼이 클릭되지 않은 경우 색깔을 바꿔주자.
//                button.setBackgroundResource(R.drawable.fragment_look_chip_off_background)
//            }
//        }
//
//
//        //스크롤 뷰의 메소드인 smoothScrollTo를 통해 idx로 이동해주는 부분
//        scrollView.smoothScrollTo(0, textList[idx].top)
//    }
//}