public class Planet {
    public double x;
    public double y;
    public double xVelocity;
    public double yVelocity;
    public double mass;
    public double xNetForce;
    public double yNetForce;
    public double xAccel;
    public double yAccel;
    public String img;

    public Planet(double x, double y, double xVelocity,
            double yVelocity, double mass, String img){
        this.x = x;
        this.y = y;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.mass = mass;
        this.img = img;
    }

    public double calcDistance(Planet planet){
        double xdiff = x - planet.x; 
        double ydiff = y - planet.y; 
        return Math.sqrt(xdiff*xdiff + ydiff*ydiff);
    }

    public double calcPairwiseForce(Planet planet){
        double r = calcDistance(planet);
        return 6.67e-11 * mass * planet.mass / (r*r);
    }

    public double calcPairwiseForceX(Planet planet){
        return calcPairwiseForce(planet) * (planet.x - x) / calcDistance(planet);
    }

    public double calcPairwiseForceY(Planet planet){
        return calcPairwiseForce(planet) * (planet.y - y) / calcDistance(planet);
    }

    public void setNetForce(Planet[] planets){
        xNetForce = 0;
        yNetForce = 0;
        for(Planet planet: planets){
            if (this == planet){
                continue;
            }
            xNetForce += calcPairwiseForceX(planet);
            yNetForce += calcPairwiseForceY(planet);
        }
    }

    public void draw(){
        StdDraw.picture(x,y, "./images/" + img);
    }

    public void update(double dt){
        xAccel = xNetForce / mass; 
        yAccel = yNetForce / mass; 
        xVelocity += xAccel * dt;
        yVelocity += yAccel * dt;
        x += xVelocity * dt;
        y += yVelocity * dt;
    }

}
