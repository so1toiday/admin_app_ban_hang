package com.quyet.banhang.admin_app_ban_hang.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.quyet.banhang.admin_app_ban_hang.R;
import com.quyet.banhang.admin_app_ban_hang.model.TheLoai;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddCategoryActivity extends AppCompatActivity {
    CircleImageView mImage;
    EditText mTitle;
    Uri uri;
    DatabaseReference reference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        setToolbar();
        findView();
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
        setTitle("Thêm Danh Mục");
    }

    private void findView() {
        mImage = findViewById(R.id.circleImageView);
        mTitle = findViewById(R.id.edTitleCategory);
        reference = FirebaseDatabase.getInstance().getReference("category");
        storageReference = FirebaseStorage.getInstance().getReference("imagecategory");
    }

    public void clickAddCategory(View view) {
        if (uri == null) {
            Toast.makeText(this, "Bạn chưa chọn ảnh", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Bạn chưa nhập Title", Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...");
        dialog.setMessage("Vui lòng đợi trong ít phút");
        dialog.show();
        storageReference.child(mTitle.getText().toString()).putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    storageReference.child(mTitle.getText().toString()).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                String URL = task.getResult().toString();
                                String Title = mTitle.getText().toString().trim();
                                TheLoai tl = new TheLoai();
                                tl.setImage(URL);
                                tl.setTitle(Title);
                                reference.push().setValue(tl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            mTitle.setText("");
                                            uri=null;
                                            mImage.setImageResource(0);
                                            Toast.makeText(AddCategoryActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(AddCategoryActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                                        }
                                        dialog.dismiss();
                                    }
                                });
                            } else {
                                Toast.makeText(AddCategoryActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                        }
                    });
                } else {
                    Toast.makeText(AddCategoryActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

    }

    public void choiceFile(View view) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "GET IMAGE"), 999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            mImage.setImageURI(uri);
        }
    }
}
