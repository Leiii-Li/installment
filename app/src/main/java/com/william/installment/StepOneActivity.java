package com.william.installment;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.Manifest;
import android.Manifest.permission;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import cn.jpush.sms.SMSSDK;
import cn.jpush.sms.listener.SmscheckListener;
import cn.jpush.sms.listener.SmscodeListener;

import com.william.installment.databinding.StepOneActivityBinding;

public class StepOneActivity extends AppCompatActivity implements OnClickListener {

    public static final String TAG = StepOneActivity.class.getSimpleName();
    private ProgressDialog mProgressDialog;
    private StepOneActivityBinding mViewDataBinding;
    private String mSmsUUid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mViewDataBinding = DataBindingUtil
                .inflate(LayoutInflater.from(this), R.layout.step_one_activity, null, false);
        mViewDataBinding.setOnClickListener(this);
        setContentView(mViewDataBinding.getRoot());
        Bitmap bitmap = Code.getInstance().createBitmap();
        ImageView imageView = findViewById(R.id.code_iv);
        imageView.setImageBitmap(bitmap);

        check();
    }

    private void check() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        Utils.getWebsiteDatetime().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean response) {
                if (response) {
                    mProgressDialog.dismiss();
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, permission.INTERNET) != PERMISSION_GRANTED) {
                // 检查权限状态
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission.INTERNET)) {
                    //  用户彻底拒绝授予权限，一般会提示用户进入设置权限界面
                } else {
                    //  用户未彻底拒绝授予权限
                    ActivityCompat.requestPermissions(this, new String[]{permission.INTERNET}, 1);
                }
            } else {
                CustomeApplication.check();
            }
        } else {
            CustomeApplication.check();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PERMISSION_GRANTED) {
                    // 申请成功
                    CustomeApplication.check();
                } else {
                    // 申请失败
                    CustomeApplication.check();
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_btn:
                String phoneNumber = mViewDataBinding.phoneNumberEt.getText().toString();
                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(StepOneActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Utils.isPhoneNumber(phoneNumber)) {
                    Toast.makeText(StepOneActivity.this, "输入的手机号码有误", Toast.LENGTH_SHORT).show();
                    return;
                }
                String ivVc = mViewDataBinding.imageVcEt.getText().toString();
                if (TextUtils.isEmpty(ivVc)) {
                    Toast.makeText(StepOneActivity.this, "图片验证码为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Code.getInstance().getCode().equalsIgnoreCase(ivVc)) {
                    Toast.makeText(StepOneActivity.this, "图片验证码错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(StepOneActivity.this, AuthenticationActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}
