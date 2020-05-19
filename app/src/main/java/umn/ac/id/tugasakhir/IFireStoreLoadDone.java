package umn.ac.id.tugasakhir;

import java.util.List;

public interface IFireStoreLoadDone {
    void onFireStoreLoadSuccess(List<Destination> destination);
    void onFireStoreLoadFailed(String message);
}
