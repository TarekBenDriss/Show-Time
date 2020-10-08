package com.hamdy.showtime.ui.ui.movies_details.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.ChangeBounds
import androidx.transition.TransitionInflater
import coil.load
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.FragmentMoviesDetailsBinding
import com.hamdy.showtime.ui.ui.movies_details.adapter.CastAdapter
import com.hamdy.showtime.ui.util.ImageUrlBase

class MoviesDetails : Fragment() {

    private lateinit var moviesDetailsViewModel: MoviesDetailsViewModel
    private lateinit var binding: FragmentMoviesDetailsBinding

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

//        val transition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
//        sharedElementEnterTransition = transition
//        sharedElementReturnTransition = transition
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        moviesDetailsViewModel =
            ViewModelProvider(this).get(MoviesDetailsViewModel::class.java)
        binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val posterPath= arguments?.get("posterPath").toString()
        val id= arguments?.getInt("id")!!
//        val position= arguments?.getInt("position")!!
//        val type= arguments?.getString("type")!!
        moviesDetailsViewModel.getCastMovieList(id)
        moviesDetailsViewModel.getMoviesDetails(id)
        binding.moviePosterImage.load(ImageUrlBase + posterPath)
        binding.moviesBackGroundImage.load(ImageUrlBase + posterPath)
        val castAdapter=CastAdapter()
        binding.castRecyclerView.adapter= castAdapter
        binding.castRecyclerView.layoutManager=LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        moviesDetailsViewModel.listCastMovie.observe(viewLifecycleOwner, Observer {
            castAdapter.setCast(it)
        })
        moviesDetailsViewModel.movieDetails.observe(viewLifecycleOwner, Observer {
            binding.movieName.text = it?.title
            binding.overviewText.text = it?.overview
            var isTextViewClicked = true
            if (binding.overviewText.lineCount > 3)
                binding.seeMoreImage.visibility = View.VISIBLE
            binding.seeMoreImage.setOnClickListener {
                isTextViewClicked = if(isTextViewClicked){
                    binding.overviewText.maxLines = Integer.MAX_VALUE
                    binding.seeMoreImage.setImageResource(R.drawable.ic_arrow_up)
                    false
                } else {
                    binding.overviewText.maxLines = 3
                    binding.seeMoreImage.setImageResource(R.drawable.ic_arrow_down)
                    true
                }
            }

            val genres = it?.genres
            var temp = ""
            for (i in genres!!) {
                temp += if (i != genres.last())
                    "${i?.name} / "
                else
                       "${i?.name}"
            }
            binding.movieCategory.text = temp
            val time = it.runtime!!
            val hours: Int = time / 60
            val minutes: Int = time % 60
            binding.movieTime.text = "$hours H $minutes Min"
            binding.rateText.text = it.voteAverage.toString()
        })
//        ViewCompat.setTransitionName(binding.moviePosterImage, "${type}Image$position")
//        ViewCompat.setTransitionName(binding.movieName, "${type}Text$position")

    }

    override fun onDetach() {
        super.onDetach()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
}