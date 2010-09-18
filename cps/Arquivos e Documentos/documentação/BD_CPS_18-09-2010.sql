---------------------------------------------------------------------------------
--TABLESPACE BRASIL
---------------------------------------------------------------------------------

CREATE TABLESPACE "BRASIL"
  OWNER POSTGRES
  LOCATION 'C:/BD_CPS/BRASIL';

---------------------------------------------------------------------------------
--DATABASE CPS
---------------------------------------------------------------------------------

CREATE DATABASE CPS 
TABLESPACE "BRASIL";

---------------------------------------------------------------------------------
--PROCEDURAL LANGUAGE PLPGSQL
---------------------------------------------------------------------------------

/*CREATE PROCEDURAL LANGUAGE 'PLPGSQL'
	HANDLER PLPGSQL_CALL_HANDLER
	VALIDATOR PLPGSQL_VALIDATOR;
*/

---------------------------------------------------------------------------------
--ROLE LANGUAGE USUARIO
---------------------------------------------------------------------------------

/*CREATE ROLE "USUARIO"
  NOSUPERUSER NOINHERIT NOCREATEDB NOCREATEROLE;

CREATE ROLE "USUARIOS" LOGIN
  NOSUPERUSER NOINHERIT NOCREATEDB NOCREATEROLE;
*/

---------------------------------------------------------------------------------
--SCHEMA TABELAS 
---------------------------------------------------------------------------------

CREATE SCHEMA TABELAS;

---------------------------------------------------------------------------------
--TABLE TABELAS.LOCALIDADE 
---------------------------------------------------------------------------------

--DROP TABLE TABELAS.LOCALIDADE
CREATE TABLE TABELAS.LOCALIDADE
(
	ID_LOCALIDADE CHAR (6) NOT NULL,
	LOCALIDADE VARCHAR (60) NOT NULL,
	CEP_LOCALIDADE CHAR (9) NOT NULL,
	ID_SUBLOCALIDADE CHAR (6) NOT NULL,
	UF CHAR (2) NOT NULL,
		CONSTRAINT PK_LOCALIDADE PRIMARY KEY (ID_LOCALIDADE)
)WITH (OIDS=FALSE)
TABLESPACE "BRASIL";

---------------------------------------------------------------------------------
--TABLE TABELAS.BAIRRO 
---------------------------------------------------------------------------------

--DROP TABLE TABELAS.BAIRRO
CREATE TABLE TABELAS.BAIRRO
(
	UF CHAR (2) NOT NULL,
	ID_LOCALIDADE CHAR (6) NOT NULL,
	ID_BAIRRO CHAR (6) NOT NULL,
	BAIRRO VARCHAR (60) NOT NULL,
	ABREVBAIRRO VARCHAR (30),
		CONSTRAINT PK_BAIRROS PRIMARY KEY (UF, ID_LOCALIDADE, ID_BAIRRO),
		CONSTRAINT FK_BAIRRO_LOCALIDADE FOREIGN KEY (ID_LOCALIDADE) REFERENCES TABELAS.LOCALIDADE
)WITH (OIDS=FALSE)
TABLESPACE "BRASIL";

---------------------------------------------------------------------------------
--TABLE TABELAS.CEP 
---------------------------------------------------------------------------------

--DROP TABLE TABELAS.CEP
CREATE TABLE TABELAS.CEP
(
	CEP CHAR (9) NOT NULL ,
	UF CHAR (2) NOT NULL ,
	ID_LOCALIDADE CHAR (6) NOT NULL ,
	ID_TIPOLOGRADOURO CHAR (4) NOT NULL ,
	LOGRADOURO VARCHAR (60) NOT NULL ,
	BAIRRO1 VARCHAR (6) NOT NULL ,
	BAIRRO2 VARCHAR (6) NOT NULL ,
	COMPLEMENTO VARCHAR (65) NOT NULL,
		 CONSTRAINT PK_CEPS PRIMARY KEY (CEP),
		 CONSTRAINT FK_CEP_LOCALIDADE FOREIGN KEY (ID_LOCALIDADE)REFERENCES TABELAS.LOCALIDADE 

)WITH (OIDS=FALSE)
TABLESPACE "BRASIL";

