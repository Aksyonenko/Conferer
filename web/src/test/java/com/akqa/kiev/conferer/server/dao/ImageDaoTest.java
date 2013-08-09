package com.akqa.kiev.conferer.server.dao;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.akqa.kiev.conferer.server.dao.ImageDao;
import com.akqa.kiev.conferer.server.dao.ImageDaoException;
import com.akqa.kiev.conferer.server.model.Image;

@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestFileStoreConfig.class })
public class ImageDaoTest {

	@Autowired
	private ImageDao dao;

	private Image testImage;

	@Before
	public void setup() throws Exception {
		FileUtils.cleanDirectory(dao.getAppHomeRoot());

		File testImageFile = new File(getClass().getResource("/2001.png")
				.getFile());
		testImage = loadTestImage(testImageFile);
	}

	@Test(expected = ImageDaoException.class)
	public void unsupportedFormat() throws Exception {

		File wrongFormatFile = new File(getClass().getResource("/2001.txt")
				.getFile());
		dao.save(loadTestImage(wrongFormatFile));
	}

	@Test
	public void saveImage() throws Exception {

		dao.save(testImage);

		assertTrue(dao.exists(new BigInteger("2001")));
	}

	@Test
	public void findImage() throws Exception {

		dao.save(testImage);

		Image storableImage = dao.findOne(new BigInteger("2001"));
		byte[] imageBytes = storableImage.getData();
		assertTrue(imageBytes.length != 0);
	}

	@Test
	public void deleteImage() throws Exception {

		dao.save(testImage);
		assertTrue(dao.exists(new BigInteger("2001")));

		dao.delete(new BigInteger("2001"));
		assertFalse(dao.exists(new BigInteger("2001")));
	}

	private Image loadTestImage(File file) {

		ByteArrayOutputStream baos;
		try {
			FileInputStream is = new FileInputStream(file);
			baos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			for (int readNum; (readNum = is.read(buf)) != -1;) {
				baos.write(buf, 0, readNum);
			}
			is.close();
		} catch (IOException e) {
			throw new ImageDaoException("Unable to load image", e);
		}

		String fileName = file.getName();

		Image image = new Image();
		image.setId(new BigInteger(FilenameUtils.removeExtension(fileName)));
		image.setFormat(FilenameUtils.getExtension(fileName));
		image.setData(baos.toByteArray());

		return image;
	}

	@After
	public void tearDown() throws Exception {
		FileUtils.cleanDirectory(dao.getAppHomeRoot());
	}
}
