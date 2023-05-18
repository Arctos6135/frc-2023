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
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.CANBus;
import frc.robot.constants.DriveConstants;

public class Drivetrain extends SubsystemBase {
    public enum State {
        HumanDriven("HumanDriven"),
        PathFollowing("PathFollowing");

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
    private final ADXRS450_Gyro gyroscope = new ADXRS450_Gyro();

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
    }

    @Override
    public void periodic() {
        rotationEstimateWidget.setDouble(getRotation());
        translationEstimateWidget.setDouble(getPosition());

        if (state == State.HumanDriven) {
            humanDrivenPeriodic();
        } else if (state == State.PathFollowing) {
            pathFollowingPeriodic();
        }
    }

    private void humanDrivenPeriodic() {
        double translation = translationLimiter.calculate(targetTranslation);
        double rotation = rotationLimiter.calculate(targetRotation);

        double left = (translation + rotation);
        double right = (translation - rotation);

        setMotors(left, right);
    }

    private void pathFollowingPeriodic() {
        double translation = translationalController.calculate(getPosition());
        double rotation = rotationController.calculate(getRotation());

        double left = (translation + rotation);
        double right = (translation - rotation);

        setMotors(left, right);
    }

    public void arcadeDrive(double translation, double rotation) {
        if (state != State.HumanDriven) {
            System.out.printf("Changing state from %s to HumanDriven\n", state.name);
            state = State.HumanDriven;
        }

        targetTranslation = translation;
        targetRotation = rotation;
    }

    public void navigateTo(double translation, double rotation) {
        if (state != State.PathFollowing) {
            System.out.printf("Changing state from %s to PathFollowing\n", state.name);
            state = State.PathFollowing;
        }

        translationalController.setSetpoint(translation);
        rotationController.setSetpoint(rotation);
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

    // get the pitch of the robot in radians, 0 is perfectly balanced
    public double getPitch() {
        return gyroscope.getAngle() * Math.PI / 180;
    }

    // get the rate of change of the pitch of the robot in radians per second (this might be wrong), 0 is not moving
    public double getPitchRate() {
        return gyroscope.getRate() * Math.PI / 180;
    }
}