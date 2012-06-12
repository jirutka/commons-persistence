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
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of the {@linkplain SpecificDAORegistry} interface for 
 * generic {@linkplain SpecificDAO} that stores DAOs in internal hashmap.
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>
 * @version 2012-06-12
 * @since 1.0
 */
public class DefaultSpecificDAORegistry implements SpecificDAORegistry<SpecificDAO> {
    
    private static final Logger LOG = LoggerFactory.getLogger(DefaultSpecificDAORegistry.class);
    
    private final Map<Class, SpecificDAO> registry = new HashMap<Class, SpecificDAO>();

    
    @Override
    public boolean containsDAO(Class<? extends Persistable> entityClass) {
        return registry.containsKey(entityClass);
    }

    @Override
    public SpecificDAO getDAO(Class<? extends Persistable> entityClass) {
        return registry.get(entityClass);
    }

    @Override
    public void register(SpecificDAO specificDAO) {
        LOG.info("Registering Specific DAO for entity class: {}", specificDAO.getEntityClass());
        registry.put(specificDAO.getEntityClass(), specificDAO);
    }
}
