package systems;

import java.io.IOException;

import krpc.client.Connection;
import krpc.client.RPCException;
import krpc.client.StreamException;
import krpc.client.services.KRPC;
import krpc.client.services.SpaceCenter;
import krpc.client.services.SpaceCenter.Flight;
import krpc.client.services.SpaceCenter.Orbit;
import krpc.client.services.SpaceCenter.ReferenceFrame;
import krpc.client.services.SpaceCenter.Vessel;
import loggers.*;

public class MainModule {

	@SuppressWarnings("unused")
	private KRPC krpc;
	private LoggerModule logger;
	private SpaceCenter sc;
	private Vessel v;
	private Orbit o;
	private ReferenceFrame orbFrame;
	private ReferenceFrame srfFrame;
	private Flight orbF;
	private Flight srfF;

	private int interval;
	private int factor;

	public static void main(String[] args) throws IOException {
		try(Connection c=Connection.newInstance("Logger", "127.0.0.1", 50000, 50001)){
			MainModule m=new MainModule(c);
			m.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public MainModule(Connection con) throws StreamException, RPCException, IOException {
		krpc = KRPC.newInstance(con);
		sc = SpaceCenter.newInstance(con);
		v = sc.getActiveVessel();
		o = v.getOrbit();
		orbFrame = v.getOrbit().getBody().getNonRotatingReferenceFrame();
		srfFrame = v.getOrbit().getBody().getReferenceFrame();
		orbF = v.flight(orbFrame);
		srfF = v.flight(srfFrame);

		interval = 200;
		factor = (int) sc.getWarpRate();

		this.logger = new LoggerModule();

		logger.addLogger(new AltitudeLogger(orbF, con));
		logger.addLogger(new OrbitalSpeedLogger(orbF, con));
		logger.addLogger(new SurfaceSpeedLogger(srfF, con));
		logger.addLogger(new ApoapsisLogger(o, con));
		logger.addLogger(new PeriapsisLogger(o, con));
		logger.addLogger(new PositionLogger(srfF, con));
		logger.addLogger(new GForceLogger(orbF, con));

		logger.addLogger(new TimeLogger(v, con));
		
		logger.initFile();
		
		while(v.getThrust()==0) {
			
		}
	}

	/**
	 * Sets the interval between runs to the specified milliseconds.
	 * 
	 * @param millis the time to wait between each run
	 */
	public void setLoggingInterval(int millis) {
		this.interval = millis;
	}

	public void run() {
		System.out.println("Starting log");
		
		while (true) {
			try {
				System.out.println(logger.log());
				factor = (int) sc.getWarpRate();
			} catch (IOException | InterruptedException | RPCException | StreamException e) {
				e.printStackTrace();
				System.exit(3);
			}
			try {
				Thread.sleep(interval / factor);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
