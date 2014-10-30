package com.example.scouter;

import java.util.Hashtable;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@SuppressLint("ViewConstructor")
public class DrawQRCode extends View{
	
	String input = "";
	int size;
	int scale = 8;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public DrawQRCode(Context context, String message){
		super(context);
		
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point windowSize = new Point();
		
		display.getSize(windowSize);
		
		this.size = windowSize.x / 8;
		
		input = message;
	}
	@SuppressWarnings("unchecked")
	public void draw(Canvas c){
		c.scale(scale, scale, 5, 0);
		
		//qr code
		//makes a qr code then draws it
		try{			
	        @SuppressWarnings("rawtypes")
			Hashtable hintMap = new Hashtable();
	        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
	        QRCodeWriter qrCodeWriter = new QRCodeWriter();	//from zxing library
	        BitMatrix byteMatrix = qrCodeWriter.encode(
	        							input, //this is the string to be converted into a bitmatrix (aka qr code)
	                					BarcodeFormat.QR_CODE, 
	                					size, size, 
	                					hintMap
	                								   );
	        
	        //drawing from bitMatrix
	        int matrixWidth = byteMatrix.getWidth();
	        
    		Paint black = new Paint();
    		black.setColor(Color.BLACK);
    		black.setStyle(Paint.Style.FILL);
    		
	        for (int i = 0; i < matrixWidth; i++) {
	            for (int j = 0; j < matrixWidth; j++) {
	                if (byteMatrix.get(i, j)) {
	                	//drawing black rectangles
	            		Rect r = new Rect();
	            		r.set(i, j, i+1, j+1);
	            		
	            		c.drawRect(r, black);
	                }
	            }
	        }
	        
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
