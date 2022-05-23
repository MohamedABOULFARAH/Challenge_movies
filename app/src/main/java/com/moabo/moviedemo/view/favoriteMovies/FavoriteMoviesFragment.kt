package com.moabo.moviedemo.view.favoriteMovies

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
import com.moabo.moviedemo.databinding.FragmentFavoriteMoviesBinding
import com.moabo.moviedemo.model.movie.Movie
import com.moabo.moviedemo.utils.ApiState
import com.moabo.moviedemo.utils.showAlert
import com.moabo.moviedemo.view.searchMovie.SearchMovieAdapter
import com.moabo.moviedemo.view.searchMovie.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteMoviesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class FavoriteMoviesFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteMoviesBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var searchMovieAdapter: SearchMovieAdapter
    private lateinit var movieDetail: Movie
    private val favoriteMoviesViewModel: FavoriteMoviesViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var onMovieClicked: (movie: Movie) -> Unit

    private var sortAZ = true
    private var sortDateDesc = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentFavoriteMoviesBinding.inflate(inflater, container, false)
        BindUI()
        BindUISearch()
        binding.titleFavorie.text="Favorite"
        // Inflate the layout for this fragment
        return binding.root
    }

    fun BindUI() {
        onMovieClicked = {
            val movieDetailFragment =
                FavoriteMoviesFragmentDirections.actionFavoriteMoviesFragmentToMovieDetailFragment(it,false)
            findNavController().navigate(movieDetailFragment)
        }

        favoriteMoviesViewModel.getFavoritesResults(1)

        initRecyclerviewFavorite()

        lifecycleScope.launchWhenCreated {
            favoriteMoviesViewModel.favoritesResultStateFlow.collect {
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
                        favoriteAdapter.setData(it.data as List<Movie>)
                    }
                    is ApiState.Empty -> {
                        binding.recyclerview.isVisible = false
                    }
                }
            }



        }
        binding.favoritesImageAlphaSort.setOnClickListener {
            favoriteAdapter.orderListByTitle(sortAZ)
                sortAZ = !sortAZ

        }

        binding.favoritesImageDateSort.setOnClickListener {
            favoriteAdapter.orderListByDate(sortDateDesc)
                sortDateDesc = !sortDateDesc

        }

    }

    fun BindUISearch() {

        onMovieClicked = {
            val movieDetailFragment =
                FavoriteMoviesFragmentDirections.actionFavoriteMoviesFragmentToMovieDetailFragment(it,true)
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
    private fun initRecyclerviewFavorite() {
        favoriteAdapter = FavoriteAdapter(onMovieClicked)
        binding.recyclerview.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = favoriteAdapter
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