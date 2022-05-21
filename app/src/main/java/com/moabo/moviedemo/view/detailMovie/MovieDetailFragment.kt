package com.moabo.moviedemo.view.detailMovie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.demo.Util.ApiState
import com.example.demo.Util.loadImage
import com.example.demo.Util.showAlert
import com.google.android.material.snackbar.Snackbar
import com.moabo.moviedemo.databinding.FragmentDetailMovieBinding
import com.moabo.moviedemo.model.movie.MovieDetail
import com.moabo.moviedemo.model.rating.RatingRQ
import com.moabo.moviedemo.model.rating.RatingRS
import com.moabo.moviedemo.model.session.SessionRS
import com.moabo.moviedemo.viewModel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import org.json.JSONObject
import java.util.*
import kotlin.properties.Delegates


/**
 * A simple [Fragment] subclass.
 * Use the [MovieDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailMovieBinding
    private val movieDetailViewModel: MovieDetailViewModel by viewModels()
    private val args: MovieDetailFragmentArgs by navArgs()
    var movieIdArg =0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        movieDetailViewModel.getMovieDetails(args.movieId)
        movieIdArg=args.movieId
        binding.rateBtnMovieDetail.setOnClickListener {

            val value = RatingRQ(2.5)

            SessionRS.UserSession.guestSessionId?.let { userSession ->
                movieDetailViewModel.rateMovie(value, 461053,
                    userSession
                )
            }
        }
        BindUI()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun BindUI() {

        lifecycleScope.launchWhenCreated {
            movieDetailViewModel.movieDetailStateFlow.collect {
                when (it) {
                    is ApiState.Loading -> {
                        //binding.searchList.isVisible=false
                    }
                    is ApiState.Failure -> {
                        Log.d("main", "onCreate: ${it.msg}")
                    }
                    is ApiState.Success -> {
                        val movieDetail: MovieDetail = it.data as MovieDetail
                        binding.titleDescriptionMovieDetail.text = movieDetail.title
                        binding.taglineMovieDetail.text = movieDetail.tagline
                        binding.titleDescriptionMovieDetail.text = movieDetail.originalTitle
                        binding.descriptionMovieDetail.text = movieDetail.overview
                        movieDetail.backdropPath?.let {
                            loadImage(
                                movieDetail.backdropPath.toString(),
                                binding.backgroundImageMovieDetail
                            )
                        }
                        movieDetail.posterPath?.let {
                            loadImage(
                                movieDetail.posterPath.toString(),
                                binding.posterImageMovieDetail
                            )
                        }

                    }
                    is ApiState.Empty -> {
                        //  binding.searchList.isVisible = false
                    }
                }
            }
            movieDetailViewModel.rateStateFlow.collect {
                when (it) {
                    is ApiState.Loading -> {
                        //binding.searchList.isVisible=false
                    }
                    is ApiState.Failure -> {
                        context?.let { it1 -> showAlert(it1,it.msg.localizedMessage!!) }
                    }
                    is ApiState.Success -> {
                        val ratingResponse: RatingRS = it.data as RatingRS
                        val snackbar: Snackbar? = ratingResponse.statusMessage?.let { it1 ->
                            Snackbar.make(
                                binding.root,
                                it1, 5000
                            )
                        }
                        snackbar?.show()
                    }
                    is ApiState.Empty -> {
                        //  binding.searchList.isVisible = false
                    }
                }
            }
        }


    }


}