package rm.com.fidgetspinnertricks.ui.holder;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import io.techery.properratingbar.ProperRatingBar;
import rm.com.fidgetspinnertricks.R;
import rm.com.fidgetspinnertricks.data.entity.Trick;

/**
 * Created by alex
 */

public final class TrickHolder extends BaseHolder<Trick> {

  //@BindView(R.id.item_trick_done) ImageView done;
  //@BindView(R.id.item_trick_share) ImageView date;
  @BindView(R.id.item_trick_level) ProperRatingBar level;
  @BindView(R.id.item_trick_preview) ImageView preview;
  @BindView(R.id.item_trick_title) TextView title;

  public TrickHolder(View itemView) {
    super(itemView);
  }

  @Override public void bind(@NonNull Trick model) {
    title.setText(model.title);
    level.setRating(model.level);

    Glide.with(itemView.getContext()).load(Uri.parse(model.path)).into(preview);
  }

  @OnClick(R.id.item_trick_share) void onShare() {

  }
}
