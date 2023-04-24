package com.hazyaz.stresstester;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hazyaz.stresstester.databinding.ActivitySignUpBinding;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(v -> {
            String email = Objects.requireNonNull(binding.emailEt.getText()).toString().trim();
            String pass = Objects.requireNonNull(binding.passEt.getText()).toString().trim();
            String confirmPass = Objects.requireNonNull(binding.confirmPassEt.getText()).toString().trim();

            if (!email.isEmpty() && !pass.isEmpty() && !confirmPass.isEmpty()) {
                if (isValidEmail(email)) {
                if (pass.equals(confirmPass)) {
                    if (isNewEmail(email)) {
                        Intent intent = new Intent(SignUp.this, CompleteProfile.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("email", email);
                        bundle.putString("password", pass);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignUp.this, "Email Already Registered !!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUp.this, "Password is not matching", Toast.LENGTH_SHORT).show();
                }
            } else{
                    Toast.makeText(SignUp.this, "Please Enter a valid Email", Toast.LENGTH_SHORT).show();

                }
            }else {
                Toast.makeText(SignUp.this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @SuppressLint("Range")
    public boolean isNewEmail(String Email) {
        DBHelper db = new DBHelper(this, null);

        Cursor cursor = db.getName();
        if (cursor.moveToFirst() && cursor.getString(cursor.getColumnIndex(DBHelper.Email_Col)).equals(Email)) {
            cursor.close();
            return false;
        }
        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex(DBHelper.Email_Col)).equals(Email)) {
                cursor.close();
                return false;
            }
        }
        cursor.close();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        SignedInUser userDb = new SignedInUser(this, null);

        Cursor cursor = userDb.getName();
        if (cursor.moveToFirst()) {
            if (!cursor.getString(1).equals("")) {
                startActivity(new Intent(this, MainActivity.class));
            }
            while (cursor.moveToNext()) {
                if (!cursor.getString(1).equals("")) {
                    startActivity(new Intent(this, MainActivity.class));
                }
            }
        }
    }
}
