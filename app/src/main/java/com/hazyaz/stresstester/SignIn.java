package com.hazyaz.stresstester;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;
import com.hazyaz.stresstester.databinding.ActivitySignInBinding;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignIn extends AppCompatActivity {
    private ActivitySignInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(SignIn.this, SignUp.class);
            startActivity(intent);
        });

        binding.button.setOnClickListener(v -> {
            String email = Objects.requireNonNull(binding.emailEt.getText()).toString().trim();
            String pass = Objects.requireNonNull(binding.passEt.getText()).toString().trim();

            if (!email.isEmpty() && !pass.isEmpty()) {
                if (isValidEmail(email)) {

                if (authListener(email, pass)) {
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        binding.progress.setVisibility(View.VISIBLE);
                        binding.button.setVisibility(View.GONE);
                        startActivity(new Intent(SignIn.this, MainActivity.class));
                    }, 500);
                } else {
                    Toast.makeText(SignIn.this, "Invalid Credentials !!", Toast.LENGTH_SHORT).show();
                }
                binding.progress.setVisibility(View.GONE);
                binding.button.setVisibility(View.VISIBLE);
            }
                else{
                    Toast.makeText(SignIn.this, "Please Enter a valid Email", Toast.LENGTH_SHORT).show();

                }
        }
            else {
                Toast.makeText(SignIn.this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("Range")
    private boolean authListener(String email, String pass) {
        DBHelper db = new DBHelper(this, null);
        Cursor cursor = db.getName();

        if (cursor.moveToFirst()) {
            String storedEmail = cursor.getString(cursor.getColumnIndex(DBHelper.Email_Col));
            String storedPass = cursor.getString(cursor.getColumnIndex(DBHelper.Pass_Col));

            if (storedEmail.equals(email) && storedPass.equals(pass)) {
                cursor.close();
                retrieveInfoAfterSignedIn(email);
                return true;
            }

            while (cursor.moveToNext()) {
                storedEmail = cursor.getString(cursor.getColumnIndex(DBHelper.Email_Col));
                storedPass = cursor.getString(cursor.getColumnIndex(DBHelper.Pass_Col));

                if (storedEmail.equals(email) && storedPass.equals(pass)) {
                    cursor.close();
                    retrieveInfoAfterSignedIn(email);
                    return true;
                }
            }
        }

        cursor.close();
        return false;
    }

    private void retrieveInfoAfterSignedIn(String email) {
        SignedInUser userDb = new SignedInUser(this, null);
        userDb.addCurrentSignedIn(email);
    }

    @SuppressLint("Range")
    @Override
    protected void onStart() {
        super.onStart();
        SignedInUser userDb = new SignedInUser(this, null);
        Cursor cursor = userDb.getName();

        if (cursor.moveToFirst()) {
            if (!cursor.getString(1).isEmpty()) {
                startActivity(new Intent(this, MainActivity.class));
            }

            while (cursor.moveToNext()) {
                if (!cursor.getString(1).isEmpty()) {
                    startActivity(new Intent(this, MainActivity.class));
                }
            }
        }
    }
    private Boolean isValidEmail(String email){
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
