/*
 * component:   "openEHR Reference Implementation"
 * description: "Class RevisionHistoryItem"
 * keywords:    "common"
 *
 * author:      "Yin Su Lim <y.lim@chime.ucl.ac.uk>"
 * support:     "CHIME, UCL"
 * copyright:   "Copyright (c) 2006 UCL, UK"
 * license:     "See notice at bottom of class"
 *
 * file:        "$URL: http://svn.openehr.org/ref_impl_java/TRUNK/libraries/src/java/org/openehr/rm/common/generic/RevisionHistoryItem.java $"
 * revision:    "$LastChangedRevision: 53 $"
 * last_change: "$LastChangedDate: 2006-08-11 13:20:08 +0100 (Fri, 11 Aug 2006) $"
 */
package org.openehr.rm.common.generic;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.openehr.rm.FullConstructor;
import org.openehr.rm.RMObject;
import org.openehr.rm.support.identification.ObjectVersionID;

/**
 * Represent an entry in revision history
 *
 * @author Yin Su Lim
 * @version 1.0
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public final class RevisionHistoryItem extends RMObject {

	/**
	 * Constructs a RevisionHistoryItem
	 * 
	 * @param audits		not null
	 * @param versionId	not null
	 * @throws IllegalArgumentException if audits null or empty, versionId null
	 */
	@FullConstructor
	public RevisionHistoryItem(List<AuditDetails> audits, ObjectVersionID versionID) {
	       if (audits == null || audits.size() == 0) {
	            throw new IllegalArgumentException("empty audits");
	        }
	       if (versionID == null) {
	    	   	throw new IllegalArgumentException("null versionId");
	       }
	       this.audits = audits;
	       this.versionId = versionID;
	}
	
	/**
	 * The audits for this revision. There will always be at least one commit audit
	 * 
	 * @return audits
	 */
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name = "RM_REVISION_HISTORY_ITEM_audits",
		joinColumns = {@JoinColumn(name = "RM_REVISION_HISTORY_ITEM_mappingId")},
		inverseJoinColumns = {@JoinColumn(name = "RM_AUDIT_DETAILS_mappingId")}
		)
	public List<AuditDetails> getAudits() {
		return audits;
	}

	/**
	 * Version identifier for this revision
	 * 
	 * @return versionId
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	public ObjectVersionID getVersionId() {
		return versionId;
	}	

    /**
     * Equals if two has same values
     *
     * @param o
     * @return true if equals
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!( o instanceof RevisionHistoryItem )) return false;

        final RevisionHistoryItem rhi = (RevisionHistoryItem) o;

        return new EqualsBuilder()
                .append(audits, rhi.audits)
                .append(versionId, rhi.versionId)
                .isEquals();
    }

    /**
     * Return a hash code of this object
     *
     * @return hash code
     */
    public int hashCode() {
        return new HashCodeBuilder(3, 97)
                .append(audits)
                .append(versionId)
                .toHashCode();
    }
    //POJO start
    RevisionHistoryItem() {
    }

    void setAudits(List<AuditDetails> audits) {
            this.audits = audits;
    }

    void setVersionId(ObjectVersionID versionID) {
            this.versionId = versionID;
    }
    //POJO end

    /* fields */
    private List<AuditDetails> audits;
    private ObjectVersionID versionId;
    
    private int mappingId;

	@Id
	@GeneratedValue
	public int getMappingId() {
		return mappingId;
	}

	public void setMappingId(int mappingId) {
		this.mappingId = mappingId;
	}
}

/*
 *  ***** BEGIN LICENSE BLOCK *****
 *  Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 *  The contents of this file are subject to the Mozilla Public License Version
 *  1.1 (the 'License'); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *  http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an 'AS IS' basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 *  for the specific language governing rights and limitations under the
 *  License.
 *
 *  The Original Code is RevisionHistoryItem.java
 *
 *  The Initial Developer of the Original Code is Yin Su Lim.
 *  Portions created by the Initial Developer are Copyright (C) 2003-2004
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 * Software distributed under the License is distributed on an 'AS IS' basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 *  ***** END LICENSE BLOCK *****
 */