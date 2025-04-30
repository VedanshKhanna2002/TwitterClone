package com.vedansh.twitterclone.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.play.integrity.internal.f
import com.vedansh.twitterclone.SuggestedAccountFragment
import com.vedansh.twitterclone.TweetFragment

class ViewPagerAdapter(frag : FragmentActivity) : FragmentStateAdapter(frag) {
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SuggestedAccountFragment()
            else -> TweetFragment()
            }

        }

    override fun getItemCount(): Int {
        return 2
    }

}



