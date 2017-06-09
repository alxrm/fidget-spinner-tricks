package rm.com.fidgetspinnertricks.data.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import rm.com.fidgetspinnertricks.data.FidgetDatabase;

/**
 * Created by alex
 */

@Table(database = FidgetDatabase.class)
public final class Trick extends BaseModel implements Parcelable {
  @PrimaryKey public String id;
  @Column public String title;
  @Column public String video;
  @Column public String preview;
  @Column public String league;
  @Column public int level;
  @Column public boolean learned = false;

  public Trick() {
  }

  protected Trick(Parcel in) {
    id = in.readString();
    title = in.readString();
    video = in.readString();
    preview = in.readString();
    league = in.readString();
    level = in.readInt();
    learned = in.readByte() != 0;
  }

  public static final Creator<Trick> CREATOR = new Creator<Trick>() {
    @Override public Trick createFromParcel(Parcel in) {
      return new Trick(in);
    }

    @Override public Trick[] newArray(int size) {
      return new Trick[size];
    }
  };

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(id);
    dest.writeString(title);
    dest.writeString(video);
    dest.writeString(preview);
    dest.writeString(league);
    dest.writeInt(level);
    dest.writeByte((byte) (learned ? 1 : 0));
  }
}
