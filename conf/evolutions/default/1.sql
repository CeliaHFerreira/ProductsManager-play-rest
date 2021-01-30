# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table categoria (
  categoria_id                  bigint auto_increment not null,
  nombre                        varchar(255),
  marca_id                      varchar(255),
  constraint pk_categoria primary key (categoria_id)
);

create table categoria_producto (
  categoria_categoria_id        bigint not null,
  producto_id2                  bigint not null,
  constraint pk_categoria_producto primary key (categoria_categoria_id,producto_id2)
);

create table marca (
  marca_id2                     bigint auto_increment not null,
  nombre                        varchar(255),
  marca_id_id2                  bigint,
  categoria_id                  varchar(255),
  vegano                        boolean,
  constraint pk_marca primary key (marca_id2)
);

create table marcas (
  id2                           bigint auto_increment not null,
  nombre                        varchar(255),
  constraint pk_marcas primary key (id2)
);

create table producto (
  id2                           bigint auto_increment not null,
  nombre                        varchar(255),
  producto_id_marca_id2         bigint,
  marca_id                      varchar(255),
  vegano                        boolean,
  apto_cg                       boolean,
  pvp                           double,
  hnr                           varchar(255),
  constraint pk_producto primary key (id2)
);

create index ix_categoria_producto_categoria on categoria_producto (categoria_categoria_id);
alter table categoria_producto add constraint fk_categoria_producto_categoria foreign key (categoria_categoria_id) references categoria (categoria_id) on delete restrict on update restrict;

create index ix_categoria_producto_producto on categoria_producto (producto_id2);
alter table categoria_producto add constraint fk_categoria_producto_producto foreign key (producto_id2) references producto (id2) on delete restrict on update restrict;

create index ix_marca_marca_id_id2 on marca (marca_id_id2);
alter table marca add constraint fk_marca_marca_id_id2 foreign key (marca_id_id2) references marcas (id2) on delete restrict on update restrict;

create index ix_producto_producto_id_marca_id2 on producto (producto_id_marca_id2);
alter table producto add constraint fk_producto_producto_id_marca_id2 foreign key (producto_id_marca_id2) references marca (marca_id2) on delete restrict on update restrict;


# --- !Downs

alter table categoria_producto drop constraint if exists fk_categoria_producto_categoria;
drop index if exists ix_categoria_producto_categoria;

alter table categoria_producto drop constraint if exists fk_categoria_producto_producto;
drop index if exists ix_categoria_producto_producto;

alter table marca drop constraint if exists fk_marca_marca_id_id2;
drop index if exists ix_marca_marca_id_id2;

alter table producto drop constraint if exists fk_producto_producto_id_marca_id2;
drop index if exists ix_producto_producto_id_marca_id2;

drop table if exists categoria;

drop table if exists categoria_producto;

drop table if exists marca;

drop table if exists marcas;

drop table if exists producto;

