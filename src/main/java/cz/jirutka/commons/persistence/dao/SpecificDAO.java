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
 * Contract for a specific DAO that provides common operations on datastore for
 * the particular entity type.
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>
 * @version 2012-06-10
 * @since 1.0
 *
 * @param <E> an entity type
 * @param <ID> a primary key type
 */
public interface SpecificDAO <E extends Persistable, ID extends Serializable> {

    /**
     * Count number of instances.

     * @return number of records
     */
    Long count();

    
    /**
     * Remove a persistent instance from the datastore.
     *
     * @param entity the instance to be removed
     */
    void delete(E entity);
    

    /**
     * Remove the persistent instance with the given identifier. It may throw
     * <tt>NoResultException</tt> if no such instance exist depending on the 
     * implementation.
     *
     * @param id a primary key
     */
    void delete(ID id);
    
    
    /**
     * Find persistent instances that are equal in listed properties with the 
     * example instance and (optinally) apply pagination and ordering.
     *
     * @param exampleInstance an example instance
     * @param includeProperties properties to match with
     * @param paging a paging & ordering
     * @return the list of matched query results
     */
    List<E> findByExample(E exampleInstance, String[] includeProperties, PagingOrdering paging);

    
    /**
     * Return the persistent instance with the given natural key. The entity 
     * class must have exactly one property annotated as Natural Key/Identifier. 
     * When no such instance exist, it may return <tt>null</tt> or throw 
     * <tt>NoResultException</tt> depending on the implementation.
     * 
     * <p>Not every JPA framework support this operation! For example Hibernate 
     * does have annotation <tt>@NaturalId</tt>, pure JPA doesn't.</p>
     * 
     * @param naturalKey a natural key
     * @return the persistent instance
     */
    E findByNaturalKey(Object naturalKey);
    
    
    /**
     * Return the persistent instance with the given identifier. When no such 
     * instance exist, it may return <tt>null</tt> or throw 
     * <tt>NoResultException</tt> depending on the implementation.
     *
     * @param id a primary key
     * @return the persistent instance
     */
    E findByPrimaryKey(ID id);


    /**
     * Find persistent instances by the given property value and (optionally) 
     * apply pagination.
     *
     * @param property a property name to match with
     * @param value the property value
     * @param paging a paging & ordering
     * @return the list of matched query results
     */
    List<E> findByProperty(String property, Object value, PagingOrdering paging);
    
    
    /**
     * Return all persistent instances.
     *
     * @return the list of persistent instances
     */
    List<E> getAll();

    
    /**
     * Return persistent instances according to the given paging & ordering.
     *
     * @param paging a paging & ordering
     * @return the list of matched query results
     */
    List<E> getPaginated(PagingOrdering paging);

    
    /**
     * Return <code>true</code> if some instance with the the given identifier 
     * is persistent.
     *
     * @param id a primary key
     * @return <tt>true</tt> if there is some instance, <tt>false</tt> otherwise
     */
    boolean isPersistent(ID id);

    
    /**
     * Return lazy-loaded persistent instance with the given identifier, assuming 
     * that the instance exists. This method might return a proxied instance that 
     * is initialized on-demand, when a non-identifier method is accessed.
     * 
     * <p>You should not use this method to determine if an instance exists 
     * (use {@linkplain #get() get()} instead). Use this only to retrieve an 
     * instance that you assume exists, where non-existence would be an actual 
     * error.</p>
     * 
     * <p>Not every ORM framework support this operation!</p>
     *
     * @param id a primary key
     * @return the persistent instance
     */
    E load(ID id);

    
    /**
     * Persist the given transient instance, first assigning a generated 
     * identifier. (Or using the current value of the identifier property if 
     * the assigned generator is used.)
     *
     * @param entity a transient instance of a persistent class
     * @return the generated identifier
     */
    ID save(E entity);

    
    /**
     * Either {@linkplain #save() save()} or {@linkplain #update() update()} the 
     * given instance, depending upon its state.
     *
     * @param entity a transient or detached instance containing new or updated state
     */
    void saveOrUpdate(E entity);

    
    /**
     * Update the state of the persistent instance with the given detached 
     * instance. The instance must not be already binded with the current session
     * (if that so exception may be throwed)! Usually dirty-checking is used 
     * rather than manually calling this operation.
     *
     * @param entity a detached instance containing updated state
     */
    void update(E entity);
    
    /**
     * Return the entity class of this DAO.
     * 
     * @return the entity class
     */
    Class<E> getEntityClass();
    
}
