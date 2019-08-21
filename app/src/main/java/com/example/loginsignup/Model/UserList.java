package com.example.loginsignup.Model;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class UserList {

    @SerializedName("page")
    public Integer page;
    @SerializedName("per_page")
    public Integer perPage;
    @SerializedName("total")
    public Integer total;
    @SerializedName("total_pages")
    public Integer totalPages;

    @SerializedName("data")
    public List<Data> data = new ArrayList<>();
}
