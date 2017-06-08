package rm.com.fidgetspinnertricks.ui.fragment;

import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rm.com.fidgetspinnertricks.FidgetApplication;
import rm.com.fidgetspinnertricks.R;
import rm.com.fidgetspinnertricks.ui.Navigator;

/**
 * Created by alex
 */
public abstract class BaseFragment extends Fragment {

  protected Unbinder unbinder;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);

    final FidgetApplication app = application();
    final Bundle args = getArguments();

    if (args != null) {
      unwrapArguments(args);
    }

    if (app != null) {
      injectDependencies(app);
    }
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    //if (!isNested()) {
    //  //noinspection WrongConstant
    //  getActivity().setRequestedOrientation(orientation());
    //}

    unbinder = ButterKnife.bind(this, view);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      navigateBack();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override public void onResume() {
    super.onResume();

    if (!isNested()) {
      updateAppbar(title(), hasBackButton());
    }
  }

  @Override public void onDestroy() {
    super.onDestroy();
    if (unbinder != null) {
      unbinder.unbind();
    }
  }

  @NonNull abstract String title();

  abstract boolean hasBackButton();

  abstract boolean isNested();

  protected int orientation() {
    return ActivityInfo.SCREEN_ORIENTATION_USER;
  }

  protected void unwrapArguments(@NonNull Bundle args) {
  }

  protected void injectDependencies(@NonNull FidgetApplication app) {
  }

  protected void navigateBack() {
    final Navigator navigator = navigator();

    if (navigator != null) {
      navigator.back();
    }
  }

  @Nullable final protected Navigator navigator() {
    return (Navigator) getActivity();
  }

  @Nullable final protected FidgetApplication application() {
    final Activity owner = getActivity();
    final Application app = owner != null ? owner.getApplication() : null;

    return ((FidgetApplication) app);
  }

  final protected void navigateTo(@NonNull BaseFragment fragment) {
    final Navigator navigator = navigator();

    if (navigator != null) {
      navigator.to(fragment);
    }
  }

  final void updateAppbar(@NonNull String title, boolean hasBackButton) {
    final Navigator navigator = navigator();

    if (navigator != null) {
      navigator.updateAppbar(title, hasBackButton);
    }
  }

  final protected void ask(String message, final OnAskListener action) {
    new AlertDialog.Builder(getActivity()).setMessage(message)
        .setNegativeButton(R.string.ask_negative, new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
          }
        })
        .setPositiveButton(R.string.ask_positive, new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            action.onAction();
          }
        })
        .create()
        .show();
  }

  protected interface OnAskListener {
    void onAction();
  }
}