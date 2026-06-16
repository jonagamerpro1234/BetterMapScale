package jss.bettermapscale.map;

public class MapColorResolver {

    public static byte getColorFromHeight(int height) {
        if(height < 50){
            return 20;
        }
        if(height < 70){
            return 40;
        }
        if(height < 90){
            return 60;
        }
        if(height < 120){
            return 80;
        }
        return 100;
    }

}
