package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.lang.Integer;
import java.util.function.DoubleToIntFunction;


public class Main {
    static final int centroids = 100;
    static int HEIGHT = 1080;
    static int WIDTH = 1920;
    static CentroidLocation[] centroidLocations = new CentroidLocation[centroids];

    public static void main(String[] args) throws IOException {
        BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        for (int i=0; i<1; i++){
            img = fillImage(img, Color.LIGHT_GRAY);
            img = setCentroids(img);
            img = drawCells(img);

            //write image
            File f = new File("cells" + i + ".jpg");
            ImageIO.write(img, "jpg", f);
        }
    }

    static BufferedImage draw(BufferedImage img, int x, int y, Color c){
        try {
            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < 3; i++) {
                    img.setRGB(x - 1 + i, y - 1 + j, c.getRGB());
                }
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException e){

        }
        return img;
    }

    static double round(double value) {

        value = value * 1;
        long tmp = Math.round(value);
        return (double) tmp / 1;
    }

    static BufferedImage fillImage(BufferedImage img, Color c){
        for (int y=0; y<HEIGHT; y++){
            for (int x=0; x<WIDTH; x++){
                img.setRGB(x,y, c.getRGB());
            }
        }
        return img;
    }

    static BufferedImage setCentroids(BufferedImage img){
        Random rnd = new Random();

        Color c = new Color(0, 0,0);

        for (int i=0; i<centroids; i++) {
            int x = (int) (Math.random() * WIDTH);
            int y = (int) (Math.random() * HEIGHT);
            img = draw(img, x,y, Color.BLACK);
            //img.setRGB(x, y, (Color.BLACK).getRGB());
            //img.setRGB(x,y, c.getRGB());
            centroidLocations[i] = new CentroidLocation(x,y);
        }

        return img;
    }

    static BufferedImage drawCells(BufferedImage img){
        for (int y=0; y<HEIGHT; y++){
            for (int x=0; x<WIDTH; x++){

                double distance = Integer.MAX_VALUE;
                int nearestIndex = centroids+1;

                for (int i=0; i<centroidLocations.length; i++){ //find nearest Centroid
                    double tmp = Math.sqrt(Math.pow((x-centroidLocations[i].getX()),2) + Math.pow((y-centroidLocations[i].getY()),2)); //calculate distance
                    if (tmp < distance){
                        nearestIndex = i;
                        distance = tmp;

                    }
                }
                for (int j=0; j<centroidLocations.length; j++){ //check if there is another Centroid equidistant to the first
                    double tmp = Math.sqrt(Math.pow((x-centroidLocations[j].getX()),2) + Math.pow((y-centroidLocations[j].getY()),2)); //calculate distance
                    if (round(tmp) == round(distance) & j != nearestIndex){
                        img = draw(img, x,y, Color.BLUE);
                        //img.setRGB(x, y, (Color.RED).getRGB());
                    }
                }


            }
        }
        return img;
    }

    //TODO: Function readImage which generates a centroidMap from a given image
}
