package com.improve10x.doordare;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ConnectMobileNumberDialog extends DialogFragment {

    private DialogConnectMobileNumberBinding binding;
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String verificationId;
    private static final String TAG = "ConnectWithMobileNumberDialog";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogConnectMobileNumberBinding.inflate(getLayoutInflater());
        handleSendOTP();
        handleConfirm();
        setupSpinner();
        return binding.getRoot();
    }

    private void handleSendOTP() {
        binding.sendOtpBtn.setOnClickListener(v -> {
            String phoneNumber = binding.mobileNumberTxt.getText().toString();
            if (!phoneNumber.isEmpty()) {
                PhoneAuthOptions phoneAuthOptions = PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(getActivity())
                        .setCallbacks(createCallbacksObject())
                        .build();
                PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);
            } else {
                Toast.makeText(getContext(), "Enter valid Mobile number", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks createCallbacksObject() {
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
                        binding.group.setVisibility(View.VISIBLE);
                        binding.sendOtpBtn.setVisibility(View.GONE);
                    }
                };
        return callBacks;
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
                            if (task.isSuccessful()) {
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

    private ArrayList<CountryCode> getCountryCodes() {
        ArrayList<CountryCode> countryCodes = new ArrayList<>();
        countryCodes.add(new CountryCode("Afghanistan","+93"));
        countryCodes.add(new CountryCode("Albania","+355"));
        countryCodes.add(new CountryCode("Algeria","+213"));
        countryCodes.add(new CountryCode("American Samoa","+1 684"));
        countryCodes.add(new CountryCode("Andorra","+376"));
        countryCodes.add(new CountryCode("Angola","+244"));
        countryCodes.add(new CountryCode("Anguilla","+1 264"));
        countryCodes.add(new CountryCode("Antarctica","+672, 64"));
        countryCodes.add(new CountryCode("Antigua and Barbuda","+1 268"));
        countryCodes.add(new CountryCode("Argentina","+54"));
        countryCodes.add(new CountryCode("Armenia","+374"));
        countryCodes.add(new CountryCode("Aruba","+297"));
        countryCodes.add(new CountryCode("Ascension Island","+247"));
        countryCodes.add(new CountryCode("Australia","+61"));
        countryCodes.add(new CountryCode("Austria","+43"));
        countryCodes.add(new CountryCode("Azerbaijan","+994"));
        countryCodes.add(new CountryCode("Bahamas","+1 242"));
        countryCodes.add(new CountryCode("Bahrain","+973"));
        countryCodes.add(new CountryCode("Bangladesh","+880"));
        countryCodes.add(new CountryCode("Barbados","+1 246"));
        countryCodes.add(new CountryCode("Belarus","+375"));
        countryCodes.add(new CountryCode("Belgium","+32"));
        countryCodes.add(new CountryCode("Belize","+501"));
        countryCodes.add(new CountryCode("Benin","+229"));
        countryCodes.add(new CountryCode("Bermuda","+1 441"));
        countryCodes.add(new CountryCode("Bhutan","+975"));
        countryCodes.add(new CountryCode("Bolivia","+591"));
        countryCodes.add(new CountryCode("Bosnia and Herzegovina","+387"));
        countryCodes.add(new CountryCode("Botswana","+267"));
        countryCodes.add(new CountryCode("Brazil","+55"));
        countryCodes.add(new CountryCode("British Virgin Islands","+1 284"));
        countryCodes.add(new CountryCode("Brunei","+673"));
        countryCodes.add(new CountryCode("Bulgaria","+359"));
        countryCodes.add(new CountryCode("Burkina Faso","+226"));
        countryCodes.add(new CountryCode("Burma","+95"));
        countryCodes.add(new CountryCode("Burundi","+257"));
        countryCodes.add(new CountryCode("Cambodia","+855"));
        countryCodes.add(new CountryCode("Cameroon","+237"));
        countryCodes.add(new CountryCode("Canada","+1"));
        countryCodes.add(new CountryCode("Cape Verde","+238"));
        countryCodes.add(new CountryCode("Cayman Islands","+1 345"));
        countryCodes.add(new CountryCode("Central African Republic","+236"));
        countryCodes.add(new CountryCode("Chad","+235"));
        countryCodes.add(new CountryCode("Chile","+56"));
        countryCodes.add(new CountryCode("China","+86"));
        countryCodes.add(new CountryCode("Christmas Island","+61"));
        countryCodes.add(new CountryCode("Cocos Islands ","+61"));
        countryCodes.add(new CountryCode("Colombia","+57"));
        countryCodes.add(new CountryCode("Comoros","+269"));
        countryCodes.add(new CountryCode("Congo","+242"));
        countryCodes.add(new CountryCode("Cook Islands","+682"));
        countryCodes.add(new CountryCode("Costa Rica","+506"));
        countryCodes.add(new CountryCode("Croatia","+385"));
        countryCodes.add(new CountryCode("Cuba","+53"));
        countryCodes.add(new CountryCode("Cyprus","+357"));
        countryCodes.add(new CountryCode("Czech Republic","+420"));
        countryCodes.add(new CountryCode("Democratic Republic of the Congo","+243"));
        countryCodes.add(new CountryCode("Denmark","+45"));
        countryCodes.add(new CountryCode("Diego Garcia","+246"));
        countryCodes.add(new CountryCode("Djibouti","+253"));
        countryCodes.add(new CountryCode("Dominica","+1 767"));
        countryCodes.add(new CountryCode("Dominican Republic","+1 809, 1 829, 1 849"));
        countryCodes.add(new CountryCode("Ecuador","+593"));
        countryCodes.add(new CountryCode("Egypt","+20"));
        countryCodes.add(new CountryCode("El Salvador","+503"));
        countryCodes.add(new CountryCode("Equatorial Guinea","+240"));
        countryCodes.add(new CountryCode("Eritrea","+291"));
        countryCodes.add(new CountryCode("Estonia","+372"));
        countryCodes.add(new CountryCode("Ethiopia","+251"));
        countryCodes.add(new CountryCode("Falkland Islands","+500"));
        countryCodes.add(new CountryCode("Faroe Islands","+298"));
        countryCodes.add(new CountryCode("Fiji","+679"));
        countryCodes.add(new CountryCode("Finland","+358"));
        countryCodes.add(new CountryCode("France","+33"));
        countryCodes.add(new CountryCode("French Guiana","+594"));
        countryCodes.add(new CountryCode("French Polynesia","+689"));
        countryCodes.add(new CountryCode("Gabon","+241"));
        countryCodes.add(new CountryCode("Gambia","+220"));
        countryCodes.add(new CountryCode("Georgia","+995"));
        countryCodes.add(new CountryCode("Germany","+49"));
        countryCodes.add(new CountryCode("Ghana","+233"));
        countryCodes.add(new CountryCode("Gibraltar","+350"));
        countryCodes.add(new CountryCode("Greece","+30"));
        countryCodes.add(new CountryCode("Greenland","+299"));
        countryCodes.add(new CountryCode("Grenada","+1 473"));
        countryCodes.add(new CountryCode("Guadeloupe","+590"));
        countryCodes.add(new CountryCode("Guam","+1 671"));
        countryCodes.add(new CountryCode("Guatemala","+502"));
        countryCodes.add(new CountryCode("Guinea","+224"));
        countryCodes.add(new CountryCode("Guinea-Bissau","+245"));
        countryCodes.add(new CountryCode("Guyana","+592"));
        countryCodes.add(new CountryCode("Haiti","+509"));
        countryCodes.add(new CountryCode("Holy See","+39"));
        countryCodes.add(new CountryCode("Honduras","+504"));
        countryCodes.add(new CountryCode("Hong Kong","+852"));
        countryCodes.add(new CountryCode("Hungary","+36"));
        countryCodes.add(new CountryCode("Iceland","+354"));
        countryCodes.add(new CountryCode("India","+91"));
        countryCodes.add(new CountryCode("Indonesia","+62"));
        countryCodes.add(new CountryCode("Iran","+98"));
        countryCodes.add(new CountryCode("Iraq","+964"));
        countryCodes.add(new CountryCode("Ireland","+353"));
        countryCodes.add(new CountryCode("Isle of Man","+44"));
        countryCodes.add(new CountryCode("Israel","+972"));
        countryCodes.add(new CountryCode("Italy","+39"));
        countryCodes.add(new CountryCode("Ivory Coast","+225"));
        countryCodes.add(new CountryCode("Jamaica","+1 876"));
        countryCodes.add(new CountryCode("Japan","+81"));
        countryCodes.add(new CountryCode("Jersey","+44"));
        countryCodes.add(new CountryCode("Jordan","+962"));
        countryCodes.add(new CountryCode("Kazakhstan","+7"));
        countryCodes.add(new CountryCode("Kenya","+254"));
        countryCodes.add(new CountryCode("Kiribati","+686"));
        countryCodes.add(new CountryCode("Kuwait","+965"));
        countryCodes.add(new CountryCode("Kyrgyzstan","+996"));
        countryCodes.add(new CountryCode("Laos","+856"));
        countryCodes.add(new CountryCode("Latvia","+371"));
        countryCodes.add(new CountryCode("Lebanon","+961"));
        countryCodes.add(new CountryCode("Lesotho","+266"));
        countryCodes.add(new CountryCode("Liberia","+231"));
        countryCodes.add(new CountryCode("Libya", "+218"));
        countryCodes.add(new CountryCode("Liechtenstein ", "+423"));
        countryCodes.add(new CountryCode("Lithuania ", "+370"));
        countryCodes.add(new CountryCode("Luxembourg", "+352 "));
        countryCodes.add(new CountryCode("Macau", "+853"));
        countryCodes.add(new CountryCode("Macedonia", "+389"));
        countryCodes.add(new CountryCode("Madagascar", "+261"));
        countryCodes.add(new CountryCode("Malawi", "+265"));
        countryCodes.add(new CountryCode("Malaysia", "+60"));
        countryCodes.add(new CountryCode("Maldives", "+960"));
        countryCodes.add(new CountryCode("Mali", "+223"));
        countryCodes.add(new CountryCode("Malta", "+356"));
        countryCodes.add(new CountryCode("Marshall Islands", "+692"));
        countryCodes.add(new CountryCode("Martinique", "+596"));
        countryCodes.add(new CountryCode("Mauritania", "+222"));
        countryCodes.add(new CountryCode("Mauritius", "+230"));
        countryCodes.add(new CountryCode("Mayotte", "+262"));
        countryCodes.add(new CountryCode("Mexico", "+52"));
        countryCodes.add(new CountryCode("Micronesia", "+691"));
        countryCodes.add(new CountryCode("Moldova", "+373"));
        countryCodes.add(new CountryCode("Monaco", "+377"));
        countryCodes.add(new CountryCode("Mongolia", "+976"));
        countryCodes.add(new CountryCode("Montenegro", "+382"));
        countryCodes.add(new CountryCode("Montserrat", "+1 664"));
        countryCodes.add(new CountryCode("Morocco", "+212"));
        countryCodes.add(new CountryCode("Mozambique", "+258"));
        countryCodes.add(new CountryCode("Namibia", "+264"));
        countryCodes.add(new CountryCode("Nauru", "+674"));
        countryCodes.add(new CountryCode("Nepal", "+977"));
        countryCodes.add(new CountryCode("Netherlands", "+31"));
        countryCodes.add(new CountryCode("NNetherlands Antilles", "+599"));
        countryCodes.add(new CountryCode("New Caledonia", "+687"));
        countryCodes.add(new CountryCode("New Zealand", "+64"));
        countryCodes.add(new CountryCode("Nicaragua", "+505"));
        countryCodes.add(new CountryCode("Niger", "+227"));
        countryCodes.add(new CountryCode("Nigeria", "+234"));
        countryCodes.add(new CountryCode("Niue", "+683"));
        countryCodes.add(new CountryCode("Norfolk Island", "+672"));
        countryCodes.add(new CountryCode("North Korea", "+850"));
        countryCodes.add(new CountryCode("Northern Mariana IslandsP", "+1 670"));
        countryCodes.add(new CountryCode("Norway", "+47"));
        countryCodes.add(new CountryCode("Oman", "+968"));
        countryCodes.add(new CountryCode("Pakistan", "+92"));
        countryCodes.add(new CountryCode("Palau", "+680"));
        countryCodes.add(new CountryCode("Palestine", "+970"));
        countryCodes.add(new CountryCode("Panama", "+507"));
        countryCodes.add(new CountryCode("Papua New", "+675"));
        countryCodes.add(new CountryCode("Paraguay", "+595"));
        countryCodes.add(new CountryCode("Peru", "+51"));
        countryCodes.add(new CountryCode("Philippines", "+63"));
        countryCodes.add(new CountryCode("Pitcairn Islands", "+870"));
        countryCodes.add(new CountryCode("Poland", "+48"));
        countryCodes.add(new CountryCode("Portugal", "+351"));
        countryCodes.add(new CountryCode("Puerto Rico", "+1 787, 1 939"));
        countryCodes.add(new CountryCode("Qatar", "+974"));
        countryCodes.add(new CountryCode("Republic of the Congo", "+242"));
        countryCodes.add(new CountryCode("Reunion Island", "+262"));
        countryCodes.add(new CountryCode("Romania", "+40"));
        countryCodes.add(new CountryCode("Russia", "+7"));
        countryCodes.add(new CountryCode("Rwanda", "+250"));
        countryCodes.add(new CountryCode("Saint Barthelemy", "+590"));
        countryCodes.add(new CountryCode("Saint Helena", "+290"));
        countryCodes.add(new CountryCode("Saint Kitts and Nevis", "+1 869"));
        countryCodes.add(new CountryCode("Saint Lucia", "+1 758"));
        countryCodes.add(new CountryCode("Saint Martin", "+590"));
        countryCodes.add(new CountryCode("Saint Pierre and Miquelon", "+508"));
        countryCodes.add(new CountryCode("Saint Vincent and the Grenadines", "+1 784"));
        countryCodes.add(new CountryCode("Samoa", "+685"));
        countryCodes.add(new CountryCode("San Marino", "+378"));
        countryCodes.add(new CountryCode("Sao Tome and Principe", "+239"));
        countryCodes.add(new CountryCode("Saudi Arabia", "+966"));
        countryCodes.add(new CountryCode("Senegal", "+221"));
        countryCodes.add(new CountryCode("Serbia", "+381"));
        countryCodes.add(new CountryCode("Seychelles", "+248"));
        countryCodes.add(new CountryCode("Sierra Leone", "+232"));
        countryCodes.add(new CountryCode("Singapore", "+65"));
        countryCodes.add(new CountryCode("Sint Maarten", "+1 721"));
        countryCodes.add(new CountryCode("Slovakia", "+421"));
        countryCodes.add(new CountryCode("Slovenia", "+386"));
        countryCodes.add(new CountryCode("Solomon Islands", "+677"));
        countryCodes.add(new CountryCode("Somalia", "+252"));
        countryCodes.add(new CountryCode("South Africa", "+27"));
        countryCodes.add(new CountryCode("South Korea", "+82"));
        countryCodes.add(new CountryCode("South Sudan", "+211"));
        countryCodes.add(new CountryCode("Spain", "+34"));
        countryCodes.add(new CountryCode("Sri Lanka", "+94"));
        countryCodes.add(new CountryCode("Sudan", "+249"));
        countryCodes.add(new CountryCode("Suriname", "+597"));
        countryCodes.add(new CountryCode("Svalbard", "+47"));
        countryCodes.add(new CountryCode("Swaziland", "+268"));
        countryCodes.add(new CountryCode("Sweden", "+46"));
        countryCodes.add(new CountryCode("Switzerland", "+41"));
        countryCodes.add(new CountryCode("Syria", "+963"));
        countryCodes.add(new CountryCode("Taiwan", "+886"));
        countryCodes.add(new CountryCode("Tajikistan", "+992"));
        countryCodes.add(new CountryCode("Tanzania", "+255"));
        countryCodes.add(new CountryCode("Thailand", "+66"));
        countryCodes.add(new CountryCode("Timor-Leste", "+670"));
        countryCodes.add(new CountryCode("Togo", "+228"));
        countryCodes.add(new CountryCode("Tokelau", "+690"));
        countryCodes.add(new CountryCode("Tonga Islands", "+676"));
        countryCodes.add(new CountryCode("Trinidad and Tobago", "+1 868"));
        countryCodes.add(new CountryCode("Tunisia", "+216"));
        countryCodes.add(new CountryCode("Turkey", "+90"));
        countryCodes.add(new CountryCode("Turkmenistan", "+993"));
        countryCodes.add(new CountryCode("Turks and Caicos Islands", "+1 649"));
        countryCodes.add(new CountryCode("Tuvalu", "+688"));
        countryCodes.add(new CountryCode("Uganda", "+256"));
        countryCodes.add(new CountryCode("Ukraine", "+380"));
        countryCodes.add(new CountryCode("United Arab Emirates", "+971"));
        countryCodes.add(new CountryCode("United Kingdom", "+44"));
        countryCodes.add(new CountryCode("United States", "+1"));
        countryCodes.add(new CountryCode("Uruguay", "+598"));
        countryCodes.add(new CountryCode("US Virgin Islands", "+1 340"));
        countryCodes.add(new CountryCode("Uzbekistan", "+998"));
        countryCodes.add(new CountryCode("Vanuatu", "+678"));
        countryCodes.add(new CountryCode("Venezuela", "+58"));
        countryCodes.add(new CountryCode("Vietnam", "+84"));
        countryCodes.add(new CountryCode("Wallis and Futuna", "+681"));
        countryCodes.add(new CountryCode("Western Sahara", "+212"));
        countryCodes.add(new CountryCode("Yemen", "+967"));
        countryCodes.add(new CountryCode("Zambia", "+260"));
        countryCodes.add(new CountryCode("Zimbabwe", "+263"));
        return countryCodes;
    }

    private void setupSpinner() {
        CountryCodesAdapter adapter = new CountryCodesAdapter(getContext(), R.layout.country_code_item, getCountryCodes());
        binding.countryCodeSp.setAdapter(adapter);
    }
}
