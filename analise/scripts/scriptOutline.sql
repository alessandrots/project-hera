--
-- PostgreSQL database dump
--

-- Dumped from database version 9.1.9
-- Dumped by pg_dump version 9.1.9
-- Started on 2013-06-10 23:28:37 BRT

SET statement_timeout = 0;
SET client_encoding = 'SQL_ASCII';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 7 (class 2615 OID 16386)
-- Name: negocio; Type: SCHEMA; Schema: -; Owner: outline
--

CREATE SCHEMA negocio;


ALTER SCHEMA negocio OWNER TO outline;

--
-- TOC entry 1975 (class 0 OID 0)
-- Dependencies: 7
-- Name: SCHEMA negocio; Type: COMMENT; Schema: -; Owner: outline
--

COMMENT ON SCHEMA negocio IS 'Schema com as tabelas negociais do sistema POutline';


--
-- TOC entry 182 (class 3079 OID 11643)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 1978 (class 0 OID 0)
-- Dependencies: 182
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = negocio, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 181 (class 1259 OID 16555)
-- Dependencies: 7
-- Name: tb_calendario; Type: TABLE; Schema: negocio; Owner: outline; Tablespace: 
--

CREATE TABLE tb_calendario (
    icodigo integer NOT NULL,
    dia_domingo numeric(3,2) NOT NULL,
    dia_segunda numeric(3,2) NOT NULL,
    dia_terca numeric(3,2) NOT NULL,
    dia_quarta numeric(3,2) NOT NULL,
    dia_quinta numeric(3,2) NOT NULL,
    dia_sexta numeric(3,2) NOT NULL,
    dia_sabado numeric(3,2) NOT NULL,
    irecurso integer NOT NULL
);


ALTER TABLE negocio.tb_calendario OWNER TO outline;

--
-- TOC entry 1979 (class 0 OID 0)
-- Dependencies: 181
-- Name: TABLE tb_calendario; Type: COMMENT; Schema: negocio; Owner: outline
--

COMMENT ON TABLE tb_calendario IS 'Tabela de Calendário';


--
-- TOC entry 180 (class 1259 OID 16553)
-- Dependencies: 181 7
-- Name: tb_calendario_icodigo_seq; Type: SEQUENCE; Schema: negocio; Owner: outline
--

CREATE SEQUENCE tb_calendario_icodigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE negocio.tb_calendario_icodigo_seq OWNER TO outline;

--
-- TOC entry 1980 (class 0 OID 0)
-- Dependencies: 180
-- Name: tb_calendario_icodigo_seq; Type: SEQUENCE OWNED BY; Schema: negocio; Owner: outline
--

ALTER SEQUENCE tb_calendario_icodigo_seq OWNED BY tb_calendario.icodigo;


--
-- TOC entry 167 (class 1259 OID 16421)
-- Dependencies: 7
-- Name: tb_eap; Type: TABLE; Schema: negocio; Owner: outline; Tablespace: 
--

CREATE TABLE tb_eap (
    icodigo integer NOT NULL,
    snome character varying(80) NOT NULL,
    sdescricao text,
    iprojeto integer NOT NULL
);


ALTER TABLE negocio.tb_eap OWNER TO outline;

--
-- TOC entry 1981 (class 0 OID 0)
-- Dependencies: 167
-- Name: TABLE tb_eap; Type: COMMENT; Schema: negocio; Owner: outline
--

COMMENT ON TABLE tb_eap IS 'Tabela de EAP';


--
-- TOC entry 166 (class 1259 OID 16419)
-- Dependencies: 7 167
-- Name: tb_eap_icodigo_seq; Type: SEQUENCE; Schema: negocio; Owner: outline
--

CREATE SEQUENCE tb_eap_icodigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE negocio.tb_eap_icodigo_seq OWNER TO outline;

--
-- TOC entry 1982 (class 0 OID 0)
-- Dependencies: 166
-- Name: tb_eap_icodigo_seq; Type: SEQUENCE OWNED BY; Schema: negocio; Owner: outline
--

ALTER SEQUENCE tb_eap_icodigo_seq OWNED BY tb_eap.icodigo;


--
-- TOC entry 169 (class 1259 OID 16437)
-- Dependencies: 7
-- Name: tb_fase; Type: TABLE; Schema: negocio; Owner: outline; Tablespace: 
--

CREATE TABLE tb_fase (
    icodigo integer NOT NULL,
    snome character varying(80) NOT NULL,
    sdescricao text,
    ieap integer NOT NULL
);


ALTER TABLE negocio.tb_fase OWNER TO outline;

