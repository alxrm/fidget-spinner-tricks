package rm.com.fidgetspinnertricks.ui.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.yqritc.scalablevideoview.ScalableVideoView;
import java.io.IOException;
import rm.com.fidgetspinnertricks.R;
import rm.com.fidgetspinnertricks.data.entity.Trick;
import rm.com.fidgetspinnertricks.util.Intents;

/**
 * Created by alex
 */

public final class TrickFragment extends BaseFragment {
  private static final String KEY_TRICK = "KEY_TRICK";

  @BindString(R.string.trick_done_msg) String trickDone;
  @BindString(R.string.trick_mark_done_msg) String trickMarkDone;

  @BindView(R.id.trick_preview) ScalableVideoView videoView;
  @BindView(R.id.trick_done) LinearLayout done;
  @BindView(R.id.trick_done_icon) ImageView doneIcon;
  @BindView(R.id.trick_done_label) TextView doneLabel;
  @BindView(R.id.trick_share) LinearLayout share;

  private Trick trick;

  public static TrickFragment newInstance(@NonNull Trick trick) {
    final Bundle args = new Bundle();
    final TrickFragment fragment = new TrickFragment();

    args.putParcelable(KEY_TRICK, trick);
    fragment.setArguments(args);

    return fragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_trick, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    updateDoneIcon();
    initVideoView();
    startVideo();
  }

  @Override protected void unwrapArguments(@NonNull Bundle args) {
    super.unwrapArguments(args);
    trick = args.getParcelable(KEY_TRICK);
  }

  @OnClick(R.id.trick_share) void onShare() {
    Intents.share(getActivity(), getString(R.string.message_share, trick.title));
  }

  @OnClick(R.id.trick_done) void onDone() {
    trick.learned = !trick.learned;
    trick.save();

    updateDoneIcon();
  }

  @NonNull @Override String title() {
    return trick.title;
  }

  @Override boolean hasBackButton() {
    return true;
  }

  @Override boolean isNested() {
    return false;
  }

  private void updateDoneIcon() {
    doneIcon.setImageResource(trick.learned ? R.drawable.ic_done_48dp : R.drawable.ic_do_48dp);
    doneLabel.setText(trick.learned ? trickDone : trickMarkDone);
  }

  private void initVideoView() {
    videoView.setKeepScreenOn(true);
    videoView.setOnTouchListener(new View.OnTouchListener() {
      @Override public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            videoView.pause();
            return true;
          case MotionEvent.ACTION_UP:
            videoView.start();
            return false;
        }

        return false;
      }
    });
  }

  private void startVideo() {
    try {
      videoView.setAssetData(trick.video);
      videoView.setLooping(true);
      videoView.prepareAsync(new MediaPlayer.OnPreparedListener() {
        @Override public void onPrepared(MediaPlayer mp) {
          videoView.start();
        }
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
