package loggers;

import krpc.client.Connection;
import krpc.client.RPCException;
import krpc.client.StreamException;
import krpc.client.services.SpaceCenter.Vessel;

public class TimeLogger extends Logger<Double>{

	public TimeLogger(Vessel v, Connection c) throws StreamException, RPCException {
		super(c.addStream(v, "getMET"));
	}

	@Override
	public String toString() {
		return "MET";
	}
	
}
