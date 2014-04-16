public class Simulation {
    public static void main() {
    }

    public void run() {
        myCanvas.setVisible(true);
        Dimension size = myCanvas.getSize();
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, size.getWidth(), size.getHeight());

        // create and show my balls
        BouncingBall[] balls = new BouncingBall[nBalls];
        for (int i = 0; i < nBalls; i++) {
            double x = (0.1 + 0.8*Math.random()) * size.getWidth();
            double y = (0.1 + 0.4*Math.random()) * size.getHeight();
            double r = (1.0 + Math.random()) * 24;
            Color c = new Color((int)(0xffffff * Math.random()));
            BouncingBall ball = new BouncingBall(x, y, r, c);
            balls[i] = ball;
            System.out.println(ball);
        }

        // make them bounce
        double ySpeeds = 1.0;
        long timePrev = System.nanoTime();
        while (ySpeeds > 0) {
            long timeStart = System.nanoTime();
            double t = (timeStart - timePrev)/1e9;
            // draw frame
            for (BouncingBall ball : balls) {
                ball.simulate(t);
                ball.collide(bounds);
                ball.draw(myCanvas);
            }
            myCanvas.flip();
            timePrev = timeStart;
        }
    }

}
