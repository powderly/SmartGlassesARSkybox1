package com.monyetmabuk.rajawali.tutorials;

import java.text.DecimalFormat;
import java.util.Map;

import rajawali.RajawaliActivity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.monyetmabuk.rajawali.tutorials.ExamplesApplication.Category;
import com.monyetmabuk.rajawali.tutorials.ExamplesApplication.ExampleItem;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;
import com.monyetmabuk.rajawali.tutorials.examples.general.WeatherFragment;

public class SmartEyeActivity extends Activity implements
		OnChildClickListener, SensorEventListener {

    private static final String FRAGMENT_TAG = "rajawali";
    private static final String PREF_FIRST_RUN = "RajawaliExamplesActivity.PREF_FIRST_RUN";
    private static final String KEY_TITLE = SmartEyeActivity.class.getSimpleName() + ".KEY_TITLE";

    //DRAWER JAMS
    private DrawerLayout mDrawerLayout;
    private ExpandableListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    boolean mDrawerOpen = false;
    String currentFragment;
    boolean FirstRun = true;
    boolean homeGroup = true;
    boolean weatherGroup = false;
    boolean sportsGroup = false;

    //SENSOR JAMS
    private SensorManager aSensorManager;
    static final String TAG = "RotationSensor";
    private Sensor aRotationVectorSensor;
    public float aRx, aRy, aRz;
    float[] aresult = new float[3];
    private final float[] aRotationMatrix = new float[9];
    public float[] aOrientation = new float[3];

    private static Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SmartEyeActivity.context = getApplicationContext();

        setContentView(R.layout.activity_main);


        aSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        aRotationVectorSensor = aSensorManager.getDefaultSensor(
                Sensor.TYPE_ROTATION_VECTOR);
        aSensorManager.registerListener(this, aRotationVectorSensor, 10000);

        // Configure the action bar
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // Configure the drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ExpandableListView) findViewById(R.id.left_drawer);
        mDrawerList.setGroupIndicator(null);
        mDrawerList.setAdapter(new ExampleAdapter(getApplicationContext(),
                ExamplesApplication.ITEMS));
        mDrawerList.setOnChildClickListener(this);
        mDrawerList.setDrawSelectorOnTop(true);
        mDrawerList.expandGroup(0, true);
        //mDrawerList.expandGroup(1,true);
        //mDrawerList.expandGroup(2,true);
        //mDrawerList.expandGroup(3,true);
        mDrawerLayout.setFocusableInTouchMode(true);
        mDrawerList.setFocusableInTouchMode(true);


        // Configure the drawer toggle
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);


        if (savedInstanceState == null) {
            onChildClick(null, null, 0, 0, 0);
        } else {
            setTitle(savedInstanceState.getString(KEY_TITLE));
        }

        // Open the drawer the very first run.
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
       /* if (!prefs.contains(PREF_FIRST_RUN)) {
            prefs.edit().putBoolean(PREF_FIRST_RUN, false).apply();
            mDrawerLayout.openDrawer(mDrawerList);
        }*/
    }

    public static Context getAppContext() {
        return SmartEyeActivity.context;
    }

    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
            aSensorManager.unregisterListener(this);
        } catch (Exception e) {
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final ExampleItem exampleItem;

        switch (item.getItemId()) {
            case android.R.id.home:
                if (mDrawerToggle.onOptionsItemSelected(item))
                    return true;

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
                                int groupPosition, int childPosition, long id) {
        Category category = Category.values()[groupPosition];
        ExampleItem exampleItem = ExamplesApplication.ITEMS.get(category)[childPosition];

        launchFragment(category, exampleItem);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(KEY_TITLE, getTitle().toString());
    }

    private void launchFragment(Category category, ExampleItem exampleItem) {
        final FragmentManager fragmentManager = getFragmentManager();

        // Close the drawer
        mDrawerLayout.closeDrawers();

        setTitle(exampleItem.title);
        final FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (FirstRun){
            FirstRun = false;
        } else if(exampleItem.title.equals("Weather")){
            category = Category.GENERAL;
            //exampleItem = exampleItem.title("Weather Menu");

        } else if(exampleItem.title.equals("Home")) {
            category = Category.HOME;

        } else if(exampleItem.title.equals("Scan Views")) {
            category = Category.SPORTS;



        }
        // Set fragment title



        try {
            final Fragment fragment = (Fragment) exampleItem.exampleClass.getConstructors()[0].newInstance();

            final Bundle bundle = new Bundle();
            bundle.putString(AExampleFragment.BUNDLE_EXAMPLE_URL, exampleItem.getUrl(category));
            fragment.setArguments(bundle);

            fragmentManager.popBackStack();
            if (fragmentManager.findFragmentByTag(FRAGMENT_TAG) != null)
                transaction.addToBackStack(null);

            transaction.replace(R.id.content_frame, fragment, FRAGMENT_TAG);
            transaction.commit();

            currentFragment = exampleItem.title;

        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        if(homeGroup){

        }else if(weatherGroup){

        }else if(sportsGroup){

        }*/
    }


    private static final class ExampleAdapter extends BaseExpandableListAdapter {

        private static final int COLORS[] = new int[]{0xFF0099CC, 0xFF9933CC,
                0xFF669900, 0xFFFF8800, 0xFFCC0000};

        private final Map<Category, ExampleItem[]> mItems;
        private final LayoutInflater mInflater;
        private final Category[] mKeys;

        public ExampleAdapter(Context context,
                              Map<Category, ExampleItem[]> items) {
            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mItems = items;
            mKeys = Category.values();
        }

        @Override
        public ExampleItem getChild(int groupPosition, int childPosition) {
            return mItems.get(mKeys[groupPosition])[childPosition];
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            ExpandableListView mExpandableListView = (ExpandableListView) parent;
            mExpandableListView.expandGroup(groupPosition);

            final ExampleItem item = getChild(groupPosition, childPosition);
            final ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(
                        R.layout.drawer_list_child_item, null);
                holder = new ViewHolder(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.textViewItemTitle.setText(item.title);

            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return mItems.get(mKeys[groupPosition]).length;
        }

        @Override
        public Category getGroup(int groupPosition) {
            return mKeys[groupPosition];
        }

        @Override
        public int getGroupCount() {
            return mKeys.length;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            final String groupName = getGroup(groupPosition).getName();
            final ViewHolder holder;

            View view = new FrameLayout(context);

            //if (convertView == null) {
            //convertView = mInflater.inflate(
            //		R.layout.drawer_list_group_item, null);
            //holder = new ViewHolder(convertView);
            //} else {
            //holder = (ViewHolder) convertView.getTag();
            //}

            //holder.imageViewItemColor.setBackgroundColor(COLORS[groupPosition
            //	% COLORS.length]);
            //holder.textViewItemTitle.setText(groupName);

            return view;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }

    private static final class ViewHolder {
        public ImageView imageViewItemColor;
        public TextView textViewItemTitle;

        public ViewHolder(View convertView) {
            imageViewItemColor = (ImageView) convertView
                    .findViewById(R.id.item_color);
            textViewItemTitle = (TextView) convertView
                    .findViewById(R.id.item_text);
            convertView.setTag(this);
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        float[] history = new float[2];

        // we received a sensor event. it is a good practice to check
        // that we received the proper event
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            // convert the rotation-vector to a 4x4 matrix. the matrix
            // is interpreted by Open GL as the inverse of the
            // rotation-vector, which is what we want.


            //float w = mGLSurfaceView.getMeasuredWidth(),
            //        h = mGLSurfaceView.getMeasuredHeight();

            SensorManager.getRotationMatrixFromVector(aRotationMatrix, event.values);
            SensorManager.remapCoordinateSystem(aRotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, aRotationMatrix);
            SensorManager.getOrientation(aRotationMatrix, aOrientation);

            DecimalFormat dff = new DecimalFormat("0.00");

            aRy = Float.parseFloat(dff.format(aOrientation[0]));
            aRx = Float.parseFloat(dff.format(aOrientation[1]));
            aRz = Float.parseFloat(dff.format(aOrientation[2]));


            //Log.d(TAG, "Pitch =" + rx);
            //Log.d(TAG, "Roll =" + rz);

            ///CHANGING THE CODE TO WORK WITH THE DMC PROTOTYPE, SWITCHING PITCH AND ROLL
            DecimalFormat df = new DecimalFormat("0");
            aresult[0] = Float.parseFloat(df.format(Math.toDegrees(aRy))); //Azimuth
            aresult[1] = Float.parseFloat(df.format(Math.toDegrees(aRx))); //Pitch
            aresult[2] = Float.parseFloat(df.format(Math.toDegrees(aRz))); //Roll

            //Log.d(TAG, "Azimuth =" + aresult[0]);
            // Log.d(TAG, "Pitch =" + aresult[1]);
            //Log.d(TAG, "Roll =" + aresult[2]);

            history[0] = aresult[0]; //currently unused
            history[1] = aresult[1];

            int posVal = (int) scale(aresult[1], -25, 25, 0, 2);
            if (posVal > 2) {
                posVal = 2;
            } else if (posVal < 0) {
                posVal = 0;
            }

            mDrawerList.setSelectionFromTop((int) posVal, mDrawerList.getHeight() / 2);
            //mDrawerList.setItemChecked((int )posVal, true);
            //Log.d(TAG, "listPosition = " + posVal);

            if (aresult[0] < -5 & aresult[1] < -10) {

                mDrawerLayout.openDrawer(Gravity.LEFT);
            //every possible way i can think to make the damn thing focus.
                mDrawerLayout.setFocusable(true);
                mDrawerList.setFocusable(true);
                mDrawerList.setFocusableInTouchMode(true);
                mDrawerLayout.setFocusableInTouchMode(true);
                mDrawerLayout.requestFocus();
                mDrawerList.requestFocus();

                if (!mDrawerOpen) {
                    playSuccessSound();
                }
                mDrawerOpen = true;
            } else if (mDrawerOpen && aresult[0] > 0) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                if (mDrawerOpen) {
                    playSuccessSound();
                }
                mDrawerOpen = false;
            }

            //DecimalFormat df = new DecimalFormat("0.00");
            //TextView panTextView = (TextView) this.getActivity().findViewById(R.id.PanValTextView);
            //panTextView.setText(String.valueOf(df.format(ry)));

            //TextView pitchTextView = (TextView) this.getActivity().findViewById(R.id.PitchValTextView2);
            //pitchTextView.setText(String.valueOf(df.format(rx)));

            //TextView yawTextView = (TextView) this.getActivity().findViewById(R.id.YawValTextView);
            //yawTextView.setText(String.valueOf(df.format(rz)));
        }

        ////Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, -tx, -ty, rz, upX, upY, upZ);
        //Matrix.multiplyMM();


        // Matrix.rotateM();
        // Matrix.rotateM
        // Matrix.rotateM()
    }

    public float scale(float x, float old_min, float old_max, float new_min, float new_max) {
        float old_range = old_max - old_min;
        float new_range = new_max - new_min;
        return new_min + (x - old_min) * new_range / old_range;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void playSuccessSound() {
        // Play sound to acknowledge action
        AudioManager audio = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        audio.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD, 3);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long lcurrentTime = System.currentTimeMillis();
        //Log.e(TAG, "onKeyDown - keyCode:" + keyCode + ", event.getRepeatCount():" + event.getRepeatCount());
        //lpreviousTime = lcurrentTime;

        switch (keyCode) {
//			case KeyEvent.KEYCODE_BACK:
//				if (event.getRepeatCount() == 0) {
//					this.moveTaskToBack(true);
//					return true;
//				}
//				break;
            case KeyEvent.KEYCODE_DPAD_UP:
                //onFlickUp();
                break;

            case KeyEvent.KEYCODE_DPAD_DOWN:
//				finishVideo();
                //onFlickDown();
                break;

            case KeyEvent.KEYCODE_DPAD_RIGHT:
                //onFlickForward();
                break;

            case KeyEvent.KEYCODE_DPAD_LEFT:
                //onFlickBackward();
                break;

            case KeyEvent.KEYCODE_ENTER:
                if(currentFragment.equals("Weather") ||currentFragment.equals("Weather Menu") ){
                    playSuccessSound();
                    launchFragment(Category.GENERAL, ExamplesApplication.ITEMS.get(Category.GENERAL)[2]);
                }else if(currentFragment.equals("Forecast")){
                    playSuccessSound();
                    launchFragment(Category.GENERAL, ExamplesApplication.ITEMS.get(Category.GENERAL)[0]);
                }else if(currentFragment.equals("Home")) {
                    playSuccessSound();
                    launchFragment(Category.GENERAL, ExamplesApplication.ITEMS.get(Category.GENERAL)[1]);
                }

                if(currentFragment.equals("Sports") || currentFragment.equals("Scan Views")){
                   if(ExamplesApplication.playvid = true){
                       playSuccessSound();
                       ExamplesApplication.playvid=false;
                   }else if(ExamplesApplication.playvid = false){
                       ExamplesApplication.playvid=false;
                   }
                }
                //onTap();
                break;

            case KeyEvent.KEYCODE_F1:
//kdh test				onFlickUp();
                break;

            case KeyEvent.KEYCODE_F2:
//kdh test				onFlickDown();
                break;

            case KeyEvent.KEYCODE_F3:
                 if(currentFragment.equals("Sports") || currentFragment.equals("Scan Views")){
                    ExamplesApplication.stepbackward = true;
                     playSuccessSound();
                }
                break;

            case KeyEvent.KEYCODE_F4:
                if(currentFragment.equals("Sports") || currentFragment.equals("Scan Views")){
                    ExamplesApplication.stepforward = true;
                    playSuccessSound();
                }
                break;

            case KeyEvent.KEYCODE_F5:
//kdh test				onTap();
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if((currentFragment.equals("Weather") ||currentFragment.equals("Weather Menu")) && ExamplesApplication.clicked ){
                    launchFragment(Category.GENERAL, ExamplesApplication.ITEMS.get(Category.GENERAL)[2]);
                    ExamplesApplication.clicked=false;
                    playSuccessSound();
                }else if(currentFragment.equals("Forecast")){
                    launchFragment(Category.GENERAL, ExamplesApplication.ITEMS.get(Category.GENERAL)[0]);
                    playSuccessSound();
                }else if(currentFragment.equals("Home")) {
                    launchFragment(Category.GENERAL, ExamplesApplication.ITEMS.get(Category.GENERAL)[1]);
                    playSuccessSound();
                }


                if(currentFragment.equals("Sports") || currentFragment.equals("Scan Views")){
                    ExamplesApplication.stepforward = true;
                    //Log.d(TAG, "stepforward =" + ExamplesApplication.stepforward);
                }
                   break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                if(currentFragment.equals("Sports") || currentFragment.equals("Scan Views")){
                    if(ExamplesApplication.playvid == true){
                        playSuccessSound();
                        ExamplesApplication.playvid = false;
                    }else if(ExamplesApplication.playvid == false){
                        ExamplesApplication.playvid = true;
                    }
                   //ExamplesApplication.stepbackward = true;
                }
                break;
           }

        return super.onKeyDown(keyCode, event);

    }
}
