void keyPressed() {
  if(key == ' ' && flag) {
    speed = 10;
    gravity = 5;
    missile1.mspeed = speed * random(2.0,2.5);
    missile2.mspeed = speed * random(2.0,2.5);
    missile3.mspeed = speed * random(2.0,2.5);
    plane.x = 50;
    plane.y = height/3;
    missile1.x = random(1400, 1400);
    missile1.y = random(50, 450);
    missile1.w = random(100, 120);
    missile1.h = random(30, 40);
    missile2.x = random(2200, 2200);
    missile2.y = random(40, 450);
    missile2.w = random(100, 120);
    missile2.h = random(30, 40);
    missile1.homing = 2.5;
    missile2.homing = 2.5;
    missile3.x = random(3000, 3000);
    missile3.y = random(40, 450);
    missile3.w = random(100, 120);
    missile3.h = random(30, 40);
    missile3.homing = 2.5;
    score = 0;
    flag = false;
    initCoins();
    initBuildings();
  }
}
