# OpenLoop

## Autonomous, Made Simple.

## Created by Abhiraj Karki

OpenLoop is an FTC autonomous system designed to make autonomous
programming **simple, intuitive, and accessible**.

OpenLoop is built for teams with little or no experience in autonomous programming, and for teams who want a simple way to get an autonomous routine working without relying on complex hardware or software. Using simple, intuitive commands, OpenLoop allows teams to create autonomous routines without odometry pods, drive encoders, or IMUs. It works with both mecanum and tank drivetrains. The goal is to make autonomous programming easier to understand and more accessible with commands as simple as-
``` java
bot.forward(24);
bot.turn(90);
bot.strafeLeft(12);
bot.backward(10);
```



------------------------------------------------------------------------

# Why I built OpenLoop

OpenLoop started with a simple observation I made while being involved in FTC in India.

As part of an FTC team, I saw other teams struggling with autonomous. Some teams did not have odometry pods or other localization hardware. Some had limited programming experience. Some were competing with little or no autonomous at all.

That bothered me because I felt autonomous should not be something that is only accessible to teams with experienced programmers, advanced hardware, or the time and resources to spend hours figuring out complex systems.

There are already powerful autonomous tools for teams that want advanced control and localization. I am not trying to replace those systems.

Instead, I wanted to create something that makes the first step into autonomous much easier.

That idea became OpenLoop.

I built OpenLoop to give teams a simple way to start writing autonomous routines, understand the basics, and build confidence without needing complex hardware or advanced programming experience from the beginning.

My goal goes beyond helping teams in India. I want OpenLoop to be available to FTC teams around the world, regardless of their programming experience, hardware, or resources.

**Autonomous should be accessible to everyone.**

------------------------------------------------------------------------

# What is OpenLoop?

OpenLoop is an **open-source, open-loop, time-based autonomous system**.

Instead of continuously measuring the robot's position and correcting
its movement, OpenLoop uses calibrated motor power and timing to
estimate how long the robot needs to move to travel a requested
distance.

For example:

``` java
bot.forward(24);
bot.turn(90);
bot.strafeLeft(12);
bot.backward(10);
```

The code describes the autonomous routine in terms of what the robot
should do, rather than requiring you to manually calculate motor powers
and sleep times for every movement.

## What OpenLoop does NOT use

OpenLoop does not use:

-   Odometry pods
-   Encoder feedback for movement control
-   IMU heading feedback
-   PID control
-   Motion profiling
-   Continuous position correction

That is intentional.

OpenLoop trades some precision for **simplicity and accessibility**.

Because it is open-loop, it cannot know that the robot actually
travelled exactly 24 inches. It estimates the required movement time
from your calibration data.

------------------------------------------------------------------------

# Who is OpenLoop for?

OpenLoop is particularly useful for teams that:

-   Are new to autonomous programming
-   Are new to Java
-   Use OnBot Java
-   Do not have odometry
-   Want a simple autonomous system
-   Want to understand what their autonomous code is doing
-   Need a straightforward starting point before moving to more advanced
    systems

It can also be useful for experienced teams who simply want a
lightweight autonomous option.

------------------------------------------------------------------------

# Features

-   Mecanum drive support
-   Tank drive support
-   Distance-based forward/backward movement
-   Distance-based mecanum strafing
-   Arbitrary-angle turning
-   Two-point movement calibration
-   Separate left/right 90° turn calibration
-   Battery voltage compensation
-   Motor direction debugger
-   Dedicated calibration OpModes for Mecanum and Tank drivetrains
-   Android Studio support
-   OnBot Java support

------------------------------------------------------------------------

# OpenLoop Can Be Used In Two Ways

You **do not need to download the entire OpenLoop project** if you only
want to use it through OnBot Java.

There are two approaches.

## Option 1: OnBot Java

Use this if you already have your team's FTC project on the Control Hub
and just want OpenLoop.

You only need to copy the OpenLoop Java files into your OnBot Java
project.

### Mecanum

You need:

1.  `OpenLoopBackend.java`
2.  `OpenLoopMecanumCalibration.java`
3.  `OpenLoopMecanumAuto.java`
4.  `MotorDirectionDebugger.java`

