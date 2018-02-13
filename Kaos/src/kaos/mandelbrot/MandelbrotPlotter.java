package kaos.mandelbrot;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

class MandelbrotPlotter {

    private Canvas canvas;
    private GraphicsContext gc;
    private PixelWriter pw;
    private double høyde, bredde;
    private int maxit = 100;
    private double zoom = bredde;
    private double xPos;
    private double yPos;
    private double creal, cimag, zcreal;

    public MandelbrotPlotter() {

        canvas = new Canvas(1200, 800);
        gc = canvas.getGraphicsContext2D();
        pw = gc.getPixelWriter();
        høyde = canvas.getHeight();
        bredde = canvas.getWidth();

        tegnMandelbrot();
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    public void zoomIn(double x, double y) {
        xPos += x;
        bredde *= 2;
        maxit += 50;

        for (int r = 0; r < bredde; r++) {
            for (int i = 0; i < høyde; i++) {
                xPos = r + x;
                creal = ((xPos - bredde / 3 * 2) * 3 / bredde);
                cimag = ((i - høyde / 2) * 3 / bredde);

                int it = 0;
                ComplexNumber z = new ComplexNumber(0, 0);
                ComplexNumber c = new ComplexNumber(creal, cimag);
                while (z.real * z.real + z.imag * z.imag < 4 && it < maxit) {
                    z = c.sum(z.multiply(z));
                    it++;
                }
                if (it < maxit) {
                    pw.setColor(r, i, Color.BLACK);
                } else {
                    pw.setColor(r, i, Color.WHITE);
                }
            }
        }
    }

    public void zoomOut(double x, double y) {
        bredde /= 2;
        maxit -= 50;

        for (int r = 0; r < bredde; r++) {
            for (int i = 0; i < høyde; i++) {
                creal = ((r - bredde / 3 * 2) * 3 / bredde);
                cimag = ((i - høyde / 2) * 3 / bredde);

                int it = 0;
                ComplexNumber z = new ComplexNumber(0, 0);
                ComplexNumber c = new ComplexNumber(creal, cimag);
                while (z.real * z.real + z.imag * z.imag < 4 && it < maxit) {
                    z = c.sum(z.multiply(z));
                    it++;
                }
                if (it < maxit) {
                    pw.setColor(r, i, Color.BLACK);
                } else {
                    pw.setColor(r, i, Color.WHITE);
                }
            }
        }
    }

    public void tegnMandelbrot() {
        for (int r = 0; r < bredde; r++) {
            for (int i = 0; i < høyde; i++) {
                creal = (r - bredde / 3 * 2) * 3 / bredde;
                cimag = (i - høyde / 2) * 3 / bredde;

                int it = 0;
                ComplexNumber z = new ComplexNumber(0, 0);
                ComplexNumber c = new ComplexNumber(creal, cimag);
                while (z.real * z.real + z.imag * z.imag < 4 && it < maxit) {
                    z = c.sum(z.multiply(z));
                    it++;
                }
                if (it < maxit) {
                    pw.setColor(r, i, Color.BLACK);
                } else {
                    pw.setColor(r, i, Color.WHITE);
                }
            }
        }
    }

    public double getHøyde() {
        return høyde;
    }

    public double getBredde() {
        return bredde;
    }

    static class ComplexNumber {

        private double real;
        private double imag;

        public ComplexNumber(double real, double imag) {
            this.real = real;
            this.imag = imag;
        }

        public ComplexNumber multiply(ComplexNumber cmn) {
            return new ComplexNumber(
                    this.real * cmn.real - this.imag * cmn.imag,
                    this.real * cmn.imag + this.imag * cmn.real
            );
        }

        public ComplexNumber sum(ComplexNumber cmn) {
            return new ComplexNumber(
                    this.real + cmn.real,
                    this.imag + cmn.imag
            );
        }

        public String toString() {
            return "real: " + this.real + " imag: " + this.imag;
        }

    }
}
