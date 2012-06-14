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
 * Contract for the Generic DAO that provides common operations on datastore.
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>
 * @version 2012-06-10
 * @since 1.0
 */
public interface GenericDAO {

    /**
     * Count number of instances of given entity class.
     * 
     * @param clazz na entity class
     * @return number of records
     */
    Long count(Class<? extends Persistable> clazz);

    
    /**
     * Remove a persistent instance from the datastore.
     *
     * @param entity the instance to be removed
     */
    void delete(Persistable entity);
    

    /**
     * Remove the persistent instance of the given entity class with the given 
     * identifier. It may throw <tt>NoResultException</tt> if no such instance 
     * exist depending on the implementation.
     *
     * @param id a primary key
     * @param clazz an entity class
     */
    void delete(Serializable id, Class<? extends Persistable> clazz);
    
    
    /**
     * Find persistent instances of the given entity class that are equal in 
     * listed properties with the example instance and (optinally) apply
     * pagination and ordering.
     *
     * @param exampleInstance an example instance
     * @param includeProperties properties to match with
     * @param paging a paging & ordering
     * @param clazz an entity class
     * @return the list of matched query results
     */
    <E extends Persistable> 
            List<E> findByExample(E exampleInstance, String[] includeProperties, PagingOrdering paging, Class<E> clazz);

    
    /**
     * Return the persistent instance of the given entity class with the given
     * natural key. The entity class must have exactly one property annotated as 
     * Natural Key/Identifier. When no such instance exist, it may return 
     * <tt>null</tt> or throw <tt>NoResultException</tt> depending on the
     * implementation.
     * 
     * <p>Not every JPA framework support this operation! For example Hibernate 
     * does have annotation <tt>@NaturalId</tt>, pure JPA doesn't.</p>
     * 
     * @param naturalKey a natural key
     * @param clazz an entity class
     * @return the persistent instance
     */
    <E extends Persistable> 
            E findByNaturalKey(Object naturalKey, Class<E> clazz);
    
    
    /**
     * Return the persistent instance of the given entity class with the given 
     * identifier. When no such instance exist, it may return <tt>null</tt> or 
     * throw <tt>NoResultException</tt> depending on the implementation.
     *
     * @param id a primary key
     * @param clazz an entity class
     * @return the persistent instance
     */
    <E extends Persistable> 
            E findByPrimaryKey(Serializable id, Class<E> clazz);


    /**
     * Find persistent instances of the given entity class by the given property
     * value and (optionally) apply pagination.
     *
     * @param property a property name to match with
     * @param value the property value
     * @param paging a paging & ordering
     * @param clazz an entity class
     * @return the list of matched query results
     */
    <E extends Persistable>
            List<E> findByProperty(String property, Object value, PagingOrdering paging, Class<E> clazz);
    
    
    /**
     * Return all persistent instances of the given entity class.
     *
     * @param clazz an entity class
     * @return the list of persistent instances
     */
    <E extends Persistable> 
            List<E> getAll(Class<E> clazz);

    
    /**
     * Return persistent instances of the given entity class according to the
     * given paging & ordering.
     *
     * @param paging a paging & ordering
     * @param clazz an entity class
     * @return the list of matched query results
     */
    <E extends Persistable>
            List<E> getPaginated(PagingOrdering paging, Class<E> clazz);

    
    /**
     * Return <code>true</code> if some instance of the given entity class and
     * the given identifier is persistent.
     *
     * @param id a primary key
     * @param clazz an entity class
     * @return <tt>true</tt> if there is some instance, <tt>false</tt> otherwise
     */
    boolean isPersistent(Serializable id, Class<? extends Persistable> clazz);

    
    /**
     * Return lazy-loaded persistent instance of the given entity class with the 
     * given identifier, assuming that the instance exists. This method might 
     * return a proxied instance that is initialized on-demand, when 
     * a non-identifier method is accessed.
     * 
     * <p>You should not use this method to determine if an instance exists 
     * (use {@linkplain #get() get()} instead). Use this only to retrieve an 
     * instance that you assume exists, where non-existence would be an actual 
     * error.</p>
     * 
     * <p>Not every ORM framework support this operation!</p>
     *
     * @param id a primary key
     * @param clazz an entity class
     * @return the persistent instance
     */
    <E extends Persistable>
            E load(Serializable id, Class<E> clazz);

    
    /**
     * Persist the given transient instance, first assigning a generated 
     * identifier. (Or using the current value of the identifier property if 
     * the assigned generator is used.)
     *
     * @param entity a transient instance of a entity class
     * @return the generated identifier
     */
    Serializable save(Persistable entity);

    
    /**
     * Either {@linkplain #save() save()} or {@linkplain #update() update()} the 
     * given instance, depending upon its state.
     *
     * @param entity a transient or detached instance containing new or updated state
     */
    void saveOrUpdate(Persistable entity);

    
    /**
     * Update the state of the persistent instance with the given detached 
     * instance. The instance must not be already binded with the current session
     * (if that so exception may be throwed)! Usually dirty-checking is used 
     * rather than manually calling this operation.
     *
     * @param entity a detached instance containing updated state
     */
    void update(Persistable entity);

}
