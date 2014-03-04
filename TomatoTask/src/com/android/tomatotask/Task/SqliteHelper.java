package com.android.tomatotask.Task;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {

	  private static String INFONAME;
	  private static String NAME;
	  private static int VERSION = 1;

	  static
	  {
	    NAME = " table_notepad";
	    INFONAME = "notepad.db";
	  }

	  public SqliteHelper(Context paramContext, String paramString, SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt)
	  {
	    super(paramContext, INFONAME, null, VERSION);
	  }

	  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
	  {
	    paramSQLiteDatabase.execSQL("create table " + NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,date TEXT,content TEXT)");
	  }

	  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
	  {
	  }
}
