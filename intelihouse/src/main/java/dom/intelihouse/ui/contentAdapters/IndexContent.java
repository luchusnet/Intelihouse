package dom.intelihouse.ui.contentAdapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dom.intelihouse.IntelihouseApp;

public class IndexContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<IndexItem> ITEMS = new ArrayList<IndexItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, IndexItem> ITEM_MAP = new HashMap<String, IndexItem>();

    static {
        addItem(new IndexItem(IntelihouseApp.FRAGMENT_LIST_AC, "Aire Acondicionado"));
        addItem(new IndexItem(IntelihouseApp.FRAGMENT_CONFIGURATION, "Configuración"));
    }

    private static void addItem(IndexItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class IndexItem {
        public String id;
        public String content;

        public IndexItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
