package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.util.Units;
import frc.robot.constants.VisionConstants;

public class VisionSystem {
    // Front Vision (Retroreflective Tape)
    private PhotonCamera limelight; 
    public static final String LIMELIGHT_URL = "http://photonvision.local:5800/"; 

    public VisionSystem() {
        this.limelight = new PhotonCamera("Limelight"); 
    }

    public PhotonPipelineResult getLimelightResult() {
        return this.limelight.getLatestResult(); 
    }

    public boolean limelightTargets() {
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
}
