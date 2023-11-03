package com.example.grocerylist.fragments;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.grocerylist.R;

public class AboutFragment extends Fragment {
    public AboutFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about, container, false);
        TextView version = root.findViewById(R.id.version);
        PackageManager manager = requireContext().getPackageManager();
        try {
            PackageInfo pi = manager.getPackageInfo(requireContext().getPackageName(), 0);
            version.append(" " + pi.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        return root;
    }
}
