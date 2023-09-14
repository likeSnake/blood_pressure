package net.bloodpressure.nice.instrument.free.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.bloodpressure.nice.instrument.free.R;
import net.bloodpressure.nice.instrument.free.fragment.HomeFragment;
import net.bloodpressure.nice.instrument.free.fragment.SettingsFragment;

public class MainAct extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private Fragment[] fragments;
    private int lastFragment;
    private BottomNavigationView mNavigationView;

    private HomeFragment mHomeFragment;
  //  private Fragment content_fragment;
    private SettingsFragment settingsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        initUI();
        initFragment();
        initListener();
    }

    public void initUI(){
        mNavigationView = findViewById(R.id.nav_view);
    }


    private void initFragment() {
        mHomeFragment = new HomeFragment(this);
        settingsFragment = new SettingsFragment(this);

        fragments = new Fragment[]{mHomeFragment,settingsFragment};
        mFragmentManager = getSupportFragmentManager();
        //默认显示HomeFragment
        mFragmentManager.beginTransaction()
                .replace(R.id.content_fragment, mHomeFragment)
                .show(mHomeFragment)
                .commit();
    }

    private void initListener() {
        mNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.navigation_home){
                    if (lastFragment != 0) {
                        MainAct.this.switchFragment(lastFragment, 0);
                        lastFragment = 0;
                    }
                    return true;
                }


                if (item.getItemId()==R.id.navigation_dashboard){
                    if (lastFragment != 1) {
                        MainAct.this.switchFragment(lastFragment, 1);
                        lastFragment = 1;
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void switchFragment(int lastFragment, int index) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.hide(fragments[lastFragment]);
        if (!fragments[index].isAdded()){
            transaction.add(R.id.content_fragment,fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }



    @Override
    protected void onResume() {
        super.onResume();
       /* MyUtil.MyLog("onResume");
        mHomeFragment.updateData();*/
    }
}