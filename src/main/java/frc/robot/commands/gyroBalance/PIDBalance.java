package frc.robot.commands.gyroBalance;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

/*
auto is fine

teleop:
- repeatedly breaking things
- add more sensors: limit switches
- put the D in stuPID
- operator routines
- switchable autos
- communication (finger triangle gestures)
- calibrate sensors during auto

training
- focus on robots in general
    - alternate between coding & general robotics
- assume python knowledge
- diversify skillsets
- tell them what FRC is
- personal connections
- getting people debugging code on robot
 */

public class PIDBalance extends CommandBase {
    private final Drivetrain drivetrain;
    private final PIDController controller = new PIDController(0.4, 0, 0);//new PIDController(0.35, 0, 0);//0.0005);

    /** Balance the robot by PIDing on the angle
    */
    public PIDBalance(Drivetrain drivetrain, ShuffleboardTab drivetrainTab) {
        this.drivetrain = drivetrain;

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        System.out.printf("Initializing PID balancer\n");
        drivetrain.setIdleMode(IdleMode.kBrake);
    }

    @Override
    public void execute() {
        double angle = -drivetrain.getPitch();
        double controlledOutput = controller.calculate(angle);
        double clampedOutput = Math.max(-0.2, Math.min(0.2, controlledOutput));

        System.out.printf("Read PID angle %f, calculated %f, clamped to %f\n", angle, controlledOutput, clampedOutput);

        drivetrain.arcadeDrive(clampedOutput, 0);
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("PID balance terminated\n");
    }
}