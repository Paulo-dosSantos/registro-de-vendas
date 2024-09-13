# Sistema de Cadastro de Vendas

Este é um projeto de exemplo usando Spring Boot e PostgreSQL para criar um sistema de cadastro de vendas, que permite o gerenciamento de usuários, produtos, pedidos, pagamentos e itens de pedidos.

## Funcionalidades

- Cadastro de usuários.
- Cadastro de produtos e categorias.
- Gerenciamento de pedidos e itens de pedidos.
- Registro de status de pedidos.
- Integração com banco de dados PostgreSQL.

## Modelo de Domínio

O modelo de domínio do sistema é composto pelas seguintes entidades:

- **User**: Representa o cliente, com informações como nome, email, telefone e senha.
- **Order**: Representa um pedido realizado por um cliente. Contém informações como data, status e total.
- **OrderItem**: Representa um item de pedido, incluindo a quantidade e o subtotal.
- **Product**: Representa um produto com detalhes como nome, descrição, preço e URL da imagem.
- **Category**: Representa uma categoria de produtos.
- **Payment**: Representa o pagamento associado a um pedido.
- **OrderStatus**: Enumeração para representar o status do pedido, como `WAITING_PAYMENT`, `PAID`, `SHIPPED`, `DELIVERED`, `CANCELED`.

## Relacionamentos entre Entidades

- **User** possui uma relação *um-para-muitos* com **Order** (um usuário pode ter muitos pedidos).
- **Order** possui uma relação *um-para-muitos* com **OrderItem** (um pedido pode ter muitos itens).
- **OrderItem** possui uma relação *muitos-para-um* com **Product** (um item de pedido está associado a um produto).
- **Product** possui uma relação *muitos-para-muitos* com **Category** (um produto pode pertencer a várias categorias e uma categoria pode ter vários produtos).
- **Order** possui uma relação *um-para-um* opcional com **Payment** (um pedido pode ter um pagamento associado).
  
## Tecnologias Utilizadas

- **Spring Boot**: Framework para simplificar o desenvolvimento de aplicações Java.
- **Spring Data JPA**: Abordagem para o acesso simplificado a dados com base em Java Persistence API (JPA).
- **PostgreSQL**: Banco de dados relacional para armazenamento de dados.
- **Lombok**: Biblioteca para reduzir o boilerplate no código Java.

## Requisitos

- JDK 11+
- Maven
- PostgreSQL

## Instalação

1. Clone o repositório:

   ```bash
   git clone https://github.com/Paulo-dosSantos/sistema-cadastro-vendas.git
