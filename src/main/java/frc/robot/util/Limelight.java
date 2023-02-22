package frc.robot.util; 

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * A Limelight smart camera.
 */
public class Limelight {

    public static final String STREAM_URL = "http://10.61.35.11:5800";

    /**
     * Whether there are valid targets (0 or 1)
     */
    private NetworkTableEntry tv;
    /**
     * Horizontal Offset From Crosshair To Target (LL1: -27 degrees to 27 degrees |
     * LL2: -29.8 to 29.8 degrees)
     */
    private NetworkTableEntry tx;
    /**
     * Vertical Offset From Crosshair To Target (LL1: -20.5 degrees to 20.5 degrees
     * | LL2: -24.85 to 24.85 degrees)
     */
    private NetworkTableEntry ty;
    /**
     * Target Area (0% of image to 100% of image)
     */
    private NetworkTableEntry ta;
    /**
     * Skew or rotation (-90 degrees to 0 degrees)
     */
    private NetworkTableEntry ts;
    /**
     * The pipeline’s latency contribution (ms) Add at least 11ms for image capture
     * latency.
     */
    private NetworkTableEntry tl;
    /**
     * Sidelength of shortest side of the fitted bounding box (pixels)
     */
    private NetworkTableEntry tshort;
    /**
     * Sidelength of longest side of the fitted bounding box (pixels)
     */
    private NetworkTableEntry tlong;
    /**
     * Horizontal sidelength of the rough bounding box (0 - 320 pixels)
     */
    private NetworkTableEntry thor;
    /**
     * Vertical sidelength of the rough bounding box (0 - 320 pixels)
     */
    private NetworkTableEntry tvert;
    /**
     * True active pipeline index of the camera (0 .. 9)
     */
    private NetworkTableEntry getpipe;
    /**
     * Results of a 3D position solution, 6 numbers: Translation (x,y,y)
     * Rotation(pitch,yaw,roll)
     */
    private NetworkTableEntry camtran;

    /**
     * Sets limelight’s LED state
     * 
     * <ul>
     * <li>0 use the LED Mode set in the current pipeline</li>
     * <li>1 force off</li>
     * <li>2 force blink</li>
     * <li>3 force on</li>
     * </ul>
     */
    private NetworkTableEntry ledMode;
    /**
     * Sets limelight’s operation mode
     * 
     * <ul>
     * <li>0 Vision processor</li>
     * <li>1 Driver Camera (Increases exposure, disables vision processing)</li>
     * </ul>
     */
    private NetworkTableEntry camMode;
    /**
     * Sets limelight's current pipeline
     * 
     * 0 .. 9 Select pipeline 0..9
     */
    private NetworkTableEntry pipeline;
    /**
     * Sets limelight’s streaming mode
     * 
     * <ul>
     * <li>0 Standard - Side-by-side streams if a webcam is attached to
     * Limelight</li>
     * <li>1 PiP Main - The secondary camera stream is placed in the lower-right
     * corner of the primary camera stream</li>
     * <li>2 PiP Secondary - The primary camera stream is placed in the lower-right
     * corner of the secondary camera stream</li>
     * </ul>
     */
    private NetworkTableEntry stream;
    /**
     * Allows users to take snapshots during a match
     * 
     * <ul>
     * <li>0 Stop taking snapshots</li>
     * <li>1 Take two snapshots per second</li>
     * </ul>
     */
    private NetworkTableEntry snapshot;

    /**
     * Get whether the limelight has any valid targets.
     * 
     * @return Whether there are valid targets
     */
    public boolean hasValidTargets() {
        return tv.getNumber(0).intValue() == 1;
    }

    /**
     * Get the horizontal angle offset from the crosshair to the target.
     * 
     * @return The horizontal angle offset (in degrees)
     */
    public double getHorizontalAngle() {
        return tx.getDouble(0);
    }

    /**
     * Get the vertical angle offset from the crosshair to the target.
     * 
     * @return The vertical angle offset (in degrees)
     */
    public double getVerticalAngle() {
        return ty.getDouble(0);
    }

    /**
     * Get the area of the target as a percentage of the total image.
     * 
     * @return The target area
     */
    public double getTargetArea() {
        return ta.getDouble(0);
    }

    /**
     * Get the skew or rotation of the target.
     * 
     * @return The target's skew or rotation
     */
    public double getSkew() {
        return ts.getDouble(0);
    }

    /**
     * Get the pipeline’s latency contribution (ms). Add at least 11ms for image
     * capture latency.
     * 
     * @return The latency
     */
    public double getLatency() {
        return tl.getDouble(0);
    }

    /**
     * Get the slide length of the shortest side of the bounding box.
     * 
     * @return The length of the shortest side of the bounding box
     */
    public double getShortSideLength() {
        return tshort.getDouble(0);
    }

    /**
     * Get the slide length of the longest side of the bounding box.
     * 
     * @return The length of the longest side of the bounding box
     */
    public double getLongSideLength() {
        return tlong.getDouble(0);
    }

    /**
     * Get the horizontal side length of the bounding box.
     * 
     * @return The horizontal side length of the bounding box
     */
    public double getHorizontalSideLength() {
        return thor.getDouble(0);
    }

    /**
     * Get the vertical side length of the bounding box.
     * 
     * @return The vertical side length of the bounding box
     */
    public double getVerticalSideLength() {
        return tvert.getDouble(0);
    }

