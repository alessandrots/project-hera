-- Table: tb_estagiario

-- DROP TABLE tb_estagiario;

CREATE TABLE tb_estagiario
(
  icodigo integer NOT NULL,
  snome character varying(100) NOT NULL,
  cativo character(1) NOT NULL,
  CONSTRAINT tb_estagiario_pkey PRIMARY KEY (icodigo)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE tb_estagiario OWNER TO postgres;