--
-- TOC entry 1983 (class 0 OID 0)
-- Dependencies: 169
-- Name: TABLE tb_fase; Type: COMMENT; Schema: negocio; Owner: outline
--

COMMENT ON TABLE tb_fase IS 'Tabela de Fase';


--
-- TOC entry 168 (class 1259 OID 16435)
-- Dependencies: 7 169
-- Name: tb_fase_icodigo_seq; Type: SEQUENCE; Schema: negocio; Owner: outline
--

CREATE SEQUENCE tb_fase_icodigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE negocio.tb_fase_icodigo_seq OWNER TO outline;

--
-- TOC entry 1984 (class 0 OID 0)
-- Dependencies: 168
-- Name: tb_fase_icodigo_seq; Type: SEQUENCE OWNED BY; Schema: negocio; Owner: outline
--

ALTER SEQUENCE tb_fase_icodigo_seq OWNED BY tb_fase.icodigo;


--
-- TOC entry 173 (class 1259 OID 16480)
-- Dependencies: 7
-- Name: tb_pacote_trabalho; Type: TABLE; Schema: negocio; Owner: outline; Tablespace: 
--

CREATE TABLE tb_pacote_trabalho (
    icodigo integer NOT NULL,
    snome character varying(80) NOT NULL,
    sdescricao text,
    iproduto integer NOT NULL
);


ALTER TABLE negocio.tb_pacote_trabalho OWNER TO outline;

--
-- TOC entry 1985 (class 0 OID 0)
-- Dependencies: 173
-- Name: TABLE tb_pacote_trabalho; Type: COMMENT; Schema: negocio; Owner: outline
--

COMMENT ON TABLE tb_pacote_trabalho IS 'Tabela de Produto';


--
-- TOC entry 172 (class 1259 OID 16478)
-- Dependencies: 7 173
-- Name: tb_pacote_trabalho_icodigo_seq; Type: SEQUENCE; Schema: negocio; Owner: outline
--

CREATE SEQUENCE tb_pacote_trabalho_icodigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE negocio.tb_pacote_trabalho_icodigo_seq OWNER TO outline;

--
-- TOC entry 1986 (class 0 OID 0)
-- Dependencies: 172
-- Name: tb_pacote_trabalho_icodigo_seq; Type: SEQUENCE OWNED BY; Schema: negocio; Owner: outline
--

ALTER SEQUENCE tb_pacote_trabalho_icodigo_seq OWNED BY tb_pacote_trabalho.icodigo;


--
-- TOC entry 171 (class 1259 OID 16453)
-- Dependencies: 7
-- Name: tb_produto; Type: TABLE; Schema: negocio; Owner: outline; Tablespace: 
--

CREATE TABLE tb_produto (
    icodigo integer NOT NULL,
    snome character varying(80) NOT NULL,
    sdescricao text,
    ifase integer NOT NULL
);


ALTER TABLE negocio.tb_produto OWNER TO outline;

--
-- TOC entry 1987 (class 0 OID 0)
-- Dependencies: 171
-- Name: TABLE tb_produto; Type: COMMENT; Schema: negocio; Owner: outline
--

COMMENT ON TABLE tb_produto IS 'Tabela de Produto';


--
-- TOC entry 170 (class 1259 OID 16451)
-- Dependencies: 171 7
-- Name: tb_produto_icodigo_seq; Type: SEQUENCE; Schema: negocio; Owner: outline
--

CREATE SEQUENCE tb_produto_icodigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE negocio.tb_produto_icodigo_seq OWNER TO outline;

--
-- TOC entry 1988 (class 0 OID 0)
-- Dependencies: 170
-- Name: tb_produto_icodigo_seq; Type: SEQUENCE OWNED BY; Schema: negocio; Owner: outline
--

ALTER SEQUENCE tb_produto_icodigo_seq OWNED BY tb_produto.icodigo;


--
-- TOC entry 165 (class 1259 OID 16399)
-- Dependencies: 7
-- Name: tb_projeto; Type: TABLE; Schema: negocio; Owner: outline; Tablespace: 
--

CREATE TABLE tb_projeto (
    icodigo integer NOT NULL,
    snome character varying(80) NOT NULL,
    sdescricao text
);


ALTER TABLE negocio.tb_projeto OWNER TO outline;

--
-- TOC entry 1989 (class 0 OID 0)
-- Dependencies: 165
-- Name: TABLE tb_projeto; Type: COMMENT; Schema: negocio; Owner: outline
--

COMMENT ON TABLE tb_projeto IS 'Tabela de Projeto';


--
-- TOC entry 164 (class 1259 OID 16397)
-- Dependencies: 165 7
-- Name: tb_projeto_icodigo_seq; Type: SEQUENCE; Schema: negocio; Owner: outline
--

