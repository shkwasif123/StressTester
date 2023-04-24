package com.hazyaz.stresstester;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.hazyaz.stresstester.databinding.ActivityCompleteProfileBinding;
import java.util.ArrayList;

public class CompleteProfile extends AppCompatActivity {

    private boolean gamingClicked = true;
    private boolean movieClicked = true;
    private boolean sportClicked = true;
    private boolean partyClicked = true;
    private boolean drawingClicked = true;
    private boolean readClicked = true;
    private boolean travelClicked = true;
    private final ArrayList<String> interestList = new ArrayList<>();
    private ActivityCompleteProfileBinding binding;
    private String gender = "";
    private String interest = "";

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
        binding = ActivityCompleteProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.finish.setOnClickListener(v -> {
            for (String item : interestList) {
                interest += item +",";
            }
            DBHelper db = new DBHelper(CompleteProfile.this, null);
            SignedInUser userDb = new SignedInUser(CompleteProfile.this, null);
            String name = binding.name.getText().toString().trim();
            String age = binding.age.getText().toString();
            String email = getIntent().getExtras().getString("email").trim();
            String password = getIntent().getExtras().getString("password").trim();

            if (!name.isEmpty() && !gender.trim().isEmpty() && !age.trim().isEmpty() && !interest.trim()
                    .isEmpty()) {
                db.addName(email, password, name, gender, age, interest);
                userDb.addCurrentSignedIn(email);
                Toast.makeText(CompleteProfile.this, name + " registered successfully", Toast.LENGTH_LONG).show();

                binding.name.setText("");

                Intent intent = new Intent(CompleteProfile.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(CompleteProfile.this, "Please fill all fields !!", Toast.LENGTH_SHORT)
                        .show();
                interestList.clear();
            }
        });

        binding.gaming.setOnClickListener(v -> {
            if (gamingClicked) {
                binding.gaming.setBackgroundColor(ContextCompat.getColor(CompleteProfile.this, R.color.yellow));
                interestList.add("Gaming");
            } else {
                binding.gaming.setBackgroundColor(ContextCompat.getColor(CompleteProfile.this, R.color.white));
                interestList.remove("Gaming");
            }
            gamingClicked = !gamingClicked;
        });

        binding.movies.setOnClickListener(v -> {
            if (movieClicked) {
                binding.movies.setBackgroundColor(ContextCompat.getColor(CompleteProfile.this, R.color.yellow));
                interestList.add("Movies");
            } else {
                binding.movies.setBackgroundColor(ContextCompat.getColor(CompleteProfile.this, R.color.white));
                interestList.remove("Movies");
            }
            movieClicked = !movieClicked;
        });

        binding.sport.setOnClickListener(v -> {
            if (sportClicked) {
                binding.sport.setBackgroundColor(ContextCompat.getColor(CompleteProfile.this, R.color.yellow));
                interestList.add("Sports");
            } else {
                binding.sport.setBackgroundColor(ContextCompat.getColor(CompleteProfile.this, R.color.white));
                interestList.remove("Sports");
            }
            sportClicked = !sportClicked;
        });
        binding.partying.setOnClickListener(v -> {
            if (partyClicked) {
                binding.partying.setBackgroundColor(ContextCompat.getColor(CompleteProfile.this, R.color.yellow));
                interestList.add("Partying");
            } else {
                binding.partying.setBackgroundColor(ContextCompat.getColor(CompleteProfile.this, R.color.white));
                interestList.remove("Partying");
            }
            partyClicked = !partyClicked;
        });
        binding.drawing.setOnClickListener(v -> {
            if (drawingClicked) {
                binding.drawing.setBackgroundColor(ContextCompat.getColor(CompleteProfile.this, R.color.yellow));
                interestList.add("Drawing");
            } else {
                binding.drawing.setBackgroundColor(ContextCompat.getColor(CompleteProfile.this, R.color.white));
                interestList.remove("Drawing");
            }
            drawingClicked = !drawingClicked;
        });
        binding.reading.setOnClickListener(v -> {
            if (readClicked) {
                binding.reading.setBackgroundColor(ContextCompat.getColor(CompleteProfile.this, R.color.yellow));
                interestList.add("Reading");
            } else {
                binding.reading.setBackgroundColor(ContextCompat.getColor(CompleteProfile.this, R.color.white));
                interestList.remove("Reading");
            }
            readClicked = !readClicked;
        });
        binding.travelling.setOnClickListener(v -> {
            if (travelClicked) {
                binding.travelling.setBackgroundColor(ContextCompat.getColor(CompleteProfile.this, R.color.yellow));
                interestList.add("Travelling");
            } else {
                binding.travelling.setBackgroundColor(ContextCompat.getColor(CompleteProfile.this, R.color.white));
                interestList.remove("Travelling");
            }
            travelClicked = !travelClicked;
        });
    }
    public void radio_button_click(View view) {
        if (view instanceof RadioButton) {
            boolean checked = ((RadioButton) view).isChecked();

            if (view.getId() == R.id.m && checked) {
                gender = "Male";
            } else if (view.getId() == R.id.f && checked) {
                gender = "Female";
            }
        }
    }
}