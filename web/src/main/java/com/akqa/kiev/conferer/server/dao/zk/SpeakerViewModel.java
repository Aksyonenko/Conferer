package com.akqa.kiev.conferer.server.dao.zk;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.Image;
import org.zkoss.util.media.Media;
import org.zkoss.zkplus.spring.SpringUtil;

import com.akqa.kiev.conferer.server.dao.SpeakerDao;
import com.akqa.kiev.conferer.server.dao.image.AbstractIdGenerator;
import com.akqa.kiev.conferer.server.dao.image.ImageDao;
import com.akqa.kiev.conferer.server.dao.image.StringIdGenerator;
import com.akqa.kiev.conferer.server.model.Speaker;

public class SpeakerViewModel extends AbstractEntityListModel<Speaker> {

	private ImageDao<String> imageDao;

	private AbstractIdGenerator<String> idGenerator;
	
	private Image speakerImage;

	public SpeakerViewModel() {
		super(SpeakerDao.class);

		imageDao = SpringUtil.getApplicationContext().getBean(ImageDao.class);
		idGenerator = SpringUtil.getApplicationContext().getBean(StringIdGenerator.class);
	}

	@Init
	public void init() {
		super.init();
	}

	@Command("upload")
	public void uploadImage(@BindingParam("media") Media aMedia) {

		if (aMedia instanceof Image) {
			speakerImage = (Image) aMedia;
		}
	}

	@Command("save-entity")
	@NotifyChange({ "editWindowVisible", "items" })
	@Override
	public void saveEntity() {
		super.saveEntity();

		com.akqa.kiev.conferer.server.model.Image<String> storableImage = new com.akqa.kiev.conferer.server.model.Image<String>();

		storableImage.setId(idGenerator.generate(editedEntity));
		storableImage.setFormat(speakerImage.getFormat());
		storableImage.setData(speakerImage.getByteData());

		imageDao.save(storableImage);
	}
	
    @Command
    @NotifyChange("items")
    public void removeItem(@BindingParam("item") Speaker myItem) {
        super.removeItem(myItem);
        
    	imageDao.delete(idGenerator.generate(editedEntity));
    }
	
	public Image getUploadedImage() {
		return speakerImage;
	}

	public void setUploadedImage(Image uploadedImage) {
		this.speakerImage = uploadedImage;
	}
}
