package com.vi.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.vi.pojo.ErrMsg;

/**
 * A data access object (DAO) providing persistence and search support for
 * ErrMsg entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.vi.pojo.ErrMsg
 * @author MyEclipse Persistence Tools
 */

public class ErrMsgDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(ErrMsgDAO.class);
	// property constants
	public static final String ERR_DESC = "errDesc";
	public static final String SOLUTION = "solution";
	public static final String COMMENTS = "comments";

	protected void initDao() {
		// do nothing
	}

	public void save(ErrMsg transientInstance) {
		log.debug("saving ErrMsg instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ErrMsg persistentInstance) {
		log.debug("deleting ErrMsg instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ErrMsg findById(java.lang.String id) {
		log.debug("getting ErrMsg instance with id: " + id);
		try {
			ErrMsg instance = (ErrMsg) getHibernateTemplate().get(
					"com.vi.dao.ErrMsg", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ErrMsg instance) {
		log.debug("finding ErrMsg instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding ErrMsg instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from ErrMsg as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByErrDesc(Object errDesc) {
		return findByProperty(ERR_DESC, errDesc);
	}

	public List findBySolution(Object solution) {
		return findByProperty(SOLUTION, solution);
	}

	public List findByComments(Object comments) {
		return findByProperty(COMMENTS, comments);
	}

	public List findAll() {
		log.debug("finding all ErrMsg instances");
		try {
			String queryString = "from ErrMsg";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ErrMsg merge(ErrMsg detachedInstance) {
		log.debug("merging ErrMsg instance");
		try {
			ErrMsg result = (ErrMsg) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ErrMsg instance) {
		log.debug("attaching dirty ErrMsg instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ErrMsg instance) {
		log.debug("attaching clean ErrMsg instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ErrMsgDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ErrMsgDAO) ctx.getBean("ErrMsgDAO");
	}
}