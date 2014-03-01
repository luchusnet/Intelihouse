package dom.intelihouse;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by Lucho on 10/02/14.
 */
public class IntelihouseApp extends Application {

    //Constantes
    public static String IP_KEY = "ip_key";
    public static String PORT_KEY = "port_key";
    public static String FRAGMENT_CONFIGURATION = "fragment_configuration";
    public static String FRAGMENT_LIST_AC = "fragment_list_ac";
    public static String FRAGMENT = "fragment";

    //enumerativos
    public enum AC_MODES {HOT, COLD, DES };
    public enum FRAGMENT_MODES {ALTA, MODIFICAR};


    private static IntelihouseApp intelh = null;

    public IntelihouseApp(){
        super();
    }

    @Override
    public void onCreate() {
        intelh = this;
    }


   public static IntelihouseApp getInstance() {
        return intelh;
    }

    public void savePreferences(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void savePreferences(Context context,String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String loadStringPreferences(Context context,String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, "");
    }

    public boolean loadBooleanPreferences(Context context,String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key, false);
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

}
