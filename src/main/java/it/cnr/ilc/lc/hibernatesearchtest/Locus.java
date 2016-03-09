/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.hibernatesearchtest;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.NumericField;

/**
 *
 * @author simone
 */
@Entity
@Indexed
public class Locus {

    @Id
    @GeneratedValue
    private Long id;

    @Field
    @NumericField
    Integer start;

    @Field
    @NumericField
    Integer end;

    @Field
    String fragment;

    @OneToOne (cascade = {CascadeType.ALL})
    @IndexedEmbedded(includePaths = {"uri"})
    Source source;

    @ContainedIn
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    Annotation annotation;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public String getFragment() {
        return fragment;
    }

    public void setFragment(String fragment) {
        this.fragment = fragment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("%s: [%d - %d)", getFragment(), getStart(), getEnd());
    }

    
    
}
