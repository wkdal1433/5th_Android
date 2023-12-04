package com.example.flo.ui.main.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.data.entities.Album
import com.example.flo.databinding.ItemAlbumBinding

class AlbumRVAdapter(private val albumList : ArrayList<Album>): RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>() {

    interface MyItemClickListner{
        fun onItemClick(album: Album)
        fun onRemoveAlbum(position: Int)
    }
    private lateinit var mItemClickListner: MyItemClickListner
    fun setMyItemClickListner(itemClickListner: MyItemClickListner){
        mItemClickListner = itemClickListner
    }
    fun addItem(album: Album){
        albumList.add(album)
        notifyDataSetChanged()
    }
    fun removeItem(position: Int){
        albumList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        //뷰 홀더 생성 시 호출
//        여기서 객체 만든 다음에 다시 재활용 하라고 뷰홀더에 던지는 작업을 해줄 것.
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
// 데이터에 뷰홀더를 바인딩 할 때마다 호출되는 함수
// 매개변수에 position은 index임.
        holder.bind(albumList[position])
        holder.itemView.setOnClickListener{mItemClickListner.onItemClick(albumList[position])}
//        holder.binding.itemAlbumTitleTv.setOnClickListener{mItemClickListner.onRemoveAlbum(position)}

    }


    //데이터세트의 크기를 알려주는 함수. 리사이클러 뷰의 마지막이 언제인지를 알려주는 함수
    override fun getItemCount(): Int = albumList.size

    inner class ViewHolder(val binding:ItemAlbumBinding):RecyclerView.ViewHolder(binding.root){
        //뷰 홀더 클래스는 아이템 객체가 날라가지 않도록 담아주는 녀석이니까, 매개변수로 ItemAlbumBinding객체를 받는 것.
        fun bind(album: Album){
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumImgIv.setImageResource(album.coverImg!!)
        }
    }
}