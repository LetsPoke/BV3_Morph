// BV Ue3 WS2019/20 Vorgabe
//
// Copyright (C) 2017 by Klaus Jung
// All rights reserved.
// Date: 2017-07-15

package bv_ws20;

public class MorphologicFilter {
	
	public enum FilterType { 
		DILATION("Dilation"),
		EROSION("Erosion");
		
		private final String name;       
	    private FilterType(String s) { name = s; }
	    public String toString() { return this.name; }
	};
	
	// filter implementations go here:
	
	public void copy(RasterImage src, RasterImage dst) {
		// TODO: just copy the image
		for(int y=0; y<src.height; y++){
			for(int x=0; x<src.width; x++){
				int pos = y * src.width + x;
				dst.argb[pos] = src.argb[pos];
			}
		}
	}
	
	public void dilation(RasterImage src, RasterImage dst, double radius) {
		// TODO: dilate the image using a structure element that is a neighborhood with the given radius
		for(int y=0; y<src.height; y++){
			for(int x=0; x<src.width; x++){
				int pos = y * src.width + x;

				if(src.argb[pos] == 0xFF000000){

					int x1 = x - (int)radius;
					int x2 = x + (int)radius;
					int y1 = y - (int)radius;
					int y2 = y + (int)radius;

					if(x1 < 0){ x1 = 0; }
					if(x2 > src.width-1){ x2 = src.width-1; }

					if(y1 < 0){ y1 = 0; }
					if(y2 > src.height-1){ y2 = src.height-1; }

					for(int i=y1; i<=y2; i++){
						for(int j=x1; j<=x2; j++){
							double distance = Math.sqrt((j-x)*(j-x)+(i-y)*(i-y)); //Kreisgleichung
							//Pixel im Kernel
							if(distance<=radius){
								int pos_n = i * dst.width + j;
								dst.argb[pos_n] = src.argb[pos];
							}
						}
					}
				}else {
					dst.argb[pos] = src.argb[pos];
				}
			}
		}
	}
	
	public void erosion(RasterImage src, RasterImage dst, double radius) {
		// TODO: erode the image using a structure element that is a neighborhood with the given radius
		src.invert(); // srx invertieren
		dilation(src,dst,radius); //dilatation mit dem invertierten bild
		dst.invert(); // zielbild zurück invertieren
		src.invert(); // src bild zurück invertieren
	}
	
	
	
	

}
