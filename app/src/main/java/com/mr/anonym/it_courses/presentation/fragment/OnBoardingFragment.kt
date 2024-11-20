package com.mr.anonym.it_courses.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mr.anonym.data.local.dataStore.DataStoreInstance
import com.mr.anonym.domain.model.CoursesModel
import com.mr.anonym.domain.useCase.CoursesUseCases
import com.mr.anonym.it_courses.R
import com.mr.anonym.it_courses.databinding.FragmentOnBoardingBinding
import com.mr.anonym.it_courses.presentation.adapter.OnBoardingAdapter
import com.mr.anonym.it_courses.presentation.viewModel.WelcomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingFragment : Fragment() {

    @Inject
    lateinit var useCases: CoursesUseCases
    @Inject
    lateinit var dataStore: DataStoreInstance
    lateinit var binding: FragmentOnBoardingBinding
    lateinit var adapter: OnBoardingAdapter
    lateinit var navController: NavController
    lateinit var navGraph: NavGraph
    private val viewModel: WelcomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeView()
    }

    private fun initializeView() {

        navController = findNavController()
        navGraph = navController.graph
        adapter = OnBoardingAdapter()
        binding.rvOnBoardOnBoarding.adapter = adapter
        viewModel.coursesList.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        binding.apply {
            btnContinueOnBoarding.setOnClickListener{
               findNavController().navigate(R.id.action_onBoardingFragment_to_signInFragment)
                CoroutineScope(Dispatchers.IO).launch {
                    dataStore.isFirstTime(true)
                }
                navGraph.setStartDestination(R.id.signInFragment)
                navController.graph = navGraph
            }
        }
    }
}