package teamcode.OpModes.Autonomi.Yos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import teamcode.Objects.Tool.BananaFruit;
import teamcode.Objects.DriveTrain;
import teamcode.Objects.Intake;
import teamcode.Objects.Tool.AprilTag;


@Autonomous(name ="Basic auto for blue alliance (It was at this point Marcus Benolirao ran out of ideas to name his programs. But hold on, he's naming this one right now so actually let's just say he's trying out new ideas lololololol) (BLUE WOOHOO)")
public class BasicAutonomous_BLUE extends LinearOpMode {

    AprilTag aprilTag;
    DriveTrain driveTrain;
    Intake intake;

    final double LAUNCH_DIST = 38;

    @Override
    public void runOpMode() throws InterruptedException {

        aprilTag.initAprilTag(hardwareMap, telemetry);
        driveTrain = DriveTrain.initDriveTrain(hardwareMap, DcMotor.ZeroPowerBehavior.BRAKE, telemetry);
        intake = Intake.initGrabber(hardwareMap);

        telemetry.addData("IsBusy", driveTrain.isBusy());
        driveTrain.logTelemetry(telemetry, driveTrain);
        telemetry.update();
        driveTrain.resetEncoders();
        BananaFruit gyro = new BananaFruit();
        gyro.runBananaFruit(hardwareMap, telemetry);
        telemetry.update();


        waitForStart();
        //ONLY MODIFY STUFF AFTER THIS

        //First we start against the wall
        //Then we move back some to see the goal tag
        driveTrain.moveForwardsBy(telemetry, 8);
        Thread.sleep(1000);
        telemetry.addLine("We Moved!");
        //Now we scan
        aprilTag.update();
        AprilTagDetection goalTag = aprilTag.getSpecificTag(20);
        Thread.sleep(1000);
        aprilTag.STOP();
        if (goalTag == null) {
            telemetry.addLine("WOMP WOMP WOMP");
            Thread.sleep(10000);
            driveTrain.StrafeLeftBy(telemetry, 24);
        } else {
            telemetry.addLine("Camera successfully scanned!");
            //calcing the corrections
                        Thread.sleep(500);
            //turn robot so that it's perfectly perpendicular
            driveTrain.turnToHeading(gyro, telemetry, goalTag.ftcPose.bearing);
            Thread.sleep(100);
            //Lunching Begins
            intake.toggleLuncher();
            Thread.sleep(1000);
            //Launch first ball
            intake.toggleRolla();
            Thread.sleep(1000);
            //stop intake to save powah
            intake.toggleRolla();
            Thread.sleep(1000);
            //Launch second AND THIRD ball
            intake.lunchingCycleforAuto();
            Thread.sleep(100);
            driveTrain.turnToHeading(gyro, telemetry, -goalTag.ftcPose.bearing);
            Thread.sleep(200);
            driveTrain.moveForwardsBy(telemetry,-8);
            Thread.sleep(200);
            driveTrain.StrafeLeftBy(telemetry,24);
            }
        }

    }
