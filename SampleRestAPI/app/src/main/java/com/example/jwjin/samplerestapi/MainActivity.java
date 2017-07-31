package com.example.jwjin.samplerestapi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

  TextView tvMain;
  int progressValue;
  BackgroundTask task;
  HttpURLConnection myConnection;
  JsonReader jsonReader;
  HashMap<String, String> resultJson;


  private class BackgroundTask extends AsyncTask<String, Integer, Long> {


    protected void onPreExecute() {
      progressValue = 0;
//            progress.setProgress(value);  //progress bar를 초기화
    }
    protected Long doInBackground(String... strs) {
//      for (String str : strs) {
//        Log.d("", str);
//      }

      try {
        // Create URL
        URL githubEndpoint = new URL("http://172.16.9.75:3000/test");

        // Create connection

        myConnection =
                (HttpURLConnection) githubEndpoint.openConnection();

        myConnection.setRequestMethod("GET");
        myConnection.setDoOutput(true);
        myConnection.getOutputStream().write("{\"foo\": \"bar\"}".getBytes());

        if (myConnection.getResponseCode() == 200) {
          // Success
          // Further processing here
          InputStream responseBody = myConnection.getInputStream();
          InputStreamReader responseBodyReader =
                  new InputStreamReader(responseBody, "UTF-8");
          jsonReader = new JsonReader(responseBodyReader);
          jsonReader.beginObject(); // Start processing the JSON object

          resultJson = new HashMap<>();
          while (jsonReader.hasNext()) { // Loop through all keys
            String key = jsonReader.nextName(); // Fetch the next key
            String val = jsonReader.nextString();
            resultJson.put(key, val);
            Log.d("", key+":"+val);
          }

          Log.d("", "success!!!!!!");
        } else {
          // Error handling code goes here
        }

        //publishProgress(++progressValue); progress bar를 업데이트 push
      }
      catch(Exception e) {
        Log.d("", e.toString());
      }
      finally {
        try {
          jsonReader.close();
        }
        catch(Exception e) {
          Log.d("err",e.toString());
        }
        myConnection.disconnect();
      }
      return 100L;

    }

    protected void onProgressUpdate(Integer... values) {
//            progress.setProgress(values[0].intValue());  progress bar를 업데이트 push

    }

    protected void onPostExecute(Long result) {
//            progress.setProgress(0);
//            textView.setText("Finished.");
      tvMain.setText(resultJson.toString());

      Log.d("", "");    // access to resultJson

    }

    protected void onCancelled() {

      throw new RuntimeException("Stub!");

    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    tvMain = (TextView) findViewById(R.id.tvMain);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
      }
    });

    task = new BackgroundTask();
    task.execute("http://naver.com", "data1", "data2", "data3");
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
