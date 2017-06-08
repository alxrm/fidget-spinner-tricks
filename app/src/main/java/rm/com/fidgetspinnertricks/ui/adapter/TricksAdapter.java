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
  @Override public TrickHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new TrickHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trick, parent, false));
  }
}
