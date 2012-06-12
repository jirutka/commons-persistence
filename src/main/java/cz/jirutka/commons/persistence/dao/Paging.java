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

/**
 * Settings for pagination and ordering of query.
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>
 * @version 2012-06-10
 * @since 1.0
 */
public class Paging implements Serializable {
    
    public static final Paging NONE = new Paging();
    
    private int firstResult = 0;
    private int maxResults = -1;
    private String orderBy;
    private boolean ascending = true;

    
    public Paging() {
    }
    public Paging(int firstResult, int maxResults) {
        this(firstResult, maxResults, null);
    }
    public Paging(int firstResult, int maxResults, String orderBy) {
        this(firstResult, maxResults, orderBy, true);
    }
    public Paging(String orderBy, boolean ascending) {
        this(0, -1, orderBy, ascending);
    }
    public Paging(int firstResult, int maxResults, String orderBy, boolean ascending) {
        this.firstResult = firstResult;
        this.maxResults = maxResults;
        this.orderBy = orderBy;
        this.ascending = ascending;
    }


    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public int getFirstResult() {
        return firstResult;
    }

    /**
     * Set the first result to be retrieved.
     * 
     * @param firstResult the first result to retrieve, numbered from <tt>0</tt>
     */
    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getMaxResults() {
        return maxResults;
    }

    /**
     * Set a limit upon the number of objects to be retrieved.
     * 
     * @param maxResults the maximum number of results
     */
    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public String getOrderBy() {
        return orderBy;
    }

    /**
     * 
     * @param orderBy property name to order by
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        
        final Paging other = (Paging) obj;
        if (this.firstResult != other.firstResult) return false;
        if (this.maxResults != other.maxResults) return false;
        if (this.ascending != other.ascending) return false;
        if ((this.orderBy == null) ? (other.orderBy != null) : !this.orderBy.equals(other.orderBy)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.firstResult;
        hash = 17 * hash + this.maxResults;
        hash = 17 * hash + (this.orderBy != null ? this.orderBy.hashCode() : 0);
        hash = 17 * hash + (this.ascending ? 1 : 0);
        return hash;
    }    
    
}
