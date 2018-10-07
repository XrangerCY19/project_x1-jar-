coin coins[];

void initCoins() {
  coins = new coin[20];
  for (int i=0; i<coins.length; i++) {
    coins[i] = new coin();
  }
}

void drawCoins() {
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

  void Draw() {
    if (hit) {
      fill(0, 0);
      noStroke();
    } else {
      fill(#EDDA2D);
      stroke(#9D8A0D);
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
