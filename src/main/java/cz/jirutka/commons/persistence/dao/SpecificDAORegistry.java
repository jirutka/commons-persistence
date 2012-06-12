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

/**
 * Interface of the {@linkplain SpecificDAO specific DAOs} registry for a
 * {@linkplain GenericDAODispatcher DAO dispatcher}.
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>
 * @version 2012-06-12
 * @since 1.0
 * 
 * @param <DAO> {@link SpecificDAO} type (optionally)
 */
public interface SpecificDAORegistry <DAO extends SpecificDAO> {

    /**
     * Does it contain {@linkplain SpecificDAO} for the given entity class?
     * 
     * @param entityClass an entity class
     * @return <tt>true</tt> if contains suitable DAO, <tt>false</tt> otherwise
     */
    boolean containsDAO(Class<? extends Persistable> entityClass);
    
    /**
     * Return the specific DAO for the given entity class.
     * 
     * @param entityClass an entity class
     * @return the <tt>SpecificDAO</tt> instance
     */
    DAO getDAO(Class<? extends Persistable> entityClass);
    
    /**
     * Register the given specific DAO.
     *
     * @param specificDAO a <tt>SpecificDAO</tt> instance
     */
    void register(DAO specificDAO);

}
