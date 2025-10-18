create database if not exists appcolegio;
-- drop database appcolegio;
use appcolegio;

-- TABLAS
-- ROL
create table tb_rol(
	id int auto_increment primary key,
    nombre varchar(50) unique not null
);

-- USUARIO
create table tb_usuario(
	id int auto_increment primary key,
    nombre varchar(50) not null,
    apepa varchar(50) not null,
    apema varchar(50) not null,
    dni char(8) unique not null,
    email varchar(100) unique not null,
    pwd varchar(60) not null,
    id_rol int not null,
    estado int default 1,
    constraint fk_usuario_rol foreign key (id_rol) references tb_rol (id)
);

-- ADMINISTRADOR
create table tb_administrador(
	id int primary key,
    constraint fk_administrador_usuario foreign key (id) references tb_usuario (id)
);

-- ALUMNO
create table tb_alumno(
	id int primary key,
    edad int not null,
    constraint fk_alumno_usuario foreign key (id) references tb_usuario (id)
);

-- MATERIA
create table tb_materia(
	id int auto_increment primary key,
    nombre varchar(100) unique not null
);

-- CURSO
create table tb_curso(
	id int auto_increment primary key,
    nombre varchar(100) unique not null,
    descripcion text,
    id_materia int not null,
    constraint fk_curso_materia foreign key (id_materia) references tb_materia (id)
);

-- DOCENTE
create table tb_docente(
	id int primary key,
    id_materia int not null,
    telefono char(9) unique not null,
    constraint fk_docente_usuario foreign key (id) references tb_usuario (id),
    constraint fk_docente_materia foreign key (id_materia) references tb_materia (id)
);

-- SALON
create table tb_salon(
	id int auto_increment primary key,
    capacidad_maxima int not null default 20
);

-- DETALLE - CURSO, DOCENTE Y SALON
create table tb_curso_docente_salon(
	id int auto_increment primary key,
    id_curso int not null,
    id_docente int not null,
    id_salon int not null,
    constraint fk_cds_curso foreign key (id_curso) references tb_curso (id),
    constraint fk_cds_usuario_docente foreign key (id_docente) references tb_docente (id),
    constraint fk_cds_salon foreign key (id_salon) references tb_salon (id)
);

-- HORARIO
create table tb_horario(
	id int auto_increment primary key,
    dia enum('LUNES','MARTES','MIERCOLES','JUEVES','VIERNES') not null,
    hora_inicio time not null,
    hora_fin time not null
);

-- DETALLE - CURSO, DOCENTE, SALON, HORARIO
create table tb_cds_horario(
	id int auto_increment primary key,
	id_cds int not null,
    id_horario int not null,
    constraint fk_cds_horario_cds foreign key (id_cds) references tb_curso_docente_salon (id),
    constraint fk_cds_horario_horario foreign key (id_horario) references tb_horario (id)
);

-- MATRICULA
create table tb_matricula (
	id int auto_increment primary key,
    id_alumno int not null,
    id_cds int not null,
    fecha_creacion datetime not null default current_timestamp,
    estado enum('ACTIVA','FINALIZADA','RETIRADA') not null,
    constraint fk_matricula_alumno foreign key (id_alumno) references tb_alumno (id),
    constraint fk_matricula_cds foreign key (id_cds) references tb_curso_docente_salon (id)
);

-- INDICES --

-- USUARIO
create index idx_usuario_nombre on tb_usuario(nombre);
create index idx_usuario_apepa on tb_usuario(apepa);
create index idx_usuario_id_rol on tb_usuario(id_rol);
create index idx_usuario_estado on tb_usuario(estado);

-- ALUMNO
create index idx_alumno_edad on tb_alumno(edad);

-- CURSO
create index idx_curso_id_materia on tb_curso(id_materia);

-- DOCENTE
create index idx_docente_id_materia on tb_docente(id_materia);

-- SALON
create index idx_salon_capacidad on tb_salon(capacidad_maxima);

-- DETALLE - CURSO_DOCENTE_SALON
create index idx_cds_curso on tb_curso_docente_salon(id_curso);
create index idx_cds_docente on tb_curso_docente_salon(id_docente);
create index idx_cds_salon on tb_curso_docente_salon(id_salon);

-- HORARIO
create index idx_horario_dia on tb_horario(dia);

-- DETALLE - CURSO, DOCENTE, SALON, HORARIO
create index idx_cds_horario_id_cds on tb_cds_horario(id_cds);
create index idx_cds_horario_id_horario on tb_cds_horario(id_horario);

-- MATRICULA
create index idx_matricula_id_alumno on tb_matricula(id_alumno);
create index idx_matricula_id_cds on tb_matricula(id_cds);
create index idx_matricula_estado on tb_matricula(estado);
create index idx_matricula_fecha on tb_matricula(fecha_creacion);

-- INSERTS
-- ROL
INSERT INTO tb_rol (nombre) 
VALUES ('ADMIN'), ('ALUMNO'), ('DOCENTE'), ('PENDIENTE');

-- USUARIO PRINCIPAL
INSERT INTO tb_usuario (nombre, apepa, apema, dni, email, pwd, id_rol, estado) 
VALUES ('Piero', 'Caro', 'Jara', '12345678', 'piero@colegio.com', '$2a$10$RjRdnnaldjsLMjBDhlkwSecqNKGs5ZQ6GP882le/3USf3XOQAq82S', 1, 1);

-- ADMINISTRADOR ASOCIADO AL USUARIO PRINCIPAL
INSERT INTO tb_administrador (id) 
VALUES (1);

-- MATERIA
INSERT INTO tb_materia (nombre)
VALUES ('MATEMATICA'), ('LETRAS'), ('CIENCIAS'), ('ARTE'), ('SOCIALES');