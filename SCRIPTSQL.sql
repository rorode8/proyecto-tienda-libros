connect 'jdbc:derby://localhost:1527/test;user=app;password=app';

DROP TABLE libro;
DROP TABLE cliente;
DROP TABLE credito;
DROP TABLE pedido;
DROP TABLE entrega;

CREATE  TABLE  libro
(
  id       INT  NOT NULL GENERATED ALWAYS AS IDENTITY 
                (START WITH 1 ,INCREMENT BY 1)
           CONSTRAINT LIBRO_PK PRIMARY KEY,
  isbn     VARCHAR(45) NOT NULL,
  cantidad INT NOT NULL
  );

------------------------------- 
--     CLIENTE
------------------------------- 
CREATE  TABLE  cliente
(
  id       INT  NOT NULL GENERATED ALWAYS AS IDENTITY 
                (START WITH 1 ,INCREMENT BY 1)
                CONSTRAINT CLIENTE_PK PRIMARY KEY,
  nombre   VARCHAR(60)   
  
);
------------------------------- 
--     CREDITO
-------------------------------
CREATE  TABLE  credito 
(
  id       INT  NOT NULL GENERATED ALWAYS AS IDENTITY 
                (START WITH 1 ,INCREMENT BY 1)
                CONSTRAINT CREDITO_PK PRIMARY KEY,
    
  amount   DECIMAL(6,2) NOT NULL ,
	
  cliente_id INT NOT NULL CONSTRAINT CRED_FK REFERENCES  cliente
);
------------------------------- 
--     Pedido
-------------------------------
CREATE  TABLE  pedido 
(
  id       INT  NOT NULL GENERATED ALWAYS AS IDENTITY 
                (START WITH 1 ,INCREMENT BY 1)
                CONSTRAINT PEDIDO_PK PRIMARY KEY,
    
  cantidad   INT NOT NULL ,

  cliente_id INT NOT NULL CONSTRAINT PED_CLI_FK REFERENCES  cliente,
  libro_id INT NOT NULL CONSTRAINT PED_LIB_FK REFERENCES  libro
);
------------------------------- 
--     ENTREGA
-------------------------------
CREATE  TABLE entrega
(
  id       INT  NOT NULL GENERATED ALWAYS AS IDENTITY 
           (START WITH 1 ,INCREMENT BY 1)
           CONSTRAINT ENTRGA_PK PRIMARY KEY,
  
  dias     INT NOT NULL ,
  pedido INT NOT NULL CONSTRAINT ENTREGA_FK REFERENCES  pedido
);

INSERT INTO libro (isbn, cantidad) VALUES
('2928183',400),
('abc',1000),
('3223893',900);

INSERT INTO cliente (nombre) VALUES
('lestat'),
('rod');

INSERT INTO credito (amount, cliente_id) VALUES
(4000.0, 1),
(7000.0, 2);

INSERT INTO pedido (cantidad, cliente_id, libro_id) VALUES
(3,1,1),
(4,2,1);

INSERT INTO entrega (dias, pedido) VALUES
(1,1),
(3,2);

disconnect;

