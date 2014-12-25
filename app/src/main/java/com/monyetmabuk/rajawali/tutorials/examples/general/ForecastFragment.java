package com.monyetmabuk.rajawali.tutorials.examples.general;

import javax.microedition.khronos.opengles.GL10;

import rajawali.Object3D;
import rajawali.animation.Animation3D;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.AlphaMapTexture;
import rajawali.math.vector.Vector3;
import rajawali.primitives.Plane;

import android.media.AudioManager;
import android.media.MediaPlayer;


import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.animation.BounceInterpolator;

import com.monyetmabuk.rajawali.tutorials.ExamplesApplication;
import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class ForecastFragment extends AExampleFragment{

    static final String TAG = "rotateDiag";
    private DirectionalLight mLight;
    private Object3D mSphere;
    private Object3D mPlane0;
    private Object3D mPlane1;
    private Object3D mPlane2;
    private Object3D mPlane3;
    private Object3D mPlane4;
    private Object3D mPlane5;
    private Object3D mPlane6;
    private Object3D mPlane7;
    private Object3D mPlane8;
    private Object3D mPlane9;
    private Object3D mPlane10;
    private boolean dropped0 = false;
    private boolean dropped1 = false;
    private boolean dropped2 = false;
    private boolean dropped3 = false;
    private boolean dropped4 = false;
    private boolean dropped5 = false;
    private boolean dropped6 = false;
    private boolean dropped7 = false;
    private boolean dropped8 = false;
    private boolean dropped9 = false;
    private boolean dropped10 = false;


    @Override
    protected AExampleRenderer createRenderer() {
        return new SkyboxRenderer(getActivity());
    }

    private final class SkyboxRenderer extends AExampleRenderer {

        double[] right_hard_stop ={90f,21f, 35f};// 5 clock
        double[] loc0={90f,21f, 35f};// 5 clock
        double[] loc1={100f,-7f, 0f};// 4 clock
        double[] loc2={90f, 23f,-35f};// 3 clock
        double[] loc3={70f,-7f,-65f}; //2 o'clock
        double[] loc4={40f,-24f,-90}; //1 o'clock
        double[] loc5 = {0f, 5f, -100f}; // l2 o'clock
        double[] loc6 = {-35f,-0f,-90f}; // 11 o'clock
        double[] loc7 ={-70f,24f,-65f}; // 10 o'clock
        double[] loc8 = {-90,-24f,-35f}; // 9 o'clock
        double[] loc9 = {-100f,-10f,0f}; // 8 o'clock
        double[] loc10 = {-90f,0f,35f}; // 7 o'clock
        double[] left_hard_stop ={90f,21f, 35f};// 5 clock
        MediaPlayer mPlayer2;


        public SkyboxRenderer(Context context) {
            super(context);
        }

        protected void initScene() {



            mLight = new DirectionalLight(1f, 0.2f, -1.0f); // set the direction
            mLight.setColor(1.0f, 1.0f, 1.0f);
            mLight.setPower(2);


            if (mPlayer2 != null) {
                mPlayer2.release();
            }

            int resID = R.raw.soundnight;

            mPlayer2= MediaPlayer.create(getContext(), resID);
            mPlayer2.start();

            getCurrentScene().addLight(mLight);
            getCurrentCamera().setFarPlane(2000);

            try {
                getCurrentScene().setSkybox(R.drawable.posx, R.drawable.negx,
                        R.drawable.posy, R.drawable.negy, R.drawable.posz,
                        R.drawable.negz);
            } catch (TextureException e) {
                e.printStackTrace();
            }


            mPlane0=initIcon(mPlane0, loc0, "nuuk", R.drawable.nuuk);
            mPlane1=initIcon(mPlane1, loc1, "brasillia", R.drawable.brasilia);
            mPlane2=initIcon(mPlane2, loc2, "banff", R.drawable.banff);
            mPlane3=initIcon(mPlane3, loc3, "losangeles", R.drawable.la);
            mPlane4=initIcon(mPlane4, loc4, "melbourne", R.drawable.melbourne);
            mPlane5=initIcon(mPlane5, loc5, "seoul", R.drawable.seoul);
            mPlane6=initIcon(mPlane6, loc6, "bangkok", R.drawable.bangkok);
            mPlane7=initIcon(mPlane7, loc7, "stockholm", R.drawable.stockholm);
            mPlane8=initIcon(mPlane8, loc8, "capetown", R.drawable.capetown);
            mPlane9=initIcon(mPlane9, loc9, "amsterdam", R.drawable.amsterdam);
            mPlane10=initIcon(mPlane10, loc10, "london", R.drawable.london);

            mPlane0.setRotY(-70);
            mPlane1.setRotY(-90);
            mPlane2.setRotY(-110);
            mPlane3.setRotY(-150);
            mPlane4.setRotY(-170);
            mPlane5.setRotY(-180);
            mPlane6.setRotY(-190);
            mPlane7.setRotY(-210);
            mPlane8.setRotY(-250);
            mPlane9.setRotY(-270);
            mPlane10.setRotY(-290);

/*            final EllipticalOrbitAnimation3D anim = new EllipticalOrbitAnimation3D(new Vector3(0, 0, 3), new Vector3(
                    0.5, 0.5, 3), 0, 359);
            anim.setDurationMilliseconds(12000);
            anim.setRepeatMode(Animation.RepeatMode.INFINITE);
            anim.setTransformable3D(getCurrentCamera());
            getCurrentScene().registerAnimation(anim);
            anim.play();*/
        }

        public Object3D initIcon(Object3D obj, double[] loc, String name, int resourceId  ){
            try {
                Material material = new Material();
                material.addTexture(new AlphaMapTexture(name, resourceId));
                material.setColor(0xffcccccc);
                obj = new Plane(22, 22, 6, 6, Vector3.Axis.Z);
                obj.setMaterial(material);
                getCurrentScene().addChild(obj);
            } catch (TextureException e) {
                e.printStackTrace();
            }

            obj.setPosition(loc[0],loc[1],loc[2]);
            obj.isTransparent();
            obj.setDoubleSided(true);
            obj.setVisible(false);
            return obj;
        }

        public void animation(Object3D o, double[] before, double[] after){
            Animation3D anim = new TranslateAnimation3D(new Vector3(before[0], before[1], before[2]), new Vector3(after[0], after[1], after[2]));
            anim.setDurationMilliseconds(1500);
            anim.setTransformable3D(o);
            anim.setInterpolator(new BounceInterpolator());
            anim.setRepeatCount(1);
            getCurrentScene().registerAnimation(anim);
            o.setVisible(true);
            anim.play();
        }

        public void onDrawFrame(GL10 glUnused) {
            super.onDrawFrame(glUnused);

            Log.d(TAG, "azimuth=" + result[0]);

            if(result[0]> -70 && result[0] < 70) {
                getCurrentCamera().setRotY(result[0]);
                getCurrentCamera().setRotX(0);
            }else if (result[0]> 70 || result[0] < -70){
                getCurrentCamera().setRotY(result[0]);
                getCurrentCamera().setRotX(0);
            }

            double[] zoom0={90f,21f, 35f};// 5 clock
            double[] zoom1={99f,-7f, 0f};// 4 clock
            double[] zoom2={90f,23f, -34f};// 3 clock
            double[] zoom3={70f,-7f,-64}; //2 o'clock
            double[] zoom4={40f,-24f,-89}; //1 o'clock
            double[] zoom5 = {0f, 5f, -99f}; // l2 o'clock
            double[] zoom6 = {-35f,-0f,-89f}; // 11 o'clock
            double[] zoom7 ={-70f,24f,-64f}; // 10 o'clock
            double[] zoom8 = {-90,-24f,-34f}; // 9 o'clock
            double[] zoom9 = {-99f,-10f,0f}; // 8 o'clock
            double[] zoom10 = {-90f,0f,35f}; // 7 o'clock

            //lookingAt(mPlane0, loc0, loc4, loc6, -90);
            //lookingAt(mPlane0, loc0, 101, 81, zoom0, dropped0);
            dropped1=lookingAt(mPlane1, loc1, 101, 81, zoom1, dropped1); //
            dropped2=lookingAt(mPlane2, loc2, 80, 55, zoom2, dropped2); //
            dropped3=lookingAt(mPlane3, loc3, 50, 35, zoom3, dropped3); //
            dropped4= lookingAt(mPlane4, loc4, 34, 16, zoom4, dropped4); //
            dropped5=lookingAt(mPlane5, loc5, 15, -15, zoom5, dropped5); //
            dropped6=lookingAt(mPlane6, loc6, -16, -34, zoom6, dropped6); //
            dropped7=lookingAt(mPlane7, loc7, -35, -50, zoom7, dropped7); //
            dropped8= lookingAt(mPlane8, loc8, -60, -75, zoom8, dropped8); //
            dropped9= lookingAt(mPlane9, loc9, -80, -101, zoom9, dropped9); //
            //lookingAt(mPlane9, loc1-, -80, -101, zoom10, dropped10); //
            //lookingAt(mPlane5, loc5, loc9, loc6, -90);

            if(islookingAt(-35, -50)) {
                ExamplesApplication.clicked = true;
            }else{
                ExamplesApplication.clicked = false;
            }
        }

        public boolean lookingAt(Object3D p, double[] loc, double right, double left, double[] s, boolean dropped) {

            //double[] loc4={40f,-24f,-90}; //1 o'clock
            //double[] loc5 = {0f, 5f, -100f}; // l2 o'clock
            //double[] loc6 = {-40f,-5f,-90f}; // 11 o'clock

            double zoom_factor = 15;
            //left = -20                                                          right = 20
            if(result[0]>left && result[0]<right){

                if (!dropped) {
                    double[] before_loc = {0, 0, 0};

                    before_loc[0] = loc[0];
                    before_loc[1] = 40f;
                    before_loc[2] = loc[2];

                    animation(p, before_loc, loc);
                    playSuccessSound();
                    dropped = true;
                }

                //p.setScaleZ(1.5);
                p.setPosition(s[0], s[1], s[2]);
                p.setColor(0xFFFFFFFF);
            }else{
                p.setPosition(loc[0],loc[1], loc[2]);
                p.setColor(0xffcccccc);
            }
            return dropped;
        }


        public boolean islookingAt(double right, double left) {

            boolean status = false;


            if(result[0]>left && result[0]<right){
                status=true;
            }else{
               status=false;
            }
            return status;
        }


        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (!visible) {
                if (mPlayer2 != null) {
                    mPlayer2.pause();
                } else if (mPlayer2 == null) {
                    mPlayer2.start();
                }
            }
        }


        public void onSurfaceDestroyed() {
            super.onSurfaceDestroyed();
            if(null!=mPlayer2){
                mPlayer2.stop();
                mPlayer2.release();
            }
        }


    }

    private void playSuccessSound() {
        // Play sound to acknowledge action
        AudioManager audio = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        audio.playSoundEffect(AudioManager.FX_KEY_CLICK, 3);
    }


}
