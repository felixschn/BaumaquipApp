package com.example.rentalApplication.ui.Baumaschine;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ModifyBaumaschineViewModelFactory implements ViewModelProvider.Factory{
    private Application mApplication;
    private int mParam;

    public ModifyBaumaschineViewModelFactory(Application mApplication, int mParam) {
        this.mApplication = mApplication;
        this.mParam = mParam;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ModifyBaumaschineViewModel(mApplication,mParam);
    }
}
