package com.example.easyeat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
{
    TextView btnStartProgress;
    private Handler progressBarHandler = new Handler();
    int progressBarStatus=0;
    long fileSize=0;
    ProgressBar progressBar;
    @SuppressLint("NewApi")
    ImageView mimageView,btn_clp,d1,d2,btn_2,img_3;
    LinearLayout ll,ll_2;
    CardView cv;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        getSupportActionBar().hide();
         mimageView=(ImageView) findViewById(R.id.image_profile);
         cv=findViewById(R.id.card_view);
         btn_clp=findViewById(R.id.collapse_btn);
         d1=findViewById(R.id.dish2);
         d2=findViewById(R.id.dish_1);
         ll=findViewById(R.id.linear_layout1);
         btn_2=findViewById(R.id.collapse_btn2);
         ll_2=findViewById(R.id.linear_layout2);

         // 1> Rounded Corner of Image:......

        Bitmap mbitmap=((BitmapDrawable) getResources().getDrawable(R.drawable.ravilpr)).getBitmap();
        setimage(mbitmap);
        mimageView=(ImageView) findViewById(R.id.img2);
        mbitmap=((BitmapDrawable) getResources().getDrawable(R.drawable.restaurant_img2)).getBitmap();
        setimage(mbitmap);

        //collapse button rotate 360-----

        btn_clp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (btn_clp.getTag()!=null && btn_clp.getTag().toString().equals("180"))
                {
                    ObjectAnimator anim = ObjectAnimator.ofFloat(view, "rotation",180, 0);
                    anim.setDuration(4);
                    anim.start();
                    btn_clp.setTag("");
                    ll.setVisibility(View.GONE);
                }
                else
                    {
                    ObjectAnimator anim = ObjectAnimator.ofFloat(view, "rotation",0,  180);
                    anim.setDuration(4);
                    anim.start();
                    btn_clp.setTag(180+"");
                    ll.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (btn_2.getTag()!=null && btn_2.getTag().toString().equals("180")){
                    ObjectAnimator anim = ObjectAnimator.ofFloat(view, "rotation",180, 0);
                    anim.setDuration(4);
                    anim.start();
                    btn_2.setTag("");
                    ll_2.setVisibility(View.GONE);
                }  else
                    {
                    ObjectAnimator anim = ObjectAnimator.ofFloat(view, "rotation",0,  180);
                    anim.setDuration(4);
                    anim.start();
                    btn_2.setTag(180+"");

                        ll_2.setVisibility(View.VISIBLE);
                }
            }
        });

        // dialog box implemet with progress bar and threading in it........

        btnStartProgress = findViewById(R.id.redeem_btn);
        btnStartProgress.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                long starttime=System.currentTimeMillis();
                Log.d("current time->",Long.toString(starttime));
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.custom_progress_bar, viewGroup, false);
                builder.setView(dialogView);
                  boolean b=false;
                final AlertDialog alertDialog = builder.create();
                //for second dialog box.....
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                View dialogView1 = LayoutInflater.from(v.getContext()).inflate(R.layout.alert_dialog, viewGroup, false);
                builder1.setView(dialogView1);
                final AlertDialog alertDialog2 = builder1.create();
                //.........................
                alertDialog.show();
                progressBar=alertDialog.findViewById(R.id.mf_progress_bar);
                progressBarStatus=0;
                fileSize=0;
             final Thread t1=  new Thread(new Runnable()
                {
                    public void run()
                    {
                        while (progressBarStatus < 100) {
                            progressBarStatus = doOperation();
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            progressBarHandler.post(new Runnable() {
                                public void run() {
                                    progressBar.setProgress(progressBarStatus);
                                }
                            });
                        }
                        if (progressBarStatus >= 100) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            alertDialog.dismiss();
                        }
                    }

                });
               t1.start();
               Thread t2=new Thread(new Runnable()
               {
                   @Override
                   public void run()
                   {
                       try {
                           t1.join();
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                       try {
                           Thread.sleep(600);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                       new Handler(Looper.getMainLooper()).post(new Runnable() {
                           @Override
                           public void run()
                           {
                               alertDialog2.show();
                           }
                       });
                       try {
                           Thread.sleep(4000);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                       alertDialog2.dismiss();
                   }
               });
               t2.start();
                long endtime=System.currentTimeMillis();
                long val=endtime-starttime;
                Log.d("time difference->",Long.toString(val));
            }
        });
    }
    public int doOperation() {
        while (fileSize <= 10000) {
            fileSize++;
            if (fileSize == 1000) {
                return 10;
            } else if (fileSize == 2000) {
                return 20;
            } else if (fileSize == 3000) {
                return 30;
            } else if (fileSize == 4000) {
                return 40;
            }
        }
        return 100;
    }
//----image corner rounded shape-----

    public void setimage(Bitmap mbitmap)
    {
        Bitmap imageRounded=Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas=new Canvas(imageRounded);
        Paint mpaint=new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 25, 25, mpaint); // Round Image Corner 100 100 100 100
        mimageView.setImageBitmap(imageRounded);
        img_3=findViewById(R.id.dish3);
        d1.setImageBitmap(imageRounded);
        d2.setImageBitmap(imageRounded);
        img_3.setImageBitmap(imageRounded);

    }
}

