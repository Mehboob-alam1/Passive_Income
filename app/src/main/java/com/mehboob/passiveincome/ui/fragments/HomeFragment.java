package com.mehboob.passiveincome.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mehboob.passiveincome.R;
import com.mehboob.passiveincome.databinding.FragmentHomeBinding;
import com.mehboob.passiveincome.ui.activities.AboutUsActivity;
import com.mehboob.passiveincome.ui.activities.ChooseAccountActivity;
import com.mehboob.passiveincome.ui.activities.DepositActivity;
import com.mehboob.passiveincome.ui.activities.HomeActivity;
import com.mehboob.passiveincome.ui.activities.PaymentRulesActivity;
import com.mehboob.passiveincome.ui.activities.WithdrawActivity;
import com.mehboob.passiveincome.ui.adapters.SliderAdapter;
import com.mehboob.passiveincome.ui.models.Packages;
import com.mehboob.passiveincome.ui.models.Slider;
import com.mehboob.passiveincome.ui.models.User;
import com.mehboob.passiveincome.utils.Constant;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.UUID;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private DatabaseReference mRef, packRef, userBalanceRef;
    private User user;
    private Context mContext;
    private StorageReference storageReference;
    private String userTotalBalance;
    SliderView sliderView;
    ArrayList<Slider> list;
    SliderAdapter adapter;
    DatabaseReference databaseReference,imageRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRef = FirebaseDatabase.getInstance().getReference("Users");
        packRef = FirebaseDatabase.getInstance().getReference("Packages");
        storageReference = FirebaseStorage.getInstance().getReference("Users");

        databaseReference= FirebaseDatabase.getInstance().getReference();
        list= new ArrayList<>();
        userBalanceRef = FirebaseDatabase.getInstance().getReference("Balance");
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        loadData();
        fetchBanners();
        fetchUserBalance();

        binding.userImage.setOnClickListener(v -> {
            Fragment fragment = new ContactUsFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            HomeActivity.bottomNavigationView.setSelectedItemId(R.id.nav_contact_us);
        });

        binding.cardInvite.setOnClickListener(v -> {
            Fragment fragment = new AccountFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            HomeActivity.bottomNavigationView.setSelectedItemId(R.id.nav_profile);
        });
        binding.btnDeposit.setOnClickListener(v -> {
            BottomSheetDialog dialog = new BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme);
            View bottomsheetView = LayoutInflater.from(requireContext()).
                    inflate(R.layout.bottom_sheet_balance, (CardView) getView().findViewById(R.id.cardBalance));
            dialog.setContentView(bottomsheetView);
            dialog.show();

            AppCompatButton button = bottomsheetView.findViewById(R.id.btnNextBalance);
            EditText editText = bottomsheetView.findViewById(R.id.etBalanceDeposit);

            button.setOnClickListener(v1 -> {
                if (editText.getText().toString().isEmpty()) {
                    Toast.makeText(mContext, "Enter your deposit balance", Toast.LENGTH_SHORT).show();
                } else {
                    String balance = editText.getText().toString();
                    Intent intent = new Intent(requireContext(), ChooseAccountActivity.class);
                    intent.putExtra("balance", balance);
                    dialog.cancel();
                    startActivity(intent);
                }
            });


        });

        binding.btnWithdraw.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(),WithdrawActivity.class);
            intent.putExtra("totalBalance",userTotalBalance);
            requireContext().startActivity(intent);

        });




        binding.btnPaymentRules.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), PaymentRulesActivity.class));
        });
        binding.btnAboutUs.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), AboutUsActivity.class));
        });


        binding.btnWhatsapp.setOnClickListener(v -> {
           openWhatsApp();
        });
        return binding.getRoot();
    }

    private void openWhatsApp() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + Constant.PHONE_NUMBER_ADMIN + "&text=" + Uri.encode(Constant.DEFAULT_MESSAGE)));

// Verify that WhatsApp is installed on the device
        PackageManager packageManager = requireContext().getPackageManager();
        if (intent.resolveActivity(packageManager) != null) {
            // WhatsApp is installed, start the activity
            startActivity(intent);
        } else {
            // WhatsApp is not installed, display an error message or redirect to the Play Store
            Toast.makeText(requireContext(), "WhatsApp is not installed.", Toast.LENGTH_SHORT).show();
        }
    }
    private void setSlider() {
       binding.imageSliderMain.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding.imageSliderMain.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.imageSliderMain.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        binding.imageSliderMain.setIndicatorSelectedColor(Color.WHITE);
        binding.imageSliderMain.setIndicatorUnselectedColor(Color.GRAY);
        binding.imageSliderMain.setScrollTimeInSec(4); //set scroll delay in seconds :
        binding.imageSliderMain.startAutoCycle();

    }

    private void fetchBanners() {

        databaseReference.child("Banners").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String imageUrl = snapshot1.child("imageUrl").getValue(String.class);
                        String imageLink = snapshot1.child("imageLink").getValue(String.class);
                        String pushId = snapshot1.child("pushId").getValue(String.class);

//                        Toast.makeText(StartScreen.this, ""+data, Toast.LENGTH_SHORT).show();
                        list.add(new Slider(imageLink, imageUrl, pushId));
                    }
                    adapter = new SliderAdapter(list,getActivity().getApplicationContext());
                    binding.imageSliderMain.setSliderAdapter(adapter);
                    setSlider();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void fetchUserBalance() {
        userBalanceRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("totalBalance")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            userTotalBalance = snapshot.getValue(String.class);

                            binding.txtUserBalance.setText(userTotalBalance);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }



    private void loadData() {
        Context context = ((HomeActivity) getActivity()).getContext();

        mRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    user = snapshot.getValue(User.class);
                    try {
                        Glide.with(context).load(user.getUser_image()).placeholder(R.drawable.profile).into(binding.userImage);
                    } catch (IllegalArgumentException e) {
                        Log.d("Exception", e.getLocalizedMessage());
                    }

                    binding.txtUserNameHome.setText(user.getFirst_name() + " " + user.getSur_name());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void startPackage(String package_name) {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme);
        View bottomsheetView = LayoutInflater.from(requireContext()).
                inflate(R.layout.bottom_sheet_balance, (CardView) getView().findViewById(R.id.cardBalance));
        dialog.setContentView(bottomsheetView);
        dialog.show();

        AppCompatButton button = bottomsheetView.findViewById(R.id.btnNextBalance);
        EditText editText = bottomsheetView.findViewById(R.id.etBalanceDeposit);

        button.setOnClickListener(v1 -> {
            if (editText.getText().toString().isEmpty()) {
                Toast.makeText(mContext, "Enter your deposit balance", Toast.LENGTH_SHORT).show();
            } else {
                String balance = editText.getText().toString();
                Intent intent = new Intent(requireContext(), ChooseAccountActivity.class);
                intent.putExtra("balance", balance);
                dialog.cancel();
                startActivity(intent);
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


}