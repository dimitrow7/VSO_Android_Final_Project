package net.northwestvision.vsoWeatherProject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;

import net.northwestvision.vsoWeatherProject.adapters.AlertAdapter;
import net.northwestvision.vsoWeatherProject.adapters.DailyAdapter;
import net.northwestvision.vsoWeatherProject.adapters.HourlyAdapter;
import net.northwestvision.vsoWeatherProject.datastructure.HourlyData;
import net.northwestvision.vsoWeatherProject.service.DataService;
import net.northwestvision.vsoWeatherProject.utils.DataResolver;
import net.northwestvision.vsoWeatherProject.utils.WeatherUtils;

/**
 * Created by Acer on 25.06.2016.
 */

public class MainActivity extends AppCompatActivity {

    /**
     * The PagerAdapter that will provide
     * fragments for each of the sections. We use a
     * FragmentPagerAdapter derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * FragmentStatePagerAdapter.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private static final String W_FORECAST_CURRENT = "В МОМЕНТА";
    private static final String W_FORECAST_HOURLY = "ПО ЧАСОВЕ";
    private static final String W_FORECAST_DAILY = "ПО ДНИ";
    private static final String W_FORECAST_ALERTS = "СИГНАЛИ";

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(net.northwestvision.vsoWeatherProject.R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(net.northwestvision.vsoWeatherProject.R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(net.northwestvision.vsoWeatherProject.R.id.container);
        //mViewPager.setPageTransformer(false, new FadePageTransformer());
        mViewPager.setPageTransformer(true, new RotateUpTransformer());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(net.northwestvision.vsoWeatherProject.R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    /**
     * A FragmentPagerAdapter that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 4 pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    //current weather data page
                    return W_FORECAST_CURRENT;
                case 1:
                    //hourly weather data page
                    return W_FORECAST_HOURLY;
                case 2:
                    //daily weather data page
                    return W_FORECAST_DAILY;
                case 3:
                    //daily weather data page
                    return W_FORECAST_ALERTS;
            }
            return null;
        }
    }


    public static class PlaceholderFragment extends Fragment {

        ListView listView;
        TextView txtSectionLoading;
        TextView txtSectionSummery;
        UpdateReciever updateReciever = new UpdateReciever();
        int pageNumber;

        public static PlaceholderFragment newInstance(int num) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(PlaceholderFragment.class.getName(), num);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            pageNumber = getArguments().getInt(PlaceholderFragment.class.getName());
            super.onCreate(savedInstanceState);
            //this
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(net.northwestvision.vsoWeatherProject.R.layout.fragment_main, container, false);
            listView = (ListView) rootView.findViewById(net.northwestvision.vsoWeatherProject.R.id.listView);
            txtSectionLoading = (TextView) rootView.findViewById(net.northwestvision.vsoWeatherProject.R.id.sectionLabel);
            txtSectionSummery = (TextView) rootView.findViewById(R.id.sectionSummery);
            return rootView;
        }


        @Override
        public void onPause() {
            super.onPause();
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(updateReciever);
        }

        @Override
        public void onResume() {
            super.onResume();
            //check internet availability before invoking the Intent Service for the Network call.
            if (WeatherUtils.isNetworkAvailable(getActivity())) {
                txtSectionLoading.setText("Loading...");

                //register receiver with intent filter DataService.DOWNLOADED
                LocalBroadcastManager.getInstance(getActivity()).registerReceiver(updateReciever, new IntentFilter(DataService.DOWNLOADED));

                //when ever the fragment is visible to the user it checks for the DataResolver for latest data,
                //given that isDataLoaded flag is true.
                if (DataResolver.getInstance().isDataLoaded()) {
                    getActivity().setTitle(getActivity().getResources().getString(R.string.app_name) + " | " + DataResolver.getInstance().getmForecast().timezone);
                    //set list adapter with corresponding data structure according to the page number
                    if (pageNumber == 1) {
                        txtSectionSummery.setVisibility(View.GONE);
                        listView.setAdapter(new HourlyAdapter(getActivity(), new HourlyData[]{DataResolver.getInstance().getCurrent()}));
                    } else if (pageNumber == 2) {
                        txtSectionSummery.setText(DataResolver.getInstance().getHourly().summary);
                        listView.setAdapter(new HourlyAdapter(getActivity(), DataResolver.getInstance().getHourly().data));
                    } else if (pageNumber == 3) {
                        txtSectionSummery.setText(DataResolver.getInstance().getDaily().summary);
                        listView.setAdapter(new DailyAdapter(getActivity(), DataResolver.getInstance().getDaily().data));
                    } else if (pageNumber == 4) {
                        if (DataResolver.getInstance().getmForecast().alerts != null) {
                            txtSectionSummery.setVisibility(View.GONE);
                            listView.setAdapter(new AlertAdapter(getActivity(), DataResolver.getInstance().getAlerts()));
                        } else {
                            txtSectionSummery.setText("Няма сигнали за опасно време!");
                        }
                    }
                    txtSectionLoading.setVisibility(View.GONE);
                }
            } else {
                txtSectionLoading.setText("Loading Failed...");
                Toast.makeText(getActivity(), "Internet not available to get weather forecast", Toast.LENGTH_SHORT).show();
            }


        }

        // UpdateReciever is implemented with intention to bridge the intent service data resolver and the UI
        // as to update UI on data download
        // completion from the server.
        public class UpdateReciever extends BroadcastReceiver {

            @Override
            public void onReceive(Context context, Intent intent) {
                getActivity().setTitle(getActivity().getResources().getString(R.string.app_name) + " | " + DataResolver.getInstance().getmForecast().timezone);

                //set list adapter with corresponding data structure according to the page number
                if (pageNumber == 1) {
                    txtSectionSummery.setVisibility(View.GONE);
                    listView.setAdapter(new HourlyAdapter(getActivity(), new HourlyData[]{DataResolver.getInstance().getCurrent()}));
                } else if (pageNumber == 2) {
                    txtSectionSummery.setText(DataResolver.getInstance().getHourly().summary);
                    listView.setAdapter(new HourlyAdapter(getActivity(), DataResolver.getInstance().getHourly().data));
                } else if (pageNumber == 3) {
                    txtSectionSummery.setText(DataResolver.getInstance().getDaily().summary);
                    listView.setAdapter(new DailyAdapter(getActivity(), DataResolver.getInstance().getDaily().data));
                } else if (pageNumber == 4) {
                    if (DataResolver.getInstance().getmForecast().alerts != null) {
                        txtSectionSummery.setVisibility(View.GONE);
                        listView.setAdapter(new AlertAdapter(getActivity(), DataResolver.getInstance().getAlerts()));
                    } else {
                        txtSectionSummery.setText("Няма сигнали за опасно време!");
                    }
                }

                txtSectionLoading.setVisibility(View.GONE);
            }
        }
    }

    public class FadePageTransformer implements ViewPager.PageTransformer {
        public void transformPage(View view, float position) {
            if (position <= -1.0F || position >= 1.0F) {
                view.setTranslationX(view.getWidth() * position);
                view.setAlpha(0.0F);
            } else if (position == 0.0F) {
                view.setTranslationX(view.getWidth() * position);
                view.setAlpha(1.0F);
            } else {
                // position is between -1.0F & 0.0F OR 0.0F & 1.0F
                view.setTranslationX(view.getWidth() * -position);
                view.setAlpha(1.0F - Math.abs(position));
            }
        }
    }
}
