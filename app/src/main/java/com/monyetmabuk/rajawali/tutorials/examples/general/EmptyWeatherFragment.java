package com.monyetmabuk.rajawali.tutorials.examples.general;

import javax.microedition.khronos.opengles.GL10;

import rajawali.Object3D;
import rajawali.animation.Animation;
import rajawali.animation.Animation3D;
import rajawali.animation.AnimationGroup;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.AlphaMapTexture;
import rajawali.materials.textures.AnimatedGIFTexture;
import rajawali.materials.textures.VideoTexture;
import rajawali.math.vector.Vector3;
import rajawali.primitives.Plane;

import android.media.MediaPlayer;


import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

import com.monyetmabuk.rajawali.tutorials.ExamplesApplication;
import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class EmptyWeatherFragment extends AExampleFragment{

    static final String TAG = "rotateDiag";
    private DirectionalLight mLight;

    private Object3D mPlane1;
    private Object3D mPlane2;
    private Object3D mPlane3;
    private Object3D mPlane4;
    private Object3D mPlane5;
    private Object3D mPlane6;
    private Object3D mPlane7;
    private Object3D mPlane8;
    private Object3D mPlane9;

    private Object3D videoPlane8;

    public AnimatedGIFTexture mGifTexture1;
    public AnimatedGIFTexture mGifTexture2;
    public AnimatedGIFTexture mGifTexture3;
    public AnimatedGIFTexture mGifTexture4;
    public AnimatedGIFTexture mGifTexture5;
    public AnimatedGIFTexture mGifTexture6;
    public AnimatedGIFTexture mGifTexture7;
    public AnimatedGIFTexture mGifTexture8;
    public AnimatedGIFTexture mGifTexture9;
    int positionCount = 0;
    boolean readytoupdate=false;

    @Override
    protected AExampleRenderer createRenderer() {
        return new SkyboxRenderer(getActivity());
    }

    private final class SkyboxRenderer extends AExampleRenderer {

        double[] loc1={50f,0, -90f};// 4 clock
        double[] loc2={0f, 0f,-90f};// 3 clock
        double[] loc3={-50f, 0f,-90f}; //2 o'clock
        double[] loc4={-200f, 0f,-90f}; //1 o'clock
        double[] loc5 = {-200f, 0f, -90f}; // l2 o'clock
        double[] loc6 = {-200f,0f,-90f}; // 11 o'clock
        double[] loc7 ={-200f,0f,-90f}; // 10 o'clock
        double[] loc8 = {-200,0f,-90f}; // 9 o'clock
        double[] loc9 = {-200f,0f,-90f}; // 8 o'clock

        MediaPlayer stadiumSounds;
        private MediaPlayer mMediaPlayer8;
        private VideoTexture mVideoTexture8;

        public SkyboxRenderer(Context context) {
            super(context);
        }

        protected void initScene() {

            mLight = new DirectionalLight(1f, 0.2f, -1.0f); // set the direction
            mLight.setColor(1.0f, 1.0f, 1.0f);
            mLight.setPower(2);
            positionCount = 0;

            if(stadiumSounds != null) {
                stadiumSounds.release();
            }

            stadiumSounds= MediaPlayer.create(getContext(), R.raw.stadium);
            stadiumSounds.start();

            getCurrentScene().addLight(mLight);
            getCurrentCamera().setFarPlane(2000);

            try {
                getCurrentScene().setSkybox(R.drawable.posx, R.drawable.negx,
                        R.drawable.posy, R.drawable.negy, R.drawable.posz,
                        R.drawable.snegz2);
            } catch (TextureException e) {
                e.printStackTrace();
            }

            mPlane1=initIcon(mPlane1, loc1 );
            mPlane2=initIcon(mPlane2, loc2 );
            mPlane3=initIcon(mPlane3, loc3 );
            mPlane4=initIcon(mPlane4, loc4 );
            mPlane5=initIcon(mPlane5, loc5 );
            mPlane6=initIcon(mPlane6, loc6);
            mPlane7=initIcon(mPlane7, loc7);
            mPlane8=initIcon(mPlane8, loc8);
            mPlane9=initIcon(mPlane9, loc9);

            try {
                mGifTexture9 = new AnimatedGIFTexture("nine", R.drawable.nine);
                mPlane9.getMaterial().addTexture(mGifTexture9);
                mPlane9.getMaterial().setColorInfluence(0);
                mGifTexture9.rewind();
                mPlane9.setScaleX((float) mGifTexture9.getWidth() / (float) mGifTexture9.getHeight());
            } catch (TextureException e) {
                e.printStackTrace();
            }

            try {
                mGifTexture8 = new AnimatedGIFTexture("eigth", R.drawable.eight);
                mPlane8.getMaterial().addTexture(mGifTexture8);
                mPlane8.getMaterial().setColorInfluence(0);
                mGifTexture8.rewind();
                mPlane8.setScaleX((float) mGifTexture8.getWidth() / (float) mGifTexture8.getHeight());
            } catch (TextureException e) {
                e.printStackTrace();
            }

            try {
                mGifTexture7 = new AnimatedGIFTexture("seven", R.drawable.seven);
                mPlane7.getMaterial().addTexture(mGifTexture7);
                mPlane7.getMaterial().setColorInfluence(0);
                mGifTexture7.rewind();
                mPlane7.setScaleX((float) mGifTexture7.getWidth() / (float) mGifTexture7.getHeight());
            } catch (TextureException e) {
                e.printStackTrace();
            }

            try {
                mGifTexture6 = new AnimatedGIFTexture("six", R.drawable.six);
                mPlane6.getMaterial().addTexture(mGifTexture6);
                mPlane6.getMaterial().setColorInfluence(0);
                mGifTexture6.rewind();
                mPlane6.setScaleX((float) mGifTexture6.getWidth() / (float) mGifTexture6.getHeight());
            } catch (TextureException e) {
                e.printStackTrace();
            }

            try {
                mGifTexture5 = new AnimatedGIFTexture("five", R.drawable.five);
                mPlane5.getMaterial().addTexture(mGifTexture5);
                mPlane5.getMaterial().setColorInfluence(0);
                mGifTexture5.rewind();
                mPlane5.setScaleX((float) mGifTexture5.getWidth() / (float) mGifTexture5.getHeight());
            } catch (TextureException e) {
                e.printStackTrace();
            }

            try {
                mGifTexture4 = new AnimatedGIFTexture("four", R.drawable.four);
                mPlane4.getMaterial().addTexture(mGifTexture4);
                mPlane4.getMaterial().setColorInfluence(0);
                mGifTexture4.rewind();
                mPlane4.setScaleX((float) mGifTexture4.getWidth() / (float) mGifTexture5.getHeight());
            } catch (TextureException e) {
                e.printStackTrace();
            }

            try {
                mGifTexture3 = new AnimatedGIFTexture("three", R.drawable.three);
                mPlane3.getMaterial().addTexture(mGifTexture3);
                mPlane3.getMaterial().setColorInfluence(0);
                mGifTexture3.rewind();
                mPlane3.setScaleX((float) mGifTexture3.getWidth() / (float) mGifTexture3.getHeight());
            } catch (TextureException e) {
                e.printStackTrace();
            }

            try {
                mGifTexture2 = new AnimatedGIFTexture("two", R.drawable.two);
                mPlane2.getMaterial().addTexture(mGifTexture2);
                mPlane2.getMaterial().setColorInfluence(0);
                mGifTexture2.rewind();
                mPlane2.setScaleX((float) mGifTexture2.getWidth() / (float) mGifTexture2.getHeight());
            } catch (TextureException e) {
                e.printStackTrace();
            }

            try {
                mGifTexture1 = new AnimatedGIFTexture("one", R.drawable.one);
                mPlane1.getMaterial().addTexture(mGifTexture1);
                mPlane1.getMaterial().setColorInfluence(0);
                mGifTexture1.rewind();
                mPlane1.setScaleX((float) mGifTexture1.getWidth() / (float) mGifTexture1.getHeight());
            } catch (TextureException e) {
                e.printStackTrace();
            }

            mMediaPlayer8 = MediaPlayer.create(getContext(),R.raw.mthree);
            mMediaPlayer8.setLooping(true);

            Material video_material = new Material();
            videoPlane8 = new Plane(160, 90, 6, 6, Vector3.Axis.Z);
            videoPlane8.setMaterial(video_material);
            videoPlane8.setRotY(-180);
            getCurrentScene().addChild(videoPlane8);
            videoPlane8.setPosition(0, -100, -3000);
            videoPlane8.setDoubleSided(true);

            mVideoTexture8 = new VideoTexture("baseball", mMediaPlayer8);
            video_material.setColorInfluence(0);
            try {
                video_material.addTexture(mVideoTexture8);
                //videoPlane8.setScaleX((float) mVideoTexture8.getWidth() / (float) mVideoTexture8.getHeight());
            } catch (TextureException e) {
                e.printStackTrace();
            }
        }

        public Object3D initIcon(Object3D obj, double[] loc){

            Material material = new Material();
            obj = new Plane(22, 22, 6, 6, Vector3.Axis.Z);
            obj.setMaterial(material);
            obj.setRotY(-180);
            getCurrentScene().addChild(obj);

            obj.setPosition(loc[0],loc[1],loc[2]);
            obj.isTransparent();
            obj.setDoubleSided(true);
            return obj;
        }

        public Object3D initGif(Object3D obj, AnimatedGIFTexture a, String name, int resourceId  ){
            try {
                a = new AnimatedGIFTexture(name, resourceId);
                obj.getMaterial().addTexture(a);
                obj.getMaterial().setColorInfluence(0);
                a.rewind();
                obj.setScaleX((float) a.getWidth() / (float) a.getHeight());
            } catch (TextureException e) {
                e.printStackTrace();
            }
            return obj;
        }

        public void animation(Object3D a, double[] before, double[] after){
            Animation3D anim = new TranslateAnimation3D(new Vector3(before[0], before[1], before[2]), new Vector3(after[0], after[1], after[2]));
            anim.setDurationMilliseconds(1000);
            anim.setTransformable3D(a);
            anim.setInterpolator(new LinearInterpolator());
            anim.setRepeatCount(1);
            getCurrentScene().registerAnimation(anim);
            //a.setVisible(true);
            anim.play();
        }

        public void playvideo() {

            double[] offscreenleft = {200,0,-90};
            double[] offscreenright = {-200,0,-90};
            double[] onscreen1 = {50,0,-90};
            double[] onscreen2 = {0,0,-90};
            double[] onscreen3 = {-50,0,-90};
            final AnimationGroup animGroup = new AnimationGroup();
            animGroup.setRepeatMode(Animation.RepeatMode.NONE);
            animGroup.setRepeatCount(1);

            Animation3D anim = new TranslateAnimation3D(new Vector3(onscreen1[0], onscreen1[1], onscreen1[2]), new Vector3(offscreenleft[0], offscreenleft[1], offscreenleft[2]));
            anim.setDurationMilliseconds(1000);
            anim.setTransformable3D(mPlane7);
            anim.setInterpolator(new LinearInterpolator());
            anim.setRepeatCount(1);
            getCurrentScene().registerAnimation(anim);
            animGroup.addAnimation(anim);

            anim = new TranslateAnimation3D(new Vector3(onscreen2[0], onscreen2[1], onscreen2[2]), new Vector3(offscreenleft[0], offscreenleft[1], offscreenleft[2]));
            anim.setDurationMilliseconds(1000);
            anim.setTransformable3D(mPlane8);
            anim.setInterpolator(new LinearInterpolator());
            anim.setRepeatCount(1);
            getCurrentScene().registerAnimation(anim);
            animGroup.addAnimation(anim);

            anim = new TranslateAnimation3D(new Vector3(onscreen3[0], onscreen3[1], onscreen3[2]), new Vector3(offscreenleft[0], offscreenleft[1], offscreenleft[2]));
            anim.setDurationMilliseconds(1000);
            anim.setTransformable3D(mPlane9);
            anim.setInterpolator(new LinearInterpolator());
            anim.setRepeatCount(1);
            getCurrentScene().registerAnimation(anim);
            animGroup.addAnimation(anim);

            anim = new TranslateAnimation3D(new Vector3(0, -100, -3000), new Vector3(0, 0, -110));
            anim.setDurationMilliseconds(2000);
            anim.setTransformable3D(videoPlane8);
            anim.setInterpolator(new LinearInterpolator());
            anim.setRepeatCount(1);
            getCurrentScene().registerAnimation(anim);
            animGroup.addAnimation(anim);
            //a.setVisible(true);
            getCurrentScene().registerAnimation(animGroup);
            animGroup.play();

            mMediaPlayer8.start();
            readytoupdate = true;
        }

        public void onDrawFrame(GL10 glUnused) {
            super.onDrawFrame(glUnused);


            //Log.d(TAG, "azimuth=" + result[0]);

            if(result[0]> -70 && result[0] < 70) {
                getCurrentCamera().setRotY(0);
                getCurrentCamera().setRotX(0);
            }else if (result[0]> 70 || result[0] < -70){
                getCurrentCamera().setRotY(0);
                getCurrentCamera().setRotX(0);
            }

            updateTexture(mGifTexture1);
            updateTexture(mGifTexture2);
            updateTexture(mGifTexture3);
            updateTexture(mGifTexture4);
            updateTexture(mGifTexture5);
            updateTexture(mGifTexture6);
            updateTexture(mGifTexture7);
            updateTexture(mGifTexture8);
            updateTexture(mGifTexture9);

            double[] offscreenleft = {200,0,-90};
            double[] offscreenright = {-200,0,-90};
            double[] onscreen1 = {50,0,-90};
            double[] onscreen2 = {0,0,-90};
            double[] onscreen3 = {-50,0,-90};

            if(ExamplesApplication.stepbackward) {
                if(positionCount == 2) {
                    animation(mPlane7, onscreen1, offscreenleft);
                    animation(mPlane8, onscreen2, offscreenleft);
                    animation(mPlane9, onscreen3, offscreenleft);

                    animation(mPlane4, offscreenright, onscreen1);
                    animation(mPlane5, offscreenright, onscreen2);
                    animation(mPlane6, offscreenright, onscreen3);
                    ExamplesApplication.stepforward = false;
                    positionCount = 1;
                } else if (positionCount == 1) {
                    ExamplesApplication.stepforward = false;
                    animation(mPlane4, onscreen1, offscreenleft);
                    animation(mPlane5, onscreen2, offscreenleft);
                    animation(mPlane6, onscreen3, offscreenleft);

                    animation(mPlane1, offscreenright, onscreen1);
                    animation(mPlane2, offscreenright, onscreen2);
                    animation(mPlane3, offscreenright, onscreen3);
                    positionCount = 0;
                }
                ExamplesApplication.stepbackward = false;
            }

            if(ExamplesApplication.stepforward) {
                if(positionCount == 0) {
                    animation(mPlane1, onscreen1, offscreenright);
                    animation(mPlane2, onscreen2, offscreenright);
                    animation(mPlane3, onscreen3, offscreenright);

                    animation(mPlane4, offscreenleft, onscreen1);
                    animation(mPlane5, offscreenleft, onscreen2);
                    animation(mPlane6, offscreenleft, onscreen3);
                    ExamplesApplication.stepforward = false;
                    positionCount = 1;
                } else if (positionCount == 1) {
                    ExamplesApplication.stepforward = false;
                    animation(mPlane4, onscreen1, offscreenright);
                    animation(mPlane5, onscreen2, offscreenright);
                    animation(mPlane6, onscreen3, offscreenright);

                    animation(mPlane7, offscreenleft, onscreen1);
                    animation(mPlane8, offscreenleft, onscreen2);
                    animation(mPlane9, offscreenleft, onscreen3);

                    positionCount = 2;
                }
            }

            Log.d(TAG, "playvid = " + ExamplesApplication.playvid + "positionCount = " + positionCount + "is looking at = " + islookingAt(-50, -20));
            if (positionCount == 2){
                if(islookingAt(-50,-20)){
                    mPlane7.setZ(-90f);
                    mPlane8.setZ(-90f);
                    mPlane9.setZ(-75f);
                    if(ExamplesApplication.playvid){
                        playvideo();
                        ExamplesApplication.playvid=false;
                    }
                }else if(!islookingAt(-50, 120)){
                    if(ExamplesApplication.playvid){
                        ExamplesApplication.playvid=false;
                    }

                }
            }

            if(readytoupdate){
                mVideoTexture8.update();
            }

      /*      if(islookingAt(-35, -50)) {
                ExamplesApplication.clicked = true;
            }else{
                ExamplesApplication.clicked = false;
            }*/
        }

        public void updateTexture(AnimatedGIFTexture a){
            if (a != null) {
                try {
                    a.update();
                } catch (TextureException e) {
                    e.printStackTrace();
                }
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

                    //animation(p, before_loc, loc);
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


        public boolean islookingAt(double left, double right) {

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
            positionCount = 0;
            if (!visible) {
                if ( stadiumSounds != null) {
                    mMediaPlayer8.start();
                    stadiumSounds.pause();
                } else if ( stadiumSounds == null) {
                    stadiumSounds.start();
                    mMediaPlayer8.start();
                }
            }
        }


        public void onSurfaceDestroyed() {
            super.onSurfaceDestroyed();
            if(null!= stadiumSounds){
                stadiumSounds.stop();
                stadiumSounds.release();
                mMediaPlayer8.stop();
                mMediaPlayer8.release();
            }
        }


    }

}
