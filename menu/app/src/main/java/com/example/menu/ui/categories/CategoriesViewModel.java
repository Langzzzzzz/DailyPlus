package com.example.menu.ui.categories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CategoriesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CategoriesViewModel(){
        mText =new MutableLiveData<>();
        mText.setValue("This is generate categories page");
    }
    public LiveData<String> getText() {
        return mText;
    }
}