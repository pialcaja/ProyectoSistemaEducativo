create database if not exists appcolegio;
-- drop database appcolegio;
use appcolegio;

create table tb_tipo_usuario(
	codigo_tipo_usuario int auto_increment primary key,
    nombre_tipo_usuario varchar(50) not null
);

create table tb_usuario(
	codigo_usuario int auto_increment primary key,
    nombre_usuario varchar(50) not null,
    apellido_paterno_usuario varchar(50) not null,
    apellido_materno_usuario varchar(50) not null,
    dni_usuario char(8) unique not null,
    codigo_tipo_usuario int not null,
    estado_usuario enum('ACTIVO','INACTIVO') not null,
    constraint fk_tipo_usuario foreign key (codigo_tipo_usuario) references tb_tipo_usuario (codigo_tipo_usuario)
);

create table tb_administrador(
	codigo_usuario int primary key,
    email_administrador varchar(100) unique not null,
    pwd_administrador varchar(250) not null,
    constraint fk_usuario_administrador foreign key (codigo_usuario) references tb_usuario (codigo_usuario)
);

create table tb_alumno(
	codigo_usuario int primary key,
    edad_alumno int not null,
    constraint fk_usuario_alumno foreign key (codigo_usuario) references tb_usuario (codigo_usuario)
);

create table tb_categoria(
	codigo_categoria int auto_increment primary key,
    nombre_categoria varchar(100) not null
);

create table tb_curso(
	codigo_curso int auto_increment primary key,
    nombre_curso varchar(250) unique not null,
    codigo_categoria int not null,
    duracion_horas_curso int not null,
    constraint fk_categoria_curso foreign key (codigo_categoria) references tb_categoria (codigo_categoria)
);

create table tb_docente(
	codigo_usuario int primary key,
    codigo_categoria int not null,
    email_docente varchar(100) unique not null,
    telefono_docente char(9) unique not null,
    constraint fk_usuario foreign key (codigo_usuario) references tb_usuario (codigo_usuario),
    constraint fk_categoria_docente foreign key (codigo_categoria) references tb_categoria (codigo_categoria)
);

create table tb_salon(
	codigo_salon int auto_increment primary key,
    capacidad_maxima int not null default 20
);

create table tb_horario(
	codigo_horario int auto_increment primary key,
    dia_horario varchar(9) not null,
--    codigo_dia int not null,
    hora_inicio_horario time not null,
    hora_fin_horario time not null
);

create table tb_curso_docente_salon(
	codigo_curso_docente_salon int auto_increment primary key,
    codigo_curso int not null,
    codigo_docente int not null,
    codigo_salon int not null,
    fecha_inicio_curso datetime not null,
    fecha_fin_curso datetime not null,
    constraint fk_curso foreign key (codigo_curso) references tb_curso (codigo_curso),
    constraint fk_docente foreign key (codigo_docente) references tb_docente (codigo_usuario),
    constraint fk_salon foreign key (codigo_salon) references tb_salon (codigo_salon)
);

create table tb_curso_horario(
	codigo_curso_docente_salon int not null,
    codigo_horario int not null,
    primary key (codigo_curso_docente_salon, codigo_horario),
    constraint fk_curso_docente_salon foreign key (codigo_curso_docente_salon) references tb_curso_docente_salon (codigo_curso_docente_salon),
    constraint fk_horario foreign key (codigo_horario) references tb_horario (codigo_horario)
);

create table tb_tipo_nota(
	codigo_tipo_nota int auto_increment primary key,
    nombre_tipo_nota varchar(50) not null
);

create table tb_nota(
	codigo_alumno int not null,
    codigo_curso int not null,
    codigo_tipo_nota int not null,
    valor_nota int not null,
    fecha_nota datetime not null,
    primary key (codigo_alumno, codigo_curso, codigo_tipo_nota),
    constraint fk_alumno_nota foreign key (codigo_alumno) references tb_alumno (codigo_usuario),
    constraint fk_curso_nota foreign key (codigo_curso) references tb_curso (codigo_curso),
    constraint fk_tipo_nota foreign key (codigo_tipo_nota) references tb_tipo_nota (codigo_tipo_nota)
);

create table tb_matricula (
	codigo_matricula int auto_increment primary key,
    codigo_alumno int not null,
    codigo_curso_docente_salon int not null,
    fecha_matricula datetime not null default current_timestamp,
    estado_matricula enum('ACTIVA','FINALIZADA','RETIRADA') default 'ACTIVA',
    constraint fk_matricula_alumno foreign key (codigo_alumno) references tb_alumno (codigo_usuario) on delete cascade,
    constraint fk_matricula_curso_docente_salon foreign key (codigo_curso_docente_salon) references tb_curso_docente_salon (codigo_curso_docente_salon) on delete cascade
);

-- INDICES
create index idx_usuario_estado on tb_usuario (estado_usuario);
create index idx_alumno_edad on tb_alumno (edad_alumno);
create index idx_curso_categoria on tb_curso (codigo_categoria);
create index idx_docente_categoria on tb_docente (codigo_categoria);
create index idx_usuario_nombre_apellido on tb_usuario (nombre_usuario, apellido_paterno_usuario);
create index idx_matricula_alumno on tb_matricula(codigo_alumno);
create index idx_matricula_curso on tb_matricula(codigo_curso_docente_salon);

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