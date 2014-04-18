package com.ty.exsample_unit_4;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android_begin_gl_3d.R;

/**
 * 
 * @author tang
 * 
 */
public class LayoutActivity extends ListActivity {

	String[] units = new String[] { "FrameLayout" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.main_items,
				units));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		super.onListItemClick(l, v, position, id);
		Class cls = null;
		switch (position) {
		case 0:

			cls = LayoutViewActivity.class;

			break;
		default:

			break;
		}

		if (cls != null) {
			intentToActivity(cls);

		}
	}

	private void intentToActivity(Class cls) {
		Intent intent = new Intent();
		intent.setClass(this, cls);

		startActivity(intent);
	}

}
