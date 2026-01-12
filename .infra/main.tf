resource "random_string" "four_char_string" {
  length = 4
  # Optional: Customize character sets
  # upper = true  # Include uppercase letters (default: true)
  # lower = true  # Include lowercase letters (default: true)
  # number = true # Include numeric characters (default: true)
  special = false # Exclude special characters (default: true)
}

resource "random_string" "index_p_sn" {
  length = 1
  # Optional: Customize character sets
  # upper = false  # Include uppercase letters (default: true)
  # lower = false  # Include lowercase letters (default: true)
  # number = true # Include numeric characters (default: true)
  special = false # Exclude special characters (default: true)

}

module "vpc" {
  source = "terraform-aws-modules/vpc/aws"

  name = "${var.mahocvien}-vpc-${var.region_code}-${var.environment_code}-${random_string.four_char_string.result}"
  cidr = var.vpc_cidr 

  azs             = var.vpc_azs 
  private_subnets = var.private_sn 
  public_subnets  = var.public_sn
  enable_nat_gateway = var.is_enable_nat

  tags = {
    Terraform = "True"
    Environment = var.environment_code
  }
}

data "aws_ami" "ubuntu" {
  most_recent = true

  filter {
    name   = "name"
    values = ["ubuntu/images/hvm-ssd/ubuntu-jammy-22.04-amd64-server-*"]
  }

  filter {
    name   = "virtualization-type"
    values = ["hvm"]
  }

  owners = ["099720109477"] # Canonical
}

# resource "aws_instance" "public_ec2" {
#   # count = var.is_create ? 1 : 0

#   ami           = data.aws_ami.ubuntu.id
#   instance_type = "t3.micro"
#   associate_public_ip_address  = true
#   subnet_id     = module.vpc.public_subnets[0]  # arr1 = ["id_p1", "id_p2", "id_p3"] #"subnet-07bf3c7c8e40e9a4e" arr1[1]

#   tags = {
#     Name = "HelloWorld"
#   }


# }
