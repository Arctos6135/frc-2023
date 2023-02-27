package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.util.Units;
import frc.robot.constants.VisionConstants;

public class VisionSystem {
    // Front Vision (Arm, Placing Game Pieces)
    private PhotonCamera limelight; 

    // Back Vision (Tracking Game Pieces)
    private PhotonCamera backCamera; 

    public VisionSystem() {
        this.limelight = new PhotonCamera("Limelight"); 
        this.backCamera = new PhotonCamera("Back Camera"); 
    }

    public PhotonPipelineResult getLimelightResult() {
        return this.limelight.getLatestResult(); 
    }

    public PhotonPipelineResult getBackCameraResult() {
        return this.backCamera.getLatestResult();
    }

    public boolean limelightTargets() {
        return getLimelightResult().hasTargets(); 
    }

    public boolean backCameraTargets() {
        return getBackCameraResult().hasTargets(); 
    }

    public PhotonTrackedTarget getLimelightTarget() {
        return getLimelightResult().getBestTarget();
    }
    
    public PhotonTrackedTarget getBackCameraTarget() {
        return getLimelightResult().getBestTarget(); 
    }

    public double calculateRange(PhotonCamera camera) {
        return PhotonUtils.calculateDistanceToTargetMeters(
            VisionConstants.CAMERA_HEIGHT, 
            VisionConstants.TARGET_HEIGHT, 
            VisionConstants.CAMERA_PITCH_RADIANS, 
            Units.degreesToRadians(camera.getLatestResult().getBestTarget().getPitch())
        ); 
    }
}