---------------------------------------------------------------------------------
--TABLE TABELAS.REDE 
---------------------------------------------------------------------------------

--DROP TABLE TABELAS.REDE
CREATE TABLE TABELAS.REDE
(
	ID_REDE INT NOT NULL,
	NOME VARCHAR (30) NOT NULL,
	DATAULTIMAMODIFICACAO DATE NOT NULL DEFAULT (CURRENT_DATE),
		CONSTRAINT PK_REDE PRIMARY KEY (ID_REDE),
		CONSTRAINT UQ_NOME UNIQUE (NOME)
	
)WITH (OIDS=FALSE)
TABLESPACE "BRASIL";

---------------------------------------------------------------------------------
--TABLE TABELAS.LOJA 
---------------------------------------------------------------------------------

--DROP TABLE TABELAS.LOJA
CREATE TABLE TABELAS.LOJA
(
        ID_LOJA INT NOT NULL,
        ID_REDE INT NOT NULL,
	RAZAOSOCIAL VARCHAR (60) NOT NULL,
	NOMEFANTASIA VARCHAR (60) NOT NULL,
	CNPJ VARCHAR (20) NOT NULL,
	INSCRICAOESTADUAL VARCHAR (20),--JUNTA COMERCIAL É ONDE A EMPRESA REGISTRA O TIPO DE EMPRESA QUE ELA SERÁ VEM COMO AS CLÁUSULAS DE CONSTITUIÇÃO DA MESMA, ONDE REGISTRAS-E O NOME DA EMPRESA RAZÃO SOCIAL E O NOME FANTASIA COM A QUAL ELA ATUARÁ NO MERCADO DEIXANDO ELA COMO SENDO ÚNICA. INSCRIÇÃO ESTADUAL É UM NUMERO QUE A EMPRESA TERÁ PARA EFETUAR OS RECOLHIMENTO DE IMPOSTOS DO ESTADO TIPO ICMS, A INSCRIÇÃO MUNICIPAL NORMALMENTE É FEITA JUNTO À PREFEITURA DO MUNICÍPIO PARA EFEITOS DE TRIBUTAÇÃO NA ÁREA DE PRESTAÇÃO DE SERVIÇOS BEM COMO NAS RELAÇÕES DE IMPOSTOS ASSOCIADOS AO IMÓVEL, PROPAGANDA. VOCÊ ESQUECEU A PRINCIPAL QUE É O CADASTRO DA RECEITA FEDERAL, ONDE A EMPRESA GANHARA UM NUMERO CHAMADO CNPJ ONDE ATRAVÉS DELA ERA FARÁ O RECOLHIMENTO DO IMPOSTO DE RENDA PESSOA JURÍDICA.
	INSCRICAOMUNICIPAL VARCHAR (20),
	NOMEDORESPONSAVELPELALOJA VARCHAR (30),
	TIPODEVENDA CHAR (1) NOT NULL DEFAULT (1),-- COMO O CONVERSADO COM O PROFESSOR MENESES, VOLTAMOS A HABILITAR ESTE CAMPO
	PAIS VARCHAR (50) NOT NULL DEFAULT ('BRASIL'),
	ESTADO CHAR (2) NOT NULL,
	CIDADE VARCHAR (50) NOT NULL,
	BAIRRO VARCHAR (50) NOT NULL,
	LOGRADOURO VARCHAR (50) NOT NULL,
	NUMERO CHAR (6) NOT NULL,
	COMPLEMENTO VARCHAR (20),
	CEP CHAR (9) NOT NULL,
	TEL_COM CHAR (13), -- TELEFONE 0800
	DDD_CEL_COM CHAR(3),
	TEL_CEL_COM CHAR(8),
	DDD_FAX CHAR(3),
	TEL_FAX CHAR(8),
	EMAIL VARCHAR (30),
	HOMEPAGE VARCHAR (100),
	DATAULTIMAMODIFICACAO DATE NOT NULL DEFAULT (CURRENT_DATE),
		CONSTRAINT FK_ID_REDE FOREIGN KEY (ID_REDE) REFERENCES TABELAS.REDE
			ON UPDATE CASCADE
			ON DELETE CASCADE,
		CONSTRAINT PK_ID_LOJA PRIMARY KEY (ID_LOJA, ID_REDE),
		CONSTRAINT UQ_CNPJ UNIQUE (CNPJ),
		CONSTRAINT UQ_INSCRICAO_ESTADUAL UNIQUE (INSCRICAOESTADUAL),
		--CONSTRAINT FK_CEP_LOJA FOREIGN KEY (CEP) REFERENCES TABELAS.CEP,
		CONSTRAINT CK_TIPOVENDA CHECK (TIPODEVENDA IN ('1','2','3'))-- TIPO VENDA 1 = VAREJO, 2 ATACADO E 3 AMBOS
	
)WITH (OIDS=FALSE)
TABLESPACE "BRASIL";

