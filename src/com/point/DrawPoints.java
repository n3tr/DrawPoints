package com.point;

import java.io.Console;
import java.util.ArrayList;


import com.point.DrawPoints.MyPath;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.Toast;


public class DrawPoints extends Activity implements OnTouchListener{
	 public static boolean action=false;
   float x1 = 0;

   float y1 = 0;

   float x2 = 0;

   float y2 = 0;
   Drawer drawView;
   ArrayList<MyPath> pointSet = new ArrayList<MyPath>();
   ArrayList<MyPath> originSet = new ArrayList<MyPath>();

   @Override

   public void onCreate(Bundle savedInstanceState)

   {

       super.onCreate(savedInstanceState);

       setContentView(R.layout.main);

        drawView = new Drawer(this);
      
        drawView.pointSet = this.pointSet;

       LinearLayout layout = (LinearLayout)this.findViewById(R.id.layout_id);

       layout.setOnTouchListener((OnTouchListener)this);

       layout.addView(drawView);
       
       
       
       // Add Origin Pattern
       MyPath point;
       
       point = new MyPath(0, 2);
       originSet.add(point);
       
       point = new MyPath(0, 1);
       originSet.add(point);
       
       point = new MyPath(0, 0);
       originSet.add(point);
       
       point = new MyPath(1, 0);
       originSet.add(point);
       
       point = new MyPath(1, 1);
       originSet.add(point);
       
       point = new MyPath(1, 2);
       originSet.add(point);
       
       point = new MyPath(2, 2);
       originSet.add(point);
       
       point = new MyPath(2, 1);
       originSet.add(point);
       
       point = new MyPath(2, 0);
       originSet.add(point);

   }

  

   
   
   
   public boolean onTouch(View v, MotionEvent event) {
	   //Log.v(null, "" + v.getHeight());
	   int colWidth = v.getWidth() / 3;
	   int rowHeight = v.getHeight() / 3;
	   
	   int marginY = 60;
	   int marginX = 40;
	   
	   switch (event.getAction()) {
	   case MotionEvent.ACTION_DOWN:
		  
		   
		   int row = 0;
		   int col = 0;
		   
		   
		   if( event.getX() < colWidth){
			   col = 0;
		   } else if (event.getX() < (colWidth *2)){
			   col = 1;
		   } else if (event.getX() < (colWidth *3)){
			   col = 2;
		   }
		   
		   if ( event.getY() <  rowHeight ){
			   row = 0;
		   } else if(event.getY() < (rowHeight * 2)){
			   row = 1;
		   } else if(event.getY() < (rowHeight * 3)){
			   row = 2;
		   }
		   
		   MyPath point = new MyPath(row,col);
		   pointSet.add(point);
		   
		   Log.v (null,  "row: " + row + ", col: " + col);
		   Log.v (null , "" + pointSet.size());
		   
		   action=false;
		   v.invalidate();
		   return true;
	   		
	   case MotionEvent.ACTION_MOVE:
		   x2 = event.getX();
		   y2 = event.getY();
		   
		  int moveRow = 0;
		  int moveCol = 0;
		   
		  boolean inX = false, inY = false;
		  
		  if(event.getX() < colWidth){
			   moveCol = 0;
			   inX = true;
		   } else if ( event.getX() > (colWidth + marginX) &&  event.getX() < (colWidth *2) - marginX){
			   moveCol = 1;
			   inX = true;
			   
		   } else if ( event.getX() > ((colWidth * 2) + marginX) &&  event.getX() < (colWidth *3)){
			   moveCol = 2;
			   inX = true;
		   }
		   
		   if ( event.getY() <  rowHeight ){
			   moveRow = 0;
			   inY = true;
		   } else if( event.getY() > (rowHeight + marginY)  && event.getY() < (rowHeight * 2) - marginY){
			   moveRow = 1;
			   inY = true;

		   } else if( event.getY() > ((rowHeight * 2) + marginY)  && event.getY() < (rowHeight * 3) - marginY){
			   moveRow = 2;
			   inY = true;
		   }
		   
		   if(!this.pointExist(moveRow,moveCol) && inX && inY ){
			   MyPath movePoint = new MyPath(moveRow,moveCol);
			   pointSet.add(movePoint);
			   Log.v (null,  "row: " + moveRow + ", col: " + moveCol);
			   
			   v.invalidate();
		   }
		   
		  
		   
		   
		   
		   
		   
		   //v.invalidate();
		   return true;
	   case MotionEvent.ACTION_UP:
		  
		 
		   if(this.validatePoint()){
			   setContentView(R.layout.page2);
		   }else{
			   Context context = getApplicationContext();
			   CharSequence text = "Invalid Pattern!";
			   int duration = Toast.LENGTH_SHORT;

			   Toast toast = Toast.makeText(context, text, duration);
			   toast.show(); 
		   }
		   pointSet.clear();
		   v.invalidate();
		   action=true;
		   return true;
	   }

       return false;

   }
   
