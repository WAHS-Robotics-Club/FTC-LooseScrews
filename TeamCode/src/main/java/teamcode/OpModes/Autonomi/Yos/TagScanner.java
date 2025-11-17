package teamcode.OpModes.Autonomi.Yos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.VisionPortal;
import android.util.Size;

import teamcode.Objects.Tool.AprilTag;
import teamcode.Objects.BananaFruit;
import teamcode.Objects.DriveTrain;
import teamcode.Objects.Intake;

//Goal : See the tag and find the distance from tag to bot (CHECK)

    @Autonomous(name ="THE ERA OF KNOWLEDGE AND PROSPERITY HAS BEGUN. ENTER: APRILTAGS!")
    public class TagScanner extends LinearOpMode {

        public double fx = 1502.91;
        public double fy = 1052.91;
        public double cx = 970.487;
        public double cy = 389.479;

        public final boolean calibratedCamera = true;

        private AprilTagProcessor aprilTagProcessor;

        private static final int DESIRED_TAG_ID = -1;
        private static AprilTagDetection desiredTag = null;

        boolean targetFound = false;

        AprilTag aprilTag;

        DriveTrain driveTrain;
        Intake intake;

        //HOWDY! I don't know if this works but I'm working on it!

        @Override
        public void runOpMode() throws InterruptedException {

            if (calibratedCamera) {
                aprilTagProcessor = AprilTagProcessor.easyCreateWithDefaults();
            } else {
                aprilTagProcessor = new AprilTagProcessor.Builder()
                        .setLensIntrinsics(fx, fy, cx, cy)
                        .build();
            }
            //What does this mean? IDK. I think it relates to camera settings defined by fx fy yadayada
            //Don't worry about it unless the camera WAHS robotics is using isn't the one we are using currently

            VisionPortal visionPortal = new VisionPortal.Builder()
                    .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                    .addProcessor(aprilTagProcessor)
                    .setCameraResolution(new Size(640, 480))
                    .build();

            driveTrain = DriveTrain.initDriveTrain(hardwareMap, DcMotor.ZeroPowerBehavior.BRAKE);
            intake = Intake.initGrabber(hardwareMap);

            telemetry.addData("IsBusy", driveTrain.isBusy());
            DriveTrain.logTelemetry(telemetry, driveTrain);
            telemetry.update();
            driveTrain.resetEncoders();
            BananaFruit gyro = new BananaFruit();
            gyro.runBananaFruit(hardwareMap, telemetry);
            telemetry.update();

            waitForStart();

            while (opModeIsActive()) {
                for (AprilTagDetection detection : aprilTagProcessor.getDetections()) {
                    targetFound = false;
                    desiredTag  = null;

                    if (detection.metadata != null) {
                        telemetry.addLine(("TAG FOUND!!!!!!!!!!!!!"));
                        telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                        telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                        telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                        telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
                        //  Check to see if we want to track towards this tag.
                        if ((DESIRED_TAG_ID < 0) || (detection.id == DESIRED_TAG_ID)) {
                            // Yes, we want to use this tag.
                            targetFound = true;
                            desiredTag = detection;
                            break;  // don't look any further.
                        } else {
                            // This tag is in the library, but we do not want to track it right now.
                            telemetry.addData("Skipping", "Tag ID %d is not desired", detection.id);
                        }
                    } else {
                        // This tag is NOT in the library, so we don't have enough information to track to it.
                        telemetry.addData("Unknown", "Tag ID %d is not in TagLibrary", detection.id);
                    }
                }
                // end for() loop
                if (targetFound) {
                    double x = desiredTag.ftcPose.x;
                    double y = desiredTag.ftcPose.y;
                    double z = desiredTag.ftcPose.z;
                    double range = desiredTag.ftcPose.range;
                    double yaw = desiredTag.ftcPose.yaw;
                    double pitch = desiredTag.ftcPose.roll;
                    double bearing = desiredTag.ftcPose.bearing;

                    double theDistance = Math.abs(Math.cos(pitch) * range);

                    driveTrain.moveForwardsBy(telemetry, range);
                }

                // Add "key" information to telemetry
                telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
                telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
                telemetry.addLine("RBE = Range, Bearing & Elevation");


                telemetry.update();

                sleep(20);

            }

        }
    }
