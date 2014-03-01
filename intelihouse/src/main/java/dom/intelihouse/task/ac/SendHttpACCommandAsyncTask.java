package dom.intelihouse.task.ac;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;

import dom.intelihouse.IntelihouseApp;
import dom.intelihouse.helpers.ConfigurationHelper;
import dom.intelihouse.helpers.HttpHelper;

/**
 * Created by Lucho on 28/02/14.
 */
public class SendHttpACCommandAsyncTask extends AsyncTask<String, Void, String> {

    private String command = null;
    private String ip = null;
    private String port = null;

    public SendHttpACCommandAsyncTask(String cmd) {
        this.command = cmd;
        this.ip = ConfigurationHelper.getLocalIpArduino();
        this.port = ConfigurationHelper.getPortArduino();
    }

    @Override
    protected String doInBackground(String... strings) {
        InputStream inputStream = null;
        String result = "";
        // create HttpClient
        HttpClient httpclient = new DefaultHttpClient();
        // make GET request to the given URL
        try {
            HttpResponse httpResponse = httpclient.execute(new HttpGet("http://"+this.ip+":"+this.port+"/?command="+this.command));
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            // convert inputstream to string
            if(inputStream != null)
                result = HttpHelper.convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (this.ip==null ||this.port==null) {
            Toast.makeText(IntelihouseApp.getInstance().getApplicationContext(), "Debe configurar la ip y puerto del servidor", 3000).show();
            cancel(true);
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Toast.makeText(IntelihouseApp.getInstance().getApplicationContext(), result, 4000).show();
    }
}
