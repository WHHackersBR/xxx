package com.converter.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.converter.TabComponentInfo;
import com.converter.TabComponentInfoId;

/**
 	* A data access object (DAO) providing persistence and search support for TabComponentInfo entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.converter.TabComponentInfo
  * @author MyEclipse Persistence Tools 
 */

public class TabComponentInfoDAO extends HibernateDaoSupport  {
	     private static final Logger log = LoggerFactory.getLogger(TabComponentInfoDAO.class);
		//property constants
	public static final String COMPONENT_NUMBER = "componentNumber";
	public static final String COMPONENT_AS = "componentAs";
	public static final String FILL_COLOR = "fillColor";
	public static final String STROKE_WIDTH = "strokeWidth";
	public static final String STROKE_COLOR = "strokeColor";



	protected void initDao() {
		//do nothing
	}
    
    public void save(TabComponentInfo transientInstance) {
        log.debug("saving TabComponentInfo instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(TabComponentInfo persistentInstance) {
        log.debug("deleting TabComponentInfo instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public TabComponentInfo findById( com.converter.TabComponentInfoId id) {
        log.debug("getting TabComponentInfo instance with id: " + id);
        try {
            TabComponentInfo instance = (TabComponentInfo) getHibernateTemplate()
                    .get("com.converter.TabComponentInfo", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(TabComponentInfo instance) {
        log.debug("finding TabComponentInfo instance by example");
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
      log.debug("finding TabComponentInfo instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from TabComponentInfo as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByComponentNumber(Object componentNumber
	) {
		return findByProperty(COMPONENT_NUMBER, componentNumber
		);
	}
	
	public List findByComponentAs(Object componentAs
	) {
		return findByProperty(COMPONENT_AS, componentAs
		);
	}
	
	public List findByFillColor(Object fillColor
	) {
		return findByProperty(FILL_COLOR, fillColor
		);
	}
	
	public List findByStrokeWidth(Object strokeWidth
	) {
		return findByProperty(STROKE_WIDTH, strokeWidth
		);
	}
	
	public List findByStrokeColor(Object strokeColor
	) {
		return findByProperty(STROKE_COLOR, strokeColor
		);
	}
	

	public List findAll() {
		log.debug("finding all TabComponentInfo instances");
		try {
			String queryString = "from TabComponentInfo";
		 	return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public TabComponentInfo merge(TabComponentInfo detachedInstance) {
        log.debug("merging TabComponentInfo instance");
        try {
            TabComponentInfo result = (TabComponentInfo) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(TabComponentInfo instance) {
        log.debug("attaching dirty TabComponentInfo instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(TabComponentInfo instance) {
        log.debug("attaching clean TabComponentInfo instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static TabComponentInfoDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (TabComponentInfoDAO) ctx.getBean("TabComponentInfoDAO");
	}
}