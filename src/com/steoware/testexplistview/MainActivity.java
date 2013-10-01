package com.steoware.testexplistview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.steoware.testexplistview.ListItemAdapter.ListItem;

public class MainActivity extends Activity {

    private ExpanderToggleManager mToggleManager;

    private static final List<ListItem> ITEMS = new ArrayList<ListItem>();

    static {
        ITEMS.add(new ListItem("Hello", "Hello details"));
        ITEMS.add(new ListItem("Another Item", "secondary text"));
        ITEMS.add(new ListItem("More things", "details"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExpandableListView lv = (ExpandableListView)findViewById(R.id.listView);
        ListItemAdapter adapter = new ListItemAdapter(getApplicationContext(), ITEMS);
        lv.setAdapter(adapter);

        mToggleManager = new ExpanderToggleManager(
                (int)getResources().getDimension(R.dimen.lvItemHeight), lv);
        lv.setToggleManager(mToggleManager);
    }
}
