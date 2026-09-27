package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.VoltageSensor;

public class OpenLoopBackend {

    // =========================================================
    //                  OPENLOOP USER SETTINGS
    // =========================================================
    //
    //       ONLY CHANGE VALUES IN THIS SECTION.
    //
    // =========================================================


    // ---------------------------------------------------------
    // MOTOR NAMES
    // ---------------------------------------------------------

    /*
     * These must exactly match the names in your
     * Robot Configuration.
     */
    public static final String FRONT_LEFT_NAME = "FL";
    public static final String FRONT_RIGHT_NAME = "FR";
    public static final String BACK_LEFT_NAME = "BL";
    public static final String BACK_RIGHT_NAME = "BR";


    // ---------------------------------------------------------
    // MOTOR DIRECTIONS
    //
    // true = reverse motor
    // false = normal direction
    //
    // Use the Motor Direction Debugger to determine
    // which motors need to be reversed.

    // ---------------------------------------------------------

    public static boolean REVERSE_FRONT_LEFT = true;
    public static boolean REVERSE_BACK_LEFT = true;

    public static boolean REVERSE_FRONT_RIGHT = false;
    public static boolean REVERSE_BACK_RIGHT = false;


    // ---------------------------------------------------------
    // CALIBRATION TEST POWER
    //
    // Power used when performing calibration tests.
    //
    // Recommended starting point: 0.50
    // ---------------------------------------------------------

    public static double TEST_POWER = 0.50;


    // ---------------------------------------------------------
    // FORWARD CALIBRATION
    //
    // Test 1:
    // Robot runs for TEST_TIME_1_MS.
    // Measure the distance travelled.
    //
    // Test 2:
    // Robot runs for TEST_TIME_2_MS.
    // Measure the distance travelled.
    // ---------------------------------------------------------

    public static long TEST_TIME_1_MS = 1000;
    public static double FORWARD_DIST_1_IN = 18.0;

    public static long TEST_TIME_2_MS = 2000;
    public static double FORWARD_DIST_2_IN = 35.0;


    // ---------------------------------------------------------
    // STRAFE CALIBRATION
    //
    // MECANUM ONLY.
    //
    // Ignore these values if using Tank.
    // ---------------------------------------------------------

    public static double STRAFE_DIST_1_IN = 16.0;
    public static double STRAFE_DIST_2_IN = 31.0;


    // ---------------------------------------------------------
    // TURN CALIBRATION
    //
    // Time required for your robot to turn exactly 90 degrees.
    //
    // Positive turn = RIGHT
    // Negative turn = LEFT
    // ---------------------------------------------------------

    public static long TURN_TIME_90_RIGHT_MS = 500;
    public static long TURN_TIME_90_LEFT_MS = 500;


    // ---------------------------------------------------------
    // AUTONOMOUS POWER
    //
    // Normally keep these equal to TEST_POWER.
    // ---------------------------------------------------------

    public static double DRIVE_POWER = 0.50;
    public static double STRAFE_POWER = 0.50;
    public static double TURN_POWER = 0.50;


    // ---------------------------------------------------------
    // BATTERY VOLTAGE COMPENSATION
    //
    // Enter the battery voltage during calibration.
    //
    // Example:
    // Battery voltage during calibration = 13.0 V
    // ---------------------------------------------------------

    public static boolean ENABLE_VOLTAGE_COMPENSATION = true;
    // VOLTAGE COMPENSATION IS RECOMMENDED FOR BEST RESULTS

    public static double CALIBRATION_VOLTAGE = 13.0;


    // =========================================================
    //              END OF USER SETTINGS
    //
    //           DO NOT EDIT BELOW THIS LINE
    // =========================================================


    public enum DriveType {
        MECANUM,
        TANK
    }


    private final LinearOpMode opMode;

    private final DriveType driveType;

    private final DcMotor frontLeft;
    private final DcMotor frontRight;
    private final DcMotor backLeft;
    private final DcMotor backRight;


    // Calculated from the user's calibration values.
    private final double forwardMsPerInch;
    private final double forwardOffsetMs;

    private final double strafeMsPerInch;
    private final double strafeOffsetMs;



    public OpenLoopBackend(
            LinearOpMode opMode,
            DriveType driveType) {

        this.opMode = opMode;
        this.driveType = driveType;


        frontLeft = opMode.hardwareMap.get(
                DcMotor.class,
                FRONT_LEFT_NAME
        );

        frontRight = opMode.hardwareMap.get(
                DcMotor.class,
                FRONT_RIGHT_NAME
        );

        backLeft = opMode.hardwareMap.get(
                DcMotor.class,
                BACK_LEFT_NAME
        );

        backRight = opMode.hardwareMap.get(
                DcMotor.class,
                BACK_RIGHT_NAME
        );


        frontLeft.setDirection(
                REVERSE_FRONT_LEFT
                        ? DcMotor.Direction.REVERSE
                        : DcMotor.Direction.FORWARD
        );

        frontRight.setDirection(
                REVERSE_FRONT_RIGHT
                        ? DcMotor.Direction.REVERSE
                        : DcMotor.Direction.FORWARD
        );

        backLeft.setDirection(
                REVERSE_BACK_LEFT
                        ? DcMotor.Direction.REVERSE
                        : DcMotor.Direction.FORWARD
        );

        backRight.setDirection(
                REVERSE_BACK_RIGHT
                        ? DcMotor.Direction.REVERSE
                        : DcMotor.Direction.FORWARD
        );


        frontLeft.setMode(
                DcMotor.RunMode.RUN_WITHOUT_ENCODER
        );

        frontRight.setMode(
                DcMotor.RunMode.RUN_WITHOUT_ENCODER
        );

        backLeft.setMode(
                DcMotor.RunMode.RUN_WITHOUT_ENCODER
        );

        backRight.setMode(
                DcMotor.RunMode.RUN_WITHOUT_ENCODER
        );


        forwardMsPerInch = solveRate(
                TEST_TIME_1_MS,
                FORWARD_DIST_1_IN,
                TEST_TIME_2_MS,
                FORWARD_DIST_2_IN
        );

        forwardOffsetMs = solveOffset(
                TEST_TIME_1_MS,
                FORWARD_DIST_1_IN,
                forwardMsPerInch
        );

        strafeMsPerInch = solveRate(
                TEST_TIME_1_MS,
                STRAFE_DIST_1_IN,
                TEST_TIME_2_MS,
                STRAFE_DIST_2_IN
        );

        strafeOffsetMs = solveOffset(
                TEST_TIME_1_MS,
                STRAFE_DIST_1_IN,
                strafeMsPerInch
        );
    }

