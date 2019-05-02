package loggers;

import krpc.client.Connection;
import krpc.client.RPCException;
import krpc.client.StreamException;
import krpc.client.services.SpaceCenter.Flight;

public class SurfaceSpeedLogger extends Logger<Double> {

	public SurfaceSpeedLogger(Flight f, Connection c) throws StreamException, RPCException {
		super(c.addStream(f, "getSpeed"));
	}

	@Override
	public String toString() {
		return "Surface speed";
	}

}
