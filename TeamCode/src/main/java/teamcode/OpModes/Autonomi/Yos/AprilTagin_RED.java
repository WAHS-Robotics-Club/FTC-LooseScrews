package teamcode.OpModes.Autonomi.Yos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import teamcode.Objects.Tool.BananaFruit;
import teamcode.Objects.DriveTrain;
import teamcode.Objects.Intake;
import teamcode.Objects.Tool.AprilTag;

@Autonomous(name = "Evil Advanced Teleop of DOOOM and DEESTRUCTION!!!! (Red)")
public class AprilTagin_RED extends LinearOpMode {

    AprilTag aprilTag = new AprilTag();
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

        waitForStart();//MAIN CODE GOES AFTER THIS.

        //This is the epic auto that can scan the obelisk and score accordingly

        //Here's the plan.
        //First we figure out where we are on the field. I want an auto that works no matter where we start
        //To do that we will look for the obelisk. If the robot can see it, we can assume we are starting in the small triangle.
        //If the robot returns null, we can assume we are starting against the goal.

        Thread.sleep(1000);
        AprilTagDetection obeliskCode = aprilTag.ObeliskScan();
        Thread.sleep(1500);
        aprilTag.STOP();

        if (obeliskCode.metadata == null) {
            //This means we were against the goal. step one: move rotate robot to see Obelisk perpendicularly
            driveTrain.moveForwardsBy(telemetry, -59.5);
            driveTrain.turnToHeading(gyro, telemetry, -63); //perp to tag now
            //obelisk scan again
            Thread.sleep(1000);
            obeliskCode = aprilTag.ObeliskScan();//scan tag
            Thread.sleep(1500);
            aprilTag.STOP();
            Thread.sleep(500);

            // Now we check what tag we got and move accordingly
        if (obeliskCode == null){
            telemetry.addLine("die and cry");
        } else if (obeliskCode.id == 21) {
            //GPP
            Thread.sleep(500);
            driveTrain.moveForwardsBy(telemetry, -45);
        } else if (obeliskCode.id == 22) {
            //PGP
            Thread.sleep(500);
            driveTrain.moveForwardsBy(telemetry, -22);
        } else if (obeliskCode.id == 23){
            Thread.sleep(500);
        } else {
            telemetry.addLine("You made a mistake pal");
        }

        Thread.sleep(500);
        driveTrain.turnToHeading(gyro, telemetry, -90); //Turn towards the ordered artifacts
        Thread.sleep(500);

        // We must now intake the ballz
        intake.toggleRolla();
        Thread.sleep(599);
        driveTrain.moveForwardsBy(telemetry, -53);
        Thread.sleep(700);
        intake.toggleRolla();

        //Now we go to launch!
        driveTrain.moveForwardsBy(telemetry, 53);
        driveTrain.turnToHeading(gyro, telemetry, 90);

            if (obeliskCode == null){
                telemetry.addLine("die and cry");
            } else if (obeliskCode.id == 21) {
                //GPP
                Thread.sleep(500);
                driveTrain.moveForwardsBy(telemetry, 45);
            } else if (obeliskCode.id == 22) {
                //PGP
                Thread.sleep(500);
                driveTrain.moveForwardsBy(telemetry, 22);
            } else if (obeliskCode.id == 23){
                Thread.sleep(500);
            } else {
                telemetry.addLine("You made a mistake pal");
            }

        //We scan goal and adjust
        aprilTag.update();
        AprilTagDetection goalTag = aprilTag.getSpecificTag(24);
        Thread.sleep(1000);
        aprilTag.STOP();
        //Now we launch
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
                aprilTag.update();
                AprilTagDetection goalTag2 = aprilTag.getSpecificTag(24);
                Thread.sleep(1000);
                aprilTag.STOP();
                Thread.sleep(500);
                //turn robot so that it's perfectly perpendicular
                driveTrain.turnToHeading(gyro, telemetry, goalTag2.ftcPose.bearing);
                Thread.sleep(100);
                //Lunching Begins
                intake.toggleVelocitayUno();
                intake.toggleLuncher();
                Thread.sleep(500);
                //Launch first ball
                intake.toggleRolla();
                Thread.sleep(200);
                //stop intake to save powah
                intake.toggleRolla();
                Thread.sleep(200);
                //Launch second AND THIRD ball
                intake.lunchingCycleforAuto2();
            }
        driveTrain.StrafeRightBy(telemetry, 18);


        } else {
            //This means we started in the small triangle. step one: run intake and pick up the correct order of artifacts

            // Now we check what tag we got and move accordingly
            if (obeliskCode == null){
                telemetry.addLine("die and cry");
            } else if (obeliskCode.id == 21) {
                //GPP
                Thread.sleep(500);
                driveTrain.moveForwardsBy(telemetry, 33);
            } else if (obeliskCode.id == 22) {
                //PGP
                Thread.sleep(500);
                driveTrain.moveForwardsBy(telemetry, 57);
            } else if (obeliskCode.id == 23){
                Thread.sleep(500);
                driveTrain.moveForwardsBy(telemetry, 75);
            } else {
                telemetry.addLine("You made a mistake pal");
            }

            Thread.sleep(500);
            driveTrain.turnToHeading(gyro, telemetry, -90); //Turn towards the ordered artifacts
            Thread.sleep(500);

            // We must now intake the ballz
            intake.toggleRolla();
            Thread.sleep(599);
            driveTrain.moveForwardsBy(telemetry, -48);
            Thread.sleep(700);
            intake.toggleRolla();

            //Now we go to launch!
            driveTrain.moveForwardsBy(telemetry, 48);
            driveTrain.turnToHeading(gyro, telemetry, 90);

            if (obeliskCode == null){
                telemetry.addLine("die and cry");
            } else if (obeliskCode.id == 21) {
                //GPP
                Thread.sleep(500);
                driveTrain.moveForwardsBy(telemetry, 66);
            } else if (obeliskCode.id == 22) {
                //PGP
                Thread.sleep(500);
                driveTrain.moveForwardsBy(telemetry, 42);
            } else if (obeliskCode.id == 23){
                Thread.sleep(500);
                driveTrain.moveForwardsBy(telemetry, 18);
            } else {
                telemetry.addLine("You made a mistake pal");
            }

            //We scan goal and adjust
            aprilTag.update();
            AprilTagDetection goalTag = aprilTag.getSpecificTag(24);
            Thread.sleep(1000);
            aprilTag.STOP();
            //Now we launch
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
                aprilTag.update();
                AprilTagDetection goalTag2 = aprilTag.getSpecificTag(24);
                Thread.sleep(1000);
                aprilTag.STOP();
                Thread.sleep(500);
                //turn robot so that it's perfectly perpendicular
                driveTrain.turnToHeading(gyro, telemetry, goalTag2.ftcPose.bearing);
                Thread.sleep(100);
                //Lunching Begins
                intake.toggleVelocitayUno();
                intake.toggleLuncher();
                Thread.sleep(500);
                //Launch first ball
                intake.toggleRolla();
                Thread.sleep(200);
                //stop intake to save powah
                intake.toggleRolla();
                Thread.sleep(200);
                //Launch second AND THIRD ball
                intake.lunchingCycleforAuto2();
            }

        }

        //FAHHHH I need the real deal field in order to figure out these measurements bruh.








    }

}
