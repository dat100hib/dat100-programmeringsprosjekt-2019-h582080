package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}
	
	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	// Total distances (i meter)
	public double totalDistance() {

		
		double distance = 0;
		
		for(int i = 0; i < gpspoints.length-1; i++) {
			distance += GPSUtils.distance(gpspoints[i],gpspoints[i+1]);
		}
			

		return distance;

	}

	// Total høyde (i meter)
	public double totalElevation() {

		double elevation = 0;

		for(int i = 0; i <gpspoints.length-1; i++) {
			if(gpspoints[i].getElevation() < gpspoints[i+1].getElevation()) {
				elevation = gpspoints[i+1].getElevation();
			}
		}
		return elevation;


	}

	// Total tiden for hele turen (i sekunder)
	public int totalTime() {

	    int time = gpspoints[gpspoints.length-1].getTime()-(gpspoints[0].getTime());
	   

		return time;
	
	}
		
	// Gjennomsnittshastighet

	public double[] speeds() {
	
		double speedtab [] = new double [gpspoints.length-1];
		
		for(int i = 0; i < gpspoints.length-1; i++) {
			speedtab[i] = GPSUtils.speed(gpspoints[i], gpspoints[i+1]);
		}
		return speedtab;		
	}
	
	public double maxSpeed() {
	
		return GPSUtils.findMax(speeds());
		
	}

	
	public double averageSpeed() {

		double average = (totalDistance()/totalTime()*3.6);
			
		return average;
	}



	// m/s
	public static double MS = 2.236936;
	public double kcal(double weight, int secs, double speed) {

		double kcal;

		double met = 0;		
		double speedmph = speed * MS;
		
		
		if (speedmph < 10 && speedmph > 0 ) {
			met = 4.0;
		}
		else if( speedmph >= 10 && speedmph < 12) {
		
			met = 6.0;
		}
		else if( speedmph >= 12 && speedmph < 14) {
			
			met = 8.0;
		}

		else if( speedmph >= 14 && speedmph < 16) {
			
			met = 10.0;
		}
        else if( speedmph >= 16 && speedmph < 20) {
			
			met = 12.0;
		}
        else if(speedmph > 20) {
        	met = 16.0;
        }
		
		kcal = met*weight*secs/3600;
		
		return kcal;
	}

	public double totalKcal(double weight) {

		double totalkcal = kcal(weight,totalTime(),averageSpeed());
		
		
		return totalkcal;
		
	}
	
	private static double WEIGHT = 80.0;
	
	public void displayStatistics() {

		System.out.println("==============================================");


	
		System.out.println("Total Time" + "\t:  "+ GPSUtils.formatTime(totalTime()));
		System.out.println("Total Distance" + "\t:"+ String.format("%1$12.2f", totalDistance()/1000)+" km");
		System.out.println("Total elevation" +  "\t:"+ String.format("%1$12.2f", totalElevation())+" m");
		System.out.println("Max speed" + "\t:"+ String.format("%1$12.2f", maxSpeed())+" km/t");
		System.out.println("Average speed" + "\t:"+ String.format("%1$12.2f", averageSpeed())+" km\t");
		System.out.println("Energy" + "\t\t:"+ String.format("%1$12.2f", totalKcal(WEIGHT))+" kcal");		
		
		System.out.println("==============================================");
		
	}

}
