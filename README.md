# new-project-banksystem-with-spring
Novo Projeto Sistema de Banco com Spring
<br>STATUS: Em Construção

# Frameworks do Projeto
- SpringBoot
- SpringData (JPA, Hibernate)
- SpringFramework
- SpringWeb (REST Producer)
- OpenFeign (REST Consumer)
- Flyway
- Lombok
- Redis (Cache)

# Ferramentas Sincronizadas
- Postman (Para chamadas REST)
- IntelliJ IDE
- MySQL (DBeaver)

# Sobre o Projeto
Projeto é baseado em um sistema bancário com todas 
suas funções básicas como Cadastro de Conta, Login, 
Depósito, Transferência e Saque.
<br> O projeto esta sendo construido com todas as 
frameworks citadas acima, utilizando as funções mais 
basicas para um projeto coeso e desacoplado.
- Com SpringFramework, utilizamos a Injeção de 
Depências ao qual nos trás mais performance no código 
e menor consumo de memória.
- Com SpringBoot, utilizamos a parte de gerênciamento 
de algumas dependencias do projeto e as pré configs 
básicas do projeto.
- Com SpringData JPA, utilizamos para fazer a manipulação 
dos nossos dados no DB, juntamente com o Hibernate 
para fazer o mapeamento das entidade.
- Com SpringWeb, usamos para a externalizar nossas API's 
que produzimos via REST.
- Com OpenFeign, utilizamos para fazer o consumo da 
API do ViaCep para obter um endereço a partir de um 
CEP.
- Com o Flyway, usamos para o controle de versionamento 
e gerênciamento do nosso DB através do Script.
- Com o Lombok, usamos todas as suas ferramentas 
para tornar o código mais limpo, pois 
ele trás as funcionalidades mais comuns em forma de 
Annotations, como: Getters e Setter, ToString, Construtores, 
Builder, entre outros.
- Por fim o Redis para expurgo de dados em memória 
juntamente com Schedulers para limitar saques e expirar 
Tokens de acesso.
