package rm.com.fidgetspinnertricks;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.google.android.gms.ads.MobileAds;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
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
    MobileAds.initialize(this, BuildConfig.ADMOB_APP_ID);

    initAnalytics();

    final FlowConfig databaseConfig =
        new FlowConfig.Builder(this).openDatabasesOnInit(true).build();

    FlowManager.init(databaseConfig);

    component = DaggerFidgetComponent.builder().fidgetModule(new FidgetModule(this)).build();
  }

  public final FidgetComponent injector() {
    return component;
  }

  private void initAnalytics() {
    final Crashlytics crashlyticsKit = new Crashlytics.Builder().core(
        new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()).build();

    Fabric.with(this, crashlyticsKit);
  }
}
