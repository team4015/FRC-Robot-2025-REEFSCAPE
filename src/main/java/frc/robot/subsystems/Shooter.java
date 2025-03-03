package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase{
    //Hardware
    private final PWMSparkMax shooterMotor;
    private final DigitalInput limitSwitch;

    public Shooter(){
        shooterMotor = new PWMSparkMax(5); //subject to change
        shooterMotor.setSafetyEnabled(true);

        limitSwitch = new DigitalInput(0); //subject to change

        shooterMotor.set(0.1);
    }

    public void intake(){
        if(limitSwitch.get() == true){
            shooterMotor.set(0);
        }
        else{
            shooterMotor.set(0.8); //motor speed subject to change
        }
    }

    public void stop(){
        shooterMotor.set(0);
    }
}
