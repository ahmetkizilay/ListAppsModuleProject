#listapps

This is a module for Android to list all installed apps with a capability to receive a specific intent. This module is intended to be an alternative to the [ShareActionProvider](http://developer.android.com/reference/android/widget/ShareActionProvider.html),  in the android library, which is limited to ```ACTION_SEND``` intent and only seems to work within the ActionBar.

### Usage
The module exposes a custom ViewGroup called ```AppListerViewGroup``` to be included in a layout xml file in your project. There are three custom attributes that can be set:

* __intent_name__: full name of the intent
* __intent_data_type__: data type for the intent (to be used in intent.setData
* __max_initial_items__: The number of items to show in view's minimal mode.

#### Sample Usage
Sample working example can be found in the app module. Check out ```ShareWithListAppsActivity``` class and the ```activity_share_with_listapps.xml``` files for sample usage.

To list activities with which you can share images:
```xml
<com.ahmetkizilay.modules.listapps.AppListerViewGroup
    android:id="@+id/wgAppListerTop"
    android:layout_width="250dp"
    android:layout_height="wrap_content"
    android:layout_gravity="top|end"
    android:layout_marginRight="80dp"
    app:intent_data_type="image/*"
    app:intent_name="android.intent.action.SEND"
    app:max_initial_items="4"/>
```