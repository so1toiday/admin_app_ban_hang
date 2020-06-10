package com.quyet.banhang.admin_app_ban_hang.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.quyet.banhang.admin_app_ban_hang.MainActivity;
import com.quyet.banhang.admin_app_ban_hang.R;
import com.quyet.banhang.admin_app_ban_hang.function.Validate;
import com.quyet.banhang.admin_app_ban_hang.ui.activity.ForgetPasswrodActivity;

public class LoginFragment extends Fragment {
    private EditText mEmail;
    private EditText mPassword;
    FirebaseAuth auth;
    Button mLogin;
    ProgressDialog dialog;
    TextView mForgetPassword;
    DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        if(user!=null){
            startActivity(new Intent(getContext(),MainActivity.class));
            getActivity().finish();
        }
        findView(view);
        dialog.setTitle("Loading...");
        mForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ForgetPasswrodActivity.class));
            }
        });
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                Validate validate = new Validate();
                boolean check = validate.checkNotEmpty(mEmail, mPassword);
                if (!check) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }
                final String Email = mEmail.getText().toString().trim();
                String Password = mPassword.getText().toString().trim();
                boolean validatePassword=validate.checkLengthPassword(Password);
                boolean validateEmail=validate.checkEmail(Email);
                if(!validateEmail){
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Địa chỉ Email không đúng định dạng", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!validatePassword){
                    dialog.dismiss();
                    Toast.makeText(getContext()   , "Độ dài mật khẩu phải lớn hơn 6 kí tự", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getActivity(),MainActivity.class));
                            getActivity().finish();
                        } else {
                            Toast.makeText(getContext(), "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }


    private void findView(View view) {
        mLogin = view.findViewById(R.id.btnLogin);
        auth = FirebaseAuth.getInstance();
        mEmail = view.findViewById(R.id.edEmail);
        mPassword = view.findViewById(R.id.edPassword);
        dialog=new ProgressDialog(getContext());
        mForgetPassword=view.findViewById(R.id.tvForgetPassword);
        reference= FirebaseDatabase.getInstance().getReference("shop");
    }
}
