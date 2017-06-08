package rm.com.fidgetspinnertricks.inject;

import dagger.Component;
import javax.inject.Singleton;
import rm.com.fidgetspinnertricks.ui.fragment.TricksListFragment;

/**
 * Created by alex
 */

@Singleton @Component(modules = FidgetModule.class)
public interface FidgetComponent {
  void inject(TricksListFragment fragment);
}
