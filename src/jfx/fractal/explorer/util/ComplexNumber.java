package jfx.fractal.explorer.util;


public class ComplexNumber  {
	public static final ComplexNumber ONE = new ComplexNumber(1.0,0.0);
	
	private double r;
	private double i;
	
	public ComplexNumber(double r,double i) {
		this.r = r;
		this.i = i;
	}

	public ComplexNumber(ComplexNumber c) {
		this(c.r,c.i);
	}
	
	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}

	public double getI() {
		return i;
	}

	public void setI(double i) {
		this.i = i;
	}
	
	@Override
	public String toString() {
		if (i == 0) return r + "";
        if (r == 0) return i + "i";
        if (i <  0) return r + " - " + (-i) + "i";
        return r + " + " + i + "i";
	}
	
	public double abs() {
        return Math.hypot(r, i);
    }
	
	public double phase() {
        return Math.atan2(i, r);
    }
	
	public ComplexNumber plus(ComplexNumber c) {
	       return new ComplexNumber(this.r+c.r,this.i+c.i);
	}
	
	public ComplexNumber minus(ComplexNumber c) {
	       return new ComplexNumber(this.r-c.r,this.i-c.i);
	}
	
	public ComplexNumber times(ComplexNumber c) {
		double re = this.r*c.r-this.i*c.i;
		double im = this.r*c.i + c.r*this.i;
		
		return new ComplexNumber(re,im);
	}
	
	public ComplexNumber scale(double scale) {
		return new ComplexNumber(this.r*scale,this.i*scale);
	}
	
	public ComplexNumber conjugate() {
		return new ComplexNumber(this.r,-this.i);
	}
	
	public ComplexNumber reciprocal() {
		double scale = r*r+i*i;
		return new ComplexNumber(r/scale,-i/scale);
	}
	
	public ComplexNumber divide(ComplexNumber c) {
		return this.times(c.reciprocal());
	}
	
	public double distance(ComplexNumber c) {
		return this.minus(c).abs();
	}
	
	public ComplexNumber pow(int power) {
		double module = Math.pow(abs(), power);
		double phase = phase() * power;
		return new ComplexNumber(module * Math.cos(phase), module * Math.sin(phase));
	}
	
	public ComplexNumber[] nroots(int n) {
		ComplexNumber[] nroots = new ComplexNumber[n];
		
		double module = Math.pow(abs(), 1.0 / n);
		double phase = phase();

		for (int k = 0; k < n; k++) {
			double phaseTemp = (phase + 2 * Math.PI * k) / n;
			nroots[k] = new ComplexNumber(module * Math.cos(phaseTemp), module * Math.sin(phaseTemp));
		}
		
		return nroots;
	}
}
