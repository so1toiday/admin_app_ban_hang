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
import com.quyet.banhang.admin_app_ban_hang.model.ThongBao;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddNotification extends AppCompatActivity {
    CircleImageView mImage;
    EditText mTitle,mContent;
    DatabaseReference reference;
    StorageReference storageReference;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);
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
        setTitle("Thêm Thông Báo");
    }

    private void findView() {
        mImage = findViewById(R.id.circleImageView);
        mTitle = findViewById(R.id.edTitleCategory);
        reference = FirebaseDatabase.getInstance().getReference("notifications");
        storageReference = FirebaseStorage.getInstance().getReference("imnotification");
        mContent =findViewById(R.id.edContent);
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

    public void clickAddNotification(View view) {
        if (mTitle.getText().toString().isEmpty() || mContent.getText().toString().isEmpty()){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        if(uri==null){
            Toast.makeText(this, "Bạn chưa chọn ảnh", Toast.LENGTH_SHORT).show();
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
                                String Content = mContent.getText().toString().trim();
                                ThongBao t=new ThongBao();
                                t.setImage(URL);
                                t.setTitle(Title);
                                t.setContent(Content);
                                SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
                                t.setDate(dateFormat.format(new Date()));
                                reference.push().setValue(t).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            mTitle.setText("");
                                            uri=null;
                                            mContent.setText("");
                                            mImage.setImageResource(0);
                                            Toast.makeText(AddNotification.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(AddNotification.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                                        }
                                        dialog.dismiss();
                                    }
                                });
                            } else {
                                Toast.makeText(AddNotification.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                        }
                    });
                } else {
                    Toast.makeText(AddNotification.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

    }
}
