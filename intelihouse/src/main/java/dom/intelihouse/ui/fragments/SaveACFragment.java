package dom.intelihouse.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mobandme.ada.Entity;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import dom.intelihouse.IntelihouseApp;
import dom.intelihouse.R;
import dom.intelihouse.db.ApplicationDataContext;
import dom.intelihouse.model.AC;

/**
 * Created by Lucho on 23/02/14.
 */
public class SaveACFragment extends Fragment {

    public static final String FRAGMENT_MODE = "fragment_mode";
    public static final String ARG_AC_ID = "arg_ac_id";
    private static String modo;
    private ApplicationDataContext appDataContext;
    private AC ac = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments().containsKey(FRAGMENT_MODE)) {
            this.modo = getArguments().getString(FRAGMENT_MODE);
        }
        if (getArguments().containsKey(ARG_AC_ID)) {
            long acId = getArguments().getLong(ARG_AC_ID);
            try {
                appDataContext = new ApplicationDataContext(getActivity().getApplicationContext());
                ac = appDataContext.ACSet.getElementByID(acId);
            } catch (AdaFrameworkException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.save_ac_item_menu){
            EditText descripTV = (EditText) getActivity().findViewById(R.id.descripcionAC);
            EditText nodoTV = (EditText) getActivity().findViewById(R.id.nodoAC);
            EditText identTV = (EditText) getActivity().findViewById(R.id.identificadorET);
            if (!descripTV.getText().toString().equals("") && !nodoTV.getText().toString().equals("")) {
                try {
                    if (this.modo.equals(IntelihouseApp.FRAGMENT_MODES.ALTA.toString())) {
                        ac = new AC();
                        ac.setDescripcion(descripTV.getText().toString());
                        ac.setNodoArduino(nodoTV.getText().toString());
                        ac.setArduinoId(identTV.getText().toString());
                        ac.setAutoFan(false);
                        ac.setPower(false);
                        ac.setMode(IntelihouseApp.AC_MODES.COLD.toString());
                        ac.setTemp(23);
                        ac.setFan(1);
                        ac.setStatus(Entity.STATUS_NEW);
                        appDataContext.ACSet.add(ac);
                        appDataContext.ACSet.save();
                    } else {
                        ac.setDescripcion(descripTV.getText().toString());
                        ac.setNodoArduino(nodoTV.getText().toString());
                        ac.setArduinoId(identTV.getText().toString());
                        ac.setStatus(Entity.STATUS_UPDATED);
                        appDataContext.ACSet.save(ac);
                    }

                } catch (AdaFrameworkException e) {
                    e.printStackTrace();
                }
            }
            getFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.saveacfragment_menu, menu);
        try {
            appDataContext = new ApplicationDataContext(getActivity().getApplicationContext());
        } catch (AdaFrameworkException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.ac_save_fragment, container, false);

        if (this.modo.equals(IntelihouseApp.FRAGMENT_MODES.ALTA.toString())) {
            getActivity().getActionBar().setTitle("Agregar AC");
        } else {
            getActivity().getActionBar().setTitle("Modificar AC");
        }

        if (ac!=null) {
            EditText descrip = (EditText) rootView.findViewById(R.id.descripcionAC);
            descrip.setText(ac.getDescripcion());
            EditText nodo = (EditText) rootView.findViewById(R.id.nodoAC);
            nodo.setText(ac.getNodoArduino());
            EditText identi = (EditText) rootView.findViewById(R.id.identificadorET);
            identi.setText(ac.getArduinoId());
        }
        return rootView;
    }
}
