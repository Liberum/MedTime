package com.liberum.medtime;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class pharmacy extends Activity {
	
	// прописываем переменные
	ListView lvPharmacy;
	DB db;
	SimpleCursorAdapter scAdapter;
	Cursor cursor;
	private static final int CM_DELETE_ID = 1;
	final int DIALOG_EXIT = 1;
	LinearLayout view;
	
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.pharmacy);
	    
	    pharmacy();
	    
	  }
	  
	  
	  public void pharmacy() {
		  
	        // открываем подключение к Ѕƒ
	        db = new DB(this);
	        db.open();
	        
	        
	        // получаем курсор
	        cursor = db.getAllMed();
	        startManagingCursor(cursor);
	        
	        // формируем столбцы сопоставлени€
	        String[] from = new String[] { DB.COLUMN_IMG_MED, DB.COLUMN_NAME_MED, DB.COLUMN_NUM };
	        int[] to = new int[] { R.id.ivImg, R.id.tvText, R.id.tvNum};
	        
	        // создааем адаптер и настраиваем список
	        scAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);
	        lvPharmacy = (ListView) findViewById(R.id.lvPharmacy);
	        lvPharmacy.setAdapter(scAdapter);
	        
	        // добавл€ем контекстное меню к списку
	        registerForContextMenu(lvPharmacy);  
	  }
	  
	  // сделать добавление нового лекарства 
	  
//	    // обработка нажати€ кнопки добавлени€ курса
//	    public void oonButtonClickMed(View view) {
//	        // вызываем диалог
////	        showDialog(DIALOG_EXIT);    
//
//	    }
//	    
//	    
//	    //ƒиалог добавлени€ курса
//	    protected Dialog onCreateDialog(int id) {
//	        if (id == DIALOG_EXIT) {
//
//	          AlertDialog.Builder adb = new AlertDialog.Builder(this);
//	          // заголовок
//	          adb.setTitle(R.string.addCourse);
//	          // сообщение
//	          adb.setMessage(R.string.course_data);
//	          // иконка
//	          adb.setIcon(android.R.drawable.ic_dialog_info);
//	          // кнопка положительного ответа
//	          adb.setPositiveButton(R.string.yes, myClickListener);
//	          // кнопка отрицательного ответа
//	          adb.setNegativeButton(R.string.no, myClickListener);
//	          // создаем view из dialog.xml
//	          view = (LinearLayout) getLayoutInflater().inflate(R.layout.add_course, null);
//	          // устанавливаем ее, как содержимое тела диалога
//	          adb.setView(view);
//	          // создаем диалог
//	          return adb.create();
//	              	
//	        }
//	        return super.onCreateDialog(id);
//	      }
//	    
//	    OnClickListener myClickListener = new OnClickListener() {
//	        public void onClick(DialogInterface dialog, int which) {
//	          switch (which) {
//	          // положительна€ кнопка
//	          case Dialog.BUTTON_POSITIVE:
//	        	  EditText text = (EditText)((AlertDialog)dialog).findViewById(R.id.new_course); 	// получаем значение
//	        	  saveData(text.getText().toString());												// переводим в стринг
//	            break;
//	          // негативна€ кнопка
//	          case Dialog.BUTTON_NEGATIVE:
//	            break;
//
//	          }
//	        }
//	      };
//	      
//	      // обработка сохранени€ нового курса
//	      void saveData(String text) {
//	    	  
//	    	  // ƒобавл€ем название курса в таблицу с названи€ми курсов
//	      	db.addCourse(text, R.drawable.ic_launcher);
//	      	// обновл€ем курсор
//	        cursor.requery();
//	        	
//	        // всплывающее окно покахывающее, что данные сохранены
//	        Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
//	        
//	        findViewById(R.id.root).invalidate();
//	        
//	        // перезапускаем активити, что бы обновилс€ спинер
//	        reload();    
//	      }
	    
	    
	    public void onCreateContextMenu(ContextMenu menu, View v,
	            ContextMenuInfo menuInfo) {
	          super.onCreateContextMenu(menu, v, menuInfo);
	          menu.add(0, CM_DELETE_ID, 0, R.string.delete_record);
	          cursor.requery();
	        }
	    
	    public boolean onContextItemSelected(MenuItem item) {
	        if (item.getItemId() == CM_DELETE_ID) {
	          // получаем из пункта контекстного меню данные по пункту списка 
	          AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
	          // извлекаем id записи и удал€ем соответствующую запись в Ѕƒ
	          db.delMed(acmi.id);
	          // обновл€ем курсор
	          cursor.requery();
	          return true;
	        }
	        return super.onContextItemSelected(item);
	      }
	    
	    
	    protected void onDestroy() {
	        super.onDestroy();
	        // закрываем подключение при выходе
	        db.close();
	      }
	    
	    
	    //перезапуск активити
	    private void reload()
	    {
	        Intent intent = getIntent();
	        overridePendingTransition(0, 0);
	        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	        finish();
	        overridePendingTransition(0, 0);
	        startActivity(intent);
	    }
	  

}
