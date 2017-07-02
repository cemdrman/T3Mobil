package jekirdek.com.t3mobil.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import jekirdek.com.t3mobil.actitiyes.BilgilerFragment;
import jekirdek.com.t3mobil.actitiyes.DersKatilimFragment;
import jekirdek.com.t3mobil.actitiyes.YoklamaListesiFragment;

/**
 * Created by cem
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position ==0) {
            return new YoklamaListesiFragment();
        } else if (position == 1) {
            return new DersKatilimFragment();
        } else return new BilgilerFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
