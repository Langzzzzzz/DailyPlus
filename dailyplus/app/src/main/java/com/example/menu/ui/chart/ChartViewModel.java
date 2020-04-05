package com.example.menu.ui.chart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChartViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ChartViewModel(){
        mText =new MutableLiveData<>();
        mText.setValue("This is generate chart page");
    }
    public LiveData<String> getText() {
        return mText;
    }
}