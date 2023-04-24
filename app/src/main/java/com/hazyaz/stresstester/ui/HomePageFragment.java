package com.hazyaz.stresstester.ui;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.hazyaz.stresstester.EditProfile;
import com.hazyaz.stresstester.R;
import com.hazyaz.stresstester.SignedInUser;
import com.hazyaz.stresstester.StoreRecord;
import com.hazyaz.stresstester.Util;
import com.hazyaz.stresstester.DataClass;
import com.hazyaz.stresstester.databinding.FragmentHomePageBinding;
import com.hazyaz.stresstester.questions;
import java.time.LocalDate;
import java.util.ArrayList;

public class HomePageFragment extends Fragment {
    // assign the _binding variable initially to null and
    // also when the view is destroyed again it has to be set to null
    private FragmentHomePageBinding binding = null;
    private float maxscore = 136.8f; // hardcoded 9.12*15
    private float score = 0f;
    private int Qn = 0;

    public HomePageFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("Range")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // inflate the layout and bind to the _binding
        binding = FragmentHomePageBinding.inflate(inflater, container, false);

        binding.test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.initial.setVisibility(View.GONE);
                binding.result.setVisibility(View.GONE);
                binding.main.setVisibility(View.VISIBLE);
            }
        });
        binding.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharing(v);
            }
        });
        binding.CallNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call(v);
            }
        });

        updateQuestion();

        binding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score += 9.12f;
                updateQuestion();
            }
        });
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score += 7.62f;
                updateQuestion();
            }
        });

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score += 5.87f;
                updateQuestion();
            }
        });
        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score += 3.87f;
                updateQuestion();
            }
        });
        binding.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score += 1.62f;
                updateQuestion();
            }
        });

        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateQuestion() {
        if (Qn <= 14) {
            questions q = new questions();
            ArrayList<Util> y = q.allData();
            binding.question.setText(y.get(Qn).getmQuestion());
            binding.button1.setText(y.get(Qn).getmChoice1());
            binding.button2.setText(y.get(Qn).getmChoice2());
            binding.button3.setText(y.get(Qn).getmChoice3());
            binding.button4.setText(y.get(Qn).getmChoice4());
            binding.button5.setText(y.get(Qn).getmChoice5());
            binding.questionCount.setText((Qn + 1) + "");
            Qn++;
        }
        else{
            DataClass dataClass = new DataClass();
            SignedInUser userDb = new SignedInUser(requireContext(), null);
            Cursor cursor = userDb.getName();
            cursor.moveToLast();
            @SuppressLint("Range")
            String email=cursor.getString(cursor.getColumnIndex(SignedInUser.EMAIL_COL));
            cursor.close();
            //extracting the date information from calender
            LocalDate date=LocalDate.now();
            int percentageStress = Math.round(100f - (score / maxscore) * 100f);
            percentageStress = Math.max(percentageStress, 0);
            int percentage=percentageStress;
            StoreRecord db = new StoreRecord(requireContext(), null);
            db.addName(email,Integer.toString(percentage),date.toString());

            if (percentageStress >= 71 && percentageStress <= 100) {
                binding.remedy.setText(dataClass.getRemedies(6));
                binding.CallNow.setVisibility(View.VISIBLE);
            } else if (percentageStress >= 51 && percentageStress <= 70) {
                binding.remedy.setText(dataClass.getRemedies(5));
            } else if (percentageStress >= 41 && percentageStress <= 50) {
                binding.remedy.setText(dataClass.getRemedies(4));
            } else if (percentageStress >= 31 && percentageStress <= 40) {
                binding.remedy.setText(dataClass.getRemedies(3));
            } else if (percentageStress >= 21 && percentageStress <= 30) {
                binding.remedy.setText(dataClass.getRemedies(2));
            } else if (percentageStress >= 11 && percentageStress <= 20) {
                binding.remedy.setText(dataClass.getRemedies(1));
            } else if (percentageStress >= 0 && percentageStress <= 10) {
                binding.remedy.setText(dataClass.getRemedies(0));
            }
            if(percentageStress >= 20 && percentageStress <= 50){
                binding.watchVideo.setVisibility(View.VISIBLE);
                binding.watchVideo.setOnClickListener(v -> {
                    redirectToVideo(dataClass.getVideoUrl(0));
                });
            } else if (percentageStress > 50 && percentageStress <= 70) {
                binding.watchVideo.setVisibility(View.VISIBLE);
                binding.watchVideo.setOnClickListener(v -> {
                    redirectToVideo(dataClass.getVideoUrl(1));
                });


            }

            binding.initial.setVisibility(View.GONE);
            binding.result.setVisibility(View.VISIBLE);
            binding.main.setVisibility(View.GONE);

            Animation(percentageStress);
        }
    }

    private void redirectToVideo(String videoUrl) {
        Intent openURL = new Intent(Intent.ACTION_VIEW);
        openURL.setData(Uri.parse(videoUrl));
        startActivity(openURL);

    }

    public void sharing(View view) {
        DataClass dataClass = new DataClass();
        Intent sharingIntent = new Intent("android.intent.action.SEND");
        sharingIntent.setType("text/plain");
        String shareBody = "The Stress Level of " + dataClass.getPersonName() + " is " + binding.percent.getText() + ".\n" +
                "Remedy to reduce stress is :" + binding.remedy.getText() + ".\n\n";
        sharingIntent.putExtra("android.intent.extra.TEXT", shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    private void Animation(int number) {
        ValueAnimator animator = ValueAnimator.ofInt(0, number);
        animator.setDuration(6000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                binding.percent.setText(animation.getAnimatedValue().toString() + " %");
                binding.progressBar.setProgress((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    public void Call(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:7738858013"));
        startActivity(intent);
    }

}
