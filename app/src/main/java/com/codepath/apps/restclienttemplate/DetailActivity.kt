package com.codepath.apps.restclienttemplate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
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
        val ivMediaImage = findViewById<ImageView>(R.id.ivMediaImage)
        val tvCounts = findViewById<TextView>(R.id.tvCounts)
        val ivRetweetBtn = findViewById<ImageView>(R.id.ivRetweetBtn)
        val ivLikeBtn = findViewById<ImageView>(R.id.ivLikeBtn)
        val ivReplyBtn = findViewById<ImageView>(R.id.ivReplyBtn)

        val tweet = intent.getParcelableExtra(TWEET_EXTRA) as Tweet

        tvUserName.text = tweet.user?.name
        tvScreenName.text = "@${tweet.user?.screenName}"
        tvTweetBody.text = tweet.body
        tvTimestamp.text = TimeFormatter.getTimeStamp(tweet.createdAt)
        var countText = ""
        if (tweet.retweetCount != 0){
            countText += "${tweet.retweetCount} Retweets "
        }
        if (tweet.likeCount != 0){
            countText += "${tweet.likeCount} Likes "
        }
        tvCounts.text = countText

        if (tweet.retweeted) {
            ivRetweetBtn.setImageResource(R.drawable.ic_vector_retweet)
        }
//        if (tweet.liked) {
//            ivLikeBtn.setImageResource(R.drawable.ic_vector_heart)
//        }

        ivReplyBtn.setOnClickListener {
            val intent = Intent(this, ComposeActivity::class.java)
            intent.putExtra("screenname", tweet.user?.screenName)
            intent.putExtra("replyId", tweet.id)
            startActivityForResult(intent, TimelineActivity.REQUEST_CODE)
        }

        Glide.with(this)
            .load(tweet.user?.publicImageUrl)
            .transform(CircleCrop())
            .into(ivProfileImage)

        Glide.with(this)
            .load(tweet.entities?.mediaImageUrl)
            .into(ivMediaImage)

    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        when (item.getItemId()) {
            R.id.miBtnExit -> {
                finish()
                return true
            }
            else ->
                return super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && requestCode == TimelineActivity.REQUEST_CODE) {
            val intent = Intent()
            intent.putExtra("tweet", data)
            setResult(RESULT_OK, intent)
            finish()
            //TODO issue: find out how to return to the timeline activity safely
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}