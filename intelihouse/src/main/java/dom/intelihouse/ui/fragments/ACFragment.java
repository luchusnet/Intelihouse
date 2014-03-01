package dom.intelihouse.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.mobandme.ada.Entity;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import dom.intelihouse.IntelihouseApp;
import dom.intelihouse.R;
import dom.intelihouse.db.ApplicationDataContext;
import dom.intelihouse.model.AC;
import dom.intelihouse.task.ac.ACCommands;
import dom.intelihouse.ui.contentAdapters.IndexContent;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link dom.intelihouse.ui.ListActivity}
 * in two-pane mode (on tablets) or a {@link dom.intelihouse.ui.DetailItemActivity}
 * on handsets.
 */
public class ACFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_AC_ID = "ac_id";
    private IndexContent.IndexItem mItem;
    private ApplicationDataContext appDataContext;
    private AC ac = null;
    private Switch mySwitch;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ACFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        this.loadACData();
        this.addViewEvents();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.acfragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.remove_ac_item_enu){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Eliminar");
            builder.setMessage("¿Está seguro que desea eliminar este AC?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ac.setStatus(Entity.STATUS_DELETED);
                    try {
                        appDataContext.ACSet.save(ac);
                    } catch (AdaFrameworkException e) {
                        e.printStackTrace();
                    }
                    getFragmentManager().popBackStack();
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // I do not need any action here you might
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        } else if (item.getItemId() == R.id.edit_ac_item_menu) {
            Bundle arguments = new Bundle();
            arguments.putLong(SaveACFragment.ARG_AC_ID, ac.getID());
            arguments.putString(SaveACFragment.FRAGMENT_MODE, IntelihouseApp.FRAGMENT_MODES.MODIFICAR.toString());
            SaveACFragment fragment = new SaveACFragment();
            fragment.setArguments(arguments);
            FragmentTransaction ft  = getFragmentManager().beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.item_detail_container, fragment);
            ft.commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.ac_fragment, container, false);
        //this.addViewEvents(rootView);
        return rootView;
    }

    private void addViewEvents() {

        final View rootView = getView();
        //ON OFF
        mySwitch = (Switch) rootView.findViewById(R.id.switchACOn);
        //mySwitch.setChecked(true);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                ac.setPower(isChecked);
                sendCommandToArduino();

            }
        });

        //MODE RADIOS
        RadioGroup rG = (RadioGroup) rootView.findViewById(R.id.radioGroup);
        rG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rB = (RadioButton) rootView.findViewById(i);
                if (rB.isChecked()) {
                    ac.setMode(rB.getText().toString().toUpperCase());
                    sendCommandToArduino();
                }
            }
        });

        //TEMP BAR
        SeekBar tempSeek = (SeekBar) rootView.findViewById(R.id.tempSeekBar);
        tempSeek.setMax(2);
        //tempSeek.setProgress(0);
        tempSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView text = (TextView) rootView.findViewById(R.id.tempTextView);
                text.setText(Integer.toString(23+progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int newTemp = 23+seekBar.getProgress();
                if (newTemp!=ac.getTemp()) {
                    ac.setTemp(newTemp);
                    sendCommandToArduino();
                }
            }

        });

        //FAN BAR
        final SeekBar fanSeek = (SeekBar) rootView.findViewById(R.id.fanSeekBar);
        fanSeek.setMax(2);
        //fanSeek.setProgress(0);
        fanSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView text = (TextView) rootView.findViewById(R.id.fanTextView);
                text.setText(Integer.toString(1+progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int newFan = 1+seekBar.getProgress();
                if (newFan!=ac.getFan()) {
                    ac.setFan(newFan);
                    sendCommandToArduino();
                }

            }

        });

        //AUTO FAN
        CheckBox autoCB = (CheckBox) rootView.findViewById(R.id.autoCheckBox);
        autoCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheked) {
                if (isCheked) {
                    fanSeek.setEnabled(false);
                } else {
                    fanSeek.setEnabled(true);
                }
                ac.setAutoFan(isCheked);
                sendCommandToArduino();
            }
        });
    }

    private void sendCommandToArduino() {
        //Se persiste el estado del ac
        ac.setStatus(Entity.STATUS_UPDATED);
        try {
            appDataContext.ACSet.save(ac);
        } catch (AdaFrameworkException e) {
            e.printStackTrace();
        }
        ACCommands.SendACState(ac);
    }


    public void loadACData() {
        Activity rootView = getActivity();
        if (ac!=null) {
            try {
                appDataContext.ACSet.fill();
            } catch (AdaFrameworkException e) {
                e.printStackTrace();
            }
            getActivity().getActionBar().setTitle(ac.getDescripcion());

            Switch swOn = (Switch) rootView.findViewById(R.id.switchACOn);
            swOn.setEnabled(false);
            swOn.setChecked(ac.isPower());
            swOn.setEnabled(true);
            switch (ac.getModeEnum()) {
                case COLD:
                    RadioButton rC = (RadioButton) rootView.findViewById(R.id.coldRadioButton);
                    rC.setChecked(true);
                    break;
                case HOT:
                    RadioButton rH = (RadioButton) rootView.findViewById(R.id.hotRadioButton);
                    rH.setChecked(true);
                    break;
                case DES:
                    RadioButton rD = (RadioButton) rootView.findViewById(R.id.desRadioButton);
                    rD.setChecked(true);
                    break;
            }

            SeekBar fanSeek = (SeekBar) rootView.findViewById(R.id.fanSeekBar);
            fanSeek.setProgress(ac.getFan()-1);
            TextView fantext = (TextView) rootView.findViewById(R.id.fanTextView);
            fantext.setText(Integer.toString(ac.getFan()));

            SeekBar tempSeek = (SeekBar) rootView.findViewById(R.id.tempSeekBar);
            tempSeek.setProgress(ac.getTemp() - 23);
            TextView temptext = (TextView) rootView.findViewById(R.id.tempTextView);
            temptext.setText(Integer.toString(ac.getTemp()));


            CheckBox autoCB = (CheckBox) rootView.findViewById(R.id.autoCheckBox);
            fanSeek.setEnabled(!ac.isAutoFan());
            autoCB.setChecked(ac.isAutoFan());
        }


    }

}
