package com.hamdy.showtime.ui.ui.all_movies.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.hamdy.showtime.databinding.FragmentAllMoviesBinding
import com.hamdy.showtime.ui.ui.home.adapter.PopularAdapter

class AllMoviesFragment : Fragment() {
    private val TAG = "AllMoviesFragment"
    private lateinit var allMoviesViewModel: AllMoviesViewModel
    private lateinit var binding: FragmentAllMoviesBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        allMoviesViewModel =
            ViewModelProvider(this).get(AllMoviesViewModel::class.java)
        binding = FragmentAllMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val type= arguments?.get("type").toString()
        when (type) {
            "popular" -> {
                allMoviesViewModel.getPopularTotalResult()
            }
            "topRate" -> {
                allMoviesViewModel.getTopRatedTotalResult()
            }
            else -> {
                allMoviesViewModel.getUpComingTotalResult()
            }
        }
        binding.allMoviesRecyclerView.layoutManager=GridLayoutManager(context,2)
        val adapter=PopularAdapter()
        binding.allMoviesRecyclerView.adapter=adapter
        allMoviesViewModel.listMovies.observe(viewLifecycleOwner, Observer {
            //Log.d(TAG, "onActivityCreated: ${it.size}")
            adapter.setPopular(it)
        })
        allMoviesViewModel.totalPages.observe(viewLifecycleOwner, Observer {
            when (type) {
                "popular" -> {
                    allMoviesViewModel.getPopular()
                }
                "topRate" -> {
                    allMoviesViewModel.getTopRated()
                }
                else -> {
                    allMoviesViewModel.getUpComing()
                }
            }
        })
    }
}