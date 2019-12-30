package com.william.installment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.william.installment.databinding.ActivityAuthenticationBinding;

public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAuthenticationBinding mViewDataBinding;
    private static final int CARD_POSITIVE_REQUEST_CODE = 1010;
    private static final int CARD_REVERSE_REQUEST_CODE = 1011;
    private static final int CARD_HUMNAN_REQUEST_CODE = 1012;
    private String mCardPositiveUrl;
    private String mCardReverseUrl;
    private String mHumanUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_authentication, null, false);
        setContentView(mViewDataBinding.getRoot());
        mViewDataBinding.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.positive_upload:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, CARD_POSITIVE_REQUEST_CODE);
                break;
            case R.id.reverse_upload:
                intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, CARD_REVERSE_REQUEST_CODE);
                break;
            case R.id.human_upload:
                intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, CARD_HUMNAN_REQUEST_CODE);
                break;
            case R.id.register_btn:
                nextStep();
                break;
            default:
                break;
        }
    }

    private void nextStep() {
        if (TextUtils.isEmpty(mCardPositiveUrl) || TextUtils.isEmpty(mCardReverseUrl) || TextUtils.isEmpty(mHumanUrl)) {
            Toast.makeText(this, "请上传所需的图片信息", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(AuthenticationActivity.this, StepTwoActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RESULT_OK) {
            String photoPath = PhotoUtils.getRealPathFromUri(this, data.getData());
            switch (requestCode) {
                case CARD_POSITIVE_REQUEST_CODE:
                    mCardPositiveUrl = photoPath;
                    loadImage();
                    break;
                case CARD_REVERSE_REQUEST_CODE:
                    mCardReverseUrl = photoPath;
                    loadImage();
                    break;
                case CARD_HUMNAN_REQUEST_CODE:
                    mHumanUrl = photoPath;
                    loadImage();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadImage() {
        Glide.with(this).load(mCardPositiveUrl).into(mViewDataBinding.cardPositiveIv);
        Glide.with(this).load(mCardReverseUrl).into(mViewDataBinding.cardReverseIv);
        Glide.with(this).load(mHumanUrl).into(mViewDataBinding.cardHumanIv);
    }
}