CREATE SEQUENCE tb_projeto_icodigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE negocio.tb_projeto_icodigo_seq OWNER TO outline;

--
-- TOC entry 1990 (class 0 OID 0)
-- Dependencies: 164
-- Name: tb_projeto_icodigo_seq; Type: SEQUENCE OWNED BY; Schema: negocio; Owner: outline
--

ALTER SEQUENCE tb_projeto_icodigo_seq OWNED BY tb_projeto.icodigo;


--
-- TOC entry 179 (class 1259 OID 16532)
-- Dependencies: 7
-- Name: tb_recurso; Type: TABLE; Schema: negocio; Owner: outline; Tablespace: 
--

CREATE TABLE tb_recurso (
    icodigo integer NOT NULL,
    itiporecurso integer NOT NULL,
    iusuario integer NOT NULL,
    ipacotetrabalho integer NOT NULL
);


ALTER TABLE negocio.tb_recurso OWNER TO outline;

--
-- TOC entry 1991 (class 0 OID 0)
-- Dependencies: 179
-- Name: TABLE tb_recurso; Type: COMMENT; Schema: negocio; Owner: outline
--

COMMENT ON TABLE tb_recurso IS 'Tabela de Recurso';


--
-- TOC entry 178 (class 1259 OID 16530)
-- Dependencies: 179 7
-- Name: tb_recurso_icodigo_seq; Type: SEQUENCE; Schema: negocio; Owner: outline
--

CREATE SEQUENCE tb_recurso_icodigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE negocio.tb_recurso_icodigo_seq OWNER TO outline;

--
-- TOC entry 1992 (class 0 OID 0)
-- Dependencies: 178
-- Name: tb_recurso_icodigo_seq; Type: SEQUENCE OWNED BY; Schema: negocio; Owner: outline
--

ALTER SEQUENCE tb_recurso_icodigo_seq OWNED BY tb_recurso.icodigo;


--
-- TOC entry 163 (class 1259 OID 16389)
-- Dependencies: 7
-- Name: tb_tarefa; Type: TABLE; Schema: negocio; Owner: outline; Tablespace: 
--

CREATE TABLE tb_tarefa (
    icodigo integer NOT NULL,
    snome character varying(80) NOT NULL,
    duracao integer NOT NULL,
    data_inicio timestamp without time zone NOT NULL,
    data_termino timestamp without time zone,
    data_entrega timestamp without time zone,
    idwintarefa character varying(50) NOT NULL,
    ipacotetrabalho integer NOT NULL
);


ALTER TABLE negocio.tb_tarefa OWNER TO outline;

--
-- TOC entry 1993 (class 0 OID 0)
-- Dependencies: 163
-- Name: TABLE tb_tarefa; Type: COMMENT; Schema: negocio; Owner: outline
--

COMMENT ON TABLE tb_tarefa IS 'Tabela da Tarefa';


--
-- TOC entry 1994 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN tb_tarefa.snome; Type: COMMENT; Schema: negocio; Owner: outline
--

COMMENT ON COLUMN tb_tarefa.snome IS 'campo nome';


--
-- TOC entry 162 (class 1259 OID 16387)
-- Dependencies: 7 163
-- Name: tb_tarefa_icodigo_seq; Type: SEQUENCE; Schema: negocio; Owner: outline
--

CREATE SEQUENCE tb_tarefa_icodigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE negocio.tb_tarefa_icodigo_seq OWNER TO outline;

--
-- TOC entry 1995 (class 0 OID 0)
-- Dependencies: 162
-- Name: tb_tarefa_icodigo_seq; Type: SEQUENCE OWNED BY; Schema: negocio; Owner: outline
--

ALTER SEQUENCE tb_tarefa_icodigo_seq OWNED BY tb_tarefa.icodigo;


--
-- TOC entry 177 (class 1259 OID 16524)
-- Dependencies: 7
-- Name: tb_tipo_recurso; Type: TABLE; Schema: negocio; Owner: outline; Tablespace: 
--

CREATE TABLE tb_tipo_recurso (
    icodigo integer NOT NULL,
    sdescricao character varying(100) NOT NULL,
    cativo bit(1) NOT NULL
);


ALTER TABLE negocio.tb_tipo_recurso OWNER TO outline;

--
-- TOC entry 1996 (class 0 OID 0)
-- Dependencies: 177
-- Name: TABLE tb_tipo_recurso; Type: COMMENT; Schema: negocio; Owner: outline
--

COMMENT ON TABLE tb_tipo_recurso IS 'Tabela de Tipo de Recurso';


