package rm.com.fidgetspinnertricks.inject;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Singleton;
import rm.com.fidgetspinnertricks.AdCoordinator;
import rm.com.fidgetspinnertricks.BuildConfig;
import rm.com.fidgetspinnertricks.FidgetApplication;
import rm.com.fidgetspinnertricks.data.PreferencesAdCoordinator;
import rm.com.fidgetspinnertricks.data.provider.TricksGsonProvider;
import rm.com.fidgetspinnertricks.data.provider.TricksProvider;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by alex
 */

@Module public final class FidgetModule {
  private static final String PREFERENCES_NAME = "fidgets";

  private final FidgetApplication app;

  public FidgetModule(FidgetApplication app) {
    this.app = app;
  }

  @Provides @Singleton Handler provideMainThreadHandler() {
    return new Handler(Looper.getMainLooper());
  }

  @Provides @Singleton SharedPreferences provideSharedPreferences() {
    return app.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
  }

  @Provides @Singleton AdCoordinator provideAdCoordinator(SharedPreferences preferences) {
    return new PreferencesAdCoordinator(preferences);
  }

  @Provides @Singleton AssetManager provideAssetManager() {
    return app.getAssets();
  }

  @Provides @Singleton Gson provideGson() {
    return new Gson();
  }

  @Provides @Singleton ExecutorService provideExecutor() {
    return Executors.newSingleThreadExecutor();
  }

  @Provides @Singleton TricksGsonProvider provideTricksJsonParser(ExecutorService executorService,
      Handler mainThread, Gson gson, AssetManager assets, @TricksJsonPath String tricksJsonPath) {
    return new TricksGsonProvider(executorService, mainThread, gson, assets, tricksJsonPath);
  }

  @Provides @Singleton TricksProvider provideTricks(ExecutorService executorService,
      Handler mainThread, TricksGsonProvider gsonProvider) {
    return new TricksProvider(executorService, mainThread, gsonProvider);
  }

  @Provides @Singleton InterstitialAd provideInterstitialAd() {
    final InterstitialAd interstitialAd = new InterstitialAd(app);
    interstitialAd.setAdUnitId(BuildConfig.INTERSTITIAL_UNIT_ID);
    interstitialAd.setAdListener(new AdListener() {
      @Override public void onAdClosed() {
        interstitialAd.loadAd(new AdRequest.Builder().build());
      }
    });

    return interstitialAd;
  }

  @Provides @Singleton @TricksJsonPath String provideTricksJsonPath() {
    return "tricks.json";
  }
}