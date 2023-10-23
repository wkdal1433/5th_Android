package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class BannerVPAdapter (fragment: Fragment) :FragmentStateAdapter(fragment){

    private val fragmentlist : ArrayList<Fragment> = ArrayList()
    override fun getItemCount(): Int = fragmentlist.size

    override fun createFragment(position: Int): Fragment = fragmentlist[position]

    fun addFragment(fragment: Fragment){// homefragment에서 추가해줄 fragment를 써주기 위한 함수.
        fragmentlist.add(fragment)
        notifyItemInserted(fragmentlist.size-1)// viewpager에게 새로운 값이 추가되었으니 이것도 추가해서 보여줘 라는 뜻임
    }

}