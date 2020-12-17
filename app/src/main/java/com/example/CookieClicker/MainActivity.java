package com.example.CookieClicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    AtomicInteger totalCookies;
    TextView mainText;
    ImageView cookie;
    int cookiesToAddPerSec;
    int cookiesToAddPerClick;
    ConstraintLayout layout;
    TextView plusOneText;
    float xClick;
    float yClick;
    ImageView waveL;
    ImageView waveR;
    AlphaAnimation animationUpgradeFADEIN;
    AlphaAnimation animationUpgradeFADEOUT;
    AnimationSet animationUpgradeBOUGHT;
    Upgrade grandma;
    Upgrade farm;
    Upgrade mine;
    ImageView shower;
    ImageView shower2;
    ImageView shine;
    AnimationSet animationShine;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics( dm );

        cookiesToAddPerSec = 1;
        cookiesToAddPerClick = 1;
        mainText = findViewById(R.id.id_tvAmountOfCookies);
        totalCookies = new AtomicInteger(0);
        cookie = findViewById(R.id.id_ivCookie);
        layout = findViewById(R.id.id_constraintLayout);
        waveL = findViewById(R.id.bottombarLeft);
        waveR = findViewById(R.id.bottombarRIGHT);
        animationUpgradeFADEIN = new AlphaAnimation(0f, 1f);
        animationUpgradeFADEIN.setDuration(200);
        animationUpgradeFADEIN.setFillAfter(true);
        shine = findViewById(R.id.id_shine);

        animationUpgradeFADEOUT = new AlphaAnimation(1f, 0f);
        animationUpgradeFADEOUT.setDuration(200);
        animationUpgradeFADEOUT.setFillAfter(true);

        ScaleAnimation expand = new ScaleAnimation(1.0f, 1.05f, 1.0f, 1.05f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF ,.5f);
        expand.setFillAfter(true);
        expand.setDuration(100);
        ScaleAnimation contract = new ScaleAnimation(1.05f, 1.0f, 1.05f, 1.0f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF ,.5f);
        contract.setFillAfter(true);
        contract.setDuration(100);
        contract.setStartOffset(100);
        animationUpgradeBOUGHT = new AnimationSet(true);
        animationUpgradeBOUGHT.addAnimation(expand);
        animationUpgradeBOUGHT.addAnimation(contract);

        grandma = new Upgrade(R.drawable.grandmacolor, R.drawable.grandmabouhgt, 5, 1, false, .25f, .0f, .3f);
        farm = new Upgrade(R.drawable.farmcolor, R.drawable.farmbought, 10, 10, false, .5f, .4f, .6f);
        mine = new Upgrade(R.drawable.minecolor, R.drawable.minebought, 25, 100, false, .75f, .7f, 1f);


        PassiveThread thread = new PassiveThread();
        thread.start();
        final ScaleAnimation animationScaleMainIamge = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF ,.5f);
        animationScaleMainIamge.setDuration(75);
        animationShine = new AnimationSet(true);
        RotateAnimation rotateAnimation = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(5000);
        ScaleAnimation scaleAnimationIn = new ScaleAnimation(1f, 1.05f, 1f, 1.05f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimationIn.setDuration(2500);
        ScaleAnimation scaleAnimationDe = new ScaleAnimation(1.05f, 1f, 1.05f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimationDe.setDuration(2500);
        scaleAnimationDe.setStartOffset(2500);
        animationShine.addAnimation(rotateAnimation);
        animationShine.addAnimation(scaleAnimationIn);
        animationShine.addAnimation(scaleAnimationDe);
        animationShine.setFillAfter(true);
        AnimationThread animationThread = new AnimationThread();
        animationThread.start();

        cookie.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    xClick = event.getX();
                    yClick = event.getY();

                }
                return false;
            }
        });
        {
            cookie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addCookie(cookiesToAddPerClick);
                    checkForUpgrades();
                    v.startAnimation(animationScaleMainIamge);
                    plusOneText = new TextView(MainActivity.this);
                    plusOneText.setId(View.generateViewId());
                    plusOneText.setText("+1");
                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                    plusOneText.setLayoutParams(params);
                    plusOneText.setTextSize(36);
                    plusOneText.setTypeface(ResourcesCompat.getFont(MainActivity.this, R.font.kavoon));
                    plusOneText.setTextColor(Color.WHITE);
                    layout.addView(plusOneText);
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(layout);
                    constraintSet.connect(plusOneText.getId(), ConstraintSet.TOP, cookie.getId(), ConstraintSet.TOP);
                    constraintSet.connect(plusOneText.getId(), ConstraintSet.BOTTOM, cookie.getId(), ConstraintSet.BOTTOM);
                    constraintSet.connect(plusOneText.getId(), ConstraintSet.RIGHT, cookie.getId(), ConstraintSet.RIGHT);
                    constraintSet.connect(plusOneText.getId(), ConstraintSet.LEFT, cookie.getId(), ConstraintSet.LEFT);
                    constraintSet.setVerticalBias(plusOneText.getId(), randomPlace(.15, .86));
                    constraintSet.setHorizontalBias(plusOneText.getId(), randomPlace(.15, .86));
                    constraintSet.applyTo(layout);
                    AnimationSet onPlusAnimationSet = new AnimationSet(true);
                    TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, -800);
                    translateAnimation.setFillAfter(true);
                    translateAnimation.setDuration(1000);
                    onPlusAnimationSet.addAnimation(translateAnimation);
                    AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
                    alphaAnimation.setStartOffset(200);
                    alphaAnimation.setFillAfter(true);
                    alphaAnimation.setDuration(1000);
                    onPlusAnimationSet.addAnimation(alphaAnimation);
                    onPlusAnimationSet.setFillAfter(true);
                    plusOneText.startAnimation(onPlusAnimationSet);


                }
            });
        }
    }
    public float randomPlace(double min, double max){
        return (float)(Math.random() * (max - min) + min);
    }
    public void checkForUpgrades(){
        Log.d(TAG, "mine" + mine.getCost());
        if (totalCookies.get() >= mine.getCost()){
            runOnUiThread(new Runnable()
            {
                @Override
                public void run() {
                    mine.putUpgradeOn();

                }
            });
        }
        else if (totalCookies.get() >= farm.getCost()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    farm.putUpgradeOn();
                    mine.takeUpgradeOff();
                }
            });
        }
        else if(totalCookies.get() >= grandma.getCost()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    grandma.putUpgradeOn();
                    farm.takeUpgradeOff();
                    mine.takeUpgradeOff();
                }
            });
        }
        else if (totalCookies.get() < grandma.getCost()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    grandma.takeUpgradeOff();
                    farm.takeUpgradeOff();
                    mine.takeUpgradeOff();
                }
            });
        }
    }
    public void addCookie(int k){
        totalCookies.getAndAdd(k);
        if (totalCookies.get() < 0)
            totalCookies.set(0);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainText.setText(totalCookies.get()+" Cookies");
            }
        });
    }
    public class Upgrade{
        private int imageID;
        private int imageID2;
        private int cost;
        private int cps;
        private boolean canBuy;
        private ImageView imageView;
        private ImageView imageView2;
        private float horiBias;
        private float horiBiasMin;
        private float horiBiasMax;
        public Upgrade(int imageID, int imageID2, int cost, int cps, boolean canBuy, float horiBias, float horiBiasMin, float horiBiasMax){
            this.imageID = imageID;
            this.imageID2 = imageID2;
            this.cost = cost;
            this.cps = cps;
            this.canBuy = canBuy;
            this.horiBias = horiBias;
            this.horiBiasMin = horiBiasMin;
            this.horiBiasMax = horiBiasMax;
            //imageView = new ImageView(MainActivity.this);
            //imageView2 = new ImageView(MainActivity.this);
        }
        public int getCost() {
            return cost;
        }
        public synchronized void putUpgradeOn(){
            if (!canBuy) {
                canBuy = true;
                imageView = new ImageView(MainActivity.this);
                imageView.setId(View.generateViewId());
                imageView.setImageResource(imageID);
                imageView.setClickable(true);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public synchronized void onClick(View v) {
                        if (canBuy) { 
                            buyUpgrade();
                        }
                    }
                });
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                imageView.setLayoutParams(params);
                layout.addView(imageView);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(layout);
                constraintSet.connect(imageView.getId(), ConstraintSet.TOP, cookie.getId(), ConstraintSet.BOTTOM);
                constraintSet.connect(imageView.getId(), ConstraintSet.BOTTOM, waveL.getId(), ConstraintSet.TOP);
                constraintSet.connect(imageView.getId(), ConstraintSet.RIGHT, layout.getId(), ConstraintSet.RIGHT);
                constraintSet.connect(imageView.getId(), ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT);
                constraintSet.setVerticalBias(imageView.getId(), .5f);
                constraintSet.setHorizontalBias(imageView.getId(), horiBias);
                constraintSet.applyTo(layout);
                imageView.startAnimation(animationUpgradeFADEIN);
            }
        }
        public synchronized void takeUpgradeOff(){
            if (canBuy) {
                canBuy = false;
                imageView.startAnimation(animationUpgradeFADEOUT);
                layout.removeView(imageView);
                imageView = null;
            }
        }
        public synchronized void buyUpgrade(){
            removeCookie(cost);
            imageView.startAnimation(animationUpgradeBOUGHT);
            imageView2 = new ImageView(MainActivity.this);
            imageView2.setId(View.generateViewId());
            imageView2.setImageResource(imageID2);
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
            imageView2.setLayoutParams(params);
            layout.addView(imageView2);
            final ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(layout);
            constraintSet.connect(imageView2.getId(), ConstraintSet.TOP, waveL.getId(), ConstraintSet.TOP, 120);
            constraintSet.connect(imageView2.getId(), ConstraintSet.BOTTOM, layout.getId(), ConstraintSet.BOTTOM);
            constraintSet.connect(imageView2.getId(), ConstraintSet.RIGHT, layout.getId(), ConstraintSet.RIGHT);
            constraintSet.connect(imageView2.getId(), ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT);

            constraintSet.setHorizontalBias(imageView2.getId(), randomPlace(horiBiasMin, horiBiasMax));
            constraintSet.setVerticalBias(imageView2.getId(), randomPlace(0, 1));
            constraintSet.applyTo(layout);
            imageView2.startAnimation(animationUpgradeFADEIN);
            cookiesToAddPerSec+=cps;

        }
        void removeCookie(int k){
            totalCookies.getAndAdd(-k);
            if (totalCookies.get() < 0)
                totalCookies.set(0);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mainText.setText(totalCookies.get()+" Cookies");
                }
            });
        }
    }
    public class PassiveThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; i--) {
                addCookie(cookiesToAddPerSec);
                checkForUpgrades();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public class AnimationThread extends Thread{
        @Override
        public void run() {
            for (int i = 1; i < 10; i--){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        shine.startAnimation(animationShine);
                    }
                });
                try {
                    AnimationThread.sleep(animationShine.getDuration());
                } catch (Exception e) {
                }
            }
        }
    }

}
