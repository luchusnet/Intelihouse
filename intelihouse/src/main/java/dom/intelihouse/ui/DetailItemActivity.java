package dom.intelihouse.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import dom.intelihouse.IntelihouseApp;
import dom.intelihouse.R;
import dom.intelihouse.ui.fragments.ConfigurationItemFragment;
import dom.intelihouse.ui.fragments.ListACFragment;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link dom.intelihouse.ui.fragments.ConfigurationItemFragment}.
 */
public class DetailItemActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_item_activity);

        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            String fId = getIntent().getStringExtra(IntelihouseApp.FRAGMENT);
            if (fId.equals(IntelihouseApp.FRAGMENT_CONFIGURATION)){
                getActionBar().setTitle("Configuración");
                Bundle arguments = new Bundle();
                arguments.putString(ConfigurationItemFragment.ARG_ITEM_ID, getIntent().getStringExtra(ConfigurationItemFragment.ARG_ITEM_ID));
                ConfigurationItemFragment fragment = new ConfigurationItemFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction().add(R.id.item_detail_container, fragment).commit();
            } else if (fId.equals(IntelihouseApp.FRAGMENT_LIST_AC)) {
                getActionBar().setTitle("Aires Acondicionados");
                ListACFragment fragment = new ListACFragment();
                 getSupportFragmentManager().beginTransaction().add(R.id.item_detail_container, fragment).commit();
            }


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, ListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
