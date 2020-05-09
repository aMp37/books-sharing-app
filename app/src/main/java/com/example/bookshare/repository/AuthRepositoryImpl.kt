import androidx.core.net.toUri
import com.example.bookshare.model.User
import com.example.bookshare.repository.AuthRepository
import com.example.bookshare.service.FirebaseAuthService
import com.example.bookshare.service.FirebaseAuthServiceImpl
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.lang.NullPointerException

class AuthRepositoryImpl(): AuthRepository<User> {
    private val mFirebaseAuthService: FirebaseAuthService by lazy {
        FirebaseAuthServiceImpl()
    }

    override suspend fun signInUser(user: User): Boolean {
        val result = withContext(IO) {
            mFirebaseAuthService.loginWithEmailAndPassword(user.email,user.password)
        }

        if(result!=null){
            return true
        }
        return false
    }

    override suspend fun signUpUser(user: User): Boolean {
        val result = mFirebaseAuthService.registerWithEmailAndPassword(user.email,user.password)

        if(result!=null){
            mFirebaseAuthService.currentUser()?.updateProfile(UserProfileChangeRequest.Builder()
                .setDisplayName(user.displayName)
                .setPhotoUri("https://img2.pngio.com/united-states-avatar-organization-information-png-512x512px-user-avatar-png-820_512.jpg".toUri())  //Todo take picture
                .build())
            return true
        }
        return false
    }

    override suspend fun updateCurrentUser(oldPassword: String, newUser: User): Boolean {

        val reAuthResult = mFirebaseAuthService.reAuthCurrentUser(EmailAuthProvider.getCredential(
            getCurrentUser().email,
            oldPassword
        ))

        if(reAuthResult!= null){
            return try {
                mFirebaseAuthService.currentUser()!!.updateEmail(newUser.email)
                mFirebaseAuthService.currentUser()!!.updatePassword(newUser.password)
                mFirebaseAuthService.currentUser()!!.updateProfile(UserProfileChangeRequest.Builder()
                    .setDisplayName(newUser.displayName)
                    .setPhotoUri("https://img2.pngio.com/united-states-avatar-organization-information-png-512x512px-user-avatar-png-820_512.jpg".toUri())  //Todo take picture
                    .build())

                true
            }catch (e: NullPointerException){
                false
            }
        }
        return false
    }

    override fun signOutUser() {
        mFirebaseAuthService.logout()
    }

    override fun getCurrentUser(): User {
        return User().apply {
            email = mFirebaseAuthService.currentUser()?.email?:""
            displayName = mFirebaseAuthService.currentUser()?.displayName?:""
        }
    }
}