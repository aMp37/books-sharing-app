import com.example.bookshare.repository.AuthRepository
import com.example.bookshare.service.FirebaseAuthService
import com.example.bookshare.service.FirebaseAuthServiceImpl
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(): AuthRepository {
    private val mFirebaseAuthService: FirebaseAuthService by lazy {
        FirebaseAuthServiceImpl()
    }

    override suspend fun signInUser(email: String, password: String): Boolean {
        val result = withContext(IO) {
            mFirebaseAuthService.loginWithEmailAndPassword(email, password)
        }

        if(result!=null){
            return true
        }
        return false
    }

    override suspend fun signUpUser(email: String, password: String): Boolean {
        val result = withContext(IO){
            mFirebaseAuthService.registerWithEmailAndPassword(email,password)
        }

        if(result!=null){
            return true
        }
        return false
    }

    override fun signOutUser() {
        mFirebaseAuthService.logout()
    }

    override fun getCurrentUser(): FirebaseUser? = mFirebaseAuthService.currentUser()

}