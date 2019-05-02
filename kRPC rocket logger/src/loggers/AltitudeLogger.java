package loggers;

import krpc.client.Connection;
import krpc.client.RPCException;
import krpc.client.StreamException;
import krpc.client.services.SpaceCenter.Flight;

public class AltitudeLogger extends Logger<Double> {
	
	public AltitudeLogger(Flight f, Connection c) throws StreamException, RPCException {
		super(c.addStream(f, "getMeanAltitude"));
	}

	public String toString() {
		return "Altitude ASL";
	}
}
