PImage player1;

class Plane {
  float x;
  float y;
  float w;
  float h;
  float rotate = 0.1;
  float speed;

  Plane() {
    x = 50;
    y = 50;
    w = 130;
    h = 60;
    speed =12;
  }

  void Draw() {    
    noFill();
    translate(x, y);
    if(x >= 0 && flag == false) {
      if (keyPressed) {
        if (keyCode == UP) {
          rotate(-0.1);
          image(player1, 0, 0, w, h);
          rotate(0.1);
          y-=speed*1.3;
        } else if (keyCode == DOWN) {
          rotate(0.2);
          y+=speed*0.8;
          image(player1, 0, 0, w, h);
          rotate(-0.1);
        } else if (keyCode == RIGHT) {
          rotate(0.1);
          x+=speed/2;
          y-=speed/2;
          image(player1, 0, 0, w, h);
          rotate(-0.1);
        } else if (keyCode == LEFT) {
          rotate(-0.1);
          x-=speed;
          image(player1, 0, 0, w, h);
          rotate(0.1);
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
