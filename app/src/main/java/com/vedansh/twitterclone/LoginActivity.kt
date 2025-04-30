package com.vedansh.twitterclone

import android.R.attr.password
import android.content.Intent
import android.hardware.biometrics.BiometricManager.Strings
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database
import com.vedansh.twitterclone.data.User

class LoginActivity : AppCompatActivity() {
    private lateinit var emailtext  : EditText
    private lateinit var passwordtext  : EditText
    private lateinit var login  : Button
    private lateinit var signup  : Button
    private lateinit var auth  : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
        if(auth.currentUser != null){
            val intent = Intent(this , HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        login.setOnClickListener {
            val emaillogin = emailtext.text.toString()
            val passwordlogin = passwordtext.text.toString()
            loginfunction(emaillogin , passwordlogin)
        }

        signup.setOnClickListener {
            val emailsignup = emailtext.text.toString()
            val passwordsignup = passwordtext.text.toString()
            signupfunction(emailsignup , passwordsignup)
        }
    }


    private fun loginfunction(email : String , password : String){
        auth.signInWithEmailAndPassword(email , password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this , HomeActivity::class.java)
                    startActivity(intent)
                    finish() // finish the login screen and pressing the back button app should close and not the come to login page

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this , "Some error Occurred" , Toast.LENGTH_LONG).show()

                }
            }


        }




    private fun signupfunction(email : String , password : String){
        auth.createUserWithEmailAndPassword(email , password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val listoffollowings= mutableListOf<String>()
                    listoffollowings.add("")
                    val listoftweets= mutableListOf<String>()
                    listoftweets.add("")
                    //add user to firebase database
                    val user1 = User(
                        userEmail = email,
                        userProfileImage = "",
                        listOffollowing = listoffollowings,
                        listOftweets = listoftweets,
                        uid = auth.uid.toString()
                    )
                    addUsertoDatabase(user1)
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this , HomeActivity::class.java)
                    startActivity(intent)
                    finish() // finish the login screen and pressing the back button app should close and not the come to login page

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this , "Some error Occurred" , Toast.LENGTH_LONG).show()

                }
            }

    }
    private fun addUsertoDatabase(user: User){
        Firebase.database.getReference("users").child(user.uid).setValue(user)

    }
    private fun init(){
        emailtext = findViewById<EditText>(R.id.email)
        passwordtext = findViewById<EditText>(R.id.password)
        login = findViewById<Button>(R.id.login)
        signup = findViewById<Button>(R.id.signup)
        auth = FirebaseAuth.getInstance()

    }

}
