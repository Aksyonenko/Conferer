package com.akqa.kiev.conferer.server.dao.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.akqa.kiev.conferer.server.model.Image;

/**
 * File system based implementation of images DAO.
 * <p>
 * Images are stored in ${user.home}/${app.home} directory.
 */
@Component
public class ImageDao<ID extends Serializable> implements AbstractImageDao<ID> {

	private static final String USER_HOME_PROPERTY = "user.home";

	@Value("${app.home}")
	private String appHome;

	@Value("${image.format}")
	private String[] supportedFileTypes;

	@Override
	public void save(Image<ID> image) {

		validateImageFormat(image);

		try {
			File imageFile = new File(getAppHomeRoot(), generateFileName(image));
			if (!imageFile.exists()) {
				imageFile.createNewFile();
			}

			FileOutputStream os = new FileOutputStream(imageFile);
			os.write(image.getData());
			os.close();

		} catch (IOException e) {
			throw new ImageDaoException("Unable to save image", e);
		}
	}

	@Override
	public boolean exists(ID id) {
		File[] matchingFiles = findFile(id);
		if (matchingFiles.length != 0) {
			return true;
		}

		return false;
	}

	@Override
	public Image<ID> findOne(ID id) {
		Image<ID> image = new Image<ID>();

		File[] matchingFiles = findFile(id);
		if(matchingFiles.length == 0) {
			throw new ImageDaoException("There is no image for the given id " + id);
		}
		
		FileInputStream is;
		try {
			is = new FileInputStream(matchingFiles[0]);
			image.setId(id);
			image.setData(IOUtils.toByteArray(is));
			is.close();
		} catch (IOException e) {
			throw new ImageDaoException("Unable to load image", e);
		}

		return image;
	}

	@Override
	public void delete(ID id) {

		File[] matchingFiles = findFile(id);
		for (File file : matchingFiles) {
			file.delete();
		}
	}

	private void validateImageFormat(Image<ID> image) {
		if (!Arrays.asList(supportedFileTypes).contains(image.getFormat())) {
			throw new ImageDaoException("Not supported format "
					+ image.getFormat());
		}
	}

	private File[] findFile(final ID id) {
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
		File userHomeDir = new File(System.getProperty(USER_HOME_PROPERTY));
		File appHomeDir = new File(userHomeDir, appHome);
		if (!appHomeDir.exists()) {
			appHomeDir.mkdirs();
		}
		return appHomeDir;
	}

	private String generateFileName(Image<ID> image) {
		return image.getId().toString() + FilenameUtils.EXTENSION_SEPARATOR
				+ image.getFormat();
	}
}
