package com.example.socialx;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp_Fragment extends Fragment {

    AwesomeValidation awesomeValidation;
    View view;
    EditText editName, editEmail, editPhone, editPassword;
    Button Register;
    CheckBox checkBox;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up_, container, false);

        //Assigning variables

        editName = (EditText) view.findViewById(R.id.Reg_Name);
        editEmail = (EditText) view.findViewById(R.id.Register_email);
        editPhone = (EditText) view.findViewById(R.id.mobile_number);
        editPassword = (EditText) view.findViewById(R.id.Reg_password);
        Register = (Button) view.findViewById(R.id.reg_button);
        checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        progressDialog = new ProgressDialog(getContext());
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        //Assigning Validation Error Style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        //Action for Register Button
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuth();
            }
        });

        //Changing from Sign-Up Fragment to Login Fragment - Doesn't update Main Activity UI

        TextView Sign_In = view.findViewById(R.id.SignIn_Text);
        Sign_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Login_Fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragment);
                fragmentTransaction.commit();
            }
        });

        return view;
        }

        //Performing Authentication by checking Validation using Awesome Validation

    private void performAuth() {
            String email = editEmail.getText().toString();
            String password = editPassword.getText().toString();
            awesomeValidation.addValidation(getActivity(),R.id.Reg_Name, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
            awesomeValidation.addValidation(getActivity(),R.id.Register_email, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
            awesomeValidation.addValidation(getActivity(),R.id.mobile_number,Patterns.PHONE,R.string.invalid_mobile);
            awesomeValidation.addValidation(getActivity(),R.id.Reg_password,".{6,}",R.string.invalid_password);

            //Check whether the check box is checked or not
            if(!checkBox.isChecked()){
                Toast.makeText(getContext(), "Please accept Terms and Conditions", Toast.LENGTH_SHORT).show();
            }

            if ((awesomeValidation.validate()) && (checkBox.isChecked())) {
            progressDialog.setTitle("Registering new User");
            progressDialog.setMessage("Registering...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(getContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    //If Registration is Successful, the Sign-Up Fragment changes to Login Fragment - Doesn't update Main Activity UI

    private void sendUserToNextActivity() {
        Fragment fragment = new Login_Fragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}