public class NBody {
    public static void main(String[] args){
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        In reader = new In(filename);
        int numPlanets = reader.readInt();
        double universeRadius = reader.readDouble();
        Planet[] planets = new Planet[numPlanets];
        for (int i = 0; i < numPlanets; i++){
            planets[i] = getPlanet(reader);
        }
        StdDraw.setScale(-universeRadius, universeRadius);
        StdDraw.picture(0,0, "./images/starfield.jpg");
        for (Planet planet : planets){
            planet.draw();
        }
        for (double t = 0; t < T; t += dt){
            for (Planet planet : planets){
                planet.setNetForce(planets);
            }
            for (Planet planet : planets){
                planet.update(dt);
            }
            StdDraw.picture(0,0, "./images/starfield.jpg");
            for (Planet planet : planets){
                planet.draw();
            }
            StdDraw.show(3);
        }

        StdOut.printf("%d\n", numPlanets);
        StdOut.printf("%.2e\n", universeRadius);
        for (Planet planet : planets){
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planet.x, 
                    planet.y,
                    planet.xVelocity,
                    planet.yVelocity,
                    planet.mass,
                    planet.img);
        }
    }

    public static Planet getPlanet(In reader){
        return new Planet(reader.readDouble(),
                          reader.readDouble(),
                          reader.readDouble(),
                          reader.readDouble(),
                          reader.readDouble(),
                          reader.readString());
    }
}
