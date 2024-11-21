package com.mr.anonym.it_courses.presentation.viewModel

import android.app.Application
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
import com.mr.anonym.it_courses.presentation.utils.appID
import com.mr.anonym.it_courses.presentation.utils.appKEY
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ok.android.sdk.Odnoklassniki
import ru.ok.android.sdk.util.OkAuthType
import ru.ok.android.sdk.util.OkScope
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val useCases: CoursesUseCases,
    private val dataStoreInstance: DataStoreInstance,
) : ViewModel() {

    private val _coursesList = MutableLiveData<List<CoursesModel>>()
    val coursesList: LiveData<List<CoursesModel>> = _coursesList
    private val _userToken = MutableLiveData<String>()
    val userToken: LiveData<String> = _userToken
    private val _user = MutableLiveData<FirebaseUser>()
    val user: LiveData<FirebaseUser> = _user

    init {
        getAllCourses()
    }

    fun getAllCourses() = viewModelScope.launch {
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
                    _userToken.value = token
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

    fun signInWithFirebase(auth: FirebaseAuth, email: String, password: String) = viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { process ->
                    if (process.isSuccessful) {
                        _user.value = auth.currentUser
                        Log.d("NetworkLogging", " signInWithFirebase: signIn success")
                    } else {
                        Log.d("NetworkLogging", " signInWithFirebase: signIn failed")
                    }
                }
    }

    fun signUpWithFirebase(auth: FirebaseAuth, email: String, password: String) = viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password) .addOnCompleteListener{process ->
                if (process.isSuccessful){
                    _user.value = auth.currentUser
                }
            }
    }
    fun signInWithOK(ok: Odnoklassniki,activity: ComponentActivity, applicationContext: Application) = viewModelScope.launch{
        ok.requestAuthorization(activity,"https://ok.ru/",OkAuthType.WEBVIEW_OAUTH, OkScope.LONG_ACCESS_TOKEN)
    }
}