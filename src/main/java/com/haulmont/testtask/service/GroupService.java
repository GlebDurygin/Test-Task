package com.haulmont.testtask.service;

import com.haulmont.testtask.database.impl.HSQLDBDaoManager;
import com.haulmont.testtask.database.impl.HSQLDBGroupDao;
import com.haulmont.testtask.database.impl.HSQLDBStudentDao;
import com.haulmont.testtask.exception.database.impl.DaoException;
import com.haulmont.testtask.exception.service.ServiceException;
import com.haulmont.testtask.model.entity.Group;

import java.util.List;
import java.util.logging.Logger;

public class GroupService {
    private HSQLDBDaoManager daoManager;
    private HSQLDBGroupDao groupDao;
    private HSQLDBStudentDao studentDao;

    private static GroupService instance;

    private Logger logger = Logger.getLogger(GroupService.class.getName());

    private GroupService() {
        try {
            daoManager = HSQLDBDaoManager.getInstance();
            groupDao = (HSQLDBGroupDao) daoManager.getGroupDao();
            studentDao = (HSQLDBStudentDao) daoManager.getStudentDao();
        } catch (DaoException e) {
            logger.severe(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    public static synchronized GroupService getInstance() {
        if (instance == null) instance = new GroupService();
        return instance;
    }

    public boolean insertGroup(Group group) {
        try {
            if (groupDao.getGroupId(group) == null) {
                groupDao.insert(group);
                return true;
            }
        } catch (DaoException e) {
            logger.severe(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return false;
    }

    public boolean deleteGroup(Long id) {
        try {
            if (studentDao.getStudentsByGroup(id).size() == 0) {
                groupDao.delete(id);
                return true;
            }
        } catch (DaoException e) {
            logger.severe(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return false;
    }

    public boolean updateGroup(Group group) {
        try {
            groupDao.update(group);
        } catch (DaoException e) {
            logger.severe(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return true;
    }

    public Group getGroup(Long id) {
        try {
            return groupDao.getById(id);
        } catch (DaoException e) {
            logger.severe(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    public List<Group> getGroups() {
        try {
            return groupDao.getAll();
        } catch (DaoException e) {
            logger.severe(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }
}
