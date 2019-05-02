package loggers;

import org.javatuples.Triplet;

import krpc.client.Connection;
import krpc.client.RPCException;
import krpc.client.StreamException;
import krpc.client.services.SpaceCenter.Flight;

public class PositionLogger extends Logger<Triplet<Double, Double, Double>> {

	private Triplet<Double, Double, Double> start;
	
	public PositionLogger(Flight f, Connection c) throws StreamException, RPCException {
		super(c.addStream(f, "getCenterOfMass"));
		this.start=super.s.get();
	}
	
	@Override
	public String getData() throws RPCException, StreamException  {
		Triplet<Double, Double, Double> current=super.s.get();
		
		double x=current.getValue0()-start.getValue0();
		double y=current.getValue1()-start.getValue1();
		double z=current.getValue2()-start.getValue2();
		
		double range=Math.sqrt(x*x+y*y+z*z);
	
		return Double.toString(range);
	}

	@Override
	public String toString() {
		return "Position Logger";
	}

}
