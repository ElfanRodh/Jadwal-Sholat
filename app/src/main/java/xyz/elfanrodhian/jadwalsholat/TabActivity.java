package xyz.elfanrodhian.jadwalsholat;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.elfanrodhian.jadwalsholat.Adapter.JadwalAdapter;
import xyz.elfanrodhian.jadwalsholat.Generator.ServiceGenerator;
import xyz.elfanrodhian.jadwalsholat.Model.Item;
import xyz.elfanrodhian.jadwalsholat.Model.Jadwal;
import xyz.elfanrodhian.jadwalsholat.Service.BGService;
import xyz.elfanrodhian.jadwalsholat.Service.GotService;

public class TabActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    private ViewPager mViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        startService(new Intent(this, BGService.class));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = null;
            //tab 1
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1){
                rootView = inflater.inflate(R.layout.fragment_tab, container, false);

                final GotService gotService;
                gotService = ServiceGenerator.createService(GotService.class);

                final TextView txtSubuh, txtDhuhur, txtAshar, txtMagrib, txtIsya;
                txtSubuh    = (TextView) rootView.findViewById(R.id.txtSubuh);
                txtDhuhur   = (TextView) rootView.findViewById(R.id.txtDhuhur);
                txtAshar    = (TextView) rootView.findViewById(R.id.txtAshar);
                txtMagrib   = (TextView) rootView.findViewById(R.id.txtMagrib);
                txtIsya     = (TextView) rootView.findViewById(R.id.txtIsya);

                Call<Jadwal> call = gotService.getJadwal();
                call.enqueue(new Callback<Jadwal>() {
                    @Override
                    public void onResponse(Call<Jadwal> call, Response<Jadwal> response) {
                        Log.d("SOKO","SUKSES");
                        txtSubuh.setText(": "+response.body().getItems().get(0).getFajr().toString());
                        txtDhuhur.setText(": "+response.body().getItems().get(0).getDhuhr().toString());
                        txtAshar.setText(": "+response.body().getItems().get(0).getAsr().toString());
                        txtMagrib.setText(": "+response.body().getItems().get(0).getMaghrib().toString());
                        txtIsya.setText(": "+response.body().getItems().get(0).getIsha().toString());
                    }

                    @Override
                    public void onFailure(Call<Jadwal> call, Throwable t) {
                        Log.d("SOKO","Gagal "+t);
                    }
                });
            }
            //tab 2
            else if(getArguments().getInt(ARG_SECTION_NUMBER) == 2){
                rootView = inflater.inflate(R.layout.fragment_tab2, container, false);
                GotService gotService;
                Button btnReload, btnToday, btnTab;
                RecyclerView rvJadwal;
                final JadwalAdapter mJadwalAdapter;

                final List<Item> mJadwalList = new ArrayList<>();
                RecyclerView.LayoutManager mLayoutManager;
                final TextView txtCity, txtLatLng;

                gotService = ServiceGenerator.createService(GotService.class);
                txtCity = (TextView) rootView.findViewById(R.id.txtCity);
                txtLatLng = (TextView) rootView.findViewById(R.id.txtLatLng);
                mJadwalAdapter = new JadwalAdapter(rootView.getContext(), mJadwalList);
                mLayoutManager = new LinearLayoutManager(rootView.getContext());
                rvJadwal = (RecyclerView) rootView.findViewById(R.id.rvJadwal);
                rvJadwal.setLayoutManager(mLayoutManager);
                rvJadwal.setAdapter(mJadwalAdapter);

                Call<Jadwal> call = gotService.getJadwal();
                call.enqueue(new Callback<Jadwal>() {
                    @Override
                    public void onResponse(Call<Jadwal> call, Response<Jadwal> response) {
                        Log.d("SOKO","SUKSES");
                        mJadwalList.clear();
                        mJadwalList.addAll(response.body().getItems());
                        mJadwalAdapter.notifyDataSetChanged();
                        txtCity.setText(response.body().getCity().toString()+", "+response.body().getCountry().toString());
                        txtLatLng.setText(" ("+response.body().getLatitude().toString()+", "+response.body().getLongitude().toString()+" )");
                    }

                    @Override
                    public void onFailure(Call<Jadwal> call, Throwable t) {
                        Log.d("SOKO","Gagal "+t);
                    }
                });
            }
            //tab 3
            else if(getArguments().getInt(ARG_SECTION_NUMBER) == 3){
                rootView = inflater.inflate(R.layout.fragment_tab3, container, false);
                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                textView.setText("Pastikan Pengaturan Lokasi Anda Menyala");
                Button btnFind = (Button) rootView.findViewById(R.id.btnMasjid);
                btnFind.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(v.getContext(), Masjid.class);
                        startActivity(i);
                    }
                });
            }
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
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
            // Show 3 total pages.
            return 3;
        }
    }
}
