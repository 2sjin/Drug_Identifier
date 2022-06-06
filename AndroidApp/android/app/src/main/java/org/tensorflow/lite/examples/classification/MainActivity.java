package org.tensorflow.lite.examples.classification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static String IP_ADDRESS = "54.180.144.133";
    private static String TAG = "phptest";

    TextView name;
    TextView Classification;
    TextView Efficacy;
    String mJSonString=null;
    Button btn;

    // 상세페이지 버튼 클릭 시 접속할 웹사이트 주소
    String link = "https://www.health.kr/searchDrug/result_drug.asp?drug_cd=2021082400002";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String gid = getIntent().getStringExtra("id");
        Log.d("안녕",gid);
        name = findViewById(R.id.nameText);
        Classification = findViewById(R.id.ClassificationText);
        Efficacy = findViewById(R.id.EfficacyText);
        InsertData task = new InsertData();
        task.execute(gid);

        // 상세정보 버튼 추가
        Button btn = findViewById(R.id.webButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(link);
                intent.setData(uri);
                startActivity(intent);
            }
        });

    }
    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;
        @Override
        public void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        public void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            if (result == null){
                Log.d(TAG, "response - " + result);
            }
            else {
                Log.d("조준호",result);
                mJSonString = result;
                showResult();

            }
        }
        @Override
        public String doInBackground(String... params) {

            String serverID = (String)params[0];
            String serverURL = "http://"+IP_ADDRESS+"/info.php";
            String postParameters = "id="+serverID;

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }
        }
        private void showResult(){
            try{
                JSONObject jsonObject = new JSONObject(mJSonString);
                JSONArray jsonArray = jsonObject.getJSONArray("user");

                for(int i=0;i<jsonArray.length();i++) {

                    JSONObject item = jsonArray.getJSONObject(i);

                    String nid = item.getString("id");
                    String nname = item.getString("name");
                    String nClass = item.getString("Classification");
                    String nEfficacy = item.getString("Efficacy");

                    name.setText(nname);
                    Classification.setText(nClass);
                    Efficacy.setText(nEfficacy);
                }

            }
            catch (JSONException e){
                Log.d(TAG,"showResult : ",e);
            }

        }

    }


}
