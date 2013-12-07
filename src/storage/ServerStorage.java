package storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class ServerStorage extends AsyncTask<String, Void, String> {

    private long parking_lot_id;
    private long is_parked;

    @Override
    protected String doInBackground(String... params) {
        return reportParking();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public String reportParking() {
        HttpClient httpClient = new DefaultHttpClient();
        String url;
        if (this.is_parked == 1) {
            url = "http://10.200.56.82:3000/park/" + this.parking_lot_id;        	
        } else {
        	url = "http://10.200.56.82:3000/unpark/" + this.parking_lot_id;
        }
        HttpPost httpPost = new HttpPost(url);
        String result = null;

        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            StatusLine statusLine = httpResponse.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream content = httpEntity.getContent();
                result = toString(content);
            }
        } catch (IOException httpResponseError) {
              Log.e("HTTP Response", "IO error");
            return "404 error";
        }
        return result;
    }

    @Override
    protected void onPostExecute(String resultString) {
        super.onPostExecute(resultString);
    }

    private String toString(InputStream content) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
        StringBuilder result = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException readerException) {
            readerException.printStackTrace();
        }
        return result.toString();
    }

    public void store(long parking_lot_id, long is_parked) {
        this.parking_lot_id = parking_lot_id;
        this.is_parked = is_parked;
        execute();
    }
}