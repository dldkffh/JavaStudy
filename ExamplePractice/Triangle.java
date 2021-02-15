package ch4CheckTime;

class Triangle {
	   double width;
	   double height;
	   Triangle() {       
	      this(0.0, 0.0); // ¨ç
	   }
	   Triangle(double width, double height) {       
	      this.width = width; // ¨è
	      this.height = height;
	   }
	   double area() { 
	      return width * height / 2;
	   }
	   Triangle biggerTriangle(Triangle t) {       
	      if(this.area() > t.area()) // ¨é
	return this; // ¨ê
	else return t;
	   }
	}

