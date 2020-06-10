package com.quyet.banhang.admin_app_ban_hang.ui.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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
import com.quyet.banhang.admin_app_ban_hang.R;
import com.quyet.banhang.admin_app_ban_hang.function.Validate;
import com.quyet.banhang.admin_app_ban_hang.model.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class RegisterFragment extends Fragment {
    ImageView btnDate;
    FirebaseAuth auth;
    DatabaseReference UserReference;
    EditText mEmail, mName, mPhone, mPassword, mBirthday;
    RadioButton mNam, mNu;
    Button mRegister;
    ProgressDialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        UserReference = FirebaseDatabase.getInstance().getReference("shop");
        findview(view);
        dialog.setTitle("Loading...");
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                Validate validate = new Validate();
                boolean validateEmpty = validate.checkNotEmpty(mEmail, mName, mPhone, mPassword, mBirthday);
                if (!validateEmpty) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String Email = mEmail.getText().toString().trim();
                final String Name = mName.getText().toString().trim();
                final String Phone = mPhone.getText().toString().trim();
                String Password = mPassword.getText().toString().trim();
                final String Birthday = mBirthday.getText().toString().trim();
                final boolean Sex = mNam.isChecked();
                boolean validateEmail = validate.checkEmail(Email);
                boolean validatePassword = validate.checkLengthPassword(Password);
                if (!validateEmail) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Địa chỉ Email không đúng định dạng", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!validatePassword) {
                    Toast.makeText(getContext(), "Độ dài mật khẩu phải trên 6 kí tự", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }

                auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            FirebaseUser CurrentUser = FirebaseAuth.getInstance().getCurrentUser();
                            if (CurrentUser != null) {
                                User model = new User();
                                model.setEmail(Email);
                                model.setName(Name);
                                model.setPhone(Phone);
                                model.setBirthday(Birthday);
                                model.setSex(Sex);
                                UserReference.child(CurrentUser.getUid()).setValue(model);
                            }
                        } else {
                            Toast.makeText(getContext(), "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar=Calendar.getInstance();
                DatePickerDialog dialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
                        calendar.set(year,month,dayOfMonth);
                        mBirthday.setText(dateFormat.format(calendar.getTime()));
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

    }


    private void findview(View view) {
        mEmail = view.findViewById(R.id.edEmail);
        mName = view.findViewById(R.id.edName);
        mPhone = view.findViewById(R.id.edPhone);
        mPassword = view.findViewById(R.id.edPassword);
        mBirthday = view.findViewById(R.id.edBirthday);
        mNam = view.findViewById(R.id.rdoNam);
        mNu = view.findViewById(R.id.rdoNu);
        mRegister = view.findViewById(R.id.btnRegister);
        dialog=new ProgressDialog(getContext());
        btnDate=view.findViewById(R.id.btnDate);
    }
}
