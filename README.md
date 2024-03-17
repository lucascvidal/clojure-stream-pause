# Playback API

Este projeto é uma solução proposta a um exercício. É uma aplicação desenvolvida em Clojure com bibliotecas comuns para desenvolvimento web:
- Ring;
- Compojure;
- Jetty.

## Arquitetura

A solução para o exercício consiste em 2 partes, a API de reprodução e o banco de dados. O frontend é mencionado no diagrama, mas não faz parte deste projeto.
![Playback API diagram](./resources/bp-backend-ex.png")

Obs.: Se a imagem acima falhar, você consegue visualizá-la na pasta resources/ do projeto.

### Componentes da API

- Router: direcionar as requisições para os endpoints específicos da API aos handlers;
- Middleware: preparar os parâmetros tanto em query string quanto no body da requsição para os handlers;
- Handler: manipular dados para responder à requisição feita;
- Position: lidar com a inserção ou leitura dos registros no banco de dados;
- DB: guardar a configuração para conexão com o banco de dados;
- Utils: armazenar diferentes funções com objetivos variados, servindo de suporte às responsabilidades das outras partes da API.

## Instruções para uso

Este projeto usa docker compose, então para começar, precisamos subir os containers:
```bash
docker compose up
```

Isso construirá a imagem do aplicativo e puxará (caso você ainda não tenha feito) a imagem do postgres. A maneira como os containers estão configurados é que eles não executam um comando e saem, eles permanecem ativos até que você os desligue explicitamente. Portanto, para executar o servidor ou os testes, você deve primeiro abrir um bash no contêiner:
```bash
docker exec -it playback-api bash
```

## Subindo o servidor

Com o bash aberto:
```bash
lein run
```

O servidor está ativo. Para interagir com ele, recomendo usar o Postman. No diretório de recursos (resources/), há um arquivo json da coleção que usei para desenvolver este exercício.

## Rodando os testes

Os testes não eram obrigatórios, embora desejados. Para executar os testes, abra o bash e, em seguida:
```bash
lein test
```

## Postgres como a escolha para armazenamento

O primeiro impulso foi pensar em salvar algo no local storage do navegador, ou nos cookies até. Porém, essa abordagem se restringiria ao dispositivo em que o usuário estava assistindo o vídeo naquele momento. Como o usuário poderia querer continuar a assistir o vídeo em outros dispositivos, essa abordagem não funciona. Partindo deste ponto, precisamos persistir as informações em disco. Tecnologias que nos permitem persistir dados em disco existem aos montes, mas acredito que a escolha por algum banco de dados deveria ser justificada por conta da facilidade de buscar informações uma vez armazenadas lá.

Pesquisando para iniciar este projeto, vi 2 opções: Datomic ou Postgres. Datomic parecia ter um hype ao seu redor que talvez seja justificado para outros tipos de aplicações, mas para este projeto, não consegui ver por que deveria escolhê-lo. Os requisitos eram persistir dados no disco, sendo os dados as posições de um determinado vídeo reproduzido pelo usuário. Não é relevante para o usuário ver todo o histórico dessas posições de mídia, então o principal recurso do Datomic (pelo menos o que mais apareceu na pesquisa) não parecia tão importante aqui. Além disso, como eu tinha tempo limitado para trabalhar neste projeto e era a primeira vez que estava fazendo qualquer coisa relacionada a Clojure, o outro motivo pelo qual escolhi Postgres foi por familiaridade com ele. Claro, a primeira coisa que verifiquei foi se ele poderia lidar com a tarefa, e como ele conseguia, a familiaridade foi um segundo critério usado para decidir entre Datomic e Postgres.
