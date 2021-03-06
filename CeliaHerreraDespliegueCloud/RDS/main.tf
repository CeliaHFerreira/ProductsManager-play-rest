provider "aws" {
  region = "eu-west-3"
}

resource "aws_db_instance" "products" {
  allocated_storage        = 20
  db_subnet_group_name     = "${var.rds_public_subnet_group}"
  engine                   = "postgres"
  engine_version           = "12.5"
  identifier               = "products"
  instance_class           = "db.t2.micro"
  multi_az                 = false
  name                     = "products"
  password                 = "celiar00t"
  port                     = 5432
  publicly_accessible      = true
  storage_type             = "gp2"
  username                 = "postgres"
  vpc_security_group_ids   = ["${aws_security_group.products_sg.id}"]
   skip_final_snapshot     = true
}

resource "aws_security_group" "products_sg" {
  name = "products_sg"

  description = "RDS postgres servers (terraform-managed)"
  vpc_id = "${var.rds_vpc_id}"

  # Only postgres in
  ingress {
    from_port = 5432
    to_port = 5432
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  # Allow all outbound traffic.
  egress {
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

variable "rds_vpc_id" {
  default = "vpc-08c03460"
  description = "default RDS virtual private cloud (rds_vpc)."
}

variable "rds_public_subnets" {
  default = "subnet-b4e8fbdd,subnet-1d8ba166,subnet-3596e978"
  description = "public subnets of RDS VPC rds-vpc."
}

variable "rds_public_subnet_group" {
  default = "default-vpc-08c03460"
  description = "group name."
}

resource "aws_route53_zone" "primary" {
  name = "productsmanager.db.com"
}

resource "aws_route53_record" "products_db" {
  zone_id = aws_route53_zone.primary.zone_id
  name = "www.test.productsmanager.db.com"
  type = "CNAME"
  ttl = "300"
  records = [aws_db_instance.products.address]
}