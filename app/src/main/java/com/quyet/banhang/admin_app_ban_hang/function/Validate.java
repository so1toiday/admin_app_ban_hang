package com.quyet.banhang.admin_app_ban_hang.function;

import android.widget.EditText;

public class Validate {
    public boolean checkEmail(String Email) {
        String s = "\\w+@\\w+\\.\\w+";
        return Email.matches(s);
    }

    public void resetEditText(EditText... editTexts) {
        for (int i = 0; i < editTexts.length; i++) {
            editTexts[i].setText("");
        }
    }

    public boolean checkNotEmpty(EditText... editTexts) {
        for (int i = 0; i < editTexts.length; i++) {
            if (editTexts[i].getText().toString().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean checkLengthPassword(String password) {
        if (password.length() >= 6) {
            return true;
        }
        return false;
    }

}
