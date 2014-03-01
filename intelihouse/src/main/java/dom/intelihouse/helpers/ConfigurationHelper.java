package dom.intelihouse.helpers;

import dom.intelihouse.IntelihouseApp;

/**
 * Created by Lucho on 28/02/14.
 */
public class ConfigurationHelper {

    public static String getLocalIpArduino() {
        return IntelihouseApp.getInstance().loadStringPreferences(IntelihouseApp.getInstance().getApplicationContext(), IntelihouseApp.IP_KEY);
    }

    public static String getPortArduino() {
        return IntelihouseApp.getInstance().loadStringPreferences(IntelihouseApp.getInstance().getApplicationContext(), IntelihouseApp.PORT_KEY);
    }
}
