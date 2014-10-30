package com.storm.qrgen;

import java.util.Hashtable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class DrawQRCode extends View{
	
	public String input = "hello world";
	private int size = 125;
	private int scale = 8;
	
	
	public DrawQRCode(Context context, String message){
		super(context);
		input = message;
	}
	protected void onDraw(Canvas c){
		c.scale(scale, scale, 5, 0);
		
		//qr code
		try{			
	        Hashtable hintMap = new Hashtable();
	        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
	        QRCodeWriter qrCodeWriter = new QRCodeWriter();
	        BitMatrix byteMatrix = qrCodeWriter.encode(input,
	                									BarcodeFormat.QR_CODE, 
	                									size, size, 
	                									hintMap);
	        
	        //drawing from bitMatrix
	        int matrixWidth = byteMatrix.getWidth();
	        
    		Paint black = new Paint();
    		black.setColor(Color.BLACK);
    		black.setStyle(Paint.Style.FILL);
    		
	        for (int i = 0; i < matrixWidth; i++) {
	            for (int j = 0; j < matrixWidth; j++) {
	                if (byteMatrix.get(i, j)) {
	                	//rectangle
	            		Rect r = new Rect();
	            		r.set(i, j, i+1, j+1);
	            		
	            		c.drawRect(r, black);
	                }
	            }
	        }
	        
			
		}catch(Exception e){
			System.out.println(e);
		}
	}
}
