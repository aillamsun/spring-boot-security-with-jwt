package com.chinawiserv.core.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by sungang on 2017/8/19.
 */
public class BaseModel implements Serializable {

    private static final long serialVersionUID = -3707046914855595598L;

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public final <T> T simpleClone() {
        try {
            @SuppressWarnings("unchecked")
            T ret = (T) clone();
            return ret;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