--
-- TOC entry 176 (class 1259 OID 16522)
-- Dependencies: 177 7
-- Name: tb_tipo_recurso_icodigo_seq; Type: SEQUENCE; Schema: negocio; Owner: outline
--

CREATE SEQUENCE tb_tipo_recurso_icodigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE negocio.tb_tipo_recurso_icodigo_seq OWNER TO outline;

--
-- TOC entry 1997 (class 0 OID 0)
-- Dependencies: 176
-- Name: tb_tipo_recurso_icodigo_seq; Type: SEQUENCE OWNED BY; Schema: negocio; Owner: outline
--

ALTER SEQUENCE tb_tipo_recurso_icodigo_seq OWNED BY tb_tipo_recurso.icodigo;


--
-- TOC entry 175 (class 1259 OID 16502)
-- Dependencies: 7
-- Name: tb_usuario; Type: TABLE; Schema: negocio; Owner: outline; Tablespace: 
--

CREATE TABLE tb_usuario (
    icodigo integer NOT NULL,
    snome character varying(100) NOT NULL,
    susername character varying(20) NOT NULL,
    semail character varying(120) NOT NULL,
    spassword character varying(128) NOT NULL
);


ALTER TABLE negocio.tb_usuario OWNER TO outline;

--
-- TOC entry 1998 (class 0 OID 0)
-- Dependencies: 175
-- Name: TABLE tb_usuario; Type: COMMENT; Schema: negocio; Owner: outline
--

COMMENT ON TABLE tb_usuario IS 'Tabela de Usuário';


--
-- TOC entry 174 (class 1259 OID 16500)
-- Dependencies: 7 175
-- Name: tb_usuario_icodigo_seq; Type: SEQUENCE; Schema: negocio; Owner: outline
--

CREATE SEQUENCE tb_usuario_icodigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE negocio.tb_usuario_icodigo_seq OWNER TO outline;

--
-- TOC entry 1999 (class 0 OID 0)
-- Dependencies: 174
-- Name: tb_usuario_icodigo_seq; Type: SEQUENCE OWNED BY; Schema: negocio; Owner: outline
--

ALTER SEQUENCE tb_usuario_icodigo_seq OWNED BY tb_usuario.icodigo;


--
-- TOC entry 1920 (class 2604 OID 16558)
-- Dependencies: 180 181 181
-- Name: icodigo; Type: DEFAULT; Schema: negocio; Owner: outline
--

ALTER TABLE ONLY tb_calendario ALTER COLUMN icodigo SET DEFAULT nextval('tb_calendario_icodigo_seq'::regclass);


--
-- TOC entry 1913 (class 2604 OID 16424)
-- Dependencies: 167 166 167
-- Name: icodigo; Type: DEFAULT; Schema: negocio; Owner: outline
--

ALTER TABLE ONLY tb_eap ALTER COLUMN icodigo SET DEFAULT nextval('tb_eap_icodigo_seq'::regclass);


--
-- TOC entry 1914 (class 2604 OID 16440)
-- Dependencies: 168 169 169
-- Name: icodigo; Type: DEFAULT; Schema: negocio; Owner: outline
--

ALTER TABLE ONLY tb_fase ALTER COLUMN icodigo SET DEFAULT nextval('tb_fase_icodigo_seq'::regclass);


--
-- TOC entry 1916 (class 2604 OID 16483)
-- Dependencies: 173 172 173
-- Name: icodigo; Type: DEFAULT; Schema: negocio; Owner: outline
--

ALTER TABLE ONLY tb_pacote_trabalho ALTER COLUMN icodigo SET DEFAULT nextval('tb_pacote_trabalho_icodigo_seq'::regclass);


--
-- TOC entry 1915 (class 2604 OID 16456)
-- Dependencies: 170 171 171
-- Name: icodigo; Type: DEFAULT; Schema: negocio; Owner: outline
--

ALTER TABLE ONLY tb_produto ALTER COLUMN icodigo SET DEFAULT nextval('tb_produto_icodigo_seq'::regclass);


--
-- TOC entry 1912 (class 2604 OID 16402)
-- Dependencies: 164 165 165
-- Name: icodigo; Type: DEFAULT; Schema: negocio; Owner: outline
--

ALTER TABLE ONLY tb_projeto ALTER COLUMN icodigo SET DEFAULT nextval('tb_projeto_icodigo_seq'::regclass);


--
-- TOC entry 1919 (class 2604 OID 16535)
-- Dependencies: 178 179 179
-- Name: icodigo; Type: DEFAULT; Schema: negocio; Owner: outline
--

ALTER TABLE ONLY tb_recurso ALTER COLUMN icodigo SET DEFAULT nextval('tb_recurso_icodigo_seq'::regclass);


