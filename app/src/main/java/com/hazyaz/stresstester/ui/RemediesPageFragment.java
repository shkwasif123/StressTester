package com.hazyaz.stresstester.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hazyaz.stresstester.R;
import com.hazyaz.stresstester.SignedInUser;
import com.hazyaz.stresstester.StoreRecord;
import com.hazyaz.stresstester.DataClass;
import com.hazyaz.stresstester.databinding.FragmentRemediesPageBinding;

import java.util.ArrayList;
import java.util.List;

public class RemediesPageFragment extends Fragment {
    private FragmentRemediesPageBinding _binding;
    ArrayList<String>recommendedBooks = new ArrayList<String>();

    private FragmentRemediesPageBinding getBinding() {
        return _binding;
    }

    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout and bind to the _binding
        _binding = FragmentRemediesPageBinding.inflate(inflater, container, false);

        getBinding().Call.setOnClickListener(this::Call);

//list of books
        recommendedBooks.add(" Be Calm: Proven Techniques to Stop Anxiety Now \n – Jill Weber. ");
        recommendedBooks.add(" Don’t Feed the Monkey Mind: How to Stop the Cycle of Anxiety, Fear and Worry \n – Jennifer Shannon. ");
        recommendedBooks.add(" How to Be Yourself: Quiet Your Inner Critic and Rise Above Social Anxiety \n – Ellen Hendriksen.");
        recommendedBooks.add(" Unwinding Anxiety: New Science Shows How to Break the Cycles of Worry and Fear to Heal Your Mind  \n – Judson Brewer.");
        recommendedBooks.add(" Taking Control of Anxiety: Small Steps for Getting the Best of Worry, Stress and Fear \n – Bret A. Moore. ");
        recommendedBooks.add(" The Anxiety Toolkit: Strategies for Fine-Tuning Your Mind and Moving Past Your Stuck Points \n  – Alice Boyes.  ");
        recommendedBooks.add(" The Anxiety First Aid Kit: Quick Tools for Extreme, Uncertain Times\n – Rick Hanson et al. ");
        recommendedBooks.add(" Don’t Sweat the Small Stuff… and It’s All Small Stuff: Simple Ways to Keep the Little Things From Taking Over Your Life \n –  Richard Carlson. ");
        recommendedBooks.add(" Why Zebras Don’t Get Ulcers \n – Robert M. Sapolsky.  ");
        recommendedBooks.add(" The Upside of Stress: Why Stress Is Good for You, and How to Get Good at It \n – Kelly McGonigal. ");


        DataClass dataClass = new DataClass();

        SignedInUser userDb = new SignedInUser(requireContext(), null);
        Cursor cursor1 = userDb.getName();
        cursor1.moveToLast();
        String signedInEmail = cursor1.getString(cursor1.getColumnIndex(SignedInUser.EMAIL_COL));
        cursor1.close();

        StoreRecord db = new StoreRecord(requireContext(), null);
        Cursor cursor = db.getName();
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(cursor.getColumnIndex(StoreRecord.Email_COL)).equals(signedInEmail)) {
                    getBinding().notTestedMsg.setVisibility(View.GONE);
                    getBinding().remedyHolder.setVisibility(View.VISIBLE);
                    int percentageStress = cursor.getInt(cursor.getColumnIndex(StoreRecord.Stress_level));
                    getBinding().remedy.setText(dataClass.getRemedies(getCorrectRemedy(percentageStress)));

                    getBinding().image.setImageResource(dataClass.setProperImage(getCorrectRemedy(percentageStress)));
                    add(recommendedBooks);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return getBinding().getRoot();
    }

    private int getCorrectRemedy(int percentageStress) {
        int index = 0;
        if (percentageStress >= 71 && percentageStress <= 100) {
            index = 6;
            getBinding().Call.setVisibility(View.VISIBLE);
        } else if (percentageStress >= 51 && percentageStress <= 70) {
            index = 5;
            getBinding().Call.setVisibility(View.GONE);
        } else if (percentageStress >= 41 && percentageStress <= 50) {
            index = 4;
            getBinding().Call.setVisibility(View.GONE);
        } else if (percentageStress >= 31 && percentageStress <= 40) {
            index = 3;
            getBinding().Call.setVisibility(View.GONE);
        } else if (percentageStress >= 21 && percentageStress <= 30) {
            index = 2;
            getBinding().Call.setVisibility(View.GONE);
        } else if (percentageStress >= 11 && percentageStress <= 20) {
            index = 1;
            getBinding().Call.setVisibility(View.GONE);
        } else if (percentageStress >= 0 && percentageStress <= 10) {
            index = 0;
            getBinding().Call.setVisibility(View.GONE);
        }
        return index;
    }

    public void Call(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:7738858013"));
        startActivity(intent);
    }
    private void add(ArrayList<String>recommendedBooks) {
        for (int i = 0; i < recommendedBooks.size(); i++) {

            CardView cardView = new CardView(requireContext());
            cardView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            ((LinearLayout.LayoutParams) cardView.getLayoutParams()).setMargins(40, 20, 40, 20);
            cardView.setCardBackgroundColor(Color.rgb(255, 255, 255));
            cardView.setRadius(10);


            TextView textView1 = new TextView(requireContext());
            textView1.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            ((LinearLayout.LayoutParams) textView1.getLayoutParams()).setMargins(40, 20, 40, 20);
            textView1.setTextAppearance(requireContext(), R.style.stress_level_style);
            textView1.setText(recommendedBooks.get(i));

            cardView.addView(textView1);
            LinearLayout ll = _binding.bookList;
            if (ll != null) {
                ll.addView(cardView);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        _binding=null;
    }
}