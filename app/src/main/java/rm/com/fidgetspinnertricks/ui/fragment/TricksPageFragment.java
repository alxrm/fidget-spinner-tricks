package rm.com.fidgetspinnertricks.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindArray;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import java.util.Arrays;
import java.util.List;
import rm.com.fidgetspinnertricks.R;
import rm.com.fidgetspinnertricks.ui.Navigator;
import rm.com.fidgetspinnertricks.ui.adapter.BasePagerAdapter;
import rm.com.fidgetspinnertricks.util.Preconditions;

/**
 * Created by alex
 */

public final class TricksPageFragment extends BaseFragment {

  @BindString(R.string.page_name_tricks) String title;
  @BindArray(R.array.trick_levels) String[] trickLevels;
  @BindArray(R.array.trick_leagues) String[] trickLeagues;
  @BindColor(R.color.color_accent) int selectedColor;

  @BindView(R.id.pager) ViewPager pager;

  private TabLayout tabs;

  public static TricksPageFragment newInstance() {
    return new TricksPageFragment();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_pager, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupTabs();

    pager.setAdapter(getSectionsPagerAdapter());
    pager.setOffscreenPageLimit(2);

    tabs.setupWithViewPager(pager);
    setupTabTitles(tabs);
    toggleTabs(true);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    toggleTabs(false);
  }

  @NonNull @Override String title() {
    return title;
  }

  @Override boolean hasBackButton() {
    return false;
  }

  @Override boolean isNested() {
    return false;
  }

  private void toggleTabs(boolean show) {
    if (tabs != null) {
      tabs.setVisibility(show ? View.VISIBLE : View.GONE);
    }
  }

  private void setupTabs() {
    final Navigator navigator = navigator();
    Preconditions.checkNotNull(navigator, "Navigator cannot be null at this stage");

    tabs = navigator.tabs();
    tabs.setSelectedTabIndicatorColor(selectedColor);
    tabs.removeAllTabs();
  }

  @NonNull private BasePagerAdapter getSectionsPagerAdapter() {
    final List<BaseFragment> pages =
        Arrays.<BaseFragment>asList(TricksListFragment.newInstance(trickLeagues[0]),
            TricksListFragment.newInstance(trickLeagues[1]));

    return new BasePagerAdapter(getChildFragmentManager(), pages);
  }

  private void setupTabTitles(@NonNull TabLayout tabs) {
    for (int i = 0; i < tabs.getTabCount(); i++) {
      final TabLayout.Tab tab = tabs.getTabAt(i);

      if (tab != null) {
        tab.setText(trickLevels[i]);
      }
    }
  }
}
