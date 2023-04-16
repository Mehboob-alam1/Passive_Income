package com.mehboob.passiveincome.ui.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.mehboob.passiveincome.R;
import com.mehboob.passiveincome.databinding.FragmentAccountBinding;
import com.mehboob.passiveincome.databinding.FragmentContactUsBinding;
import com.mehboob.passiveincome.ui.activities.LoginActivity;


public class ContactUsFragment extends Fragment {

private FragmentContactUsBinding binding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentContactUsBinding.inflate(inflater,container,false);

binding.imgCopyReferCode.setOnClickListener(v -> {
    copyTextToClipboard(binding.txtReferalCode.getText().toString());
});

binding.btnLogout.setOnClickListener(v -> {
   signOut();

});
binding.txtInviteFriend.setOnClickListener(v -> {
    Fragment fragment = new AccountFragment();
    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.container, fragment);
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();
});

        return binding.getRoot();
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        getContext().startActivity(intent);
        getActivity().finishAffinity();
    }

    private void copyTextToClipboard(String text) {
        // Get the text from the TextView


        // Get the ClipboardManager
        ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);

        // Create a new ClipData object to store the text
        ClipData clipData = ClipData.newPlainText("label", text);

        // Set the ClipData object as the clipboard data
        clipboardManager.setPrimaryClip(clipData);

        // Show a toast message to indicate that the text has been copied
        Toast.makeText(getContext(), "Text copied to clipboard " +text, Toast.LENGTH_SHORT).show();
    }
}