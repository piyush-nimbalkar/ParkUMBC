package storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.message.BasicNameValuePair;

public class ServerStorage extends AsyncTask<String, Void, ServerResponse> {

    static final String TAG = "STORAGE_SERVER";
    private long parking_lot_id;
    private boolean is_parked;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ServerResponse doInBackground(String... params) {
        ServerResponse serverResponse = null;
        String base_url = "http://mpss.csce.uark.edu/~devan/";

        String url = base_url + (is_parked ? "park.php" : "checkout.php");

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> value = new LinkedList<NameValuePair>();
        value.add(new BasicNameValuePair("parking_lot_id", String.valueOf(parking_lot_id)));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(value));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String responseString = reader.readLine();
            serverResponse = new ServerResponse(httpResponse.getStatusLine().getStatusCode(), responseString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return serverResponse;
    }

    @Override
    protected void onPostExecute(ServerResponse response) {
        super.onPostExecute(response);
        Log.d(TAG, response.getMessage());
    }

    public void store(long parking_lot_id, boolean is_parked) {
        this.parking_lot_id = parking_lot_id;
        this.is_parked = is_parked;
        execute();
    }
}