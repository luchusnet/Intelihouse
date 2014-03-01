package dom.intelihouse.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import dom.intelihouse.IntelihouseApp;
import dom.intelihouse.R;
import dom.intelihouse.task.TestConfigurationAsyncTask;
import dom.intelihouse.ui.contentAdapters.IndexContent;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link dom.intelihouse.ui.ListActivity}
 * in two-pane mode (on tablets) or a {@link dom.intelihouse.ui.DetailItemActivity}
 * on handsets.
 */
public class ConfigurationItemFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private IndexContent.IndexItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ConfigurationItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = IndexContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
         final View rootView = inflater.inflate(R.layout.configuration_fragment_item, container, false);

        final Button button = (Button) rootView.findViewById(R.id.connectbutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String ip = ((EditText) rootView.findViewById(R.id.ipText)).getText().toString();
                String port = ((EditText) rootView.findViewById(R.id.portText)).getText().toString();

                IntelihouseApp.getInstance().savePreferences(rootView.getContext(),IntelihouseApp.IP_KEY,ip);
                IntelihouseApp.getInstance().savePreferences(rootView.getContext(),IntelihouseApp.PORT_KEY,port);

                TestConfigurationAsyncTask test = new TestConfigurationAsyncTask(rootView.getContext(), ip, port);
                test.execute();
            }
        });

        String ip = IntelihouseApp.getInstance().loadStringPreferences(rootView.getContext(), IntelihouseApp.IP_KEY);
        String port = IntelihouseApp.getInstance().loadStringPreferences(rootView.getContext(),IntelihouseApp.PORT_KEY);
        if (!ip.equals(""))
            ((EditText) rootView.findViewById(R.id.ipText)).setText(ip);
        if (!port.equals(""))
            ((EditText) rootView.findViewById(R.id.portText)).setText(port);
        return rootView;
    }
}
