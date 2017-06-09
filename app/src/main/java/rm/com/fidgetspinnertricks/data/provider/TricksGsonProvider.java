package rm.com.fidgetspinnertricks.data.provider;

import android.content.res.AssetManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.concurrent.ExecutorService;
import rm.com.fidgetspinnertricks.data.entity.Trick;

/**
 * Created by alex
 */

public final class TricksGsonProvider extends GsonProvider<List<Trick>> {

  public TricksGsonProvider(@NonNull ExecutorService executor, @NonNull Handler mainThreadHandler,
      @NonNull Gson gson, @NonNull AssetManager assets, @NonNull String path) {
    super(executor, mainThreadHandler, gson, assets, path);
  }

  @NonNull @Override protected List<Trick> parse(@NonNull String src) {
    return gson.fromJson(src, new TypeToken<List<Trick>>() {}.getType());
  }
}
