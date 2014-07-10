package com.ahmetkizilay.modules.test.listappsmoduleproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ahmetkizilay.modules.listapps.AppListerViewGroup;


public class ShareWithListAppsActivity extends Activity {

    private AppListerViewGroup mAppsListViewBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_with_listapps);

        this.mAppsListViewBottom = (AppListerViewGroup) findViewById(R.id.wgAppListerBottom);
        this.mAppsListViewBottom.setListItemClickedListener(new AppListerViewGroup.ListItemClickedListener() {

            @Override
            public void onListItemClicked(String packageName, String appName) {
                handleShareIntent(packageName, appName);
                Toast.makeText(ShareWithListAppsActivity.this, "Clicked: " + appName, Toast.LENGTH_SHORT).show();
            }
        });
        this.mAppsListViewBottom.setVisibility(View.GONE);

        ImageButton btnToggleBottom = (ImageButton) findViewById(R.id.btnToggleBottom);
        btnToggleBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ShareWithListAppsActivity.this.mAppsListViewBottom.getVisibility() == View.VISIBLE) {
                    ShareWithListAppsActivity.this.mAppsListViewBottom.setVisibility(View.INVISIBLE);
                }
                else {
                    ShareWithListAppsActivity.this.mAppsListViewBottom.setMinimalMode();
                    ShareWithListAppsActivity.this.mAppsListViewBottom.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void handleShareIntent(String packageName, String appName) {
        Uri imgUri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.drawable.jazz);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "my fantastic day");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "it was such a memorable day");
        shareIntent.putExtra(Intent.EXTRA_STREAM, imgUri);
        shareIntent.setClassName(packageName, appName);

        startActivity(shareIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.demo_list_apps, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
