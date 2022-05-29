package com.example.socialx;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

public class Login_Fragment extends Fragment {

    View view;
    AwesomeValidation awesomeValidation;
    EditText editEmail, editPassword;
    Button Login;
    ImageView GoogleLogin;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    RelativeLayout rl_1,rl_2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login_, container, false);

        //Assigning variables

        editEmail = (EditText) view.findViewById(R.id.Login_email);
        editPassword = (EditText) view.findViewById(R.id.Login_password);
        Login = (Button) view.findViewById(R.id.button);
        progressDialog = new ProgressDialog(getContext());
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        GoogleLogin = (ImageView) view.findViewById(R.id.Google);

        //Performing Google Sign-In
        GoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GoogleSignInActivity.class);
                startActivity(intent);
            }
        });

        //Assigning Validation Error Style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //Action for Register Button
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuth();
            }
        });
        
        //Changing from Login Fragment to Sign-Up Fragment 
        TextView Register = view.findViewById(R.id.Register_text);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Fragment fragment = new SignUp_Fragment();
              FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
              FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
              fragmentTransaction.replace(R.id.frame_layout,fragment);
              fragmentTransaction.commit();
                
                //Updating UI
                rl_1 = (RelativeLayout)getActivity().findViewById(R.id.rl_1);
                rl_1.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.white_outlook_left));
                TextView text = (TextView) getActivity().findViewById(R.id.Login);
                text.setTextColor(getResources().getColor(R.color.text_color));

                rl_2 = (RelativeLayout)getActivity().findViewById(R.id.rl_2);
                rl_2.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.red_outlook));
                TextView text_1 = (TextView) getActivity().findViewById(R.id.Sign_Up);
                text_1.setTextColor(getResources().getColor(R.color.white));
            }
        });
        return view;
    }
    //Performing Authentication by checking Validation using Awesome Validation
    private void performAuth() {
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        awesomeValidation.addValidation(getActivity(),R.id.Login_email, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        awesomeValidation.addValidation(getActivity(),R.id.Login_password,".{6,}",R.string.invalid_password);

        if (awesomeValidation.validate()){
            progressDialog.setTitle("User Logging in");
            progressDialog.setMessage("Checking Credentials...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        progressDialog.dismiss();
                        LoginActivity();
                        Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(getContext(),"Invalid Credentials"+"\n"+task.getException(),Toast.LENGTH_SHORT).show();}

                }
            });
        }
    }

    //If Login is Successful, the Sign-Up Fragment changes to Login Fragment - Doesn't update Main Activity UI
    private void LoginActivity() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
    }
}
