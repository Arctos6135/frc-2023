package frc.robot.commands.elevator;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.Arm;

public class AutoRotate extends CommandBase {
    private final Arm arm;
    
    public static double kP = 0.00001;
    public static double kI = 0;
    public static double kD = 0; 

    private final PIDController rotationController;

    private final GenericEntry angleEntry;
    private final GenericEntry percentHoldEntry;
    private final GenericEntry feedforwardEntry;
    private final GenericEntry feedbackEntry;
    private final GenericEntry controlEntry;
    

    /**
     * Autonomously rotates the arm to a set position for scoring. 
     * 
     * @param arm
     * @param angle in radians. 
     */
    public AutoRotate(Arm arm, double setpointAngle) {
        this.arm = arm; 

        this.rotationController = new PIDController(kP, kI, kD);
        this.rotationController.setSetpoint(setpointAngle); 
        
        ShuffleboardTab armTab = Shuffleboard.getTab("Arm");
        
        armTab.add("Pid Controller", this.rotationController).withWidget(BuiltInWidgets.kPIDController);
        this.angleEntry = armTab.add("Angle", 0).getEntry();
        this.percentHoldEntry = armTab.add("Percent Hold", 0).getEntry();
        this.feedforwardEntry = armTab.add("Feedforward", 0).getEntry();
        this.feedbackEntry = armTab.add("Feedback", 0).getEntry();
        this.controlEntry = armTab.add("Control", 0).getEntry();
        
        addRequirements(arm); 
    }

    @Override
    public void execute() {
        double angle = this.arm.getAngle();
        double percentHold = Math.sin(angle);
        double feedforward = percentHold * ElevatorConstants.HOLD_FACTOR;
        double feedback = rotationController.calculate(angle) * ElevatorConstants.ARM_SPEED_FACTOR;
        double control = feedforward + feedback;

        this.angleEntry.setDouble(angle);
        this.percentHoldEntry.setDouble(percentHold);
        this.feedforwardEntry.setDouble(feedforward);
        this.feedbackEntry.setDouble(feedback);
        this.controlEntry.setDouble(control);

        //this.arm.setMotor(control);
    }

    @Override 
    public boolean isFinished() {
        return this.rotationController.atSetpoint();
    }
}
