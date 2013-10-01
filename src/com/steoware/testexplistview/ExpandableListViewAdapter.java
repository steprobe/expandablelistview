package com.steoware.testexplistview;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.WrapperListAdapter;

import com.steoware.testexplistview.ExpanderToggleManager.ToggleButtonTag;

public class ExpandableListViewAdapter implements WrapperListAdapter {

    private final ListItemAdapter mWrapped;
    private ExpanderToggleManager mToggleManager;

    private final View.OnClickListener mOnToggleClicked = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            final int position = ((ToggleButtonTag)view.getTag()).position;;
            mToggleManager.toggle(position);
        }
    };

    public ExpandableListViewAdapter(ListItemAdapter adapter) {
        mWrapped = adapter;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return mWrapped.areAllItemsEnabled();
    }

    @Override
    public boolean isEnabled(int position) {
        return mWrapped.isEnabled(position);
    }

    @Override
    public int getCount() {
        return mWrapped.getCount();
    }

    @Override
    public Object getItem(int position) {
        return mWrapped.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return mWrapped.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return mWrapped.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //This takes the wrapped view and adds it to the wrappers frame layout

        if (convertView == null) {
            Context context = parent.getContext();

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.lv_wrapper_layout, parent, false);

            View toggleButton = convertView.findViewById(R.id.lv_item_button);
            toggleButton.setOnClickListener(mOnToggleClicked);
        }

        ViewGroup expandablePanel = (ViewGroup)convertView.findViewById(R.id.expandanblePanel);

        ToggleButtonTag tag = new ToggleButtonTag(position, expandablePanel);
        convertView.findViewById(R.id.lv_item_button).setTag(tag);

        ViewGroup contentContainer = (ViewGroup)convertView.findViewById(R.id.contentContainer);

        View content = null;
        if (contentContainer.getChildCount() > 0) {
            content = contentContainer.getChildAt(0);
        }

        View wrappedView = mWrapped.getView(position, content, contentContainer);
        if (content != wrappedView) {
            if (content != null) {
                contentContainer.removeView(content);
            }

            contentContainer.addView(wrappedView);
        }

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return mWrapped.getViewTypeCount();
    }

    @Override
    public boolean hasStableIds() {
        return mWrapped.hasStableIds();
    }

    @Override
    public boolean isEmpty() {
        return mWrapped.isEmpty();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mWrapped.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mWrapped.unregisterDataSetObserver(observer);
    }

    @Override
    public ListItemAdapter getWrappedAdapter() {
        return mWrapped;
    }

    void setToggleManager(ExpanderToggleManager toggleManager) {
        mToggleManager = toggleManager;
        mWrapped.notifyDataSetChanged();
    }
}
