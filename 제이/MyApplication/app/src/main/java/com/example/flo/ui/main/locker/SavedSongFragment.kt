package com.example.flo.ui.main.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.ui.song.SongDatabase
import com.example.flo.data.entities.Song
import com.example.flo.databinding.FragmentLockerSavedsongBinding

class SavedSongFragment : Fragment() {
    lateinit var binding: FragmentLockerSavedsongBinding
    lateinit var songDB : SongDatabase
//    private var albumDatas = ArrayList<SavedSong>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerSavedsongBinding.inflate(inflater,container,false)

        songDB = SongDatabase.getInstance(requireContext())!!
        return binding.root
//        prev()
    }

    override fun onStart(){
        super.onStart()
        initRecyclerView()
    }

    private fun initRecyclerView(){
        binding.lockerSavedSongRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        val songRVAdapter = SavedSongRVAdapter()

        songRVAdapter.setMyItemClickListener(object : SavedSongRVAdapter.MyItemClickListener {
            override fun onRemoveSong(songId: Int) {
                songDB.songDao().updateIsLikeById(false,songId)
            }
        })

        binding.lockerSavedSongRecyclerView.adapter = songRVAdapter

        songRVAdapter.addSongs(songDB.songDao().getLikedSongs(true) as ArrayList<Song>)
    }

//    private fun prev() {
        //        binding = FragmentLockerSavedsongBinding.inflate(inflater,container,false)
        //
        //        //데이터 리스트 생성 더미 데이터
        //        albumDatas.apply {
        //            add(SavedSong("Butter","방탄소년단 (BTS)" ,R.drawable.img_album_exp))
        //            add(SavedSong("Circles","Post Malone" ,R.drawable.img_album_art))
        //            add(SavedSong("Lilac","아이유 (IU)" ,R.drawable.img_album_exp2))
        //            add(SavedSong("Next Level","에스파 (AESPA)" , R.drawable.img_album_exp3))
        //            add(SavedSong("Boy with Luv","방탄소년단 (BTS)" ,R.drawable.img_album_exp4))
        //            add(SavedSong("BBoom BBoom","모모랜드 (MOMOLAND)" ,R.drawable.img_album_exp5))
        //            add(SavedSong("Weekend","태연 (Tae Yeon)" ,R.drawable.img_album_exp6))
        //            add(SavedSong("Butter","방탄소년단 (BTS)" ,R.drawable.img_album_exp))
        //            add(SavedSong("Circles","Post Malone" ,R.drawable.img_album_art))
        //            add(SavedSong("Lilac","아이유 (IU)" ,R.drawable.img_album_exp2))
        //            add(SavedSong("Next Level","에스파 (AESPA)" , R.drawable.img_album_exp3))
        //            add(SavedSong("Boy with Luv","방탄소년단 (BTS)" ,R.drawable.img_album_exp4))
        //            add(SavedSong("BBoom BBoom","모모랜드 (MOMOLAND)" ,R.drawable.img_album_exp5))
        //            add(SavedSong("Weekend","태연 (Tae Yeon)" ,R.drawable.img_album_exp6))
        //        }
        //
        //        val savedSongRVAdapter = SavedSongRVAdapter(albumDatas)
        //        //리사이클러뷰에 어댑터를 연결
        //        binding.lockerSavedSongRecyclerView.adapter = savedSongRVAdapter
        //        //리사이클러 뷰에 레이아웃을 관리해줄 매니저 연결.
        //        binding.lockerSavedSongRecyclerView.layoutManager = LinearLayoutManager(context , LinearLayoutManager.VERTICAL,false)
        //
        //
        //        savedSongRVAdapter.setMyItemClickListner(object :SavedSongRVAdapter.MyItemClickListner{
        //            override fun onItemClick(song: SavedSong){
        //                changeSongFragment(song)
        //            }
        //
        //            override fun onRemoveAlbum(position: Int) {
        //                savedSongRVAdapter.removeItem(position)
        //            }
        //
        //            override fun setStatus(switch:Boolean){
        //                savedSongRVAdapter.setSwitch(switch)
        //            }
        //
        //
        //            private fun changeSongFragment(song: SavedSong) {
        //                (context as MainActivity).supportFragmentManager.beginTransaction()
        //                    .replace(R.id.main_frm, AlbumFragment().apply {
        //                        arguments = Bundle().apply {
        //                            val gson = Gson()
        //                            val albumJson = gson.toJson(song)
        //                            putString("album", albumJson)
        //                        }
        //                    }).commitAllowingStateLoss()
        //            }
        //        })
//    }
}