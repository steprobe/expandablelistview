package com.steoware.testexplistview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListItemAdapter extends BaseAdapter {

    public static class ListItem {

        public String primaryText;
        public String secondaryText;

        public ListItem(String primaryText, String secondaryText) {
            this.primaryText = primaryText;
            this.secondaryText = secondaryText;
        }
    }

    private List<ListItem> mEntries = new ArrayList<ListItem>();
    private final Context mContext;

    public interface OnItemClicked {
        public void onItemClicked(int position);
    }

    public ListItemAdapter(Context context, List<ListItem> entries) {
        mContext = context;
        mEntries = entries;
    }

    @Override
    public int getCount() {
        return mEntries.size();
    }

    @Override
    public ListItem getItem(int position) {
        return mEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        ListItem entry = getItem(position);

        View item = convertView;
        if(convertView == null) {
            item = inflater.inflate(R.layout.lv_layout, parent, false);
        }

        TextView header = (TextView)item.findViewById(R.id.lv_item_header);
        header.setText(entry.primaryText);

        TextView subtextTv = (TextView)item.findViewById(R.id.lv_item_subtext);
        subtextTv.setText(entry.secondaryText);

        return item;
    }
}