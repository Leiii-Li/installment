package com.william.installment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.bumptech.glide.Glide;
import java.util.Random;

public class StepTwoActivity extends AppCompatActivity {

    public static int[] mDrawableArray = new int[]{R.drawable.iphone_8, R.drawable.iphone_xr_black,
        R.drawable.iphone_xr_red};
    public static String[] mDesc = {"苹果(Apple)苹果XR 64GB白色 移动联通电信4G全面屏手机", "苹果(Apple)苹果8 64GB黑色 移动联通电信4G手机",
        "苹果(Apple)苹果8 64GB黑色 移动联通电信4G手机"};
    public static String[] mPrice = {"¥2300", "¥4655", "¥4000"};

    private Random mRandom = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.step_two_activity);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PhoneGoodsAdapter());
    }

    private class PhoneGoodsAdapter extends RecyclerView.Adapter<PhoneGoodsHolder> {

        @NonNull
        @Override
        public PhoneGoodsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            PhoneGoodsHolder holder = new PhoneGoodsHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_item_layout, null));
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final PhoneGoodsHolder holder, final int position) {
            int index = mRandom.nextInt(mDrawableArray.length);
            holder.bindView(index);
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(StepTwoActivity.this, GoodsActivity.class);
                    intent.putExtra(GoodsActivity.INDEX_KEY, holder.getIndex());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return 30;
        }
    }

    private static class PhoneGoodsHolder extends ViewHolder {

        private ImageView mPhoneIconIv;
        private TextView mPhoneDesTv, mPriceTv;
        private int mPosition;

        public PhoneGoodsHolder(@NonNull View itemView) {
            super(itemView);
            mPhoneIconIv = itemView.findViewById(R.id.phone_icon_iv);
            mPhoneDesTv = itemView.findViewById(R.id.phone_desc);
            mPriceTv = itemView.findViewById(R.id.phone_price);
        }

        public void bindView(int position) {
            mPosition = position;
            Glide.with(CustomeApplication.mContext).load(mDrawableArray[position]).into(mPhoneIconIv);
            mPhoneDesTv.setText(mDesc[position]);
            mPriceTv.setText(mPrice[position]);
        }

        public int getIndex() {
            return mPosition;
        }
    }
}
