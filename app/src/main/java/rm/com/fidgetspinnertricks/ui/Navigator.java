package rm.com.fidgetspinnertricks.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import rm.com.fidgetspinnertricks.ui.fragment.BaseFragment;

/**
 * Created by alex
 */

public interface Navigator {
  void to(@NonNull BaseFragment dest);

  void back();

  void updateAppbar(@NonNull String title, boolean hasBackButton);

  @NonNull TabLayout tabs();
}