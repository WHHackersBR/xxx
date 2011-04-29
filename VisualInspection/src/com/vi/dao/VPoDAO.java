package com.vi.dao;
// default package


import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.vi.pojo.VPo;
import com.vi.pojo.VPoId;

/**
 	* A data access object (DAO) providing persistence and search support for VPo entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .VPo
  * @author MyEclipse Persistence Tools 
 */

public class VPoDAO extends HibernateDaoSupport  {
		 private static final Log log = LogFactory.getLog(VPoDAO.class);
		//property constants



	protected void initDao() {
		//do nothing
	}
    
    public void save(VPo transientInstance) {
        log.debug("saving VPo instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(VPo persistentInstance) {
        log.debug("deleting VPo instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public VPo findById( VPoId id) {
        log.debug("getting VPo instance with id: " + id);
        try {
            VPo instance = (VPo) getHibernateTemplate()
                    .get("com.vi.pojo.VPo", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(VPo instance) {
        log.debug("finding VPo instance by example");
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
      log.debug("finding VPo instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from VPo as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

    public List findByProperty3(String[] propertyName, Object[] value) {
        log.debug("finding SvgSide instance with property[0]: " + propertyName[0]
              + ", value[0]: " + value[0] + ";" 
              + "property[1]: " + propertyName[1] + ", value[1]: " + value[1]
              + "property[2]: " + propertyName[2] + ", value[2]: " + value[2]);
        try {
           String queryString = "from SvgSide as model where model." 
           						+ propertyName[0] + "= ?"
           						+ " and model." + propertyName[1] + "= '" + value[1] +"'"
								+ " and model." + propertyName[2] + "= '" + value[2] +"'";
  		 return getHibernateTemplate().find(queryString, value[0]);
        } catch (RuntimeException re) {
           log.error("find by property name failed", re);
           throw re;
        }
  	}

	public List findAll() {
		log.debug("finding all VPo instances");
		try {
			String queryString = "from VPo";
		 	return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public VPo merge(VPo detachedInstance) {
        log.debug("merging VPo instance");
        try {
            VPo result = (VPo) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(VPo instance) {
        log.debug("attaching dirty VPo instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(VPo instance) {
        log.debug("attaching clean VPo instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static VPoDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (VPoDAO) ctx.getBean("VPoDAO");
	}
}