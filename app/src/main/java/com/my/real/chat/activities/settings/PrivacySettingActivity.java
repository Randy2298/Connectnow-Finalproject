package com.my.real.chat.activities.settings;

import static com.my.real.chat.activities.constants.IConstants.EXTRA_HIDE_EMAIL;
import static com.my.real.chat.activities.constants.IConstants.EXTRA_HIDE_PROFILE_PHOTO;
import static com.my.real.chat.activities.constants.IConstants.REF_USERS;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.my.real.chat.activities.BaseActivity;
import com.my.real.chat.activities.R;
import com.my.real.chat.activities.managers.Utils;
import com.my.real.chat.activities.models.User;
import com.my.real.chat.activities.views.SingleClickListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PrivacySettingActivity extends BaseActivity implements View.OnClickListener {

    private SwitchCompat emailOnOff, profilePhotoOnOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_settings);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        final String currentId = firebaseUser.getUid();

        final Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.strPrivacySetting);
        mToolbar.setNavigationOnClickListener(new SingleClickListener() {
            @Override
            public void onClickView(View v) {
                onBackPressed();
            }
        });

        final LinearLayout layoutEmail = findViewById(R.id.layoutEmail);
        final LinearLayout layoutProfilePhoto = findViewById(R.id.layoutProfilePhoto);

        profilePhotoOnOff = findViewById(R.id.profilePhotoOnOff);
        profilePhotoOnOff.setOnCheckedChangeListener((compoundButton, b) -> Utils.updateGenericUserField(currentId, EXTRA_HIDE_PROFILE_PHOTO, b));

        emailOnOff = findViewById(R.id.emailOnOff);
        emailOnOff.setOnCheckedChangeListener((compoundButton, b) -> Utils.updateGenericUserField(currentId, EXTRA_HIDE_EMAIL, b));

        reference = FirebaseDatabase.getInstance().getReference(REF_USERS).child(currentId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    assert user != null;
                    emailOnOff.setChecked(user.isHideEmail());
                    profilePhotoOnOff.setChecked(user.isHideProfilePhoto());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        layoutEmail.setOnClickListener(this);
        layoutProfilePhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final int id = view.getId();
        if (id == R.id.layoutEmail) {
            emailOnOff.setChecked(!emailOnOff.isChecked());
        } else if (id == R.id.layoutProfilePhoto) {
            profilePhotoOnOff.setChecked(!profilePhotoOnOff.isChecked());
        }
    }
}
