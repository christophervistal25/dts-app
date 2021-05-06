package www.seotoolzz.com.dts;

import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import www.seotoolzz.com.dts.Database.DB;
import www.seotoolzz.com.dts.Database.Models.Document;
import www.seotoolzz.com.dts.Fragments.HomeFragment;
import www.seotoolzz.com.dts.Fragments.ProfileFragment;


public class MainActivity extends AppCompatActivity
{
    private TabLayout mTabLayout;



    private int[] mTabsIcons = {
            R.drawable.ic_baseline_format_list_bulleted_24,
            R.drawable.ic_baseline_send_24,
            R.drawable.ic_baseline_account_circle_24,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        List<Document> documentList = DB.getInstance(this).documentDao().get();

        askPermissionForCamera();

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        if (viewPager != null)
            viewPager.setAdapter(pagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if (mTabLayout != null) {
            mTabLayout.setupWithViewPager(viewPager);

            for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = mTabLayout.getTabAt(i);
                if (tab != null)
                    tab.setCustomView(pagerAdapter.getTabView(i));
            }

            mTabLayout.getTabAt(0).getCustomView().setSelected(true);
        }

        findViewById(R.id.fab).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ScanQRActivity.class);
            startActivity(intent);
        });
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public final int PAGE_COUNT = 2;

        private final String[] mTabsTitle = {"List", "Profile"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public View getTabView(int position) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.toolbar, null);
            ImageView icon = view.findViewById(R.id.icon);
            icon.setImageResource(mTabsIcons[position]);
            return view;
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return HomeFragment.newInstance(2);

//                return ListScanFragment.newInstance(1);

                case 1:
                    return ProfileFragment.newInstance(3);

//                case 2:
            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabsTitle[position];
        }
    }

    private void askPermissionForCamera() {
        // Have access for camera to run the QR Code Scanner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.CAMERA}, 1);
            }
        }
    }
}
