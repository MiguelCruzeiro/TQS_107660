## A.

AssertJ é usado para verificar se o retorno de uma pesquisa que nao existe na DB é vazio.

```java
@Test
void whenInvalidEmployeeName_thenReturnNull() {
    Employee fromDb = employeeRepository.findByName("Does Not Exist");
    assertThat(fromDb).isNull();
}
```

Verifica se o Employee que é retornado nao é null e o email coincide com o email original.

```java
@Test
    void whenFindEmployedByExistingId_thenReturnEmployee() {
        Employee emp = new Employee("test", "test@deti.com");
        entityManager.persistAndFlush(emp);

        Employee fromDb = employeeRepository.findById(emp.getId()).orElse(null);
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getEmail()).isEqualTo(emp.getEmail());
    }
```

## B.

O Mock é injetado em  EmployeeRepository que simula um objeto que pode ser configurado para se comportar de maneira especifica durante os testes.  
(lenient = true) é usado para que quando métodos que nao foram configurados pelo mock sejam chamados nao dar erro e retornar um valor padrao.

```java
@Mock( lenient = true)
    private EmployeeRepository employeeRepository;
```


## C. 

**@Mock** cria objetos falsos para simular um sistema e para testar certas partes do código.

**@MockBean** é mais especifico para SpringBoot. Também cria objetos falsos para substituir partes reais do sistema como os repositorios ou serviços e testar essas mesmas partes.

## D.

Estas configuraçoes sao usadas para a conexao com uma base de dados num sistema SpringBoot. Define também o comportamento de criaçao e atualizaçao do esquema de base de dados.
É útil para o desenvolvimento de testes de integraçao.

```java
    spring.datasource.url=jdbc:mysql://localhost:33060/tqsdemo
    spring.jpa.hibernate.ddl-auto=create-drop
    spring.datasource.username=demo
    spring.datasource.password=demo
```


## E.

### C - Teste com MockMvc

É usado para testar controllers de forma isolada, sem necessidade de iniciar a aplicaçao. o MockMvc simula HTTP requests e verifica as respostas do controller.

### D - Teste de integraçao com DB falsa

São testes de integraçao em que é necessário iniciar a aplicaçao por completo, mas que usa uma DB falsa em memoria. Verifica se as operaçoes com a Db estao a funcionar corretamente.

### E - Teste de integraçao com TestRestTemplate

Aqui é usado o TestRestTemplate para simular os HTTP requests. Verifica se todas as chamadas e respostas à api estao corretas e é o teste que mais se aproxima do ambiente de produção.



