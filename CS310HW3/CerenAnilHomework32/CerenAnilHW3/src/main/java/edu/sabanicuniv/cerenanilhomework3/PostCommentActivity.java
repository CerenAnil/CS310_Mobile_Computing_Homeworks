package edu.sabanicuniv.cerenanilhomework3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PostCommentActivity extends AppCompatActivity {
    ProgressDialog prgDialog;
    int selectedNewsID;
    EditText editname;
    EditText editcomment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comment);

        editname = findViewById(R.id.editname);
        editcomment = findViewById(R.id.editcomment);

        getSupportActionBar().setTitle("Post Comment");

        ActionBar currentBar =getSupportActionBar();
        currentBar.setHomeButtonEnabled(true);
        currentBar.setDisplayHomeAsUpEnabled(true);
        currentBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_ios_white_18dp);

        selectedNewsID = (int)getIntent().getSerializableExtra("selectedNewsID");

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

      if(item.getItemId() == android.R.id.home){
            finish();
        }
        return true;
    }

    public void addcomment(View v)
    {
        PostCommentTask tsk = new PostCommentTask();
        tsk.execute("http://94.138.207.51:8080/NewsApp/service/news/savecomment", editname.getText().toString(), editcomment.getText().toString(), String.valueOf(selectedNewsID));
    }
    class PostCommentTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute() {
            prgDialog = new ProgressDialog(PostCommentActivity.this);
            prgDialog.setTitle("Loading");
            prgDialog.setMessage("Please wait...");
            prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            prgDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder strBuilder = new StringBuilder();
            String urlStr = strings[0];
            String name = strings[1];
            String message = strings[2];
            String news_id = strings[3];
            JSONObject obj = new JSONObject();
            try {
                obj.put("name", name);
                obj.put("text", message);
                obj.put("news_id", news_id);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-type", "application/json");
                conn.connect();

                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.writeBytes(obj.toString());

                if(conn.getResponseCode()== HttpURLConnection.HTTP_OK)
                {


                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = "";
                    while ((line = reader.readLine() )!= null){
                        strBuilder.append(line);
                    }


                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(PostCommentActivity.this);
                    builder.setMessage("Failed");
                    builder.setNegativeButton("OK", null);
                    builder.show();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return strBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            prgDialog.dismiss();
            finish();

        }
    }

}
