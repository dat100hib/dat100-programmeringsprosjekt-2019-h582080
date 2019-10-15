package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 600;
	private static int MAPYSIZE = 600;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;
	
	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();
	}

	public static void main(String[] args) {
 	launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);

//		playRoute(MARGIN + MAPYSIZE);
		
		showStatistics();
	}

	//X lengdegrad
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon)); 

		return xstep;
	}

	//Y Breddegrad
	public double ystep() {
	
		double maxlon = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		
		double ystep = MAPYSIZE / (Math.abs(maxlon-minlon));
		
		
		return ystep;
	}

	public void showRouteMap(int ybase) {

		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		
		
		int x,y,r=3;
		int x1,y1;
        //setColor(0,255,0);
		
		for(int i = 0; i < gpspoints.length-1; i++) {
			x = (MARGIN+(int)((gpspoints[i].getLongitude()-minlon)*xstep()));
			y = ((int)(ybase-((gpspoints[i].getLatitude()-minlat)*ystep())));
			x1 = (MARGIN+(int)((gpspoints[i+1].getLongitude()-minlon)*xstep()));
			y1 = ((int)(ybase-((gpspoints[i+1].getLatitude()-minlat)*ystep())));
			fillCircle(x,y,r);
			drawLine(x,y,x1,y1);
			
         	if(gpspoints[i].getElevation()<=gpspoints[i+1].getElevation()) {
         		setColor(0,255,0);
         	}
         	else {
         		setColor(255,0,0);
         	}
		}
		
//funker ikke, finn ut hvorfor!	(	

//		double [] latitudes = GPSUtils.getLatitudes(gpspoints);
//		double [] longitudes = GPSUtils.getLongitudes(gpspoints);
//		setColor(0,255,0);
//	        for(int i = 0; i < gpspoints.length; i++) {
//         	fillCircle(MARGIN+(int)((longitudes[i]-minlon)*xstep()),(int)((ybase-(latitudes[i])-minlan)*ystep()),3);
//         	}
		
	}

	public void showStatistics() {

		int TEXTDISTANCE = 20;

		setColor(0,0,0);
		setFont("Courier",12);
		
		int WEIGHT = 80;
		
		String start = ("==========================================");

		
		
		String tt= ("Total Time" + "\t:        "+ GPSUtils.formatTime(gpscomputer.totalTime()));
		String td = ("Total Distance" + "\t:"+ String.format("%1$11.2f", gpscomputer.totalDistance()/1000)+" km");
		String te = ("Total elevation" +  "\t:"+ String.format("%1$10.2f", gpscomputer.totalElevation())+" m");
		String ms = ("Max speed" + "\t:"+ String.format("%1$16.2f", gpscomputer.maxSpeed())+" km/t");
		String as = ("Average speed" + "\t:"+ String.format("%1$12.2f", gpscomputer.averageSpeed())+" km\t");
		String e = ("Energy" + "\t\t:"+ String.format("%1$20.2f", gpscomputer.totalKcal(WEIGHT))+" kcal");		
		
		String [] stat = {start,tt,td,te,ms,as,e};
		
		int antallstrings = 7;
		int x = MARGIN+5;
		int y = 600-580;
		for(int i = 0; i < antallstrings; i++) {
			drawString(stat[i],x,y);
			y += 10;			
		}
		
	}

	public void playRoute(int ybase) {
		
//		// TODO - START
//		
				throw new UnsupportedOperationException(TODO.method());
//				
//				// TODO - SLUTT

		
	}

}
