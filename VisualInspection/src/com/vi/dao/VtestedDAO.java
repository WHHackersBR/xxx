package com.vi.dao;
// default package

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.vi.pojo.Vtested;
import com.vi.pojo.VtestedId;

/**
 	* A data access object (DAO) providing persistence and search support for Vtested entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .Vtested
  * @author MyEclipse Persistence Tools 
 */

public class VtestedDAO extends HibernateDaoSupport  {
		 private static final Log log = LogFactory.getLog(VtestedDAO.class);
		//property constants



	protected void initDao() {
		//do nothing
	}
    
    public void save(Vtested transientInstance) {
        log.debug("saving Vtested instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Vtested persistentInstance) {
        log.debug("deleting Vtested instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Vtested findById( VtestedId id) {
        log.debug("getting Vtested instance with id: " + id);
        try {
            Vtested instance = (Vtested) getHibernateTemplate()
                    .get("com.vi.pojo.Vtested", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(Vtested instance) {
        log.debug("finding Vtested instance by example");
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
      log.debug("finding Vtested instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Vtested as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}


	public List findAll() {
		log.debug("finding all Vtested instances");
		try {
			String queryString = "from Vtested";
		 	return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Vtested merge(Vtested detachedInstance) {
        log.debug("merging Vtested instance");
        try {
            Vtested result = (Vtested) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Vtested instance) {
        log.debug("attaching dirty Vtested instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Vtested instance) {
        log.debug("attaching clean Vtested instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static VtestedDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (VtestedDAO) ctx.getBean("VtestedDAO");
	}
}