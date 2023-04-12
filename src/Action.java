final class Action
{
    private final ActionKind kind;
    private final Entity entity;
    private final WorldModel world;
    private final ImageStore imageStore;
    private final int repeatCount;

    public Action(
            ActionKind kind,
            Entity entity,
            WorldModel world,
            ImageStore imageStore,
            int repeatCount)
    {
        this.kind = kind;
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }

    public void executeAction(EventScheduler scheduler) {
        switch (this.kind) {
            case ACTIVITY:
                executeActivityAction(scheduler);
                break;

            case ANIMATION:
                executeAnimationAction(scheduler);
                break;
        }
    }

    public void executeAnimationAction(EventScheduler scheduler)
    {
        this.entity.nextImage();

        if (this.repeatCount != 1) {
            scheduler.scheduleEvent(this.entity, entity.createAnimationAction(
                            Math.max(this.repeatCount - 1,
                                    0)),
                    this.entity.getAnimationPeriod());
        }
    }

    public void executeActivityAction(EventScheduler scheduler)
    {
        switch (this.entity.getKind()) {
            case MINER_FULL:
                entity.executeMinerFullActivity(this.world,
                        this.imageStore, scheduler);
                break;

            case MINER_NOT_FULL:
                entity.executeMinerNotFullActivity(this.world,
                        this.imageStore, scheduler);
                break;

            case ORE:
                entity.executeOreActivity(this.world,
                        this.imageStore, scheduler);
                break;

            case ORE_BLOB:
                entity.executeOreBlobActivity(this.world,
                        this.imageStore, scheduler);
                break;

            case QUAKE:
                entity.executeQuakeActivity(this.world,
                        this.imageStore, scheduler);
                break;

            case VEIN:
                entity.executeVeinActivity(this.world, this.imageStore, scheduler);
                break;

            default:
                throw new UnsupportedOperationException(String.format(
                        "executeActivityAction not supported for %s",
                        this.entity.getKind()));
        }
    }

}