--
-- TOC entry 1911 (class 2604 OID 16392)
-- Dependencies: 163 162 163
-- Name: icodigo; Type: DEFAULT; Schema: negocio; Owner: outline
--

ALTER TABLE ONLY tb_tarefa ALTER COLUMN icodigo SET DEFAULT nextval('tb_tarefa_icodigo_seq'::regclass);


--
-- TOC entry 1918 (class 2604 OID 16527)
-- Dependencies: 176 177 177
-- Name: icodigo; Type: DEFAULT; Schema: negocio; Owner: outline
--

ALTER TABLE ONLY tb_tipo_recurso ALTER COLUMN icodigo SET DEFAULT nextval('tb_tipo_recurso_icodigo_seq'::regclass);


--
-- TOC entry 1917 (class 2604 OID 16505)
-- Dependencies: 175 174 175
-- Name: icodigo; Type: DEFAULT; Schema: negocio; Owner: outline
--

ALTER TABLE ONLY tb_usuario ALTER COLUMN icodigo SET DEFAULT nextval('tb_usuario_icodigo_seq'::regclass);


--
-- TOC entry 1969 (class 0 OID 16555)
-- Dependencies: 181 1970
-- Data for Name: tb_calendario; Type: TABLE DATA; Schema: negocio; Owner: outline
--



--
-- TOC entry 2000 (class 0 OID 0)
-- Dependencies: 180
-- Name: tb_calendario_icodigo_seq; Type: SEQUENCE SET; Schema: negocio; Owner: outline
--

SELECT pg_catalog.setval('tb_calendario_icodigo_seq', 1, false);


--
-- TOC entry 1955 (class 0 OID 16421)
-- Dependencies: 167 1970
-- Data for Name: tb_eap; Type: TABLE DATA; Schema: negocio; Owner: outline
--

INSERT INTO tb_eap (icodigo, snome, sdescricao, iprojeto) VALUES (1, 'EAP Projeto Teste', 'Teste', 1);


--
-- TOC entry 2001 (class 0 OID 0)
-- Dependencies: 166
-- Name: tb_eap_icodigo_seq; Type: SEQUENCE SET; Schema: negocio; Owner: outline
--

SELECT pg_catalog.setval('tb_eap_icodigo_seq', 1, true);


--
-- TOC entry 1957 (class 0 OID 16437)
-- Dependencies: 169 1970
-- Data for Name: tb_fase; Type: TABLE DATA; Schema: negocio; Owner: outline
--

INSERT INTO tb_fase (icodigo, snome, sdescricao, ieap) VALUES (1, 'FASE EAP Teste', 'Teste', 1);


--
-- TOC entry 2002 (class 0 OID 0)
-- Dependencies: 168
-- Name: tb_fase_icodigo_seq; Type: SEQUENCE SET; Schema: negocio; Owner: outline
--

SELECT pg_catalog.setval('tb_fase_icodigo_seq', 1, true);


--
-- TOC entry 1961 (class 0 OID 16480)
-- Dependencies: 173 1970
-- Data for Name: tb_pacote_trabalho; Type: TABLE DATA; Schema: negocio; Owner: outline
--

INSERT INTO tb_pacote_trabalho (icodigo, snome, sdescricao, iproduto) VALUES (1, 'Pacote Trabalho Teste', 'Teste', 1);


--
-- TOC entry 2003 (class 0 OID 0)
-- Dependencies: 172
-- Name: tb_pacote_trabalho_icodigo_seq; Type: SEQUENCE SET; Schema: negocio; Owner: outline
--

SELECT pg_catalog.setval('tb_pacote_trabalho_icodigo_seq', 1, true);


--
-- TOC entry 1959 (class 0 OID 16453)
-- Dependencies: 171 1970
-- Data for Name: tb_produto; Type: TABLE DATA; Schema: negocio; Owner: outline
--

INSERT INTO tb_produto (icodigo, snome, sdescricao, ifase) VALUES (1, 'Produto Fase Teste', 'Teste', 1);


--
-- TOC entry 2004 (class 0 OID 0)
-- Dependencies: 170
-- Name: tb_produto_icodigo_seq; Type: SEQUENCE SET; Schema: negocio; Owner: outline
--

SELECT pg_catalog.setval('tb_produto_icodigo_seq', 1, true);


--
-- TOC entry 1953 (class 0 OID 16399)
-- Dependencies: 165 1970
-- Data for Name: tb_projeto; Type: TABLE DATA; Schema: negocio; Owner: outline
--

INSERT INTO tb_projeto (icodigo, snome, sdescricao) VALUES (1, 'Projeto Teste', 'Teste');


