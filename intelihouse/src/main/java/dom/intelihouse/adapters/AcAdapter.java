package dom.intelihouse.adapters;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import dom.intelihouse.R;
import dom.intelihouse.model.AC;

public class AcAdapter extends BaseAdapter{

    protected LayoutInflater lyoutInflater;
    protected List<AC> items = new ArrayList<AC>();

    public AcAdapter(Context context, List<AC> items) {
        this.lyoutInflater = lyoutInflater;
        if (items!=null)
            this.items = items;
        lyoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Generamos una convertView por motivos de eficiencia
        View v = convertView;

        //Asociamos el layout de la lista que hemos creado
        if(convertView == null){
            v = lyoutInflater.inflate(R.layout.listitem_ac, null);
        }

        // Creamos un objeto directivo
        AC ac = items.get(position);
        //Rellenamos la fotografía
        //ImageView foto = (ImageView) v.findViewById(R.id.foto);
        //foto.setImageDrawable(dir.getFoto());
        TextView descripcion = (TextView) v.findViewById(R.id.descripcion);
        descripcion.setText(ac.getDescripcion());
        TextView estado = (TextView) v.findViewById(R.id.estado);
        estado.setText((ac.isPower())?"ON":"OFF");
        ImageView image = (ImageView) v.findViewById(R.id.foto);
        if (ac.isPower()) {
            image.setBackgroundResource(R.drawable.acc_list_on);
        } else {
            image.setBackgroundResource(R.drawable.acc_list_off);
        }
        // Retornamos la vista
        return v;
    }


}