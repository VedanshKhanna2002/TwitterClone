package com.vedansh.twitterclone.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vedansh.twitterclone.R

class TweetAdapter(
    private val listOfTweets  : List<String>
) : RecyclerView.Adapter<TweetAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_tweet , parent , false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val currentTweet = listOfTweets[position]
        holder.tweet.text = currentTweet
    }

    override fun getItemCount(): Int {
        return listOfTweets.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tweet  : TextView = itemView.findViewById<TextView>(R.id.text_tweet)

    }
}