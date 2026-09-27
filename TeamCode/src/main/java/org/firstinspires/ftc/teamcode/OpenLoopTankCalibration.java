package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "OpenLoop Tank Calibration")
public class OpenLoopTankCalibration extends LinearOpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    private final ElapsedTime timer = new ElapsedTime();

    private boolean lastA;
    private boolean lastY;
    private boolean lastUp;
    private boolean lastDown;


    @Override
    public void runOpMode() {

        frontLeft = hardwareMap.get(
                DcMotor.class,
                OpenLoopBackend.FRONT_LEFT_NAME
        );

        frontRight = hardwareMap.get(
                DcMotor.class,
                OpenLoopBackend.FRONT_RIGHT_NAME
        );

        backLeft = hardwareMap.get(
                DcMotor.class,
                OpenLoopBackend.BACK_LEFT_NAME
        );

        backRight = hardwareMap.get(
                DcMotor.class,
                OpenLoopBackend.BACK_RIGHT_NAME
        );


        setMotorDirections();


        telemetry.addLine(
                "OPENLOOP TANK CALIBRATION"
        );

        telemetry.addLine("");

        telemetry.addLine(
                "A  = Forward Test 1"
        );

        telemetry.addLine(
                "Y  = Forward Test 2"
        );

        telemetry.addLine(
                "UP = 90° Right"
        );

        telemetry.addLine(
                "DOWN = 90° Left"
        );

        telemetry.addLine("");

        telemetry.addLine(
                "Record battery voltage during calibration."
        );

        telemetry.update();


        waitForStart();


        while (opModeIsActive()) {

            double p =
                    OpenLoopBackend.TEST_POWER;


            if (pressed(gamepad1.a, lastA)) {

                runTest(
                        "FORWARD TEST 1",
                        OpenLoopBackend.TEST_TIME_1_MS,
                        p, p, p, p
                );
            }


            if (pressed(gamepad1.y, lastY)) {

                runTest(
                        "FORWARD TEST 2",
                        OpenLoopBackend.TEST_TIME_2_MS,
                        p, p, p, p
                );
            }


            if (pressed(gamepad1.dpad_up, lastUp)) {

                runTurn(
                        "90 DEGREE RIGHT",
                        OpenLoopBackend.TURN_TIME_90_RIGHT_MS,
                        p, -p, p, -p
                );
            }


            if (pressed(gamepad1.dpad_down, lastDown)) {

                runTurn(
                        "90 DEGREE LEFT",
                        OpenLoopBackend.TURN_TIME_90_LEFT_MS,
                        -p, p, -p, p
                );
            }


            lastA = gamepad1.a;
            lastY = gamepad1.y;
            lastUp = gamepad1.dpad_up;
            lastDown = gamepad1.dpad_down;


            telemetry.addLine(
                    "A=Forward1  Y=Forward2"
            );

            telemetry.addLine(
                    "UP=90 Right  DOWN=90 Left"
            );

            telemetry.addData(
                    "Test Power",
                    OpenLoopBackend.TEST_POWER
            );

            telemetry.update();
        }
    }


    private void setMotorDirections() {

        frontLeft.setDirection(
                OpenLoopBackend.REVERSE_FRONT_LEFT
                        ? DcMotor.Direction.REVERSE
                        : DcMotor.Direction.FORWARD
        );

        frontRight.setDirection(
                OpenLoopBackend.REVERSE_FRONT_RIGHT
                        ? DcMotor.Direction.REVERSE
                        : DcMotor.Direction.FORWARD
        );

        backLeft.setDirection(
                OpenLoopBackend.REVERSE_BACK_LEFT
                        ? DcMotor.Direction.REVERSE
                        : DcMotor.Direction.FORWARD
        );

        backRight.setDirection(
                OpenLoopBackend.REVERSE_BACK_RIGHT
                        ? DcMotor.Direction.REVERSE
                        : DcMotor.Direction.FORWARD
        );
    }


    private boolean pressed(
            boolean current,
            boolean previous) {

        return current && !previous;
    }


    private void runTest(
            String name,
            long duration,
            double fl,
            double fr,
            double bl,
            double br) {

        telemetry.clearAll();

        telemetry.addLine(
                "Running: " + name
        );

        telemetry.addLine(
                "DO NOT TOUCH THE ROBOT"
        );

        telemetry.update();


        frontLeft.setPower(fl);
        frontRight.setPower(fr);
        backLeft.setPower(bl);
        backRight.setPower(br);


        timer.reset();


        while (
                opModeIsActive() &&
                        timer.milliseconds() < duration) {

            idle();
        }


        stopMotors();


        telemetry.clearAll();

        telemetry.addLine(
                name + " COMPLETE"
        );

        telemetry.addLine("");

        telemetry.addLine(
                "Measure the distance travelled."
        );

        telemetry.addLine(
                "Then enter it in OpenLoopBackend."
        );

        telemetry.update();


        sleep(2500);
    }


    private void runTurn(
            String name,
            long duration,
            double fl,
            double fr,
            double bl,
            double br) {

        telemetry.clearAll();

        telemetry.addLine(
                "Running: " + name
        );

        telemetry.addLine("");

        telemetry.addLine(
                "Compare the robot with a 90° reference."
        );

        telemetry.update();


        frontLeft.setPower(fl);
        frontRight.setPower(fr);
        backLeft.setPower(bl);
        backRight.setPower(br);


        timer.reset();


        while (
                opModeIsActive() &&
                        timer.milliseconds() < duration) {

            idle();
        }


        stopMotors();


        telemetry.clearAll();

        telemetry.addLine(
                name + " COMPLETE"
        );

        telemetry.addLine("");

        telemetry.addLine(
                "Adjust the corresponding"
        );

        telemetry.addLine(
                "TURN_TIME_90 value."
        );

        telemetry.update();


        sleep(2000);
    }


    private void stopMotors() {

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
}