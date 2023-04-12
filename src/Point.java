import processing.core.PImage;

import java.util.List;
import java.util.Optional;

final class Point
{
    private final int x;
    private final int y;

    public static final String QUAKE_ID = "quake";
    public static final int QUAKE_ACTION_PERIOD = 1100;
    public static final int QUAKE_ANIMATION_PERIOD = 100;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public boolean equals(Object other) {
        return other instanceof Point && ((Point)other).x == this.x
                && ((Point)other).y == this.y;
    }

    public int hashCode() {
        int result = 17;
        result = result * 31 + x;
        result = result * 31 + y;
        return result;
    }

    public static Entity createMinerFull(
            Point position,
            String id,
            int resourceLimit,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new Entity(EntityKind.MINER_FULL, id, position, images,
                resourceLimit, resourceLimit, actionPeriod,
                animationPeriod);
    }

    public static Entity createMinerNotFull(
            Point position,
            String id,
            int resourceLimit,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new Entity(EntityKind.MINER_NOT_FULL, id, position, images,
                resourceLimit, 0, actionPeriod, animationPeriod);
    }

    public static Entity createBlacksmith(
            Point position, String id, List<PImage> images)
    {
        return new Entity(EntityKind.BLACKSMITH, id, position, images, 0, 0, 0,
                0);
    }

    public static Entity createObstacle(Point position, String id,List<PImage> images)
    {
        return new Entity(EntityKind.OBSTACLE, id, position, images, 0, 0, 0,
                0);
    }

    public static Entity createOre(String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Entity(EntityKind.ORE, id, position, images, 0, 0,
                actionPeriod, 0);
    }

    public static Entity createOreBlob(
            Point position, String id,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new Entity(EntityKind.ORE_BLOB, id, position, images, 0, 0,
                actionPeriod, animationPeriod);
    }

    public static Entity createQuake(List<PImage> images, Point position)
    {
        return new Entity(EntityKind.QUAKE, QUAKE_ID, position, images, 0, 0,
                QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
    }

    public static Entity createVein(
            Point position, String id, int actionPeriod, List<PImage> images)
    {
        return new Entity(EntityKind.VEIN, id, position, images, 0, 0,
                actionPeriod, 0);
    }

    public Optional<Entity> nearestEntity(
            List<Entity> entities)
    {
        if (entities.isEmpty()) {
            return Optional.empty();
        }
        else {
            Entity nearest = entities.get(0);
            int nearestDistance = Functions.distanceSquared(nearest.getPosition(), this);

            for (Entity other : entities) {
                int otherDistance = Functions.distanceSquared(other.getPosition(), this);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }

}
