package com.akqa.kiev.conferer.server.controller;

import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.akqa.kiev.conferer.server.dao.AbstractDao;
import com.akqa.kiev.conferer.server.dao.ActionDao;
import com.akqa.kiev.conferer.server.model.AbstractEntity;
import com.akqa.kiev.conferer.server.model.Action;
import com.akqa.kiev.conferer.server.model.Action.ActionName;

public abstract class AbstractConfererController<T extends AbstractEntity> {
    
    private static final String ID_PARAM = "id";
    
    @Autowired
    private ActionDao actionDao;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public T findOne(@PathVariable BigInteger id) {
		try {
			T entity = getDao().findOne(id);
			if (entity == null) throw new IncorrectResultSizeDataAccessException(1, 0);
			return entity;
			
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new ResourceNotFoundException("Conference " + id);
		}
	}
	
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    protected void save(HttpServletRequest request) {
        String idParam = request.getParameter(ID_PARAM);
        String actionName = ActionName.ADDED.name();
        if (idParam != null) {
            if (getDao().exists(new BigInteger(idParam))) {
                actionName = ActionName.MODIFIED.name();
            }
        }
        T entity = saveEntity(request);

        Action action = new Action();
        action.setActionName(actionName);
        action.setEntityId(entity.getId());
        action.setEntityClass(getEntityClassName());
        actionDao.save(action);
    }
	   
    protected abstract T saveEntity(HttpServletRequest request) ;
	
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public void delete(@PathVariable BigInteger id) {
        boolean exists = getDao().exists(id);
        if (exists) {
            getDao().delete(id);
            Action action = new Action();
            action.setActionName(ActionName.DELETED.name());
            action.setEntityId(id);
            action.setEntityClass(getEntityClassName());
            actionDao.save(action);
        }
    }
	
    @SuppressWarnings("unchecked")
	private String getEntityClassName(){
	    return ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0]).getSimpleName();
	}
	
	protected abstract AbstractDao<T> getDao();

}
