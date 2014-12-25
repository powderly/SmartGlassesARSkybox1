package com.monyetmabuk.rajawali.tutorials;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.app.Application;
import android.os.Handler;

import com.monyetmabuk.rajawali.tutorials.examples.AExampleFragment;

import com.monyetmabuk.rajawali.tutorials.examples.home.EmptyFragment;
import com.monyetmabuk.rajawali.tutorials.examples.general.EmptyWeatherFragment;
import com.monyetmabuk.rajawali.tutorials.examples.general.WeatherFragment;
import com.monyetmabuk.rajawali.tutorials.examples.general.ForecastFragment;
import com.monyetmabuk.rajawali.tutorials.examples.sports.EmptySportsFragment;
import com.monyetmabuk.rajawali.tutorials.examples.sports.SportsFragment;

public class ExamplesApplication extends Application {

	static enum Category {

		// @formatter:off
        HOME("Home"),
		GENERAL("General"),
        SPORTS("Sports"),
        GALLERY("Gallery");
		// @formatter:on

		private String name;

		Category(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

	}

	public static final Map<Category, ExampleItem[]> ITEMS = new HashMap<Category, ExamplesApplication.ExampleItem[]>();
	public static final String BASE_EXAMPLES_URL = "https://github.com/MasDennis/RajawaliExamples/blob/master/src/com/monyetmabuk/rajawali/tutorials/examples";
    /* sensor variables/objects */
    boolean mBound = false;

    static final String TAG = "ScrollDiag";
    private Handler uiUpdater;

    public static boolean clicked = false;
    public static boolean playvid = false;
    public static boolean stepforward = false;
    public static boolean stepbackward = false;


	@Override
	public void onCreate() {
		super.onCreate();
		// @formatter:off
        ITEMS.put(Category.HOME, new ExampleItem[]{
                new ExampleItem("Home", EmptyFragment.class)
                , new ExampleItem("Weather", ForecastFragment.class)
                , new ExampleItem("Sports", SportsFragment.class)
        });
        ITEMS.put(Category.GENERAL, new ExampleItem[] {
                new ExampleItem("Home",EmptyWeatherFragment.class)
                , new ExampleItem("Weather Menu", ForecastFragment.class)
                , new ExampleItem("Forecast", WeatherFragment.class)
                , new ExampleItem("Maps", EmptyFragment.class)
			});
        ITEMS.put(Category.SPORTS, new ExampleItem[] {
                new ExampleItem("Home", EmptyFragment.class)
                , new ExampleItem("Replays", EmptyFragment.class)
                , new ExampleItem("Game Stats",EmptyFragment.class)
                , new ExampleItem("Scan Views", SportsFragment.class)

        });
        ITEMS.put(Category.GALLERY, new ExampleItem[]{
                new ExampleItem("Folders", ForecastFragment.class)
                , new ExampleItem("Skyboxes", WeatherFragment.class)
                , new ExampleItem("Panoramas", ForecastFragment.class)
        });
	}


	public static final class ExampleItem {

		public final Class<? extends AExampleFragment> exampleClass;
		public final String title;
		public final String url;

		public ExampleItem(String title,
				Class<? extends AExampleFragment> exampleClass) {
			this.title = title;
			this.exampleClass = exampleClass;
			this.url = exampleClass.getSimpleName() + ".java";
		}

		public String getUrl(Category category) {
		    return BASE_EXAMPLES_URL + "/" + category.name.toLowerCase(Locale.US) + "/" + url;
		}
	}
}
