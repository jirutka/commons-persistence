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
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Abstract implementation of the {@linkplain SpecificDAO} that simply delegates
 * all calls to the given instance of {@linkplain GenericDAO}. Every specific
 * implementation of <tt>SpecificDAO</tt> should extend from this class.
 * 
 * @author Jakub Jirutka <jakub@jirutka.cz>
 * @version 2012-06-12
 * @since 1.0
 *
 * @param <E> an entity class type
 * @param <ID> an identifier type
 */
public abstract class DelegatingSpecificDAO 
        <E extends Persistable, ID extends Serializable> implements SpecificDAO<E, ID> {

    private static final Logger LOG = LoggerFactory.getLogger(DelegatingSpecificDAO.class);

    protected final Class<E> entityClass;
    private GenericDAO genericDAO;



    public DelegatingSpecificDAO(GenericDAO genericDAO) {
        this.genericDAO = genericDAO;
        this.entityClass = determineEntityClass();
        System.out.println("___entityClass = " + this.entityClass);
        LOG.debug("Initializing {} for entity {}", this.getClass().getSimpleName(), entityClass.getSimpleName());
    }
    /**
     * If you want to use non-parametric constructor, then you MUST 
     * {@linkplain #setGenericDAO() set genericDAO} manually just after 
     * initialization!
     * This is useful when you're using some DI framework like Spring.
     */
    public DelegatingSpecificDAO() {
        this(null);
    }

    
    @Override
    public Class<E> getEntityClass() {
        return entityClass;
    }
    
    /**
     * @return underlying generic DAO
     */
    protected GenericDAO getGenericDAO() {
        return genericDAO;
    }
    /**
     * @param genericDAO underlying generic DAO
     */
    protected void setGenericDAO(GenericDAO genericDAO) {
        assert genericDAO != null;
        this.genericDAO = genericDAO;
    }
    
    private Class<E> determineEntityClass() {
        TypeVariable<?> typeVarE = DelegatingSpecificDAO.class.getTypeParameters()[0];
        Type implType = this.getClass();
        
        return (Class<E>) TypeUtils.getTypeArguments(implType, DelegatingSpecificDAO.class).get(typeVarE);
    }


    ///// Delegate to genericDAO /////

    @Override
    public Long count() {
        return genericDAO.count(entityClass);
    }

    @Override
    public void delete(E entity) {
        genericDAO.delete(entity);
    }

    @Override
    public void delete(ID id) {
        genericDAO.delete(id, entityClass);
    }

    @Override
    public List<E> findByExample(E exampleInstance, String[] includeProperties, Paging paging) {
        return genericDAO.findByExample(exampleInstance, includeProperties, paging, entityClass);
    }
    
    @Override
    public E findByNaturalKey(Object naturalKey) {
        return genericDAO.findByNaturalKey(naturalKey, entityClass);
    }
    
    @Override
    public E findByPrimaryKey(ID id) {
        return genericDAO.findByPrimaryKey(id, entityClass);
    }

    @Override
    public List<E> findByProperty(String property, Object value, Paging paging) {
        return genericDAO.findByProperty(property, value, paging, entityClass);
    }
    
    @Override
    public List<E> getAll() {
        return genericDAO.getAll(entityClass);
    }

    @Override
    public List<E> getPaginated(Paging paging) {
        return genericDAO.getPaginated(paging, entityClass);
    }

    @Override
    public boolean isPersistent(ID id) {
        return genericDAO.isPersistent(id, entityClass);
    }

    @Override
    public E load(ID id) {
        return genericDAO.load(id, entityClass);
    }

    @Override
    public ID save(E entity) {
        return (ID) genericDAO.save(entity);
    }

    @Override
    public void saveOrUpdate(E entity) {
        genericDAO.saveOrUpdate(entity);
    }

    @Override
    public void update(E entity) {
        genericDAO.update(entity);
    }

}
