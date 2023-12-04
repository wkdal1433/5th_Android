package com.example.flo.ui.main.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flo.ui.main.home.HomeFragmentPanel
import com.example.flo.ui.main.home.HomeFragmentPanel_2
import com.example.flo.ui.main.home.HomeFragmentPanel_3

class PanelVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3 // 페이지 개수

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragmentPanel()
            1 -> HomeFragmentPanel_2()
            2 -> HomeFragmentPanel_3()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}