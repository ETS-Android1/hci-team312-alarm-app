package at.ac.univie.hci.u_alarm.ui.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapViewModel extends ViewModel {

    private MutableLiveData<String> mText;


    public MapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Google Map");
    }

    public LiveData<String> getText() {
        return mText;
    }
}