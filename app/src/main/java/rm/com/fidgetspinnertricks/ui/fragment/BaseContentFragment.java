package rm.com.fidgetspinnertricks.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import rm.com.fidgetspinnertricks.R;

/**
 * Created by alex
 */

public abstract class BaseContentFragment extends BaseFragment {

  @BindView(R.id.content_loader) ProgressBar loader;
  @BindView(R.id.content_message_empty) TextView empty;
  @BindView(R.id.content) RecyclerView content;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_content, container, false);
  }

  final protected void toggleContent(boolean show, boolean isEmpty) {
    content.setVisibility(show ? View.VISIBLE : View.GONE);
    loader.setVisibility(show ? View.GONE : View.VISIBLE);
    empty.setVisibility(isEmpty && show ? View.VISIBLE : View.GONE);
  }
}
