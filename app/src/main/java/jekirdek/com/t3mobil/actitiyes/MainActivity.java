package jekirdek.com.t3mobil.actitiyes;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import jekirdek.com.t3mobil.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    private DrawerLayout drawer;
    private TabLayout tabLayout;
    private String[] pageTitle = {"Yoklama Listesi", "Öğrenci Ders Katılımı", "Bilgiler"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
//        setSupportActionBar(toolbar);

        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
      //  drawer.addDrawerListener(toggle);
       // toggle.syncState();

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        for (int i = 0; i < 3; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(pageTitle[i]));
        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

       // NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_order);
      //  assert navigationView != null;
      //  navigationView.setNavigationItemSelectedListener(this);

        //set viewpager adapter
        //ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
       // viewPager.setAdapter(pagerAdapter);
//
        //change Tab selection when swipe ViewPager
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //change ViewPager page when tab selected
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.fr_yoklama_listesi) {
            viewPager.setCurrentItem(0);
        } else if (id == R.id.fr_ders_katilim) {
            viewPager.setCurrentItem(1);
        } else if (id == R.id.fr_bilgilerim) {
            viewPager.setCurrentItem(2);
        }
        return true;
    }
}