---------------------------------------------------------------------------------
--TABLE TABELAS.USUARIO 
---------------------------------------------------------------------------------

--DROP TABLE TABELAS.USUARIO
CREATE TABLE TABELAS.USUARIO
(
	ID_USUARIO SERIAL NOT NULL,
	NOME_USUARIO VARCHAR (20) NOT NULL,
	SOBRENOME_USUARIO VARCHAR (30) NOT NULL,
	DATA_NASCIMENTO DATE NOT NULL,-- COLOCAMOS A DATA DE NASCIMENTO DO USUARIO
	CPF_USUARIO CHAR (11) NOT NULL,
	RG_USUARIO CHAR (15) NOT NULL,
	ORGAO_ESPEDIDOR_USU CHAR (6) NOT NULL,
	DDD_CEL CHAR (3),
        TEL_CEL CHAR (8),
	DDD_RES CHAR (3),
	TEL_RES CHAR (8),
	EMAIL VARCHAR (30) NOT NULL,
	DATAULTIMAMODIFICACAO DATE NOT NULL DEFAULT (CURRENT_DATE),
		CONSTRAINT CK_EMAIL_USU CHECK (EMAIL LIKE '%@%'),
		CONSTRAINT UQ_CPF UNIQUE (CPF_USUARIO),
		CONSTRAINT UQ_EMAIL UNIQUE (EMAIL),
		CONSTRAINT PK_ID_USUARIO PRIMARY KEY (ID_USUARIO)
)WITH (OIDS=FALSE)
TABLESPACE "BRASIL";

---------------------------------------------------------------------------------
--TABLE TABELAS.ENDERECO 
---------------------------------------------------------------------------------

--DROP TABLE TABELAS.ENDERECO
CREATE TABLE TABELAS.ENDERECO
(
	ID_ENDERECO SERIAL NOT NULL,-- AGORA O USUARIO PODE TER + DE UM ENDEREÇO CADASTRADO
	ID_USUARIO INT NOT NULL,
	PAIS VARCHAR (50) NOT NULL DEFAULT ('BRASIL'),
	ESTADO CHAR (2) NOT NULL,
	CIDADE VARCHAR (50) NOT NULL,
	BAIRRO VARCHAR (50) NOT NULL,
	LOGRADOURO VARCHAR (50) NOT NULL,
	NUMERO CHAR (6) NOT NULL, 
	COMPLEMETO VARCHAR (20),
	CEP CHAR (9) NOT NULL,
	DATAULTIMAMODIFICACAO DATE NOT NULL DEFAULT (CURRENT_DATE),
		CONSTRAINT PK_ID_ID_USUARIO PRIMARY KEY (ID_ENDERECO, ID_USUARIO),
		--CONSTRAINT FK_CEP_USUARIO FOREIGN KEY (CEP) REFERENCES TABELAS.CEP,
		CONSTRAINT FK_ID_USUARIO_ENDERECO FOREIGN KEY (ID_USUARIO) REFERENCES TABELAS.USUARIO
				ON UPDATE CASCADE
				ON DELETE CASCADE
)WITH (OIDS=FALSE)
TABLESPACE "BRASIL";

