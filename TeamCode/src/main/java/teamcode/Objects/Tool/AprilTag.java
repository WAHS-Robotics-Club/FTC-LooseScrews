package teamcode.Objects.Tool;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.List;

public class AprilTag {

    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;

    private List<AprilTagDetection> detectedTags = new ArrayList<>();

    private Telemetry telemetry;

    final public double minRange = 38;
    final public double LAUNCHDIST_ACCURACY = 2;
    final public double MAX_LAUNCHDIST = 3;

    public void initAprilTag (HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        AprilTagLibrary tagLibrary = AprilTagGameDatabase.getDecodeTagLibrary();

        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawCubeProjection(true)
                .setTagLibrary(tagLibrary)
                .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
                .build();

        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        builder.setCameraResolution(new Size(640,480));
        builder.addProcessor(aprilTagProcessor);

        visionPortal = builder.build();

    }

    public void update() {
        detectedTags = aprilTagProcessor.getDetections();
    }

    public List<AprilTagDetection> getDetectedTags() {
        return detectedTags;
    }

    public void TagTelemetry (AprilTagDetection detectedID) {
        if (detectedID == null) {return;}
        if (detectedID.metadata != null) {
            telemetry.addLine(String.format("\n==== (ID %d) %s", detectedID.id, detectedID.metadata.name));
            telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detectedID.ftcPose.x, detectedID.ftcPose.y, detectedID.ftcPose.z));
            telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detectedID.ftcPose.pitch, detectedID.ftcPose.roll, detectedID.ftcPose.yaw));
            telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detectedID.ftcPose.range, detectedID.ftcPose.bearing, detectedID.ftcPose.elevation));
        } else {
            telemetry.addLine(String.format("\n==== (ID %d) Unknown", detectedID.id));
            telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detectedID.center.x, detectedID.center.y));
        }
    }

    public void LaunchGauger (AprilTagDetection detectedID) {
        if (detectedID == null) {
            telemetry.addLine();
            telemetry.addLine("Hey buddy. I can't see the goal. Do you wanna score points or not? -_-");
        } else if (detectedID.ftcPose.range < (minRange + LAUNCHDIST_ACCURACY)) {
            double CurrentDistance = detectedID.ftcPose.range;
            double correction = Math.abs(minRange - CurrentDistance);
            telemetry.addLine();
            telemetry.addLine(String.format("HEY BUD. YOU'RE TOO CLOSE TO LAUNCH"));
            telemetry.addLine(String.format("Current Range: %6.1f (in)", detectedID.ftcPose.range));
            telemetry.addLine(String.format("You gotta move %6.1f in backwards", correction));
        } else if (detectedID.ftcPose.range > minRange + LAUNCHDIST_ACCURACY) {
            double CurrentDistance = detectedID.ftcPose.range;
            double correction = Math.abs(minRange - CurrentDistance);
            telemetry.addLine();
            telemetry.addLine(String.format("HEY PAL. YOU'RE NOT ClOSE ENOUGH TO LAUNCH"));
            telemetry.addLine(String.format("Current Range: %6.1f (in)", detectedID.ftcPose.range));
            telemetry.addLine(String.format("You gotta move %6.1f in forwards", correction));
        } else if (detectedID.ftcPose.range == minRange) {
            telemetry.addLine();
            telemetry.addLine("How is this possible. You are exactly the right distance 0_0 ");
        }
    }

    public int velocitayEstimator (AprilTagDetection detectedID) {
        int velocitay = 3100;
        if (detectedID == null) {
            velocitay = 0;
        } else {
            double d = ((Math.sqrt((Math.pow(detectedID.ftcPose.range, 2)) - (Math.pow(detectedID.ftcPose.z, 2)))) * 0.0254);
            if (d <= MAX_LAUNCHDIST) {
                double linearVelocitay = Math.sqrt((Math.pow(d, 2) * 9.81) / (((d * 0.900404044298) - (19.5)*0.0254) * 2 * (0.552264231634))); // The incredible William Lewis Equation
                velocitay = (int) (5.5*((linearVelocitay * 60) / (2 * Math.PI * 0.1))); //Converting m/s to RPM (I put 6.4 cuz I really have no idea what I'm doing)
            }
        }
        return velocitay;
    }     //There's definitely some holes here that I will figure out tomorrow which is now today

    public AprilTagDetection getSpecificTag (int id) {
        for (AprilTagDetection detection : detectedTags) {
            if (detection.id == id) {
                return detection;
            }
        }
        return null;
    }

        public AprilTagDetection getGoalTag (int id) {
            for (AprilTagDetection detection : detectedTags) {
                if (detection.id == id && detection.metadata != null) {
                    return detection;
                }
            }
            return null;
    }

    public AprilTagDetection ObeliskScan () {
        for (AprilTagDetection obeliskTag : detectedTags) {
            if (obeliskTag.id == 21) {
                return obeliskTag;
            } else if (obeliskTag.id == 22) {
                return obeliskTag;
            } else if (obeliskTag.id == 33) {
                return obeliskTag;
            }
        }
        return null;
    }

    public void STOP() {
        if (visionPortal != null) {
            visionPortal.close();
        }
    }





















}
