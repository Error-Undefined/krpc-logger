package loggers;

import krpc.client.Connection;
import krpc.client.RPCException;
import krpc.client.StreamException;
import krpc.client.services.SpaceCenter.Orbit;

public class ApoapsisLogger extends Logger<Double> {

	public ApoapsisLogger(Orbit o, Connection c) throws StreamException, RPCException {
		super(c.addStream(o, "getApoapsisAltitude"));
	}

	@Override
	public String toString() {
		return "Apoapsis Altitude";
	}

}
