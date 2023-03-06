package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.DriveConstants;

public class Drivetrain extends SubsystemBase {
    private CANSparkMax rightMaster;
    private CANSparkMax leftMaster;
    private CANSparkMax rightFollower;
    private CANSparkMax leftFollower;

    private SparkMaxPIDController rightController; 
    private SparkMaxPIDController leftController; 

    public static double kP = 0.00025;
    public static double kI = 0.000001; 
    public static double kD = 0.0150; 
    public static double kFF = 0; 

    private RelativeEncoder rightEncoder; 
    private RelativeEncoder leftEncoder; 

    public Drivetrain(int rightMaster, int leftMaster, int rightFollower, int leftFollower) {
        this.rightMaster = new CANSparkMax(rightMaster, MotorType.kBrushless);
        this.leftMaster = new CANSparkMax(leftMaster, MotorType.kBrushless);
        this.rightFollower = new CANSparkMax(rightFollower, MotorType.kBrushless);
        this.leftFollower = new CANSparkMax(leftFollower, MotorType.kBrushless);

        this.rightFollower.follow(this.rightMaster);
        this.leftFollower.follow(this.leftMaster);

        this.rightMaster.setInverted(true);

        this.rightController = this.rightMaster.getPIDController(); 
        this.leftController = this.leftMaster.getPIDController(); 

        this.rightController.setP(kP); 
        this.rightController.setI(kI); 
        this.rightController.setD(kD); 
        this.rightController.setFF(kFF);
        this.rightController.setOutputRange(-1.0, 1.0);

        this.leftController.setP(kP); 
        this.leftController.setI(kI); 
        this.leftController.setD(kD);
        this.leftController.setFF(kFF); 
        this.leftController.setOutputRange(-1.0, 1.0);

        this.rightEncoder = this.rightMaster.getEncoder(); 
        this.leftEncoder = this.leftMaster.getEncoder(); 

        this.rightEncoder.setPositionConversionFactor(DriveConstants.POSITION_CONVERSION_FACTOR); 
        this.leftEncoder.setPositionConversionFactor(DriveConstants.POSITION_CONVERSION_FACTOR); 
    }

    public void arcadeDrive(double translation, double rotation, double scalingFactor) {
        double left = (translation + rotation) * scalingFactor;
        double right = (translation - rotation) * scalingFactor;

        setMotors(left, right);
    }

    private void setMotors(double left, double right) {
        this.leftMaster.set(left);
        this.rightMaster.set(right);
    }

    /**
     * Drives a specified distance with PID Control, based on rotations 
     * from the encoder. 
     * @param distance the required distance in inches.
     */
    public void driveDistance(double distance) {
        this.rightController.setReference(distance, CANSparkMax.ControlType.kPosition);
        this.leftController.setReference(distance, CANSparkMax.ControlType.kPosition); 
    }

    public double getPosition() {
        return (this.leftEncoder.getPosition() + this.rightEncoder.getPosition()) / 2; 
    }

    public void resetEncoders() {
        this.rightEncoder.setPosition(0); 
        this.leftEncoder.setPosition(0); 
    }
}
