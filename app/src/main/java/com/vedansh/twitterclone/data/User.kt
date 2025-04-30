package com.vedansh.twitterclone.data
// data to store
class User (
    val userEmail : String = "",
    val userProfileImage : String = "" ,
    val listOffollowing : List<String> = listOf(),
    val listOftweets  :List<String> = listOf(),
    val uid : String = ""
) {
}