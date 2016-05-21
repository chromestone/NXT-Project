package com.github.chromestone;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Properties;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;

/**
 * Main class of the "application"
 * 
 * @author Derek Zhang
 *
 */
public class Application extends ApplicationAdapter implements ControllerListener, InputProcessor {
	
	public static boolean debug = false;
	
	//represents the status of the program, which is displayed
	private Color status;
	
	private Socket socket;
	private DataInputStream inStream;
	private DataOutputStream outStream;
	
	private OrthographicCamera cam;
	//provides shape drawing
	private ShapeRenderer shapeRenderer;
	
	private TCPClient client;
	//the controller currently polling, may be null
	private Controller myController;
	
	@Override
	public void create() {

		//stuff the libGDX makes you do to set up the window "normally"
		//otherwise screens that aren't squares (which most screens aren't) will render weirdly
		cam = new OrthographicCamera();
		cam.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//setting up shape drawing
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);
		
		status = Color.GREEN;
		
		myController = null;
		
		Properties defaults = new Properties();
		defaults.setProperty("ip", "localhost");
		defaults.setProperty("port", "5005");
		defaults.setProperty("debug", "false");
		
		//loads the config file if it exists
		//if not defaults to defaults listed above and creates config file
		Properties properties = new Properties(defaults);
		File propertiesFile = new File("./nxt-client.config");
		if (propertiesFile.exists()) {
			
			try {

				properties.load(new FileInputStream(propertiesFile));
				debug = Boolean.parseBoolean(properties.getProperty("debug"));
			}
			catch (Exception e) {
				
				status = Color.BLUE;
				debug = true;
			}
		}
		else {
			
			try {
				
				if (propertiesFile.createNewFile()) {
					
					defaults.store(new FileOutputStream(propertiesFile), "");
				}
				else {
					
					status = Color.BLUE;
					debug = true;
				}
			} catch (IOException e) {
				
				status = Color.BLUE;
				debug = true;
			}
		}
		

		if (debug) {
			
			try {

				File logFile = new File("./nxt-client.log");
				if (!logFile.exists()) {

					logFile.createNewFile();
				}
				System.setErr(new PrintStream(logFile));
			}
			catch (Exception e) {

				e.printStackTrace();
				
				status = Color.YELLOW;
			}
		}
		
		try {
			
			socket = new Socket(properties.getProperty("ip"), new Integer(properties.getProperty("port")));
			inStream = new DataInputStream(socket.getInputStream());
			outStream = new DataOutputStream(socket.getOutputStream());
		}
		catch (Exception e) {
			
			e.printStackTrace();
			e.printStackTrace();
			
			status = Color.RED;
			
			return;
		}
		
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render() {
		
		Gdx.gl.glClearColor(status.r, status.g, status.b, status.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//draws the square red button for "emergencies"
		//though one should shut down the actual server in case of one
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(360, 360, 40, 40);
		shapeRenderer.end();
	}
	
	@Override
	public void dispose() {
		
		debug = false;
		
		cam = null;
		shapeRenderer = null;
		
		myController = null;

		if (client != null) {

			client.interrupt();
		}
		
		if (socket != null){
			
			try {
				
				socket.close();
			}
			catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	public void setStatus(Color color) {
		
		this.status = color;
	}
	
	//CONTROLLER_LISTENER
	
	@Override
	public void disconnected(Controller controller) {
		
		if (myController != null && myController == controller) {
			
			myController = null;
			if (client != null) {

				client.interrupt();
				client = null;
			}
		}
	}	
	
	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {
		
		// if (myController != controller && controller.getButton(Button.Start) && buttonCode == Button.A) {
//		if (client != null) {
//			
//			client.interrupt();
//		}
//		myController = controller;
//		client = new TCPClient(this, myController, inStream, outStream);
//		client.start();
//		//return true;
		//}
		
		return false;
	}
	
	@Override
	public void connected(Controller controller) {
		
		if (client != null) {
			
			client.interrupt();
		}
		myController = controller;
		client = new TCPClient(this, myController, inStream, outStream);
		client.start();
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonCode) {
		return false;
	}

	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
		return false;
	}

	@Override
	public boolean povMoved(Controller controller, int povCode,
			PovDirection value) {
		return false;
	}

	@Override
	public boolean xSliderMoved(Controller controller, int sliderCode,
			boolean value) {
		return false;
	}

	@Override
	public boolean ySliderMoved(Controller controller, int sliderCode,
			boolean value) {
		return false;
	}

	@Override
	public boolean accelerometerMoved(Controller controller,
			int accelerometerCode, Vector3 value) {
		return false;
	}
	
	//INPUT_LISTENER
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		//I know it's hardcoded and bad, but I got lazy
		//if the red button is clicked, its equivalent to disconnecting the controller
		if (screenX > 360 && screenY > 360) {
			
			disconnected(myController);
			return true;
		}
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}