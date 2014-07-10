package com.ahmetkizilay.modules.listapps;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ahmetkizilay on 09.07.2014.
 */
public class AppListerAdapter extends BaseAdapter {
    private List<AppInfo> mItems;
    private Context context;


    public AppListerAdapter(Context context, List<AppInfo> items) {
        this.context = context;
        this.mItems = items;
    }

    public void setData(List<AppInfo> data) {
        this.mItems = data;
    }

    @Override
    public int getCount() {
        return this.mItems.size();
    }

    @Override
    public Object getItem(int i) {
        return this.mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if(view == null) {
            LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
            view = inflater.inflate(R.layout.applister_item, viewGroup, false);

            holder = new ViewHolder();

            holder.img = (ImageView) view.findViewById(R.id.imgAppIcon);
            holder.txt = (TextView) view.findViewById(R.id.txtAppName);

            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        AppInfo item = this.mItems.get(position);
        holder.txt.setText(item.getLabel());

        try {
            Resources resources = context.getPackageManager().getResourcesForApplication(item.getPackageName());
            holder.img.setImageDrawable(resources.getDrawable(item.getAppIcon()));
        }
        catch(Exception exp) {
            holder.img.setImageResource(R.drawable.ic_launcher);
        }

        return view;
    }

    private static class ViewHolder {
        private ImageView img;
        private TextView txt;
    }
}
