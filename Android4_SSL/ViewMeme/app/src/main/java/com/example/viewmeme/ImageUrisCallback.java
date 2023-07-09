package com.example.viewmeme;

import android.net.Uri;

import java.util.List;

public interface ImageUrisCallback {
    void onSuccess(List<Uri> data);
    void onFailure(String errorMessage);
}
