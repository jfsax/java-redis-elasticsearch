# java-redis-elasticsearch

## antes de executar
antes de executar o projeto, instale:
- <a href="https://www.oracle.com/java/technologies/downloads/">java</a>
- <a href="https://maven.apache.org/download.cgi">maven</a>
- <a href="https://redis.io/download/">redis</a>

## executando o redis
- configure seu bash profile:
```
code ~/.bash_profile

alias "redis_start= ~/Desktop/redis/bin/redis-server"
alias "redis= ~/Desktop/redis/bin/redis-cli"

source ~/.bash_profile
```

- abra um terminal e execute o seguinte comando para iniciar o servidor redis:
```
redis_start
```

- abra uma segunda instância do terminal e execute o seguinte comando para acessar o cliente redis:
```
redis
```

## executando o projeto

- através de um terceiro terminal, clone o repositório:
```
git clone git@github.com:jfsax/java-redis-elasticsearch.git
```

- vá para a pasta raiz do projeto:
```
cd java-redis-elasticsearch
```

- execute o comando:
```
./start.sh
```
