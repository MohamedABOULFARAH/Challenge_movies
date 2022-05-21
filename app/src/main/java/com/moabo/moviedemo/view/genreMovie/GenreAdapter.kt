package com.moabo.moviedemo.view.genreMovie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moabo.moviedemo.databinding.EachRowBinding
import com.moabo.moviedemo.model.genre.Genre

class GenreAdapter(private var genreList: List<Genre>, private var onCatClicked:()->Unit)
    : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {
    private lateinit var binding:EachRowBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        binding = EachRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return GenreViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        binding.tasks.text=genreList[position].name
        holder.itemView.setOnClickListener {
            onCatClicked.invoke()
        }
    }

    override fun getItemCount(): Int = genreList.size

    class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    }

    fun setData(postList: List<Genre>)
    {
        this.genreList=postList
        notifyDataSetChanged()
    }

}