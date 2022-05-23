package com.moabo.moviedemo.view.detailMovie

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.moabo.moviedemo.R
import com.moabo.moviedemo.databinding.FragmentDetailMovieBinding
import com.moabo.moviedemo.model.movie.Movie
import com.moabo.moviedemo.model.rating.RatingRQ
import com.moabo.moviedemo.model.rating.RatingRS
import com.moabo.moviedemo.model.session.SessionRS
import com.moabo.moviedemo.utils.ApiState
import com.moabo.moviedemo.utils.loadImage
import com.moabo.moviedemo.utils.showAlert
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*


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
    private lateinit var movieDetail: Movie
    private  var refreshApi: Boolean=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieDetail = args.movie
        refreshApi=args.refreshApi
        movieDetail.let { movieDetailViewModel.getMovieDetails(it, args.refreshApi) }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentDetailMovieBinding.inflate(inflater, container, false)

//rate a movie click
        binding.rateBtnMovieDetail.setOnClickListener {
            val value = RatingRQ(2.5)
            SessionRS.UserSession.guestSessionId?.let { userSession ->
                movieDetailViewModel.rateMovie(
                    value, 461053,
                    userSession
                )
            }
        }
// show alert to add/remove favorie movie
        binding.favorisBtnMovieDetail.setOnClickListener {
            if (movieDetail.isFavorite!=null && movieDetail.isFavorite!=false)
                context?.let { it1 -> showAlertFavorite(it1,true) }
            else
                context?.let { it1 -> showAlertFavorite(it1,false) }
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
                        movieDetail = it.data as Movie
                        binding.titleMovieDetail.text = movieDetail.title
                        binding.taglineMovieDetail.text = movieDetail.voteAverage.toString()
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

                        if (!refreshApi){
                            binding.favorisBtnMovieDetail.visibility=View.VISIBLE
                        }
                        if (movieDetail.isFavorite != null && movieDetail.isFavorite != false) {
                            binding.favorisBtnMovieDetail.setBackgroundResource(R.drawable.ic_remove_fav);
                        } else {
                            binding.favorisBtnMovieDetail.setBackgroundResource(R.drawable.ic_add_fav);
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
                        context?.let { it1 -> showAlert(it1, it.msg.localizedMessage!!) }
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

    fun showAlertFavorite(context: Context, isfavorite: Boolean) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Favorite")
        if (isfavorite){
            builder.setMessage("remove movie from favorite ?")

        }
        else{
            builder.setMessage("Add movie to favorite ?")

        }

        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            if (!isfavorite) {
                movieDetail.isFavorite =true
                binding.favorisBtnMovieDetail.setBackgroundResource(R.drawable.ic_remove_fav);
            } else {
                movieDetail.isFavorite =false
                binding.favorisBtnMovieDetail.setBackgroundResource(R.drawable.ic_add_fav);
            }
            movieDetailViewModel.addToFavorite(movieDetail)
            }

        builder.setNegativeButton(android.R.string.cancel) { _, _ ->
            Toast.makeText(
                context,
                android.R.string.cancel, Toast.LENGTH_SHORT
            ).show()
        }


        builder.show()
    }
}