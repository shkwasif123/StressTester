package com.hazyaz.stresstester.ui;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.hazyaz.stresstester.R;
import com.hazyaz.stresstester.SignedInUser;
import com.hazyaz.stresstester.StoreRecord;
import com.hazyaz.stresstester.databinding.FragmentRecordPageBinding;

import java.time.LocalDate;

public class RecordPageFragment extends Fragment {

    private FragmentRecordPageBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecordPageBinding.inflate(inflater, container, false);
        //fetching signed in user

         SignedInUser userDb = new SignedInUser(requireContext(), null);
        Cursor cursor1 = userDb.getName();
        cursor1.moveToLast();
        @SuppressLint("Range") String email=cursor1.getString(cursor1.getColumnIndex(SignedInUser.EMAIL_COL));

        cursor1.close();
        binding.chooseDate.setOnClickListener(v -> binding.calenderHolder.setVisibility(View.VISIBLE));
        binding.SHOWALL.setOnClickListener(v -> {
            binding.container.removeAllViews();
                showAll(email);
            binding.calenderHolder.setVisibility(View.GONE);
        });
        showAll(email);

        //filtering

        binding.calenderView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String date = year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
            binding.container.removeAllViews();
            StoreRecord db = new StoreRecord(requireContext(), null);
            Cursor cursor = db.getName();
            if (cursor.moveToFirst()) {
                cursor.moveToLast();
                @SuppressLint("Range")
                LocalDate dateObj = LocalDate.parse(cursor.getString(cursor.getColumnIndex(StoreRecord.date_Col)));
                String printDate = dateObj.getDayOfMonth() + " " + dateObj.getMonth() + " " + dateObj.getYear();
                @SuppressLint("Range") String level = cursor.getString(cursor.getColumnIndex(StoreRecord.Stress_level));
                @SuppressLint("Range") boolean isPresent = cursor.getString(cursor.getColumnIndex(StoreRecord.date_Col)).equals(date);
                if (isPresent && cursor.getString(cursor.getColumnIndex(StoreRecord.Email_COL)).equals(email)) {
                    binding.msg.setVisibility(View.GONE);
                    add(level, printDate);
                }
                while (cursor.moveToPrevious()) {
                    if (cursor.getString(cursor.getColumnIndex(StoreRecord.date_Col)).equals(date) && cursor.getString(cursor.getColumnIndex(StoreRecord.Email_COL)).equals(email)) {
                        LocalDate dateObj2 = LocalDate.parse(cursor.getString(cursor.getColumnIndex(StoreRecord.date_Col)));
                        String printDate2 = dateObj2.getDayOfMonth() + " " + dateObj2.getMonth() + " " + dateObj2.getYear();
                        String level2 = cursor.getString(cursor.getColumnIndex(StoreRecord.Stress_level));
                        binding.msg.setVisibility(View.GONE);
                        add(level2, printDate2);
                    }
                }
            }
            cursor.close();
        });

        return binding.getRoot();
    }

    private void add(String value, String date) {
        CardView cardView = new CardView(requireContext());
        cardView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        ((LinearLayout.LayoutParams) cardView.getLayoutParams()).setMargins(40, 20, 40, 20);
        cardView.setCardBackgroundColor(Color.rgb(255, 255, 255));
        cardView.setRadius(10);
        LinearLayout linearLayout = new LinearLayout(requireContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        ((LinearLayout.LayoutParams) linearLayout.getLayoutParams()).setMargins(16, 16, 16, 16);
        linearLayout.setPadding(20, 20, 20, 20);

        TextView dateView = new TextView(requireContext());
        dateView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        dateView.setTextAppearance(requireContext(), R.style.date_style);
        dateView.setText(date);

        linearLayout.addView(dateView);

        TextView textView1 = new TextView(requireContext());
        textView1.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView1.setTextAppearance(requireContext(), R.style.stress_level_style);
        textView1.setText("Stress Level: " + value + "%");

        linearLayout.addView(textView1);
        cardView.addView(linearLayout);
        LinearLayout ll = binding.container;
        if (ll != null) {
            ll.addView(cardView);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("Range")
    private void showAll(String email) {
        StoreRecord db = new StoreRecord(requireContext(), null);
        Cursor cursor = db.getName();
        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToLast();
            String level;
            if (cursor.getString(cursor.getColumnIndex(StoreRecord.Email_COL)).equals(email)) {
                LocalDate date = LocalDate.parse(cursor.getString(cursor.getColumnIndex(StoreRecord.date_Col)));
                String printDate = date.getDayOfMonth() + " " + date.getMonth() + " " + date.getYear();
                level = cursor.getString(cursor.getColumnIndex(StoreRecord.Stress_level));
                binding.msg.setVisibility(View.GONE);
                add(level, printDate);
            }
            while (cursor.moveToPrevious()) {
                if (cursor.getString(cursor.getColumnIndex(StoreRecord.Email_COL)).equals(email)) {
                    LocalDate date = LocalDate.parse(cursor.getString(cursor.getColumnIndex(StoreRecord.date_Col)));
                    String printDate = date.getDayOfMonth() + " " + date.getMonth() + " " + date.getYear();
                    level = cursor.getString(cursor.getColumnIndex(StoreRecord.Stress_level));
                    binding.msg.setVisibility(View.GONE);
                    add(level, printDate);
                }
            }
            cursor.close();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
 }
}