---------------------------------------------------------------------------------
--TABLE TABELAS.LOGIN 
---------------------------------------------------------------------------------

--DROP TABLE TABELAS.LOGIN
CREATE TABLE TABELAS.LOGIN
(
	ID_LOGIN INT NOT NULL,
	NOME_LOGIN VARCHAR (30) NOT NULL,
	SENHA CHAR (8) NOT NULL,
	TIPO_USUARIO CHAR (1) NOT NULL,
	--DATA_ULTIMO_ACESSO DATE NOT NULL,
		CONSTRAINT PK_ID_LOGIN PRIMARY KEY (ID_LOGIN),
		CONSTRAINT UQ_LOGIN UNIQUE (NOME_LOGIN),
		CONSTRAINT FK_ID_LOGIN_USUARIO FOREIGN KEY (ID_LOGIN) REFERENCES TABELAS.USUARIO
			ON UPDATE CASCADE
			ON DELETE CASCADE
)WITH (OIDS=FALSE)
TABLESPACE "BRASIL";

---------------------------------------------------------------------------------
--TABLE TABELAS.PRODUTO 
---------------------------------------------------------------------------------

--DROP TABLE TABELAS.PRODUTO
CREATE TABLE TABELAS.PRODUTO
(
	CODIGO_BARRAS CHAR (20) NOT NULL,
        ID_REDE INT NOT NULL,
	ID_LOJA INT NOT NULL,
	DESCRICAO VARCHAR (60) NOT NULL,
	UNIDADE_MEDIDA CHAR (3),
	EMBALAGEM CHAR (20),
	TIPO_PRODUTO CHAR (1) DEFAULT (1),-- SE NÃO INFORMAR NADA O PADRÃO SERÁ VAREJO
	STATUS CHAR (1) NOT NULL,
		CONSTRAINT FK_ID_LOJA_PRODUTO FOREIGN KEY (ID_LOJA, ID_REDE) REFERENCES TABELAS.LOJA
			ON UPDATE CASCADE
			ON DELETE CASCADE,
		CONSTRAINT PK_CODIGO_BARRAS PRIMARY KEY (CODIGO_BARRAS, ID_LOJA, ID_REDE),
		CONSTRAINT CK_TIPO_PRODUTO CHECK (TIPO_PRODUTO IN ('1', '2', '3')),
		CONSTRAINT CK_CHECK CHECK (STATUS IN ('1', '2'))
)WITH (OIDS=FALSE)
TABLESPACE "BRASIL";

---------------------------------------------------------------------------------
--TABLE TABELAS.PRODUTOGERAL 
---------------------------------------------------------------------------------

--DROP TABLE TABELAS.PRODUTOGERAL
CREATE TABLE TABELAS.PRODUTOGERAL
(
	CODIGO_BARRAS CHAR (20) NOT NULL,
	DESCRICAO VARCHAR (60) NOT NULL,
	UNIDADE_MEDIDA CHAR (3),
	EMBALAGEM CHAR (20),
	STATUS CHAR (1),
	POSSUI_IMAGEM CHAR (1), 
		CONSTRAINT PK_CODIGO_BARRAS_PRODUTO_GERAL PRIMARY KEY (CODIGO_BARRAS),
		CONSTRAINT CK_CHECK CHECK (STATUS IN ('1', '2'))
		
)WITH (OIDS=FALSE)
TABLESPACE "BRASIL";

---------------------------------------------------------------------------------
--TABLE TABELAS.PRECO 
---------------------------------------------------------------------------------

