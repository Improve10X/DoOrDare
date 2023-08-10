package com.improve10x.doordare.connectguesttomobilelogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.improve10x.doordare.databinding.CountryCodeItemBinding;

import java.util.List;

public class CountryCodesAdapter extends ArrayAdapter<CountryCode> {

    public CountryCodesAdapter(@NonNull Context context, int resource, @NonNull List<CountryCode> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CountryCodeItemBinding binding = CountryCodeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        CountryCode countryCode = getItem(position);
        binding.text1.setText(countryCode.getCountryName() + " (" + countryCode.getCountryCode() + ")");
        return binding.getRoot();
    }
}