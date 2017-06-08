package rm.com.fidgetspinnertricks;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import rm.com.fidgetspinnertricks.inject.DaggerFidgetComponent;
import rm.com.fidgetspinnertricks.inject.FidgetComponent;
import rm.com.fidgetspinnertricks.inject.FidgetModule;

/**
 * Created by alex
 */

public final class FidgetApplication extends Application {

  private FidgetComponent component;

  @Override public void onCreate() {
    super.onCreate();
    Fabric.with(this, new Crashlytics());

    //final FlowConfig databaseConfig =
    //    new FlowConfig.Builder(this).openDatabasesOnInit(true).build();
    //
    //FlowManager.init(databaseConfig);
    //
    component = DaggerFidgetComponent.builder().fidgetModule(new FidgetModule(this)).build();
  }

  public final FidgetComponent injector() {
    return component;
  }
}