   private boolean pointExist(int row,int col){
	 for (MyPath point : pointSet) {
		if (point.row == row && point.col == col)
			return true;
	}
	 
	 return false;
   }
   
   private boolean validatePoint()
   {
	   
	   if (originSet.size() != pointSet.size())
		   return false;
	   
	   for(int i = 0; i < originSet.size(); i++){
		   if( originSet.get(i).row != pointSet.get(i).row )
			   return false;
		   
		   if( originSet.get(i).col != pointSet.get(i).col )
			   return false;
	   }
	   
	   return true;
   }



   public class Drawer extends View

   {
	   ArrayList<MyPath> pointSet;

        public Drawer(Context context)

        {

             super(context);

        }



        protected void onDraw(Canvas canvas)
        {
        	
        	
        	Log.v ("OnDraw" , "" + pointSet.size());
	        Paint p = new Paint();
	        p.setColor(Color.parseColor("#dd3366"));
	        p.setStrokeWidth(10);
	        
	        Paint tablePaint = new Paint();
	        tablePaint.setColor(Color.parseColor("#ff3366"));
	        tablePaint.setStrokeWidth(2);
	        
	        Paint pcircle = new Paint();
	        pcircle.setColor(Color.parseColor("#e6e6e6"));
	        
	        Paint pcircleholder = new Paint();
	        pcircleholder.setColor(Color.parseColor("#dd3366"));
	        
	        
	        int viewWidth = this.getWidth();
	        int viewHeight = this.getHeight();
	        
	        int colWidth = viewWidth / 3;
	        int rowHeight = viewHeight / 3;
	        
	        /*
	        canvas.drawLine(colWidth, 2, colWidth, viewHeight - 2, tablePaint);
    		canvas.drawLine(2* colWidth, 5, 2*colWidth, viewHeight - 5, tablePaint);
    		canvas.drawLine(5, rowHeight, viewWidth - 5, rowHeight, tablePaint);
    		canvas.drawLine(5, 2*rowHeight, viewWidth - 5, 2*rowHeight, tablePaint);
    		*/
    		
    		
    		for (int i = 0 ; i < 3; i++){
    			canvas.drawCircle((colWidth * i) + colWidth / 2, (rowHeight * 0) + rowHeight / 2, 40, pcircle);
    			canvas.drawCircle((colWidth * i) + colWidth / 2, (rowHeight * 1) + rowHeight / 2, 40, pcircle);
    			canvas.drawCircle((colWidth * i) + colWidth / 2, (rowHeight * 2) + rowHeight / 2, 40, pcircle);
    			
    		}
    		
    		if(pointSet.size()  > 0){
    			MyPath stopPoint = pointSet.get(0);
    			float stopX = (stopPoint.col * colWidth) + colWidth / 2;
        		float stopY = (stopPoint.row * rowHeight) + rowHeight / 2;
    			canvas.drawCircle(stopX, stopY, 35, pcircleholder);
    		}
    		
	        if(pointSet.size() > 1){
	        	
	        	for (int i = 1; i < pointSet.size(); i++) {
	        		MyPath startPoint = pointSet.get(i-1);
	        		MyPath stopPoint = pointSet.get(i);
	        		float startX = (startPoint.col * colWidth) + colWidth / 2;
	        		float startY = (startPoint.row * rowHeight) + rowHeight / 2;
	        		float stopX = (stopPoint.col * colWidth) + colWidth / 2;
	        		float stopY = (stopPoint.row * rowHeight) + rowHeight / 2;
					canvas.drawLine(startX, startY, stopX, stopY, p);
					
					canvas.drawCircle(stopX, stopY, 35, pcircleholder);
				}
	        	
	        }
	        
	        
        }
        //canvas.drawLine()
        
        
}
   
   class MyPath{
	   int row,col;
	   
	   public MyPath(int row,int col){
		   this.row = row;
		   this.col = col;
	   }
   }


}
