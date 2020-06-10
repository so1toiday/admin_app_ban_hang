package com.quyet.banhang.admin_app_ban_hang.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.quyet.banhang.admin_app_ban_hang.R;
import com.quyet.banhang.admin_app_ban_hang.adapter.AddImageAdapter;

import java.util.ArrayList;
import java.util.List;

public class AddBannerActivity extends AppCompatActivity {

    RecyclerView mListBanner;
    AddImageAdapter adapter;
    List<String> list;
    StorageReference storage;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_banner);
        findView();
        setToolbar();
        setAdapterBanner();
    }

    private void setAdapterBanner() {
        list=new ArrayList<>();
        adapter=new AddImageAdapter(this,list);
        mListBanner.setAdapter(adapter);
        mListBanner.setLayoutManager(new GridLayoutManager(this,3));
    }

    private void findView() {
        mListBanner=findViewById(R.id.reImageBanner);
        storage=FirebaseStorage.getInstance().getReference("banner");
        reference= FirebaseDatabase.getInstance().getReference("banner");
    }

    public void choiceFile(View view) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "GET IMAGE"), 999);
    }

    private void setToolbar() {
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Cập Nhật Banner");
    }
    public void clickAddBanner(View view){
        reference.setValue(list).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
               if(task.isSuccessful()){
                   adapter.clearData();
                   Toast.makeText(AddBannerActivity.this, "Cập nhật banner", Toast.LENGTH_SHORT).show();
               }else {
                   Toast.makeText(AddBannerActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK && data != null) {
            String key = reference.push().toString();
            Uri uri = data.getData();
            storage = FirebaseStorage.getInstance().getReference("banner").child(key);
            storage.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storage.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            adapter.addImage(task.getResult().toString());
                        }
                    });

                }
            });
        }
    }
}
