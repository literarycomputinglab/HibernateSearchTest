/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.hibernatesearchtest;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

/**
 *
 * @author simone
 */
@Entity
@Indexed
public class Annotation {

    @Id
    @GeneratedValue
    private Long id;

//    @Field
//    @ManyToMany
//    List<Annotation> annotations;

    @Field
    @OneToOne
    @IndexedEmbedded
    Content cont;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @IndexedEmbedded
    List<Locus> loci;

//    public List<Annotation> getAnnotations() {
//        return annotations;
//    }
//
//    public void setAnnotations(List<Annotation> annotations) {
//        this.annotations = annotations;
//    }

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
