package frc.robot.commands.autonomous;

import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.VisionConstants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.VisionSystem;

public class AutoAlign extends CommandBase {

    private final Drivetrain drivetrain; 
    private final VisionSystem visionSystem; 
    private boolean tape; 

    public AutoAlign(Drivetrain drivetrain, VisionSystem visionSystem, boolean tape) {
        this.drivetrain = drivetrain; 
        this.visionSystem = visionSystem;
        this.tape = tape; 

        addRequirements(drivetrain);
    }

    @Override 
    public void initialize() {

    }

    @Override 
    public void execute() {
        double translationalSpeed; 
        double rotationSpeed; 

        PhotonPipelineResult result = visionSystem.getLimelightResult();

        if (result.hasTargets()) {
            double range = Units.metersToInches(visionSystem.calculateLimelightRange(this.tape));   

            translationalSpeed = -this.drivetrain.translationalController.calculate(range, VisionConstants.GOAL_RANGE_INCHES);

            rotationSpeed = -this.drivetrain.rotationController.calculate(result.getBestTarget().getYaw(), 0); 
        } else {
            translationalSpeed = 0; 
            rotationSpeed = 0; 
        }

        drivetrain.arcadeDrive(translationalSpeed, rotationSpeed); 
    }

    @Override 
    public boolean isFinished() {
        return false; 
    }

    @Override 
    public void end(boolean interrupted) {
        drivetrain.arcadeDrive(0, 0); 
    }
}