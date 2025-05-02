package com.vedansh.twitterclone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.vedansh.twitterclone.Adapters.SuggestedAccountAdapter
import com.vedansh.twitterclone.data.SuggestedAccount
import com.vedansh.twitterclone.data.User

class SuggestedAccountFragment : Fragment() , SuggestedAccountAdapter.ClickListener {
    private lateinit var suggestedAccountAdapter: SuggestedAccountAdapter
    private lateinit var recyclerView: RecyclerView
    private val listOfAccounts = mutableListOf<SuggestedAccount>()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_suggested_account , container , false)
        return view
        //return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView  = view.findViewById<RecyclerView>(R.id.rv_suggested_account )
        auth = FirebaseAuth.getInstance()
        FirebaseDatabase.getInstance().getReference().child("users").child(auth.uid.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listOfFollowings = snapshot.child("listOffollowing").value as MutableList<String>
                    FirebaseDatabase.getInstance().getReference().child("users")
                        .addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for(datasnapshot in snapshot.children){
                                    val user = datasnapshot.getValue(User::class.java)
                                    if(user?.uid.toString() != auth.uid.toString() && !listOfFollowings.contains(user?.uid.toString() )){ //uid of that user should not be equal to the current user and  user uid should also be not in listOfFolowings
                                        val suggestedAccount = SuggestedAccount(user?.userProfileImage.toString() , user?.userEmail.toString() , user?.uid.toString())
                                        listOfAccounts.add(suggestedAccount)
                                        suggestedAccountAdapter = SuggestedAccountAdapter(listOfAccounts  , requireContext() , this@SuggestedAccountFragment)
                                        recyclerView.adapter = suggestedAccountAdapter
                                        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL , false)
                                    }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                //TODO("Not yet implemented")
                            }

                        })
                }

                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
                }

            })
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onFollowClicked(uid : String) {
        followUser(uid)
    }
    private fun followUser(uid : String){
        FirebaseDatabase.getInstance().getReference().child("users").child(auth.uid.toString())
            .addListenerForSingleValueEvent(object  : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listOfFollowing = snapshot.child("listOffollowing").value as MutableList<String>
                    listOfFollowing.add(uid)
                    FirebaseDatabase.getInstance().getReference().child("users").child(auth.uid.toString())
                        .child("listOffollowing").setValue(listOfFollowing)
                    Toast.makeText(requireContext() , "You are successfully following the user" , Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
                }

            })

    }

}