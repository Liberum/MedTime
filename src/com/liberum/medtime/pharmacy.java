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
	
	// ����������� ����������
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
		  
	        // ��������� ����������� � ��
	        db = new DB(this);
	        db.open();
	        
	        
	        // �������� ������
	        cursor = db.getAllMed();
	        startManagingCursor(cursor);
	        
	        // ��������� ������� �������������
	        String[] from = new String[] { DB.COLUMN_IMG_MED, DB.COLUMN_NAME_MED, DB.COLUMN_NUM };
	        int[] to = new int[] { R.id.ivImg, R.id.tvText, R.id.tvNum};
	        
	        // �������� ������� � ����������� ������
	        scAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);
	        lvPharmacy = (ListView) findViewById(R.id.lvPharmacy);
	        lvPharmacy.setAdapter(scAdapter);
	        
	        // ��������� ����������� ���� � ������
	        registerForContextMenu(lvPharmacy);  
	  }
	  
	  // ������� ���������� ������ ��������� 
	  
//	    // ��������� ������� ������ ���������� �����
//	    public void oonButtonClickMed(View view) {
//	        // �������� ������
////	        showDialog(DIALOG_EXIT);    
//
//	    }
//	    
//	    
//	    //������ ���������� �����
//	    protected Dialog onCreateDialog(int id) {
//	        if (id == DIALOG_EXIT) {
//
//	          AlertDialog.Builder adb = new AlertDialog.Builder(this);
//	          // ���������
//	          adb.setTitle(R.string.addCourse);
//	          // ���������
//	          adb.setMessage(R.string.course_data);
//	          // ������
//	          adb.setIcon(android.R.drawable.ic_dialog_info);
//	          // ������ �������������� ������
//	          adb.setPositiveButton(R.string.yes, myClickListener);
//	          // ������ �������������� ������
//	          adb.setNegativeButton(R.string.no, myClickListener);
//	          // ������� view �� dialog.xml
//	          view = (LinearLayout) getLayoutInflater().inflate(R.layout.add_course, null);
//	          // ������������� ��, ��� ���������� ���� �������
//	          adb.setView(view);
//	          // ������� ������
//	          return adb.create();
//	              	
//	        }
//	        return super.onCreateDialog(id);
//	      }
//	    
//	    OnClickListener myClickListener = new OnClickListener() {
//	        public void onClick(DialogInterface dialog, int which) {
//	          switch (which) {
//	          // ������������� ������
//	          case Dialog.BUTTON_POSITIVE:
//	        	  EditText text = (EditText)((AlertDialog)dialog).findViewById(R.id.new_course); 	// �������� ��������
//	        	  saveData(text.getText().toString());												// ��������� � ������
//	            break;
//	          // ���������� ������
//	          case Dialog.BUTTON_NEGATIVE:
//	            break;
//
//	          }
//	        }
//	      };
//	      
//	      // ��������� ���������� ������ �����
//	      void saveData(String text) {
//	    	  
//	    	  // ��������� �������� ����� � ������� � ���������� ������
//	      	db.addCourse(text, R.drawable.ic_launcher);
//	      	// ��������� ������
//	        cursor.requery();
//	        	
//	        // ����������� ���� ������������, ��� ������ ���������
//	        Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
//	        
//	        findViewById(R.id.root).invalidate();
//	        
//	        // ������������� ��������, ��� �� ��������� ������
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
	          // �������� �� ������ ������������ ���� ������ �� ������ ������ 
	          AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
	          // ��������� id ������ � ������� ��������������� ������ � ��
	          db.delMed(acmi.id);
	          // ��������� ������
	          cursor.requery();
	          return true;
	        }
	        return super.onContextItemSelected(item);
	      }
	    
	    
	    protected void onDestroy() {
	        super.onDestroy();
	        // ��������� ����������� ��� ������
	        db.close();
	      }
	    
	    
	    //���������� ��������
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
