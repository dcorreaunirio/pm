package unirio.pm.gpx.calc;
import unirio.pm.gpx.model.TrackPoint;

 public class DistanceCalculator {
	
	static public double getDistance(TrackPoint current, TrackPoint previous, TrackPoint next) {
		
		//Getting latitudes
		double x1 = previous.getLatitude(); 
		double x2 = next.getLatitude();
		double x3 = current.getLatitude();
		
		//Getting longitudes
		double y1 = previous.getLongitude();
		double y2 = next.getLongitude(); 
		double y3 = current.getLongitude(); 
		
		//Calculating distance between points
		double d = distanceBetweenPoints(x1, y1, x2, y2, x3, y3);
		
		//Passing from KM to meters
		d = d*1000;
		
		return d;
		
	}

	static public double distanceBetweenPoints(double x1, double y1, double x2, double y2, double x3, double y3) {		
				
		//Calculating Slope
		double a = calculateSlope(x1, y1, x2, y2);
		
		//Calculating Y-intercept
		double b = calculateYIntercept (a, x1, y1);	
		
		//Calculating Orthogonal parameters
		double orthA = calculateOrthogonalSlope(a);
		double orthB = calculateOrthogonalYIntercept(orthA, x3, y3);
		double x4 = calculateOrthogonalX(a, orthA, b, orthB);
		double y4 = calculateOrthogonalY(a, b, x4);
		
		//Calculating distance using haversine
		double d = haversine(x3,y3,x4,y4);
		return d;

	}

	static public double calculateSlope(double x1, double y1, double x2, double y2) {
		
		double b = (y1 - y2);
		
		//You can't divide per 0
		if(b == 0) {
			b = 1;
		}
		
		double a = (x1 - x2)/b;
		return a;
		
	}
	
	static public double calculateYIntercept(double a, double x1, double y1) {
		
		double b = y1 - (a * x1);
		return b;
	}
	
	//Orthogonal Parameters
	static public double calculateOrthogonalSlope(double a) {
		
		double orthA = -1/a;
		return orthA;
		
	}
	
	static private double calculateOrthogonalYIntercept(double orthA, double x3, double y3) {
		
		double orthB = y3 + (orthA * x3);
		return orthB;
		
	}
	
	static private double calculateOrthogonalX(double a, double orthA, double b, double orthB) {
		
		double x4 = (orthB - b)/(a + orthA);
		return x4;
	
	}
	
	static private double calculateOrthogonalY(double a, double b, double x4) {
		
		double y4 = (a * x4) + b;
		return y4;
		
	}
	
	//Calculating distance between coordinates
	static public double haversine(double x1, double y1, double x2, double y2) {
    	
		//Getting the Earth's distance in radians
		double R = 6372.8; 
		
		//Getting the distance between the points in radians
		double dLat =  Math.toRadians(x2 - x1);
		double dLon =  Math.toRadians(y2 - y1);
		
		//Getting the points in radians
		x1 =  Math.toRadians(x1);
		x2 =  Math.toRadians(x2);
		
		//Calculating
		double a =  (Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(x1) * Math.cos(x2));
		double c =  (2 * Math.asin(Math.sqrt(a)));
		return R * c;
		
	}
		
}
	
	
	
	

	
	
	

