package com.hazyaz.stresstester;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hazyaz.stresstester.ui.HomePageFragment;
import com.hazyaz.stresstester.ui.ProfilePageFragment;
import com.hazyaz.stresstester.ui.RecordPageFragment;
import com.hazyaz.stresstester.ui.RemediesPageFragment;

public class MainActivity extends AppCompatActivity {
    private long mBackPressed = 0;

    public static String personName;
    public static String personEmail;
    public static String personGender;
    public static String personAge;
    public static String personInterest;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new HomePageFragment());
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationBar);
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    loadFragment(new HomePageFragment());
                    return true;
                case R.id.nav_records:
                    loadFragment(new RecordPageFragment());
                    return true;
                case R.id.nav_remedies:
                    loadFragment(new RemediesPageFragment());
                    return true;
                default:
                    loadFragment(new ProfilePageFragment());
                    return true;
            }
        });

        SignedInUser userDb = new SignedInUser(this, null);
        android.database.Cursor cursor1 = userDb.getName();
        cursor1.moveToLast();
        @SuppressLint("Range") String storedEmail = cursor1.getString(cursor1.getColumnIndex(SignedInUser.EMAIL_COL));
        cursor1.close();

        DBHelper db = new DBHelper(this, null);

        android.database.Cursor cursor = db.getName();

        if (cursor.moveToFirst()) {
            if (cursor.getString(cursor.getColumnIndex(DBHelper.Email_Col)).equals(storedEmail)) {
                personEmail = cursor.getString(cursor.getColumnIndex(DBHelper.Email_Col));
                personName = cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl));
                personGender = cursor.getString(cursor.getColumnIndex(DBHelper.Gen_COL));
                personAge = cursor.getString(cursor.getColumnIndex(DBHelper.Age_Col));
                personInterest = cursor.getString(cursor.getColumnIndex(DBHelper.Interest_COL));
            }
            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex(DBHelper.Email_Col)).equals(storedEmail)) {
                    personEmail = cursor.getString(cursor.getColumnIndex(DBHelper.Email_Col));
                    personName = cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl));
                    personGender = cursor.getString(cursor.getColumnIndex(DBHelper.Gen_COL));
                    personAge = cursor.getString(cursor.getColumnIndex(DBHelper.Age_Col));
                    personInterest= cursor.getString(cursor.getColumnIndex(DBHelper.Interest_COL));
                }
            }
            cursor.close();
        }


    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        int TIME_INTERVAL = 3000;
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            finishAffinity();
        } else {
            Toast.makeText(getApplicationContext(), "Tap back button again to exit", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }
}
