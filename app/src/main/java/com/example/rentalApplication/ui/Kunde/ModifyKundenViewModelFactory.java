package com.example.rentalApplication.ui.Kunde;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
//creating Factory to handle extra parameter input in ViewModel; in this case for the rowid
public class ModifyKundenViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private int mParam;

    public ModifyKundenViewModelFactory(Application application, int mParam) {
        this.mApplication = application;
        this.mParam = mParam;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ModifyKundenViewModel(mApplication, mParam);
    }
}
