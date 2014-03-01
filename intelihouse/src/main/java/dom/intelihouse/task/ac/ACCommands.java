package dom.intelihouse.task.ac;

import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import dom.intelihouse.IntelihouseApp;
import dom.intelihouse.helpers.ConfigurationHelper;
import dom.intelihouse.helpers.HttpHelper;
import dom.intelihouse.model.AC;

/**
 * Created by Lucho on 28/02/14.
 */
public class ACCommands {

    public static void SendACState(AC ac) {
        final String cmd = generateCommand(ac);
        SendHttpACCommandAsyncTask at = new SendHttpACCommandAsyncTask(cmd);
        at.execute();
    }

    private static String generateCommand(AC ac) {
        String nodo = ac.getNodoArduino();
        String id = ac.getArduinoId();
        String fan= "0";
        if (!ac.isAutoFan())
            fan = String.valueOf(ac.getFan());

        if (ac.isPower())
            return nodo+":"+id+":"+String.valueOf(ac.getTemp())+"_"+fan+"_"+ac.getModeShort()+"+";
        else
            return nodo+":"+id+":00_X_X+"; //off command
    }


}
