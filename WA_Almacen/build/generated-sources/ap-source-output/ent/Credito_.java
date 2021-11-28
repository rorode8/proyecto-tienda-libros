package ent;

import ent.Cliente;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-11-27T17:26:28")
@StaticMetamodel(Credito.class)
public class Credito_ { 

    public static volatile SingularAttribute<Credito, BigDecimal> amount;
    public static volatile SingularAttribute<Credito, Cliente> clienteId;
    public static volatile SingularAttribute<Credito, Integer> id;

}