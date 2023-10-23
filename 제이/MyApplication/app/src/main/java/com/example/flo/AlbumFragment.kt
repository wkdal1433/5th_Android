package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flo.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator

class AlbumFragment : Fragment(){
    lateinit var binding : FragmentAlbumBinding

    private val information = arrayListOf("수록곡", "상세 정보", "영상")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        binding.albumBackIv.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,HomeFragment()).commitAllowingStateLoss()

        }

        binding.albumLikeIv.setOnClickListener{
            setLikeStatus(false)
        }
        binding.albumLikeIvOn.setOnClickListener{
            setLikeStatus(true)
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
    fun setLikeStatus(LikeOff : Boolean){
        if(LikeOff){
            binding.albumLikeIv.visibility = View.VISIBLE
            binding.albumLikeIvOn.visibility = View.GONE
        }
        else{
            binding.albumLikeIv.visibility = View.GONE
            binding.albumLikeIvOn.visibility = View.VISIBLE
        }
    }


    class FragmentAdapter(fragmentActivity: FragmentActivity, private val fragmentList: List<Fragment>) :
        FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int = fragmentList.size

        override fun createFragment(position: Int): Fragment = fragmentList[position]
    }
}