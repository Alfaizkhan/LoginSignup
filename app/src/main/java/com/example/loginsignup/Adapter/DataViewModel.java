package com.example.loginsignup.Adapter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.loginsignup.API.APIService;
import com.example.loginsignup.API.ApiClient;
import com.example.loginsignup.Model.Data;
import com.example.loginsignup.Model.UserList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataViewModel extends AndroidViewModel {

    private MutableLiveData<List<Data>> dataList;

    public DataViewModel(@NonNull Application application) {
        super(application);
        dataList = new MutableLiveData<>();
    }

    public LiveData<List<Data>> getData() {
        return dataList;
    }

    public void loadData() {


        APIService service = ApiClient.getClient().create(APIService.class);

        Call<UserList> call = service.getData(1);


        call.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {

                dataList.setValue(response.body().data);
            }


            @Override
            public void onFailure(Call<UserList> call, Throwable t) {

            }
        });
    }
}
