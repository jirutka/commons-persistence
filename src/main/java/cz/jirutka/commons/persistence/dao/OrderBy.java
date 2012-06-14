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
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * This class represents an order by single property for {@link PagingOrdering}.
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>
 * @version 2012-06-15
 * @since 1.0
 */
public class OrderBy implements Serializable {
    
    /** Ascending order */
    public static final boolean ASC = true;
    
    /** Descending order */
    public static final boolean DESC = false;
    
    private final String propertyName;
    private final boolean ascending;


    /**
     * Create a new instance of <tt>OrderBy</tt>. You can use {@link #ASC} /
     * {@link #DESC} or simply <tt>true</tt> / <tt>false</tt> for sorting
     * direction.
     * 
     * @param propertyName name of property to order by
     * @param ascending <tt>true</tt> for ascending, <tt>false</tt> for descending
     */
    public OrderBy(String propertyName, boolean ascending) {
        this.propertyName = propertyName;
        this.ascending = ascending;
    }

    /**
     * Ascending order
     * 
     * @param propertyName name of property to order by
     * @return OrderBy
     */
    public static OrderBy asc(String propertyName) {
        return new OrderBy(propertyName, OrderBy.ASC);
    }

    /**
     * Descending order
     * 
     * @param propertyName name of property to order by
     * @return OrderBy
     */
    public static OrderBy desc(String propertyName) {
        return new OrderBy(propertyName, OrderBy.DESC);
    }


    /**
     * Ascending or descending order?
     * 
     * @return <tt>true</tt> if ascending, <tt>false</tt> if descending
     */
    public boolean isAscending() {
        return ascending;
    }

    /**
     * Return name of the property to order by.
     * 
     * @return property name
     */
    public String getPropertyName() {
        return propertyName;
    }
    

    @Override
    public String toString() {
        return propertyName + ' ' + (ascending ? "asc" : "desc");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        final OrderBy other = (OrderBy) obj;
        return new EqualsBuilder()
                .append(propertyName, other.propertyName)
                .append(ascending, other.ascending)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(5, 83).append(propertyName).append(ascending).toHashCode();
    }

}
