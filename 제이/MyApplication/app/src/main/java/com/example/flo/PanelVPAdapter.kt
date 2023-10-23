package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

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