package rm.com.fidgetspinnertricks.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.View;
import java.util.List;
import javax.inject.Inject;
import rm.com.fidgetspinnertricks.FidgetApplication;
import rm.com.fidgetspinnertricks.data.entity.Trick;
import rm.com.fidgetspinnertricks.data.provider.ProviderListener;
import rm.com.fidgetspinnertricks.data.provider.TricksProvider;
import rm.com.fidgetspinnertricks.ui.adapter.TricksAdapter;
import rm.com.fidgetspinnertricks.ui.holder.BaseHolder;
import rm.com.fidgetspinnertricks.ui.holder.TrickHolder;
import rm.com.fidgetspinnertricks.util.Preconditions;

/**
 * Created by alex
 */

public final class TricksListFragment extends BaseContentFragment
    implements BaseHolder.OnClickListener<Trick>, ProviderListener<Pair<String, List<Trick>>>,
    TrickHolder.OnDoneListener {
  private static final String KEY_LEAGUE = "KEY_LEAGUE";

  @Inject TricksProvider provider;

  private TricksAdapter adapter;
  private String league;

  public static TricksListFragment newInstance(@NonNull String league) {
    final Bundle args = new Bundle();
    final TricksListFragment fragment = new TricksListFragment();

    args.putString(KEY_LEAGUE, league);
    fragment.setArguments(args);

    return fragment;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    toggleContent(false, true);

    adapter = new TricksAdapter();
    adapter.setOnClickListener(this);
    adapter.setOnDoneListener(this);
    content.setAdapter(adapter);

    provider.provide(league, this);
  }

  @Override protected void injectDependencies(@NonNull FidgetApplication app) {
    super.injectDependencies(app);
    app.injector().inject(this);
  }

  @Override protected void unwrapArguments(@NonNull Bundle args) {
    super.unwrapArguments(args);
    league = args.getString(KEY_LEAGUE);
  }

  @Override public void onItemClick(@NonNull Trick item) {
    navigateTo(TrickFragment.newInstance(item));
  }

  @Override public void onDone(int position, @NonNull Trick item) {
    item.learned = !item.learned;
    item.save();

    adapter.notifyItemChanged(position);
  }

  @Override public void onProviderResult(@NonNull Pair<String, List<Trick>> payload) {
    final String queryLeague = payload.first;
    final List<Trick> tricks = payload.second;

    Preconditions.check(!queryLeague.isEmpty(),
        "For whatever reason we got unknown league, seem like a bug!");

    if (queryLeague.equals(league)) {
      adapter.updateData(tricks);
      toggleContent(true, tricks.isEmpty());
    }
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
