package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Claw{
    private boolean isOpen;
    private WPI_VictorSPX snowMotor;
}
public Claw(int deviceNumber){
    // need motor id (and name of motor would be helpful)
    this.snowMotor = new WPI_VictorSPX(deviceNumber);
    isOpen = false;
}
public void close(){
    if (isOpen){
    snowMotor.disable(); // Not sure what this does but it probably cuts voltage to motor.
    isOpen = false;
    }
}
public void open(){
    if(!isOpen){
        snowMotor.set(ControlMode PercentOutput, 0.5); // 0.5 is test value. Will (most likley) change after testing
        isOpen = true;
    }
public void stopOpen(){
    // Needs more code once able. Will interact with motor limiter part.(unfinished)
    snowMotor.stopMotor
}
}
