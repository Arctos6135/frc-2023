package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.CANBus;
import frc.robot.constants.DriveConstants;
import frc.robot.util.Odometer;

public class Drivetrain extends SubsystemBase {
    public enum State {
        HumanDriven("HumanDriven"),
        AutoNavigating("AutoNavigating");

        public String name;

        private State(String name) {
            this.name = name;
        }
    }

    private State state = State.HumanDriven;

    private final CANSparkMax rightMaster = new CANSparkMax(CANBus.RIGHT_MASTER, MotorType.kBrushless);
    private final CANSparkMax leftMaster = new CANSparkMax(CANBus.LEFT_MASTER, MotorType.kBrushless);
    private final CANSparkMax rightFollower = new CANSparkMax(CANBus.RIGHT_FOLLOWER, MotorType.kBrushless);
    private final CANSparkMax leftFollower = new CANSparkMax(CANBus.LEFT_FOLLOWER, MotorType.kBrushless);

    private final GenericEntry rotationEstimateWidget;
    private final GenericEntry translationEstimateWidget;
    private final GenericEntry xPositionWidget;
    private final GenericEntry yPositionWidget;
    
    // Rate limiting on drivetrain while in HumanDriven mode
    private SlewRateLimiter translationLimiter = new SlewRateLimiter(3);
    private SlewRateLimiter rotationLimiter = new SlewRateLimiter(2);

    private double targetTranslation = 0;
    private double targetRotation = 0;

    // PID control on drivetrain while path following
    public final PIDController translationalController = new PIDController(0.00001, 0, 0);
    public final PIDController rotationController = new PIDController(0.00001, 0, 0);

    // Encoders
    private final RelativeEncoder rightEncoder;
    private final RelativeEncoder leftEncoder;

    // Gyro
    private final ADIS16470_IMU gyroscope = new ADIS16470_IMU();

    // Odometry
    private Odometer odometer;

    public Drivetrain(ShuffleboardTab drivetrainTab) {
        this.rightFollower.follow(this.rightMaster);
        this.leftFollower.follow(this.leftMaster);

        this.rightMaster.setInverted(true);

        this.leftMaster.setIdleMode(IdleMode.kBrake);
        this.rightMaster.setIdleMode(IdleMode.kBrake);

        this.rightEncoder = this.rightMaster.getEncoder();
        this.leftEncoder = this.leftMaster.getEncoder();

        this.rightEncoder.setPositionConversionFactor(DriveConstants.POSITION_CONVERSION_FACTOR);
        this.leftEncoder.setPositionConversionFactor(DriveConstants.POSITION_CONVERSION_FACTOR);

        gyroscope.calibrate();

        odometer = new Odometer(leftEncoder.getPosition(), rightEncoder.getPosition(), getYaw());

        drivetrainTab.add("Left Master", this.leftMaster).withWidget(BuiltInWidgets.kMotorController)
            .withPosition(0, 0).withSize(1, 2);

        drivetrainTab.add("Left Follower", this.leftFollower).withWidget(BuiltInWidgets.kMotorController)
            .withPosition(1, 0).withSize(1, 2);

        drivetrainTab.add("Right Master", this.rightMaster).withWidget(BuiltInWidgets.kMotorController)
            .withPosition(2, 0).withSize(1, 2);

        drivetrainTab.add("Right Follower", this.rightFollower).withWidget(BuiltInWidgets.kMotorController)
            .withPosition(3, 0).withSize(1, 2);

        drivetrainTab.add("Left Master", this.leftMaster.getEncoder()).withWidget(BuiltInWidgets.kEncoder)
            .withPosition(0, 2).withSize(1, 1);

        drivetrainTab.add("Left Follower", this.leftFollower.getEncoder()).withWidget(BuiltInWidgets.kEncoder)
            .withPosition(1, 2).withSize(1, 1);

        drivetrainTab.add("Right Master", this.rightMaster.getEncoder()).withWidget(BuiltInWidgets.kEncoder)
            .withPosition(2, 2).withSize(1, 1);

        drivetrainTab.add("Right Follower", this.rightFollower.getEncoder()).withWidget(BuiltInWidgets.kEncoder)
            .withPosition(3, 2).withSize(1, 1);

        drivetrainTab.add("Gyroscope", this.gyroscope).withWidget(BuiltInWidgets.kGyro)
            .withPosition(0, 3).withSize(2, 2);

        translationEstimateWidget = drivetrainTab.add("Translation Estimate", 0).withWidget(BuiltInWidgets.kTextView)
            .withPosition(2, 3).withSize(1, 1).getEntry();

        rotationEstimateWidget = drivetrainTab.add("Rotation Estimate", 0).withWidget(BuiltInWidgets.kTextView)
            .withPosition(2, 4).withSize(1, 1).getEntry();

        xPositionWidget = drivetrainTab.add("X Position", 0).withWidget(BuiltInWidgets.kTextView)
            .withPosition(3, 3).withSize(1, 1).getEntry();
        
        yPositionWidget = drivetrainTab.add("Y Position", 0).withWidget(BuiltInWidgets.kTextView)
            .withPosition(3, 3).withSize(1, 1).getEntry();
    }

