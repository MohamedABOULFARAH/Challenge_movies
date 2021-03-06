package com.moabo.moviedemo.view.topRated

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.moabo.moviedemo.R
import com.moabo.moviedemo.utils.ApiState
import com.moabo.moviedemo.utils.showAlert
import com.moabo.moviedemo.databinding.FragmentTopRatedMovieBinding
import com.moabo.moviedemo.model.movie.Movie
import com.moabo.moviedemo.view.searchMovie.SearchMovieAdapter
import com.moabo.moviedemo.view.searchMovie.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [TopRatedMoviesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class TopRatedMoviesFragment : Fragment() {

    private lateinit var binding: FragmentTopRatedMovieBinding
    private lateinit var topRatedMoviesAdapter: TopRatedAdapter
    private lateinit var searchMovieAdapter: SearchMovieAdapter
    private val topRatedViewModel: TopRatedMoviesViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var onMovieClicked: (movie: Movie) -> Unit

    private var sortAZ = true
    private var sortDateDesc = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentTopRatedMovieBinding.inflate(inflater, container, false)
        BindUI()
        BindUISearch()
        // Inflate the layout for this fragment
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun BindUI() {
        onMovieClicked = {
            val movieDetailFragment =
                TopRatedMoviesFragmentDirections.actionTopRatedMoviesFragmentToMovieDetailFragment(it,false)
            findNavController().navigate(movieDetailFragment)
        }
        binding.topRatedImageFavorites.setOnClickListener {
            val favoriteMoviesFragment =
                TopRatedMoviesFragmentDirections.actionTopRatedMoviesFragmentToFavoriteMoviesFragment()
            findNavController().navigate(favoriteMoviesFragment)
        }
        topRatedViewModel.getTopRatedResults(1)

        initRecyclerviewTopRated()

        lifecycleScope.launchWhenCreated {
            topRatedViewModel.topRatedResultStateFlow.collect {
                when (it) {
                    is ApiState.Loading -> {
                        binding.recyclerview.isVisible = false
                    }
                    is ApiState.Failure -> {
                        binding.recyclerview.isVisible = false
                        context?.let { it1 -> showAlert(it1, it.msg.localizedMessage!!) }
                    }
                    is ApiState.Success -> {
                        binding.recyclerview.isVisible = true
                        topRatedMoviesAdapter.setData(it.data as List<Movie>)
                        // topRatedMoviesAdapter.notifyDataSetChanged()
                    }
                    is ApiState.Empty -> {
                        binding.recyclerview.isVisible = false
                    }
                }
            }



        }
        binding.topRatedImageAlphaSort.setOnClickListener {
                topRatedMoviesAdapter.orderListByTitle(sortAZ)
                sortAZ = !sortAZ
            if (sortAZ) {
                binding.topRatedImageAlphaSort.setImageDrawable(resources.getDrawable(R.drawable.filter_down,
                    context?.theme))
            } else {
                binding.topRatedImageAlphaSort.setImageDrawable(resources.getDrawable(R.drawable.filter_up,
                    context?.theme
                ))
            }
        }

        binding.topRatedImageDateSort.setOnClickListener {
                topRatedMoviesAdapter.orderListByDate(sortDateDesc)
                sortDateDesc = !sortDateDesc
            if (sortDateDesc) {
                binding.topRatedImageDateSort.setBackgroundResource(R.drawable.ic_baseline_arrow_up);
            } else {
                binding.topRatedImageDateSort.setBackgroundResource(R.drawable.ic_baseline_arrow_down);
            }

        }

    }

    fun BindUISearch() {

        onMovieClicked = {
            val movieDetailFragment =
                TopRatedMoviesFragmentDirections.actionTopRatedMoviesFragmentToMovieDetailFragment(it,true)
            findNavController().navigate(movieDetailFragment)
        }

        initRecyclerviewSearch()

        lifecycleScope.launchWhenCreated {
            searchViewModel.searchResultStateFlow.collect {
                when(it){
                    is ApiState.Loading->{
                        binding.searchList.isVisible=false
                    }
                    is ApiState.Failure -> {
                        binding.searchList.isVisible = false
                        //context?.let { it1 -> showAlert(it1,it.msg.localizedMessage!!) }
                    }
                    is ApiState.Success->{
                        binding.searchList.isVisible = true
                        searchMovieAdapter.setData(it.data as List<Movie>)
                        searchMovieAdapter.notifyDataSetChanged()
                    }
                    is ApiState.Empty->{
                        binding.searchList.isVisible = false
                    }
                }
            }
        }
        binding.searchViewEdtxt.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let { searchViewModel.getSearchResults(it) }
                binding.searchList.isVisible = false
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                p0?.let { searchViewModel.getSearchResults(it) }
                return false
            }

        })
    }
    private fun initRecyclerviewTopRated() {
        topRatedMoviesAdapter = TopRatedAdapter(onMovieClicked)
        binding.recyclerview.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = topRatedMoviesAdapter
        }

    }
    fun initRecyclerviewSearch(){
        searchMovieAdapter = SearchMovieAdapter(ArrayList(),onMovieClicked)
        binding.searchList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = searchMovieAdapter
        }
    }



}