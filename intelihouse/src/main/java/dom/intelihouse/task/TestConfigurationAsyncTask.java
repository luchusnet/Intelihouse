package dom.intelihouse.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import dom.intelihouse.IntelihouseApp;

/**
 * Created by Lucho on 10/02/14.
 */
public class TestConfigurationAsyncTask extends AsyncTask<String, Void, String> {

    private ProgressDialog pdialog;
    private Context context;
    private String ip;
    private String port;

    public TestConfigurationAsyncTask(Context contP, String ipP, String portP) {
        context = contP;
        ip = ipP;
        port = portP;
    }

    @Override
    protected String doInBackground(String... strings) {
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
             // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet("http://"+ip+":"+port+"/?command=0:ping+"));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.e("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        pdialog.dismiss();
        pdialog = null;
        Toast toast = Toast.makeText(context, result, 3000);
        toast.show();
    }

    @Override
    protected void onPreExecute() {
        pdialog=new ProgressDialog(context);
        pdialog.setCancelable(true);
        pdialog.setMessage("Probando ...");
        pdialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