--
-- TOC entry 2005 (class 0 OID 0)
-- Dependencies: 164
-- Name: tb_projeto_icodigo_seq; Type: SEQUENCE SET; Schema: negocio; Owner: outline
--

SELECT pg_catalog.setval('tb_projeto_icodigo_seq', 1, true);


--
-- TOC entry 1967 (class 0 OID 16532)
-- Dependencies: 179 1970
-- Data for Name: tb_recurso; Type: TABLE DATA; Schema: negocio; Owner: outline
--

INSERT INTO tb_recurso (icodigo, itiporecurso, iusuario, ipacotetrabalho) VALUES (1, 1, 2, 1);


--
-- TOC entry 2006 (class 0 OID 0)
-- Dependencies: 178
-- Name: tb_recurso_icodigo_seq; Type: SEQUENCE SET; Schema: negocio; Owner: outline
--

SELECT pg_catalog.setval('tb_recurso_icodigo_seq', 1, true);


--
-- TOC entry 1951 (class 0 OID 16389)
-- Dependencies: 163 1970
-- Data for Name: tb_tarefa; Type: TABLE DATA; Schema: negocio; Owner: outline
--

INSERT INTO tb_tarefa (icodigo, snome, duracao, data_inicio, data_termino, data_entrega, idwintarefa, ipacotetrabalho) VALUES (4, 'Tarefa teste 1', 3, '2013-01-10 13:40:19.885', NULL, '2013-01-12 13:40:19.885', 'window_1001', 1);
INSERT INTO tb_tarefa (icodigo, snome, duracao, data_inicio, data_termino, data_entrega, idwintarefa, ipacotetrabalho) VALUES (5, 'Tarefa teste 1', 3, '2013-01-10 20:38:27.799', NULL, '2013-01-12 20:38:27.799', 'window_1001', 1);
INSERT INTO tb_tarefa (icodigo, snome, duracao, data_inicio, data_termino, data_entrega, idwintarefa, ipacotetrabalho) VALUES (6, 'Tarefa teste 2', 3, '2013-01-10 20:39:31.665', NULL, '2013-01-12 20:39:31.665', 'window_1002', 1);
INSERT INTO tb_tarefa (icodigo, snome, duracao, data_inicio, data_termino, data_entrega, idwintarefa, ipacotetrabalho) VALUES (7, 'Tarefa teste 1', 3, '2013-01-10 20:49:49.7', NULL, '2013-01-12 20:49:49.699', 'window_1001', 1);
INSERT INTO tb_tarefa (icodigo, snome, duracao, data_inicio, data_termino, data_entrega, idwintarefa, ipacotetrabalho) VALUES (8, 'Tarefa teste 1', 3, '2013-01-10 20:49:51.544', NULL, '2013-01-12 20:49:51.543', 'window_1001', 1);
INSERT INTO tb_tarefa (icodigo, snome, duracao, data_inicio, data_termino, data_entrega, idwintarefa, ipacotetrabalho) VALUES (9, 'Tarefa teste 1', 3, '2013-01-10 20:50:19.857', NULL, '2013-01-12 20:50:19.856', 'window_1002', 1);
INSERT INTO tb_tarefa (icodigo, snome, duracao, data_inicio, data_termino, data_entrega, idwintarefa, ipacotetrabalho) VALUES (10, 'Tarefa teste 20', 3, '2013-01-10 08:55:12.89', NULL, '2013-01-12 08:55:12.889', 'window_1001', 1);
INSERT INTO tb_tarefa (icodigo, snome, duracao, data_inicio, data_termino, data_entrega, idwintarefa, ipacotetrabalho) VALUES (11, 'Tarefa teste 21', 3, '2013-01-10 08:59:25.542', NULL, '2013-01-12 08:59:25.542', 'window_1001', 1);


--
-- TOC entry 2007 (class 0 OID 0)
-- Dependencies: 162
-- Name: tb_tarefa_icodigo_seq; Type: SEQUENCE SET; Schema: negocio; Owner: outline
--

SELECT pg_catalog.setval('tb_tarefa_icodigo_seq', 11, true);


--
-- TOC entry 1965 (class 0 OID 16524)
-- Dependencies: 177 1970
-- Data for Name: tb_tipo_recurso; Type: TABLE DATA; Schema: negocio; Owner: outline
--

INSERT INTO tb_tipo_recurso (icodigo, sdescricao, cativo) VALUES (1, 'Recurso', B'1');


--
-- TOC entry 2008 (class 0 OID 0)
-- Dependencies: 176
-- Name: tb_tipo_recurso_icodigo_seq; Type: SEQUENCE SET; Schema: negocio; Owner: outline
--

