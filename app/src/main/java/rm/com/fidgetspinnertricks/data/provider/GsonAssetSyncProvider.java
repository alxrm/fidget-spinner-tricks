package rm.com.fidgetspinnertricks.data.provider;

import android.content.res.AssetManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.Gson;
import java.util.concurrent.ExecutorService;
import rm.com.fidgetspinnertricks.util.Files;

/**
 * Created by alex
 */

@SuppressWarnings("WeakerAccess")
abstract public class GsonAssetSyncProvider<T> implements SyncProvider<T> {

  protected final ExecutorService executor;
  protected final Handler mainThreadHandler;
  protected final Gson gson;

  private final AssetManager assets;
  private final String path;

  public GsonAssetSyncProvider(@NonNull ExecutorService executor, @NonNull Handler mainThreadHandler,
      @NonNull Gson gson, @NonNull AssetManager assets, @NonNull String path) {
    this.executor = executor;
    this.mainThreadHandler = mainThreadHandler;
    this.gson = gson;
    this.assets = assets;
    this.path = path;
  }

  @NonNull @Override public T provide(@NonNull T defaultValue) {
    final String raw = Files.readText(assets, path);
    final T result = parse(raw != null ? raw : "");

    return result != null ? result : defaultValue;
  }

  @Nullable protected abstract T parse(@NonNull String src);
}