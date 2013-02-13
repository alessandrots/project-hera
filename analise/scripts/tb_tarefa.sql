-- Table: tb_tarefa

-- DROP TABLE tb_tarefa;

CREATE TABLE tb_tarefa
(
  icodigo integer NOT NULL,
  snome character varying(100) NOT NULL,
  dataInicio timestamp NOT NULL,
  dataFim timestamp  NOT NULL,
  CONSTRAINT tb_tarefa_pkey PRIMARY KEY (icodigo)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE tb_tarefa OWNER TO postgres;