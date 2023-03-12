package frc.robot.subsystems;

import java.util.Map;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.DriveConstants;

public class Drivetrain extends SubsystemBase {
    private CANSparkMax rightMaster;
    private CANSparkMax leftMaster;
    private CANSparkMax rightFollower;
    private CANSparkMax leftFollower;

    private SparkMaxPIDController rightController; 
    private SparkMaxPIDController leftController; 

    private SimpleWidget kPW;
    private SimpleWidget kIW;
    private SimpleWidget kDW;
    private SimpleWidget kFFW;
    private SimpleWidget rawLeft;
    private SimpleWidget rawRight;

    private SimpleWidget gainWidget;
    private SimpleWidget transTargetW;
    private SimpleWidget rotTargetW;
    private SimpleWidget transCurrentW;
    private SimpleWidget rotCurrentW;
    private SimpleWidget rotLagW;
    private SimpleWidget transLagW;

    private SimpleWidget rightEncoderReading;
    private SimpleWidget leftEncoderReading;

    private SimpleWidget rotationEstimate;
    private SimpleWidget translationEstimate;

    public double transCurrent = 0;
    public double rotCurrent = 0;

    public double transTarget = 0;
    public double rotTarget = 0;

    // for now ignoring these and delegating to the sparkmax pid controllers
    private PIDController translationalController; 
    private PIDController rotationController; 

    // Translational 
    public static double kP = 0.00025;
    public static double kI = 0.000001; 
    public static double kD = 0.0150; 
    public static double kFF = 0; 

    // Rotational 
    public static double rotP = 0.00001; 
    public static double rotI = 0.0; 
    public static double rotD = 0.0; 

    private RelativeEncoder rightEncoder; 
    private RelativeEncoder leftEncoder; 

    public Drivetrain(int rightMaster, int leftMaster, int rightFollower, int leftFollower, ShuffleboardTab driveTab) {
        this.rightMaster = new CANSparkMax(rightMaster, MotorType.kBrushless);
        this.leftMaster = new CANSparkMax(leftMaster, MotorType.kBrushless);
        this.rightFollower = new CANSparkMax(rightFollower, MotorType.kBrushless);
        this.leftFollower = new CANSparkMax(leftFollower, MotorType.kBrushless);

        this.rightFollower.follow(this.rightMaster);
        this.leftFollower.follow(this.leftMaster);

        this.rightMaster.setInverted(true);

        this.leftMaster.setIdleMode(IdleMode.kBrake);
        this.rightMaster.setIdleMode(IdleMode.kBrake);

        this.rightController = this.rightMaster.getPIDController(); 
        this.leftController = this.leftMaster.getPIDController(); 

        this.translationalController = new PIDController(kP, kI, kD); 
        this.rotationController = new PIDController(rotP, rotI, rotD);

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

        kPW = driveTab.add("kP", 0.001).withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Map.of("min", 0.0, "max", 1.0));
        kIW = driveTab.add("kI", 0).withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Map.of("min", 0.0, "max", 0.01));
        kDW = driveTab.add("kD", 0).withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Map.of("min", 0.0, "max", 0.01));
        kFFW = driveTab.add("kFF", 0).withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Map.of("min", 0.0, "max", 0.01));

        rawLeft = driveTab.add("raw left motor speed", 0);
        rawRight = driveTab.add("raw right motor speed", 0);

        gainWidget = driveTab.add("acceleration gain", DriveConstants.DEFAULT_ACCELERATION_GAIN).withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Map.of("min", 0, "max", 0.1));

        transTargetW = driveTab.add("target transation", 0.0);
        rotTargetW = driveTab.add("target rotation", 0.0);
        transCurrentW = driveTab.add("current transation", 0.0);
        rotCurrentW = driveTab.add("current rotation", 0.0);
        transLagW = driveTab.add("translation lag", 0);
        rotLagW = driveTab.add("rotation lag", 0);

        leftEncoderReading = driveTab.add("left encoder (yards)", 0.0);
        rightEncoderReading = driveTab.add("right encoder (yards)", 0.0);

        rotationEstimate = driveTab.add("estimate of rotation (inches)", 0);
        translationEstimate = driveTab.add("estimate of translation (inches)", 0);
    }

    @Override
    public void periodic() {
        leftController.setP(kPW.getEntry().getDouble(0));
        leftController.setI(kIW.getEntry().getDouble(0));
        leftController.setD(kDW.getEntry().getDouble(0));
        leftController.setFF(kFFW.getEntry().getDouble(0));

        rightController.setP(kPW.getEntry().getDouble(0));
        rightController.setI(kIW.getEntry().getDouble(0));
        rightController.setD(kDW.getEntry().getDouble(0));
        rightController.setFF(kFFW.getEntry().getDouble(0));

        leftEncoderReading.getEntry().setDouble(leftEncoder.getPosition() / 36);
        rightEncoderReading.getEntry().setDouble(rightEncoder.getPosition() / 36);

        rotationEstimate.getEntry().setDouble(getRotation());
        translationEstimate.getEntry().setDouble(getPosition());

        transLagW.getEntry().setDouble(transTarget - transCurrent);
        rotLagW.getEntry().setDouble(rotTarget - rotCurrent);


        this.transTargetW.getEntry().setDouble(transTarget);
        this.rotTargetW.getEntry().setDouble(rotTarget);

        this.transCurrentW.getEntry().setDouble(transCurrent);
        this.rotCurrentW.getEntry().setDouble(rotCurrent);

        double gain = gainWidget.getEntry().getDouble(0.01);

        if (transTarget > transCurrent) {
            transCurrent +=  Math.min(transTarget - transCurrent, gain);
        } else {
            transCurrent -= Math.min(transCurrent - transTarget, gain);
        }

        if (rotTarget > rotCurrent) {
            rotCurrent += Math.min(rotTarget - rotCurrent, gain);
        } else {
            rotCurrent -= Math.min(rotCurrent - rotTarget, gain);
        }
    }

    public void arcadeDrive(double translation, double rotation, double scalingFactor) {
        transTarget = translation;
        rotTarget = rotation;
    
        double left = (transCurrent + rotCurrent) * scalingFactor;
        double right = (transCurrent - rotCurrent) * scalingFactor;

        setMotors(left, right);
    }

    public void arcadeDrive(double translation, double rotation) {
        arcadeDrive(translation, rotation, 1.0);
    }

    private void setMotors(double left, double right) {
        this.rawLeft.getEntry().setDouble(left);
        this.rawRight.getEntry().setDouble(right);
        
        this.leftMaster.set(left);
        this.rightMaster.set(right);
    }

    public void setIdleMode(IdleMode idleMode) {
        this.rightMaster.setIdleMode(idleMode);
        this.leftMaster.setIdleMode(idleMode);
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

    public double getRotation() {
        return (this.leftEncoder.getPosition() - this.rightEncoder.getPosition()) / 2;
    }

    public void resetEncoders() {
        this.rightEncoder.setPosition(0); 
        this.leftEncoder.setPosition(0); 
    }

    public PIDController getTranslationalController() {
        return this.translationalController;
    }
    
    public PIDController getRotationController() {
        return this.rotationController; 
    }
}
