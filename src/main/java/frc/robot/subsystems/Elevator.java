package frc.robot.subsystems;

import frc.robot.constants.CANBus;
import frc.robot.constants.ElevatorConstants;
import edu.wpi.first.hal.ThreadsJNI;
import edu.wpi.first.math.controller.PIDController;
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

    // TODO values need to be tested and adjusted
    public static final double kP = 0.00001;
    public static final double kI = 0;
    public static final double kD = 0;

    private PIDController extensionController;

    // When set to false, .periodic() will try to achieve value set by .setPosition(double position)
    private boolean positionMode = false;

    private double initialAngle = 0;
    private boolean isInitialized = false;

    // TODO these values need to be tested
    private final double lowAngle = 0;
    private final double highAngle = 5;

    private double speed = 0;

    public Elevator(ShuffleboardTab armTab) {
        extensionController = new PIDController(kP, kI, kD);

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

        extensionController.setSetpoint(initialAngle);
    }

    @Override
    public void periodic() {
        if (!isInitialized && Timer.getFPGATimestamp() > 30) {
            initialAngle = encoder.get();
            extensionController.setSetpoint(initialAngle);
            isInitialized = true;
        }

        if (!isInitialized)
            return;
        
        if (!softstopEnabled.getBoolean(true)) {
            System.out.println("Elevator soft stop disabled\n");
            this.motor.set(ControlMode.PercentOutput, speed);
            initialAngle = encoder.get();
            extensionController.setSetpoint(initialAngle);
        }

        if (positionMode) {
            double pid = extensionController.calculate(getAngle());
            setMotor(pid); // Hyper-extension and -contraction will be handled by the code below
        }

        // TODO test the 0.1 as outputValue to ensure there isnt jittering or any issues
        if (getAngle() > highAngle && speed < 0) {
            System.out.printf("Stopped in software\n");
            this.motor.set(ControlMode.PercentOutput, .1);
        } else if (getAngle() < lowAngle && speed > 0) {
            System.out.printf("Stopped in software at angle %f\n", getAngle());
            this.motor.set(ControlMode.PercentOutput, -.1);
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

    public void resetEncoder() {
        encoder.reset();
    }

    /**
     * Use the PID to set the position
     * @param position technically an angle, but since it's called 'position-mode', we're 
     *                  considering it as a position value
     */
    public void setPosition(double position) {
        extensionController.setSetpoint(position);
    }

    public boolean atSetpoint() {
        return extensionController.atSetpoint();
    }

    public double getAngle() {
        return encoder.get() - initialAngle;
    }

    public void setPositionMode(boolean enabled) {
        this.positionMode = enabled;
    }
}