package frc.robot.subsystems;

//import com.studica.frc.AHRS;
//import com.studica.frc.AHRS.NavXComType;

//import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.math.geometry.Rotation2d;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Drivetrain extends SubsystemBase
{
  private XboxController controller;

  private static final int FrontLeftChannel = 0;
  private static final int RearLeftChannel = 1;
  private static final int FrontRightChannel = 2;
  private static final int RearRightChannel = 3;

  //private ADXRS450_Gyro gyro;
  private AHRS gyro;
  private MecanumDrive robotDrive;

  public Drivetrain()
  {
    PWMSparkMax frontleft = new PWMSparkMax(FrontLeftChannel);
    PWMSparkMax rearleft = new PWMSparkMax(RearLeftChannel);
    PWMSparkMax frontright = new PWMSparkMax(FrontRightChannel);
    PWMSparkMax rearright = new PWMSparkMax(RearRightChannel);

    //gyro = new ADXRS450_Gyro();
    gyro = new AHRS(SPI.Port.kMXP);

    frontright.setInverted(true);
    rearright.setInverted(true);

    SendableRegistry.addChild(robotDrive, frontleft);
    SendableRegistry.addChild(robotDrive, rearleft);
    SendableRegistry.addChild(robotDrive, frontright);
    SendableRegistry.addChild(robotDrive, rearright);

    robotDrive = new MecanumDrive(frontleft, rearleft, frontright, rearright);

    //gyro.calibrate();
  }

  public void drive(double xSpeed, double ySpeed, double zRotation, boolean fieldOriented)
  {
    if(fieldOriented)
    {
      double angle = gyro.getAngle();
      robotDrive.driveCartesian(xSpeed, ySpeed, zRotation, Rotation2d.fromDegrees(angle));
    }
    else
    {
      robotDrive.driveCartesian(xSpeed, ySpeed, zRotation);
    }
  }

  public void resetGyro()
  {
    if (controller.getBackButtonPressed())
    {
      gyro.reset();
      //gyro.calibrate();
    }
  }

  public double getGyroAngle()
  {
    return gyro.getAngle();
  }
}