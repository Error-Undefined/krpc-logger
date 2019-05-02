package loggers;

import krpc.client.Connection;
import krpc.client.RPCException;
import krpc.client.StreamException;
import krpc.client.services.SpaceCenter.Orbit;

public class PeriapsisLogger extends Logger<Double> {

	public PeriapsisLogger(Orbit o, Connection c) throws StreamException, RPCException {
		super(c.addStream(o, "getPeriapsisAltitude"));
	}

	@Override
	public String toString() {
		return "Periapsis Altitude";
	}

}
