package com.example.flo.ui.song

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentDetailBinding
import com.example.flo.databinding.FragmentSongBinding

class SongFragment : Fragment() {

    lateinit var binding: FragmentSongBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater,container,false)
        binding.songLalacLayout.setOnClickListener {
            Toast.makeText(activity,"Circles",Toast.LENGTH_SHORT).show()
        }
        binding.songMixoffTg.setOnClickListener{
            setMixStatus(false)
        }
        binding.songMixoffTgOn.setOnClickListener{
            setMixStatus(true)
        }
        return binding.root
    }

    fun setMixStatus(MixOff : Boolean){
        if(MixOff){
            binding.songMixoffTg.visibility = View.VISIBLE
            binding.songMixoffTgOn.visibility = View.GONE
        }
        else{
            binding.songMixoffTg.visibility = View.GONE
            binding.songMixoffTgOn.visibility = View.VISIBLE
        }
    }
}