# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table marca (
  marca_id                      varchar(255) not null,
  nombre                        varchar(255),
  categoria_id                  varchar(255),
  producto_id                   varchar(255),
  vegano                        boolean,
  constraint pk_marca primary key (marca_id)
);


# --- !Downs

drop table if exists marca;

