package com.ahmetkizilay.modules.listapps;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmetkizilay on 10.07.2014.
 */
public class AppListerViewGroup extends RelativeLayout {

    public AppListerViewGroup(Context context) {
        super(context);
        init();
    }

    public AppListerViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AppListerViewGroup, 0, 0);
        try {
            this.mIntentName = ta.getString(R.styleable.AppListerViewGroup_intent_name);
            this.mIntentType = ta.getString(R.styleable.AppListerViewGroup_intent_data_type);
            this.mMaxInitialDisplayLength = ta.getInt(R.styleable.AppListerViewGroup_max_initial_items, 3);
        }
        finally {
            ta.recycle();
        }

        init();
    }

    public AppListerViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private String mIntentName;
    private String mIntentType;
    private int mMaxInitialDisplayLength;

    TextView twShowMore;
    ListView lwApps;
    List<AppInfo> mAppList;

    private ListItemClickedListener mCallback;

    private void init() {
        LayoutInflater mInflater = LayoutInflater.from(this.getContext());
        mInflater.inflate(R.layout.applister_layout, this, true);

        this.mAppList = initAppList();
        lwApps = (ListView) this.findViewById(R.id.lwAppSharing);
        twShowMore = (TextView) findViewById(R.id.txtShowMore);

        if(this.mAppList.size() >= this.mMaxInitialDisplayLength) {
            setMinimalMode();
        }
        else {
            setExpandedMode();
        }

        lwApps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if(mCallback != null) {
                    AppInfo info = (AppInfo) lwApps.getAdapter().getItem(pos);
                    mCallback.onListItemClicked(info.getPackageName(), info.getAppName());
                }
            }
        });

    }

    private List<AppInfo> initAppList() {
        PackageManager pm = getContext().getPackageManager();
        Intent shareIntent = new Intent(this.mIntentName, null);
        if(this.mIntentType != null && !this.mIntentType.equals("")) {
            shareIntent.setType(this.mIntentType);
        }

        List<ResolveInfo> shareApps = pm.queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);

        List<AppInfo> appList = new ArrayList<AppInfo>();
        for(int i = 0; i < shareApps.size(); i += 1) {
            ResolveInfo thisInfo = shareApps.get(i);

            String appLabel = thisInfo.loadLabel(pm).toString();
            String appName = thisInfo.activityInfo.name;
            String packageName = thisInfo.activityInfo.packageName;
            int appIcon = thisInfo.getIconResource();

            appList.add(new AppInfo(packageName, appName, appLabel, appIcon));

        }

        return appList;
    }

    public void setExpandedMode() {
        lwApps.setAdapter(new AppListerAdapter(getContext(), this.mAppList));
        twShowMore.setVisibility(View.GONE);
    }

    public void setMinimalMode() {
        List<AppInfo> dummyList = new ArrayList<AppInfo>();
        for(int i = 0; i < this.mMaxInitialDisplayLength; i += 1) {
            dummyList.add(this.mAppList.get(i));
        }

        lwApps.setAdapter(new AppListerAdapter(getContext(), dummyList));
        twShowMore.setVisibility(View.VISIBLE);
        twShowMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setExpandedMode();
            }
        });
    }

    public void setListItemClickedListener(ListItemClickedListener listener) {
        this.mCallback = listener;
    }

    public interface ListItemClickedListener {
        public void onListItemClicked(String packageName, String appName);
    }
}
