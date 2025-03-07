package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase{
  private static final int FrontLeftChannel = 0;
  private static final int RearLeftChannel = 1;
  private static final int FrontRightChannel = 2;
  private static final int RearRightChannel = 3;

  private ADXRS450_Gyro gyro;
  private MecanumDrive robotDrive;

  public Drivetrain(){
    PWMSparkMax frontleft = new PWMSparkMax(FrontLeftChannel);
    PWMSparkMax rearleft = new PWMSparkMax(RearLeftChannel);
    PWMSparkMax frontright = new PWMSparkMax(FrontRightChannel);
    PWMSparkMax rearright = new PWMSparkMax(RearRightChannel);

    frontright.setInverted(true);
    rearright.setInverted(true);

    robotDrive = new MecanumDrive(frontleft, rearleft, frontright, rearright);

    gyro.calibrate();
  }

  public void drive(double xSpeed, double ySpeed, double zRotation, boolean fieldOriented){
    if(fieldOriented){
      double angle = gyro.getAngle();
      robotDrive.driveCartesian(xSpeed, ySpeed, zRotation, Rotation2d.fromDegrees(angle));
    }
    else{
      robotDrive.driveCartesian(xSpeed, ySpeed, zRotation);
    }
  }

  public void resetGyro(){
    gyro.reset();
  }

  public double getGyroAngle(){
    return gyro.getAngle();
  }
}
