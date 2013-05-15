-- Table: negocio.tb_tarefa

-- DROP TABLE negocio.tb_tarefa;

CREATE TABLE negocio.tb_tarefa
(
  icodigo serial NOT NULL,
  snome character varying(80) NOT NULL, -- campo nome
  duracao integer not null,
  data_inicio timestamp not null, 
  data_termino  timestamp, 
  data_entrega timestamp, 
  idWinTarefa character varying(50) NOT NULL,
  CONSTRAINT icodigo_tb_tarefa_pk PRIMARY KEY (icodigo)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE negocio.tb_tarefa
  OWNER TO outline;
COMMENT ON TABLE negocio.tb_tarefa
  IS 'Tabela da Tarefa';
COMMENT ON COLUMN negocio.tb_tarefa.snome IS 'campo nome';

