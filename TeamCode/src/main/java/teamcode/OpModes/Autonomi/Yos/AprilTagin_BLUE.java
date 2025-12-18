package teamcode.OpModes.Autonomi.Yos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import teamcode.Objects.BananaFruit;
import teamcode.Objects.DriveTrain;
import teamcode.Objects.Intake;
import teamcode.Objects.Tool.AprilTag;

@Autonomous(name = "It's Taging Time (Blue)")
public class AprilTagin_BLUE extends LinearOpMode {

    AprilTag aprilTag = new AprilTag();
    DriveTrain driveTrain;
    Intake intake;

    double launchDist = 34;

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

        waitForStart();//MAIN CODE GOES AFTER THIS.

        //move to spot where robot WILL see the goaltag (robot forwards starts against the goal)
        driveTrain.moveForwardsBy(telemetry, -36);
        //update vision pootal
        aprilTag.update();
        //Scan the goal tag
        AprilTagDetection goalTag = aprilTag.getSpecificTag(20);
        if (goalTag == null) {
            telemetry.addLine("YOU FAILED!!!!!!!!!!! WOMP WOMP WOMP WOOOOOOOOMP!");
        } else {
            double perfDist = launchDist - goalTag.ftcPose.y;

            driveTrain.moveForwardsBy(telemetry, -perfDist);
            driveTrain.turnToHeading(gyro, telemetry, goalTag.ftcPose.bearing);

        }

        intake.toggleLuncher();
        intake.toggleRolla();
        Thread.sleep(1000);
        intake.toggleRolla();
        intake.toggleLuncher();
        driveTrain.StrafeLeftBy(telemetry, 18);
        stop();





    }

}
