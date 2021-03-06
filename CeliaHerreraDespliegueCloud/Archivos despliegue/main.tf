provider "aws" {
  region = "eu-west-3"
}

data "aws_vpc" "default" {
  default = true
}

data "aws_subnet_ids" "default" {
  vpc_id = data.aws_vpc.default.id
}


resource "aws_launch_configuration" "play_products_manager" {
  image_id = "ami-00459cef3a475e14a"
  instance_type = "t2.micro"
  security_groups = [aws_security_group.play_products_manager_alb.id]

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_autoscaling_group" "play_products_manager_asg" {
  launch_configuration = aws_launch_configuration.play_products_manager.name
  vpc_zone_identifier = data.aws_subnet_ids.default.ids

  target_group_arns = [aws_lb_target_group.play_products_manager_tg.arn]
  health_check_type = "ELB"

  min_size = 3
  max_size = 3

  tag {
    key = "Name"
    value = "play-products-manager-asg"
    propagate_at_launch = false
  }
}

resource "aws_lb" "play_products_manager_lb" {
  name = "play-products-manager"
  load_balancer_type = "application"
  subnets = data.aws_subnet_ids.default.ids
  security_groups = [aws_security_group.play_products_manager_alb.id]
}

resource "aws_lb_listener" "http" {
  load_balancer_arn = aws_lb.play_products_manager_lb.arn
  port = 80
  protocol = "HTTP"

  default_action {
    type = "fixed-response"

    fixed_response {
      content_type = "text/plain"
      message_body = "Page Not Found"
      status_code = 404
    }
  }
}

resource "aws_security_group" "play_products_manager_alb" {
  name = "play-products-manager-alb"
  
  ingress {
    from_port = 80
    to_port = 80
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
      from_port = 0
      to_port = 0
      protocol = "-1"
      cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_lb_target_group" "play_products_manager_tg" {
  name = "play-products-manager-tg"
  port = 80
  protocol = "HTTP"
  vpc_id = data.aws_vpc.default.id

  health_check {
    path = "/"
    protocol = "HTTP"
    matcher = "200-499"
    interval = 60
    timeout = 30
    healthy_threshold = 3
    unhealthy_threshold = 3
  }
}

resource "aws_lb_listener_rule" "asg" {
  listener_arn = aws_lb_listener.http.arn
  priority = 100

  condition {
    path_pattern {
      values = ["*"]
    }
  }

  action {
    type = "forward"
    target_group_arn = aws_lb_target_group.play_products_manager_tg.arn
  }
}

output "play_alb_dns_name" {
  value = aws_lb.play_products_manager_lb
  description = "Domain name of the Play productss Manager application load balancer"
}