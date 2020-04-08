package com.example.menu.ui.language;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LanguageViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LanguageViewModel(){
        mText =new MutableLiveData<>();
        mText.setValue("This is home language");
    }
    public LiveData<String> getText() {
        return mText;
    }
}