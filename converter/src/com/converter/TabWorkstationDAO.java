package com.converter;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * TabWorkstation entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.converter.TabWorkstation
 * @author MyEclipse Persistence Tools
 */

public class TabWorkstationDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(TabWorkstationDAO.class);
	// property constants
	public static final String SIDE = "side";
	public static final String TYPE = "type";
	public static final String CODE = "code";
	public static final String EQUIP_CONTENT = "equipContent";
	public static final String MACH_ID = "machId";

	protected void initDao() {
		// do nothing
	}

	public void save(TabWorkstation transientInstance) {
		log.debug("saving TabWorkstation instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TabWorkstation persistentInstance) {
		log.debug("deleting TabWorkstation instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TabWorkstation findById(java.lang.String id) {
		log.debug("getting TabWorkstation instance with id: " + id);
		try {
			TabWorkstation instance = (TabWorkstation) getHibernateTemplate()
					.get("com.converter.TabWorkstation", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TabWorkstation instance) {
		log.debug("finding TabWorkstation instance by example");
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
		log.debug("finding TabWorkstation instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TabWorkstation as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findBySide(Object side) {
		return findByProperty(SIDE, side);
	}

	public List findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List findByCode(Object code) {
		return findByProperty(CODE, code);
	}

	public List findByEquipContent(Object equipContent) {
		return findByProperty(EQUIP_CONTENT, equipContent);
	}

	public List findByMachId(Object machId) {
		return findByProperty(MACH_ID, machId);
	}

	public List findAll() {
		log.debug("finding all TabWorkstation instances");
		try {
			String queryString = "from TabWorkstation";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TabWorkstation merge(TabWorkstation detachedInstance) {
		log.debug("merging TabWorkstation instance");
		try {
			TabWorkstation result = (TabWorkstation) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TabWorkstation instance) {
		log.debug("attaching dirty TabWorkstation instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TabWorkstation instance) {
		log.debug("attaching clean TabWorkstation instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TabWorkstationDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (TabWorkstationDAO) ctx.getBean("TabWorkstationDAO");
	}
}