package rm.com.fidgetspinnertricks.data;

import com.raizlabs.android.dbflow.annotation.Database;

import static rm.com.fidgetspinnertricks.data.FidgetDatabase.NAME;
import static rm.com.fidgetspinnertricks.data.FidgetDatabase.SEPARATOR;
import static rm.com.fidgetspinnertricks.data.FidgetDatabase.VERSION;

/**
 * Created by alex
 */

@Database(name = NAME, version = VERSION, generatedClassSeparator = SEPARATOR)
public final class FidgetDatabase {
  static final String NAME = "main_db";
  static final int VERSION = 1;
  static final String SEPARATOR = "_";
}
