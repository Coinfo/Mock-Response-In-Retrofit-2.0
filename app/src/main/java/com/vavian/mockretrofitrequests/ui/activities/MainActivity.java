package com.vavian.mockretrofitrequests.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.vavian.mockretrofitrequests.R;
import com.vavian.mockretrofitrequests.rest.model.Teacher;
import com.vavian.mockretrofitrequests.rest.service.RestClient;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private final static String TAG  = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonGetTeacher)
    public void getTeacher(View view) {

        Call<Teacher> teacherCall = RestClient.getClient().getTeacherById("1");
        teacherCall.enqueue(new Callback<Teacher>() {
            @Override
            public void onResponse(Response<Teacher> response, Retrofit retrofit) {
                Teacher teacher = response.body();
                Log.d(TAG, teacher.toString());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "Failure " + t.getMessage());
            }
        });


    }
}
