package loggers;

import krpc.client.RPCException;
import krpc.client.Stream;
import krpc.client.StreamException;

public abstract class Logger<T> {
	
	protected Stream<T> s;
	
	/**
	 * Creates a logger for the stream s.
	 * @param s the stream to be logged
	 * @throws StreamException
	 * @throws RPCException
	 */
	public Logger(Stream<T> s) throws StreamException, RPCException {
		this.s=s;
	}
	
	/**
	 * Gets the data of the stream at this moment.
	 * @return the data as a string
	 * @throws RPCException
	 * @throws StreamException
	 */
	public String getData() throws RPCException, StreamException {
		return s.get().toString();
	}
	
	/**
	 * Closes the stream.
	 * @throws RPCException
	 */
	public void terminate() throws RPCException {
		s.remove();
	}
	
	/**
	 * Returns a description of the logger.
	 */
	public abstract String toString();
}
