package model;

public class District extends Cluster {

    public District(Cluster c) {
        super(c);
    }

    public double getPerimeter() {//TODO
        return -1.0;
    }

    public double getArea() {
        double area = 0;
        for(Precinct p : getPrecinctSet()) {
            area += p.getArea();
        }
        return area;
    }

    public double getBoundingCircleArea() {//TODO
        return -1.0;
    }

    public double getConvexHull() {//TODO
        return -1.0;
    }

    public int wastedDemVotes() {//TODO
        return -1;
    }

    public int wastedRepVotes() {//TODO
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("District: ");
        for(Precinct p : getPrecinctSet()) {
            sb.append(p);
            sb.append(",");
        }

        return sb.toString();
    }
}
