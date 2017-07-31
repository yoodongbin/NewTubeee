package com.example.dong.doitmission_03;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends ActionBarActivity {
    Button loginBtn;
    public static final int REQUEST_CODE_MENU = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = (Button)findViewById(R.id.button1);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
//                startActivityForResult(intent,REQUEST_CODE_MENU);

                String strId = ((EditText)findViewById(R.id.editText)).getText().toString();
                String strPw = ((EditText)findViewById(R.id.editText2)).getText().toString();
                try {
                    Communication comm = new Communication();
                    JSONObject json = new JSONObject();
                    json.put("ID", strId);
                    json.put("PW", strPw);
                    json.put("data", "data");
                    json.put("data2", "data2");
                    comm.execute(json);
                }
                catch (Exception e) {
                    Log.d("Error", e.toString());
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == REQUEST_CODE_MENU){
            if(resultCode == RESULT_OK){
                int menu = intent.getExtras().getInt("menu");

                switch(menu){
                    case 1:
                        Toast.makeText(this,"고객관리",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(this,"매출관리",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(this,"상품관리",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
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


    public class Communication extends AsyncTask<JSONObject, Integer, Long> {
        HttpURLConnection myConnection;
        Activity mainActivity;
        JSONObject resultJson;

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
                OutputStreamWriter streamWriter =
                        new OutputStreamWriter(connection.getOutputStream());
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
                    resultJson = new JSONObject(stringBuilder.toString());
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

        protected Long doInBackground(JSONObject ...obj) {
//            for (String str : strs) {
//                Log.d("", str);
//            }


            try {
                // Create URL
                sendHTTPData("http://172.16.9.75:3000/userLogin", obj[0]);

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
            TextView tvResult = (TextView) findViewById(R.id.tvResult);
            try {
                String rslt;
                Log.d("=========", "--------");
                Log.d("", rslt = resultJson.get("status").toString());
//                rslt += "/";
//                JSONArray arr1 = (JSONArray)resultJson.get("data");
//                for(int i = 0; i < arr1.length(); i++) {
//                    Log.d("", rslt += arr1.getString(i));
//                    rslt += "/";
//                }
//
//                JSONObject obj1 = (JSONObject)resultJson.get("data2");
//                Log.d("", rslt += obj1.getString("one2"));
//                rslt += "/";
//
//                JSONArray arr2 = (JSONArray)resultJson.get("data3");
//                JSONObject obj2 = arr2.getJSONObject(0);
//                Log.d("", rslt += obj2.getString("one3"));


                Log.d("=========", "--------");

                tvResult.setText(rslt);
            }
            catch(Exception e) {

            }

//            tvResult.setText();
        }

        protected void onCancelled() {

            throw new RuntimeException("Stub!");

        }

    }

}
