# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table categoria (
  id                            bigint auto_increment not null,
  nombre                        varchar(255),
  categoria_id                  bigint,
  marca_id                      varchar(255),
  constraint pk_categoria primary key (id)
);

create table categoria_producto (
  categoria_id                  bigint not null,
  producto_id                   bigint not null,
  constraint pk_categoria_producto primary key (categoria_id,producto_id)
);

create table marca (
  id                            bigint auto_increment not null,
  nombre                        varchar(255),
  marca_id_id                   bigint,
  categoria_id                  varchar(255),
  vegano                        boolean,
  constraint pk_marca primary key (id)
);

create table marcas (
  id                            bigint auto_increment not null,
  nombre                        varchar(255),
  constraint pk_marcas primary key (id)
);

create table producto (
  id                            bigint auto_increment not null,
  nombre                        varchar(255),
  producto_id_id                bigint,
  marca_id                      varchar(255),
  vegano                        boolean,
  apto_cg                       boolean,
  pvp                           double,
  hnr                           varchar(255),
  constraint pk_producto primary key (id)
);

create index ix_categoria_producto_categoria on categoria_producto (categoria_id);
alter table categoria_producto add constraint fk_categoria_producto_categoria foreign key (categoria_id) references categoria (id) on delete restrict on update restrict;

create index ix_categoria_producto_producto on categoria_producto (producto_id);
alter table categoria_producto add constraint fk_categoria_producto_producto foreign key (producto_id) references producto (id) on delete restrict on update restrict;

create index ix_marca_marca_id_id on marca (marca_id_id);
alter table marca add constraint fk_marca_marca_id_id foreign key (marca_id_id) references marcas (id) on delete restrict on update restrict;

create index ix_producto_producto_id_id on producto (producto_id_id);
alter table producto add constraint fk_producto_producto_id_id foreign key (producto_id_id) references marca (id) on delete restrict on update restrict;


# --- !Downs

alter table categoria_producto drop constraint if exists fk_categoria_producto_categoria;
drop index if exists ix_categoria_producto_categoria;

alter table categoria_producto drop constraint if exists fk_categoria_producto_producto;
drop index if exists ix_categoria_producto_producto;

alter table marca drop constraint if exists fk_marca_marca_id_id;
drop index if exists ix_marca_marca_id_id;

alter table producto drop constraint if exists fk_producto_producto_id_id;
drop index if exists ix_producto_producto_id_id;

drop table if exists categoria;

drop table if exists categoria_producto;

drop table if exists marca;

drop table if exists marcas;

drop table if exists producto;

