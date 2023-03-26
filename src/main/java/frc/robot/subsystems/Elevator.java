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
    private final TalonSRX elevatorMotor = new TalonSRX(CANBus.ELEVATOR_MOTOR);
    private final DutyCycleEncoder elevatorEncoder = new DutyCycleEncoder(ElevatorConstants.ELEVATOR_ENCODER);
    private final GenericEntry encoderWidget;
    private double speed = 0;
    private Double initialAngle = null;

    public Elevator(ShuffleboardTab armTab) {
        this.elevatorMotor.setNeutralMode(NeutralMode.Brake);
        // this.elevatorEncoder.setDistancePerRotation(ElevatorConstants.DISTANCE_PER_ROTATION_RADIANS_ELEVATOR);
        this.encoderWidget = armTab.add("Elevator encoder (radians)", 0)
                .withWidget(BuiltInWidgets.kNumberBar)
                .withSize(1, 1)
                .withPosition(2, 3)
                .getEntry();
        elevatorEncoder.reset();
    }

    @Override
    public void periodic() {
        if (elevatorEncoder.isConnected() && initialAngle == null && Timer.getFPGATimestamp() > 5) {
            initialAngle = elevatorEncoder.getAbsolutePosition();
        }
        if (initialAngle == null) return;
        double lowerValue = -10;
        double upperValue = 10;
        if (getAngle() > upperValue && speed > 0) {
            System.out.printf("Stopped in software\n");
        } else if (getAngle() < lowerValue && speed < 0) {
            System.out.printf("Stopped in software\n");
        } else {
            System.out.printf("Setting speed to %f\n", speed);
            this.elevatorMotor.set(ControlMode.PercentOutput, speed);
            System.out.printf("Getting %f raw is %f initial is %f\n", getAngle(), elevatorEncoder.getAbsolutePosition(), initialAngle);
            System.out.println(elevatorEncoder.isConnected());
            encoderWidget.setDouble(elevatorEncoder.get());
        }
    }

    /**
     * @param armSpeed the power of the motor, in the range [-1, 1]
     */
    public void setMotor(double elevatorSpeed) {
        speed = elevatorSpeed;
    }

    public void stopMotor() {
        this.elevatorMotor.set(ControlMode.PercentOutput, 0);
    }

    public DutyCycleEncoder getEncoder() {
        return elevatorEncoder;
    }

    public double getAngle() {
        return elevatorEncoder.getAbsolutePosition() - initialAngle;
    }
}