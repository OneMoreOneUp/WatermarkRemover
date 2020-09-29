import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.commons.io.FilenameUtils;

/**
 * Takes all images from the UNCROPPED folder and crops them to the CROPPED folder (original images are not edited)
 * Currently supports removal of watermarks from: iFunny
 * 
 * Copyright (c) 2020, Matthew Crabtree
 * All rights reserved.
 * 
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 * 
 * @author Matthew Crabtree
 */
public final class ImageCropper {
	
	private static final String UNCROPPED_FOLDER = "./Uncropped";											//Location of where the uncropped folder is
	private static final String CROPPED_FOLDER = "./Cropped";												//Location of where the cropped folder is
	private static final String[] SUPPORTED_EXT = {".jpg", ".gif", ".png", ".bmp", ".webmp", ".jpeg"};		//Support image file extensions
	private static final int IFUNNY_WATERMARK_SIZE = 22;													//Size of the watermark in pixels

    public static void main(String[] args) {
    	File uncroppedDir = new File(UNCROPPED_FOLDER);					//Directory for uncropped images	
    	File croppedDir = new File(CROPPED_FOLDER);						//Directory for cropped images
    	
    	//Create directories if missing
		if(uncroppedDir.mkdirs()) System.err.println("[WARNING] Uncropped directory location was missing and has been created: " + UNCROPPED_FOLDER);
	    if(croppedDir.mkdirs()) System.err.println("[WARNING] Cropped directory location was missing and has been created: " + CROPPED_FOLDER);
    	
    	//Crop all supported images to the uncropped folder
        for(final File imgFile : uncroppedDir.listFiles()) {
        	String fileName = imgFile.getName();
        	if(supportsExt(fileName)) {
        		cropIFunnyImage(imgFile, croppedDir);
        	} else {
        		System.err.println("[WARNING] " + fileName + " is not of a supported image type and has been ignored.");
        	}
        }
        
        System.out.println("[SUCCESS] Program has cropped all supported images in: " + UNCROPPED_FOLDER + " . They have been moved to: " + CROPPED_FOLDER);
    }
    
    /**
     * Checks if the filename ends with each extension in the support extension list.
     * 
     * @param fileName - The filename to check the extension of
     * @return true iff the filename ends with a supported extension
     */
    private static boolean supportsExt(String fileName) {
    	for(String ext : SUPPORTED_EXT) if(fileName.toLowerCase().endsWith(ext)) return true;
    	return false;
    }
    
    /**
     * Removes the bottom of an image by 22 pixels (iFunny's watermark size is 20 pixels but bleeds an additional 1-3 pixels). The cropped version is saved to the cropped folder.
     * 
     * @param imgFile - The image file to crop
     * @param croppedDir - The directory to store the cropped image
     */
    private static void cropIFunnyImage(File imgFile, File croppedDir) {
		try {
			BufferedImage image = ImageIO.read(imgFile);
			BufferedImage img = image.getSubimage(0, 0, image.getWidth(), image.getHeight() - IFUNNY_WATERMARK_SIZE);	
			ImageIO.write(img, FilenameUtils.getExtension(imgFile.getName()), new File(croppedDir, imgFile.getName()));
		} catch (IOException e) {
			System.err.println("[ERROR] Could not crop file: " + imgFile.getName());
			System.err.println(e.getMessage());
		}
    }
}