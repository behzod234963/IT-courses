package com.mr.anonym.it_courses.presentation.fragment

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.mr.anonym.data.local.dataStore.DataStoreInstance
import com.mr.anonym.it_courses.databinding.FragmentSignUpBinding
import com.mr.anonym.it_courses.di.app.CoursesApp
import com.mr.anonym.it_courses.presentation.activity.MainActivity
import com.mr.anonym.it_courses.presentation.utils.appID
import com.mr.anonym.it_courses.presentation.utils.appKEY
import com.mr.anonym.it_courses.presentation.viewModel.WelcomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ok.android.sdk.Odnoklassniki
import ru.ok.android.sdk.util.OkAuthType
import ru.ok.android.sdk.util.OkScope
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    lateinit var binding: FragmentSignUpBinding
    @Inject lateinit var dataStoreInstance: DataStoreInstance
    lateinit var auth: FirebaseAuth
    lateinit var ok: Odnoklassniki
    private val welcomeViewModel: WelcomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews()
    }

    private fun initializeViews() {

        ok = Odnoklassniki.createInstance(requireContext(), appID, appKEY)
        auth = FirebaseAuth.getInstance()
        var isAuthorized = false
        CoroutineScope(Dispatchers.Default).launch { dataStoreInstance.getIsAuthorized().collect { isAuthorized = it } }
        binding.apply {

            val email = etEmailSingUp.text.toString()
            val password = etPasswordSignUp.text.toString()
            val confirmPassword = etConfirmPasswordSingUp.text.toString()

            btnSignUpSignUp.setOnClickListener{
                if (email.isNotEmpty() || email.isNotBlank()
                    && password.isNotEmpty() || password.isNotBlank()
                ) {
                    if (confirmPassword == password) {
                        welcomeViewModel.signUpWithFirebase(auth,email,confirmPassword)
                        if (welcomeViewModel.user.value == auth.currentUser){
                            CoroutineScope(Dispatchers.Default).launch{
                                dataStoreInstance.saveEmail(email)
                                dataStoreInstance.setIsAuthorized(true)
                                dataStoreInstance.getIsAuthorized().collect{
                                    isAuthorized = it
                                }
                                dataStoreInstance.firebaseUser(welcomeViewModel.user.value!!)
                            }
                            Intent(requireContext(),MainActivity::class.java).also {
                                startActivity(it)
                            }
                        }
                    }
                }else{
                    Intent(requireContext(),MainActivity::class.java).also {
                        startActivity(it)
                    }
                }
            }
            btnSignVkSingUp.setOnClickListener{
                if (!isAuthorized){
                    welcomeViewModel.signInWithVK(requireActivity())
                    welcomeViewModel.userToken.observe(viewLifecycleOwner){}
                }else{
                    Intent(requireContext(),MainActivity::class.java).also {
                        startActivity(it)
                    }
                }
            }
            btnSignOKSingUp.setOnClickListener{
                if (!isAuthorized){
                    welcomeViewModel.signInWithOK(ok,requireActivity(), CoursesApp())
                    CoroutineScope(Dispatchers.Default).launch{
                        dataStoreInstance.setIsAuthorized(true)
                    }
                }else{
                    Intent(requireContext(),MainActivity::class.java).also {
                        startActivity(it)
                    }
                }
            }
        }
    }
}