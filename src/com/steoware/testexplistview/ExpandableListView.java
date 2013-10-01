package com.steoware.testexplistview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ExpandableListView extends ListView {

    private ExpandableListViewAdapter mAdapter;

    public ExpandableListView(Context context) {
        super(context);
    }

    public ExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandableListView(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        mAdapter = new ExpandableListViewAdapter((ListItemAdapter)adapter);
        super.setAdapter(mAdapter);
    }

    public void setToggleManager(ExpanderToggleManager toggleManager) {
        mAdapter.setToggleManager(toggleManager);
    }
}
