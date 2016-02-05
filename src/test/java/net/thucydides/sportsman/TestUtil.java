package net.thucydides.sportsman;

import net.thucydides.core.Thucydides;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.jbehave.ThucydidesJUnitStories;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.sun.org.apache.xml.internal.security.utils.JavaUtils.getBytesFromFile;

public class TestUtil {

	public static Object getFromSession(String key) {
		return Thucydides.getCurrentSession().get(key);

	}

	public static void putInSession(String key, Object value) {
		@SuppressWarnings({"unchecked", "unused"})
		Object put = Thucydides.getCurrentSession().put(key, value);

	}

	public static String getProjectFolder() {
		String folder = "";
		EnvironmentVariables properties = new ThucydidesJUnitStories().getEnvironmentVariables();
		folder = properties.getProperty("project.folder", System.getProperty("user.dir"));
		return folder;
	}

	public static boolean compareImages(String expectedFile, String actualFile)
			throws IOException {
		BufferedImage img1 = ImageIO.read(new File(expectedFile));
		BufferedImage img2 = ImageIO.read(new File(actualFile));
		if (img1.getWidth() == img2.getWidth()
				&& img1.getHeight() == img2.getHeight()) {
			for (int x = 0; x < img1.getWidth(); x++) {
				for (int y = 0; y < img1.getHeight(); y++) {
					if (img1.getRGB(x, y) != img2.getRGB(x, y))
						return false;
				}

			}
		} else {
			return false;
		}
		return true;
	}


	public static boolean comparePdf(String fileActual, String fileExpected)
			throws IOException {
		byte[] b1 = getBytesFromFile(fileActual);
		byte[] b2 = getBytesFromFile(fileExpected);
		if(b1.length != b2.length) return false;
		for(int i = 0; i < b1.length; i++) {
			if(b1[i] != b2[i]) return false;
		}
		return true;

	}


}
