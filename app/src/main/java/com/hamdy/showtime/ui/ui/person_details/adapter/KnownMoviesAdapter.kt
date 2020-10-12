package com.hamdy.showtime.ui.ui.person_details.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.MoviesItemBinding
import com.hamdy.showtime.ui.model.PopularResultsItem
import com.hamdy.showtime.ui.util.ImageUrlBase
import kotlin.random.Random


class KnownMoviesAdapter : RecyclerView.Adapter<KnownMoviesAdapter.Holder>() {
    private var movies: List<PopularResultsItem>? = null
    private var action: Int? = null
    private var type: String? = null
    var context: Context? = null
    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        context=parent.context
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.movies_small_item, parent, false)
        )
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
//        val movie= movies?.get(position)
//        holder.moviesName.text=movie?.title
//        //holder.companyName.text=movie?.
//        holder.rateText.text=movie?.voteAverage.toString()
//        holder.movieImage.load(ImageUrlBase + movie?.posterPath){
//            crossfade(true)
//            crossfade(500)
//        }
////        ViewCompat.setTransitionName(holder.movieImage, "${type}Image$position")
////        ViewCompat.setTransitionName(holder.moviesName, "${type}Text$position")
//
//        setAnimation(holder.itemView, position)
//        holder.movieContainer.setOnClickListener {
////            val extras = FragmentNavigator.Extras.Builder()
////                .addSharedElement(holder.movieImage, ViewCompat.getTransitionName(holder.movieImage)!!)
////                .addSharedElement(holder.moviesName, ViewCompat.getTransitionName(holder.moviesName)!!)
////                .build()
//            val bundle = bundleOf("posterPath" to movie?.posterPath)
//            bundle.putInt("id", movie?.id!!)
////            bundle.putInt("position", position)
////            bundle.putString("type", type)
//            it.findNavController().navigate(action!!,bundle,null,null)
//        }

    }
    override fun getItemCount(): Int {
        if(movies!=null)
            return movies!!.size
        return 5
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

    fun setMovies(movies: List<PopularResultsItem>) {
        this.movies = movies
        notifyDataSetChanged()
    }
    /*fun setPopular(movies: List<PopularResultsItem>,action:Int,type:String) {
        this.movies = movies
        this.action = action
        this.type = type
        notifyDataSetChanged()
    }*/

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = MoviesItemBinding.bind(itemView)
        val movieContainer = binding.movieContainer
        val movieImage = binding.movieImage
        //val companyName = binding.companyName
        val moviesName = binding.moviesName
        val rateText = binding.rateText

    }
}