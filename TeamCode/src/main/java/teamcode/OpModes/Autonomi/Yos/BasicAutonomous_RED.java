package teamcode.OpModes.Autonomi.Yos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import teamcode.Objects.BananaFruit;
import teamcode.Objects.DriveTrain;
import teamcode.Objects.Intake;
import teamcode.Objects.Tool.AprilTag;


@Autonomous(name ="TESTING NEW AUTO For Red Team")
public class BasicAutonomous_RED extends LinearOpMode {

    DriveTrain driveTrain;
    Intake intake;
    AprilTag aprilTag = new AprilTag();

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

        //First we start against the goal
        //Then we move back some to see the goal tag
        driveTrain.moveForwardsBy(telemetry, -42);
        Thread.sleep(1000);
        telemetry.addLine("We Moved!");
        //Now we scan
        aprilTag.update();
        AprilTagDetection goalTag = aprilTag.getSpecificTag(24);
        Thread.sleep(1000);
        aprilTag.STOP();
        if (goalTag == null) {
            telemetry.addLine("WOMP WOMP WOMP");
            Thread.sleep(10000);
        } else {
            telemetry.addLine("Camera successfully scanned!");
            //calcing the corrections
            double ycorrection = -(LAUNCH_DIST - goalTag.ftcPose.y);
            //move by that correction
            if (ycorrection > 1) {
                driveTrain.moveForwardsBy(telemetry, ycorrection);
            } else if (ycorrection < 1) {
                driveTrain.moveForwardsBy(telemetry, ycorrection);
            }
            Thread.sleep(100);
            if (goalTag.ftcPose.x > 1) {
                driveTrain.StrafeRightBy(telemetry, goalTag.ftcPose.x);
            } else if (goalTag.ftcPose.x < -1) {
                driveTrain.StrafeLeftBy(telemetry, goalTag.ftcPose.x);
            }
            Thread.sleep(100);
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
        }






    }
}