public class Projectile{

    private double launchAngle;
    private double launchVelocity;
    private double positionX;
    private double positionY;
    private double toRadians;

    final double g = 9.8;

    public Projectile(double launchAngle, double launchVelocity){
        this.launchAngle = launchAngle;
        this.toRadians = Math.toRadians(launchAngle);
        this.launchVelocity = launchVelocity;
    }

    public double calculateY(double t){
        double A = (((Math.sin(this.toRadians) * this.launchVelocity) * t) - (0.5 * (g * Math.pow(t,2))));
        this.positionY = A;
        return positionY;
    }

    public double calculateX(double t){
        double V = this.launchVelocity * Math.cos(this.toRadians) * t;
        this.positionX = V;
        return positionX;
    }

    public void setLaunchVelocity(double lV){
        this.launchVelocity = lV;
    }

    public double getRad(){
        return this.toRadians;
    }


    public double calculateAirTime(){
        double airTime = (2 * this.launchVelocity * Math.sin(this.toRadians))/g;
        return airTime;
    }

    public double[] getlocation(double t) {

        double[] instantaneousLocation =  new double[2];
        instantaneousLocation[0] = calculateX(t);
        instantaneousLocation[1] = calculateY(t);
        return instantaneousLocation;
    }




}
