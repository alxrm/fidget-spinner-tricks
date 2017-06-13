package rm.com.fidgetspinnertricks.inject;

import dagger.Component;
import javax.inject.Singleton;
import rm.com.fidgetspinnertricks.ui.fragment.TrickFragment;
import rm.com.fidgetspinnertricks.ui.fragment.TricksListFragment;
import rm.com.fidgetspinnertricks.ui.fragment.TricksPageFragment;

/**
 * Created by alex
 */

@Singleton @Component(modules = FidgetModule.class)
public interface FidgetComponent {
  void inject(TricksListFragment fragment);
  void inject(TrickFragment fragment);
  void inject(TricksPageFragment fragment);
}
