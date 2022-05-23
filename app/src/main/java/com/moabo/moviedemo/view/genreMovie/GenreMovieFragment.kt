package com.moabo.moviedemo.view.genreMovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.moabo.moviedemo.utils.ApiState
import com.moabo.moviedemo.utils.showAlert
import com.moabo.moviedemo.databinding.FragmentGenreMovieBinding
import com.moabo.moviedemo.model.genre.Genre
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [GenreMovieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class GenreMovieFragment : Fragment() {

    private lateinit var binding: FragmentGenreMovieBinding
    private lateinit var genreAdapter: GenreAdapter
    private val genreViewModel: GenreViewModel by viewModels()
    private lateinit var onCatClicked: () -> Unit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentGenreMovieBinding.inflate(inflater, container, false)
        BindUI()
        // Inflate the layout for this fragment
        return binding.root
    }

    fun BindUI() {
        onCatClicked = {
            val searchMovieFragment = GenreMovieFragmentDirections.actionGenreMovieFragmentToSearchMovieFragment()
            findNavController().navigate(searchMovieFragment)
        }
        genreViewModel.getGenres()
        initRecyclerview()
        lifecycleScope.launchWhenCreated {
            genreViewModel.genreStateFlow.collect {
                when (it) {
                    is ApiState.Loading -> {
                        binding.recyclerview.isVisible = false
                        binding.progressBar.isVisible = true
                    }
                    is ApiState.Failure -> {
                        binding.recyclerview.isVisible = false
                        binding.progressBar.isVisible = false
                        context?.let { it1 -> showAlert(it1,it.msg.localizedMessage!!) }
                    }
                    is ApiState.Success -> {
                        binding.recyclerview.isVisible = true
                        binding.progressBar.isVisible = false
                        genreAdapter.setData(it.data as List<Genre>)
                        genreAdapter.notifyDataSetChanged()
                    }
                    is ApiState.Empty -> {

                    }
                }
            }
        }
    }


    private fun initRecyclerview() {
        genreAdapter = GenreAdapter(ArrayList(),onCatClicked)
        binding.recyclerview.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = genreAdapter
        }
    }
}