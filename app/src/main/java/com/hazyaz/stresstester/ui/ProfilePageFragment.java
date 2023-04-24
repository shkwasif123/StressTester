package com.hazyaz.stresstester.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.hazyaz.stresstester.DataClass;
import com.hazyaz.stresstester.EditProfile;
import com.hazyaz.stresstester.R;
import com.hazyaz.stresstester.SignIn;
import com.hazyaz.stresstester.SignedInUser;
import com.hazyaz.stresstester.databinding.FragmentProfilePageBinding;

public class ProfilePageFragment extends Fragment {
    private FragmentProfilePageBinding binding;
    final DataClass dataClass=new DataClass();
    TextView newBtn;



    @SuppressLint({"SuspiciousIndentation", "Range"})
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfilePageBinding.inflate(inflater, container, false);

        binding.editProfile.setOnClickListener(view -> startActivity(new Intent(requireContext(), EditProfile.class)));
        binding.signOutBtn.setOnClickListener(view -> {
            SignedInUser db = new SignedInUser(requireContext(), null);
            db.delete();
            startActivity(new Intent(requireContext(), SignIn.class));
            if (getActivity() != null) {
                getActivity().finish();
            }
            Toast.makeText(requireContext(), "You are Signed Out", Toast.LENGTH_SHORT).show();
        });

        binding.email.setText("E-mail : " + dataClass.getPersonEmail());
        binding.name.setText("Name : " + dataClass.getPersonName());
        binding.gender.setText("Gender : " + dataClass.getPersonGender());
        binding.age.setText("Age: " + dataClass.getPersonAge());
        addButton(dataClass.getPersonInterest());
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void addButton(String interest) {
        String[] parts=interest.split(",");
       // String[] parts={"Movie","Gaming"};
        LinearLayout layout = binding.rv;
        LinearLayout layout1 = binding.rv2;
        LinearLayout layout2 = binding.rv3;
        for(int i=0;i<parts.length;i++){
            if(i>=0 && i<3){
                newBtn = new TextView(requireContext());
                newBtn.setText(parts[i]);
                newBtn.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                newBtn.setBackground(ContextCompat.getDrawable (requireContext(), R.drawable.txt_btn));
                ((LinearLayout.LayoutParams) newBtn.getLayoutParams()).setMargins(8, 8, 8, 8);
                newBtn.setPadding(16,12,16,12);
                newBtn.setTextAppearance(R.style.interest_txt);
                layout.addView(newBtn);
            } else if (i>=3 && i<6) {
                newBtn = new TextView(requireContext());
                newBtn.setText(parts[i]);
                newBtn.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                newBtn.setBackground(ContextCompat.getDrawable (requireContext(), R.drawable.txt_btn));
                ((LinearLayout.LayoutParams) newBtn.getLayoutParams()).setMargins(8, 8, 8, 8);
                newBtn.setPadding(16,12,16,12);
                newBtn.setTextAppearance(R.style.interest_txt);
                layout1.addView(newBtn);
            }
            else{
                newBtn = new TextView(requireContext());
                newBtn.setText(parts[i]);
                newBtn.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                newBtn.setBackground(ContextCompat.getDrawable (requireContext(), R.drawable.txt_btn));
                ((LinearLayout.LayoutParams) newBtn.getLayoutParams()).setMargins(8, 8, 8, 8);
                newBtn.setPadding(16,12,16,12);
                newBtn.setTextAppearance(R.style.interest_txt);
                layout2.addView(newBtn);
            }
        }
       
       

    }


}
