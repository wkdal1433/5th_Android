package com.example.flo.ui.main.locker

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.data.entities.Song
import com.example.flo.databinding.ItemSongBinding

class SavedSongRVAdapter() :
    RecyclerView.Adapter<SavedSongRVAdapter.ViewHolder>() {
    private val songs = ArrayList<Song>()

    interface MyItemClickListener {
        fun onRemoveSong(songId: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: ItemSongBinding =
            ItemSongBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songs[position])
        holder.binding.itemSongMoreIv.setOnClickListener {
            mItemClickListener.onRemoveSong(songs[position].id)
            removeSong(position)
        }
    }

    override fun getItemCount(): Int = songs.size

    @SuppressLint("NotifyDataSetChanged")
    fun addSongs(songs: ArrayList<Song>) {
        this.songs.clear()
        this.songs.addAll(songs)

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeSong(position: Int) {
        songs.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.itemSongImgIv.setImageResource(song.coverImg!!)
            binding.itemSongTitleTv.text = song.title
            binding.itemSongSingerTv.text = song.singer
        }
    }

//    private var switch :Boolean? = null
//
//    interface MyItemClickListner{
//        fun onItemClick(song: SavedSong)
//        fun onRemoveAlbum(position: Int)
//        fun setStatus(switch: Boolean)
//    }
//    private lateinit var mItemClickListner: MyItemClickListner
//    fun setMyItemClickListner(itemClickListner: MyItemClickListner){
//        mItemClickListner = itemClickListner
//    }
//    fun addItem(song: SavedSong){
//        songList.add(song)
//        notifyDataSetChanged()
//    }
//    fun removeItem(position: Int){
//        songList.removeAt(position)
//        notifyDataSetChanged()
//    }
//    fun setSwitch(switch:Boolean){
//        this.switch = switch
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SavedSongRVAdapter.ViewHolder {
//        //뷰 홀더 생성 시 호출
////        여기서 객체 만든 다음에 다시 재활용 하라고 뷰홀더에 던지는 작업을 해줄 것.
//        val binding: ItemsongBinding = ItemsongBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
//        return ViewHolder(binding)
//
//    }
//
//    override fun onBindViewHolder(holder: SavedSongRVAdapter.ViewHolder, position: Int) {
//// 데이터에 뷰홀더를 바인딩 할 때마다 호출되는 함수
//// 매개변수에 position은 index임.
//        holder.bind(songList[position])
////        holder.itemView.setOnClickListener{mItemClickListner.onItemClick(songList[position])}
//        holder.binding.itemAlbumCategoryImgIv.setOnClickListener{mItemClickListner.onRemoveAlbum(position)}
//
//        holder.setSwitch(this.switch)
//        holder.binding.switchIvOff.setOnClickListener{mItemClickListner.setStatus(false)}
//        holder.binding.switchIvOn.setOnClickListener{mItemClickListner.setStatus(true)}
//    }
//
//
//    //데이터세트의 크기를 알려주는 함수. 리사이클러 뷰의 마지막이 언제인지를 알려주는 함수
//    override fun getItemCount(): Int = songList.size
//
//    inner class ViewHolder(val binding:ItemsongBinding):RecyclerView.ViewHolder(binding.root){
//        //뷰 홀더 클래스는 아이템 객체가 날라가지 않도록 담아주는 녀석이니까, 매개변수로 ItemAlbumBinding객체를 받는 것.
//        fun bind(song: SavedSong){
//            binding.itemAlbumTitleTv.text = song.title
//            binding.itemAlbumSingerTv.text = song.singer
//            binding.itemAlbumCoverImgIv.setImageResource(song.coverImg!!)
//        }
//
//
//        fun setSwitch(switch:Boolean?){
//            if(switch==false){
//                binding.switchIvOff.visibility = View.GONE
//                binding.switchIvOn.visibility = View.VISIBLE
//            }
//            else if(switch==true){
//                binding.switchIvOn.visibility = View.GONE
//                binding.switchIvOff.visibility = View.VISIBLE
//            }else{
//
//            }
//        }
//    }
}