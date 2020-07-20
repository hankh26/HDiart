package com.hh1995.life;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Multipart;

public class DiaryActivity extends AppCompatActivity {

    EditText etdate;
    EditText ettitle;
    EditText etimpressive;
    EditText etmsg;
    ImageView iv;

    String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        etdate=findViewById(R.id.et_date);
        ettitle=findViewById(R.id.et_title);
        etimpressive=findViewById(R.id.et_impressive);
        etmsg=findViewById(R.id.et_msg);
        iv=findViewById(R.id.iv);

        String[] permissions=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(permissions[0])== PackageManager.PERMISSION_DENIED) {
                requestPermissions(permissions,100);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "사진파일 전송 불가능", Toast.LENGTH_SHORT).show();
        }
    }

    public void clickImg(View view) {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 10);
    }

    public void clickComplete(View view) {
        String date=etdate.getText().toString();
        String title=ettitle.getText().toString();
        String impressive=etimpressive.getText().toString();
        String msg=etmsg.getText().toString();

        Retrofit retrofit=RetrofitHelper.getInstance2();
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);

        Map<String,String> dataPart=new HashMap<>();
        dataPart.put("date",date);
        dataPart.put("impressive",impressive);
        dataPart.put("title",title);
        dataPart.put("msg",msg);

        MultipartBody.Part filePart=null;
        if (imgPath != null) {
            File file=new File(imgPath);
            RequestBody requestBody=RequestBody.create(MediaType.parse("image/*"),file);
            filePart=MultipartBody.Part.createFormData("img",file.getName(),requestBody);
        }
        Call<String> call= retrofitService.postDataToDiary(dataPart,filePart);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(DiaryActivity.this, t+"", Toast.LENGTH_SHORT).show();

            }
        });


    }
    //Uri -- > 절대경로로 바꿔서 리턴시켜주는 메소드
    String getRealPathFromUri(Uri uri){
        String[] proj= {MediaStore.Images.Media.DATA};
        CursorLoader loader= new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor= loader.loadInBackground();
        int column_index= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result= cursor.getString(column_index);
        cursor.close();
        return  result;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==10&&resultCode==RESULT_OK){
            Uri uri=data.getData();
            if (uri != null) {
                Glide.with(this).load(uri).into(iv);
                imgPath=getRealPathFromUri(uri);
            }
        }
    }
}
