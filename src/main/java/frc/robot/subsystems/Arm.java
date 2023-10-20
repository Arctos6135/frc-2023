package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.CANBus;
import frc.robot.constants.ElevatorConstants;
import frc.robot.util.Clamp;

//code for rotating arm controlled by a neo, chain, and sprockets

public class Arm extends SubsystemBase {
    public enum State {
        HumanDriven,
        Automatic
    }

    private State state = State.HumanDriven;
    private final CANSparkMax motor = new CANSparkMax(CANBus.ARM_MOTOR, MotorType.kBrushless);
    private final RelativeEncoder encoder;

    private PIDController controller = new PIDController(0.0001, 0, 0);

    private final GenericEntry encoderOutputWidget;
    private final GenericEntry motorSpeedWidget;
    private final GenericEntry softstopEnabled;

    private boolean isInitialized = false;

    // measured in radians from vertical
    private final float lowAngle = 0.8f; 
    private final float highAngle = 1.6f;
    private final double startAngle = 0.8;

    private double speed = 0;

    /**
     * This is our constructor
     * 
     * @param armMotor can ID of the motor for flipping the arm
     */
    public Arm(ShuffleboardTab armTab) {
        motor.restoreFactoryDefaults();
        motor.setSmartCurrentLimit(30);
        motor.setIdleMode(IdleMode.kBrake);
        motor.setInverted(true); // TODO: maybe?
        encoder = motor.getEncoder();
        
        encoder.setPositionConversionFactor(ArmConstants.RADIANS_PER_ROTATION); // TODO: actually measure constant

        encoderOutputWidget = armTab.add("Arm encoder angle", 0).withWidget(BuiltInWidgets.kTextView)
                .withPosition(3, 1).withSize(1, 1).getEntry();
        motorSpeedWidget = armTab.add("Arm speed", 0).withWidget(BuiltInWidgets.kTextView)
                .withPosition(3, 2).withSize(1, 1).getEntry();

        softstopEnabled = armTab.add("Arm soft stop enabled", true)
            .withWidget(BuiltInWidgets.kToggleButton)
            .withPosition(3, 2)
            .withSize(1, 1)
            .getEntry(); 
    }

    @Override
    public void periodic() {

        if (!isInitialized && Timer.getFPGATimestamp() > 10) {
            REVLibError okay = encoder.setPosition(startAngle);
            if (okay.equals(REVLibError.kOk)) {
                motor.setSoftLimit(SoftLimitDirection.kForward, highAngle); // does this work??? no clue!
                motor.setSoftLimit(SoftLimitDirection.kForward, lowAngle); // same here!
                isInitialized = true;
            } else {
                System.out.printf("Error: tried to set the default encoder position for the arm, but found %s\n", okay);
            }
        }
        
        if (!isInitialized) return;

        System.out.printf("Running with power%f\n", speed);

        if (!softstopEnabled.getBoolean(true)) {
            System.out.println("Arm soft stop disabled\n");
            motor.enableSoftLimit(SoftLimitDirection.kForward, false);
            motor.enableSoftLimit(SoftLimitDirection.kReverse, false);
            this.motor.set(speed);
            REVLibError okay = encoder.setPosition(startAngle);
            if (!okay.equals(REVLibError.kOk)) {
                System.out.printf("Error: tried to set the default encoder position for the arm, but found %s\n", okay);
            }
        } else {
            motor.enableSoftLimit(SoftLimitDirection.kForward, true);
            motor.enableSoftLimit(SoftLimitDirection.kReverse, true);
        }
        /*
        if (encoder.getPosition() > highAngle && speed < 0) {
            System.out.printf("Stopped in software\n");
            this.motor.set(0);
        } else if (encoder.getPosition() < lowAngle && speed > 0) {
            System.out.printf("Stopped in software at angle %f\n", encoder.getPosition());
            this.motor.set(0);
        } else {
         */
        if (state.equals(State.Automatic)) {
            // since the encoder measures in radians from vertical
            double feedforward = Math.sin(encoder.getPosition()) * 0.1; // adjust constant so that the arm holds level
            double control = controller.calculate(encoder.getPosition()) + feedforward;
            control = Clamp.clamp(control, -0.2, 0.2); // make these more generous as necessary
            motor.set(control);
        } else {
            System.out.printf("Angle is %f\n", encoder.getPosition());

            this.motor.set(speed);
        }
        //}

        encoderOutputWidget.setDouble(encoder.getPosition());
        motorSpeedWidget.setDouble(motor.get());
    }

    // Sets speed of motor
    public void setMotor(double armSpeed) {
        setHumanDriveSpeed(armSpeed);
    }

    public void setHumanDriveSpeed(double speed) {
        this.speed = speed;

        if (!state.equals(State.HumanDriven)) {
            System.out.println("Transitioning to human driven mode");
            state = State.HumanDriven;
        }
    }

    public void setAutomaticPosition(double position) {
        this.controller.setSetpoint(position);

        if (!state.equals(State.Automatic)) {
            System.out.println("Transitioning to automatic mode");
            state = State.Automatic;
        }
    }

    public boolean atTarget() {
        return Math.abs(encoder.getPosition() - controller.getSetpoint()) < 1 * Math.PI / 180;
    }

    public void disableBrake() {
        motor.setIdleMode(IdleMode.kCoast);
    }

    public void enableBrake() {
        motor.setIdleMode(IdleMode.kBrake);
    }
}