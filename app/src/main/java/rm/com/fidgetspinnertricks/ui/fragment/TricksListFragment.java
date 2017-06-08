package rm.com.fidgetspinnertricks.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import java.util.List;
import javax.inject.Inject;
import rm.com.fidgetspinnertricks.FidgetApplication;
import rm.com.fidgetspinnertricks.data.entity.Trick;
import rm.com.fidgetspinnertricks.data.provider.ProviderListener;
import rm.com.fidgetspinnertricks.data.provider.TricksProvider;
import rm.com.fidgetspinnertricks.ui.adapter.TricksAdapter;
import rm.com.fidgetspinnertricks.ui.holder.BaseHolder;

/**
 * Created by alex
 */

public final class TricksListFragment extends BaseContentFragment
    implements BaseHolder.OnClickListener<Trick>, ProviderListener<List<Trick>> {
  private static final String KEY_PATH = "KEY_PATH";

  @Inject TricksProvider provider;
  @Inject TricksAdapter adapter;

  private String tricksPath;

  public static TricksListFragment newInstance(@NonNull String path) {
    final Bundle args = new Bundle();
    final TricksListFragment fragment = new TricksListFragment();

    args.putString(KEY_PATH, path);
    fragment.setArguments(args);

    return fragment;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    toggleContent(false, true);

    adapter.setOnClickListener(this);
    content.setAdapter(adapter);
    provider.provide(tricksPath, this);
  }

  @Override protected void injectDependencies(@NonNull FidgetApplication app) {
    super.injectDependencies(app);
    app.injector().inject(this);
  }

  @Override protected void unwrapArguments(@NonNull Bundle args) {
    super.unwrapArguments(args);
    tricksPath = args.getString(KEY_PATH);
  }

  @Override public void onItemClick(@NonNull Trick item) {
    navigateTo(TrickFragment.newInstance());
  }

  @Override public void onProvide(@NonNull List<Trick> payload) {
    adapter.updateData(payload);
    toggleContent(true, payload.isEmpty());
  }

  @NonNull @Override String title() {
    return "";
  }

  @Override boolean hasBackButton() {
    return false;
  }

  @Override boolean isNested() {
    return true;
  }
}
