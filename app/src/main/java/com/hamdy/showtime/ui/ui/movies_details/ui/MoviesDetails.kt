package com.hamdy.showtime.ui.ui.movies_details.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Path
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.FragmentMoviesDetailsBinding
import com.hamdy.showtime.ui.ui.movies_details.adapter.CastAdapter
import androidx.core.os.bundleOf
import androidx.core.view.marginBottom
import com.flaviofaria.kenburnsview.RandomTransitionGenerator
import com.hamdy.showtime.ui.ui.MainActivity
import com.hamdy.showtime.ui.util.*


class MoviesDetails : Fragment() {

    private lateinit var moviesDetailsViewModel: MoviesDetailsViewModel
    private lateinit var binding: FragmentMoviesDetailsBinding
    private var sharedIdValue = false
    private var favorite = false
    private lateinit var trailerId: String

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val transition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
//        sharedElementEnterTransition = transition
//        sharedElementReturnTransition = transition
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        moviesDetailsViewModel = ViewModelProvider(this)[MoviesDetailsViewModel::class.java]
        binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        activity?.window?.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        )


        val posterPath = arguments?.get(POSTER_PATH_KEY).toString()
        val id = arguments?.getInt(ID_KEY)!!

//        val position= arguments?.getInt("position")!!
//        val type= arguments?.getString("type")!!
        moviesDetailsViewModel.getCastMovieList(id)
        moviesDetailsViewModel.getMoviesDetails(id)
        moviesDetailsViewModel.getFavorite(id)

        binding.moviePosterImage.load(ImageUrlBase + posterPath)
        val backGroundImage = binding.moviesBackGroundImage
        val adi = LinearInterpolator()
        val generator = BackgroundTransitionGenerator()
        generator.SetMinRectFactor(1F)
        generator.setTransitionGenerator(10000, adi)
//        val generator = RandomTransitionGenerator(10000, adi)
        backGroundImage.setTransitionGenerator(generator)
        backGroundImage.load(ImageUrlBase + posterPath)

        val castAdapter = CastAdapter()
        binding.castRecyclerView.adapter = castAdapter

        binding.castRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.playButton.setOnClickListener {
            if (::trailerId.isInitialized) {
                val bundle = bundleOf("url" to trailerId)
                it.findNavController()
                    .navigate(R.id.action_moviesDetails_to_videoPlayerFragment, bundle, null, null)
            }
        }
        binding.favoriteIcon.setOnClickListener {
            val sharedPreferences: SharedPreferences =
                context?.getSharedPreferences(SHARED_PREF_ID, Context.MODE_PRIVATE)!!
            sharedIdValue = sharedPreferences.getBoolean(LOGIN_KEY, false)
            if (sharedIdValue) {
                moviesDetailsViewModel.setFavorite(id, posterPath, favorite)
                if (favorite)
                    binding.favoriteIcon.setImageResource(R.drawable.ic_favorite)
                else
                    binding.favoriteIcon.setImageResource(R.drawable.ic_favorite_choosed)
                favorite = !favorite
            } else {
                it.findNavController()
                    .navigate(R.id.action_moviesDetails_to_loginFragment, null, null, null)
                Toast.makeText(context, getString(R.string.favorite_should_be_logged_in), Toast.LENGTH_SHORT).show()
            }
        }

        moviesDetailsViewModel.trailerId.observe(viewLifecycleOwner, {
            trailerId = it?.key ?: ""
        })

        moviesDetailsViewModel.listCastMovie.observe(viewLifecycleOwner, {
            castAdapter.setCast(it)
        })

        moviesDetailsViewModel.favorite.observe(viewLifecycleOwner, {
            favorite = it
            if (it)
                binding.favoriteIcon.setImageResource(R.drawable.ic_favorite_choosed)
            else
                binding.favoriteIcon.setImageResource(R.drawable.ic_favorite)
        })

        moviesDetailsViewModel.movieDetails.observe(viewLifecycleOwner, {
            binding.movieName.text = it?.title
            binding.overviewText.text = it?.overview
            var isTextViewClicked = true
            if (binding.overviewText.lineCount > 3)
                binding.seeMoreImage.visibility = View.VISIBLE
            binding.seeMoreImage.setOnClickListener {
                isTextViewClicked = if (isTextViewClicked) {
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
            if (genres != null) {
                for (i in genres) {
                    temp += if (i != genres.last())
                        "${i?.name} / "
                    else
                        "${i?.name}"
                }
            }
            binding.movieCategory.text = temp
            val time = it?.runtime ?: 0
            val hours: Int = time / 60
            val minutes: Int = time % 60
            binding.movieTime.text = "$hours H $minutes Min"
            binding.rateText.text = it?.voteAverage.toString()
        })
//        ViewCompat.setTransitionName(binding.moviePosterImage, "${type}Image$position")
//        ViewCompat.setTransitionName(binding.movieName, "${type}Text$position")

    }

    override fun onDetach() {
        super.onDetach()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }


}