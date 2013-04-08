package com.capinfo.myslidingmenu;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;
import android.view.Window;

import com.capinfo.myslidingmenu.view.PageWidget;
import com.example.myslidingmenu2.R;

/**
 * Æô¶¯Ò³
 * @author Gongcong
 *
 */
public class FistStart extends Activity{
	protected void onCreate(android.os.Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		PageWidget pageWidget = new PageWidget(this);

		Display display = getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();

		pageWidget.SetScreen(width, height);

//		Bitmap bm1 = BitmapFactory.decodeResource(getResources(),
//				R.drawable.before);
//		Bitmap bm2 = BitmapFactory.decodeResource(getResources(),
//				R.drawable.after);

	//	Bitmap foreImage = Bitmap.createScaledBitmap(bm1, width, height, false);
	//	Bitmap bgImage = Bitmap.createScaledBitmap(bm2, width, height, false);

	//	pageWidget.setBgImage(bgImage);
	//	pageWidget.setForeImage(foreImage);

		setContentView(pageWidget);
		
		super.onCreate(savedInstanceState);
	}



}
