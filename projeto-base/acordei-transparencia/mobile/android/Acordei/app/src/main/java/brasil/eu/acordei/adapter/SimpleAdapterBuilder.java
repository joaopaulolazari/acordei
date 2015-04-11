package brasil.eu.acordei.adapter;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SimpleAdapterBuilder {

    private List<Map<String, Object>> buildList;
    private List<String> keys;
    private List<Integer> ids;
    private ViewHolder viewHolder;

    private SimpleAdapterBuilder() {
        viewHolder = new ViewHolder();
    }


    public static SimpleAdapterBuilder getInstance() {
        SimpleAdapterBuilder adapter = new SimpleAdapterBuilder();

        return adapter;
    }

    public SimpleAdapterBuilder append(String id,Object value, int viewId){
        createInstances();
        if (shouldCloseLine(id)) closeLine();
        viewHolder.put(id, value);
        addKeysIfNew(id, viewId);
        return this;
    }

    private void addKeysIfNew(String id, int viewId) {
        if (!keys.contains(id)) keys.add(id);
        if (!ids.contains(viewId)) ids.add(viewId);
    }

    private boolean shouldCloseLine(String id) {
        return !keys.isEmpty() && id.equals(keys.get(0));
    }

    private void createInstances() {
        if (keys == null) keys = new ArrayList<String>();
        if (ids == null) ids = new ArrayList<Integer>();
    }

    private SimpleAdapterBuilder closeLine() {
        getBuildList().add(viewHolder);
        viewHolder = new ViewHolder();
        return this;
    }

    private List<Map<String, Object>> getBuildList() {
        if (buildList == null) buildList = new ArrayList<Map<String, Object>>();

        return buildList;
    }

    public SimpleAdapter getSimpleAdapter(Context context, int resource) {
        int[] ret = new int[ids.size()];
        Iterator<Integer> iter = ids.iterator();
        for (int i = 0; iter.hasNext(); i++) {
            ret[i] = iter.next();
        }
        String[] keysString = new String[keys.size()];
        Iterator<String> iterKey = keys.iterator();
        for (int i = 0; iterKey.hasNext(); i++) {
            keysString[i] = iterKey.next();
        }
        return new SimpleAdapter(context, buildList, resource, keysString, ret);
    }

    public class ViewHolder extends HashMap<String, Object> {

        public ViewHolder() {

        }

        ViewHolder(String id, Object value) {
            put(id, value);
        }
    }
}
