package com.akqa.kiev.conferer.server.dao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;

import com.akqa.kiev.conferer.server.model.Image;

public class ImageDao implements AbstractImageDao {

	@Value("${app.home}")
	private String appHome;

	@Value("${image.format}")
	String[] fileTypes;

	@Override
	public void save(Image image) {

		if (!Arrays.asList(fileTypes).contains(image.getFormat())) {
			throw new ImageDaoException("Not supported format "
					+ image.getFormat());
		}

		try {

			File imagefile = new File(getAppHomeRoot(),
					generateFileName(image));

			if (!imagefile.exists()) {
				imagefile.createNewFile();
			}

			FileOutputStream os = new FileOutputStream(imagefile);
			os.write(image.getData());
			os.close();

		} catch (IOException e) {
			throw new ImageDaoException("Unable to save image", e);
		}
	}

	public boolean exists(BigInteger id) {

		File[] matchingFiles = loadFile(id);

		if (matchingFiles.length != 0) {
			return true;
		}

		return false;
	}

	@Override
	public Image findOne(BigInteger id) {
		File[] matchingFiles = loadFile(id);

		ByteArrayOutputStream baos;
		try {
			FileInputStream is = new FileInputStream(matchingFiles[0]);
			baos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			for (int readNum; (readNum = is.read(buf)) != -1;) {
				baos.write(buf, 0, readNum);
			}
			is.close();
		} catch (IOException e) {
			throw new ImageDaoException("Unable to load image", e);
		}

		Image im = new Image();
		im.setId(id);
		im.setData(baos.toByteArray());

		return im;
	}

	@Override
	public void delete(BigInteger id) {

		File[] matchingFiles = loadFile(id);
		for (File f : matchingFiles) {
			f.delete();
		}
	}

	private File[] loadFile(final BigInteger id) {
		File[] matchingFiles = getAppHomeRoot().listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return String.valueOf(id).equals(
						FilenameUtils.getBaseName(name));
			}
		});
		return matchingFiles;
	}

	public File getAppHomeRoot() {
		File userHomeDir = new File(System.getProperty("user.home"));
		File appHomeDir = new File(userHomeDir, appHome);
		if (!appHomeDir.exists()) {
			appHomeDir.mkdirs();
		}
		return appHomeDir;
	}

	private String generateFileName(Image image) {
		String string = image.getId().toString();
		return string + FilenameUtils.EXTENSION_SEPARATOR + image.getFormat();
	}

	public String getAppHome() {
		return appHome;
	}

	public void setAppHome(String appHome) {
		this.appHome = appHome;
	}
}
