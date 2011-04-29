package com.vi.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.vi.pojo.VFailure;
import com.vi.pojo.VFailureId;

/**
 * A data access object (DAO) providing persistence and search support for
 * VFailure entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.vi.pojo.VFailure
 * @author MyEclipse Persistence Tools
 */

public class VFailureDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(VFailureDAO.class);

	// property constants

	protected void initDao() {
		// do nothing
	}

	public void save(VFailure transientInstance) {
		log.debug("saving VFailure instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(VFailure persistentInstance) {
		log.debug("deleting VFailure instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public VFailure findById(com.vi.pojo.VFailureId id) {
		log.debug("getting VFailure instance with id: " + id);
		try {
			VFailure instance = (VFailure) getHibernateTemplate().get(
					"com.vi.pojo.VFailure", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(VFailure instance) {
		log.debug("finding VFailure instance by example");
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
		log.debug("finding VFailure instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from VFailure as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all VFailure instances");
		try {
			String queryString = "from VFailure";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public VFailure merge(VFailure detachedInstance) {
		log.debug("merging VFailure instance");
		try {
			VFailure result = (VFailure) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(VFailure instance) {
		log.debug("attaching dirty VFailure instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(VFailure instance) {
		log.debug("attaching clean VFailure instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static VFailureDAO getFromApplicationContext(ApplicationContext ctx) {
		return (VFailureDAO) ctx.getBean("VFailureDAO");
	}
}