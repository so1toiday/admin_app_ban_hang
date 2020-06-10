package com.quyet.banhang.admin_app_ban_hang.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quyet.banhang.admin_app_ban_hang.R;
import com.quyet.banhang.admin_app_ban_hang.function.FormatMany;
import com.quyet.banhang.admin_app_ban_hang.model.Cart;
import com.quyet.banhang.admin_app_ban_hang.model.User;

public class DetailsBillActivity extends AppCompatActivity {
    TextView mGia,mName,mDescription,mCode,mCount,mColor,mSize,mAddress,mPhone,mEmail;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_bill);
        setToolbar();
        findView();
        reference= FirebaseDatabase.getInstance().getReference();
        Intent i=getIntent();
        if(i.hasExtra("bill")){
            Cart c= (Cart) i.getSerializableExtra("bill");
            mGia.setText(FormatMany.getMany(c.getSanPham().getPrice()*c.getCount()));
            mName.setText(c.getName());
            mDescription.setText(c.getDescreption());
            mCode.setText(c.getMadonhang());
            mCount.setText(String.valueOf(c.getCount()));
            mColor.setText(c.getSanPham().getColor());
            mSize.setText(c.getSanPham().getSize());
            reference.child("user").child(c.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User u=dataSnapshot.getValue(User.class);
                    mPhone.setText(u.getPhone());
                    mEmail.setText(u.getEmail());
                    mAddress.setText(u.getAddress());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void findView() {
        mGia=findViewById(R.id.tvPrice);
        mName=findViewById(R.id.tvName);
        mDescription=findViewById(R.id.tvDescrition);
        mCode=findViewById(R.id.tvCode);
        mCount=findViewById(R.id.tvCount);
        mColor=findViewById(R.id.tvColor);
        mSize=findViewById(R.id.tvSize);
        mAddress=findViewById(R.id.tvAddress);
        mPhone=findViewById(R.id.tvPhone);
        mEmail=findViewById(R.id.tvEmail);
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
        setTitle("Chi Tiết Đơn");
    }

}
