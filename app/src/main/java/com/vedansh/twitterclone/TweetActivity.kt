package com.vedansh.twitterclone

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.play.integrity.internal.f
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TweetActivity : AppCompatActivity() {
    lateinit var tweetBox  : EditText
    lateinit var tweetbtn : Button
    lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tweet)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()
        tweetbtn.setOnClickListener {
            val tweet = tweetBox.text.toString()
            addTweet(tweet)
        }
    }
    private fun init(){
        tweetbtn = findViewById<Button>(R.id.upload_tweet)
        tweetBox = findViewById<EditText>(R.id.tweet_box)
        auth = FirebaseAuth.getInstance()
    }
    private fun addTweet(tweet  : String){
        FirebaseDatabase.getInstance().getReference().child("users").child(auth.uid.toString())
            .addListenerForSingleValueEvent(object :  ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val genericTypeIndicator = object : com.google.firebase.database.GenericTypeIndicator<MutableList<String>>() {}
                    val listOfTweets = snapshot.child("listOftweets").getValue(genericTypeIndicator) ?: mutableListOf()
                    listOfTweets.add(tweet)
                    uploadTweetList(listOfTweets)
                }


                override fun onCancelled(error: DatabaseError) {
                    //TODO("Not yet implemented")
                }


            })
    }
    private fun uploadTweetList(listOfTweets : List<String>){
        FirebaseDatabase.getInstance().getReference().child("users").child(auth.uid.toString()).child("listOftweets").setValue(listOfTweets)
        Toast.makeText(this , "Tweets uploaded Sucessfully"  , Toast.LENGTH_SHORT).show()

    }
}