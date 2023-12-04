package com.example.flo.ui.main.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flo.ui.main.home.HomeFragment
import com.example.flo.R
import com.example.flo.ui.song.SongDatabase
import com.example.flo.data.entities.Album
import com.example.flo.data.entities.Like
import com.example.flo.databinding.FragmentAlbumBinding
import com.example.flo.ui.main.MainActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class AlbumFragment : Fragment(){
    lateinit var binding : FragmentAlbumBinding
    private var gson: Gson = Gson()

    private val information = arrayListOf("수록곡", "상세 정보", "영상")

    private var isLiked : Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        val albumJson = arguments?.getString("album")
        val album = gson.fromJson(albumJson, Album::class.java)

        //Home에서 넘어온 데이터를 반영
        isLiked = isLikedAlbum(album.id)
        setInit(album)
        setOnClickListeners(album)

        binding.albumBackIv.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(
                R.id.main_frm,
                HomeFragment()
            ).commitAllowingStateLoss()

        }



        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter
        TabLayoutMediator(binding.albumContentTb,binding.albumContentVp){
            tab,position ->
            tab.text = information[position]
        }.attach()
        // TabLayoutMediator는 tablayout을 viewpager2와 연결하는 중재자임
        // 탭이 선택될 때 viewpager2의 위치를 tab과 동기화하고, viewpager2를 스크롤 할 때, tablayout의 스크롤 위치를 동기화 함
        // ViewPager2 설정
//        val viewPager2 = binding.albumContentVp
//        val fragmentList = listOf(SongFragment()) // SongFragment를 리스트에 추가
//        val adapter = FragmentAdapter(requireActivity(), fragmentList)
//        viewPager2.adapter = adapter
        return binding.root
//      AlbumFragment 클래스가 Fragment를 상속받고, 앨범 화면을 나타냅니다.
//      onCreateView 메서드에서 FragmentAlbumBinding을 초기화하고, 뷰의 이벤트 핸들러를 설정합니다.
//      ViewPager2를 초기화하고, 그 안에 SongFragment를 추가합니다. 이를 위해 FragmentAdapter 클래스를 사용합니다.
//      FragmentAdapter 클래스는 FragmentStateAdapter를 상속받아 ViewPager2에 표시할 프래그먼트들을 관리합니다.
    }



//    class FragmentAdapter(fragmentActivity: FragmentActivity, private val fragmentList: List<Fragment>) :
//        FragmentStateAdapter(fragmentActivity) {
//
//        override fun getItemCount(): Int = fragmentList.size
//
//        override fun createFragment(position: Int): Fragment = fragmentList[position]
//    }

    private fun setInit(album: Album){
        binding.albumAlbumIv.setImageResource(album.coverImg!!)
        binding.albumMusicTitleTv.text = album.title.toString()
        binding.albumSingerNameTv.text = album.singer.toString()
        if(isLiked){
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
        }else{binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)}
    }

    private fun getJwt():Int{
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("jwt",0)
    }

    private fun likeAlbum(userId : Int, albumId : Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val like = Like(userId,albumId)

        songDB.albumDao().likeAlbum(like)
    }
    private fun isLikedAlbum(albumId: Int):Boolean{
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()
        val likeId : Int? = songDB.albumDao().isLikedAlbum(userId,albumId)
        return likeId != null
    }
    private fun disLikedAlbum(albumId: Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()
        songDB.albumDao().disLikedAlbum(userId,albumId)
    }

    private fun setOnClickListeners(album : Album){
        val userId = getJwt()
        binding.albumLikeIv.setOnClickListener{
            if(isLiked){
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
                disLikedAlbum(album.id)
            }else{
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
                likeAlbum(userId,album.id)
            }
        }
    }

}