    @Override
    public void periodic() {
        rotationEstimateWidget.setDouble(getRotation());
        translationEstimateWidget.setDouble(getPosition());
        xPositionWidget.setDouble(odometer.getX());
        yPositionWidget.setDouble(odometer.getY());

        if (state == State.HumanDriven) {
            humanDrivenPeriodic();
        }

        odometer.update(leftEncoder.getPosition(), rightEncoder.getPosition(), getYaw());
    }

    private void humanDrivenPeriodic() {
        double translation = translationLimiter.calculate(targetTranslation);
        double rotation = rotationLimiter.calculate(targetRotation);

        double left = (translation + rotation);
        double right = (translation - rotation);

        setMotors(left, right);
    }

    public void arcadeDrive(double translation, double rotation) {
        if (state != State.HumanDriven) {
            if (Math.abs(translation) > 0.1 || Math.abs(rotation) > 0.1) {
                DriverStation.reportWarning(String.format("Not applying controller input to drivetrain because the robot is in %s mode", state.name), false);
            }
        } else {
            targetTranslation = translation;
            targetRotation = rotation;
        }
    }

    // reset encoders and gyro and enter autonomous navigation mode
    public void startNavigation() {
        if (state == State.AutoNavigating) {
            DriverStation.reportError("RACE CONDITION: tried to start autonomous navigation when it had already been started", false);
        }

        System.out.printf("Changing state from %s to AutoNavigating\n", state.name);
        state = State.AutoNavigating;

        resetEncoders();
        resetGyro();
        odometer = new Odometer(leftEncoder.getPosition(), rightEncoder.getPosition(), getYaw());
    }

    // reset encoders and gyro and enter human driven mode
    public void endNavigation() {
        if (state != State.AutoNavigating) {
            DriverStation.reportError("RACE CONDITION: tried to end autonomous navigation when it had already been ended", false);
        }

        System.out.printf("Changing state from AutoNavigating to HumanDriven\n");
        state = State.HumanDriven;

        resetEncoders();
        resetGyro();
    }

    // Only to be called during auto navition mode. Left and right are [-1, 1] percent motor powers.
    public void tankDrive(double left, double right) {
        if (state == State.AutoNavigating) {
            setMotors(left, right);
        } else {
            DriverStation.reportError(
                String.format("Calling Drivetrain.tankDrive while robot in %s mode instead of autonomous navigation", state.name), 
                true
            );
        }
    }

    private void setMotors(double left, double right) {
        leftMaster.set(left);
        rightMaster.set(right);
    }

    public void setIdleMode(IdleMode idleMode) {
        rightMaster.setIdleMode(idleMode);
        leftMaster.setIdleMode(idleMode);
    }

    public double getPosition() {
        return (leftEncoder.getPosition() + rightEncoder.getPosition()) / 2;
    }

    public double getRotation() {
        return (leftEncoder.getPosition() - rightEncoder.getPosition()) / 2;
    }

    public void resetEncoders() {
        rightEncoder.setPosition(0);
        leftEncoder.setPosition(0);
    }

    public void resetGyro() {
        if (Math.abs(gyroscope.getAngle()) > 0.5) {
            gyroscope.reset();
        }
    }

    // get the yaw of the robot in radians
    public double getYaw() {
        return gyroscope.getAngle() * Math.PI / 180;
    }

    // get the pitch of the robot in radians, 0 is perfectly balanced
    public double getPitch() {
        return gyroscope.getYComplementaryAngle() * Math.PI / 180;
    }

    // get the rate of change of the pitch of the robot in radians per second (this might be wrong), 0 is not moving
    public double getPitchRate() {
        return gyroscope.getRate() * Math.PI / 180;
    }
}