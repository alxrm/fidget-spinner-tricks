package rm.com.fidgetspinnertricks.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import rm.com.fidgetspinnertricks.R;
import rm.com.fidgetspinnertricks.data.entity.Trick;
import rm.com.fidgetspinnertricks.ui.holder.TrickHolder;

/**
 * Created by alex
 */

public final class TricksAdapter extends BaseAdapter<Trick, TrickHolder> {

  protected TrickHolder.OnDoneListener doneListener;

  @Override public TrickHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new TrickHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trick, parent, false));
  }

  @Override public void onBindViewHolder(TrickHolder holder, int position) {
    if (holder != null) {
      holder.setOnDoneListener(doneListener);
    }

    super.onBindViewHolder(holder, position);
  }

  final public void setOnDoneListener(TrickHolder.OnDoneListener doneListener) {
    this.doneListener = doneListener;
  }
}
