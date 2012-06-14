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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * This class holds information for paging & ordering in one object to
 * simplify DAO methods.
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>
 * @version 2012-06-15
 * @since 1.0
 */
public class PagingOrdering implements Serializable {
    
    /** Null object */
    public static final PagingOrdering NONE = new PagingOrdering();
    
    private int limit = -1;
    private int offset = 0;
    private List<OrderBy> ordering = new ArrayList<OrderBy>(1);

    
    
    /**
     * Create a new empty instance.
     */
    public PagingOrdering() {
    }
    /**
     * Create a new instance of <tt>PagingOrdering</tt> with the given limit and 
     * offset.
     * 
     * @param limit maximum number of results (default -1; non-limit)
     * @param offset offset of the first result to be retrieved, numbered from 0
     */
    public PagingOrdering(int limit, int offset) {
        this.limit = limit;
        this.offset = offset;
    }
    /**
     * Create a new instance of <tt>PagingOrdering</tt> with the given limit, 
     * offset and ordering by a single column.
     * 
     * @param limit maximum number of results (default -1; non-limit)
     * @param offset offset of the first result to be retrieved, numbered from 0
     * @param order object representing an ordering to be applied to the results
     */
    public PagingOrdering(int limit, int offset, OrderBy order) {
        this(limit, offset);
        ordering.add(order);
    }
    /**
     * Create a new instance of <tt>PagingOrdering</tt> with the given limit, 
     * offset and multi-dimensional ordering.
     * 
     * @param limit maximum number of results (default -1; non-limit)
     * @param offset offset of the first result to be retrieved, numbered from 0
     * @param ordering list of {@linkplain OrderBy orders}, i.e. multi-dimensional ordering
     */
    public PagingOrdering(int limit, int offset, List<OrderBy> ordering) {
        this.limit = limit;
        this.offset = offset;
        this.ordering = ordering;
    }
    
    
    ///// Fluent interface /////
    
    /**
     * Fluent alternative for {@link #setLimit(int) setLimit()}.
     */
    public PagingOrdering limit(int limit) {
        this.limit = limit;
        return this;
    }
    /**
     * Fluent alternative for {@link #setOffset(int) setOffset()}.
     */
    public PagingOrdering offset(int offset) {
        this.offset = offset;
        return this;
    }
    
    /**
     * Add an {@linkplain OrderBy order} by the given property in ascending 
     * direction. This is a fluent alternative for {@link #addOrderBy(OrderBy) 
     * addOrderBy()}.
     * 
     * @param propertyName name of property to order by
     * @return this (for method-chain)
     */
    public PagingOrdering orderBy(String propertyName) {
        ordering.add(new OrderBy(propertyName, OrderBy.ASC));
        return this;
    }
    /**
     * Add an {@linkplain OrderBy order} by the given property in ascending 
     * ({@linkplain OrderBy#ASC}) or descending ({@linkplain OrderBy#DESC}) 
     * direction. This is a fluent alternative for {@link #addOrderBy(OrderBy) 
     * addOrderBy()}.
     * 
     * @param propertyName name of property to order by
     * @param ascending {@linkplain OrderBy#ASC} / {@linkplain OrderBy#DESC}
     * @return this (for method-chain)
     */
    public PagingOrdering orderBy(String propertyName, boolean ascending) {
        ordering.add(new OrderBy(propertyName, ascending));
        return this;
    }

    
    ///// Normal interface /////
    
    /**
     * @return maximum number of results (default -1; non-limit)
     */
    public int getLimit() { return limit; }
    /**
     * Set a limit upon the number of results to be retrieved.
     * 
     * @param limit maximum number of results (default -1; non-limit)
     */
    public void setLimit(int limit) { this.limit = limit; }

    /**
     * @return results offset, numbered from 0 (default 0)
     */
    public int getOffset() { return offset; }
    /**
     * Set a offset of the first result to be retrieved.
     * 
     * @param offset results offset, numbered from 0 (default 0)
     */
    public void setOffset(int offset) { this.offset = offset; }


    /**
     * Add an {@linkplain OrderBy order by} statement.
     * 
     * @param orderBy object representing an ordering to be applied to the results
     */
    public void addOrderBy(OrderBy orderBy) {
        ordering.add(orderBy);
    }
    /**
     * @return the list of ordering
     */
    public List<OrderBy> getOrdering() {
        return ordering;
    }
    /**
     * @return <tt>true</tt> if ordering is defined, <tt>false</tt> otherwise
     */
    public boolean hasOrdering() {
        return !ordering.isEmpty();
    }

    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        
        final PagingOrdering other = (PagingOrdering) obj;
        return new EqualsBuilder()
                .append(limit, other.limit)
                .append(offset, other.offset)
                .append(ordering, other.ordering)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(7, 17)
                .append(limit)
                .append(offset)
                .append(ordering)
                .toHashCode();
    }
    
}