--DROP TABLE TABELAS.PRECO
CREATE TABLE TABELAS.PRECO
(
	ID_REDE INT NOT NULL,
	ID_LOJA INT NOT NULL,
	CODIGO_BARRAS CHAR (20) NOT NULL,
	PRECO_ATACADO float NOT NULL DEFAULT (0),-- SE NÃO TIVER NENHUM PREÇO O PADRÃO SERÁ R$ 0
	PRECO_VAREJO float NOT NULL DEFAULT (0),-- SE NÃO TIVER NENHUM PREÇO O PADRÃO SERÁ R$ 0
	PRECO_PROMOCAO float NOT NULL DEFAULT (0),-- SE NÃO TIVER NENHUM PREÇO O PADRÃO SERÁ R$ 0 
	DATAULTIMAMODIFICACAO DATE NOT NULL DEFAULT (CURRENT_DATE),
		CONSTRAINT FK_PRECO FOREIGN KEY (CODIGO_BARRAS, ID_LOJA, ID_REDE) REFERENCES TABELAS.PRODUTO (CODIGO_BARRAS, ID_LOJA, ID_REDE)
			ON UPDATE CASCADE
			ON DELETE CASCADE,
		CONSTRAINT PK_PRECO PRIMARY KEY (ID_REDE, ID_LOJA, CODIGO_BARRAS)

)WITH (OIDS=FALSE)
TABLESPACE "BRASIL";

---------------------------------------------------------------------------------
--TABLE TABELAS.LISTA_PRODUTO 
---------------------------------------------------------------------------------

--DROP TABLE TABELAS.LISTA_PRODUTO
CREATE TABLE TABELAS.LISTA_PRODUTO
(
	ID_LISTA SERIAL NOT NULL,
	ID_USUARIO INT NOT NULL,
	NOME_LISTA VARCHAR (30) NOT NULL,
	DATA_CRIACAO DATE NOT NULL DEFAULT (CURRENT_DATE),
		CONSTRAINT PK_LISTA_PRODUTO PRIMARY KEY (ID_LISTA),
		CONSTRAINT FK_LISTA_PRODUTO FOREIGN KEY (ID_USUARIO) REFERENCES TABELAS.USUARIO
			ON DELETE CASCADE
			ON UPDATE CASCADE
)WITH (OIDS=FALSE)
TABLESPACE "BRASIL";

---------------------------------------------------------------------------------
--TABLE TABELAS.LISTA_PRODUTO_ITEM 
---------------------------------------------------------------------------------

--DROP TABLE TABELAS.LISTA_PRODUTO_ITEM
CREATE TABLE TABELAS.LISTA_PRODUTO_ITEM
(
	ID_ITENS_LISTA SERIAL NOT NULL,
	ID_LISTA INT NOT NULL,
	CODIGO_BARRAS CHAR (20) NOT NULL,
	QUANTIDADE INT NOT NULL,
		CONSTRAINT PK_LISTA_PRODUTO_ITEM PRIMARY KEY (ID_ITENS_LISTA),
		CONSTRAINT FK_LISTA_PRODUTO_ITEM_PRODUTO_GERAL FOREIGN KEY (CODIGO_BARRAS) REFERENCES TABELAS.PRODUTOGERAL
			ON DELETE CASCADE
			ON UPDATE CASCADE,
		CONSTRAINT FK_LISTA_PRODUTO_ITEM_LISTA_PRODUTO FOREIGN KEY (ID_LISTA) REFERENCES TABELAS.LISTA_PRODUTO
			ON DELETE CASCADE
			ON UPDATE CASCADE
	--PRIMARY KEY (ID_ITENS_CARRINHO, ID_CARRINHO)	
)WITH (OIDS=FALSE)
TABLESPACE "BRASIL";

---------------------------------------------------------------------------------
--TABLE TABELAS.PARAMETRIZACAO_CPS 
---------------------------------------------------------------------------------

--DROP TABLE TABELAS.PARAMETRIZACAO_CPS
CREATE TABLE TABELAS.PARAMETRIZACAO_CPS
(
	ID_PARAMETRIZACAO SERIAL NOT NULL,
	NUM_MAX_ITENS_LISTA CHAR (5) NOT NULL,
	NUM_MAX_LOJAS_COMPARACAO CHAR (2) NOT NULL,
	DIRETORIO_PROCESSAMENTO_XML VARCHAR (100) NOT NULL DEFAULT ('\\CPS_XML\PROCESSAR'),
	DIRETORIO_IMAGENS_PRO_CPS VARCHAR (100) NOT NULL DEFAULT ('\\CPS_IMAGENS\PRODUTOS'),
	valormediocombustivel float,
	rendimentomediocombustivel float,
		CONSTRAINT PK_ID_PARAMETRIZACAO PRIMARY KEY (ID_PARAMETRIZACAO)
)WITH (OIDS=FALSE)
TABLESPACE "BRASIL";