    /**
     * Get the true active pipeline index of the camera.
     * 
     * @return The true active pipeline index.
     */
    public int getActivePipeline() {
        return getpipe.getNumber(0).intValue();
    }

    /**
     * Get the 3D position translation of the camera with respect to the target.
     * 
     * @return The position translation
     */
    public Vector3D getTranslation() {
        double[] tran = camtran.getDoubleArray(new double[] { 0, 0, 0, 0, 0, 0 });
        Vector3D v = new Vector3D();
        v.x = tran[0];
        v.y = tran[1];
        v.z = tran[2];
        return v;
    }

    /**
     * Get the 3D rotation of the camera with respect to the target.
     * 
     * @return The rotation (in degrees)
     */
    public Rotation3D getRotation() {
        double[] tran = camtran.getDoubleArray(new double[] { 0, 0, 0, 0, 0, 0 });
        Rotation3D r = new Rotation3D();
        r.pitch = tran[3];
        r.yaw = tran[4];
        r.roll = tran[5];
        return r;
    }

    /**
     * Set the mode of the LEDs.
     * 
     * @param mode The LED mode
     */
    public void setLEDMode(LEDMode mode) {
        ledMode.setNumber(mode.value);
    }

    /**
     * Set the operation mode of the Limelight.
     * 
     * @param mode The operation mode
     */
    public void setOperationMode(OperationMode mode) {
        camMode.setNumber(mode.value);
    }

    /**
     * Set the current pipeline.
     * 
     * @param pipeline The pipeline index (0-9)
     * @throws IllegalArgumentException If index out of range
     */
    public void setPipeline(int pipeline) {
        if (pipeline < 0 || pipeline > 9) {
            throw new IllegalArgumentException("Pipeline index out of range!");
        }
        this.pipeline.setNumber(pipeline);
    }

    /**
     * Set the streaming mode.
     * 
     * @param mode The streaming mode
     */
    public void setStreamingMode(StreamingMode mode) {
        stream.setNumber(mode.value);
    }

    /**
     * Turns snapshots on or off.
     * 
     * <p>
     * Snapshots are taken at a rate of 2 per second.
     * </p>
     * 
     * @param on Whether snapshots are on
     */
    public void setSnapshotOn(boolean on) {
        snapshot.setNumber(on ? 1 : 0);
    }

    /**
     * Estimates the distance to the target based on its vertical angle.
     * 
     * @param camHeight    The height of the camera
     * @param targetHeight The height of the target
     * @param camAngle     The angle of the camera (in degrees)
     * @return The estimated distance
     */
    public double estimateDistance(double camHeight, double targetHeight, double camAngle) {
        double targetAngle = Math.toRadians(getVerticalAngle());
        return (targetHeight - camHeight) / Math.tan(Math.toRadians(camAngle) + targetAngle);
    }

    /**
     * A vector in 3d.
     */
    public static class Vector3D {
        public double x;
        public double y;
        public double z;
    }

    /**
     * A rotation in 3d expressed in Euler (Tait-Bryan) angles.
     */
    public static class Rotation3D {
        public double yaw;
        public double pitch;
        public double roll;
    }

    /**
     * The Limelight's LED modes.
     */
    public enum LEDMode {

        /**
         * Use the configuration in the pipeline.
         */
        USE_PIPELINE(0),
        /**
         * Force off.
         */
        OFF(1),
        /**
         * Force blink.
         */
        BLINK(2),
        /**
         * Force on.
         */
        ON(3);

        public final int value;

        LEDMode(int value) {
            this.value = value;
        }
    }

    /**
     * The Limelight's operation modes.
     */
    public enum OperationMode {

        /**
         * Vision processor.
         */
        VISION_PROCESSOR(0),
        /**
         * Driver Camera (Increases exposure, disables vision processing).
         */
        DRIVER_CAMERA(1);

        public final int value;

        OperationMode(int value) {
            this.value = value;
        }
    }

    /**
     * The Limelight's streaming modes.
     */
    public enum StreamingMode {

        /**
         * Side-by-side streams if a webcam is attached to Limelight.
         */
        STANDARD(0),
        /**
         * The secondary camera stream is placed in the lower-right corner of the
         * primary camera stream.
         */
        PiP_MAIN(1),
        /**
         * The primary camera stream is placed in the lower-right corner of the
         * secondary camera stream.
         */
        PiP_SECONDARY(2);

        public final int value;

        StreamingMode(int value) {
            this.value = value;
        }
    }

    /**
     * Creates a new Limelight object.
     */
    public Limelight() {
        this("limelight");
    }

    /**
     * Creates a new Limelight object from a NetworkTables table name.
     */
    public Limelight(String tableName) {
        NetworkTable table = NetworkTableInstance.getDefault().getTable(tableName);
        tv = table.getEntry("tv");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        ts = table.getEntry("ts");
        tl = table.getEntry("tl");
        tshort = table.getEntry("tshort");
        tlong = table.getEntry("tlong");
        thor = table.getEntry("thor");
        tvert = table.getEntry("tvert");
        getpipe = table.getEntry("getpipe");
        camtran = table.getEntry("camtran");

        ledMode = table.getEntry("ledMode");
        camMode = table.getEntry("camMode");
        pipeline = table.getEntry("pipeline");
        stream = table.getEntry("stream");
        snapshot = table.getEntry("snapshot");
    }
}