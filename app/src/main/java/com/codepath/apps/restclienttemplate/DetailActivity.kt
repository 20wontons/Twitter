package com.codepath.apps.restclienttemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.apps.restclienttemplate.models.Tweet

private const val TAG = "DetailActivity"
class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        Log.i(TAG, "onCreate")

        val ivProfileImage = findViewById<ImageView>(R.id.ivProfileImage)
        val tvUserName = findViewById<TextView>(R.id.tvUsername)
        val tvScreenName = findViewById<TextView>(R.id.tvScreenname)
        val tvTweetBody = findViewById<TextView>(R.id.tvTweetBody)
        val tvTimestamp = findViewById<TextView>(R.id.tvTimestamp)

        val tweet = intent.getParcelableExtra(TWEET_EXTRA) as Tweet

        tvUserName.text = tweet.user?.name
        tvScreenName.text = "@${tweet.user?.screenName}"
        tvTweetBody.text = tweet.body
        tvTimestamp.text = TimeFormatter.getTimeStamp(tweet.createdAt)

        Glide.with(this).load(tweet.user?.publicImageUrl).into(ivProfileImage)

    }
}