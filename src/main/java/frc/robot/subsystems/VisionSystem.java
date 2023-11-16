package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.common.hardware.VisionLEDMode;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.VisionConstants;

public class VisionSystem extends SubsystemBase {
    // Front Vision (Retroreflective Tape)
    private PhotonCamera limelight; 
    public static final String LIMELIGHT_URL = "http://10.61.35.11:5800/";//"http://photonvision.local:5800/"; 

    public VisionSystem() {
        this.limelight = new PhotonCamera("limelight");
    }

    public PhotonPipelineResult getLimelightResult() {
        return this.limelight.getLatestResult(); 
    }

    public boolean hasTargets() {
        limelight.setLED(VisionLEDMode.kOff);
        SmartDashboard.putString("Limelight Mode", limelight.getLEDMode().name());
        return getLimelightResult().hasTargets(); 
    }

    public PhotonTrackedTarget getLimelightTarget() {
        return getLimelightResult().getBestTarget();
    }
    
    public double calculateLimelightRange(boolean tape) {
        return PhotonUtils.calculateDistanceToTargetMeters(
            VisionConstants.CAMERA_HEIGHT_METERS, 
            tape ? VisionConstants.TAPE_HEIGHT_METERS : VisionConstants.APRIL_TAG_HEIGHT_METERS, 
            VisionConstants.CAMERA_PITCH_RADIANS, 
            Units.degreesToRadians(getLimelightResult().getBestTarget().getPitch())
        ); 
    }

    public double getYaw() {
        return getLimelightTarget().getYaw();
    }

    public double getArea() {
        return getLimelightTarget().getArea();
    }

    public boolean hasConnected() {
        return limelight.isConnected();
    }
}
