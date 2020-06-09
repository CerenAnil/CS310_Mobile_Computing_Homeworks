package edu.sabanicuniv.cerenanilhomework3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    ProgressDialog prgDialog;
    RecyclerView CommentsRecView;
    int selectedNewsID;
    List<CommentItem> cdata;
    CommentsAdapter adp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        cdata = new ArrayList<>();

        selectedNewsID = (int)getIntent().getSerializableExtra("selectedNewsID");

        CommentsRecView = findViewById(R.id.cmtrec);
        adp = new CommentsAdapter(cdata, this);
        CommentsRecView.setLayoutManager(new LinearLayoutManager(this));
        CommentsRecView.setAdapter(adp);

        getSupportActionBar().setTitle("Comments");

        ActionBar currentBar = getSupportActionBar();
        currentBar.setHomeButtonEnabled(true);
        currentBar.setDisplayHomeAsUpEnabled(true);
        currentBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_ios_white_18dp);

        CommentsTask tsk = new CommentsTask();
        tsk.execute("http://94.138.207.51:8080/NewsApp/service/news/getcommentsbynewsid");
    }

    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.comments_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.mn_addcomment) {
            Intent i = new Intent(getApplicationContext(), PostCommentActivity.class);
            i.putExtra("selectedNewsID", selectedNewsID);
            startActivity(i);
        }
        else if(item.getItemId() == android.R.id.home){
            finish();
        }
        return true;
    }

    class CommentsTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            prgDialog = new ProgressDialog(CommentsActivity.this);
            prgDialog.setTitle("Loading");
            prgDialog.setMessage("Please wait...");
            prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            prgDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            String newsid = String.valueOf(selectedNewsID);
            String urlStr = strings[0] + "/" + newsid;
            StringBuilder buffer = new StringBuilder();
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader((conn.getInputStream())));

                String line = "";
                while ((line = reader.readLine()) != null){

                    buffer.append(line);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            cdata.clear();
            try {
                JSONObject obj = new JSONObject(s);

                if(obj.getInt("serviceMessageCode") == 1){

                    JSONArray arr = obj.getJSONArray("items");

                    for(int i= 0; i< arr.length(); i++)
                    {
                        JSONObject current = (JSONObject)arr.get(i);



                        CommentItem item = new CommentItem(current.getInt("id"),
                                current.getString("name"),
                                current.getString("text")
                        );
                        cdata.add(item);

                    }

                }
                else{
                }
                Log.i("DEV", String.valueOf(cdata.size()));
                adp.notifyDataSetChanged();
                prgDialog.dismiss();
            } catch (JSONException e) {
                Log.e("DEV", e.getMessage());
            }


        }
    }

}
