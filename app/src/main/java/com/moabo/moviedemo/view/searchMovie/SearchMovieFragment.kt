package com.moabo.moviedemo.view.searchMovie

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo.Util.ApiState
import com.example.demo.Util.showAlert
import com.moabo.moviedemo.databinding.FragmentSearchMovieBinding
import com.moabo.moviedemo.model.movie.Movie
import com.moabo.moviedemo.viewModel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [SearchMovieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SearchMovieFragment : Fragment() {

    private lateinit var binding: FragmentSearchMovieBinding
    private lateinit var searchMovieAdapter: SearchMovieAdapter
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var onMovieClicked: (movieId:Int) -> Unit


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentSearchMovieBinding.inflate(inflater, container, false)
        BindUI()
        // Inflate the layout for this fragment
        return binding.root
    }

    fun BindUI() {

        onMovieClicked = {
            val movieDetail = SearchMovieFragmentDirections.actionSearchMovieFragmentToMovieDetailFragment(it)
            findNavController().navigate(movieDetail)
        }

        initRecyclerview()

        lifecycleScope.launchWhenCreated {
            searchViewModel.searchResultStateFlow.collect {
                when(it){
                    is ApiState.Loading->{
                        binding.searchList.isVisible=false
                    }
                    is ApiState.Failure -> {
                        binding.searchList.isVisible = false
                        context?.let { it1 -> showAlert(it1,it.msg.localizedMessage!!) }
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

    private fun initRecyclerview() {
        searchMovieAdapter = SearchMovieAdapter(ArrayList(),onMovieClicked)
        binding.searchList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = searchMovieAdapter
        }
    }
}