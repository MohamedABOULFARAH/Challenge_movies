package com.moabo.moviedemo.view.splashScreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.moabo.moviedemo.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [SplashScreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenFragment : Fragment() {

    private lateinit var binding: ActivitySplashBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = ActivitySplashBinding.inflate(inflater, container, false)
        val movieDetail =
            SplashScreenFragmentDirections.actionSplashScreenFragmentToTopRatedMoviesFragment()

        GlobalScope.launch(Dispatchers.Main) {
            delay(3000)
            findNavController().navigate(movieDetail)

        }

        // Inflate the layout for this fragment
        return binding.root
    }








}