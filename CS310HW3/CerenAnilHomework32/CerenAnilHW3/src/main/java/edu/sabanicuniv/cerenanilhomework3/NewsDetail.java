package edu.sabanicuniv.cerenanilhomework3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class NewsDetail extends AppCompatActivity {

    NewsItem selectedNews;
    ImageView imgDetail;
    TextView txtDetailTitle;
    TextView txtDetailDate;
    TextView txtDetailNews;
    int newsID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        imgDetail = findViewById(R.id.imgdetail);
        txtDetailTitle = findViewById(R.id.txtdetailtitle);
        txtDetailDate = findViewById(R.id.txtdetaildate);
        txtDetailNews = findViewById(R.id.txtdetailnews);


        selectedNews = (NewsItem)getIntent().getSerializableExtra("selectedNewsItem");

        txtDetailTitle.setText(selectedNews.getTitle());
        txtDetailDate.setText(new SimpleDateFormat("dd/MM/yyy").format(selectedNews.getNewsDate()));
        txtDetailNews.setText(selectedNews.getText());
        newsID = selectedNews.getId();

        if(selectedNews.getBitmap() == null){
            new ImageDownloadTask(imgDetail).execute(selectedNews);
        }
        else{
            imgDetail.setImageBitmap(selectedNews.getBitmap());
        }

        getSupportActionBar().setTitle("News Details");

        ActionBar currentBar =getSupportActionBar();
        currentBar.setHomeButtonEnabled(true);
        currentBar.setDisplayHomeAsUpEnabled(true);
        currentBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_ios_white_18dp);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.mn_comment) {
            Intent i = new Intent(getApplicationContext(), CommentsActivity.class);
            i.putExtra("selectedNewsID", newsID);
            startActivity(i);
        }
        else if(item.getItemId() == android.R.id.home){
            finish();
        }
        return true;
    }

}