### Tank

You need:

1.  `OpenLoopBackend.java`
2.  `OpenLoopTankCalibration.java`
3.  `OpenLoopTankAuto.java`
4.  `MotorDirectionDebugger.java`

You **do not need** the other drive-type files.

------------------------------------------------------------------------

## Option 2: Android Studio

If you use Android Studio, you can clone or download this repository and
use the included FTC Robot Controller project.

The OpenLoop code is located in:

``` text
TeamCode/src/main/java/org/firstinspires/ftc/teamcode/
```

------------------------------------------------------------------------

# OnBot Java: Full Walkthrough

This section is for teams who just want to get OpenLoop running without
downloading the entire repository.

## Step 1: Open OnBot Java

Connect your computer to your Robot Controller / Control Hub and open
the FTC Robot Controller's **Program & Manage** page.

Open **OnBot Java**.

You should see your team's existing Java files.

------------------------------------------------------------------------

# Step 2: Create the OpenLoop Backend

Create a new Java file named:

``` text
OpenLoopBackend.java
```

It must use the same package as your team's other OnBot Java files:

``` java
package org.firstinspires.ftc.teamcode;
```

Copy the complete contents of the `OpenLoopBackend.java` file from this
repository into that file.

The backend is the main OpenLoop engine.

You normally only edit the section at the **top** marked:

``` text
OPENLOOP USER SETTINGS
```

Do not modify the rest unless you know exactly what you are changing.

------------------------------------------------------------------------

# Step 3: Set Your Motor Names

In `OpenLoopBackend.java`, find:

``` java
public static final String FRONT_LEFT_NAME = "FL";
public static final String FRONT_RIGHT_NAME = "FR";
public static final String BACK_LEFT_NAME = "BL";
public static final String BACK_RIGHT_NAME = "BR";
```

Change these to exactly match the names in your Robot Configuration.

For example, if your configuration contains:

``` text
frontLeft
frontRight
backLeft
backRight
```

use:

``` java
public static final String FRONT_LEFT_NAME = "frontLeft";
public static final String FRONT_RIGHT_NAME = "frontRight";
public static final String BACK_LEFT_NAME = "backLeft";
public static final String BACK_RIGHT_NAME = "backRight";
```

The names must match exactly.

------------------------------------------------------------------------

# Step 4: Find the Correct Motor Directions

Do **not** guess the motor directions.

Upload:

``` text
MotorDirectionDebugger.java
```

Then run:

``` text
OpenLoop Motor Direction Debugger
```

### Controls

  Control       Action
  ------------- -----------------------------
  D-pad Left    Select previous motor
  D-pad Right   Select next motor
  A             Run selected motor forward
  B             Run selected motor backward
  X             Stop

The selected motor is shown through telemetry.

Use this to determine whether each motor needs to be reversed.

Then change:

``` java
public static boolean REVERSE_FRONT_LEFT = true;
public static boolean REVERSE_BACK_LEFT = true;

public static boolean REVERSE_FRONT_RIGHT = false;
public static boolean REVERSE_BACK_RIGHT = false;
```

to match your robot.

------------------------------------------------------------------------

# Step 5: Calibrate the Robot

This is the most important part.

OpenLoop is open-loop, so calibration determines how accurately it can
estimate movement.

You will perform:

1.  Forward Test 1
2.  Forward Test 2
3.  Strafe Test 1 and 2 if using mecanum
4.  90° right turn
5.  90° left turn
6.  Battery voltage recording

------------------------------------------------------------------------

# Mecanum Calibration

Upload:

``` text
OpenLoopMecanumCalibration.java
```

Run:

``` text
OpenLoop Mecanum Calibration
```

The controller will show these controls:

  Button       Test
  ------------ ----------------
  A            Forward Test 1
  Y            Forward Test 2
  X            Strafe Test 1
  B            Strafe Test 2
  D-pad Up     90° Right
  D-pad Down   90° Left

Make sure the robot has enough space to move safely.

------------------------------------------------------------------------

## Forward Test 1

Press **A**.

The robot will drive forward for:

``` java
TEST_TIME_1_MS
```

The default is:

``` java
1000
```

milliseconds.

Measure how far the robot actually travelled.

