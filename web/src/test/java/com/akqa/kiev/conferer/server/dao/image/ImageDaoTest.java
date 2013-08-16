package com.akqa.kiev.conferer.server.dao.image;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.akqa.kiev.conferer.server.dao.image.ImageDao;
import com.akqa.kiev.conferer.server.dao.image.ImageDaoException;
import com.akqa.kiev.conferer.server.model.Image;

@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestFileStoreConfig.class })
public class ImageDaoTest {

	@Autowired
	private ImageDao<String> dao;

	private Image<String> testImage;

	@Before
	public void setup() throws Exception {
		File appHomeRoot = dao.getAppHomeRoot();
		if (appHomeRoot.exists()) {
			FileUtils.cleanDirectory(appHomeRoot);
		}

		File testImageFile = new File(getClass().getResource("/Session1.png")
				.getFile());
		testImage = loadTestImage(testImageFile);
	}

	@Test(expected = ImageDaoException.class)
	public void unsupportedFormat() throws Exception {

		File wrongFormatFile = new File(getClass().getResource("/Session1.txt")
				.getFile());
		dao.save(loadTestImage(wrongFormatFile));
	}

	@Test
	public void saveImage() throws Exception {

		dao.save(testImage);
		assertTrue(dao.exists("Session1"));
	}

	@Test
	public void updateImage() throws Exception {

		dao.save(testImage);
		Image<String> storedImage = dao.findOne("Session1");

		dao.save(testImage);
		Image<String> updatedImage = dao.findOne("Session1");
		
		assertEquals(storedImage.getData().length,
				updatedImage.getData().length);
	}

	@Test
	public void findImage() throws Exception {

		dao.save(testImage);

		Image<String> storableImage = dao.findOne("Session1");
		byte[] imageBytes = storableImage.getData();
		assertTrue(imageBytes.length != 0);
	}
	
	@Test(expected = ImageDaoException.class)
	public void findNonExistentImage() throws Exception {

		dao.findOne("Session1");
	}

	@Test
	public void deleteImage() throws Exception {

		dao.save(testImage);
		assertTrue(dao.exists("Session1"));

		dao.delete("Session1");
		assertFalse(dao.exists("Session1"));
	}

	private Image<String> loadTestImage(File file) {

		Image<String> image = new Image<String>();
		
		String fileName = file.getName();
		image.setId(FilenameUtils.removeExtension(fileName));
		image.setFormat(FilenameUtils.getExtension(fileName));
		
		try {
			FileInputStream is = new FileInputStream(file);
			image.setData(IOUtils.toByteArray(is));
			is.close();
		} catch (IOException e) {
			throw new ImageDaoException("Unable to load image", e);
		}

		return image;
	}

	@After
	public void tearDown() throws Exception {
		FileUtils.cleanDirectory(dao.getAppHomeRoot());
	}
}
