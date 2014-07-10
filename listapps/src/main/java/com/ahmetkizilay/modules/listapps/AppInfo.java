package com.ahmetkizilay.modules.listapps;

/**
 * Created by ahmetkizilay on 09.07.2014.
 */
public class AppInfo {
    private String mPackageName;
    private String mAppName;
    private String mLabel;
    private int mAppIcon;

    public AppInfo(String packageName, String appName, String label, int appIcon) {
        this.mPackageName = packageName;
        this.mAppIcon = appIcon;
        this.mAppName = appName;
        this.mLabel = label;
    }

    public String getAppName() {
        return this.mAppName;
    }

    public int getAppIcon() {
        return this.mAppIcon;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getLabel() {
        return this.mLabel;
    }
}
