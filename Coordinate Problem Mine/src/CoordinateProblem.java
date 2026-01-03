import java.util.*;

public class CoordinateProblem {
    private final List<Point> points;
    // Removed unused field: linesThroughPoint

    public CoordinateProblem() {
        points = new ArrayList<>();
        // Removed unused initialization
    }

    public void addPoint(Point p) {
        points.add(p);
    }

    private int gcd(int a, int b) {
        // FIX: Recursion must pass 'b', not 'a'.
        return b == 0 ? a : gcd(b, a % b);
    }

    private String getSlope(Point p1, Point p2) {
        int dx = p2.x - p1.x;
        int dy = p2.y - p1.y;

        int g = gcd(Math.abs(dx), Math.abs(dy));
        if (g != 0) {
            dx /= g;
            dy /= g;
        }

        return dx + ":" + dy;
    }

    // Fixed typo: Colinear -> Collinear
    public boolean areAllPointsCollinear() {
        if (points.size() <= 2) {
            return true;
        }

        Point p1 = points.get(0);
        Point p2 = points.get(1);

        String slope = getSlope(p1, p2);

        for (int i = 2; i < points.size(); i++) {
            if (!getSlope(p1, points.get(i)).equals(slope))
                return false;
        }
        return true;
    }

    public List<Point> getLargestLineThroughPoint(Point p) {
        if (!points.contains(p)) return new ArrayList<>();

        Map<String, List<Point>> slopeMap = new HashMap<>();

        for (Point other : points) {
            if (!other.equals(p)) {
                String slope = getSlope(p, other);
                slopeMap.computeIfAbsent(slope, k -> new ArrayList<>()).add(other);
            }
        }

        List<Point> largestLine = new ArrayList<>();
        for (List<Point> line : slopeMap.values()) {
            if (line.size() > largestLine.size()) {
                largestLine = line;
            }
        }

        largestLine.add(p);

        // IDE Suggestion: Use List.sort instead of Collections.sort
        largestLine.sort((a, b) -> {
            if (a.x != b.x) return a.x - b.x;
            return a.y - b.y;
        });

        return largestLine;
    }

    public List<Point> getPointsBetween(Point p1, Point p2) {
        List<Point> result = new ArrayList<>();
        String slope = getSlope(p1, p2);

        for (Point p : points) {
            if (!p.equals(p1) && !p.equals(p2) && getSlope(p1, p).equals(slope)) {
                if ((p.x >= Math.min(p1.x, p2.x) && p.x <= Math.max(p1.x, p2.x)) &&
                        (p.y >= Math.min(p1.y, p2.y) && p.y <= Math.max(p1.y, p2.y))) {
                    result.add(p);
                }
            }
        }

        // IDE Suggestion: Use List.sort
        result.sort((a, b) -> {
            if (a.x != b.x) return a.x - b.x;
            return a.y - b.y;
        });

        return result;
    }

    public int getLargestLineSize() {
        if (points.size() == 0) return 0;
        if (points.size() == 1) return 1;
        int maxSize = 0;

        for (int i = 0; i < points.size(); i++) {
            Map<String, List<Point>> slopeMap = new HashMap<>();
            for (int j = 0; j < points.size(); j++) {
                if (i != j) {
                    String slope = getSlope(points.get(i), points.get(j));

                    // FIX: Moved .add() outside the lambda.
                    // This fixes "Variable used in lambda..." and the return type error.
                    slopeMap.computeIfAbsent(slope, k -> new ArrayList<>()).add(points.get(j));
                }
            }

            for (List<Point> line : slopeMap.values()) {
                maxSize = Math.max(maxSize, line.size() + 1);
            }
        }

        return maxSize;
    }

    public List<List<Point>> getAllLinesThroughPoint(Point p) {
        if (!points.contains(p)) return new ArrayList<>();

        Map<String, List<Point>> slopeMap = new HashMap<>();

        for (Point other : points) {
            if (!other.equals(p)) {
                String slope = getSlope(p, other);
                slopeMap.computeIfAbsent(slope, k -> new ArrayList<>()).add(other);
            }
        }

        List<List<Point>> result = new ArrayList<>();

        for (List<Point> line : slopeMap.values()) {
            line.add(p);
            // IDE Suggestion: Use List.sort
            line.sort((a, b) -> {
                if (a.x != b.x) return a.x - b.x;
                return a.y - b.y;
            });
            result.add(line);
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoordinateProblem problem = new CoordinateProblem();

        System.out.println("Enter coordinates (x y) separated by spaces, one point per line. Enter 'done' when finished:");

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("done")) break;

            String[] parts = input.split("\\s+");
            if (parts.length != 2) {
                System.out.println("Invalid input. Please enter x and y coordinates separated by space.");
                continue;
            }

            try {
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                problem.addPoint(new Point(x, y));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter valid integers.");
            }
        }

        // 1. Check if all points are collinear
        System.out.println("\n1. Are all points collinear? " + problem.areAllPointsCollinear());

        // 2. Find largest line through a point
        System.out.println("\n2. Enter a point to find the largest line through it (x y):");
        String[] input = scanner.nextLine().split("\\s+");
        Point p = new Point(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
        System.out.println("Points on largest line: " + problem.getLargestLineThroughPoint(p));

        // 3. Find points between two points
        System.out.println("\n3. Enter two points to find points between them (x1 y1 x2 y2):");
        input = scanner.nextLine().split("\\s+");
        Point p1 = new Point(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
        Point p2 = new Point(Integer.parseInt(input[2]), Integer.parseInt(input[3]));
        System.out.println("Points between: " + problem.getPointsBetween(p1, p2));

        // 4. Find size of largest line
        System.out.println("\n4. Size of largest line: " + problem.getLargestLineSize());

        // 5. Find all lines through a point
        System.out.println("\n5. Enter a point to find all lines through it (x y):");
        input = scanner.nextLine().split("\\s+");
        p = new Point(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
        System.out.println("All lines through the point:");
        for (List<Point> line : problem.getAllLinesThroughPoint(p)) {
            System.out.println(line);
        }

        scanner.close();
    }
}