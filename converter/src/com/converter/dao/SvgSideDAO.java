package com.converter.dao;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.converter.SvgSide;

/**
 	* A data access object (DAO) providing persistence and search support for SvgSide entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.converter.SvgSide
  * @author MyEclipse Persistence Tools 
 */

public class SvgSideDAO extends HibernateDaoSupport  {
	     private static final Logger log = LoggerFactory.getLogger(SvgSideDAO.class);
		//property constants
	public static final String STYLE = "style";
	public static final String STOKE_WIDTH = "stokeWidth";
	public static final String COLOR = "color";
	public static final String COMMENTS = "comments";



	protected void initDao() {
		//do nothing
	}
    
    public void save(SvgSide transientInstance) {
        log.debug("saving SvgSide instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(SvgSide persistentInstance) {
        log.debug("deleting SvgSide instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public SvgSide findById( com.converter.SvgSideId id) {
        log.debug("getting SvgSide instance with id: " + id);
        try {
            SvgSide instance = (SvgSide) getHibernateTemplate()
                    .get("com.converter.dao.SvgSide", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(SvgSide instance) {
        log.debug("finding SvgSide instance by example");
        try {
            List results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
        log.debug("finding SvgSide instance with property: " + propertyName
              + ", value: " + value);
        try {
           String queryString = "from SvgSide as model where model." 
           						+ propertyName + "= ?";
  		 return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
           log.error("find by property name failed", re);
           throw re;
        }
  	}

    public List findByPropertys(String[] propertyName, Object[] value) {
        log.debug("finding SvgSide instance with property[0]: " + propertyName[0]
              + ", value[0]: " + value[0] + ";" 
              + "property[1]: " + propertyName[1] + ", value[1]: " + value[1]);
        try {
           String queryString = "from SvgSide as model where model." 
           						+ propertyName[0] + "= ?"
           						+ " and model." + propertyName[1] + "= '" + value[1] +"'";
  		 return getHibernateTemplate().find(queryString, value[0]);
        } catch (RuntimeException re) {
           log.error("find by property name failed", re);
           throw re;
        }
  	}

	public List findByStyle(Object style
	) {
		return findByProperty(STYLE, style
		);
	}
	
	public List findByStokeWidth(Object stokeWidth
	) {
		return findByProperty(STOKE_WIDTH, stokeWidth
		);
	}
	
	public List findByColor(Object color
	) {
		return findByProperty(COLOR, color
		);
	}
	
	public List findByComments(Object comments
	) {
		return findByProperty(COMMENTS, comments
		);
	}
	

	public List findAll() {
		log.debug("finding all SvgSide instances");
		try {
			String queryString = "from SvgSide";
		 	return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public SvgSide merge(SvgSide detachedInstance) {
        log.debug("merging SvgSide instance");
        try {
            SvgSide result = (SvgSide) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(SvgSide instance) {
        log.debug("attaching dirty SvgSide instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(SvgSide instance) {
        log.debug("attaching clean SvgSide instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static SvgSideDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (SvgSideDAO) ctx.getBean("SvgSideDAO");
	}
}