If it travelled 18 inches, enter:

``` java
public static double FORWARD_DIST_1_IN = 18.0;
```

with your actual measured distance.

------------------------------------------------------------------------

## Forward Test 2

Press **Y**.

The robot will drive forward for:

``` java
TEST_TIME_2_MS
```

The default is:

``` java
2000
```

milliseconds.

Measure the distance travelled and enter it as:

``` java
public static double FORWARD_DIST_2_IN = 35.0;
```

Again, replace `35.0` with your actual measurement.

------------------------------------------------------------------------

# Mecanum Strafe Calibration

Press **X** for Strafe Test 1.

Measure the distance travelled and enter:

``` java
public static double STRAFE_DIST_1_IN = ...;
```

Press **B** for Strafe Test 2.

Measure the distance travelled and enter:

``` java
public static double STRAFE_DIST_2_IN = ...;
```

These values are only used for mecanum drive.

------------------------------------------------------------------------

# Turn Calibration

OpenLoop uses separate calibration values for right and left turns:

``` java
public static long TURN_TIME_90_RIGHT_MS = 500;
public static long TURN_TIME_90_LEFT_MS = 500;
```

Press **D-pad Up**.

The robot attempts to turn 90° right.

If it turns less than 90°, increase the corresponding time.

If it turns more than 90°, decrease it.

Repeat until it is approximately 90°.

Then do the same with **D-pad Down** for the left turn.

------------------------------------------------------------------------

# How Battery Voltage Compensation Works

As the battery voltage drops, the motors generally produce less power when given the same commanded power. This can cause a movement calibrated with a higher battery voltage to behave differently when the battery voltage is lower.

OpenLoop compensates for this by comparing the calibration voltage with the current battery voltage when a movement starts.

It calculates a voltage compensation factor by dividing the calibration voltage by the current voltage:

Compensation Factor = Calibration Voltage ÷ Current Voltage

It then multiplies the requested motor power by this factor:

Adjusted Power = Requested Power × Compensation Factor

For example, suppose you calibrated the robot at 13.0 V, and during autonomous the battery is currently at 12.0 V.

13.0 ÷ 12.0 = 1.083

If OpenLoop was going to use:

Requested Power = 0.50

it multiplies the power by the compensation factor:

0.50 × 1.083 = 0.542

So OpenLoop sends approximately:

0.542 power

instead of:

0.50 power

This scales the motor power up when the battery voltage is lower than the calibration voltage.

If the current voltage is higher than the calibration voltage, the opposite happens:

13.0 ÷ 13.5 = 0.963
0.50 × 0.963 = 0.482

So the power is scaled down slightly.
------------------------------------------------------------------------

# How the Distance Calibration Works

OpenLoop performs two calibration tests for forward movement and two for
mecanum strafing.

For example:

``` text
1000 ms → 18 inches
2000 ms → 35 inches
```

From those two measurements, OpenLoop calculates a linear relationship
between time and distance.

It then uses that relationship when you write:

``` java
bot.forward(24);
```

instead of requiring you to manually calculate:

``` java
sleep(...);
```

for every distance.

This is still an approximation.

The robot is not measuring its actual position while moving.

------------------------------------------------------------------------

# Step 6: Create Your Autonomous

For mecanum, create:

``` text
OpenLoopMecanumAuto.java
```

For tank, create:

``` text
OpenLoopTankAuto.java
```

The only part you should normally edit is the section labelled:

``` text
YOUR AUTONOMOUS CODE GOES HERE
```

For example:

``` java
bot.forward(24);

bot.turn(90);

bot.strafeLeft(12);

bot.forward(18);

bot.turn(-45);

bot.backward(10);
```

------------------------------------------------------------------------

# OpenLoop Commands

## Forward

``` java
bot.forward(24);
```

Moves forward approximately 24 inches.

------------------------------------------------------------------------

## Backward

``` java
bot.backward(10);
```

Moves backward approximately 10 inches.

------------------------------------------------------------------------

## Turn

``` java
bot.turn(90);
```

Turns approximately 90° right.

Negative values turn left:

``` java
bot.turn(-90);
```

You can also use other angles:

``` java
bot.turn(45);
bot.turn(-45);
bot.turn(180);
```

