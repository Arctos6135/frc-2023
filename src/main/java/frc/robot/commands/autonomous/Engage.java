package frc.robot.commands.autonomous;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class Engage extends CommandBase {
    private final Drivetrain drivetrain;
    
    private final PIDController autoBalancing;

    public boolean isFinished = false; 

    public static double kP = 0.001;
    public static double kI = 0.000;
    public static double kD = 0.000;
    
    // Radians 
    public static double tolerance = Units.degreesToRadians(5); 

    public Engage(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        
        this.autoBalancing = new PIDController(kP, kI, kD); 

        addRequirements(drivetrain);
    }

    @Override 
    public void initialize() { 
        this.drivetrain.setIdleMode(IdleMode.kBrake);
    }

    @Override 
    public void execute() {
        double current = Units.degreesToRadians(-drivetrain.getGyroscope().getAngle());

        if (Math.abs(current) > tolerance) {
            this.drivetrain.arcadeDrive(this.autoBalancing.calculate(current, 0), 0);
        }
        
        else {
            this.isFinished = true; 
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished; 
    }

    @Override 
    public void end(boolean interrupted) {
        this.drivetrain.arcadeDrive(0, 0);
    }
}
