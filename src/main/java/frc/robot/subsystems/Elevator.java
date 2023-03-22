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

public class Elevator extends SubsystemBase {
    private final TalonSRX elevatorMotor = new TalonSRX(CANBus.ELEVATOR_MOTOR);
    private final DutyCycleEncoder elevatorEncoder = new DutyCycleEncoder(ElevatorConstants.ELEVATOR_ENCODER);
    private final GenericEntry encoderWidget;
    private double speed;
    // Nick told me to do this
    // private final DigitalInput limitSwitch = new
    // DigitalInput(ElevatorConstants.ELEVATOR_LIMIT_SWITCH_PORT);
    // private final GenericEntry limitSwitchOutput;

    public Elevator(ShuffleboardTab armTab) {
        this.elevatorMotor.setNeutralMode(NeutralMode.Brake);
        this.elevatorEncoder.setDistancePerRotation(ElevatorConstants.DISTANCE_PER_ROTATION_RADIANS_ELEVATOR);
        this.encoderWidget = armTab.add("Elevator encoder (radians)", 0)
            .withWidget(BuiltInWidgets.kNumberBar)
            .withSize(1, 1)
            .withPosition(2, 3)
            .getEntry();

        // limitSwitchOutput = armTab.add("Limit switch on elevator", false)
        // .withWidget(BuiltInWidgets.kBooleanBox).withSize(1, 1).withPosition(3,
        // 3).getEntry();
    }
    
    @Override
    public void periodic() {
        // limitSwitchOutput.setBoolean(limitSwitch.get());
    }

    /**
     * @param armSpeed the power of the motor, in the range [-1, 1]
     */
    public void setMotor(double elevatorSpeed) {
        System.out.printf("Elevator running at speed %f\n", elevatorSpeed);
        // if (!limitSwitch.get()) {
            this.elevatorMotor.set(ControlMode.PercentOutput, elevatorSpeed);
        // } else {
        //     System.out.printf("hard stop on elevator reached\n");
        //     this.elevatorMotor.set(ControlMode.PercentOutput, -0.5 * elevatorSpeed);
        // }
    }

    public void stopMotor() {
        this.elevatorMotor.set(ControlMode.PercentOutput, 0);
    }
}