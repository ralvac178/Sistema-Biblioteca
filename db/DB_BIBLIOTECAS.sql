CREATE DATABASE BibliotecaPro;
-- activamos la bd
USE BibliotecaPro;

-- User Table
CREATE TABLE tb_user (
	dni_user CHAR(8),
    nom_user VARCHAR(16),
    apell_user VARCHAR(16),
    telf_user CHAR(10),
    edad_user INT,
    PRIMARY KEY (dni_user)
);

-- Book Table
CREATE TABLE tb_book (
	id_book CHAR(8),
    tit_book VARCHAR(16),
    cat_book VARCHAR(16),
    año_book YEAR,
    pais_book VARCHAR(16),
    PRIMARY KEY (id_book)
);

-- Bibliothecary Table
CREATE TABLE tb_bibliothecary (
	id_bib CHAR(8),
    nom_bib VARCHAR(16),
    apell_bib VARCHAR(16),
    PRIMARY KEY (id_bib)
);

-- Loan Table
CREATE TABLE tb_loan(
	id_loan CHAR(8),
    id_book CHAR(8),
    dni_user CHAR(8),
    id_bib CHAR(8),
    fecha_loan date,
    fechaEntrega_loan date,
    PRIMARY KEY (id_loan),
    FOREIGN KEY (id_book) REFERENCES tb_book(id_book),
    FOREIGN KEY (dni_user) REFERENCES tb_user(dni_user),
    FOREIGN KEY (id_bib) REFERENCES tb_bibliothecary(id_bib)
);



