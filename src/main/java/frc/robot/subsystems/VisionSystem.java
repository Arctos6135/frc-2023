package frc.robot.subsystems;

import org.photonvision.PhotonCamera;

public class VisionSystem {
    // Front Vision (Arm, Placing Game Pieces)
    private PhotonCamera limelight; 

    // Back Vision (Tracking Game Pieces)
    private PhotonCamera backCamera; 

    public VisionSystem() {
        limelight = new PhotonCamera(null, "Limelight"); 
        backCamera = new PhotonCamera(null, "Back Camera"); 
        
    }
}
