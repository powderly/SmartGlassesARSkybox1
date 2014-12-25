package com.monyetmabuk.rajawali.tutorials.examples;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.RajawaliFragment;
import rajawali.math.Matrix4;
import rajawali.renderer.RajawaliRenderer;
import rajawali.util.RajLog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.views.GithubLogoView;

import java.text.DecimalFormat;

public abstract class AExampleFragment extends RajawaliFragment implements
		OnClickListener, SensorEventListener {

	public static final String BUNDLE_EXAMPLE_URL = "BUNDLE_EXAMPLE_URL";

	protected RajawaliRenderer mRenderer;
	protected ProgressBar mProgressBarLoader;
	protected GithubLogoView mImageViewExampleLink;
	protected String mExampleUrl;

    private SensorManager mSensorManager;
    static final String TAG = "RotationSensor";
    protected int mLastAccuracy;
    private Sensor mRotationVectorSensor;
    public float rx,ry,rz;
    public float[] result = new float[3];
    //public final float[] mRotationMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];
    protected static final int SENSOR_RATE = 5 * 1000;
    public float[] orientation = new float[3];

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        mSensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
       // mRotationVectorSensor = mSensorManager.getDefaultSensor(
       //         Sensor.TYPE_ORIENTATION);

        mRotationVectorSensor = mSensorManager.getDefaultSensor(
                Sensor.TYPE_ROTATION_VECTOR);

       // mRotationMatrix[ 0] = 1;
        //mRotationMatrix[ 4] = 1;
        //mRotationMatrix[ 8] = 1;
        //mRotationMatrix[12] = 1;

        mSensorManager.registerListener(this, mRotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);



        final Bundle bundle = getArguments();
		if (bundle == null || !bundle.containsKey(BUNDLE_EXAMPLE_URL)) {
			throw new IllegalArgumentException(getClass().getSimpleName()
					+ " requires " + BUNDLE_EXAMPLE_URL
					+ " argument at runtime!");
		}

		mExampleUrl = bundle.getString(BUNDLE_EXAMPLE_URL);

		if (isTransparentSurfaceView())
			setGLBackgroundTransparent(true);

		mRenderer = createRenderer();
		if (mRenderer == null)
			mRenderer = new NullRenderer(getActivity());

		mRenderer.setSurfaceView(mSurfaceView);
		setRenderer(mRenderer);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mLayout = (FrameLayout) inflater.inflate(R.layout.rajawali_fragment,
				container, false);

		mLayout.addView(mSurfaceView);

        mLayout.findViewById(R.id.relative_layout_loader_container).bringToFront();


		// Create the loader
		mProgressBarLoader = (ProgressBar) mLayout
				.findViewById(R.id.progress_bar_loader);
		mProgressBarLoader.setVisibility(View.GONE);

		// Set the example link
		mImageViewExampleLink = (GithubLogoView) mLayout
			.findViewById(R.id.image_view_example_link);
		mImageViewExampleLink.setVisibility(View.INVISIBLE);

		return mLayout;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image_view_example_link:
			if (mImageViewExampleLink == null)
				throw new IllegalStateException("Example link is null!");

			final Intent intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse(mExampleUrl));
			startActivity(intent);
			break;
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();

		if (mLayout != null)
			mLayout.removeView(mSurfaceView);

        mSensorManager.unregisterListener(this);
	}

	@Override
	public void onDestroy() {
		try {
			super.onDestroy();
		} catch (Exception e) {
		}
		mRenderer.onSurfaceDestroyed();

	}

    public void onSensorChanged(SensorEvent event) {
        // we received a sensor event. it is a good practice to check
        // that we received the proper event
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            // convert the rotation-vector to a 4x4 matrix. the matrix
            // is interpreted by Open GL as the inverse of the
            // rotation-vector, which is what we want.


            //float w = mGLSurfaceView.getMeasuredWidth(),
            //        h = mGLSurfaceView.getMeasuredHeight();

            SensorManager.getRotationMatrixFromVector(mRotationMatrix , event.values);
            SensorManager.remapCoordinateSystem(mRotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, mRotationMatrix);
            SensorManager.getOrientation(mRotationMatrix, orientation);

         /*   result[0] =event.values[0];
            result[1] =event.values[1];
            result[2] =event.values[2];*/

            ry = orientation[0];
            rx = orientation[1];
            rz = orientation[2];


            DecimalFormat df = new DecimalFormat("0");

           result[0] = Float.parseFloat(df.format(Math.toDegrees(ry))); //Yaw

           result[1] = Float.parseFloat(df.format(Math.toDegrees(rx))); //Pitch

           result[2] = Float.parseFloat(df.format(Math.toDegrees(rz))); //Roll

             //Log.d(TAG, "Azimuth =" + result[0]);
            //Log.d(TAG, "Pitch =" + result[1]);
            //Log.d(TAG, "Roll =" + result[2]);

            //DecimalFormat df = new DecimalFormat("0.00");
            //TextView panTextView = (TextView) this.getActivity().findViewById(R.id.PanValTextView);
            //panTextView.setText(String.valueOf(df.format(ry)));

            //TextView pitchTextView = (TextView) this.getActivity().findViewById(R.id.PitchValTextView2);
            //pitchTextView.setText(String.valueOf(df.format(rx)));

            //TextView yawTextView = (TextView) this.getActivity().findViewById(R.id.YawValTextView);
            //yawTextView.setText(String.valueOf(df.format(rz)));
        }

        ////Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, -tx, -ty, rz, upX, upY, upZ);
        //Matrix.multiplyMM();


        // Matrix.rotateM();
        // Matrix.rotateM
        // Matrix.rotateM()
    }

    protected float radbox(float x) {
        // keep radian in [-pi: pi]
        return (float) (x + (x>Math.PI ? -2*Math.PI : x<-Math.PI ? 2*Math.PI : 0));
    }
	/**
	 * Create a renderer to be used by the fragment. Optionally null can be returned by fragments
	 * that do not intend to display a rendered scene. Returning null will cause a warning to be
	 * logged to the console in the event null is in error.
	 * 
	 * @return
	 */
	protected abstract AExampleRenderer createRenderer();

	public void hideLoader() {
		mProgressBarLoader.post(new Runnable() {
			@Override
			public void run() {
				mProgressBarLoader.setVisibility(View.GONE);
			}
		});
	}

    public void showlogo() {
        mImageViewExampleLink.post(new Runnable() {
            @Override
            public void run() {
   mImageViewExampleLink.setVisibility(View.VISIBLE);
            }
        });
    }

    public void hidelogo() {
        mImageViewExampleLink.post(new Runnable() {
            @Override
            public void run() {
                mImageViewExampleLink.setVisibility(View.GONE);
            }
        });
    }

	protected boolean isTransparentSurfaceView() {
		return false;
	}

	protected void showLoader() {
		mProgressBarLoader.post(new Runnable() {
			@Override
			public void run() {
				mProgressBarLoader.setVisibility(View.VISIBLE);
			}
		});
	}

	protected abstract class AExampleRenderer extends RajawaliRenderer {

		public AExampleRenderer(Context context) {
			super(context);
			setFrameRate(60);
		}

		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			showLoader();
			super.onSurfaceCreated(gl, config);
			hideLoader();
		}

	}

	private static final class NullRenderer extends RajawaliRenderer {

		public NullRenderer(Context context) {
			super(context);
			RajLog.w(this + ": Fragment created without renderer!");
		}

		@Override
		public void onSurfaceDestroyed() {
			stopRendering();
		}
	}

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


}