OpenLoop calculates the required time by scaling the calibrated 90° turn
time.

------------------------------------------------------------------------

## Strafe Left

Mecanum only:

``` java
bot.strafeLeft(12);
```

------------------------------------------------------------------------

## Strafe Right

Mecanum only:

``` java
bot.strafeRight(12);
```

------------------------------------------------------------------------
## Sleep

``` java
sleep(2000);
```

This pauses the autonomous routine for 2000 milliseconds (2 seconds).
------------------------------------------------------------------------

## Stop

``` java
bot.stop();
```

Stops all four drive motors.

------------------------------------------------------------------------

# A Complete Mecanum Example

``` java
bot.forward(24);

bot.turn(90);

bot.strafeLeft(12);

bot.forward(18);

bot.turn(-45);

bot.backward(10);
```

That is the entire idea behind OpenLoop.

The autonomous should read almost like instructions to a person:

**Go forward. Turn. Strafe. Go forward. Turn. Go backward.**

------------------------------------------------------------------------

# A Complete Tank Example

``` java
bot.forward(24);

bot.turn(90);

bot.forward(18);

bot.turn(-45);

bot.backward(10);
```

Tank drives do not support strafing, so do not use:

``` java
bot.strafeLeft(...);
bot.strafeRight(...);
```

with a tank backend.

------------------------------------------------------------------------

# What Files Do What?

## `OpenLoopBackend.java`

The core of OpenLoop.

It:

-   Reads your motor configuration
-   Applies motor directions
-   Uses `RUN_WITHOUT_ENCODER`
-   Calculates movement timing from calibration
-   Runs forward/backward movement
-   Runs mecanum strafing
-   Runs turns
-   Handles battery voltage compensation
-   Stops the motors after each movement

This is the only file that contains the main OpenLoop system.

------------------------------------------------------------------------

## `OpenLoopMecanumAuto.java`

A ready-to-edit mecanum autonomous OpMode.

You write your autonomous routine here.

------------------------------------------------------------------------

## `OpenLoopTankAuto.java`

A ready-to-edit tank autonomous OpMode.

------------------------------------------------------------------------

## `OpenLoopMecanumCalibration.java`

Calibration tool for:

-   Forward movement
-   Strafing
-   Right turns
-   Left turns

------------------------------------------------------------------------

## `OpenLoopTankCalibration.java`

Calibration tool for:

-   Forward movement
-   Right turns
-   Left turns

Tank drive does not need strafe calibration.

------------------------------------------------------------------------

## `MotorDirectionDebugger.java`

A simple tool for determining which motors need to be reversed.

------------------------------------------------------------------------

# For Teams Using OnBot Java: What Exactly Do I Copy?

You **do not need the entire FTC Robot Controller project**.

For a mecanum robot, copy these four Java files:

``` text
OpenLoopBackend.java
MotorDirectionDebugger.java
OpenLoopMecanumCalibration.java
OpenLoopMecanumAuto.java
```

For a tank robot:

``` text
OpenLoopBackend.java
MotorDirectionDebugger.java
OpenLoopTankCalibration.java
OpenLoopTankAuto.java
```

The complete source of each file is available directly in this
repository:

-   [`OpenLoopBackend.java`](TeamCode/src/main/java/org/firstinspires/ftc/teamcode/OpenLoopBackend.java)
-   [`MotorDirectionDebugger.java`](TeamCode/src/main/java/org/firstinspires/ftc/teamcode/MotorDirectionDebugger.java)
-   [`OpenLoopMecanumCalibration.java`](TeamCode/src/main/java/org/firstinspires/ftc/teamcode/OpenLoopMecanumCalibration.java)
-   [`OpenLoopMecanumAuto.java`](TeamCode/src/main/java/org/firstinspires/ftc/teamcode/OpenLoopMecanumAuto.java)
-   [`OpenLoopTankCalibration.java`](TeamCode/src/main/java/org/firstinspires/ftc/teamcode/OpenLoopTankCalibration.java)
-   [`OpenLoopTankAuto.java`](TeamCode/src/main/java/org/firstinspires/ftc/teamcode/OpenLoopTankAuto.java)

