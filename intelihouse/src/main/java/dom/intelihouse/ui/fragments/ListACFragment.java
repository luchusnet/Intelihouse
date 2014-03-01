package dom.intelihouse.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mobandme.ada.exceptions.AdaFrameworkException;

import java.util.List;

import dom.intelihouse.IntelihouseApp;
import dom.intelihouse.R;
import dom.intelihouse.adapters.AcAdapter;
import dom.intelihouse.db.ApplicationDataContext;
import dom.intelihouse.model.AC;

/**
 * Created by Lucho on 17/02/14.
 */
public class ListACFragment extends Fragment {


    public ListACFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.listacfragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.add_ac_item_menu){
            Bundle arguments = new Bundle();
            arguments.putString(SaveACFragment.FRAGMENT_MODE, IntelihouseApp.FRAGMENT_MODES.ALTA.toString());
            SaveACFragment fragment = new SaveACFragment();
            fragment.setArguments(arguments);
            FragmentTransaction ft  = getFragmentManager().beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.item_detail_container, fragment);
            ft.commit();
        }
        return super.onOptionsItemSelected(item);
    }


    private ApplicationDataContext appDataContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.list_ac_fragment, container, false);
        getActivity().getActionBar().setTitle("Aires Acondicionados");
        ListView listaACView = (ListView) rootView.findViewById(R.id.ac_listview);
        TextView empty = (TextView)rootView.findViewById(R.id.ac_listview_empty);
        try {
            appDataContext = new ApplicationDataContext(getActivity().getApplicationContext());
            List<AC> aCList = appDataContext.ACSet.search(false, null, null, null, null, null, null, null);
            // Creo el adapter personalizado
            AcAdapter adapter = new AcAdapter(rootView.getContext(), aCList);
            // Lo aplico
            listaACView.setEmptyView(empty);
            listaACView.setAdapter(adapter);
            listaACView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> av, View view, int position, long id) {
                    AC ac = (AC) av.getItemAtPosition(position);
                    if (ac!=null) {
                        Bundle arguments = new Bundle();
                        arguments.putLong(ACFragment.ARG_AC_ID, ac.getID());
                        ACFragment fragment = new ACFragment();
                        fragment.setArguments(arguments);
                        FragmentTransaction ft  = getFragmentManager().beginTransaction();
                        ft.addToBackStack(null);
                        ft.replace(R.id.item_detail_container, fragment);
                        ft.commit();
                    }
                }
            });
        } catch (AdaFrameworkException e) {
            e.printStackTrace();
        }

        return rootView;
    }

}
