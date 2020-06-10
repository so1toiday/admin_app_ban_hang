package com.quyet.banhang.admin_app_ban_hang.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.quyet.banhang.admin_app_ban_hang.R;
import com.quyet.banhang.admin_app_ban_hang.function.Validate;

public class ForgetPasswrodActivity extends AppCompatActivity {
    Button mbtnForgetPassword;
    EditText mEmail;
    TextInputLayout tipEmail;
    FirebaseAuth auth;
    ProgressDialog dialog;
    AlertDialog Thongbao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_passwrod);
        findView();
        final Validate validate = new Validate();
        dialog.setTitle("Loading...");
        Thongbao.setTitle("Thông báo");
        Thongbao.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean check = validate.checkEmail(s.toString());

                if (s.toString().isEmpty()) {
                    tipEmail.setError("Email không được để trống");
                    mbtnForgetPassword.setBackgroundColor(Color.parseColor("#D3D0D0"));
                    mbtnForgetPassword.setEnabled(false);
                } else if (!check) {
                    tipEmail.setError("Địa chỉ Email không đúng định dạng");
                    mbtnForgetPassword.setBackgroundColor(Color.parseColor("#D3D0D0"));
                    mbtnForgetPassword.setEnabled(false);
                } else {
                    mbtnForgetPassword.setEnabled(true);
                    mbtnForgetPassword.setBackgroundColor(Color.parseColor("#FF291A"));
                    tipEmail.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mbtnForgetPassword.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {
                                                      dialog.show();
                                                      String Email=mEmail.getText().toString().trim();
                                                      auth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                          @Override
                                                          public void onComplete(@NonNull Task<Void> task) {
                                                              dialog.dismiss();
                                                              if(task.isSuccessful()){
                                                                  Thongbao.setMessage("Vui lòng kiểm tra Email");
                                                                  Thongbao.show();
                                                              }else{
                                                                  Thongbao.setMessage("Xảy ra lỗi");
                                                                  Thongbao.show();
                                                              }
                                                          }
                                                      });
                                                  }
                                              }
        );
    }

    private void findView() {
        auth=FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Quên mật khẩu");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgetPasswrodActivity.this, LoginActivity.class));
                finish();
            }
        });
        mbtnForgetPassword = findViewById(R.id.btnForgetPassword);
        mEmail = findViewById(R.id.edForgetEmail);
        tipEmail = findViewById(R.id.tipForgetEmail);
        dialog=new ProgressDialog(ForgetPasswrodActivity.this);
        Thongbao=new AlertDialog.Builder(ForgetPasswrodActivity.this).create();

    }
}
