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
import rm.com.fidgetspinnertricks.data.provider.TricksProvider;
import rm.com.fidgetspinnertricks.ui.adapter.TricksAdapter;

/**
 * Created by alex
 */

@Module public final class FidgetModule {

  private final FidgetApplication app;

  public FidgetModule(FidgetApplication app) {
    this.app = app;
  }

  @Provides @Singleton TricksAdapter provideTricksAdapter() {
    return new TricksAdapter();
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

  @Provides @Singleton TricksProvider provideCatalog(ExecutorService executorService,
      Handler mainThread, Gson gson, AssetManager assets) {
    return new TricksProvider(executorService, mainThread, gson, assets);
  }
}