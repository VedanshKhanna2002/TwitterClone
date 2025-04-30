package com.vedansh.twitterclone

import android.app.ComponentCaller
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import java.util.UUID

class ProfileActivity : AppCompatActivity() {
    lateinit var circularimage : CircleImageView
    lateinit var btnopen : Button
    lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        init()
        btnopen.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent , 100)
        }


        val mainView = findViewById<View>(R.id.main)
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        } else {
            Log.e("ProfileActivity", "Main view not found!")
        }
    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        if(requestCode == 100 && resultCode == RESULT_OK){
            circularimage.setImageURI(data?.data)
            uploadProfileImage(data?.data)
        }
        super.onActivityResult(requestCode, resultCode, data, caller)
    }

    private fun uploadProfileImage(uri : Uri?){
        val profileImageName = UUID.randomUUID().toString() + ".jpg"
        val storageref = FirebaseStorage.getInstance().getReference().child("profileImages/$profileImageName")
        storageref.putFile(uri!!).addOnSuccessListener {
            val result = it.metadata?.reference?.downloadUrl //to get the link of the image
            result?.addOnSuccessListener {
                FirebaseDatabase.getInstance().reference.child("users").child(auth.uid.toString())
                    .child("userProfileImage").setValue(it.toString())
            }

        }

    }
    fun init(){
        circularimage = findViewById<CircleImageView>(R.id.profile_image)
        btnopen = findViewById<Button>(R.id.open_gallery)
        auth = FirebaseAuth.getInstance()
        FirebaseDatabase.getInstance().reference.child("users").child(auth.uid.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val link = snapshot.child("userProfileImage").value.toString()
                    if(link.isNotBlank()){
                        Glide.with(this@ProfileActivity)
                            .load(link)
                            .into(circularimage)
                    }else{
                        circularimage.setImageResource(R.drawable.ic_launcher_background)
                        Toast.makeText(this@ProfileActivity , "There is an error" , Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    //TODO("Not yet implemented")
                }

            })

    }
}