---------------------------------------------------------------------------------
--TABLE TABELAS.LOCALIZACAO 
---------------------------------------------------------------------------------

--DROP TABLE TABELAS.LOCALIZACAO
CREATE TABLE TABELAS.LOCALIZACAO 
(
	Country varchar(2) NOT NULL,
	Language varchar(2) NOT NULL,
	ID bigint[20] NOT NULL,
	ISO2 varchar(6) NOT NULL,
	Region1 varchar(60) NOT NULL,
	Region2 varchar(60) NOT NULL,
	Region3 varchar(60) NOT NULL,
	Region4 varchar(60) NOT NULL,
	ZIP varchar(10) NOT NULL,
	City varchar(60) NOT NULL,
	Area1 varchar(80) NOT NULL,
	Area2 varchar(80) NOT NULL,
	Lat float NOT NULL,
	Lng float NOT NULL,
	TZ varchar(30) NOT NULL,
	UTC varchar(10) NOT NULL,
	DST varchar(1) NOT NULL,
	constraint pk_localizacao primary key (ID)
)WITH (OIDS=FALSE)
TABLESPACE "BRASIL";

---------------------------------------------------------------------------------
--FUNCTION TABELAS.FUNC_TBPRODUTO 
---------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION TABELAS.FUNC_TBPRODUTO () RETURNS TRIGGER
AS 
$$
BEGIN
INSERT INTO TABELAS.PRODUTOGERAL SELECT CODIGO_BARRAS, DESCRICAO, UNIDADE_MEDIDA, EMBALAGEM, STATUS
		FROM TABELAS.PRODUTO WHERE CODIGO_BARRAS NOT IN (SELECT CODIGO_BARRAS FROM TABELAS.PRODUTOGERAL);
RETURN NEW;
END;
$$
LANGUAGE 'PLPGSQL';

---------------------------------------------------------------------------------
--TRIGGER TR_INSERE_PRODUTOGERAL 
---------------------------------------------------------------------------------

CREATE TRIGGER TR_INSERE_PRODUTOGERAL AFTER INSERT
ON TABELAS.PRODUTO FOR EACH ROW EXECUTE PROCEDURE TABELAS.FUNC_TBPRODUTO();

---------------------------------------------------------------------------------
--view tabelas.vw_cep 
---------------------------------------------------------------------------------

create view tabelas.vw_cep
as    select c.logradouro,
	b.bairro,
	l.localidade,
	c.uf,
	c.cep
	from tabelas.cep as c,
	tabelas.bairro as b,
	tabelas.localidade as l
	where l.id_localidade = c.id_localidade
	and b.id_bairro = c.bairro1; 

---------------------------------------------------------------------------------
--FUNCTION tabelas.sp_listarcep 
---------------------------------------------------------------------------------

CREATE or replace FUNCTION tabelas.sp_listarcep(char (9)) RETURNS SETOF tabelas.vw_cep AS $$
	select * from tabelas.vw_cep where cep = $1;
$$ LANGUAGE SQL;

---------------------------------------------------------------------------------
--view tabelas.vw__listar_lojas
---------------------------------------------------------------------------------

create view tabelas.vw_listar_lojas
as    select id_loja,
	id_rede,
	nomefantasia,
	cep,
	estado
	from tabelas.loja;	

---------------------------------------------------------------------------------
--FUNCTION tabelas.sp_listar_lojas
---------------------------------------------------------------------------------

CREATE or replace FUNCTION tabelas.sp_listar_lojas(char (9)) RETURNS SETOF tabelas.vw_listar_lojas AS $$
	select * from tabelas.vw_listar_lojas where estado = $1;
$$ LANGUAGE SQL;

--select tabelas.sp_listar_lojas ('SP')

