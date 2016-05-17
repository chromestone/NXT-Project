package com.github.chromestone;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Color;

public class CStoneProtocolClient extends Thread {

	private Application app;
	private Controller controller;
	private DataInputStream in;
	private DataOutputStream out;
	
	public CStoneProtocolClient(Application app, Controller controller, DataInputStream in, DataOutputStream out) { 
		
		this.app = app;
		this.controller = controller;
		this.in = in;
		this.out = out;
	}
	
	@Override
	public void run() {
		try {
			while (!isInterrupted()) {

				int leftMotorPow = (int) (100 * controller.getAxis(1));
				if (Math.abs(leftMotorPow) < 10) {
	
					leftMotorPow = 0;
				}
				int rightMotorPow = (int) (100 * controller.getAxis(3));
				if (Math.abs(rightMotorPow) < 10) {
					
					rightMotorPow = 0;
				}
				
				out.writeBytes("lr:" + leftMotorPow + ":" + rightMotorPow + "|");
				in.readByte();
			}
			
			out.writeBytes("lr:" + 0 + ":" + 0 + "|");
			in.readByte();
		}
		catch (IOException e) {
			
			if (!e.getMessage().contains("Socket closed")) {

				e.printStackTrace();
			}
			
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {

					app.setStatus(Color.RED);
					app.disconnected(controller);
				}
			});
		}
	}
}
