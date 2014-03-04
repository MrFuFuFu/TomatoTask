package com.android.tomatotask.Task;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class BaseActivity extends Activity {
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
	}

	public void startActivity(Class<?> paramClass) {
		startActivity(new Intent(this, paramClass));
	}
}
