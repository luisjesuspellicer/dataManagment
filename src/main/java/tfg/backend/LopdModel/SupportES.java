/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class represents a e/s register for one support.
 */

package tfg.backend.LopdModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "SupportES")
public class SupportES implements Serializable{


    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    private Support support;

    @Column(name = "date")
    private Date date;

    @Column(name = "emisor")
    private String emisor;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "typeInformation")
    private String typeInformation;

    @Column(name = "shippingWay")
    private String  shippingWay;

    @Column(name = "deliveryResponsible")
    private String  deliveryResponsible;

    @Column(name = "receivingResponsible")
    private String receivingResponsible;


    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Support getSupport() {

        return support;
    }

    public void setSupport(Support support) {

        this.support = support;
    }

    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {

        this.date = date;
    }

    public String getEmisor() {

        return emisor;
    }

    public void setEmisor(String emisor) {

        this.emisor = emisor;
    }

    public String getReceiver() {

        return receiver;
    }

    public void setReceiver(String receiver) {

        this.receiver = receiver;
    }

    public String getTypeInformation() {

        return typeInformation;
    }

    public void setTypeInformation(String typeInformation) {

        this.typeInformation = typeInformation;
    }

    public String getShippingWay() {

        return shippingWay;
    }

    public void setShippingWay(String shippingWay) {

        this.shippingWay = shippingWay;
    }

    public String getDeliveryResponsible() {

        return deliveryResponsible;
    }

    public void setDeliveryResponsible(String deliveryResponsible) {

        this.deliveryResponsible = deliveryResponsible;
    }

    public String getReceivingResponsible() {

        return receivingResponsible;
    }

    public void setReceivingResponsible(String receivingResponsible) {

        this.receivingResponsible = receivingResponsible;
    }
}
