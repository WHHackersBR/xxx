package com.vi.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.vi.pojo.TabTested;

/**
 * A data access object (DAO) providing persistence and search support for
 * TabTested entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.vi.pojo.TabTested
 * @author MyEclipse Persistence Tools
 */

public class TabTestedDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(TabTestedDAO.class);
	// property constants
	public static final String TEST_RESULT = "testResult";
	public static final String ITEM_NO = "itemNo";
	public static final String AS = "as";
	public static final String PO_NO = "poNo";
	public static final String FAKE = "fake";
	public static final String OPERATOR_ID = "operatorId";
	public static final String SIDE = "side";
	public static final String MACH_TIME = "machTime";

	protected void initDao() {
		// do nothing
	}

	public void save(TabTested transientInstance) {
		log.debug("saving TabTested instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TabTested persistentInstance) {
		log.debug("deleting TabTested instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TabTested findById(com.vi.pojo.TabTestedId id) {
		log.debug("getting TabTested instance with id: " + id);
		try {
			TabTested instance = (TabTested) getHibernateTemplate().get(
					"com.vi.pojo.TabTested", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TabTested instance) {
		log.debug("finding TabTested instance by example");
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
		log.debug("finding TabTested instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TabTested as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByTestResult(Object testResult) {
		return findByProperty(TEST_RESULT, testResult);
	}

	public List findByItemNo(Object itemNo) {
		return findByProperty(ITEM_NO, itemNo);
	}

	public List findByAs(Object as) {
		return findByProperty(AS, as);
	}

	public List findByPoNo(Object poNo) {
		return findByProperty(PO_NO, poNo);
	}

	public List findByFake(Object fake) {
		return findByProperty(FAKE, fake);
	}

	public List findByOperatorId(Object operatorId) {
		return findByProperty(OPERATOR_ID, operatorId);
	}

	public List findBySide(Object side) {
		return findByProperty(SIDE, side);
	}

	public List findByMachTime(Object machTime) {
		return findByProperty(MACH_TIME, machTime);
	}

	public List findAll() {
		log.debug("finding all TabTested instances");
		try {
			String queryString = "from TabTested";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TabTested merge(TabTested detachedInstance) {
		log.debug("merging TabTested instance");
		try {
			TabTested result = (TabTested) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TabTested instance) {
		log.debug("attaching dirty TabTested instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TabTested instance) {
		log.debug("attaching clean TabTested instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TabTestedDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TabTestedDAO) ctx.getBean("TabTestedDAO");
	}
}