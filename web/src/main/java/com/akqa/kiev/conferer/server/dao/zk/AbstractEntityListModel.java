package com.akqa.kiev.conferer.server.dao.zk;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.ListModelList;

import com.akqa.kiev.conferer.server.dao.AbstractDao;
import com.akqa.kiev.conferer.server.model.AbstractEntity;

public abstract class AbstractEntityListModel<E extends AbstractEntity> {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final AbstractDao<E> dao;
	
	private final Class<E> entityClass;
	
	private E selected;
	private ListModelList<E> items;
	
	private boolean editWindowVisible = false;
	protected E editedEntity;

	@SuppressWarnings("unchecked")
	protected AbstractEntityListModel(Class<? extends AbstractDao<E>> daoClass) {
		logger.debug("Instantiating {}", this.getClass().getSimpleName());
		dao = SpringUtil.getApplicationContext().getBean(daoClass);
		
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[0];
	}
	
	@Init
	public void init() {
		LinkedList<E> list = new LinkedList<>();
		for (E e : dao.findAll()) list.add(e);
		
		items = new ListModelList<>(list);
		editedEntity = BeanUtils.instantiate(entityClass);
	}
	
	@Command("save-entity")
	@NotifyChange({"editWindowVisible", "items"})
	public void saveEntity() {
		dao.save(editedEntity);
		editWindowVisible = false;
	}
	
	@Command("close-edit-window")
	@NotifyChange("editWindowVisible")
	public void closeEditWindow() {
		editWindowVisible = false;
	}
	
	@Command("show-edit-window")
	@NotifyChange("editWindowVisible")
	public void showEditWindow() {
		editedEntity = BeanUtils.instantiate(entityClass);
		
		editWindowVisible = true;
	}

	public ListModelList<E> getItems() {
		return items;
	}

	public void setItems(ListModelList<E> items) {
		this.items = items;
	}

	public E getSelected() {
		return selected;
	}

	public void setSelected(E selected) {
		this.selected = selected;
	}

	public boolean isEditWindowVisible() {
		return editWindowVisible;
	}

	public void setEditWindowVisible(boolean editWindowVisible) {
		this.editWindowVisible = editWindowVisible;
	}
	
	public E getEditedEntity() {
		return editedEntity;
	}

	public void setEditedEntity(E editedEntity) {
		this.editedEntity = editedEntity;
	}
}
