package com.vi.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.vi.pojo.VFidHist;
import com.vi.pojo.VFidHistId;

/**
 	* A data access object (DAO) providing persistence and search support for VFidHist entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.vi.pojo.VFidHist
  * @author MyEclipse Persistence Tools 
 */

public class VFidHistDAO extends HibernateDaoSupport  {
		 private static final Log log = LogFactory.getLog(VFidHistDAO.class);
		//property constants



	protected void initDao() {
		//do nothing
	}
    
    public void save(VFidHist transientInstance) {
        log.debug("saving VFidHist instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(VFidHist persistentInstance) {
        log.debug("deleting VFidHist instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public VFidHist findById( com.vi.pojo.VFidHistId id) {
        log.debug("getting VFidHist instance with id: " + id);
        try {
            VFidHist instance = (VFidHist) getHibernateTemplate()
                    .get("com.vi.pojo.VFidHist", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(VFidHist instance) {
        log.debug("finding VFidHist instance by example");
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
      log.debug("finding VFidHist instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from VFidHist as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}


	public List findAll() {
		log.debug("finding all VFidHist instances");
		try {
			String queryString = "from VFidHist";
		 	return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public VFidHist merge(VFidHist detachedInstance) {
        log.debug("merging VFidHist instance");
        try {
            VFidHist result = (VFidHist) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(VFidHist instance) {
        log.debug("attaching dirty VFidHist instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(VFidHist instance) {
        log.debug("attaching clean VFidHist instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static VFidHistDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (VFidHistDAO) ctx.getBean("VFidHistDAO");
	}
}