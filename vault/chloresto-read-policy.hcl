path "secret/application" {
  capabilities = ["read"]
}

path "secret/application/devora" {
  capabilities = ["read"]
}

path "secret/chloresto" {
  capabilities = ["read"]
}

path "secret/chloresto/*" {
  capabilities = ["read"]
}

path "secret/data/application" {
  capabilities = ["read"]
}

path "secret/data/application/devora" {
  capabilities = ["read"]
}

path "secret/data/chloresto" {
  capabilities = ["read"]
}

path "secret/data/chloresto/*" {
  capabilities = ["read"]
}
