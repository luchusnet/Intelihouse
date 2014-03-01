package dom.intelihouse.db;

import android.content.Context;

import com.mobandme.ada.ObjectContext;
import com.mobandme.ada.ObjectSet;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import dom.intelihouse.model.AC;

/**
 * Created by Lucho on 18/02/14.
 */
public class ApplicationDataContext extends ObjectContext {

    public ObjectSet<AC> ACSet;

    public ApplicationDataContext(Context pContext) throws AdaFrameworkException {
        super(pContext);

        if (this.ACSet==null)
            this.ACSet = new ObjectSet<AC>(AC.class, this);
    }
}
