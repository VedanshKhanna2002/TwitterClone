package com.vedansh.twitterclone.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vedansh.twitterclone.R
import com.vedansh.twitterclone.data.SuggestedAccount
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text

class SuggestedAccountAdapter(
    private val listOfAccounts  : List<SuggestedAccount>,
    private val context  :Context,
    private val clickListener : ClickListener
) : RecyclerView.Adapter<SuggestedAccountAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view  = LayoutInflater.from(parent.context).inflate(R.layout.layout_suggested_account , parent , false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val currentAccount   = listOfAccounts[position]
        holder.profileemail.text = currentAccount.profileemail
        Glide.with(context)
            .load(currentAccount.profileImage)
            .into(holder.profileimage)

        holder.followbtn.setOnClickListener {
            clickListener.onFollowClicked()

        }
    }


    interface ClickListener{
        fun onFollowClicked()
    }

    override fun getItemCount(): Int {
        return listOfAccounts.size
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val profileimage : CircleImageView = itemView.findViewById<CircleImageView>(R.id.suggested_account_profile)
        val profileemail : TextView = itemView.findViewById<TextView>(R.id.suggested_account_email)
        val followbtn : Button = itemView.findViewById<Button>(R.id.follow_button)

    }

}