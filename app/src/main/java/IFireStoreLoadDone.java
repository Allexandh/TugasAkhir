import java.util.List;

import umn.ac.id.tugasakhir.Destination;

public interface IFireStoreLoadDone {
    void onFireStoreLoadSuccess(List<Destination> destination);
    void onFireStoreLoadFailed(String message);
}
