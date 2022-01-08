package com.hamdy.showtime.ui.ui.login.repository
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.hamdy.showtime.ui.util.FAILED_RESPONSE_VALUE
import com.hamdy.showtime.ui.util.SUCCESSFUL_RESPONSE_VALUE
import kotlinx.coroutines.tasks.await

class LoginRepository {
    private val TAG = "LoginRepository"
    suspend fun login(email:String,password:String):String{

        return try {
            val result=FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).await()
            if(result.user !=null) {
                Log.d(TAG, "login: if")
                SUCCESSFUL_RESPONSE_VALUE
            }else{
                Log.d(TAG, "login: else")
                FAILED_RESPONSE_VALUE
            }
        }catch (e:Exception){
            FAILED_RESPONSE_VALUE
        }

    }

}