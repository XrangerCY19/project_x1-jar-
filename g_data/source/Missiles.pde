PImage Missile1;
Missiles missile1;
Missiles missile2;
Missiles missile3;

void initMissiles() {
  missile1 = new Missiles(random(1400, 1400));
  missile2 = new Missiles(random(2200, 2200));
  missile3 = new Missiles(random(3000, 3000));
}

void drawMissiles() {
  missile1.Draw();
  missile2.Draw();
  missile3.Draw();
}

class Missiles {
  float x;
  float y;
  float w;
  float h;
  float mspeed;
  int c;
  float homing;

  Missiles(float rx) {
    x = rx;
    y = random(50, 450);
    w = random(100, 120);
    h = random(30, 40);
    mspeed = speed * random(2.0, 2.5);
    c = int(random(0, 255));
    homing = 2.5;
    rotate = 0;
  }

  void Draw() {
    fill(c);
    stroke(255, 0, 0);
    strokeWeight(2);
    if (y > plane.y) { 
      y -= homing;
    } else if (y < plane.y) {       
      y += homing;
    }

    image(Missile1, x, y, w, h);
    x-=mspeed;
    if (x < -w) {
      x = random(2100, 2200);
      y = random(50,450);
      w = random(100, 120);
      h = random(30, 40);
      c = int(random(0, 255));
      mspeed = speed * random(2.0, 2.5);
    }

    if (plane.x + plane.w -10 > x && plane.x + 10 < x + w && plane.y + plane.h - 10 > y  && plane.y + 10< y + h) {
      speed = 0;
      gravity = 0;
      missile1.mspeed = 0;
      missile2.mspeed = 0;
      missile3.mspeed = 0;
      missile1.homing = 0;
      missile2.homing = 0;
      missile3.homing = 0;
      flag = true;
      fill(100);
      textSize(40);
      textAlign(CENTER,CENTER);
      text("SPACE\nto\nRESTART",width/2,height/2.5);
      homing = 0;
      //row = highScore.getRow(0);
      //row.setInt(1,coin);
      if(score > highscore) {
        highscore = score;
        if ( msql.connect()) {
             msql.query( "UPDATE login&reg set u_highscore = "+highscore+"where u_username = "+ username);
             msql.next();
        }
        //row.setInt(0,highscore);
        //saveTable(highScore,"Data/highscore.csv"); 
      }
      //saveTable(highScore,"Data/highscore.csv"); 
    }
  }
}
