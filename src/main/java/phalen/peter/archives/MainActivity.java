package phalen.peter.archives;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.app.AlertDialog;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import phalen.peter.archives.CustomViewPager;

public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //this holds tabs and the app title
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //this holds the webeviews and manages toggling
        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.setPagingEnabled(false);

        //this holds tabs
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons(); //this method is defined below



    }



    protected void onResume() {
        super.onResume();

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        //here we are checking connectivity settings and prompting user to change their settings if any of the following
        if (    connectivityManager.getActiveNetworkInfo() == null   //we can't get info from the connectivity manager, OR
                || !connectivityManager.getActiveNetworkInfo().isAvailable() // no active network is available, OR
                || !connectivityManager.getActiveNetworkInfo().isConnected()) { //active network is not connected

            //////////AlertDialog prompting internet settings
            AlertDialog.Builder NoConnectionAlertDialog = new AlertDialog.Builder(MainActivity.this);

            // Setting Dialog Title
            NoConnectionAlertDialog.setTitle("Unable to connect");

            // Setting Dialog Message
            NoConnectionAlertDialog.setMessage("You need a network connection to use this application. Please turn on mobile network or Wi-Fi in Settings.");

            // Setting Icon to Dialog
            NoConnectionAlertDialog.setIcon(R.mipmap.ic_launcher);

            // Setting Positive "Yes" Button
            NoConnectionAlertDialog.setPositiveButton("Settings",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            // Activity transfer to connectivity settings
                            startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });

            // Setting Negative "NO" Button
            NoConnectionAlertDialog.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event
                            dialog.cancel();
                        }
                    });

            // Showing Alert Message
            NoConnectionAlertDialog.show();
            //////////ENDAlertDialog prompting connectivity settings

        }


    }

    //this method is fired by the little refresh button defined in xml
    public void refresh(View view) {
        viewPager.setAdapter(null); //'delete' viewpager
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //these javascript calls request a reload from the urls associated with the fragments
        adapter.addFragment(new NewsFeedFragment("javascript:window.location.reload( true )"), "NEWSFEED");
        adapter.addFragment(new MapFragment("javascript:window.location.reload( true )"), "MAP");
        //reset adapter
        viewPager.setAdapter(adapter);
    }

    //this method is fired by the little question mark button defined in xml
    public void help(View view) {

        //take us to the Guardian Website
        String url = "http://www.theguardian.com/thecounted";
        //android OS is smart enough to figure out which browser to open, so
        //a generic "ACTION_VIEW" call is used
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void setupTabIcons() {

        //get tabs as textViews
        TextView tabNews = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        TextView tabMap = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);

        //Set text for tabs
        tabNews.setText("Newsfeed");
        tabMap.setText("Map");

        //assign icons to the tabs
        tabNews.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_newsfeed, 0, 0);
        tabMap.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_map, 0, 0);

        //set negative padding or the tabindiciators occlude the tab text
        tabNews.setCompoundDrawablePadding(-12);
        tabMap.setCompoundDrawablePadding(-12);

        //set these parameters to the tabs
        tabLayout.getTabAt(0).setCustomView(tabNews);
        tabLayout.getTabAt(1).setCustomView(tabMap);

    }

    //this method sets up our custom viewpager
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewsFeedFragment("*****"), "NEWSFEED"); //custom website URLs
        adapter.addFragment(new MapFragment("*****"), "MAP");
        viewPager.setAdapter(adapter);
    }

    //this defines our custom viewpageradapter class
    //it figures out what tab the user taps and tells us which
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
