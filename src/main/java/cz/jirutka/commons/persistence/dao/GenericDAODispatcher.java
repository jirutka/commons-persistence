/*
 * Copyright (c) 2012 Jakub Jirutka <jakub@jirutka.cz>
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the  GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cz.jirutka.commons.persistence.dao;

import cz.jirutka.commons.persistence.Persistable;
import java.io.Serializable;
import java.util.List;

/**
 * Basic implementation of the DAO Dispatcher pattern [1] for {@link GenericDAO}. 
 * 
 * <p>The dispatcher holds an instance of the <tt>GenericDAO</tt> and a
 * {@linkplain SpecificDAORegistry registry} with {@linkplain SpecificDAO 
 * specific DAO} instances. It implements interface of a generic DAO so it can 
 * by called with any {@link Persistable} (entity) instance or class. When there 
 * is found a suitable specific DAO for the given entity in the registry, method 
 * call is delegated to it. Otherwise, it's simply delegated to the generic DAO.
 * </p>
 * 
 * <p><i>(1) The DAO Dispatcher pattern and concept of this DAO architecture is 
 * idea of Pavel Micka (pavel.micka@flexibase.net). Thanks.<i></p>
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>
 * @version 2012-06-12
 * @since 1.0
 */
public class GenericDAODispatcher implements GenericDAO {

    private final GenericDAO genericDAO;
    private final SpecificDAORegistry registry;

    
    /**
     * Create new instance of the <tt>GenericDAODispatcher</tt>.
     * 
     * @param genericDAO a generic DAO
     * @param registry a registry with specific DAOs
     */
    public GenericDAODispatcher(GenericDAO genericDAO, SpecificDAORegistry registry) {
        this.genericDAO = genericDAO;
        this.registry = registry;
    }
    
    

    ///// Delegate to SpecificDAO or genericDAO /////

    @Override
    public Long count(Class<? extends Persistable> clazz) {
        if (registry.containsDAO(clazz)) {
            return registry.getDAO(clazz).count();
        } else {
            return genericDAO.count(clazz);
        }
    }

    @Override
    public void delete(Persistable entity) {
        if (registry.containsDAO(entity.getClass())) {
            registry.getDAO(entity.getClass()).delete(entity);
        } else {
            genericDAO.delete(entity);
        }
    }

    @Override
    public void delete(Serializable id, Class<? extends Persistable> clazz) {
        if (registry.containsDAO(clazz)) {
            registry.getDAO(clazz).delete(id);
        } else {
            genericDAO.delete(id, clazz);
        }
    }

    @Override
    public <E extends Persistable> 
            List<E> findByExample(E exampleInstance, String[] includeProperties, PagingOrdering paging, Class<E> clazz) {
        
        if (registry.containsDAO(clazz)) {
            return registry.getDAO(clazz).findByExample(exampleInstance, includeProperties, paging);
        } else {
            return genericDAO.findByExample(exampleInstance, includeProperties, paging, clazz);
        }
    }

    @Override
    public <E extends Persistable> 
            E findByNaturalKey(Object naturalKey, Class<E> clazz) {

        if (registry.containsDAO(clazz)) {
            return (E) registry.getDAO(clazz).findByNaturalKey(naturalKey);
        } else {
            return genericDAO.findByNaturalKey(naturalKey, clazz);
        }
    }

    @Override
    public <E extends Persistable> 
            E findByPrimaryKey(Serializable id, Class<E> clazz) {

        if (registry.containsDAO(clazz)) {
            return (E) registry.getDAO(clazz).findByPrimaryKey(id);
        } else {
            return genericDAO.findByPrimaryKey(id, clazz);
        }
    }

    @Override
    public <E extends Persistable> 
            List<E> findByProperty(String property, Object value, PagingOrdering paging, Class<E> clazz) {
        
        if (registry.containsDAO(clazz)) {
            return registry.getDAO(clazz).findByProperty(property, value, paging);
        } else {
            return genericDAO.findByProperty(property, value, paging, clazz);
        }
    }

    @Override
    public <E extends Persistable> 
            List<E> getAll(Class<E> clazz) {
        
        if (registry.containsDAO(clazz)) {
            return registry.getDAO(clazz).getAll();
        } else {
            return genericDAO.getAll(clazz);
        }
    }

    @Override
    public <E extends Persistable> 
            List<E> getPaginated(PagingOrdering paging, Class<E> clazz) {
        
        if (registry.containsDAO(clazz)) {
            return registry.getDAO(clazz).getPaginated(paging);
        } else {
            return genericDAO.getPaginated(paging, clazz);
        }
    }

    @Override
    public boolean isPersistent(Serializable id, Class<? extends Persistable> clazz) {
        if (registry.containsDAO(clazz)) {
            return registry.getDAO(clazz).isPersistent(id);
        } else {
            return genericDAO.isPersistent(id, clazz);
        }
    }

    @Override
    public <E extends Persistable> 
            E load(Serializable id, Class<E> clazz) {
        
        if (registry.containsDAO(clazz)) {
            return (E) registry.getDAO(clazz).load(id);
        } else {
            return genericDAO.load(id, clazz);
        }
    }

    @Override
    public Serializable save(Persistable entity) {
        if (registry.containsDAO(entity.getClass())) {
            return registry.getDAO(entity.getClass()).save(entity);
        } else {
            return genericDAO.save(entity);
        }
    }

    @Override
    public void saveOrUpdate(Persistable entity) {
        if (registry.containsDAO(entity.getClass())) {
            registry.getDAO(entity.getClass()).saveOrUpdate(entity);
        } else {
            genericDAO.saveOrUpdate(entity);
        }
    }

    @Override
    public void update(Persistable entity) {
        if (registry.containsDAO(entity.getClass())) {
            registry.getDAO(entity.getClass()).update(entity);
        } else {
            genericDAO.update(entity);
        }
    }

}
