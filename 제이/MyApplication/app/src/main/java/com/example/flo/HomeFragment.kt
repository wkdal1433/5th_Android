package com.example.flo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentHomeBinding
import me.relex.circleindicator.CircleIndicator
import me.relex.circleindicator.CircleIndicator3
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    private val timer = Timer()
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.homePannelAlbumImgIv1.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,AlbumFragment()).
                commitAllowingStateLoss()
        }
        binding.homePannelAlbumImgIv2.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,AlbumFragment())
                .commitAllowingStateLoss()
        }
        binding.homePannelAlbumImgIv3.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,AlbumFragment())
                .commitAllowingStateLoss()
        }
        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeview1.adapter = bannerAdapter
        binding.homeview1.orientation = ViewPager2.ORIENTATION_HORIZONTAL



        //fragment_home_panel
        val viewPager2: ViewPager2 = binding.homePannelBackgroundVp
        val indicator: CircleIndicator3 = binding.homePannelBackgroundIndicator

        val adapter = PanelVPAdapter(this)
        viewPager2.adapter = adapter
        indicator.setViewPager(viewPager2)
        startAutoSlide(adapter)


        return binding.root
    }

    private fun startAutoSlide(adapter : PanelVPAdapter) {
        // 일정 간격으로 슬라이드 변경 (3초마다)
        timer.scheduleAtFixedRate(3000, 3000) { // 이 메소드의 매개변수가 두개인 이유는 첫번째 지연시간/ 반복 지연시간 임.
            handler.post {
                val nextItem = binding.homePannelBackgroundVp.currentItem + 1
                if (nextItem < adapter.itemCount) {
                    binding.homePannelBackgroundVp.currentItem = nextItem //PanelVPAdapter에 있는 item 개수의 다음 번 fragment를 3초마다 옮겨감.
                } else {
                    binding.homePannelBackgroundVp.currentItem = 0 // 마지막 페이지에서 첫 페이지로 순환
                }
            }
        }
    }
}