SELECT pg_catalog.setval('tb_tipo_recurso_icodigo_seq', 1, true);


--
-- TOC entry 1963 (class 0 OID 16502)
-- Dependencies: 175 1970
-- Data for Name: tb_usuario; Type: TABLE DATA; Schema: negocio; Owner: outline
--

INSERT INTO tb_usuario (icodigo, snome, susername, semail, spassword) VALUES (2, 'Alessandro T. Santos', 'alessandrots', 'alessandro.teixeira@gmail.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92');


--
-- TOC entry 2009 (class 0 OID 0)
-- Dependencies: 174
-- Name: tb_usuario_icodigo_seq; Type: SEQUENCE SET; Schema: negocio; Owner: outline
--

SELECT pg_catalog.setval('tb_usuario_icodigo_seq', 2, true);


--
-- TOC entry 1940 (class 2606 OID 16560)
-- Dependencies: 181 181 1971
-- Name: icodigo_tb_calendario_pk; Type: CONSTRAINT; Schema: negocio; Owner: outline; Tablespace: 
--

ALTER TABLE ONLY tb_calendario
    ADD CONSTRAINT icodigo_tb_calendario_pk PRIMARY KEY (icodigo);


--
-- TOC entry 1926 (class 2606 OID 16429)
-- Dependencies: 167 167 1971
-- Name: icodigo_tb_eap_pk; Type: CONSTRAINT; Schema: negocio; Owner: outline; Tablespace: 
--

ALTER TABLE ONLY tb_eap
    ADD CONSTRAINT icodigo_tb_eap_pk PRIMARY KEY (icodigo);


--
-- TOC entry 1928 (class 2606 OID 16445)
-- Dependencies: 169 169 1971
-- Name: icodigo_tb_fase_pk; Type: CONSTRAINT; Schema: negocio; Owner: outline; Tablespace: 
--

ALTER TABLE ONLY tb_fase
    ADD CONSTRAINT icodigo_tb_fase_pk PRIMARY KEY (icodigo);


--
-- TOC entry 1932 (class 2606 OID 16488)
-- Dependencies: 173 173 1971
-- Name: icodigo_tb_pacote_trabalho_pk; Type: CONSTRAINT; Schema: negocio; Owner: outline; Tablespace: 
--

ALTER TABLE ONLY tb_pacote_trabalho
    ADD CONSTRAINT icodigo_tb_pacote_trabalho_pk PRIMARY KEY (icodigo);


--
-- TOC entry 1930 (class 2606 OID 16461)
-- Dependencies: 171 171 1971
-- Name: icodigo_tb_produto_pk; Type: CONSTRAINT; Schema: negocio; Owner: outline; Tablespace: 
--

ALTER TABLE ONLY tb_produto
    ADD CONSTRAINT icodigo_tb_produto_pk PRIMARY KEY (icodigo);


--
-- TOC entry 1924 (class 2606 OID 16407)
-- Dependencies: 165 165 1971
-- Name: icodigo_tb_projeto_pk; Type: CONSTRAINT; Schema: negocio; Owner: outline; Tablespace: 
--

ALTER TABLE ONLY tb_projeto
    ADD CONSTRAINT icodigo_tb_projeto_pk PRIMARY KEY (icodigo);


--
-- TOC entry 1938 (class 2606 OID 16537)
-- Dependencies: 179 179 1971
-- Name: icodigo_tb_recurso_pk; Type: CONSTRAINT; Schema: negocio; Owner: outline; Tablespace: 
--

ALTER TABLE ONLY tb_recurso
    ADD CONSTRAINT icodigo_tb_recurso_pk PRIMARY KEY (icodigo);


--
-- TOC entry 1922 (class 2606 OID 16394)
-- Dependencies: 163 163 1971
-- Name: icodigo_tb_tarefa_pk; Type: CONSTRAINT; Schema: negocio; Owner: outline; Tablespace: 
--

ALTER TABLE ONLY tb_tarefa
    ADD CONSTRAINT icodigo_tb_tarefa_pk PRIMARY KEY (icodigo);


--
-- TOC entry 1936 (class 2606 OID 16529)
-- Dependencies: 177 177 1971
-- Name: icodigo_tb_tipo_recurso_pk; Type: CONSTRAINT; Schema: negocio; Owner: outline; Tablespace: 
--

ALTER TABLE ONLY tb_tipo_recurso
    ADD CONSTRAINT icodigo_tb_tipo_recurso_pk PRIMARY KEY (icodigo);


