package rm.com.fidgetspinnertricks.ui.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import io.techery.properratingbar.ProperRatingBar;
import rm.com.fidgetspinnertricks.R;
import rm.com.fidgetspinnertricks.data.entity.Trick;
import rm.com.fidgetspinnertricks.util.Logger;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.bumptech.glide.request.RequestOptions.centerCropTransform;
import static com.bumptech.glide.request.RequestOptions.diskCacheStrategyOf;

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

  @Override public void bind(@NonNull final Trick model) {
    title.setText(model.title);
    level.setRating(model.level);

    Logger.d("Trick in holder: " + model.title);

    Glide.with(itemView.getContext())
        .load(model.preview)
        .apply(diskCacheStrategyOf(DiskCacheStrategy.DATA))
        .apply(centerCropTransform())
        .transition(withCrossFade())
        .into(preview);

    itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (clickListener != null) {
          clickListener.onItemClick(model);
        }
      }
    });
  }

  @OnClick(R.id.item_trick_share) void onShare() {
    Logger.d("Share!");
  }
}
