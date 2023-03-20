package frc.robot.subsystems;

import frc.robot.constants.ElevatorConstants;
import edu.wpi.first.hal.ThreadsJNI;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DutyCycleEncoder;

public class Elevator extends SubsystemBase {
    private final TalonSRX elevatorMotor;
    private final DutyCycleEncoder elevatorEncoder;
    private final double initialAngle;
    // Nick told me to do this
    // private final DigitalInput limitSwitch = new
    // DigitalInput(ElevatorConstants.ELEVATOR_LIMIT_SWITCH_PORT);
    // private final GenericEntry limitSwitchOutput;

    public Elevator(int elevatorMotorId, int elevatorEncoderPort) { // removed argument: ShuffleboardTab armTab
        this.elevatorMotor = new TalonSRX(elevatorMotorId);
        this.elevatorMotor.setNeutralMode(NeutralMode.Brake);

        this.elevatorEncoder = new DutyCycleEncoder(elevatorEncoderPort);
        this.elevatorEncoder.setDistancePerRotation(ElevatorConstants.DISTANCE_PER_ROTATION_RADIANS_ELEVATOR);

        initialAngle = 0;

        // limitSwitchOutput = armTab.add("Limit switch on elevator", false)
        // .withWidget(BuiltInWidgets.kBooleanBox).withSize(1, 1).withPosition(3,
        // 3).getEntry();
    }

    // @Override
    // public void periodic() {
    // limitSwitchOutput.setBoolean(limitSwitch.get());
    // }

    /**
     * @param armSpeed the power of the motor, in the range [-1, 1]
     */
    public void setMotor(double elevatorSpeed) {
        // if (!limitSwitch.get()) {
        // this.elevatorMotor.set(ControlMode.PercentOutput, elevatorSpeed);
        // } else {
        // System.out.printf("hard stop on elevator reached\n");
        // this.elevatorMotor.set(ControlMode.PercentOutput, -0.5 * elevatorSpeed);
        // }

        elevatorSpeed *= ElevatorConstants.SPEED_FACTOR;

        if (this.getAngle() >= ElevatorConstants.ENCODER_ANGLE_HIGHEST
                || this.getAngle() <= ElevatorConstants.ENCODER_ANGLE_LOWEST) {
            elevatorSpeed = -elevatorSpeed;
        }

        this.elevatorMotor.set(ControlMode.PercentOutput, elevatorSpeed);
    }

    public double getAngle() {
        return this.initialAngle + this.elevatorEncoder.getDistance();
    }

    public void resetEncoder() {
        this.elevatorEncoder.reset();
    }

    public DutyCycleEncoder getEncoder() {
        return this.elevatorEncoder;
    }

    public void stopMotor() {
        this.elevatorMotor.set(ControlMode.PercentOutput, 0);
    }
}