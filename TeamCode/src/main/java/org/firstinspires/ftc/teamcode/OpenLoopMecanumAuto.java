package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "OpenLoop Mecanum Auto")
public class OpenLoopMecanumAuto extends LinearOpMode {

    @Override
    public void runOpMode() {

        OpenLoopBackend bot =
                new OpenLoopBackend(
                        this,
                        OpenLoopBackend.DriveType.MECANUM
                );


        telemetry.addLine(
                "OpenLoop Mecanum Ready"
        );

        telemetry.addLine(
                "Waiting for start..."
        );

        telemetry.update();


        waitForStart();


        if (isStopRequested()) {
            return;
        }


        // =====================================================
        // YOUR AUTONOMOUS CODE GOES HERE
        // =====================================================

        /*
         * -----------------------------------------------------
         *             OPENLOOP AUTONOMOUS CODE
         * -----------------------------------------------------
         *
         * Add, remove, or rearrange the commands below.
         *
         * Do not edit anything outside this section.
         * -----------------------------------------------------
         */

        bot.forward(24);
        sleep(2000);// Move forward 24 inches

        bot.turn(90);          // Turn right 90 degrees

        bot.strafeLeft(12);    // Strafe left 12 inches

        bot.forward(18);       // Move forward 18 inches

        bot.turn(-45);         // Turn left 45 degrees

        bot.backward(10);      // Move backward 10 inches


        // =====================================================


        telemetry.addLine(
                "OpenLoop autonomous complete."
        );

        telemetry.update();
    }
}