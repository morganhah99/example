package com.sample.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sample.myapplication.databinding.FragmentFirstBinding
import com.sample.myapplication.viewmodel.ApiResponse
import com.sample.myapplication.viewmodel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FirstFragment : Fragment() {
    private val viewModel: CharacterViewModel by viewModels()

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        /**
         * viewmodel observes changes in livedata
         * viewmodel collect values in Flow
         *
         * Jetpack compose = stateflow
         *
         * MVVM = XML = VIEWBINDING = LIVEDATA
         * MVVM = JETPACK COMPOSE = FLOWS
         *
         */
        viewLifecycleOwner.lifecycleScope.launch { // new coroutine
            viewModel.characterResponse.collect { response ->
           // reactive
                // leaks
                when (response) {
                    is ApiResponse.SuccessState -> {
                        val characters = response.data
                        Toast.makeText(activity, characters.data?.results?.get(0)?.name, Toast.LENGTH_LONG).show()
                        // show recyclerview
                        // progressBar.visibility = View.GONE

                    }
                    is ApiResponse.ErrorState -> {
                        val errorMessage = response.message
                        //progressBar.visibility = View.GONE

                    }
                    is ApiResponse.LoadingState -> {
                        println("Loading: ${response.message}")
                        //show progress Dialog
                        // progressBar.visibility = View.VISIBLE
                    }
                }

            }

        }
        viewModel.fetchMarvelCharacter()


//        viewModel.characterResponse.observe(viewLifecycleOwner){ response ->
//            when (response) {
//                is ApiResponse.SuccessState -> {
//                  val characters = response.data
//                    Toast.makeText(activity, characters.data?.results?.get(0)?.name, Toast.LENGTH_LONG).show()
//                   // show recyclerview
//                   // progressBar.visibility = View.GONE
//
//                }
//                is ApiResponse.ErrorState -> {
//                    val errorMessage = response.message
//                    //progressBar.visibility = View.GONE
//
//                }
//                is ApiResponse.LoadingState -> {
//                    println("Loading: ${response.message}")
//                   //show progress Dialog
//                   // progressBar.visibility = View.VISIBLE
//                }
//            }
     //   }




        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}