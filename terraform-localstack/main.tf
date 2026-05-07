terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

variable "use_localstack" {
  default = true
}

provider "aws" {
  region     = "eu-west-1"
  access_key = "test"
  secret_key = "test"
  skip_credentials_validation = true
  skip_requesting_account_id  = true
  skip_metadata_api_check     = true

  endpoints {
    # ecs              = "http://localhost:4566"  # Pro feature
    # elbv2            = "http://localhost:4566"  # Pro feature
    # servicediscovery = "http://localhost:4566"  # Pro feature
    iam              = "http://localhost:4566"
    logs             = "http://localhost:4566"
    sts              = "http://localhost:4566"
    ec2              = "http://localhost:4566"
  }
}
