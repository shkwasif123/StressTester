package com.hazyaz.stresstester;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.hazyaz.stresstester.databinding.ActivityEditProfileBinding;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EditProfile extends AppCompatActivity {
    private ActivityEditProfileBinding binding;
    private boolean gamingClicked = true;
    private boolean movieClicked = true;
    private boolean sportClicked = true;
    private boolean partyClicked = true;
    private boolean drawingClicked = true;
    private boolean readClicked = true;
    private boolean travelClicked = true;
    private String interest = "";
    final DBHelper dbHelper=new DBHelper(this,null);
    final DataClass dataClass=new DataClass();

    final SignedInUser signedInUser=new SignedInUser(this,null);
    private final ArrayList<String> interestList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.saveChanges.setOnClickListener(view -> {
            String email = binding.email.getText().toString().trim();
            String age = binding.age.getText().toString().trim();
            for (String item : interestList) {
                interest += item + ",";
            }
            Toast.makeText(EditProfile.this,email,Toast.LENGTH_SHORT).show();

            if (!email.isEmpty() && !age.isEmpty() && !interest.isEmpty()) {
                if (isValidEmail(email)) {
                  if(isNewEmail(email)) {
                      dbHelper.update(dataClass.getPersonEmail(), email, age, interest);
                      signedInUser.update(dataClass.getPersonEmail(), email);
                      startActivity(new Intent(EditProfile.this, SignUp.class));
                      interestList.clear();
                  }
                  else{
                      Toast.makeText(EditProfile.this,"This Email is already taken, Try different email",Toast.LENGTH_SHORT).show();

                  }
                }
                else{
                    Toast.makeText(EditProfile.this,"Please enter a valid Email",Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(EditProfile.this,"Empty fields are not allowed!!",Toast.LENGTH_SHORT).show();
            }
        });

        DataClass dataClass= new DataClass();
        binding.email.setText(dataClass.getPersonEmail());
        binding.name.setText("Name : " + dataClass.getPersonName());
        binding.gender.setText("Gender : " + dataClass.getPersonGender());
        binding.age.setText(dataClass.getPersonAge());
        buildList(dataClass.getPersonInterest());


        binding.gaming.setOnClickListener(v -> {
            if (gamingClicked) {
                binding.gaming.setBackgroundColor(ContextCompat.getColor(EditProfile.this, R.color.yellow));
                interestList.add("Gaming");
            } else {
                binding.gaming.setBackgroundColor(ContextCompat.getColor(EditProfile.this, R.color.white));
                interestList.remove("Gaming");
            }
            gamingClicked = !gamingClicked;
        });

        binding.movies.setOnClickListener(v -> {
            if (movieClicked) {
                binding.movies.setBackgroundColor(ContextCompat.getColor(EditProfile.this, R.color.yellow));
                interestList.add("Movies");
            } else {
                binding.movies.setBackgroundColor(ContextCompat.getColor(EditProfile.this, R.color.white));
                interestList.remove("Movies");
            }
            movieClicked = !movieClicked;
        });

        binding.sport.setOnClickListener(v -> {
            if (sportClicked) {
                binding.sport.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));
                interestList.add("Sports");
            } else {
                binding.sport.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                interestList.remove("Sports");
            }
            sportClicked = !sportClicked;
        });
        binding.partying.setOnClickListener(v -> {
            if (partyClicked) {
                binding.partying.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));
                interestList.add("Partying");
            } else {
                binding.partying.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                interestList.remove("Partying");
            }
            partyClicked = !partyClicked;
        });
        binding.drawing.setOnClickListener(v -> {
            if (drawingClicked) {
                binding.drawing.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));
                interestList.add("Drawing");
            } else {
                binding.drawing.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                interestList.remove("Drawing");
            }
            drawingClicked = !drawingClicked;
        });
        binding.reading.setOnClickListener(v -> {
            if (readClicked) {
                binding.reading.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));
                interestList.add("Reading");
            } else {
                binding.reading.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                interestList.remove("Reading");
            }
            readClicked = !readClicked;
        });
        binding.travelling.setOnClickListener(v -> {
            if (travelClicked) {
                binding.travelling.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));
                interestList.add("Travelling");
            } else {
                binding.travelling.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                interestList.remove("Travelling");
            }
            travelClicked = !travelClicked;
        });
    }

    private void buildList(String personInterest) {
        String[] parts=personInterest.split(",");
        for (String part : parts) {

            interestList.add(part);
            if (part.equals("Gaming")) {
                binding.gaming.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));
                gamingClicked = false;
            }
            if (part.equals("Movies")) {
                movieClicked = false;
                binding.movies.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));

            }
            if (part.equals("Sports")) {
                sportClicked = false;
                binding.sport.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));

            }

            if (part.equals("Partying")) {
                partyClicked = false;
                binding.partying.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));

            }
            if (part.equals("Drawing")) {
                drawingClicked = false;
                binding.drawing.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));

            }
            if (part.equals("Reading")) {
                readClicked = false;
                binding.reading.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));

            }
            if (part.equals("Travelling")) {
                travelClicked = false;
                binding.travelling.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));

            }
        }


    }
    private Boolean isValidEmail(String email){
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


}