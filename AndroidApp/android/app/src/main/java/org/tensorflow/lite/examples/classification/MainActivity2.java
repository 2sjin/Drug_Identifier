package org.tensorflow.lite.examples.classification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity2 extends AppCompatActivity {
    String mJSonString=null;
    private static String IP_ADDRESS = "54.180.144.133";
    private static String TAG = "phptest";
    private ListView mListView;
    ArrayList<SampleData> mitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mitem = new ArrayList<SampleData>();
        String [] names = getIntent().getStringArrayExtra("classNames");
        String [] values = getIntent().getStringArrayExtra("classValues");
        mListView = (ListView) findViewById(R.id.listView_main_list);
        InsertData_sub task = new InsertData_sub();

        String []sepID = {names[0].replaceAll("[^0-9]", ""),
                            names[1].replaceAll("[^0-9]", ""),
                            names[2].replaceAll("[^0-9]", "")};
        task.execute(sepID);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("id", sepID[0]);
                startActivity(intent);
            }
        });





    }
    public class SampleData {
        private int poster;
        private String medicinName;
        private String Classification;

        public SampleData(int poster, String medicinName, String Classification){
            this.poster = poster;
            this.medicinName = medicinName;
            this.Classification = Classification;
        }

        public int getPoster()
        {
            return this.poster;
        }

        public String getMedicinName()
        {
            return this.medicinName;
        }

        public String getClassification()
        {
            return this.Classification;
        }
    }
    public class MyAdapter extends BaseAdapter {

        Context mContext = null;
        LayoutInflater mLayoutInflater = null;
        ArrayList<SampleData> sample;

        public MyAdapter(Context context, ArrayList<SampleData> data) {
            mContext = context;
            sample = data;
            mLayoutInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return sample.size();
        }

        @Override
        public SampleData getItem(int position) {
            return sample.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View converView, ViewGroup parent) {
            View view = mLayoutInflater.inflate(R.layout.list_view, null);

            WebView imageView = (WebView)view.findViewById(R.id.poster);    // 리스트에 출력할 이미지
            TextView mMedicine = (TextView)view.findViewById(R.id.medicine_Name);
            TextView mClassification = (TextView)view.findViewById(R.id.Classification_Name);

            mMedicine.setText(sample.get(position).getMedicinName());
            mClassification.setText(sample.get(position).getClassification());

            // 리스트에 출력할 이미지 설정
            imageView.loadUrl("https://www.pharm.or.kr:442/images/sb_photo/big3/A11A1270A006002.jpg");
            imageView.getSettings().setUseWideViewPort(true);
            imageView.getSettings().setLoadWithOverviewMode(true);

            return view;
        }
    }

    class InsertData_sub extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MainActivity2.this,
                    "Please Wait", null, true, true);
        }

        @Override
        public void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("전체출력",result);
            /*
            String[] st = result.split("},");
            for(int i=0;i<3;i++){
                String token = st[i];
                Log.d("Token"+i,token);
            }
            */
            progressDialog.dismiss();
            Log.d(TAG, "response - " + result);
            if (result == null){

                Log.d("첫번쨰 실패",errorString);
            }
            else {
                mJSonString = result;
                showResult();
            }
        }
        public String doInBackground(String... params) {
            List<String> testList = new ArrayList<String>();
            for (int i=0;i<3;i++) {
                String serverID = (String) params[i];
                Log.d("serverID 확인", serverID);
                String serverURL = "http://" + IP_ADDRESS + "/info.php";
                String postParameters = "id=" + serverID;

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
                    if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                        inputStream = httpURLConnection.getInputStream();
                    } else {
                        inputStream = httpURLConnection.getErrorStream();
                    }


                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }


                    bufferedReader.close();

                    testList.add(sb.toString().trim());

                } catch (Exception e) {

                    Log.d(TAG, "InsertData: Error ", e);
                    errorString = e.toString();
                    return null;
                }
            }
            //return String.valueOf(testList);
            return testList.get(0);
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
                    Log.d("nname+nClass",nname+nClass);
                    mitem.add(new SampleData(i,nname,nClass));



                }
                final MyAdapter myAdapter = new MyAdapter(MainActivity2.this,mitem);
                mListView.setAdapter(myAdapter);

            }
            catch (JSONException e){
                Log.d(TAG,"showResult : ",e);
            }

        }

    }
}