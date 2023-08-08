package com.improve10x.doordare;

import android.annotation.SuppressLint;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.improve10x.doordare.databinding.DialogConnectMobileNumberBinding;

import java.util.concurrent.TimeUnit;

public class ConnectMobileNumberDialog extends DialogFragment {

    private DialogConnectMobileNumberBinding binding;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String verificationId;

    public static final String TAG = "ConnectWithMobileNumberDialog";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogConnectMobileNumberBinding.inflate(getLayoutInflater());
        handleVerify();
        handleConfirm();
        return binding.getRoot();
    }

    private void handleVerify() {
        binding.verifyBtn.setOnClickListener(v -> {
            PhoneAuthProvider.OnVerificationStateChangedCallbacks callBacks =
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            ConnectMobileNumberDialog.this.verificationId = verificationId;
                        }
                    };
            PhoneAuthOptions phoneAuthOptions = PhoneAuthOptions.newBuilder(firebaseAuth)
                    .setPhoneNumber(binding.mobileNumberTxt.getText().toString())
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(getActivity())
                    .setCallbacks(callBacks)
                    .build();
            PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);
        });
    }
    private void handleConfirm() {
        binding.confirmBtn.setOnClickListener(v -> {
            String otp = binding.otpTxt.getText().toString();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
            firebaseAuth.getCurrentUser().linkWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @SuppressLint("LongLogTag")
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Log.w(TAG, "LinkWithCredential:Success");
                                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                                dismiss();
                            } else {
                                Log.w(TAG, "LinkWithCredential:Failure", task.getException());
                                Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });
    }
}