package com.monyetmabuk.rajawali.tutorials.examples.home;

import javax.microedition.khronos.opengles.GL10;

import rajawali.lights.DirectionalLight;

import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

public class EmptyFragment extends AExampleFragment{

    static final String TAG = "rotateDiag";
    private DirectionalLight mLight;


    @Override
    protected AExampleRenderer createRenderer() {
        return new SkyboxRenderer(getActivity());
    }

    private final class SkyboxRenderer extends AExampleRenderer {

        public SkyboxRenderer(Context context) {
            super(context);
        }

        protected void initScene() {



            mLight = new DirectionalLight(1f, 0.2f, -1.0f); // set the direction
            mLight.setColor(1.0f, 1.0f, 1.0f);
            mLight.setPower(2);

            getCurrentScene().addLight(mLight);
            getCurrentCamera().setFarPlane(3000);

        }



        public void onDrawFrame(GL10 glUnused) {
            super.onDrawFrame(glUnused);






            super.onDrawFrame(glUnused);


        }

        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);

        }

        public void onSurfaceDestroyed() {
            super.onSurfaceDestroyed();

        }
    }

}
