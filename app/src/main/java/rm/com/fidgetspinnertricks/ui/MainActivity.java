package rm.com.fidgetspinnertricks.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rm.com.fidgetspinnertricks.R;
import rm.com.fidgetspinnertricks.ui.fragment.BaseFragment;
import rm.com.fidgetspinnertricks.ui.fragment.TricksPageFragment;

import static rm.com.fidgetspinnertricks.constant.Navigation.ICON_ARROW;
import static rm.com.fidgetspinnertricks.constant.Navigation.KEY_FRAGMENT_SAVE;

public final class MainActivity extends AppCompatActivity implements Navigator {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.tabs) TabLayout tabs;

  private Unbinder unbinder;
  private DrawerArrowDrawable navigationIcon;
  private BaseFragment currentFragment;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    unbinder = ButterKnife.bind(this);
    currentFragment = getInitialFragment(savedInstanceState, TricksPageFragment.newInstance());

    setSupportActionBar(toolbar);
    setupNavigationIcon(toolbar);

    navigateTo(currentFragment, true);
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    getSupportFragmentManager().putFragment(outState, KEY_FRAGMENT_SAVE, currentFragment);
  }

  @Override protected void onDestroy() {
    super.onDestroy();

    if (unbinder != null) {
      unbinder.unbind();
    }
  }

  @Override public void onBackPressed() {
    super.onBackPressed();
    currentFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.container);
  }

  @Override public void to(@NonNull BaseFragment dest) {
    navigateTo(dest, false);
  }

  @Override public void back() {
    onBackPressed();
  }

  @NonNull @Override public TabLayout tabs() {
    return tabs;
  }

  @Override public void updateAppbar(@NonNull String title, boolean hasBackButton) {
    toolbar.setTitle(title);
    toolbar.setNavigationIcon(hasBackButton ? navigationIcon : null);
  }

  @NonNull final protected BaseFragment getInitialFragment(@Nullable Bundle state,
      @NonNull BaseFragment defaultFragment) {
    return (state == null) ? defaultFragment
        : (BaseFragment) getSupportFragmentManager().getFragment(state, KEY_FRAGMENT_SAVE);
  }

  private void navigateTo(@NonNull BaseFragment fragment, boolean root) {
    this.currentFragment = fragment;

    final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        .replace(R.id.container, fragment);

    if (!root) {
      fragmentTransaction.addToBackStack(null);
    }

    fragmentTransaction.commit();
  }

  private void setupNavigationIcon(@NonNull Toolbar toolbar) {
    navigationIcon = new DrawerArrowDrawable(this);
    navigationIcon.setColor(Color.WHITE);
    navigationIcon.setProgress(ICON_ARROW);
    toolbar.setNavigationIcon(navigationIcon);
  }
}