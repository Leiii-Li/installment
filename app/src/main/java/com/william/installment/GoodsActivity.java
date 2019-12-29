package com.william.installment;

import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class GoodsActivity extends AppCompatActivity {

    public static final String INDEX_KEY = "INDEX_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        int index = getIntent().getIntExtra(INDEX_KEY, 0);
        ImageView iv = findViewById(R.id.icon_iv);
        iv.setBackgroundResource(StepTwoActivity.mDrawableArray[index]);
    }
}
