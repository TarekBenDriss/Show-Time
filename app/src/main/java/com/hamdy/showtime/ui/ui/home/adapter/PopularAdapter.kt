package com.hamdy.showtime.ui.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.MoviesItemBinding
import com.hamdy.showtime.ui.ui.home.model.PopularResultsItem
import com.hamdy.showtime.ui.util.ImageUrlBase
import kotlin.random.Random


class PopularAdapter : RecyclerView.Adapter<PopularAdapter.Holder>() {
    private var movies: List<PopularResultsItem>? = null
    var context: Context? = null
    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        context=parent.context
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.movies_item, parent, false)
        )
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val movie= movies?.get(position)
        holder.moviesName.text=movie?.originalTitle
        //holder.companyName.text=movie?.
        holder.rateText.text=movie?.voteAverage.toString()
        holder.movieImage.load(ImageUrlBase + movie?.posterPath){
            crossfade(true)
            crossfade(500)
        }
        setAnimation(holder.itemView, position);

    }
    override fun getItemCount(): Int {
        if(movies!=null)
            return movies!!.size
        return 0
    }
    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val anim = ScaleAnimation(
                0.0f,
                1.0f,
                0.0f,
                1.0f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
            anim.duration = Random.nextLong(501) //to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim)
            lastPosition = position
        }
    }
    fun setPopular(movies: List<PopularResultsItem>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = MoviesItemBinding.bind(itemView)
        val movieImage = binding.movieImage
        //val companyName = binding.companyName
        val moviesName = binding.moviesName
        val rateText = binding.rateText

    }
}