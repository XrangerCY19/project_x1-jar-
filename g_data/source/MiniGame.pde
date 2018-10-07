import de.bezier.data.sql.*;
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
float rotate = 0.1;
float gravity = 5;
float speed = 10;
int highscore;
int score = 0;
int coin;

void setup() {
  fullScreen();
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

void draw() {
  speed = speed*1.001;
  score+=ceil(speed/10);
  drawbackground();  
  drawBuildings();
  //devMode();
  drawCoins();
  drawTexts();
  drawMissiles();
  plane.Draw();
}

void drawTexts() {
  fill(100, 255, 100);
  textSize(50);
  textAlign(CENTER, CENTER);
  text(score, width/2, 100);
  fill(100, 150, 250);
  textSize(30);
  text("HighScore:"+highscore, width/2, 50);
  textAlign(LEFT, CENTER);
  textSize(30);
  fill(#EDDA2D);
  text("Coins:"+coin, 10, 50);
}

void devMode() {
  fill(200, 50, 50);
  textSize(12);
  textAlign(CENTER, CENTER);
  text("X:"+mouseX+" Y:"+mouseY+" FR:"+frameRate, width/2, 10);
  textAlign(LEFT, CENTER);
  text("Speed:"+speed+"\nM1Speed"+missile1.mspeed+"\nM2Speed"+missile2.mspeed+"\nM3Speed"+missile3.mspeed, 10, 300);
}

void loadStuffs() {
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

void connectSQL() {
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
