create database if not exists appcolegio;
-- drop database appcolegio;
use appcolegio;

create table tb_rol(
	id int auto_increment primary key,
    nombre varchar(50) unique not null
);

create table tb_estado(
	id int auto_increment primary key,
    nombre varchar(50) unique not null
);

create table tb_usuario(
	id int auto_increment primary key,
    nombre varchar(50) not null,
    apepa varchar(50) not null,
    apema varchar(50) not null,
    dni char(8) unique not null,
    email varchar(100) unique not null,
    pwd varchar(250) not null,
    id_rol int not null,
    id_estado int default 1,
    constraint fk_usuario_rol foreign key (id_rol) references tb_rol (id),
    constraint fk_usuario_estado foreign key (id_estado) references tb_estado (id)
);

create table tb_administrador(
	id int primary key,
    constraint fk_administrador_usuario foreign key (id) references tb_usuario (id)
);

create table tb_alumno(
	id int primary key,
    edad int not null,
    constraint fk_alumno_usuario foreign key (id) references tb_usuario (id)
);

create table tb_materia(
	id int auto_increment primary key,
    nombre varchar(100) unique not null
);

create table tb_curso(
	id int auto_increment primary key,
    nombre varchar(250) unique not null,
    descripcion text,
    id_materia int not null,
    constraint fk_curso_materia foreign key (id_materia) references tb_materia (id)
);

create table tb_docente(
	id int primary key,
    id_materia int not null,
    telefono char(9) unique not null,
    constraint fk_docente_usuario foreign key (id) references tb_usuario (id),
    constraint fk_docente_materia foreign key (id_materia) references tb_materia (id)
);

create table tb_salon(
	id int auto_increment primary key,
    capacidad_maxima int not null default 20
);

create table tb_curso_docente_salon(
	id int auto_increment primary key,
    id_curso int not null,
    id_docente int not null,
    id_salon int not null,
    constraint fk_cds_curso foreign key (id_curso) references tb_curso (id),
    constraint fk_cds_usuario_docente foreign key (id_docente) references tb_docente (id),
    constraint fk_cds_salon foreign key (id_salon) references tb_salon (id)
);

create table tb_horario(
	id int auto_increment primary key,
    dia enum('LUNES','MARTES','MIERCOLES','JUEVES','VIERNES') not null,
    hora_inicio time not null,
    hora_fin time not null
);

create table tb_cds_horario(
	id_cds int not null,
    id_horario int not null,
    primary key (id_cds, id_horario),
    constraint fk_cds_horario_cds foreign key (id_cds) references tb_curso_docente_salon (id),
    constraint fk_cds_horario_horario foreign key (id_horario) references tb_horario (id)
);

create table tb_matricula (
	id int auto_increment primary key,
    id_alumno int not null,
    id_cds int not null,
    fecha_creacion datetime not null default current_timestamp,
    estado int default 1,
    constraint fk_matricula_alumno foreign key (id_alumno) references tb_alumno (id),
    constraint fk_matricula_cds foreign key (id_cds) references tb_curso_docente_salon (id)
);

-- INDICES

-- INSERTS
-- TIPO USUARIO
INSERT INTO tb_tipo_usuario (nombre_tipo_usuario)
VALUES ('ADMIN');

-- USUARIO PRINCIPAL
INSERT INTO tb_usuario (
    nombre_usuario,
    apellido_paterno_usuario,
    apellido_materno_usuario,
    dni_usuario,
    codigo_tipo_usuario,
    estado_usuario
) VALUES (
    'Piero',               -- nombre_usuario
    'Caro',              -- apellido_paterno_usuario
    'Jara',               -- apellido_materno_usuario
    '12345678',            -- dni_usuario
    (SELECT codigo_tipo_usuario 
     FROM tb_tipo_usuario 
     WHERE nombre_tipo_usuario = 'ADMIN'), 
    'ACTIVO'
);

-- ADMINISTRADOR ASOCIADO AL USUARIO PRINCIPAL
INSERT INTO tb_administrador (
    codigo_usuario,
    email_administrador,
    pwd_administrador
) VALUES (
    (SELECT codigo_usuario 
     FROM tb_usuario 
     WHERE dni_usuario = '12345678'), 
    'piero@colegio.com', 
    '$2a$10$RjRdnnaldjsLMjBDhlkwSecqNKGs5ZQ6GP882le/3USf3XOQAq82S'
);

-- CATEGORIA
INSERT INTO tb_categoria (nombre_categoria)
VALUES ('Matematica'), ('Letras'), ('Ciencias');