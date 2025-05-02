package com.vedansh.twitterclone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vedansh.twitterclone.Adapters.TweetAdapter

class TweetFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var  tweetAdapter: TweetAdapter
    private val listOfTweets = mutableListOf<String>()
    private lateinit var  auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_tweet , container , false)
        return view

        //return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView  = view.findViewById<RecyclerView>(R.id.rv_tweets)
        auth = FirebaseAuth.getInstance()
        FirebaseDatabase.getInstance().getReference().child("users").child(auth.uid.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listOfFollowingsUids = snapshot.child("listOffollowing").value as MutableList<String>
                    listOfFollowingsUids.forEach {
                        getTweetFromUid(it)
                    }

                }
//                private fun getTweetFromUid(uid  : String){
//                    FirebaseDatabase.getInstance().getReference().child("users").child(uid)
//                        .addListenerForSingleValueEvent(object  : ValueEventListener{
//                            override fun onDataChange(snapshot: DataSnapshot) {
//                                var tweetList = mutableListOf<String>()
//                                snapshot.child("listOftweets")?.let {
//                                    tweetList = it as MutableList<String>
//                                }
//                                tweetList.forEach {
//                                    if(it.isBlank() == false){
//                                        listOfTweets.add(it)
//                                    }
//                                }
//                                tweetAdapter = TweetAdapter(listOfTweets)
//                                recyclerView.layoutManager = LinearLayoutManager(requireContext())
//                                recyclerView.adapter = tweetAdapter
//                            }
//
//                            override fun onCancelled(error: DatabaseError) {
////                                TODO("Not yet implemented")
//                            }
//
//                        })
//
//                }

                private fun getTweetFromUid(uid: String) {
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (child in snapshot.child("listOftweets").children) {
                                    val tweet = child.getValue(String::class.java)
                                    if (!tweet.isNullOrBlank()) {
                                        listOfTweets.add(tweet)
                                    }
                                }

                                if (!::tweetAdapter.isInitialized) {
                                    tweetAdapter = TweetAdapter(listOfTweets)
                                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                                    recyclerView.adapter = tweetAdapter
                                } else {
                                    tweetAdapter.notifyDataSetChanged()
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                // Handle error
                            }
                        })
                }


                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
                }

            })

                super.onViewCreated(view, savedInstanceState)
    }
}