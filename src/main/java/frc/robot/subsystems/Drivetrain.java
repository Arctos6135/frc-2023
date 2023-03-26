package frc.robot.subsystems;

import java.util.Map;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.CANBus;
import frc.robot.constants.DriveConstants;

public class Drivetrain extends SubsystemBase {
    private final CANSparkMax rightMaster = new CANSparkMax(CANBus.RIGHT_MASTER, MotorType.kBrushless);
    private final CANSparkMax leftMaster = new CANSparkMax(CANBus.LEFT_MASTER, MotorType.kBrushless);
    private final CANSparkMax rightFollower = new CANSparkMax(CANBus.RIGHT_FOLLOWER, MotorType.kBrushless);
    private final CANSparkMax leftFollower = new CANSparkMax(CANBus.LEFT_FOLLOWER, MotorType.kBrushless);

    private final GenericEntry rawLeft;
    private final GenericEntry rawRight;
    private final GenericEntry timeToFullSpeedWidget;
    private final GenericEntry rightEncoderReading;
    private final GenericEntry leftEncoderReading;
    private final GenericEntry rotationEstimate;
    private final GenericEntry translationEstimate;
    private final GenericEntry gyroAngle;
    private final ComplexWidget gyroscopeWidget = null;

    // Rate limiting on drivetrain
    private SlewRateLimiter translationLimiter = new SlewRateLimiter(3);
    private SlewRateLimiter rotationLimiter = new SlewRateLimiter(2);

    private double targetTranslation = 0;
    private double targetRotation = 0;

    // using these for autonomous routines, for them to actually do anything, you need to repeatedly call calculate in the `execute` of the command you are making.
    public final PIDController translationalController;
    public final PIDController rotationController;

    // Translational
    private static double kP = 0.00025;
    private static double kI = 0.000001;
    private static double kD = 0.0150;

    // Rotational
    private static double rotP = 0.00001;
    private static double rotI = 0.0;
    private static double rotD = 0.0;

    // Encoders
    private final RelativeEncoder rightEncoder;
    private final RelativeEncoder leftEncoder;

    // Gyro
    private final ADXRS450_Gyro gyroscope = null;// new ADXRS450_Gyro();

    public Drivetrain(ShuffleboardTab drivetrainTab) {
        this.rightFollower.follow(this.rightMaster);
        this.leftFollower.follow(this.leftMaster);

        this.rightMaster.setInverted(true);

        this.leftMaster.setIdleMode(IdleMode.kBrake);
        this.rightMaster.setIdleMode(IdleMode.kBrake);

        this.translationalController = new PIDController(kP, kI, kD);
        this.rotationController = new PIDController(rotP, rotI, rotD);

        this.rightEncoder = this.rightMaster.getEncoder();
        this.leftEncoder = this.leftMaster.getEncoder();

        this.rightEncoder.setPositionConversionFactor(DriveConstants.POSITION_CONVERSION_FACTOR);
        this.leftEncoder.setPositionConversionFactor(DriveConstants.POSITION_CONVERSION_FACTOR);

        rawLeft = drivetrainTab.add("raw left motor speed", 0).getEntry();
        rawRight = drivetrainTab.add("raw right motor speed", 0).getEntry();

        timeToFullSpeedWidget = drivetrainTab.add("acceleration gain", DriveConstants.DEFAULT_ACCELERATION_GAIN)
                .withWidget(BuiltInWidgets.kNumberSlider)
                .withProperties(Map.of("min", 0, "max", 0.1))
                .getEntry();

        leftEncoderReading = drivetrainTab.add("left encoder (yards)", 0.0).getEntry();
        rightEncoderReading = drivetrainTab.add("right encoder (yards)", 0.0).getEntry();

        rotationEstimate = drivetrainTab.add("estimate of rotation (inches)", 0).getEntry();
        translationEstimate = drivetrainTab.add("estimate of translation (inches)", 0).getEntry();

       // gyroscopeWidget = drivetrainTab.add("Gyroscope", this.gyroscope).withWidget(BuiltInWidgets.kGyro);
        gyroAngle = drivetrainTab.add("gyro angle", 0).withWidget(BuiltInWidgets.kNumberBar).getEntry();
    }

    @Override
    public void periodic() {
        leftEncoderReading.setDouble(leftEncoder.getPosition() / 36);
        rightEncoderReading.setDouble(rightEncoder.getPosition() / 36);

        rotationEstimate.setDouble(getRotation());
        translationEstimate.setDouble(getPosition());

        double translation = translationLimiter.calculate(targetTranslation);
        double rotation = rotationLimiter.calculate(targetRotation);

        double left = (translation + rotation);
        double right = (translation - rotation);

        setMotors(left, right);
    }

    public void arcadeDrive(double translation, double rotation) {
        targetTranslation = translation;
        targetRotation = rotation;
    }

    private void setMotors(double left, double right) {
        this.rawLeft.setDouble(left);
        this.rawRight.setDouble(right);

        this.leftMaster.set(left);
        this.rightMaster.set(right);
    }

    public void setIdleMode(IdleMode idleMode) {
        this.rightMaster.setIdleMode(idleMode);
        this.leftMaster.setIdleMode(idleMode);
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

    public double getPitch() {
        return gyroscope.getAngle();
    }

    
    public double getPitchRate() {
        return gyroscope.getRate();
    }
}
