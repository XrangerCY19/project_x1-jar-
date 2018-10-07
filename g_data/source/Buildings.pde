
void initBuildings() {
  building = new Buildings[25];
  bX = 0;
  float bw = 0;
  for (int i=0; i<building.length; i++) {
    bW = random(60, 120);
    bH = random(40, 230);
    bX+=bw - (bw/2-bW/2);
    building[i] = new Buildings(bX, bW, bH);
    bw = bW;
  }
}

void drawBuildings() {
  for (int i=0; i<building.length; i++) {    
    building[i].Draw();
    building[i].move();
  }
}

class Buildings {
  boolean f1 = false;
  boolean f2= false;
  boolean f3 = false;
  boolean f4 = false;
  float x;
  float y;
  float w;
  float h;
  int r;
  int g;
  int b;
  int sr;
  int sg;
  int sb;

  Buildings(float a, float b, float c) {
    x = a;
    w = b;
    h = c;
    y = 680 - h/2;
    r = int(random(0, 155));
    g = int(random(0, 155));
    b = int(random(0, 155));
    sr = int(random(0, 100));
    sg = int(random(0, 100));
    sb = int(random(0, 100));
    if (random(0.0, 1.0) >= 0.01 ) {
      f1 = true;
    }
    if (random(0.0, 1.0) >= 0.01 ) {
      f2 = true;
    }
    if (random(0.0, 1.0) >= 0.1 ) {
      f3 = true;
    }
    if (random(0.0, 1.0) >= 0.1 ) {
      f4 = true;
    }
  }

  void Draw() {
    fill(r, g, b);
    strokeWeight(4);
    stroke(sr, sg, sb);
    rect(x, y, w, h);

    strokeWeight(2);
    stroke(r-10, g-10, b-10);
    fill(#DBF56A);
    if (f1)
      rect(x-w/4, y-h/4, w/4, h/5);
    if (f2)
      rect(x-w/4, y+h/4, w/4, h/5);
    if (f3)
      rect(x+w/4, y+h/4, w/4, h/5);
    if (f4)
      rect(x+w/4, y-h/4, w/4, h/5);
  }

  void move() {
    x-=speed;
    if (x < -w/2) {
      x = bX-w/2;
      h = random(40, 230);
      y = 680 - h/2;
      r = int(random(0, 255));
      g = int(random(0, 255));
      b = int(random(0, 255));
      sr = int(random(0, 101));
      sg = int(random(0, 100));
      sb = int(random(0, 100));
    }
    
    if(plane.y+plane.h > y - h/2 +20) {
      if(plane.x+plane.w + 5> x - w/2 && plane.x +20 < x + w/2) {
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
         text("SPACE",width/2,height/2.5);
         text("TO",width/2,height/2.2);
         text("RESTART",width/2,height/2);
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
}
