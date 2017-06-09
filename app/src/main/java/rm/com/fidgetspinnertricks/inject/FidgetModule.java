package rm.com.fidgetspinnertricks.inject;

import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Looper;
import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Singleton;
import rm.com.fidgetspinnertricks.FidgetApplication;
import rm.com.fidgetspinnertricks.data.provider.TricksGsonProvider;
import rm.com.fidgetspinnertricks.data.provider.TricksProvider;

/**
 * Created by alex
 */

@Module public final class FidgetModule {

  private final FidgetApplication app;

  public FidgetModule(FidgetApplication app) {
    this.app = app;
  }

  @Provides @Singleton Handler provideMainThreadHandler() {
    return new Handler(Looper.getMainLooper());
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

  @Provides @Singleton @TricksJsonPath String provideTricksJsonPath() {
    return "tricks.json";
  }
}