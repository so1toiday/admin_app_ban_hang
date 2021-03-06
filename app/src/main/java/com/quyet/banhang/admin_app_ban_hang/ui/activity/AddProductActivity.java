package com.quyet.banhang.admin_app_ban_hang.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.quyet.banhang.admin_app_ban_hang.R;
import com.quyet.banhang.admin_app_ban_hang.adapter.AdapterCategoryProduct;
import com.quyet.banhang.admin_app_ban_hang.adapter.AddImageAdapter;
import com.quyet.banhang.admin_app_ban_hang.model.DetailsSanPham;
import com.quyet.banhang.admin_app_ban_hang.model.SanPham;
import com.quyet.banhang.admin_app_ban_hang.model.TheLoai;


import java.util.ArrayList;
import java.util.List;

public class AddProductActivity extends AppCompatActivity {
    EditText mName, mDescretion;
    RecyclerView mImage, mProduct;
    StorageReference storage;
    List<String> dataImage;
    MaterialSpinner mCategory;
    List<DetailsSanPham> dataProduct;
    AddImageAdapter addImageAdapter;
    List<String> datacategory;
    DatabaseReference reference;
    AdapterCategoryProduct adapterCategoryProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        setToolbar();
        findView();
        getDataCategory();
        setImageAdapter();
        setAdapterDetailsp();
    }

    private void setAdapterDetailsp() {
        mProduct.setNestedScrollingEnabled(false);
        adapterCategoryProduct = new AdapterCategoryProduct(this, dataProduct);
        mProduct.setAdapter(adapterCategoryProduct);
        mProduct.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        setTitle("Thêm sản phẩm");
    }

    private void setAdapterSpiner() {
        mCategory.setItems(datacategory);
        mCategory.setSelectedIndex(0);
    }

    private void getDataCategory() {
        DatabaseReference categoryReference = reference.child("category");
        categoryReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                datacategory.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TheLoai tl = snapshot.getValue(TheLoai.class);
                    datacategory.add(tl.getTitle());
                }
                setAdapterSpiner();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setImageAdapter() {
        mImage.setNestedScrollingEnabled(false);
        addImageAdapter = new AddImageAdapter(AddProductActivity.this, dataImage);
        mImage.setAdapter(addImageAdapter);
        mImage.setLayoutManager(new GridLayoutManager(this, 3));
    }

    private void findView() {
        mName = findViewById(R.id.edName);
        mDescretion = findViewById(R.id.edDescreption);
        mImage = findViewById(R.id.reImage);
        mProduct = findViewById(R.id.reProduct);
        mCategory = findViewById(R.id.spCategory);
        dataImage = new ArrayList<>();
        dataProduct = new ArrayList<>();
        datacategory = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference();
    }

    public void clickAddImage(View view) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "SELECT IMAGE"), 999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK && data != null) {
            String key = reference.push().toString();
            Uri uri = data.getData();
            storage = FirebaseStorage.getInstance().getReference("productimage").child(key);
            storage.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storage.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            addImageAdapter.addImage(task.getResult().toString());
                        }
                    });

                }
            });
        }
    }

    public void clickAddProduct(View view) {
        String Name = mName.getText().toString().trim();
        String Descripbe = mDescretion.getText().toString().trim();
        String Category = datacategory.get(mCategory.getSelectedIndex());
        if (Name.isEmpty() || Descripbe.isEmpty()) {
            Toast.makeText(this, "Vui lòng không để trống thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dataImage.size() < 1) {
            Toast.makeText(this, "Vui lòng chọn ảnh", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dataProduct.size() < 1) {
            Toast.makeText(this, "Vui lòng thêm loại sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }
        SanPham sp = new SanPham();
        sp.setCategory(Category);
        sp.setDescreption(Descripbe);
        sp.setName(Name);
        sp.setImage(dataImage);
        sp.setSanPhams(dataProduct);
        sp.setDaban(0);
        reference.child("products").push().setValue(sp).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AddProductActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    mName.setText("");
                    mDescretion.setText("");
                    mCategory.setSelectedIndex(0);
                    adapterCategoryProduct.clearData();
                    addImageAdapter.clearData();
                } else {
                    Toast.makeText(AddProductActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void clickAddCategoryProduct(View view) {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        View view1 = LayoutInflater.from(this).inflate(R.layout.item_dialog_details, null);
        final EditText mPrice = view1.findViewById(R.id.edPrice);
        final EditText mColor = view1.findViewById(R.id.edColor);
        final EditText mSize = view1.findViewById(R.id.edSize);
        final EditText mCount = view1.findViewById(R.id.edCount);
        Button mXacNhan = view1.findViewById(R.id.btnXacNhan);
        mXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Price = Integer.parseInt(mPrice.getText().toString().trim());
                String Color = mColor.getText().toString().trim();
                String Size = mSize.getText().toString().trim();
                int Count = Integer.parseInt(mCount.getText().toString().trim());
                DetailsSanPham d = new DetailsSanPham(Color, Size, Price, Count);
                adapterCategoryProduct.addData(d);
                dialog.dismiss();
            }
        });
        dialog.setContentView(view1);


    }
}