--
-- TOC entry 1934 (class 2606 OID 16507)
-- Dependencies: 175 175 1971
-- Name: icodigo_tb_usuario_pk; Type: CONSTRAINT; Schema: negocio; Owner: outline; Tablespace: 
--

ALTER TABLE ONLY tb_usuario
    ADD CONSTRAINT icodigo_tb_usuario_pk PRIMARY KEY (icodigo);


--
-- TOC entry 1943 (class 2606 OID 16446)
-- Dependencies: 169 167 1925 1971
-- Name: fk_negocio_eap_fase; Type: FK CONSTRAINT; Schema: negocio; Owner: outline
--

ALTER TABLE ONLY tb_fase
    ADD CONSTRAINT fk_negocio_eap_fase FOREIGN KEY (ieap) REFERENCES tb_eap(icodigo);


--
-- TOC entry 1941 (class 2606 OID 16495)
-- Dependencies: 173 163 1931 1971
-- Name: fk_negocio_pacote_trab_tb_tarefa; Type: FK CONSTRAINT; Schema: negocio; Owner: outline
--

ALTER TABLE ONLY tb_tarefa
    ADD CONSTRAINT fk_negocio_pacote_trab_tb_tarefa FOREIGN KEY (ipacotetrabalho) REFERENCES tb_pacote_trabalho(icodigo);


--
-- TOC entry 1942 (class 2606 OID 16430)
-- Dependencies: 165 1923 167 1971
-- Name: fk_negocio_projeto_eap; Type: FK CONSTRAINT; Schema: negocio; Owner: outline
--

ALTER TABLE ONLY tb_eap
    ADD CONSTRAINT fk_negocio_projeto_eap FOREIGN KEY (iprojeto) REFERENCES tb_projeto(icodigo);


--
-- TOC entry 1949 (class 2606 OID 16561)
-- Dependencies: 1937 181 179 1971
-- Name: fk_negocio_tb_calendario_tb_recurso; Type: FK CONSTRAINT; Schema: negocio; Owner: outline
--

ALTER TABLE ONLY tb_calendario
    ADD CONSTRAINT fk_negocio_tb_calendario_tb_recurso FOREIGN KEY (irecurso) REFERENCES tb_recurso(icodigo);


--
-- TOC entry 1945 (class 2606 OID 16489)
-- Dependencies: 1929 173 171 1971
-- Name: fk_negocio_tb_pacote_trabalho; Type: FK CONSTRAINT; Schema: negocio; Owner: outline
--

ALTER TABLE ONLY tb_pacote_trabalho
    ADD CONSTRAINT fk_negocio_tb_pacote_trabalho FOREIGN KEY (iproduto) REFERENCES tb_produto(icodigo);


--
-- TOC entry 1944 (class 2606 OID 16462)
-- Dependencies: 171 1925 167 1971
-- Name: fk_negocio_tb_produto; Type: FK CONSTRAINT; Schema: negocio; Owner: outline
--

ALTER TABLE ONLY tb_produto
    ADD CONSTRAINT fk_negocio_tb_produto FOREIGN KEY (ifase) REFERENCES tb_eap(icodigo);


--
-- TOC entry 1948 (class 2606 OID 16548)
-- Dependencies: 1931 179 173 1971
-- Name: fk_negocio_tb_recurso_tb_pacote_trabalho; Type: FK CONSTRAINT; Schema: negocio; Owner: outline
--

ALTER TABLE ONLY tb_recurso
    ADD CONSTRAINT fk_negocio_tb_recurso_tb_pacote_trabalho FOREIGN KEY (ipacotetrabalho) REFERENCES tb_pacote_trabalho(icodigo);


--
-- TOC entry 1947 (class 2606 OID 16543)
-- Dependencies: 179 1935 177 1971
-- Name: fk_negocio_tb_recurso_tb_tiporecurso; Type: FK CONSTRAINT; Schema: negocio; Owner: outline
--

ALTER TABLE ONLY tb_recurso
    ADD CONSTRAINT fk_negocio_tb_recurso_tb_tiporecurso FOREIGN KEY (itiporecurso) REFERENCES tb_tipo_recurso(icodigo);


--
-- TOC entry 1946 (class 2606 OID 16538)
-- Dependencies: 179 1933 175 1971
-- Name: fk_negocio_tb_recurso_tb_usuario; Type: FK CONSTRAINT; Schema: negocio; Owner: outline
--

ALTER TABLE ONLY tb_recurso
    ADD CONSTRAINT fk_negocio_tb_recurso_tb_usuario FOREIGN KEY (iusuario) REFERENCES tb_usuario(icodigo);


--
-- TOC entry 1977 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2013-06-10 23:28:37 BRT

--
-- PostgreSQL database dump complete
--

