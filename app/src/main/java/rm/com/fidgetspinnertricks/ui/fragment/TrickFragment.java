package rm.com.fidgetspinnertricks.ui.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import com.yqritc.scalablevideoview.ScalableVideoView;
import java.io.IOException;
import rm.com.fidgetspinnertricks.R;
import rm.com.fidgetspinnertricks.data.entity.Trick;

/**
 * Created by alex
 */

public final class TrickFragment extends BaseFragment {
  private static final String KEY_TRICK = "KEY_TRICK";

  @BindView(R.id.trick_preview) ScalableVideoView videoView;

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

    startVideo();
  }

  @Override protected void unwrapArguments(@NonNull Bundle args) {
    super.unwrapArguments(args);
    trick = args.getParcelable(KEY_TRICK);
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
