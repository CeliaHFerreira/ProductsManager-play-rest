# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table categoria (
  id                            bigint auto_increment not null,
  nombre                        varchar(255),
  constraint pk_categoria primary key (id)
);

create table categoria_producto (
  categoria_id                  bigint not null,
  producto_id                   bigint not null,
  constraint pk_categoria_producto primary key (categoria_id,producto_id)
);

create table codigo (
  id                            bigint auto_increment not null,
  codigo_barras                 varchar(255),
  id_producto_id                bigint,
  constraint uq_codigo_id_producto_id unique (id_producto_id),
  constraint pk_codigo primary key (id)
);

create table marca (
  id                            bigint auto_increment not null,
  nombre                        varchar(255),
  marcas_id_id                  bigint,
  vegano                        boolean,
  constraint pk_marca primary key (id)
);

create table marca_categoria (
  marca_id                      bigint not null,
  categoria_id                  bigint not null,
  constraint pk_marca_categoria primary key (marca_id,categoria_id)
);

create table marcas (
  id                            bigint auto_increment not null,
  nombre                        varchar(255),
  constraint pk_marcas primary key (id)
);

create table producto (
  id                            bigint auto_increment not null,
  nombre                        varchar(255),
  marca_id_id                   bigint,
  vegano                        boolean,
  apto_cg                       boolean,
  pvp                           double,
  hnr                           varchar(255),
  nombre_marca                  varchar(255),
  constraint pk_producto primary key (id)
);

create index ix_categoria_producto_categoria on categoria_producto (categoria_id);
alter table categoria_producto add constraint fk_categoria_producto_categoria foreign key (categoria_id) references categoria (id) on delete restrict on update restrict;

create index ix_categoria_producto_producto on categoria_producto (producto_id);
alter table categoria_producto add constraint fk_categoria_producto_producto foreign key (producto_id) references producto (id) on delete restrict on update restrict;

alter table codigo add constraint fk_codigo_id_producto_id foreign key (id_producto_id) references producto (id) on delete restrict on update restrict;

create index ix_marca_marcas_id_id on marca (marcas_id_id);
alter table marca add constraint fk_marca_marcas_id_id foreign key (marcas_id_id) references marcas (id) on delete restrict on update restrict;

create index ix_marca_categoria_marca on marca_categoria (marca_id);
alter table marca_categoria add constraint fk_marca_categoria_marca foreign key (marca_id) references marca (id) on delete restrict on update restrict;

create index ix_marca_categoria_categoria on marca_categoria (categoria_id);
alter table marca_categoria add constraint fk_marca_categoria_categoria foreign key (categoria_id) references categoria (id) on delete restrict on update restrict;

create index ix_producto_marca_id_id on producto (marca_id_id);
alter table producto add constraint fk_producto_marca_id_id foreign key (marca_id_id) references marca (id) on delete restrict on update restrict;


# --- !Downs

alter table categoria_producto drop constraint if exists fk_categoria_producto_categoria;
drop index if exists ix_categoria_producto_categoria;

alter table categoria_producto drop constraint if exists fk_categoria_producto_producto;
drop index if exists ix_categoria_producto_producto;

alter table codigo drop constraint if exists fk_codigo_id_producto_id;

alter table marca drop constraint if exists fk_marca_marcas_id_id;
drop index if exists ix_marca_marcas_id_id;

alter table marca_categoria drop constraint if exists fk_marca_categoria_marca;
drop index if exists ix_marca_categoria_marca;

alter table marca_categoria drop constraint if exists fk_marca_categoria_categoria;
drop index if exists ix_marca_categoria_categoria;

alter table producto drop constraint if exists fk_producto_marca_id_id;
drop index if exists ix_producto_marca_id_id;

drop table if exists categoria;

drop table if exists categoria_producto;

drop table if exists codigo;

drop table if exists marca;

drop table if exists marca_categoria;

drop table if exists marcas;

drop table if exists producto;

