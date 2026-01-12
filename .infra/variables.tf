variable "vpc_name" {
  type = string
  description = "Enter your VPC name"
  default = "my-vpc-default"
}

variable "vpc_cidr" {
  type = string
  description = "Enter your VPC CIDR"
  default = "10.0.0.0/16"
}

variable "vpc_azs" {
  type = list(string)
  description = "Enter your VPC azs"
  default = ["us-east-1a", "us-east-1b", "us-east-1c"]
}

variable "private_sn" {
  type = list(string)
  description = "Enter your VPC private subnets"
  default = ["10.0.1.0/24", "10.0.2.0/24", "10.0.3.0/24"]
}

variable "public_sn" {
  type = list(string)
  description = "Enter your VPC private subnets"
  default = ["10.0.101.0/24", "10.0.102.0/24", "10.0.103.0/24"]
}

variable "is_enable_nat" {
  type = bool
  description = "Do you want to enable NAT Gateway?"
  default = false
}

variable "environment_code" {
  type = string
  description = "Which is environment?"
  default = "Dev"
}

variable "mahocvien" {
  type = string
  description = "Owner"
  default = "Huy01"
}

variable "region_code" {
  type = string
  description = "Region that will be host AWS Resources"
  default = "ue1"
  validation {
    condition     = contains(["ue1", "uw2", "eu1"], var.region_code)
    error_message = "Invalid AWS region. Please choose from 'us-east-1', 'us-west-2', or 'eu-central-1'."
  }
}

variable "is_create" {
  type = bool
  description = "Turn this flag 1 to create this resource."
  default = true
}