
void drawbackground() {
  background(#8AC3D8);
  mount();
  noStroke();
  fill(#6F4817);
  rect(width/2, 760, width, 160);
  fill(#4F2800);
  rect(width/2, 695, width, 30);
  stroke(0);
  strokeWeight(1);
  line(0, 680, width, 680);
  for(int i=0; i<clouds.length;i++) {
    clouds[i].move();
  }
}

void initBackground() {
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

  void resetCoords()
  {    
    x = random(1400, 1800);
    y = random(0, 300);
    size = random(50, 90);
  }

  void move()
  {
    fill(255);
    noStroke();
    ellipseMode(CENTER);
    ellipse(x, y, size, size);
    ellipse(x+size/2, y+size/3, size*0.9, size*0.9);
    ellipse(x, y+size/1.5, size*0.85, size*0.85);
    ellipse(x-size/1.5, y+size/4, size*0.9, size*0.9);
    ellipse(x-size/1.4, y+size/1.5, size*1.1, size*0.7);
    ellipse(x-size*1.25, y+size/2, size*0.75, size*0.75);
    x -= speed*(y/450)+0.5;    
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
void mount()
{
  fill(#85D1B2);
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
