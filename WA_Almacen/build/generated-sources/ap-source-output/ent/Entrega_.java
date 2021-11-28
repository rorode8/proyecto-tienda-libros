package ent;

import ent.Pedido;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-11-27T17:26:28")
@StaticMetamodel(Entrega.class)
public class Entrega_ { 

    public static volatile SingularAttribute<Entrega, Integer> dias;
    public static volatile SingularAttribute<Entrega, Pedido> pedido;
    public static volatile SingularAttribute<Entrega, Integer> id;

}