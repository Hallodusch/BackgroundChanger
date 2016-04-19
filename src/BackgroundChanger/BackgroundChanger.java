package BackgroundChanger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class BackgroundChanger extends Thread{
	
	static final String SAVE_LOCATION = "..";

	
	public static void main(String[] args) {

		new MainWindow(new Dimension(600,400));

	}

	static File convertToJPG(File f){
		String directory = f.getParent();
		String name = f.getName();
		boolean isJpg;
		System.out.println(name);

		try{
			isJpg = ".jpg".equals(name.substring(name.indexOf("."), name.length()));
		}catch(StringIndexOutOfBoundsException e){
			isJpg = false;
			System.out.println("not jpg");
		}

		//if the image is jpg already
		if(isJpg){
			return f;
		}else{
			//save the file name
			name = name.substring(0, name.indexOf("."));
			try {
				//read image file
                BufferedImage bufferedImage = ImageIO.read(f);

				// create a blank, RGB, same width and height, and a white background
				BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
						bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
				newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

				// write to jpeg file
				File newFile =  new File(directory + "\\" + name + ".jpg");
				ImageIO.write(newBufferedImage, "jpg", newFile);

				System.out.println("Done");
				return newFile;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;

	}

    //make the local path to the image
    static String makeLocalPath(String linkToImage){
        return SAVE_LOCATION.concat("/wallpaper.").concat(getSuffix(linkToImage.toString()));
    }

    //build the reddit URL with the filters
    static String makeRedditUrl(String subreddit){
        return "http://www.reddit.com/r/".concat(subreddit).concat("/search.json?limit=1&restrict_sr=true&sort=new");
    }

    //return the suffix of the image (e.g. 'jpg')
    private static String getSuffix(String url){
        return url.substring(url.lastIndexOf(".") + 1, url.length());
    }

    public static void setImageFromReddit(String subreddit){


        API api = new RedditAPI();

        //Get the URL to the actual image
        URL linkToImage = api.giveLinkToImage(makeRedditUrl(subreddit));

        //Get the image data from the link
        BufferedImage image = api.requestData(linkToImage);


        if(image instanceof BufferedImage){
            try{
                //Save the image to the local hard disk
                saveToDisk(image, linkToImage);

                //change the background
                Changer.changeBackground(new File(makeLocalPath(linkToImage.toString())));

            }catch(IOException e){
            }catch(NullPointerException e){
                e.printStackTrace();
            }

        }

    }

    static void saveToDisk(BufferedImage img, URL path) throws IOException{

        //configure image correctly and prepare for writing
        BufferedImage bImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D bGr = bImage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        File outputFile = new File(makeLocalPath(path.toString()));
        try {
//			System.out.println(makeLocalPath(path.toString()));

            //save the image into outputfile
            ImageIO.write(bImage, getSuffix(path.toString()), outputFile);
        } catch (IOException e) {
            throw new IOException();
        }
    }


}
