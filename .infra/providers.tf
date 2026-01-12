provider "aws" {
  region = "us-east-1"
}

terraform {
  backend "s3" {
    bucket         = "huy99-tf-state-ue1-lenv-01"
    key            = "do01/dev"
    region         = "us-east-1"
  }
  required_providers {
    aws = {
      source  = "hashicorp/aws"
    }
  }
}
