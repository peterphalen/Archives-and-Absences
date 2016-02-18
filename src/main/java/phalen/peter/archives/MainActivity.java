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



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.setPagingEnabled(false);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();



    }



    protected void onResume() {
        super.onResume();
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() == null
                || !connectivityManager.getActiveNetworkInfo().isAvailable()
                || !connectivityManager.getActiveNetworkInfo().isConnected()) {

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

    public void refresh(View view) {
        viewPager.setAdapter(null);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewsFeedFragment("javascript:window.location.reload( true )"), "NEWSFEED");
        adapter.addFragment(new MapFragment("javascript:window.location.reload( true )"), "MAP");
        viewPager.setAdapter(adapter);
    }

    public void help(View view) {
        String url = "http://www.theguardian.com/us-news/ng-interactive/2015/jun/01/the-counted-police-killings-us-database";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void setupTabIcons() {
        TextView tabNews = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabNews.setText("Newsfeed");
        tabNews.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_newsfeed, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabNews);


        TextView tabMap = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabMap.setText("Map");
        tabMap.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_map, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabMap);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewsFeedFragment("*****"), "NEWSFEED");
        adapter.addFragment(new MapFragment("*****"), "MAP");
        viewPager.setAdapter(adapter);
    }

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