---------------------------------------------------------------------------------
--view tabelas.vw_listar_produto
---------------------------------------------------------------------------------

create view tabelas.vw_listar_produto
as    select a.id_rede,
	a.id_loja,
	a.codigo_barras,
	a.descricao,
	b.preco_varejo
	from tabelas.produto as a,
	tabelas.preco as b
		where a.id_rede = b.id_rede
		and a.id_loja = b.id_loja
		and a.codigo_barras = b.codigo_barras;

---------------------------------------------------------------------------------
--FUNCTION tabelas.sp_listar_produto
---------------------------------------------------------------------------------

CREATE or replace FUNCTION tabelas.sp_listar_produto(int, int,char (20)) RETURNS SETOF tabelas.vw_listar_produto AS $$
	select * from tabelas.vw_listar_produto where id_rede = $1 and id_loja = $2 and codigo_barras = $3;
$$ LANGUAGE SQL;

--select tabelas.sp_listar_produto (1,1,'789100010010')

---------------------------------------------------------------------------------
--view tabelas.vw_listar_localizacao
---------------------------------------------------------------------------------

create view tabelas.vw_localizacao
as    select 
	ZIP,
	Lat,
	Lng
	from tabelas.LOCALIZACAO;	

---------------------------------------------------------------------------------
--FUNCTION tabelas.sp_listar_localizacao_geo
---------------------------------------------------------------------------------

CREATE or replace FUNCTION tabelas.sp_localizacao_geo(char (9)) RETURNS SETOF tabelas.vw_localizacao AS $$
	select * from tabelas.vw_localizacao where zip = $1;
$$ LANGUAGE SQL;


---------------------------------------------------------------------------------
--Indices
---------------------------------------------------------------------------------

ALTER TABLE TABELAS.LOJA OWNER TO POSTGRES;

CREATE INDEX IDX_TABELASLOJACEP
  ON TABELAS.LOJA
  USING BTREE
  (CEP);

CREATE INDEX IDX_TABELASLOJAUF
  ON TABELAS.LOJA
  USING BTREE
  (ESTADO);

ALTER TABLE TABELAS.ENDERECO OWNER TO POSTGRES;

CREATE INDEX IDX_TABELASENDERECO
  ON TABELAS.ENDERECO
  USING BTREE
  (CEP);


ALTER TABLE TABELAS.PRODUTO OWNER TO POSTGRES;

CREATE INDEX IDX_TABELASPRODUTO
  ON TABELAS.PRODUTO
  USING BTREE
  (CODIGO_BARRAS);

ALTER TABLE TABELAS.PRODUTOGERAL OWNER TO POSTGRES;

CREATE INDEX IDX_TABELASPRODUTOGERAL
  ON TABELAS.PRODUTOGERAL
  USING BTREE
  (DESCRICAO);

ALTER TABLE TABELAS.LOCALIZACAO OWNER TO POSTGRES;

CREATE INDEX IDX_TABELASLOCALIZACAO
  ON TABELAS.LOCALIZACAO
  USING BTREE
  (ZIP);


ALTER TABLE TABELAS.CEP OWNER TO POSTGRES;

CREATE INDEX IDX_TABELASCEP
  ON TABELAS.CEP
  USING BTREE
  (CEP);



ALTER TABLE TABELAS.PRECO OWNER TO POSTGRES;

CREATE INDEX IDX_TABELASPRECO
  ON TABELAS.PRECO
  USING BTREE
  (id_rede, id_loja, codigo_barras);



/*
SELECT SPCNAME AS “TABLESPACE”,
PG_SIZE_PRETTY(PG_TABLESPACE_SIZE (SPCNAME)) AS “TAMANHO”,
SPCLOCATION AS “CAMINHO”
FROM PG_TABLESPACE;
*/






/*
SELECT SPCNAME AS “TABLESPACE”,
PG_SIZE_PRETTY(PG_TABLESPACE_SIZE (SPCNAME)) AS “TAMANHO”,
SPCLOCATION AS “CAMINHO”
FROM PG_TABLESPACE;
*/


*/