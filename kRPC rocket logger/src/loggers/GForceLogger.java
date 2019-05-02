package loggers;

import krpc.client.Connection;
import krpc.client.RPCException;
import krpc.client.StreamException;
import krpc.client.services.SpaceCenter.Flight;

public class GForceLogger extends Logger<Float> {

	
	public GForceLogger(Flight f, Connection c) throws StreamException, RPCException {
		super(c.addStream(f, "getGForce"));
	}

	@Override
	public String toString() {
		return "GForce";
	}

}
