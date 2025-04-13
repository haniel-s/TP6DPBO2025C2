import java.awt.*;

public class Pipe {
        private int posX;
        private int posY;
        private int width;
        private int height;
        private Image image;
        private int velocityX;
        boolean passed = false;

        public Pipe(int posX,int posY, int width, int height, Image image){
            this.posX = posX;
            this.posY = posY;
            this.width = width;
            this.height = height;
            this.image = image;

            this.velocityX = -10;
            this.passed = false;
        }

        public int getPosX() {
            return posX;
        }

        public void setPosX(int posX) {
            this.posX = posX;
        }

        public int getPosY() {
            return posY;
        }

        public void setPosY(int posY) {
            this.posY = posY;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public Image getImage() {
            return image;
        }

        public void setImage(Image image) {
            this.image = image;
        }

        public int getVelocityX() {
            return velocityX;
        }

        public void setvelocityX(int velocityX) {
            this.velocityX = velocityX;
        }

        public boolean getpassed() {
        return passed;
        }

        public void setpassed(boolean passed) {
        this.passed = passed;
        }


}
