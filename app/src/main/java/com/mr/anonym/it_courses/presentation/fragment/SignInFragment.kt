package com.mr.anonym.it_courses.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.mr.anonym.data.local.dataStore.DataStoreInstance
import com.mr.anonym.it_courses.R
import com.mr.anonym.it_courses.databinding.FragmentSignInBinding
import com.mr.anonym.it_courses.presentation.activity.MainActivity
import com.mr.anonym.it_courses.presentation.viewModel.WelcomeViewModel
import com.vk.api.sdk.auth.VKAuthenticationResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInFragment : Fragment() {

    @Inject
    lateinit var dataStoreInstance: DataStoreInstance
    lateinit var auth: FirebaseAuth
    lateinit var authManager: VKAuthenticationResult
    lateinit var binding: FragmentSignInBinding
    private val welcomeViewModel: WelcomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
    }

    private fun initializeViews() {

        FirebaseApp.initializeApp(requireContext())
        auth = FirebaseAuth.getInstance()
        var isAuthorized = false
        CoroutineScope(Dispatchers.Default).launch {
            dataStoreInstance.getIsAuthorized().collect { isAuthorized = it }
        }
        binding.apply {
            val email = etEmailSignIn.text.toString()
            val password = etPasswordSignIn.text.toString()

            btnSignInSingIn.setOnClickListener {
                if (etEmailSignIn.text.isNotEmpty() || etEmailSignIn.text.isNotBlank()
                    && etPasswordSignIn.text.isNotEmpty() || etPasswordSignIn.text.isNotBlank()
                ) {
                    if (!isAuthorized){
                        CoroutineScope(Dispatchers.IO).launch {
                            welcomeViewModel.signInWithFirebase(auth,email, password)
                            if (welcomeViewModel.userFireBase.value == auth.currentUser){
                                dataStoreInstance.setIsAuthorized(true)
                                dataStoreInstance.getIsAuthorized().collect{
                                    isAuthorized = it
                                }
                                dataStoreInstance.firebaseUser(welcomeViewModel.userFireBase.value!!)
                                Intent(requireContext(), MainActivity::class.java).also {
                                    startActivity(it)
                                }
                            }else{
                                dataStoreInstance.setIsAuthorized(false)
                                Toast.makeText(requireContext(), "Wrong email or password", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        Intent(requireContext(), MainActivity::class.java).also {
                            startActivity(it)
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Fill all fields", Toast.LENGTH_SHORT).show()
                }
            }

            btnSignVkSingIn.setOnClickListener {
                var token = ""

                if (!isAuthorized) {
                    welcomeViewModel.signInWithVK(requireActivity())
                    welcomeViewModel.vkUserToken.observe(viewLifecycleOwner) {
                        token = it
                    }
                } else {
                    Intent(requireContext(), MainActivity::class.java).also {
                        startActivity(it)
                    }
                }
            }
            tvSingUpSingIn.setOnClickListener {
                findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
            }
        }
    }
}