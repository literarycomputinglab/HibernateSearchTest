/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.hibernatesearchtest;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Facet;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

/**
 *
 * @author simone
 */
@Entity
@Indexed
public class Annotation implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

//    @Field
//    @ManyToMany
//    List<Annotation> annotations;

    @OneToOne (cascade = {CascadeType.ALL})
    @IndexedEmbedded
    Content cont;

    @OneToMany(mappedBy = "annotation", cascade = {CascadeType.ALL})
    @IndexedEmbedded 
    List<Locus> loci;

    @Field(analyze = Analyze.NO)
    @Facet
    String type;
//    public List<Annotation> getAnnotations() {
//        return annotations;
//    }
//
//    public void setAnnotations(List<Annotation> annotations) {
//        this.annotations = annotations;
//    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Content getCont() {
        return cont;
    }

    public void setCont(Content cont) {
        this.cont = cont;
    }

    public List<Locus> getLoci() {
        return loci;
    }

    public void setLoci(List<Locus> loci) {
        this.loci = loci;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
