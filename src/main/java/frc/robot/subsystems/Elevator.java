package frc.robot.subsystems;

import frc.robot.constants.CANBus;
import frc.robot.constants.ElevatorConstants;
import edu.wpi.first.hal.ThreadsJNI;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.ResourceBundle.Control;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Timer;

public class Elevator extends SubsystemBase {
    private final TalonSRX motor = new TalonSRX(CANBus.ELEVATOR_MOTOR);
    private final DutyCycleEncoder encoder = new DutyCycleEncoder(ElevatorConstants.ELEVATOR_ENCODER);
    private final GenericEntry encoderWidget;
    private final GenericEntry softstopEnabled;

    private double initialAngle = 0;
    private boolean isInitialized = false;

    private final double lowAngle = 0;
    private final double highAngle = 5;

    private double speed = 0;

    public Elevator(ShuffleboardTab armTab) {
        this.motor.setNeutralMode(NeutralMode.Brake);
        // this.elevatorEncoder.setDistancePerRotation(ElevatorConstants.DISTANCE_PER_ROTATION_RADIANS_ELEVATOR);
        this.encoderWidget = armTab.add("Elevator encoder (radians)", 0)
                .withWidget(BuiltInWidgets.kTextView)
                .withSize(1, 1)
                .withPosition(2, 3)
                .getEntry();
        softstopEnabled = armTab.add("Elevator soft stop enabled", true)
            .withWidget(BuiltInWidgets.kToggleButton)
            .withSize(1, 1)
            .withPosition(2, 2)
            .getEntry();
        encoder.reset();
    }

    @Override
    public void periodic() {
        if (!isInitialized && Timer.getFPGATimestamp() > 1) {
            initialAngle = encoder.get();
            isInitialized = true;
        }

        if (!isInitialized)
            return;

        if (0 == 0)
        return;

        if (true) {//!softstopEnabled.getBoolean(true)) {
            System.out.println("Elevator soft stop disabled\n");
            this.motor.set(ControlMode.PercentOutput, speed);
            initialAngle = encoder.get();
            return;
        }

        if (getAngle() > highAngle && speed < 0) {
            System.out.printf("Stopped in software\n");
            this.motor.set(ControlMode.PercentOutput, 0);
        } else if (getAngle() < lowAngle && speed > 0) {
            System.out.printf("Stopped in software at angle %f\n", getAngle());
            this.motor.set(ControlMode.PercentOutput, 0);
        } else {
            this.motor.set(ControlMode.PercentOutput, speed);
        }

        encoderWidget.setDouble(getAngle());
    }

    /**
     * @param armSpeed the power of the motor, in the range [-1, 1]
     */
    public void setMotor(double elevatorSpeed) {
        speed = elevatorSpeed;
    }

    public void stopMotor() {
        System.out.printf("Setting elevator speed to 0\n");
        this.motor.set(ControlMode.PercentOutput, 0);
    }

    public DutyCycleEncoder getEncoder() {
        return encoder;
    }

    public double getAngle() {
        return encoder.get() - initialAngle;
    }
}