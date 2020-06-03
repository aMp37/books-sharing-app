import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bookshare.model.User
import com.example.bookshare.repository.AuthRepository
import com.example.bookshare.service.FirebaseAuthService
import com.example.bookshare.service.FirebaseAuthServiceImpl
import com.example.bookshare.util.UiAvatarsUtil
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.lang.NullPointerException

object AuthRepositoryImpl: AuthRepository<User> {
    private val mFirebaseAuthService: FirebaseAuthService by lazy {
        FirebaseAuthServiceImpl()
    }

    private val mNetworkState = MutableLiveData<AuthRepository.NetworkState>().apply {
        value = AuthRepository.NetworkState.Success(AuthRepository.Operation.LOGIN)
    }
    override val networkState: LiveData<AuthRepository.NetworkState>
    get() = mNetworkState

    override suspend fun signInUser(user: User): Boolean {
        try{
            val result = withContext(IO) {
                mNetworkState.postValue(AuthRepository.NetworkState.Loading(AuthRepository.Operation.LOGIN))
                mFirebaseAuthService.loginWithEmailAndPassword(user.email,user.password)
            }

            if(result!=null){
                mNetworkState.postValue(AuthRepository.NetworkState.Success(AuthRepository.Operation.LOGIN))
                return true
            }

        }catch (e: Exception){  //TODO specify error code by exception type
            mNetworkState.postValue(AuthRepository.NetworkState.Error(AuthRepository.Operation.LOGIN,-1))
        }
        return false
    }

    override suspend fun signUpUser(user: User): Boolean {
        try {
            mNetworkState.postValue(AuthRepository.NetworkState.Loading(AuthRepository.Operation.SIGNUP))
            val result = mFirebaseAuthService.registerWithEmailAndPassword(user.email,user.password)

            if(result!=null){
                mFirebaseAuthService.currentUser()?.updateProfile(UserProfileChangeRequest.Builder()
                    .setDisplayName(user.displayName)
                    .setPhotoUri(user.photoUrl.toUri())
                    .build())
                mNetworkState.postValue(AuthRepository.NetworkState.Success(AuthRepository.Operation.SIGNUP))
                return true
            }
        }catch (e:Exception){   //TODO specify error code by exception type
            mNetworkState.postValue(AuthRepository.NetworkState.Error(AuthRepository.Operation.SIGNUP,-2))
        }
        return false
    }

    override suspend fun updateCurrentUser(oldPassword: String, newUser: User): Boolean {
        try{
            mNetworkState.postValue(AuthRepository.NetworkState.Loading(AuthRepository.Operation.UPDATE))

            mFirebaseAuthService.currentUser()?.updateProfile(UserProfileChangeRequest.Builder()
                .setDisplayName(newUser.displayName)
                .setPhotoUri(newUser.photoUrl.toUri())
                .build())

            val reAuthResult = mFirebaseAuthService.reAuthCurrentUser(EmailAuthProvider.getCredential(
                getCurrentUser()!!.email,
                oldPassword
            ))

            if(reAuthResult!= null){
                    mFirebaseAuthService.currentUser()?.updatePassword(newUser.password)
                    mNetworkState.postValue(AuthRepository.NetworkState.Success(AuthRepository.Operation.UPDATE))
                    return true
            }
        }catch (e: Exception){
            mNetworkState.postValue(AuthRepository.NetworkState.Error(AuthRepository.Operation.UPDATE,-3))
        }
        return false
    }

    override fun signOutUser() {
        mFirebaseAuthService.logout()
    }

    override fun getCurrentUser(): User? {
        var currentUser: User? = null
        try{
            mFirebaseAuthService.currentUser()?.let {
                currentUser = User().apply {
                    email = it.email!!
                    displayName = it.displayName!!
                }
            }
        }catch (e: NullPointerException){}
        finally {
            return currentUser
        }
    }
}