Open the file, click **Raw**, and copy the code directly into OnBot
Java. This lets teams take only the files they actually need instead of
downloading the entire project.

After copying them into your OnBot Java project, they should all use:

``` java
package org.firstinspires.ftc.teamcode;
```

Do not change the package unless your project uses a different package
structure.

------------------------------------------------------------------------

# Recommended OnBot Java Workflow

If you are completely new, follow this order:

### 1. Add `OpenLoopBackend.java`

Make sure it compiles.

### 2. Set your motor names

Match your Robot Configuration.

### 3. Add `MotorDirectionDebugger.java`

Determine the correct motor directions.

### 4. Update the reverse settings

Change the four `REVERSE_...` values.

### 5. Add the calibration file

Use the mecanum or tank calibration OpMode.

### 6. Calibrate forward movement

Run both forward tests.

### 7. Calibrate strafing

Mecanum only.

### 8. Calibrate turning

Calibrate both right and left turns.

### 9. Set battery voltage

Enter the voltage measured during calibration.

### 10. Add your autonomous file

Write your movements.

### 11. Test repeatedly

Start with simple movements before building the complete autonomous.

------------------------------------------------------------------------


# Limitations

OpenLoop is intentionally simple.

Because it does not use position feedback:

-   The robot can drift.
-   Errors can accumulate through a long routine.
-   A collision can move the robot away from its expected position.
-   Strafing can be less consistent than forward movement.
-   Different surfaces can require different calibration.
-   Mechanical changes can require recalibration.
-   It cannot automatically correct its position.

------------------------------------------------------------------------

# Safety

Always test autonomous code with the robot in a safe environment.

During calibration:

-   Keep people away from moving parts.
-   Make sure the robot has enough space.
-   Keep the robot away from walls and obstacles.
-   Be ready to stop the OpMode.
-   Secure loose wires and hardware.
-   Start with conservative motor power.

Never assume a calibration value will work perfectly on a different
robot.

------------------------------------------------------------------------

# Troubleshooting

## The robot does not move

Check:

1.  Motor names in `OpenLoopBackend.java`
2.  Robot Configuration
3.  Motor connections
4.  Whether the correct OpMode was selected
5.  Whether the OpMode compiled successfully

------------------------------------------------------------------------

## The robot moves in the wrong direction

Run:

``` text
OpenLoop Motor Direction Debugger
```

Then correct the four `REVERSE_...` values.

------------------------------------------------------------------------

## The robot drives straight but the distance is wrong

Re-run the forward calibration tests.

Make sure the measured distances are entered correctly:

``` java
FORWARD_DIST_1_IN
FORWARD_DIST_2_IN
```

------------------------------------------------------------------------

## The robot strafes too far or too little

Re-run the mecanum strafe tests and update:

``` java
STRAFE_DIST_1_IN
STRAFE_DIST_2_IN
```

------------------------------------------------------------------------

## The robot turns too far or not far enough

Adjust:

``` java
TURN_TIME_90_RIGHT_MS
TURN_TIME_90_LEFT_MS
```

Do not change both unless both directions are inaccurate.

------------------------------------------------------------------------

## Autonomous changes as the battery gets lower

Make sure voltage compensation is enabled:

``` java
ENABLE_VOLTAGE_COMPENSATION = true;
```

and that:

``` java
CALIBRATION_VOLTAGE
```

matches the voltage measured during calibration.

------------------------------------------------------------------------

# Project Structure

``` text
OpenLoop/
├── FtcRobotController/
├── TeamCode/
│   └── src/main/java/org/firstinspires/ftc/teamcode/
│       ├── OpenLoopBackend.java
│       ├── OpenLoopMecanumAuto.java
│       ├── OpenLoopMecanumCalibration.java
│       ├── OpenLoopTankAuto.java
│       ├── OpenLoopTankCalibration.java
│       └── MotorDirectionDebugger.java
├── gradle/
├── build.gradle
├── settings.gradle
├── LICENSE
└── README.md
```


------------------------------------------------------------------------

# License

See the `LICENSE` file for licensing information.

------------------------------------------------------------------------

## OpenLoop

**Autonomous, Made Simple.**

**Simple. Intuitive. Accessible.**
