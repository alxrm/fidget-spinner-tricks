package rm.com.fidgetspinnertricks.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;
import rm.com.fidgetspinnertricks.ui.fragment.BaseFragment;

/**
 * Created by alex
 */

public final class BasePagerAdapter extends FragmentPagerAdapter {

  private final List<BaseFragment> pages;

  public BasePagerAdapter(@NonNull FragmentManager fragmentManager,
      @NonNull List<BaseFragment> pages) {
    super(fragmentManager);
    this.pages = pages;
  }

  @NonNull @Override public Fragment getItem(int position) {
    return pages.get(position);
  }

  @Override public int getCount() {
    return pages.size();
  }
}
