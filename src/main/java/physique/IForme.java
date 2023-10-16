package physique;

import kernel.Objet;

public interface IForme {
    boolean percute(Objet self, Objet other);
    double avoirLong();
    double avoirHaut();

    // https://stackoverflow.com/questions/401847/circle-rectangle-collision-detection-intersection
    static double clamp(double val, double min, double max) {
        if (val <= min)
            return min;
        return Math.min(val, max);
    }

    static boolean percuteCercleRect(Objet oc, Objet or, Cercle c, Rectangle r) {
        double sx = oc.x + c.avoirLong();
        double sy = oc.y + c.avoirHaut();
        double rx1 = or.x;
        double ry1 = or.y;
        double rx2 = rx1 + r.avoirLong();
        double ry2 = ry1 + r.avoirHaut();
        // Find the closest point to the circle within the rectangle
        double closestX = clamp(sx, rx1, rx2);
        double closestY = clamp(sy, ry1, ry2);

        // Calculate the distance between the circle's center and this closest point
        double distanceX = sx - closestX;
        double distanceY = sy - closestY;

        // If the distance is less than the circle's radius, an intersection occurs
        double distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);
        return distanceSquared < Math.pow(c.r, 2);
    }
    static boolean percuteCercleLigne(Objet oc, Objet ol, Cercle c, Ligne l) {
        return doesCircleIntersectLine(oc.x + c.r, oc.y + c.r, c.r, ol.x, ol.y, ol.x + l.l, ol.y + l.h);
    }
    static boolean percuteLigneRect(Objet ol, Objet or, Ligne l, Rectangle r) {
        double lx1 = ol.x;
        double ly1 = ol.y;
        double rx1 = or.x;
        double ry1 = or.y;
        double lx2 = lx1 + l.avoirLong();
        double ly2 = ly1 + l.avoirHaut();
        double rx2 = rx1 + r.avoirLong();
        double ry2 = ry1 + r.avoirHaut();
        return (lx1 >= rx1 && ly1 >= ry1 && lx1 <= rx2 && ly1 <= ry2)
                || percuteLigneLigne(lx1, ly1, rx1, ry1, lx2, ly2, rx1, ry2)
                || percuteLigneLigne(lx1, ly1, rx1, ry2, lx2, ly2, rx2, ry2)
                || percuteLigneLigne(lx1, ly1, rx2, ry2, lx2, ly2, rx2, ry1)
                || percuteLigneLigne(lx1, ly1, rx2, ry1, lx2, ly2, rx1, ry1);
    }
    static boolean percuteLigneLigne(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        // http://www.java2s.com/example/java/java.lang/this-will-check-if-two-lines-collide.html
        double denom = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);

        if (denom == 0.0)
            return false;

        double ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / denom;
        double ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / denom;
        return ua >= 0.0f && ua <= 1.0f && ub >= 0.0f && ub <= 1.0f;
    }

    // http://www.java2s.com/example/java/java.lang/intersection-between-line-determined-for-two-points-and-a-circle-wit.html
    // https://www.geeksforgeeks.org/check-line-touches-intersects-circle/
    static boolean doesCircleIntersectLine(double x, double y, double radius, double x1, double y1, double x2, double y2) {
        // Calculate the squared distance between the circle center and the line segment
        double distance = squaredDistanceToLineSegment(x, y, x1, y1, x2, y2);

        // Check if the squared distance is less than or equal to the square of the radius
        return distance <= (radius * radius);
    }

    static double squaredDistanceToLineSegment(double x, double y, double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;

        // Check if the line segment is a point (zero length)
        if (dx == 0 && dy == 0) {
            double dxToPoint = x - x1;
            double dyToPoint = y - y1;
            return dxToPoint * dxToPoint + dyToPoint * dyToPoint;
        }

        // Calculate the parametric value t for the closest point on the line segment
        double t = ((x - x1) * dx + (y - y1) * dy) / (dx * dx + dy * dy);

        // Clamp t to the range [0, 1]
        t = Math.max(0, Math.min(1, t));

        // Calculate the coordinates of the closest point on the line segment
        double closestX = x1 + t * dx;
        double closestY = y1 + t * dy;

        // Calculate the squared distance from the circle center to the closest point on the line
        double dxToClosest = x - closestX;
        double dyToClosest = y - closestY;
        return dxToClosest * dxToClosest + dyToClosest * dyToClosest;
    }
}
