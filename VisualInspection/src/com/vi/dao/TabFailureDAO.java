package com.vi.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.vi.pojo.TabFailure;
import com.vi.pojo.TabFailureId;

/**
 * A data access object (DAO) providing persistence and search support for
 * TabFailure entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.vi.pojo.TabFailure
 * @author MyEclipse Persistence Tools
 */

public class TabFailureDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(TabFailureDAO.class);
	// property constants
	public static final String MACH_TIME = "machTime";
	public static final String FAILURE_DESCRIPTION = "failureDescription";
	public static final String SIDE = "side";

	protected void initDao() {
		// do nothing
	}

	public void save(TabFailure transientInstance) {
		log.debug("saving TabFailure instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TabFailure persistentInstance) {
		log.debug("deleting TabFailure instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TabFailure findById(com.vi.pojo.TabFailureId id) {
		log.debug("getting TabFailure instance with id: " + id);
		try {
			TabFailure instance = (TabFailure) getHibernateTemplate().get(
					"com.vi.pojo.TabFailure", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TabFailure instance) {
		log.debug("finding TabFailure instance by example");
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
		log.debug("finding TabFailure instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TabFailure as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByMachTime(Object machTime) {
		return findByProperty(MACH_TIME, machTime);
	}

	public List findByFailureDescription(Object failureDescription) {
		return findByProperty(FAILURE_DESCRIPTION, failureDescription);
	}

	public List findBySide(Object side) {
		return findByProperty(SIDE, side);
	}

	public List findAll() {
		log.debug("finding all TabFailure instances");
		try {
			String queryString = "from TabFailure";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TabFailure merge(TabFailure detachedInstance) {
		log.debug("merging TabFailure instance");
		try {
			TabFailure result = (TabFailure) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TabFailure instance) {
		log.debug("attaching dirty TabFailure instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TabFailure instance) {
		log.debug("attaching clean TabFailure instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TabFailureDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TabFailureDAO) ctx.getBean("TabFailureDAO");
	}
}