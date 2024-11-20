package com.mr.anonym.it_courses.presentation.viewModel

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mr.anonym.data.local.dataStore.DataStoreInstance
import com.mr.anonym.domain.model.CoursesModel
import com.mr.anonym.domain.useCase.CoursesUseCases
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val useCases: CoursesUseCases,
    private val dataStoreInstance: DataStoreInstance
): ViewModel() {

    private val _coursesList = MutableLiveData<List<CoursesModel>>()
    val coursesList: LiveData<List<CoursesModel>> = _coursesList
    private val _vkUserToken = MutableLiveData<String>()
    val vkUserToken: LiveData<String> = _vkUserToken
    private val _userFireBase = MutableLiveData<FirebaseUser>()
    val userFireBase: LiveData<FirebaseUser> = _userFireBase

    init {
        getAllCourses()
    }

    fun getAllCourses() = viewModelScope.launch{
        _coursesList.value = useCases.getAllCoursesUseCase.execute()
    }

    fun signInWithVK(activity: ComponentActivity) = viewModelScope.launch {
        val authLauncher = VK.login(activity) { result: VKAuthenticationResult ->
            when (result) {
                is VKAuthenticationResult.Success -> {
//                    User passed authorization
                    val token = result.token.accessToken
                    val email = result.token.email
                    val phone = result.token.phoneAccessKey
                    CoroutineScope(Dispatchers.IO).launch {
                        dataStoreInstance.saveToken(token)
                        dataStoreInstance.saveEmail(email.toString())
                        dataStoreInstance.savePhone(phone.toString())
                    }
                    _vkUserToken.value = token
                }
                is VKAuthenticationResult.Failed -> {
                    CoroutineScope(Dispatchers.Default).launch {
                        dataStoreInstance.setIsAuthorized(false)
                    }
                    Log.d("NetworkLogging", "signInWithVK: signWithVkFailed")
                }
            }
        }
        authLauncher.launch(arrayListOf(VKScope.EMAIL))
    }

    fun signInWithFirebase(auth: FirebaseAuth,email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { process ->
                    if (process.isSuccessful) {
                        val user = auth.currentUser!!
                        _userFireBase.value = user
                        Log.d("NetworkLogging", " signInWithFirebase: signIn success")
                    } else {
                        Log.d("NetworkLogging", " signInWithFirebase: signIn failed")
                    }
                }
        }
    }
}