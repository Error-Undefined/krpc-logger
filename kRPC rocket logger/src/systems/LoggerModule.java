package systems;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import krpc.client.RPCException;
import krpc.client.StreamException;
import loggers.Logger;

public class LoggerModule {

	private Map<String, Logger<?>> loggers;
	private FileWriter fw = null;
	private StringBuilder sb;
	private boolean fileWriting;

	/**
	 * Creates the main logging module.
	 * 
	 * @param c the server connection
	 * @throws IOException
	 */
	public LoggerModule() throws IOException {
		this.loggers = new HashMap<String, Logger<?>>();
		this.sb = new StringBuilder();
		this.fileWriting = true;

		String time=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		try {
			this.fw = new FileWriter("C:\\Users\\Otto\\Documents\\KSP_KRPC\\Telemetry\\telemetry_"+time+".txt");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		
	}
	
	/**
	 * Initializes the file with descriptions of the data
	 * @throws IOException
	 */
	public void initFile() throws IOException {
		sb.setLength(0);
		Set<Map.Entry<String, Logger<?>>> entries = loggers.entrySet();
		for (Map.Entry<String, Logger<?>> entry : entries) {
			sb.append(entry.getKey());
			sb.append(",");
		}
		sb.append("\n");
		fw.write(sb.toString());
		sb.setLength(0);
	}

	/**
	 * Adds a logger for the flight.
	 * 
	 * @param l the logger to add
	 */
	public void addLogger(Logger<?> l) {
		loggers.put(l.toString(), l);
	}

	/**
	 * Removes a logger from the flight.
	 * 
	 * @param l the logger to remove
	 * @return the logger, or null if it wasn't present
	 */
	public Logger<?> removeLogger(Logger<?> l) {
		Logger<?> temp = loggers.remove(l.toString());
		return temp;
	}

	/**
	 * Turns writing to a file on or off.
	 * 
	 * @param b true to enable, false to disable
	 */
	public void setFilewriting(boolean b) {
		this.fileWriting = b;
	}

	/**
	 * Logs all specified data, with a pause as specified.
	 * 
	 * @param pause the amount of milliseconds to wait before returning
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws StreamException
	 * @throws RPCException
	 */
	public String log() throws IOException, InterruptedException, RPCException, StreamException {
		sb.setLength(0);

		Set<Map.Entry<String, Logger<?>>> entries = loggers.entrySet();
		for (Map.Entry<String, Logger<?>> entry : entries) {
			sb.append(entry.getValue().getData());
			sb.append(",");
		}
		sb.append("\n");
		if (fileWriting) {
			fw.write(sb.toString());
		}
		return sb.toString();
	}

}
