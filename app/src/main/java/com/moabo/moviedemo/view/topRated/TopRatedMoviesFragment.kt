package com.moabo.moviedemo.view.topRated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo.Util.ApiState
import com.example.demo.Util.showAlert
import com.moabo.moviedemo.databinding.FragmentTopRatedMovieBinding
import com.moabo.moviedemo.model.movie.Movie
import com.moabo.moviedemo.viewModel.TopRatedMoviesViewModel
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
    private lateinit var topRatedMoviesAdapter: TopRatedMoviesAdapter
    private val topRatedViewModel: TopRatedMoviesViewModel by viewModels()
    private lateinit var onMovieClicked: (movieId: Int) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentTopRatedMovieBinding.inflate(inflater, container, false)
        BindUI()
        // Inflate the layout for this fragment
        return binding.root
    }

    fun BindUI() {
        onMovieClicked = {
            val movieDetailFragment =
                TopRatedMoviesFragmentDirections.actionTopRatedMoviesFragmentToMovieDetailFragment(it)
            findNavController().navigate(movieDetailFragment)
        }
        binding.topRatedImageAlphaSort.setOnClickListener {
            topRatedMoviesAdapter.orderListByTitle()
        }
        binding.topRatedImageDateSort.setOnClickListener {
            topRatedMoviesAdapter.orderListByDate()
        }
        topRatedViewModel.getTopRatedResults(1)
        initRecyclerview()
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
    }


    private fun initRecyclerview() {
        topRatedMoviesAdapter = TopRatedMoviesAdapter(ArrayList(), onMovieClicked)
        binding.recyclerview.setHasFixedSize(true)
        binding.recyclerview.isNestedScrollingEnabled = false
        binding.recyclerview.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = topRatedMoviesAdapter
        }
    }
}