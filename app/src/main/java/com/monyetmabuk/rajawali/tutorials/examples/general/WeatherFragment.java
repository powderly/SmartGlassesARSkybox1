package com.monyetmabuk.rajawali.tutorials.examples.general;

import javax.microedition.khronos.opengles.GL10;

import rajawali.Object3D;
import rajawali.animation.Animation;
import rajawali.animation.Animation3D;
import rajawali.animation.EllipticalOrbitAnimation3D;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.AlphaMapTexture;
import rajawali.materials.textures.AnimatedGIFTexture;
import rajawali.materials.textures.Texture;
import rajawali.materials.textures.VideoTexture;
import rajawali.math.vector.Vector3;
import rajawali.primitives.Plane;
import rajawali.primitives.Sphere;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.monyetmabuk.rajawali.tutorials.ExamplesApplication;
import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class WeatherFragment extends AExampleFragment {

    static final String TAG = "rotateDiag";
    private DirectionalLight mLight;
    MediaPlayer mPlayer2;


	@Override
	protected AExampleRenderer createRenderer() {
		return new SkyboxRenderer(getActivity());
	}

	private final class SkyboxRenderer extends AExampleRenderer {

        public SkyboxRenderer(Context context) {
			super(context);
		}

		protected void initScene() {


            int resID = R.raw.soundstockholm;

            mPlayer2= MediaPlayer.create(getContext(), resID);
            mPlayer2.start();

            mLight = new DirectionalLight(1f, 0.2f, -1.0f); // set the direction
            mLight.setColor(1.0f, 1.0f, 1.0f);
            mLight.setPower(2);

            getCurrentScene().addLight(mLight);
            getCurrentCamera().setFarPlane(3000);

            showlogo();

            try {
                getCurrentScene().setSkybox(R.drawable.posx1, R.drawable.negx1,
                        R.drawable.posy1, R.drawable.negy1, R.drawable.posz1,
                        R.drawable.negz1);
            } catch (TextureException e) {
                e.printStackTrace();
            }

        }

        public Object3D initIcon(Object3D obj, double[] loc, String name, int resourceId  ){
            try {
                Material material = new Material();
                material.addTexture(new AlphaMapTexture(name, resourceId));
                material.setColor(0xFFFFFFFF);
                obj = new Plane(24, 24, 6, 6, Vector3.Axis.Z);
                obj.setMaterial(material);
                getCurrentScene().addChild(obj);
            } catch (TextureException e) {
                e.printStackTrace();
            }

            obj.setPosition(loc[0],loc[1],loc[2]);
            obj.isTransparent();
            obj.setDoubleSided(true);
            return obj;
        }

		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);



            if(result[0]> -70 && result[0] < 70) {
                getCurrentCamera().setRotY(result[0]);
                getCurrentCamera().setRotX(result[1]);
            }else if (result[0]> 70 || result[0] < -70){
               getCurrentCamera().setRotY(result[0]);
               getCurrentCamera().setRotX(-result[1]);
             }


            super.onDrawFrame(glUnused);


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
            mPlayer2.stop();
            mPlayer2.release();
        }
	}

}
