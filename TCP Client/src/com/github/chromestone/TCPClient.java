package com.github.chromestone;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Color;

/**
 * A TCP Client thread that follows "chromestone protocol"
 * 
 * @author derekzhang
 *
 */
public class TCPClient extends Thread {

	private Application app;
	private Controller controller;
	private DataInputStream in;
	private DataOutputStream out;
	
	public TCPClient(Application app, Controller controller, DataInputStream in, DataOutputStream out) { 
		
		this.app = app;
		this.controller = controller;
		this.in = in;
		this.out = out;
	}
	
	@Override
	public void run() {
		try {
			while (!isInterrupted()) {
				
				//gets input and formats it
				//for some reason, controllers I tested kept getting stuck at numbers 1-10
				//note that you can add other inputs here
				int leftMotorPow = (int) (100 * controller.getAxis(1));
				if (Math.abs(leftMotorPow) < 10) {
	
					leftMotorPow = 0;
				}
				int rightMotorPow = (int) (100 * controller.getAxis(3));
				if (Math.abs(rightMotorPow) < 10) {
					
					rightMotorPow = 0;
				}
				
				//arguments delimited by ':'; end of message char is '|'
				out.writeBytes("lr:" + leftMotorPow + ":" + rightMotorPow + "|");
				//part of the protocol
				//when server is done, client continues
				//curently what should be sent back is a char ('|' for all good)
				in.readByte();
			}
			
			//makes sure server gets 0
			//in terms of robots, shutdown
			out.writeBytes("lr:" + 0 + ":" + 0 + "|");
			in.readByte();
		}
		catch (IOException e) {
			
			if (!e.getMessage().contains("Socket closed")) {

				e.printStackTrace();
			}
			
			//write back to the main thread that something is wrong
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
