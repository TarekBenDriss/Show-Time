package com.hamdy.showtime.ui.ui.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.FragmentHomeBinding
import com.hamdy.showtime.ui.ui.home.adapter.PopularAdapter
import com.hamdy.showtime.ui.ui.home.adapter.TopRatedAdapter
import com.hamdy.showtime.ui.ui.home.adapter.TrendingAdapter
import com.hamdy.showtime.ui.ui.home.adapter.UpComingAdapter
import com.hamdy.showtime.ui.ui.home.model.PopularResultsItem
import com.hamdy.showtime.ui.util.CenterZoomLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), View.OnClickListener {
    private val TAG = "HomeFragment"
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val helper: SnapHelper = LinearSnapHelper()
        binding.trendingRecyclerview.layoutManager = CenterZoomLayoutManager(context, RecyclerView.HORIZONTAL, true)
        val adapterTrending=TrendingAdapter()
        binding.trendingRecyclerview.adapter = adapterTrending
        helper.attachToRecyclerView(trendingRecyclerview)

        homeViewModel.listTrending.observe(viewLifecycleOwner, Observer {
            adapterTrending.setTrends(it)
        })

        binding.popularRecyclerView.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,true)
        val adapterPopular=PopularAdapter()
        binding.popularRecyclerView.adapter=adapterPopular
        homeViewModel.listPopular.observe(viewLifecycleOwner, Observer {
            adapterPopular.setPopular(it)
        })

        binding.hightRateRecyclerView.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,true)
        val topRatedAdapter=PopularAdapter()
        binding.hightRateRecyclerView.adapter=topRatedAdapter
        homeViewModel.listTopRated.observe(viewLifecycleOwner, Observer {
            topRatedAdapter.setPopular(it)
        })

        binding.upcomingRecyclerView.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,true)
        val upComingAdapter=PopularAdapter()
        binding.upcomingRecyclerView.adapter=upComingAdapter
        homeViewModel.listUpComing.observe(viewLifecycleOwner, Observer {
            upComingAdapter.setPopular(it)
        })
        binding.seeAllPopular.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.seeAllPopular->{p0.findNavController().navigate(R.id.action_navigation_home_to_fragment_all_movies)}
        }
    }


}