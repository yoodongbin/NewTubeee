package com.example.dong.doitmission_03;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by jwjin on 27/07/2017.
 */

public class Communication extends AsyncTask<String, Integer, Long> {
  HttpURLConnection myConnection;
  JsonReader jsonReader;
  HashMap<String, String> resultJson;
  Activity mainActivity;

  public String sendHTTPData(String urlpath, JSONObject json) {
    HttpURLConnection connection = null;
    try {
      URL url=new URL(urlpath);
      connection = (HttpURLConnection) url.openConnection();
      connection.setDoOutput(true);
      connection.setDoInput(true);
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setRequestProperty("Accept", "application/json");
      OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
      streamWriter.write(json.toString());
      streamWriter.flush();
      StringBuilder stringBuilder = new StringBuilder();
      if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
        InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        String response = null;
        while ((response = bufferedReader.readLine()) != null) {
          stringBuilder.append(response + "\n");
        }
        bufferedReader.close();

        Log.d("test", stringBuilder.toString());
        return stringBuilder.toString();
      } else {
        Log.e("test", connection.getResponseMessage());
        return null;
      }
    } catch (Exception exception){
      Log.e("test", exception.toString());
      return null;
    } finally {
      if (connection != null){
        connection.disconnect();
      }
    }
  }

  protected void onPreExecute() {
  }
  protected Long doInBackground(String... strs) {
//      for (String str : strs) {
//        Log.d("", str);
//      }


    try {
      // Create URL
      JSONObject obj = new JSONObject();
      obj.put("foo","boo");
      sendHTTPData("http://172.16.9.75:3000/test", obj);

    }
    catch(Exception e) {
      Log.d("", e.toString());
    }
    finally {
    }
    return 100L;

  }

  protected void onProgressUpdate(Integer... values) {

  }

  protected void onPostExecute(Long result) {
    Log.d("", "");    // access to resultJson

  }

  protected void onCancelled() {

    throw new RuntimeException("Stub!");

  }

}