    public void forward(double inches) {

        if (inches < 0) {
            backward(-inches);
            return;
        }

        long time = distanceToTime(
                inches,
                forwardMsPerInch,
                forwardOffsetMs
        );

        drive(
                DRIVE_POWER,
                DRIVE_POWER,
                DRIVE_POWER,
                DRIVE_POWER,
                time
        );
    }

    public void backward(double inches) {

        if (inches < 0) {
            forward(-inches);
            return;
        }

        long time = distanceToTime(
                inches,
                forwardMsPerInch,
                forwardOffsetMs
        );

        drive(
                -DRIVE_POWER,
                -DRIVE_POWER,
                -DRIVE_POWER,
                -DRIVE_POWER,
                time
        );
    }
    public void strafeLeft(double inches) {

        if (driveType != DriveType.MECANUM) {
            return;
        }

        if (inches < 0) {
            strafeRight(-inches);
            return;
        }

        long time = distanceToTime(
                inches,
                strafeMsPerInch,
                strafeOffsetMs
        );

        drive(
                -STRAFE_POWER,
                STRAFE_POWER,
                STRAFE_POWER,
                -STRAFE_POWER,
                time
        );
    }
    public void strafeRight(double inches) {

        if (driveType != DriveType.MECANUM) {
            return;
        }

        if (inches < 0) {
            strafeLeft(-inches);
            return;
        }

        long time = distanceToTime(
                inches,
                strafeMsPerInch,
                strafeOffsetMs
        );

        drive(
                STRAFE_POWER,
                -STRAFE_POWER,
                -STRAFE_POWER,
                STRAFE_POWER,
                time
        );
    }
    public void turn(double degrees) {

        if (degrees == 0) {
            return;
        }

        double power = TURN_POWER;

        long time;


        if (degrees > 0) {

            time = Math.round(
                    TURN_TIME_90_RIGHT_MS
                            * (Math.abs(degrees) / 90.0)
            );

            drive(
                    power,
                    -power,
                    power,
                    -power,
                    time
            );

        } else {

            time = Math.round(
                    TURN_TIME_90_LEFT_MS
                            * (Math.abs(degrees) / 90.0)
            );

            drive(
                    -power,
                    power,
                    -power,
                    power,
                    time
            );
        }
    }
    public void stop() {
        setPowers(0, 0, 0, 0);
    }

    private void drive(
            double fl,
            double fr,
            double bl,
            double br,
            long timeMs) {

        if (timeMs <= 0) {
            return;
        }
        fl = compensateVoltage(fl);
        fr = compensateVoltage(fr);
        bl = compensateVoltage(bl);
        br = compensateVoltage(br);


        setPowers(
                fl,
                fr,
                bl,
                br
        );


        opMode.sleep(timeMs);


        stop();
    }


    private void setPowers(
            double fl,
            double fr,
            double bl,
            double br) {

        frontLeft.setPower(fl);
        frontRight.setPower(fr);
        backLeft.setPower(bl);
        backRight.setPower(br);
    }
    private double solveRate(
            double time1,
            double distance1,
            double time2,
            double distance2) {

        if (distance1 == distance2) {
            return 0;
        }

        return (time2 - time1)
                / (distance2 - distance1);
    }
    private double solveOffset(
            double time,
            double distance,
            double msPerInch) {

        return time
                - (msPerInch * distance);
    }
    private long distanceToTime(
            double inches,
            double msPerInch,
            double offsetMs) {

        double result =
                (msPerInch * inches)
                        + offsetMs;

        return Math.max(
                0,
                Math.round(result)
        );
    }
    private double compensateVoltage(
            double requestedPower) {

        if (requestedPower == 0) {
            return 0;
        }

        if (!ENABLE_VOLTAGE_COMPENSATION) {
            return requestedPower;
        }

        if (CALIBRATION_VOLTAGE <= 0) {
            return requestedPower;
        }


        double currentVoltage =
                getBatteryVoltage();


        if (currentVoltage <= 0) {
            return requestedPower;
        }


        double scale =
                CALIBRATION_VOLTAGE
                        / currentVoltage;


        double compensated =
                requestedPower * scale;


        // Motor power cannot exceed ±1.0.
        compensated = Math.max(
                -1.0,
                Math.min(1.0, compensated)
        );


        return compensated;
    }
    private double getBatteryVoltage() {

        double minimumVoltage =
                Double.POSITIVE_INFINITY;


        for (VoltageSensor sensor :
                opMode.hardwareMap.voltageSensor) {

            double voltage =
                    sensor.getVoltage();


            if (voltage > 0 &&
                    voltage < minimumVoltage) {

                minimumVoltage = voltage;
            }
        }


        if (minimumVoltage ==
                Double.POSITIVE_INFINITY) {

            return 0;
        }


        return minimumVoltage;
    }
}