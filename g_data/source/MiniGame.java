import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import de.bezier.data.sql.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class MiniGame extends PApplet {


Buildings building[];
Cloud clouds[];
Plane plane;
MySQL msql;

TableRow row;
boolean flag = false;
Table highScore;
float bX;
float bW;
float bH;
float rotate = 0.1f;
float gravity = 5;
float speed = 10;
int highscore;
int score = 0;
int coin;

public void setup() {
  
  frameRate(30);  
  rectMode(CENTER);
  loadStuffs();
  initBackground();
  initBuildings();
  initCoins();
  plane = new Plane();
  initMissiles();
  connectSQL();
}

public void draw() {
  speed = speed*1.001f;
  score+=ceil(speed/10);
  drawbackground();  
  drawBuildings();
  //devMode();
  drawCoins();
  drawTexts();
  drawMissiles();
  plane.Draw();
}

public void drawTexts() {
  fill(100, 255, 100);
  textSize(50);
  textAlign(CENTER, CENTER);
  text(score, width/2, 100);
  fill(100, 150, 250);
  textSize(30);
  text("HighScore:"+highscore, width/2, 50);
  textAlign(LEFT, CENTER);
  textSize(30);
  fill(0xffEDDA2D);
  text("Coins:"+coin, 10, 50);
}

public void devMode() {
  fill(200, 50, 50);
  textSize(12);
  textAlign(CENTER, CENTER);
  text("X:"+mouseX+" Y:"+mouseY+" FR:"+frameRate, width/2, 10);
  textAlign(LEFT, CENTER);
  text("Speed:"+speed+"\nM1Speed"+missile1.mspeed+"\nM2Speed"+missile2.mspeed+"\nM3Speed"+missile3.mspeed, 10, 300);
}

public void loadStuffs() {
  player1 = loadImage("player01.png");
  Missile1 = loadImage("missile01.png");
  //highScore = loadTable("highscore.csv");
  //row = highScore.getRow(0);
  //highscore = row.getInt(0);
  //coin = row.getInt(1);
}

String user     = "root";
String pass     = "";
String database = "game";
String username = "";

public void connectSQL() {
  msql = new MySQL( this, "localhost", database, user, pass );
  if ( msql.connect()) {
    while (msql.next())
    {
      username = msql.getString("u_username");
      highscore = msql.getInt("u_highscore");
      if (msql.getInt("loged") == 1) {
        break;
      }
    }
    msql.query( "UPDATE login&reg set u_highscore = "+highscore+"where u_username = "+ username);
    msql.next();
  } else {
    println("DataBase not Connected");
  }
}


public void drawbackground() {
  background(0xff8AC3D8);
  mount();
  noStroke();
  fill(0xff6F4817);
  rect(width/2, 760, width, 160);
  fill(0xff4F2800);
  rect(width/2, 695, width, 30);
  stroke(0);
  strokeWeight(1);
  line(0, 680, width, 680);
  for(int i=0; i<clouds.length;i++) {
    clouds[i].move();
  }
}

public void initBackground() {
  clouds = new Cloud[5];
  for(int i=0; i<clouds.length;i++) {
    clouds[i] = new Cloud();
  }
}

class Cloud
{
  float x = random(0, 1400);
  float y = random(50, 250);
  float size = random(50, 90);

  public void resetCoords()
  {    
    x = random(1400, 1800);
    y = random(0, 300);
    size = random(50, 90);
  }

  public void move()
  {
    fill(255);
    noStroke();
    ellipseMode(CENTER);
    ellipse(x, y, size, size);
    ellipse(x+size/2, y+size/3, size*0.9f, size*0.9f);
    ellipse(x, y+size/1.5f, size*0.85f, size*0.85f);
    ellipse(x-size/1.5f, y+size/4, size*0.9f, size*0.9f);
    ellipse(x-size/1.4f, y+size/1.5f, size*1.1f, size*0.7f);
    ellipse(x-size*1.25f, y+size/2, size*0.75f, size*0.75f);
    x -= speed*(y/450)+0.5f;    
    if (x < - 150)
    {
      resetCoords();
    }
  }
}

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
float xPos = 0;
float xPos2 = 1300;
float yPos = 610;
public void mount()
{
  fill(0xff85D1B2);
  noStroke();
  triangle(xPos,yPos,xPos+200,yPos-300,xPos+500,yPos);
  triangle(xPos+350,yPos,xPos+600,yPos-150,xPos+800,yPos);
  triangle(xPos+550,yPos,xPos+850,yPos-300,xPos+1150,yPos);
  triangle(xPos+1000,yPos,xPos+1200,yPos-100,xPos+1360,yPos);
  triangle(xPos2-50,yPos,xPos2+225,yPos-300,xPos2+450,yPos);
  triangle(xPos2+350,yPos,xPos2+600,yPos-150,xPos2+800,yPos);
  triangle(xPos2+550,yPos,xPos2+850,yPos-300,xPos2+1150,yPos);
  triangle(xPos2+1000,yPos,xPos2+1200,yPos-100,xPos2+1360,yPos);
  rect(width/2,645,width,80);
  if(xPos > -width)
  {
    xPos-=speed/5;
  }
  if(xPos <=-width)
  {
    xPos = width;
  }
  if(xPos2 > -width)
  {
    xPos2-=speed/5;
  }
  if(xPos2 <=-width)
  {
    xPos2 = width;
  }
}

public void initBuildings() {
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

public void drawBuildings() {
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
    r = PApplet.parseInt(random(0, 155));
    g = PApplet.parseInt(random(0, 155));
    b = PApplet.parseInt(random(0, 155));
    sr = PApplet.parseInt(random(0, 100));
    sg = PApplet.parseInt(random(0, 100));
    sb = PApplet.parseInt(random(0, 100));
    if (random(0.0f, 1.0f) >= 0.01f ) {
      f1 = true;
    }
    if (random(0.0f, 1.0f) >= 0.01f ) {
      f2 = true;
    }
    if (random(0.0f, 1.0f) >= 0.1f ) {
      f3 = true;
    }
    if (random(0.0f, 1.0f) >= 0.1f ) {
      f4 = true;
    }
  }

  public void Draw() {
    fill(r, g, b);
    strokeWeight(4);
    stroke(sr, sg, sb);
    rect(x, y, w, h);

    strokeWeight(2);
    stroke(r-10, g-10, b-10);
    fill(0xffDBF56A);
    if (f1)
      rect(x-w/4, y-h/4, w/4, h/5);
    if (f2)
      rect(x-w/4, y+h/4, w/4, h/5);
    if (f3)
      rect(x+w/4, y+h/4, w/4, h/5);
    if (f4)
      rect(x+w/4, y-h/4, w/4, h/5);
  }

  public void move() {
    x-=speed;
    if (x < -w/2) {
      x = bX-w/2;
      h = random(40, 230);
      y = 680 - h/2;
      r = PApplet.parseInt(random(0, 255));
      g = PApplet.parseInt(random(0, 255));
      b = PApplet.parseInt(random(0, 255));
      sr = PApplet.parseInt(random(0, 101));
      sg = PApplet.parseInt(random(0, 100));
      sb = PApplet.parseInt(random(0, 100));
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
         text("SPACE",width/2,height/2.5f);
         text("TO",width/2,height/2.2f);
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
coin coins[];

public void initCoins() {
  coins = new coin[20];
  for (int i=0; i<coins.length; i++) {
    coins[i] = new coin();
  }
}

public void drawCoins() {
  for (int i=0; i<coins.length; i++) {
    coins[i].Draw();
  }
}

class coin {
  float x;
  float y;
  float w;
  float h;
  int i =0;
  boolean hit = false;

  coin() {
    x = random(1300, 10000);
    y = random(30, 400);
    w = 20;
    h = 20;
  }

  public void Draw() {
    if (hit) {
      fill(0, 0);
      noStroke();
    } else {
      fill(0xffEDDA2D);
      stroke(0xff9D8A0D);
    }
    ellipse(x, y, w, h);
    x-=speed;

    if (x < -w) {
      x = random(1300, 10000);
      y = random(30,400);
      i = 0;
      hit = false;
    }
    
    if (plane.x + plane.w > x && plane.x  < x + w && plane.y + plane.h  > y  && plane.y < y + h) {
      if(i==0){
        coin++;
        i=1;
      }
      hit = true;
    }
  }
}
PImage Missile1;
Missiles missile1;
Missiles missile2;
Missiles missile3;

public void initMissiles() {
  missile1 = new Missiles(random(1400, 1400));
  missile2 = new Missiles(random(2200, 2200));
  missile3 = new Missiles(random(3000, 3000));
}

public void drawMissiles() {
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
    mspeed = speed * random(2.0f, 2.5f);
    c = PApplet.parseInt(random(0, 255));
    homing = 2.5f;
    rotate = 0;
  }

  public void Draw() {
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
      c = PApplet.parseInt(random(0, 255));
      mspeed = speed * random(2.0f, 2.5f);
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
      text("SPACE\nto\nRESTART",width/2,height/2.5f);
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
public void keyPressed() {
  if(key == ' ' && flag) {
    speed = 10;
    gravity = 5;
    missile1.mspeed = speed * random(2.0f,2.5f);
    missile2.mspeed = speed * random(2.0f,2.5f);
    missile3.mspeed = speed * random(2.0f,2.5f);
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
    missile1.homing = 2.5f;
    missile2.homing = 2.5f;
    missile3.x = random(3000, 3000);
    missile3.y = random(40, 450);
    missile3.w = random(100, 120);
    missile3.h = random(30, 40);
    missile3.homing = 2.5f;
    score = 0;
    flag = false;
    initCoins();
    initBuildings();
  }
}
PImage player1;

class Plane {
  float x;
  float y;
  float w;
  float h;
  float rotate = 0.1f;
  float speed;

  Plane() {
    x = 50;
    y = 50;
    w = 130;
    h = 60;
    speed =12;
  }

  public void Draw() {    
    noFill();
    translate(x, y);
    if(x >= 0 && flag == false) {
      if (keyPressed) {
        if (keyCode == UP) {
          rotate(-0.1f);
          image(player1, 0, 0, w, h);
          rotate(0.1f);
          y-=speed*1.3f;
        } else if (keyCode == DOWN) {
          rotate(0.2f);
          y+=speed*0.8f;
          image(player1, 0, 0, w, h);
          rotate(-0.1f);
        } else if (keyCode == RIGHT) {
          rotate(0.1f);
          x+=speed/2;
          y-=speed/2;
          image(player1, 0, 0, w, h);
          rotate(-0.1f);
        } else if (keyCode == LEFT) {
          rotate(-0.1f);
          x-=speed;
          image(player1, 0, 0, w, h);
          rotate(0.1f);
        } else image(player1, 0, 0, w, h);
      } else image(player1, 0, 0, w, h);
    } else image(player1, 0, 0, w, h);
    if (x <= 0) {   
      x=0;
    }
    if (y < 0) {   
      y=0;
    }
    if (x + w > width) {   
      x = width - w;
    }
    y+=gravity;
    translate(-x, -y);
  }
}
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "MiniGame" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
